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

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import ninja.cyplay.com.playretail_api.model.business.Product;
import ninja.cyplay.com.playretail_api.model.business.Recommendation;
import ninja.cyplay.com.playretail_api.model.events.GetCustomerInfoEvent;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.ui.activity.CustomerActivity;
import ninja.cyplay.com.playretail_api.ui.adapter.PlaylistProductRecyclerAdapter;

/**
 * Created by damien on 07/05/15.
 */
public class CustomerPlaylistFragment extends OpinionBaseFragment {

    @Inject
    public Bus bus;

    @InjectView(R.id.recycler_view)
    RecyclerView recyclerView;

    @InjectView(R.id.empty_string_text_view)
    TextView emptyString;

    private PlaylistProductRecyclerAdapter      adapter;

    private RecyclerView.LayoutManager  layoutManager;

    private Recommendation PRRecommendation;

    List<Product> PRProducts = new ArrayList<Product>();

    // Constant to specify the max number of product in the playlist & restocking
    final int MAX_RECO_PRODUCT = 10;
    final int MAX_RESTO_PRODUCT = 10;

    // -------------------------------------------------------------------------------------------
    //                                      Lifecycle
    // -------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_customer_playlist, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bus.register(this);
        adapter = new PlaylistProductRecyclerAdapter(getActivity(), PRProducts);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.PRRecommendation = ((CustomerActivity)getActivity()).recommendation;
        initRecycler();
        this.PRProducts.clear();
        loadRestocking();
        loadRecommendation();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
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

    private void loadRecommendation() {
        if (PRRecommendation != null && PRRecommendation.rl != null)
            adapter.loadProducts(PRRecommendation.rl, MAX_RECO_PRODUCT, Product.PRODUCT_TYPE.PRODUCT_TYPE_RECO);
    }

    private void loadRestocking() {
        if (PRRecommendation != null && PRRecommendation.rsl != null) {
            adapter.loadProducts(PRRecommendation.rsl, MAX_RESTO_PRODUCT, Product.PRODUCT_TYPE.PRODUCT_TYPE_RECURING);
        }
        // Show error message if empty
        if (PRRecommendation != null && PRRecommendation.rl != null && PRRecommendation.rl.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            emptyString.setVisibility(View.GONE);
        }
        else {
            recyclerView.setVisibility(View.GONE);
            emptyString.setVisibility(View.VISIBLE);
        }
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
        this.PRRecommendation = ((CustomerActivity)getActivity()).recommendation;
        this.PRProducts.clear();
        loadRestocking();
        loadRecommendation();
    }

}
