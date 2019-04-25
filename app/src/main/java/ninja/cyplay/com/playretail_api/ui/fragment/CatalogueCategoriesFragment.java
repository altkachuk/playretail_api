package ninja.cyplay.com.playretail_api.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import ninja.cyplay.com.playretail_api.model.business.Filter;
import ninja.cyplay.com.playretail_api.model.business.Category;
import ninja.cyplay.com.playretail_api.model.business.Product;
import ninja.cyplay.com.apilibrary.models.business.EScanFilters;
import ninja.cyplay.com.playretail_api.model.singleton.CustomerContext;
import ninja.cyplay.com.playretail_api.utils.ColorUtils;
import ninja.cyplay.com.apilibrary.utils.Constants;
import ninja.cyplay.com.playretail_api.utils.ImageUtils;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.app.BaseFragment;
import ninja.cyplay.com.playretail_api.model.business.EFeature;
import ninja.cyplay.com.playretail_api.ui.activity.BarCodeScannerActivity;
import ninja.cyplay.com.playretail_api.ui.activity.CatalogueActivity;
import ninja.cyplay.com.playretail_api.ui.activity.ProductActivity;
import ninja.cyplay.com.playretail_api.ui.adapter.CategoryRecyclerAdapter;
import ninja.cyplay.com.playretail_api.ui.adapter.TopSalesProductRecyclerAdapter;
import ninja.cyplay.com.playretail_api.ui.component.MarginDecoration;
import ninja.cyplay.com.playretail_api.ui.listener.RecyclerItemClickListener;
import ninja.cyplay.com.playretail_api.utils.ConfigHelper;

/**
 * Created by damien on 04/05/15.
 */
public class CatalogueCategoriesFragment extends BaseFragment {

    @Inject
    CustomerContext customerContext;

    @InjectView(R.id.top_sales_product_recycler_view)
    RecyclerView topSalesRecyclerView;

    @InjectView(R.id.category_recycler_view)
    RecyclerView categoryRecyclerView;

    @InjectView(R.id.parallax_image_container)
    RelativeLayout parallaxContainer;

    @InjectView(R.id.parallax_image)
    ImageView parallax;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @InjectView(R.id.search_fab)
    FloatingActionButton search_fab;

    private Category PRCategory;

    private List<Category> categories;
    private List<Product> topPRProducts;
    private List<Filter> PRFilters;

    private CategoryRecyclerAdapter categoriesAdapter;
    private TopSalesProductRecyclerAdapter topSalesProductAdapter;

    // -------------------------------------------------------------------------------------------
    //                                      Lifecycle
    // -------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_categories_catalogue, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topSalesProductAdapter = new TopSalesProductRecyclerAdapter(getActivity(), topPRProducts);
        categoriesAdapter = new CategoryRecyclerAdapter(getActivity(), categories);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateDesign();
        initToolbar();
        initSearchButton();
        initImageParallax();
        initCategoryRecycler();
        initTopSalesRecycler();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (customerContext != null && customerContext.isCustomerIdentified())
            collapsingToolbarLayout.setTitle(customerContext.getCustomer().getFormatName());
        else if (PRCategory != null && PRCategory.getCat_lab() != null && PRCategory.getCat_lab().length() > 0)
            collapsingToolbarLayout.setTitle(PRCategory.getCat_lab());
    }

    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    private void updateDesign() {
        int primary_dark_color = Color.parseColor(ColorUtils.FeatureColor.getColorHex(ColorUtils.FeatureColor.PRIMARY_DARK));
        collapsingToolbarLayout.setBackgroundColor(primary_dark_color);
        collapsingToolbarLayout.setContentScrimColor(primary_dark_color);
    }

    private void initToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_home_white_24dp);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        if (activity.getSupportActionBar() != null)
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initSearchButton() {
        search_fab.setClickable(true);
        search_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    new MaterialDialog.Builder(getActivity())
                            .title(R.string.search_products)
                            .content(R.string.desc_search_products)
                            .inputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS)
                            .input(R.string.catalogue_search, R.string.no_prefill, new MaterialDialog.InputCallback() {
                                @Override
                                public void onInput(MaterialDialog dialog, CharSequence input) {
                                    if (input != null)
                                        ((CatalogueActivity) getActivity()).productSearchPresenter.searchProduct(input.toString());
                                }
                            }).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initTopSalesRecycler() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
//         use this setting to improve performance if you know that changes
//         in content do not change the layout size of the RecyclerView
        topSalesRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        topSalesRecyclerView.setLayoutManager(layoutManager);

        topSalesRecyclerView.setAdapter(topSalesProductAdapter);
        topSalesRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new ProductItemClick()));
    }

    private void initCategoryRecycler() {
        categoryRecyclerView.addItemDecoration(new MarginDecoration(getActivity(), 5, 5, 5, 0));

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        categoryRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
//        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity());
        categoryRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        categoryRecyclerView.setAdapter(categoriesAdapter);
        categoryRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new CategoryItemClick()));
    }

    private void initImageParallax() {
        if (parallax != null && PRCategory != null && PRCategory.getCat_image() != null) {
            parallaxContainer.setVisibility(View.VISIBLE);
            topSalesRecyclerView.setVisibility(View.GONE);
            Picasso.with(getActivity())
                    .load(ImageUtils.getImageUrl(PRCategory.getCat_image(), "sd"))
                    .placeholder(R.drawable.img_placeholder)
                    .into(parallax);
        }
    }

    public void setPRCategory(Category PRCategory) {
        this.PRCategory = PRCategory;
        initImageParallax();
//        if (category != null && category.getCat_code() != null)
//            ((CatalogueActivity) getActivity()).setSupportActionBarTitleForCategoryTitle(category.getCat_code());
    }

    public void loadTopSales(List<Product> topPRProducts) {
        this.topPRProducts = topPRProducts;
        if (topSalesRecyclerView != null && parallax != null
            && topPRProducts != null && topPRProducts.size() > 0) {
            parallaxContainer.setVisibility(View.GONE);
            topSalesRecyclerView.setVisibility(View.VISIBLE);
            topSalesProductAdapter.setTopSalesPRProducts(this.topPRProducts);
            topSalesProductAdapter.notifyDataSetChanged();
        }
    }

    public void loadFilters(List<Filter> PRFilters) {
        this.PRFilters = PRFilters;
    }

    public void loadCategories(List<Category> categories) {
        this.categories = categories;
        if (categoriesAdapter != null) {
            categoriesAdapter.setCategories(this.categories);
            categoriesAdapter.notifyDataSetChanged();
        }
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

    private class ProductItemClick implements RecyclerItemClickListener.OnItemClickListener {

        @Override
        public void onItemClick(View view, int position) {
            // TODO Handle item click
            Intent intent = new Intent(getActivity(), ProductActivity.class);
            intent.putExtra(Constants.EXTRA_PRODUCT, Parcels.wrap(topPRProducts.get(position)));
            startActivity(intent);
        }
    }

    private class CategoryItemClick implements RecyclerItemClickListener.OnItemClickListener {

        @Override
        public void onItemClick(View view, int position) {
            // TODO Handle item click
            ((CatalogueActivity)getActivity()).searchNextCatalogueLevel(categories.get(position));
        }
    }

}
