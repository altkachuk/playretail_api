package ninja.cyplay.com.playretail_api.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;

import com.squareup.otto.Bus;

import org.parceler.Parcels;

import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.Optional;
import ninja.cyplay.com.playretail_api.model.business.Filter;
import ninja.cyplay.com.playretail_api.model.business.Offer;
import ninja.cyplay.com.playretail_api.model.business.Category;
import ninja.cyplay.com.playretail_api.model.business.CustomerDetails;
import ninja.cyplay.com.playretail_api.model.business.FilterCheck;
import ninja.cyplay.com.playretail_api.model.business.Product;
import ninja.cyplay.com.playretail_api.model.business.Recommendation;
import ninja.cyplay.com.playretail_api.model.events.CustomerContextFoundEvent;
import ninja.cyplay.com.playretail_api.model.singleton.CustomerContext;
import ninja.cyplay.com.apilibrary.utils.Constants;
import ninja.cyplay.com.apilibrary.utils.DialogUtils;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.app.BaseActivity;
import ninja.cyplay.com.playretail_api.ui.custom.FullScreenView;
import ninja.cyplay.com.playretail_api.ui.fragment.CatalogueCategoriesFragment;
import ninja.cyplay.com.playretail_api.ui.fragment.CatalogueProductsFragment;
import ninja.cyplay.com.playretail_api.ui.presenter.CataloguePresenter;
import ninja.cyplay.com.playretail_api.ui.presenter.GetCustomerInfoPresenter;
import ninja.cyplay.com.playretail_api.ui.presenter.ProductSearchPresenter;
import ninja.cyplay.com.playretail_api.ui.view.CatalogueView;
import ninja.cyplay.com.playretail_api.ui.view.GetCustomerInfoView;
import ninja.cyplay.com.playretail_api.ui.view.ProductSearchView;

/**
 * Created by damien on 04/05/15.
 */
public class CatalogueActivity extends BaseActivity implements CatalogueView, ProductSearchView, GetCustomerInfoView{

    @Inject
    Bus bus;

    @Inject
    public CataloguePresenter cataloguePresenter;

    @Inject
    GetCustomerInfoPresenter getCustomerInfoPresenter;

    @Inject
    public ProductSearchPresenter productSearchPresenter;

    @Inject
    CustomerContext customerContext;

    @Optional
    @InjectView(R.id.full_screen_view)
    FullScreenView fullScreenView;

    // vars
    private boolean                     firstFragment = true;
    public final static String CATALOGUE_ROOT_CATEGORY = "ROOT";

    private Category current__category;

    CatalogueProductsFragment catalogueProductsFragment;

    // -------------------------------------------------------------------------------------------
    //                                      Lifecycle
    // -------------------------------------------------------------------------------------------

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(this).inflate(R.layout.activity_catalogue, null);
        setContentView(view);
        bus.register(this);
        injectViews();
        initFragment();

        getCustomerInfoPresenter.setView(this);
        productSearchPresenter.setView(this);
        cataloguePresenter.setView(this);

        getCustomerInfoPresenter.initialize();
        productSearchPresenter.initialize();
        cataloguePresenter.initialize();

        cataloguePresenter.searchCatalogue(CATALOGUE_ROOT_CATEGORY, getString(R.string.catalogue));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.RESULT_CUSTOMER_SEARCH_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                bus.post(new CustomerContextFoundEvent());
            }
        }
        else {
            if (requestCode == Constants.RESULT_SELECT_FILTERS_ACTIVITY) {
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        List<FilterCheck> chosenFilters = Parcels.unwrap(data.getParcelableExtra(Constants.EXTRA_SEARCH_FILTER_CHECK));
                        if (chosenFilters != null && chosenFilters.size() > 0) {
                            //reload the list with filtred elements
                            cataloguePresenter.filterProducts(current__category.getCat_code(), chosenFilters);
                        }
                    }
                }
            }
        }
    }

    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    private void initFragment() {
        FragmentManager manager = getSupportFragmentManager();
//        // in case of Orientation change
        manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public void searchNextCatalogueLevel(Category PRCategory) {
        this.current__category = PRCategory;
        cataloguePresenter.searchCatalogue(PRCategory.getCat_code(), PRCategory.getCat_lab());
    }

    private void showFragment(Fragment fragment) {
        try {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.container, fragment);
            if (!firstFragment)
                transaction.addToBackStack(null);
            transaction.commit();
            manager.executePendingTransactions();
            firstFragment = false;
        }
        catch (Exception e) { e.printStackTrace(); }
    }

    // -------------------------------------------------------------------------------------------
    //                                        View Impl
    // -------------------------------------------------------------------------------------------

    @Override
    public void showLoading() {
        if (fullScreenView != null)
            fullScreenView.showLoading();
    }

    @Override
    public void hideLoading() {
        if (fullScreenView != null)
            fullScreenView.hideLoading();
    }

    @Override
    public void onCustomerInfoSuccess(String cid, CustomerDetails PRCustomerDetails, Recommendation PRRecommendation, List<Offer> offers, List<Product> wishlist) {
        catalogueProductsFragment.loadTopRecommendation(PRRecommendation);
    }

    @Override
    public void onSearchSuccess(List<Product> PRProducts) {
        catalogueProductsFragment = new CatalogueProductsFragment();
        showFragment(catalogueProductsFragment);
        Category search_result_cat = new Category();
        search_result_cat.setCat_lab("");
        search_result_cat.setCat_code("");
        search_result_cat.setCat_type("");
        catalogueProductsFragment.setPRCategory(search_result_cat);
        catalogueProductsFragment.loadProducts(PRProducts);
    }

    @Override
    public void onProductsSuccesss(Category PRCategory, List<Product> PRProducts, List<Filter> filtres, List<Product> topPRProducts) {
        catalogueProductsFragment = new CatalogueProductsFragment();
        catalogueProductsFragment.setPRCategory(PRCategory);
        catalogueProductsFragment.loadTopSales(topPRProducts);
        catalogueProductsFragment.loadProducts(PRProducts);
        catalogueProductsFragment.loadFilters(filtres);
        showFragment(catalogueProductsFragment);

        // load user recommendeds products to show in the top sales products
        if(customerContext != null && customerContext.isCustomerIdentified()){
            getCustomerInfoPresenter.getCustomerInfoFromEAN(customerContext.getCustomer().getEAN());
        }

    }

    @Override
    public void onCategoriesSuccess(Category PRCategory, List<Category> categories, List<Filter> filtres, List<Product> topPRProducts) {
        CatalogueCategoriesFragment catalogueCategoriesFragment = new CatalogueCategoriesFragment();
        catalogueCategoriesFragment.setPRCategory(PRCategory);
        catalogueCategoriesFragment.loadTopSales(topPRProducts);
        catalogueCategoriesFragment.loadFilters(filtres);
        catalogueCategoriesFragment.loadCategories(categories);
        showFragment(catalogueCategoriesFragment);
    }

    @Override
    public void displayPopUp(String message) {
        DialogUtils.showDialog(CatalogueActivity.this, getString(R.string.warning), message);
    }

}
