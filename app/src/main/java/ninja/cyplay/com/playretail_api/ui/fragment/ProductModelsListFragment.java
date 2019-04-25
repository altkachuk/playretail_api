package ninja.cyplay.com.playretail_api.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import butterknife.InjectView;
import ninja.cyplay.com.playretail_api.model.business.CartItem;
import ninja.cyplay.com.playretail_api.model.business.Product;
import ninja.cyplay.com.playretail_api.model.events.BasketItemPlusPressedEvent;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.ui.activity.ProductActivity;
import ninja.cyplay.com.playretail_api.ui.adapter.SkusRecyclerAdapter;

/**
 * Created by damien on 05/05/15.
 */
public class ProductModelsListFragment extends BasketBaseFragment {

    @Inject
    Bus bus;

    @InjectView(R.id.recycler_view)
    RecyclerView recyclerView;

    private Product PRProduct;

    private SkusRecyclerAdapter adapter;

    private CartItem lastItemAddedToBasket;

    // -------------------------------------------------------------------------------------------
    //                                      Lifecycle
    // -------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_models_list, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bus.register(this);
        PRProduct = ((ProductActivity)getActivity()).product;
        if (PRProduct != null)
            adapter = new SkusRecyclerAdapter(getActivity(), PRProduct.getSkl(), PRProduct, this);
        setRetainInstance(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (PRProduct != null) {
            initRecycler();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
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

//        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new SkuCellItemClick()));
    }

    // -------------------------------------------------------------------------------------------
    //                                      View Impl
    // -------------------------------------------------------------------------------------------

    @Override
    public void onSyncSuccess() {
        super.onSyncSuccess();
        if (lastItemAddedToBasket != null && lastItemAddedToBasket.sku != null)
            Toast.makeText(getActivity(), lastItemAddedToBasket.sku.skd + " "
                    + getString(R.string.add_to_basket), Toast.LENGTH_SHORT).show();
    }

    // -------------------------------------------------------------------------------------------
    //                                       Event(s)
    // -------------------------------------------------------------------------------------------

    @Subscribe
    public void onAddBasketClicked(BasketItemPlusPressedEvent basketItemPlusPressedEvent) {
        lastItemAddedToBasket = basketItemPlusPressedEvent.getCartItem();
        basketPresenter.addOneQuantityItemToContextBasket(lastItemAddedToBasket.product, lastItemAddedToBasket.sku);
    }

    // -------------------------------------------------------------------------------------------
    //                                      Action(s)
    // -------------------------------------------------------------------------------------------

//    private class SkuCellItemClick implements RecyclerItemClickListener.OnItemClickListener {
//
//        @Override
//        public void onItemClick(View view, int position) {
//            // TODO Handle item click
//            Intent intent = new Intent(getActivity(), SkaListActivity.class);
//            intent.putExtra(Constants.EXTRA_PRODUCT, Parcels.wrap(product));
//            intent.putExtra(Constants.EXTRA_SKU, Parcels.wrap(product.getSkl().get(position)));
//            startActivity(intent);
//        }
//    }

}
