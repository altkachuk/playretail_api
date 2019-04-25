package ninja.cyplay.com.playretail_api.ui.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.text.DecimalFormat;
import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;
import ninja.cyplay.com.apilibrary.models.business.PR_Basket;
import ninja.cyplay.com.playretail_api.model.business.CartItem;
import ninja.cyplay.com.playretail_api.model.events.BasketItemDeletePressedEvent;
import ninja.cyplay.com.playretail_api.model.events.BasketItemMinusPressedEvent;
import ninja.cyplay.com.playretail_api.model.events.BasketItemPlusPressedEvent;
import ninja.cyplay.com.apilibrary.models.singleton.APIValue;
import ninja.cyplay.com.playretail_api.model.singleton.CustomerContext;
import ninja.cyplay.com.playretail_api.model.singleton.SellerContext;
import ninja.cyplay.com.playretail_api.utils.ColorUtils;
import ninja.cyplay.com.apilibrary.utils.Constants;
import ninja.cyplay.com.apilibrary.utils.DialogUtils;
import ninja.cyplay.com.apilibrary.utils.ScreenUtils;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.ui.activity.PaymentActivity;
import ninja.cyplay.com.playretail_api.ui.adapter.BasketRecyclerAdapter;
import ninja.cyplay.com.playretail_api.ui.custom.MessageProgressDialog;
import ninja.cyplay.com.playretail_api.ui.presenter.BasketPresenter;

/**
 * Created by damien on 19/05/15.
 */
public class BasketFragment extends BasketBaseFragment {

    @Inject
    Bus bus;

    @Inject
    APIValue apiValue;

    @Inject
    SellerContext sellerContext;

    @Inject
    CustomerContext customerContext;

    @Inject
    BasketPresenter basketPresenter;

    @InjectView(R.id.recycler_view)
    RecyclerView recyclerView;

    @InjectView(R.id.basket_confirm_buy)
    TextView confirmBuy;

    @InjectView(R.id.total_price)
    TextView total;

    private PR_Basket basket;

    private MessageProgressDialog progress;

    private BasketRecyclerAdapter adapter;
    private RecyclerView.LayoutManager  layoutManager;

    private final static DecimalFormat df = new DecimalFormat("0.00##");

    // -------------------------------------------------------------------------------------------
    //                                      Lifecycle
    // -------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_basket, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        bus.register(this);
        basketPresenter.setView(this);
        basket = basketPresenter.getContextBasket();
//        initCartItemsFromProducts(basketPresenter.getContextBasket());
        initLoadingDialog();

//        if (apiValue != null && apiValue.getPRConfig() != null
//                && apiValue.getPRConfig().getPRShopConfig() != null)
//            this.store_id = apiValue.getPRConfig().getPRShopConfig().store_id;
        adapter = new BasketRecyclerAdapter(getActivity(), basket.getItems());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateDesign();
        initRecycler();
        checkIfLoadBasketNeeded();
        updatePrice();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.RESULT_PAIEMENT_COMPLETE) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    //TODO Clear basket
                    getActivity().finish();
                    break;
            }
        }
    }

    // -------------------------------------------------------------------------------------------
    //                                      Override(s)
    // -------------------------------------------------------------------------------------------

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.basket, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                getActivity().finish();
                getActivity().overridePendingTransition(R.anim.slide_no_anim, R.anim.slide_out_right);
                return true;
            case R.id.action_buy:
                confirmBuy();
                return true;
        }

        return super.onOptionsItemSelected(item);
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

    private void initLoadingDialog() {
        // initialize
        progress = new MessageProgressDialog(getActivity());
    }

    private void checkIfLoadBasketNeeded() {
        if (customerContext != null && !customerContext.isCustomerIdentified())
//            if (sellerContext.getBasket() == null) // always load basket (or certain case scenario won't work)
            basketPresenter.getSellerBasket();
    }

    private void updatePrice() {
        Double total_price = basketPresenter.getTotalPrice();
        if (total_price > 0)
            total.setText(df.format(total_price) + " €");
        else
            total.setText("");
    }

    void updateDesign() {
        // Confirm button
        ColorUtils.setTextColor(confirmBuy, ColorUtils.FeatureColor.PRIMARY_DARK);
        GradientDrawable confirmBG = new GradientDrawable();
        confirmBG.setStroke((int) ScreenUtils.convertDpToPixel(2.f, getActivity()),
                (Color.parseColor(ColorUtils.FeatureColor.getColorHex(ColorUtils.FeatureColor.PRIMARY_DARK))));
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN)
            confirmBuy.setBackgroundDrawable(confirmBG);
        else
            confirmBuy.setBackground(confirmBG);
    }

    private void reloadCurrentBasket() {
//        this.cartItemList = basketPresenter.getCartItems();
//        initCartItemsFromProducts(basketPresenter.getContextBasket());
        basket = basketPresenter.getContextBasket();
        adapter.setItems(basket.getItems());
        adapter.notifyDataSetChanged();
        updatePrice();
    }

    // -------------------------------------------------------------------------------------------
    //                                      Action(s)
    // -------------------------------------------------------------------------------------------

    @OnClick(R.id.delete_all_basket)
    void deleteAllBasket() {
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.cart_delete_all_items_title)
                .setMessage(R.string.cart_delete_all_items_desc)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        basketPresenter.deleteAll();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) { }
                })
//                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @OnClick(R.id.basket_confirm_buy)
    void confirmBuy() {
//        if (cartItemList == null || cartItemList.size() == 0) {
        if (basket != null && basket.getItems() != null && basket.getItems().size() == 0) {
            DialogUtils.showDialog(getActivity(), getString(R.string.title_error), getString(R.string.empty_cart));
        }
        else {
            Intent intent = new Intent(getActivity(), PaymentActivity.class);
            startActivityForResult(intent, Constants.RESULT_PAIEMENT_COMPLETE);
        }
    }

    // -------------------------------------------------------------------------------------------
    //                                      View Impl
    // -------------------------------------------------------------------------------------------

    @Override
    public void onSellerBasketSuccess() {
        if (sellerContext != null)
            reloadCurrentBasket();
    }

    @Override
    public void onSellerBasketError() {

    }

    @Override
    public void onSyncSuccess() {
        reloadCurrentBasket();
    }

    @Override
    public void onSyncError() {
    }

    @Override
    public void onDeleteSuccess() {
        reloadCurrentBasket();
        adapter.closeAllItems();
    }

    @Override
    public void onDeleteError() {
    }

    @Override
    public void displayPopUp(String message) {
        DialogUtils.showDialog(getActivity(), getString(R.string.warning), message);
    }

    @Override
    public void showLoading() {
        if (progress != null)
            progress.show();
    }

    @Override
    public void hideLoading() {
        if (progress != null)
            progress.dismiss();
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

    @Subscribe
    public void onMinusButton(BasketItemMinusPressedEvent basketItemMinusPressedEvent) {
        if (basketItemMinusPressedEvent != null && basketItemMinusPressedEvent.getCartItem() != null)
            basketPresenter.removeOneQuantityItemFromContextBasket(basketItemMinusPressedEvent.getCartItem().product,
                    basketItemMinusPressedEvent.getCartItem().sku);
    }

    @Subscribe
    public void onDeleteButton(BasketItemDeletePressedEvent basketItemDeletePressedEvent) {
        if (basketItemDeletePressedEvent != null && basketItemDeletePressedEvent.getCartItem() != null)
            basketPresenter.deleteItemFromContextBasket(basketItemDeletePressedEvent.getCartItem().product, basketItemDeletePressedEvent.getCartItem().sku);
    }


}
