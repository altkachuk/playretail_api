package ninja.cyplay.com.playretail_api.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.squareup.otto.Bus;

import org.parceler.Parcels;

import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.Optional;
import ninja.cyplay.com.playretail_api.model.business.Product;
import ninja.cyplay.com.apilibrary.models.business.SugarORM.PageView;
import ninja.cyplay.com.playretail_api.model.events.CustomerContextFoundEvent;
import ninja.cyplay.com.playretail_api.model.singleton.CustomerContext;
import ninja.cyplay.com.playretail_api.model.singleton.PagesViews;
import ninja.cyplay.com.playretail_api.utils.ColorUtils;
import ninja.cyplay.com.apilibrary.utils.Constants;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.app.BaseActivity;
import ninja.cyplay.com.playretail_api.ui.adapter.ProductViewPagerAdapter;
import ninja.cyplay.com.playretail_api.ui.component.SlidingTabLayout;

/**
 * Created by damien on 25/06/15.
 */
public class ProductActivity extends BaseActivity {

    @Inject
    public Bus bus;

    @Inject
    PagesViews pagesViews;

    @Inject
    CustomerContext customerContext;

    @Optional
    @InjectView(R.id.tabLayout)
    SlidingTabLayout tabLayout;

    @Optional
    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @Optional
    @InjectView(R.id.view_pager)
    ViewPager viewPager;

    public Product product;

    public List<Product> relatedProducts;

    private ProductViewPagerAdapter adapter;

    // -------------------------------------------------------------------------------------------
    //                                      Lifecycle
    // -------------------------------------------------------------------------------------------

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(this).inflate(R.layout.activity_product, null);
        setContentView(view);

        // registration
        bus.register(this);
        injectViews();

        updateDesign();
        initToolbar();
        // Get product from Extra
        getProduct();
        createViewPager();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (product != null)
            setSupportActionBarTitleForProductName(product.getPn());
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
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    private void updateDesign() {
        // show back button
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ColorUtils.setBackgroundColor(toolbar, ColorUtils.FeatureColor.PRIMARY_DARK);
        ColorUtils.setBackgroundColor(tabLayout, ColorUtils.FeatureColor.PRIMARY_DARK);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void createViewPager() {
        adapter = new ProductViewPagerAdapter(getSupportFragmentManager(), this, product);
        // to already create the 3rd fragment (related products will be loaded in background)
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);

        tabLayout.setDistributeEvenly(true);
        tabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return Color.parseColor(ColorUtils.FeatureColor.getColorHex(ColorUtils.FeatureColor.PRIMARY_LIGHT));
            }
        });
        tabLayout.setViewPager(viewPager);
    }

    public void setSupportActionBarTitleForProductName(String title) {
        if (getSupportActionBar() != null) {
            if (customerContext != null && customerContext.isCustomerIdentified())
                getSupportActionBar().setTitle(customerContext.getCustomer().getFormatName());
            else if (title != null && title.length() > 0)
                getSupportActionBar().setTitle(title);
        }
    }

    private void getProduct() {
        if (getIntent() != null && getIntent().hasExtra(Constants.EXTRA_PRODUCT))
            product = Parcels.unwrap(getIntent().getParcelableExtra(Constants.EXTRA_PRODUCT));
    }

    // -------------------------------------------------------------------------------------------
    //                                      Override(s)
    // -------------------------------------------------------------------------------------------

    @Override
    public void savePageView() {
        List<String> idsList;
        if (product != null && relatedProducts != null) {
            Function<Product, String> transform = new Function<Product, String>() {
                @Override
                public String apply(Product from) {
                    if (from.getSkid() != null)
                        return String.valueOf(from.getSkid());
                    return "";
                }
            };
            idsList = Lists.transform(relatedProducts, transform);
            String skid = product.getSkid() != null ? String.valueOf(product.getSkid()) : "";
            PageView pg =  pagesViews.createPageView(className);
            pg.setSkid(skid);
            pg.setPrec(idsList);
            pg.serializeFields();
            pagesViews.addPageView(pg);
        }
    }

}
