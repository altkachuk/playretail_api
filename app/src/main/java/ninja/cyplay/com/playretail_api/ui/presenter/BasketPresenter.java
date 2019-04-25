package ninja.cyplay.com.playretail_api.ui.presenter;

import android.app.Activity;

import java.util.List;

import ninja.cyplay.com.apilibrary.models.business.PR_Basket;
import ninja.cyplay.com.playretail_api.model.business.CartItem;
import ninja.cyplay.com.playretail_api.model.business.Product;
import ninja.cyplay.com.playretail_api.model.business.Sku;
import ninja.cyplay.com.playretail_api.ui.view.BasketView;

/**
 * Created by damien on 27/05/15.
 */
public interface BasketPresenter extends Presenter<BasketView> {

    public PR_Basket getContextBasket();

    public void getSellerBasket();

    public void deleteAll();

    public Double getTotalPrice();

    public List<CartItem> getCartItems();

    public void addOneQuantityItemToContextBasket(Product PRProduct, Sku sku);

    public void removeOneQuantityItemFromContextBasket(Product PRProduct, Sku sku);

    public void deleteItemFromContextBasket(Product PRProduct, Sku sku);

    public void moreOption(Activity activity, Product PRProduct, Sku sku);
}