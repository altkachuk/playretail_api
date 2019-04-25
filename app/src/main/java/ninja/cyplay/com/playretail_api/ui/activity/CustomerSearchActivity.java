package ninja.cyplay.com.playretail_api.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.squareup.otto.Bus;

import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnTextChanged;
import butterknife.Optional;
import ninja.cyplay.com.playretail_api.model.business.CustomerPreview;
import ninja.cyplay.com.apilibrary.models.business.EScanFilters;
import ninja.cyplay.com.playretail_api.model.singleton.SellerContext;
import ninja.cyplay.com.playretail_api.utils.ColorUtils;
import ninja.cyplay.com.apilibrary.utils.Constants;
import ninja.cyplay.com.apilibrary.utils.FragmentHelper;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.app.BaseActivity;
import ninja.cyplay.com.playretail_api.model.business.EFeature;
import ninja.cyplay.com.playretail_api.ui.fragment.CustomerCompletionFragment;
import ninja.cyplay.com.playretail_api.ui.fragment.CustomerSearchPreviewFragment;
import ninja.cyplay.com.playretail_api.ui.presenter.CustomerCompletionPresenter;
import ninja.cyplay.com.playretail_api.ui.presenter.CustomerSearchPresenter;
import ninja.cyplay.com.playretail_api.ui.view.CustomerCompletionView;
import ninja.cyplay.com.playretail_api.ui.view.CustomerSearchView;
import ninja.cyplay.com.playretail_api.utils.ConfigHelper;

/**
 * Created by damien on 05/05/15.
 */
public class CustomerSearchActivity extends BaseActivity implements CustomerCompletionView, CustomerSearchView {

    @Inject
    SellerContext sellerContext;

    @Inject
    Bus bus;

    @Inject
    CustomerCompletionPresenter customerCompletionPresenter;

    @Inject
    CustomerSearchPresenter customerSearchPresenter;

    @Optional
    @InjectView(R.id.progress_bar)
    ProgressBar wait;

    // Fragments
    private CustomerCompletionFragment customerCompletionFragment;
    private CustomerSearchPreviewFragment customerSearchPreviewFragment;

    // -------------------------------------------------------------------------------------------
    //                                      Lifecycle
    // -------------------------------------------------------------------------------------------

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(this).inflate(R.layout.activity_customer_search, null);
        setContentView(view);
        injectViews();
        bus.register(this);

        createFragments();
        updateDesign();
        initPresenter();
        loadCustomersFromSellerHistory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                if (getIntent() != null && getIntent().hasExtra(Constants.EXTRA_SEARCH_CUSTOMER_CONTEXT)) {
                    Intent returnIntent = new Intent();
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }
            }
        }
    }


    // -------------------------------------------------------------------------------------------
    //                                      Override(s)
    // -------------------------------------------------------------------------------------------


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.catalogue_menu, menu);
        if (ConfigHelper.getInstance().isFeatureActivated(EFeature.SCAN))
            menu.findItem(R.id.action_scan).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_scan) {
            Intent intent = new Intent(this, BarCodeScannerActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(Constants.EXTRA_SCAN_FILTER, EScanFilters.SCAN_CUSTOMER);
            if (getIntent() != null && getIntent().hasExtra(Constants.EXTRA_SEARCH_CUSTOMER_CONTEXT))
                intent.putExtra(Constants.EXTRA_SEARCH_CUSTOMER_CONTEXT,
                        getIntent().getBooleanExtra(Constants.EXTRA_SEARCH_CUSTOMER_CONTEXT, false));
            startActivityForResult(intent, 1);
        }
        return super.onOptionsItemSelected(item);
    }

    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    private void createFragments() {
        customerCompletionFragment = new CustomerCompletionFragment();
        customerSearchPreviewFragment = new CustomerSearchPreviewFragment();
    }

    private void initPresenter() {
        customerCompletionPresenter.initialize();
        customerSearchPresenter.initialize();
        customerCompletionPresenter.setView(this);
        customerSearchPresenter.setView(this);
    }

    private void updateDesign() {
        // show back button
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ColorUtils.setProgressBarColor(wait, ColorUtils.FeatureColor.PRIMARY_LIGHT);
    }

    private void showFragment(Fragment fragment) {
        try {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.container, fragment);
            transaction.commitAllowingStateLoss();
            manager.executePendingTransactions();
        }
        catch (Exception e) { /* orientation changes fixed */ }
    }

    public void searchCustomerFromCompleteName(String customerName) {
        customerSearchPresenter.searchCustomer(customerName);
    }
    private void loadCustomersFromSellerHistory() {
        onCompletionSuccess(sellerContext.getCustomer_historyNames());
    }

    // -------------------------------------------------------------------------------------------
    //                                      Action(s)
    // -------------------------------------------------------------------------------------------

    @Optional
    @OnTextChanged(R.id.customer_search_name)
    void onTextChanged(CharSequence text) {
        if (text != null && text.length() >= 3) {
            customerCompletionPresenter.completionCustomer(text.toString());
        }
    }

//    @Optional
//    @OnClick(R.id.button_scan)
//    void scanPressed() {
//        Intent intent = new Intent(this, ScannerActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.putExtra(Constants.EXTRA_SCAN_FILTER, EScanFilters.SCAN_CUSTOMER);
//        if (getIntent() != null && getIntent().hasExtra(Constants.EXTRA_SEARCH_CUSTOMER_CONTEXT)) {
//            intent.putExtra(Constants.EXTRA_SEARCH_CUSTOMER_CONTEXT, getIntent().getBooleanExtra(Constants.EXTRA_SEARCH_CUSTOMER_CONTEXT, false));
//        }
//        startActivityForResult(intent, 1);
//
//    }

    // -------------------------------------------------------------------------------------------
    //                                      View Impl
    // -------------------------------------------------------------------------------------------

    @Override
    public void showLoading() {
        if (wait != null)
            wait.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        if (wait != null)
            wait.setVisibility(View.GONE);
    }


    @Override
    public void onSearchSuccess(List<CustomerPreview> customers) {
        if (!FragmentHelper.isFragmentUIActive(customerSearchPreviewFragment))
            showFragment(customerSearchPreviewFragment);
        customerSearchPreviewFragment.loadCustomers(customers);
    }

    @Override
    public void onCompletionSuccess(List<String> customers) {
        if (!FragmentHelper.isFragmentUIActive(customerCompletionFragment))
            showFragment(customerCompletionFragment);
        customerCompletionFragment.loadCustomers(customers);
    }


    @Override
    public void displayPopUp(String message) {

    }
}
