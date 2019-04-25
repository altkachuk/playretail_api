package ninja.cyplay.com.playretail_api.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import ninja.cyplay.com.playretail_api.model.business.Product;
import ninja.cyplay.com.playretail_api.model.events.GetCustomerInfoEvent;
import ninja.cyplay.com.playretail_api.model.events.WishlistTabEvent;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.ui.activity.CustomerActivity;
import ninja.cyplay.com.playretail_api.ui.adapter.ProductRecyclerAdapter;

/**
 * Created by damien on 07/05/15.
 */
public class CustomerWishlistFragment extends OpinionBaseFragment {

    @Inject
    public Bus bus;

    @InjectView(R.id.recycler_view)
    RecyclerView recyclerView;

    @InjectView(R.id.empty_string_text_view)
    TextView emptyString;

    private ProductRecyclerAdapter      adapter;
    private RecyclerView.LayoutManager  layoutManager;

    private List<Product> PRProducts;

    // -------------------------------------------------------------------------------------------
    //                                      Lifecycle
    // -------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_customer_wishlist, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bus.register(this);
        adapter = new ProductRecyclerAdapter(getActivity(), PRProducts);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecycler();
        loadWishList();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null)
            adapter.notifyDataSetChanged();
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
    }

    private void loadWishList() {
        List<Product> wishlist = ((CustomerActivity)getActivity()).wishlist;
        if (wishlist != null && wishlist.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            emptyString.setVisibility(View.GONE);
            loadWishListProduct(wishlist);
        }
        else {
            recyclerView.setVisibility(View.GONE);
            emptyString.setVisibility(View.VISIBLE);
        }

    }

    public void loadWishListProduct(List<Product> PRProducts) {
        this.PRProducts = PRProducts;
        if (adapter != null)
            adapter.setProducts(this.PRProducts);
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
    //                                       Event(s)
    // -------------------------------------------------------------------------------------------

    @Subscribe
    public void getCustomerInfoEvent(GetCustomerInfoEvent event) {
        loadWishList();
    }

    @Subscribe
    public void onTabSelect(WishlistTabEvent customerInfoTabEvent) {
        pagesViews.addPageView(className);
    }

}
