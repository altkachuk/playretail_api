package ninja.cyplay.com.playretail_api.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.parceler.Parcels;

import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import ninja.cyplay.com.playretail_api.model.business.Product;
import ninja.cyplay.com.apilibrary.utils.Constants;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.app.BaseFragment;
import ninja.cyplay.com.playretail_api.ui.activity.ProductActivity;
import ninja.cyplay.com.playretail_api.ui.adapter.ProductRecyclerAdapter;
import ninja.cyplay.com.playretail_api.ui.custom.FullScreenView;
import ninja.cyplay.com.playretail_api.ui.listener.RecyclerItemClickListener;
import ninja.cyplay.com.playretail_api.ui.presenter.GetProductsPresenter;
import ninja.cyplay.com.playretail_api.ui.view.ProductsRelatedView;

/**
 * Created by damien on 05/05/15.
 */
public class ProductsRelatedFragment extends BaseFragment implements ProductsRelatedView {

    @Inject
    GetProductsPresenter getProductsPresenter;

    @InjectView(R.id.full_screen_view)
    FullScreenView fullScreenView;

    @InjectView(R.id.recycler_view)
    RecyclerView recyclerView;

    private ProductRecyclerAdapter adapter;
    private RecyclerView.LayoutManager  layoutManager;

    private Product product;
    private List<Product> productsRelated;

    // -------------------------------------------------------------------------------------------
    //                                      Lifecycle
    // -------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_related, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        product = ((ProductActivity)getActivity()).product;
        setRetainInstance(true);
        getProductsPresenter.initialize();
        getProductsPresenter.setView(this);
        // request only if the product has related product
        if (product != null && product.rp != null && product.rp.size() > 0)
            getProductsPresenter.getRelatedProducts(product.rp);
        initAdapter();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecycler();
        setReloadAction();
    }

    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    private void initRecycler() {
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new ProductItemClick()));
    }

    private void initAdapter() {
        adapter = new ProductRecyclerAdapter(getActivity(), productsRelated);
        adapter.setDisableRightSwipe(true);
        adapter.setDisableLeftSwipe(true);
    }

    private void setReloadAction() {
        if (fullScreenView!= null && fullScreenView.getReloadButton() != null) {
            fullScreenView.getReloadButton().setClickable(true);
            fullScreenView.getReloadButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fullScreenView.hideReloadButton();
                    if (product != null && product.rp != null && product.rp.size() > 0)
                        getProductsPresenter.getRelatedProducts(product.rp);
                }
            });
        }
    }

    // -------------------------------------------------------------------------------------------
    //                                      View Impl
    // -------------------------------------------------------------------------------------------

    @Override
    public void showLoading() {
        if (fullScreenView!= null)
            fullScreenView.showLoading();
    }

    @Override
    public void hideLoading() {
        if (fullScreenView != null)
            fullScreenView.hideLoading();
    }

    @Override
    public void onProductsRelatedSuccess(List<Product> productsRelated) {
        ((ProductActivity)getActivity()).relatedProducts = productsRelated;
        //TODO Move this pageView on event "click" tab bar
        ((ProductActivity)getActivity()).savePageView();
        this.productsRelated = productsRelated;
        adapter.setProducts(this.productsRelated);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onError() {
        if (fullScreenView != null) {
            fullScreenView.showError();
            fullScreenView.showReloadButton();
        }
    }

    @Override
    public void displayPopUp(String message) {
        // DialogUtils.showDialog(getActivity(), getString(R.string.warning), message);
    }

    // -------------------------------------------------------------------------------------------
    //                                      Action(s)
    // -------------------------------------------------------------------------------------------

    private class ProductItemClick implements RecyclerItemClickListener.OnItemClickListener {

        @Override
        public void onItemClick(View view, int position) {
            // TODO Handle item click
            Intent intent = new Intent(getActivity(), ProductActivity.class);
            intent.putExtra(Constants.EXTRA_PRODUCT, Parcels.wrap(productsRelated.get(position)));
            startActivity(intent);
        }
    }

}
