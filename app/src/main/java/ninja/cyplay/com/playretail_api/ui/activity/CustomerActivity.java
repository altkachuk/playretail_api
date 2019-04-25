package ninja.cyplay.com.playretail_api.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import org.parceler.Parcels;

import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.Optional;
import ninja.cyplay.com.playretail_api.model.business.CustomerDetails;
import ninja.cyplay.com.playretail_api.model.business.CustomerPreview;
import ninja.cyplay.com.playretail_api.model.business.Offer;
import ninja.cyplay.com.playretail_api.model.business.Product;
import ninja.cyplay.com.playretail_api.model.business.Recommendation;
import ninja.cyplay.com.playretail_api.model.business.SalesHistory;
import ninja.cyplay.com.playretail_api.model.events.CustomerContextFoundEvent;
import ninja.cyplay.com.playretail_api.model.events.CustomerEditEvent;
import ninja.cyplay.com.playretail_api.model.events.CustomerInfoTabEvent;
import ninja.cyplay.com.playretail_api.model.events.GetCustomerInfoEvent;
import ninja.cyplay.com.playretail_api.model.events.GetSalesHistoryEvent;
import ninja.cyplay.com.playretail_api.model.events.HistoryTabEvent;
import ninja.cyplay.com.playretail_api.model.events.OfferTabEvent;
import ninja.cyplay.com.playretail_api.model.events.PlaylistTabEvent;
import ninja.cyplay.com.playretail_api.model.events.WishlistTabEvent;
import ninja.cyplay.com.apilibrary.models.singleton.APIValue;
import ninja.cyplay.com.playretail_api.utils.ColorUtils;
import ninja.cyplay.com.apilibrary.utils.Constants;
import ninja.cyplay.com.apilibrary.utils.DialogUtils;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.app.BaseActivity;
import ninja.cyplay.com.playretail_api.model.business.EFeature;
import ninja.cyplay.com.playretail_api.ui.adapter.CustomerSlidingViewPagerAdapter;
import ninja.cyplay.com.playretail_api.ui.component.SlidingTabLayout;
import ninja.cyplay.com.playretail_api.ui.presenter.GetCustomerInfoPresenter;
import ninja.cyplay.com.playretail_api.ui.presenter.GetSalesHistoryPresenter;
import ninja.cyplay.com.playretail_api.ui.view.GetCustomerInfoView;
import ninja.cyplay.com.playretail_api.ui.view.GetSalesHistoryView;
import ninja.cyplay.com.playretail_api.utils.ConfigHelper;

/**
 * Created by damien on 06/05/15.
 */
public class CustomerActivity extends BaseActivity implements GetCustomerInfoView, GetSalesHistoryView, ViewPager.OnPageChangeListener {

    @Inject
    public APIValue apiValue;

    @Inject
    public Bus bus;

    @Inject
    GetCustomerInfoPresenter getCustomerInfoPresenter;

    @Inject
    GetSalesHistoryPresenter getSalesHistoryPresenter;

    @Optional
    @InjectView(R.id.loading_panel)
    LinearLayout loadingPanel;

    @Optional
    @InjectView(R.id.progress_bar)
    ProgressBar progressBar;

    @Optional
    @InjectView(R.id.tabs)
    public SlidingTabLayout tabs;

    @Optional
    @InjectView(R.id.customer_pager)
    public ViewPager viewPager;

    private CustomerSlidingViewPagerAdapter adapter;

    // Menu
    private Menu menu = null;

    // Extra
    private CustomerPreview customerPreview;

    // Vars
    public CustomerDetails customerDetails;
    public Recommendation recommendation;
    public List<Offer> offers;
    public List<Product> wishlist;
    public List<SalesHistory> salesHistories;

    // To detect if an error Occur while getting sales History
    public boolean salesHistoryError = false;

    // Vars key (retain)
    private final static String VAR_CUSTOMER_DETAILS = "VAR_CUSTOMER_DETAILS";
    private final static String VAR_RECOMMENDATION = "VAR_RECOMMENDATION";
    private final static String VAR_OFFERS = "VAR_OFFERS";
    private final static String VAR_WISHLIST = "VAR_WISHLIST";
    private final static String VAR_SALES_HISTORIES = "VAR_SALES_HISTORIES";

    // To detect if customer is edited
    boolean CustomerIsEdited = false;

    // -------------------------------------------------------------------------------------------
    //                                      Lifecycle
    // -------------------------------------------------------------------------------------------

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(this).inflate(R.layout.activity_customer, null);
        setContentView(view);
        // registration
        bus.register(this);
        injectViews();

        updateDesign();
        initPresenters();
        checkSavedInstance(savedInstanceState);
        getCustomerInfo();
        createPager();
        // report first view
        bus.post(new CustomerInfoTabEvent());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (customerDetails != null)
            outState.putParcelable(VAR_CUSTOMER_DETAILS, Parcels.wrap(customerDetails));
        if (recommendation != null)
            outState.putParcelable(VAR_RECOMMENDATION, Parcels.wrap(recommendation));
        if (offers != null)
            outState.putParcelable(VAR_OFFERS, Parcels.wrap(offers));
        if (wishlist != null)
            outState.putParcelable(VAR_WISHLIST, Parcels.wrap(wishlist));
        if (salesHistories != null)
            outState.putParcelable(VAR_SALES_HISTORIES, Parcels.wrap(salesHistories));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.RESULT_CUSTOMER_SEARCH_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                bus.post(new CustomerContextFoundEvent());
            }
        }
    }

    // -------------------------------------------------------------------------------------------
    //                                      Override(s)
    // -------------------------------------------------------------------------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.customer_menu, menu);
        if (ConfigHelper.getInstance().isFeatureActivated(EFeature.SCAN))
            menu.findItem(R.id.action_scan).setVisible(false);
        if (ConfigHelper.getInstance().isFeatureActivated(EFeature.BASKET))
            menu.findItem(R.id.action_basket).setVisible(false);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.action_scan:
                intent = new Intent(this, BarCodeScannerActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_catalog:
                intent = new Intent(this, CatalogueActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_basket:
                intent = new Intent(this, BasketActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_no_anim);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    private void updateDesign() {
        // show back button
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ColorUtils.setProgressBarColor(progressBar, ColorUtils.FeatureColor.PRIMARY_LIGHT);
        ColorUtils.setBackgroundColor(tabs, ColorUtils.FeatureColor.PRIMARY_DARK);
    }

    private void initPresenters() {
        getCustomerInfoPresenter.initialize();
        getSalesHistoryPresenter.initialize();
        getCustomerInfoPresenter.setView(this);
        getSalesHistoryPresenter.setView(this);
    }

    private void getCustomerInfo() {
        if (getIntent() != null && getIntent().hasExtra(Constants.EXTRA_CUSTOMER_PREVIEW))
            customerPreview = Parcels.unwrap(getIntent().getParcelableExtra(Constants.EXTRA_CUSTOMER_PREVIEW));
        if (getSupportActionBar() != null && customerPreview != null)
            getSupportActionBar().setTitle(customerPreview.getFormatName());
        if (customerPreview != null) {
            getCustomerInfoRequest();
            getSalesHistoryRequest();
        }
    }

    public void getCustomerInfoRequest() {
        if (customerDetails == null || recommendation == null || offers == null || wishlist == null)
            getCustomerInfoPresenter.getCustomerInfoFromEAN(customerPreview.getEAN());
    }

    public void getSalesHistoryRequest() {
        if (customerPreview != null) {
            if (salesHistories == null)
                getSalesHistoryPresenter.getSalesHistoryFromEAN(customerPreview.getEAN());
        }
    }

    private void checkSavedInstance(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(VAR_CUSTOMER_DETAILS))
                customerDetails = Parcels.unwrap(savedInstanceState.getParcelable(VAR_CUSTOMER_DETAILS));
            if (savedInstanceState.containsKey(VAR_RECOMMENDATION))
                recommendation = Parcels.unwrap(savedInstanceState.getParcelable(VAR_RECOMMENDATION));
            if (savedInstanceState.containsKey(VAR_OFFERS))
                offers = Parcels.unwrap(savedInstanceState.getParcelable(VAR_OFFERS));
            if (savedInstanceState.containsKey(VAR_WISHLIST))
                wishlist = Parcels.unwrap(savedInstanceState.getParcelable(VAR_WISHLIST));
            if (savedInstanceState.containsKey(VAR_SALES_HISTORIES))
                salesHistories = Parcels.unwrap(savedInstanceState.getParcelable(VAR_SALES_HISTORIES));
        }
    }

    public void showPagerFromPosition(int position) {
        if (viewPager != null)
            viewPager.setCurrentItem(position);
    }

    private void createPager() {
        String[] titles = {
                getResources().getString(R.string.customer_info),
                getResources().getString(R.string.playlist),
                getResources().getString(R.string.wishlist),
                getResources().getString(R.string.offer),
                getResources().getString(R.string.purchase),
        };
        adapter = new CustomerSlidingViewPagerAdapter(getSupportFragmentManager(), titles);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);

        tabs.setDistributeEvenly(true);
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return Color.parseColor(ColorUtils.FeatureColor.getColorHex(ColorUtils.FeatureColor.PRIMARY_LIGHT));
            }
        });
        tabs.setViewPager(viewPager);
    }

    private void showMenu() {
        if (menu != null) {
            if (menu.findItem(R.id.action_scan) != null)
                menu.findItem(R.id.action_scan).setVisible(true);
            if (menu.findItem(R.id.action_catalog) != null)
                menu.findItem(R.id.action_catalog).setVisible(true);
            if (menu.findItem(R.id.action_basket) != null)
                menu.findItem(R.id.action_basket).setVisible(true);
        }
    }

    private void hideMenu() {
        if (menu != null) {
            if (menu.findItem(R.id.action_scan) != null)
                menu.findItem(R.id.action_scan).setVisible(false);
            if (menu.findItem(R.id.action_catalog) != null)
                menu.findItem(R.id.action_catalog).setVisible(false);
            if (menu.findItem(R.id.action_basket) != null)
                menu.findItem(R.id.action_basket).setVisible(false);
        }
    }

    // -------------------------------------------------------------------------------------------
    //                                       Event(s)
    // -------------------------------------------------------------------------------------------

    @Subscribe
    public void onCustomerEditEvent(CustomerEditEvent event) {
        this.customerDetails = null;
        this.customerPreview = null;
        CustomerIsEdited = true;
        getCustomerInfo();
    }

    // -------------------------------------------------------------------------------------------
    //                                      View Impl
    // -------------------------------------------------------------------------------------------

    @Override
    public void showLoading() {
        loadingPanel.setVisibility(View.VISIBLE);
        hideMenu();
    }

    @Override
    public void hideLoading() {
        loadingPanel.setVisibility(View.GONE);
        showMenu();
    }

    @Override
    public void onCustomerInfoSuccess(String cid, CustomerDetails PRCustomerDetails, Recommendation PRRecommendation, List<Offer> off, List<Product> wishlist) {
        if (CustomerIsEdited) {
            this.customerPreview = new CustomerPreview(cid, PRCustomerDetails);
            if (getSupportActionBar() != null)
                getSupportActionBar().setTitle(customerPreview.getFormatName());
        }
        this.customerDetails = PRCustomerDetails;
        this.recommendation = PRRecommendation;
        this.wishlist = wishlist;
        this.offers = off;
        bus.post(new GetCustomerInfoEvent());
    }

    @Override
    public void onSalesHistorySuccess(Integer cnt, List<SalesHistory> salesHistories) {
        this.salesHistories = salesHistories;
        bus.post(new GetSalesHistoryEvent());
    }

    @Override
    public void onSalesHistoryError() {
        salesHistoryError = true;
        bus.post(new GetSalesHistoryEvent());
    }

    @Override
    public void onError() {
        super.onError();
        finish();
    }

    @Override
    public void displayPopUp(String message) {
        DialogUtils.showDialog(CustomerActivity.this, getString(R.string.warning), message);
    }

    // -------------------------------------------------------------------------------------------
    //                                      Listeners
    // -------------------------------------------------------------------------------------------


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                bus.post(new CustomerInfoTabEvent());
                break;
            case 1:
                bus.post(new PlaylistTabEvent());
                break;
            case 2:
                bus.post(new WishlistTabEvent());
                break;
            case 3:
                bus.post(new OfferTabEvent());
                break;
            case 4:
                bus.post(new HistoryTabEvent());
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
