package ninja.cyplay.com.playretail_api.ui.presenter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ninja.cyplay.com.apilibrary.domain.interactor.BasketInteractor;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.exception.BaseException;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ABasket;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AProduct;
import ninja.cyplay.com.apilibrary.models.business.PR_Basket;
import ninja.cyplay.com.playretail_api.model.business.CartItem;
import ninja.cyplay.com.playretail_api.model.business.Product;
import ninja.cyplay.com.playretail_api.model.business.Sku;
import ninja.cyplay.com.playretail_api.model.singleton.CustomerContext;
import ninja.cyplay.com.playretail_api.model.singleton.SellerContext;
import ninja.cyplay.com.apilibrary.utils.LogUtils;
import ninja.cyplay.com.playretail_api.utils.SkuUtils;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.app.BasePresenter;
import ninja.cyplay.com.playretail_api.ui.view.BasketView;

/**
 * Created by damien on 27/05/15.
 */
public class BasketPresenterImpl extends BasePresenter implements BasketPresenter {

    @Inject
    SellerContext sellerContext;

    @Inject
    CustomerContext customerContext;

    private Context context;
    private BasketView basketView;
    private BasketInteractor basketInteractor;

    public BasketPresenterImpl(Context context, BasketInteractor basketInteractor) {
        super(context);
        this.context = context;
        this.basketInteractor = basketInteractor;
    }

    @Override
    public void initialize() {

    }

    @Override
    public PR_Basket getContextBasket() {
        if (customerContext != null && customerContext.isCustomerIdentified())
            return (PR_Basket) customerContext.getBasket();
        else if (sellerContext != null && sellerContext.getBasket() != null)
            return (sellerContext.getBasket());
        return null;
    }

    @Override
    public void getSellerBasket() {
        if (sellerContext != null && sellerContext.getSeller() != null && sellerContext.getBasket() == null) {
            basketView.showLoading();
            basketInteractor.getSellerBasket(sellerContext.getSeller().getUn(), new BasketInteractor.SellerBasketCallback() {

                @Override
                public void onSellerBasketSuccess(PR_ABasket basket) {
                    basketView.hideLoading();
                    sellerContext.setBasket((PR_Basket) basket);
                    basketView.onSellerBasketSuccess();
                }

                @Override
                public void onSellerBasketError(BaseException e) {
                    Log.e(LogUtils.generateTag(this), "Error on SellerBasket presenter");
                    basketView.hideLoading();
                    if (e.getResponse() != null && e.getResponse().getStatus() != null)
                        basketView.displayPopUp(e.getResponse().getDetail());
                    basketView.onSellerBasketError();
                }

            });
        }
    }

    // -------------------------------------------------------------------------------------------
    //                                     ADD / DEL
    // -------------------------------------------------------------------------------------------

    @Override
    public void addOneQuantityItemToContextBasket(Product PRProduct, Sku sku) {
        if (customerContext != null && customerContext.isCustomerIdentified()) {
            Sku bsku = customerContext.getSavedSkuFromBasket(PRProduct, sku);
            addOneQuantityItemToCustomerBasket(PRProduct, bsku);
        }
        else {
            Sku bsku = sellerContext.getSavedSkuFromBasket(PRProduct, sku);
            addOneQuantityItemToSellerBasket(PRProduct, bsku);
        }
    }

    @Override
    public void removeOneQuantityItemFromContextBasket(Product PRProduct, Sku sku) {
        if (customerContext != null && customerContext.isCustomerIdentified()) {
            Sku bsku = customerContext.getSavedSkuFromBasket(PRProduct, sku);
            removeOneQuantityItemFromCustomerBasket(PRProduct, bsku);
        }
        else {
            Sku bsku = sellerContext.getSavedSkuFromBasket(PRProduct, sku);
            removeOneQuantityItemFromSellerBasket(PRProduct, bsku);
        }
    }

    private void addOneQuantityItemToSellerBasket(Product PRProduct, Sku sku) {
        synchronizeItemSellerBasket(PRProduct, sku, sku.quantity == null ? 1 : sku.quantity + 1);
    }

    private void addOneQuantityItemToCustomerBasket(Product PRProduct, Sku sku) {
        if (customerContext != null && customerContext.isCustomerIdentified())
            synchronizeItemCustomerBasket(PRProduct, sku, sku.quantity == null ? 1 : sku.quantity + 1);
    }

    private void removeOneQuantityItemFromSellerBasket(Product PRProduct, Sku sku) {
        if (sku.quantity != null) {
            if (sku.quantity > 1)
                synchronizeItemSellerBasket(PRProduct, sku, sku.quantity - 1);
            else
                deleteItemFromSellerBasket(PRProduct, sku);
        }
    }

    private void removeOneQuantityItemFromCustomerBasket(Product PRProduct, Sku sku) {
        if (customerContext != null && customerContext.isCustomerIdentified()) {
            if (sku.quantity != null) {
                if (sku.quantity > 1)
                    synchronizeItemCustomerBasket(PRProduct, sku, sku.quantity - 1);
                else
                    deleteItemFromCustomerBasket(PRProduct, sku);
            }
        }
    }

    // -------------------------------------------------------------------------------------------
    //                                         SYNC
    // -------------------------------------------------------------------------------------------

    private void synchronizeItemSellerBasket(final Product product, final Sku sku, final Integer quantity) {
        basketView.showLoading();
        basketInteractor.syncProductSellerBasket(
                sellerContext.getSeller().getUn(),
                product.getSkid(),
                sku.skid,
                quantity,
                new BasketInteractor.SyncBasketCallBack() {
                    @Override
                    public void onSyncSuccess() {
                        basketView.hideLoading();
                        // update quantity
                        if (sellerContext != null)
                            sellerContext.getSavedSkuFromBasket(product, sku).quantity = quantity;
                        basketView.onSyncSuccess();
                    }

                    @Override
                    public void onSyncError(BaseException e) {
                        Log.e(LogUtils.generateTag(this), "Error on synchronizeItemSellerBasket presenter");
                        basketView.hideLoading();
                        if (e.getResponse() != null && e.getResponse().getStatus() != null)
                            basketView.displayPopUp(e.getResponse().getDetail());
                        basketView.onSyncError();
                    }
                });
    }

    private void synchronizeItemCustomerBasket(final Product PRProduct, final Sku sku, final Integer quantity) {
        basketView.showLoading();
        basketInteractor.syncProductCustomerBasket(
                customerContext.getCustomer().cid,
                PRProduct.getSkid(),
                sku.skid,
                quantity,
                new BasketInteractor.SyncBasketCallBack() {
                    @Override
                    public void onSyncSuccess() {
                        basketView.hideLoading();
                        // update quantity
                        if (customerContext != null && customerContext.isCustomerIdentified())
                            customerContext.getSavedSkuFromBasket(PRProduct, sku).quantity = quantity;
                        basketView.onSyncSuccess();
                    }

                    @Override
                    public void onSyncError(BaseException e) {
                        Log.e(LogUtils.generateTag(this), "Error on synchronizeItemCustomerBasket presenter");
                        basketView.hideLoading();
                        if (e.getResponse() != null && e.getResponse().getStatus() != null)
                            basketView.displayPopUp(e.getResponse().getDetail());
                        basketView.onSyncError();
                    }
                });
    }

    // -------------------------------------------------------------------------------------------
    //                                       DELETE
    // -------------------------------------------------------------------------------------------

    @Override
    public void deleteAll() {
        if (customerContext != null && customerContext.isCustomerIdentified()
                && customerContext.getBasket() != null
                && customerContext.getBasket().getItems() != null
                && customerContext.getBasket().getItems().size() > 0)
            deleteCustomerBasket();
        else if (sellerContext != null && sellerContext.getBasket() != null
                && sellerContext.getBasket().getItems() != null && sellerContext.getBasket().getItems().size() > 0)
            deleteSellerBasket();
    }

    @Override
    public List<CartItem> getCartItems() {

        //TODO redo that
//        List<Product> PRProducts = getContextBasket();
//        if (PRProducts != null) {
//            List<CartItem> cartItemList = new ArrayList<>();
//            for (int i = 0 ; i < PRProducts.size() ; i++) {
//                Product PRProduct = PRProducts.get(i);
//                if (PRProduct.getSkl() != null)
//                    for (int j = 0 ; j < PRProduct.getSkl().size() ; j++) {
//                        Sku sku = PRProduct.getSkl().get(j);
//                        if (sku.quantity != null && sku.quantity != 0) {
//                            cartItemList.add(new CartItem(PRProduct, sku));
//                        }
//                    }
//            }
//            return cartItemList;
//        }
        return null;
    }

    @Override
    public Double getTotalPrice() {
        List<CartItem> cartItemList = getCartItems();
        Double total_price = 0D;
        if (cartItemList != null) {
            for (CartItem item : cartItemList) {
                if (item.product != null
                        && item.sku != null
                        && item.sku.quantity != null
                        && item.sku.quantity > 0) {
                    Double skp = SkuUtils.getPriceForSku(item.sku);
                    total_price += skp * item.sku.quantity;
                }

            }
            return total_price;
        }
        return 0D;
    }

    private void deleteSellerBasket() {
        basketView.showLoading();
        basketInteractor.deleteSellerBasket(
                sellerContext.getSeller().getUn(),
                new BasketInteractor.SyncBasketCallBack() {
                    @Override
                    public void onSyncSuccess() {
                        basketView.hideLoading();
                        // quantity 0 = item deleted from basket
                        if (sellerContext != null)
                            sellerContext.clearBasket();
                        basketView.onDeleteSuccess();
                    }

                    @Override
                    public void onSyncError(BaseException e) {
                        Log.e(LogUtils.generateTag(this), "Error on deleteSellerBasket presenter");
                        basketView.hideLoading();
                        if (e.getResponse() != null && e.getResponse().getStatus() != null)
                            basketView.displayPopUp(e.getResponse().getDetail());
                        basketView.onDeleteError();
                    }
                });
    }

    private void deleteCustomerBasket() {
        basketView.showLoading();
        basketInteractor.deleteClientBasket(
                customerContext.getCustomer().cid,
                new BasketInteractor.SyncBasketCallBack() {
                    @Override
                    public void onSyncSuccess() {
                        basketView.hideLoading();
                        // quantity 0 = item deleted from basket
                        if (customerContext != null && customerContext.isCustomerIdentified())
                            customerContext.clearBasket();
                        basketView.onDeleteSuccess();
                    }

                    @Override
                    public void onSyncError(BaseException e) {
                        Log.e(LogUtils.generateTag(this), "Error on deleteCustomerBasket presenter");
                        basketView.hideLoading();
                        if (e.getResponse() != null && e.getResponse().getStatus() != null)
                            basketView.displayPopUp(e.getResponse().getDetail());
                        basketView.onDeleteError();
                    }
                });
    }

    // -------------------------------------------------------------------------------------------
    //
    // -------------------------------------------------------------------------------------------

    @Override
    public void deleteItemFromContextBasket(Product PRProduct, Sku sku) {
        if (customerContext != null && customerContext.isCustomerIdentified())
            deleteItemFromCustomerBasket(PRProduct, sku);
        else
            deleteItemFromSellerBasket(PRProduct, sku);
    }

    private void deleteItemFromSellerBasket(final Product PRProduct, final Sku sku) {
        basketView.showLoading();
        basketInteractor.deleteProductToSellerBasket(
                sellerContext.getSeller().getUn(),
                PRProduct.getSkid(),
                sku.skid,
                new BasketInteractor.SyncBasketCallBack() {
                    @Override
                    public void onSyncSuccess() {
                        basketView.hideLoading();
                        // quantity 0 = item deleted from basket
                        if (sellerContext != null)
                            sellerContext.getSavedSkuFromBasket(PRProduct, sku).quantity = 0;
                        basketView.onDeleteSuccess();
                    }

                    @Override
                    public void onSyncError(BaseException e) {
                        Log.e(LogUtils.generateTag(this), "Error on deleteItemFromSellerBasket presenter");
                        basketView.hideLoading();
                        if (e.getResponse() != null && e.getResponse().getStatus() != null)
                            basketView.displayPopUp(e.getResponse().getDetail());
                        basketView.onDeleteError();
                    }
                });
    }

    private void deleteItemFromCustomerBasket(final Product PRProduct, final Sku sku) {
        basketView.showLoading();
        basketInteractor.deleteProductToCustomerBasket(
                customerContext.getCustomer().cid,
                PRProduct.getSkid(),
                sku.skid,
                new BasketInteractor.SyncBasketCallBack() {
                    @Override
                    public void onSyncSuccess() {
                        basketView.hideLoading();
                        // quantity 0 = item deleted from basket
                        if (customerContext != null && customerContext.isCustomerIdentified())
                            customerContext.getSavedSkuFromBasket(PRProduct, sku).quantity = 0;
                        basketView.onDeleteSuccess();
                    }

                    @Override
                    public void onSyncError(BaseException e) {
                        Log.e(LogUtils.generateTag(this), "Error on deleteItemFromSellerBasket presenter");
                        basketView.hideLoading();
                        if (e.getResponse() != null && e.getResponse().getStatus() != null)
                            basketView.displayPopUp(e.getResponse().getDetail());
                        basketView.onDeleteError();
                    }
                });
    }


    @Override
    public void moreOption(final Activity activity, final Product PRProduct, final Sku sku) {
        if (activity != null) {
            new MaterialDialog.Builder(activity)
                    .title("More options")
                    .items(new String[]{"Add To Cart", "Reserve", "Command"})
                    .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                        @Override
                        public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                            /**
                             * If you use alwaysCallSingleChoiceCallback(), which is discussed below,
                             * returning false here won't allow the newly selected radio button to actually be selected.
                             **/
                            if (which == 0)
                                addOneQuantityItemToContextBasket(PRProduct, sku);
                            else if (which == -1)
                                Toast.makeText(activity, "Option [" + which + "] selected", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                    })
                    .positiveText(R.string.confirm)
                    .show();
        }
    }

    // -------------------------------------------------------------------------------------------
    //
    // -------------------------------------------------------------------------------------------

    @Override
    public void setView(BasketView view) {
        this.basketView = view;
    }

    @Override
    public void onViewCreate() {

    }

    @Override
    public void onViewResume() {

    }

    @Override
    public void onViewDestroy() {

    }

}
