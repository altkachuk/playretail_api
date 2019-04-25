package ninja.cyplay.com.playretail_api.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import ninja.cyplay.com.playretail_api.model.business.Filter;
import ninja.cyplay.com.playretail_api.model.business.Category;
import ninja.cyplay.com.playretail_api.model.business.Product;
import ninja.cyplay.com.apilibrary.models.business.EScanFilters;
import ninja.cyplay.com.playretail_api.model.business.Recommendation;
import ninja.cyplay.com.playretail_api.model.business.Segment;
import ninja.cyplay.com.playretail_api.model.singleton.CustomerContext;
import ninja.cyplay.com.playretail_api.utils.ColorUtils;
import ninja.cyplay.com.apilibrary.utils.Constants;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.model.business.EFeature;
import ninja.cyplay.com.playretail_api.ui.activity.BarCodeScannerActivity;
import ninja.cyplay.com.playretail_api.ui.activity.CatalogueFilterActivity;
import ninja.cyplay.com.playretail_api.ui.activity.ProductActivity;
import ninja.cyplay.com.playretail_api.ui.adapter.ProductRecyclerAdapter;
import ninja.cyplay.com.playretail_api.ui.adapter.TopSalesProductRecyclerAdapter;
import ninja.cyplay.com.playretail_api.ui.custom.WrapContentLinearLayoutManager;
import ninja.cyplay.com.playretail_api.ui.listener.RecyclerItemClickListener;
import ninja.cyplay.com.playretail_api.utils.ConfigHelper;

/**
 * Created by damien on 04/05/15.
 */
public class CatalogueProductsFragment extends OpinionBaseFragment {

    @Inject
    CustomerContext customerContext;

    @InjectView(R.id.top_sales_product_recycler_view)
    RecyclerView topSalesRecyclerView;

    @InjectView(R.id.catalogue_products)
    RecyclerView recyclerView;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @InjectView(R.id.filter_fab)
    FloatingActionButton filter_fab;

    private Category PRCategory;

    private ProductRecyclerAdapter          adapter;
    private TopSalesProductRecyclerAdapter  topSalesProductAdapter;

    private List<Product> products;
    private List<Product> topProducts;
    private List<Product> topRecoPRProducts;
    private List<Filter> PRFilters;

    // -------------------------------------------------------------------------------------------
    //                                      Lifecycle
    // -------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_products_catalogue, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ProductRecyclerAdapter(getActivity(), this.products);
        adapter.setShowProductCategory(false);
        topSalesProductAdapter = new TopSalesProductRecyclerAdapter(getActivity() , topProducts);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateDesign();
        initToolbar();
        initFilterButton();
        initProductRecycler();
        initTopSalesRecycler();
        if (PRFilters != null && PRFilters.size() > 0)
            filter_fab.setVisibility(View.VISIBLE);
        else {
            // Prevent anchor from changing the view visibility
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) filter_fab.getLayoutParams();
            layoutParams.setAnchorId(View.NO_ID);
            filter_fab.setLayoutParams(layoutParams);
            // Hide the button
            filter_fab.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateTitle();
        if (adapter != null)
            adapter.notifyDataSetChanged();
    }

    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    private void initToolbar() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        toolbar.setNavigationIcon(R.drawable.ic_home_white_24dp);
        activity.setSupportActionBar(toolbar);
        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void updateDesign() {
        int primary_dark_color = Color.parseColor(ColorUtils.FeatureColor.getColorHex(ColorUtils.FeatureColor.PRIMARY_DARK));
        collapsingToolbarLayout.setBackgroundColor(primary_dark_color);
        collapsingToolbarLayout.setContentScrimColor(primary_dark_color);
    }


    private void updateTitle() {
        if (collapsingToolbarLayout != null) {
            if (customerContext != null && customerContext.isCustomerIdentified())
                collapsingToolbarLayout.setTitle(customerContext.getCustomer().getFormatName());
            else if (PRCategory != null && PRCategory.getCat_lab() != null && PRCategory.getCat_lab().length() > 0)
                collapsingToolbarLayout.setTitle(PRCategory.getCat_lab());
        }
    }

    public void setPRCategory(Category PRCategory) {
        this.PRCategory = PRCategory;
        updateTitle();
    }

    public void loadProducts(List<Product> PRProducts) {
        this.products = PRProducts;
        if (adapter != null) {
            adapter.setProducts(this.products);
            adapter.notifyDataSetChanged();
        }
    }

    public void loadTopSales(List<Product> topPRProducts) {
        this.topProducts = topPRProducts;
        if (topSalesRecyclerView != null && topPRProducts != null && topPRProducts.size() > 0) {
            topSalesRecyclerView.setVisibility(View.VISIBLE);
            topSalesProductAdapter.setTopSalesPRProducts(this.topProducts);
            topSalesProductAdapter.notifyDataSetChanged();
        }
    }

    public void loadTopRecommendation(Recommendation PRRecommendation) {
        List<Product> reco = loadRecoProductsByCategory(PRRecommendation, this.PRCategory.getCat_code());
        this.topRecoPRProducts = reco;
        if (topSalesRecyclerView != null && topProducts != null && topProducts.size() > 0) {
            topSalesRecyclerView.setVisibility(View.VISIBLE);
            topSalesProductAdapter.addTopRecommendedProducts(this.topRecoPRProducts);
            topSalesProductAdapter.notifyDataSetChanged();
        }
    }


    public void loadFilters(List<Filter> PRFilters) {
        this.PRFilters = PRFilters;
    }

    private void initFilterButton() {
        filter_fab.setClickable(true);
        filter_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CatalogueFilterActivity.class);
                intent.putExtra(Constants.EXTRA_SEARCH_FILTER, Parcels.wrap(PRFilters));
                getActivity().startActivityForResult(intent, Constants.RESULT_SELECT_FILTERS_ACTIVITY);
            }
        });
    }

    private void initProductRecycler() {
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);
    }

    private void initTopSalesRecycler() {
        RecyclerView.LayoutManager layoutManager = new WrapContentLinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
//         use this setting to improve performance if you know that changes
//         in content do not change the layout size of the RecyclerView
        topSalesRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        topSalesRecyclerView.setLayoutManager(layoutManager);

        topSalesRecyclerView.setAdapter(topSalesProductAdapter);
        topSalesRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new TopProductItemClick()));
    }

    // Constant to specify the number of product to show per category
    final int PRODUCT_NUMBER_PER_CATEGORY = 1;

    /**
     * try to load a PRODUCT_NUMBER_PER_CATEGORY products from every segment (category)
     */
    private List<Product> loadRecoProductsByCategories(Recommendation reco) {

        List<Product> recolist = new ArrayList<Product>();

        if ( reco != null && reco.rl != null && reco.segments != null) {
            for (int i = 0; i < reco.segments.size(); i++) {
                Segment segment = reco.segments.get(i);
                int found_products = 0;
                int j = 0;
                while (found_products< PRODUCT_NUMBER_PER_CATEGORY &  j < reco.rl.size() ){
                    Product current__product = reco.rl.get(j);
                    if (current__product.getCat() != null && segment.seg_code.toUpperCase().equals(current__product.getCat().toUpperCase())) {
                        recolist.add(current__product);
                        found_products++;
                    }
                    j++;
                }
            }
            return  recolist;

        }
        return null;
    }
    private List<Product> loadRecoProductsByCategory(Recommendation reco, String category) {
        List<Product> recolist = new ArrayList<Product>();
                int found_products = 0;
                int j = 0;
                while (found_products< PRODUCT_NUMBER_PER_CATEGORY &  j < reco.rl.size() ){
                    Product current__product = reco.rl.get(j);
                    if (current__product.getCat() != null && category.toUpperCase().equals(current__product.getCat().toUpperCase())) {
                        recolist.add(current__product);
                        found_products++;
                    }
                    j++;
                }
            return  recolist;
    }

    // -------------------------------------------------------------------------------------------
    //                                      View Impl
    // -------------------------------------------------------------------------------------------

    @Override
    public void onLikeSuccess() {
        if (adapter != null)
            adapter.notifyDataSetChanged();
    }

    @Override
    public void onDisLikeSuccess() {
        if (adapter != null)
            adapter.notifyDataSetChanged();
    }

    // -------------------------------------------------------------------------------------------
    //                                      Override(s)
    // -------------------------------------------------------------------------------------------

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Auto-generated method stub
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.catalogue_menu, menu);
        if (ConfigHelper.getInstance().isFeatureActivated(EFeature.SCAN))
            menu.findItem(R.id.action_scan).setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.action_scan:
                Intent intent = new Intent(getActivity(), BarCodeScannerActivity.class);
                intent.putExtra(Constants.EXTRA_SCAN_FILTER, EScanFilters.SCAN_PROD);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // -------------------------------------------------------------------------------------------
    //                                      Action(s)
    // -------------------------------------------------------------------------------------------

    private class TopProductItemClick implements RecyclerItemClickListener.OnItemClickListener {

        @Override
        public void onItemClick(View view, int position) {
            // TODO Handle item click
            Intent intent = new Intent(getActivity(), ProductActivity.class);
            intent.putExtra(Constants.EXTRA_PRODUCT, Parcels.wrap(topProducts.get(position)));
            startActivity(intent);
        }
    }

}
