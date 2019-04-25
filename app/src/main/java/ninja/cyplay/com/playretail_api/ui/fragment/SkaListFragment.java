package ninja.cyplay.com.playretail_api.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import org.parceler.Parcels;

import javax.inject.Inject;

import butterknife.InjectView;
import ninja.cyplay.com.playretail_api.model.business.Product;
import ninja.cyplay.com.playretail_api.model.business.Sku;
import ninja.cyplay.com.playretail_api.model.events.BasketItemPlusPressedEvent;
import ninja.cyplay.com.apilibrary.utils.Constants;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.ui.activity.SkaListActivity;
import ninja.cyplay.com.playretail_api.ui.adapter.SkaRecyclerAdapter;

/**
 * Created by damien on 09/06/15.
 */
public class SkaListFragment extends BasketBaseFragment {

    @Inject
    Bus bus;

    @InjectView(R.id.recycler_view)
    RecyclerView recyclerView;

    private Product PRProduct;

    private Sku sku;

    private SkaRecyclerAdapter adapter;

    // -------------------------------------------------------------------------------------------
    //                                      Lifecycle
    // -------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ska_list, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bus.register(this);
        if (getActivity().getIntent() != null) {
            if (getActivity().getIntent().hasExtra(Constants.EXTRA_PRODUCT))
                this.PRProduct = Parcels.unwrap(getActivity().getIntent().getParcelableExtra(Constants.EXTRA_PRODUCT));
            if (getActivity().getIntent().hasExtra(Constants.EXTRA_SKU)) {
                this.sku = Parcels.unwrap(getActivity().getIntent().getParcelableExtra(Constants.EXTRA_SKU));
                if (this.sku != null)
                    ((SkaListActivity)getActivity()).setActionBarTitle(getString(R.string.product_model) + ": " + sku.skd);
            }
            if (sku != null)
                adapter = new SkaRecyclerAdapter(getActivity(), sku.ska, sku, PRProduct);
        }
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
    }

    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    private void initRecycler() {
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);
    }

    // -------------------------------------------------------------------------------------------
    //                                       Event(s)
    // -------------------------------------------------------------------------------------------

    @Subscribe
    public void onPlusButton(BasketItemPlusPressedEvent basketItemPlusPressedEvent) {
        if (basketItemPlusPressedEvent != null && basketItemPlusPressedEvent.getCartItem() != null)
            basketPresenter.addOneQuantityItemToContextBasket(basketItemPlusPressedEvent.getCartItem().product,
                    basketItemPlusPressedEvent.getCartItem().sku);
    }

}
