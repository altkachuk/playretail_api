package ninja.cyplay.com.apilibrary.domain.interactor;

import java.util.List;

import ninja.cyplay.com.apilibrary.domain.interactor.callback.ResourceRequestCallback;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ABasket;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ABasketComment;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ABasketItem;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_APayment;


/**
 * Created by damien on 28/05/15.
 */
public interface BasketInteractor {

    void getSellerCart(String sellerUserName, final ResourceRequestCallback<PR_ABasket> callback);

    void getCustomerCart(String customerId, final ResourceRequestCallback<PR_ABasket> callback);

    void addBasketItemToCustomerBasket(String customerId, PR_ABasketItem basketItem, final ResourceRequestCallback<PR_ABasketItem> callback);

    <Item extends PR_ABasketItem> void addBasketItemsToCustomerBasket(String customerId, List<Item> basketItems, final ResourceRequestCallback<List<PR_ABasketItem>> callback);

    void updateBasketItemInCustomerBasket(String customerId, PR_ABasketItem basketItem, final ResourceRequestCallback<PR_ABasketItem> callback);

    void removeBasketItemFromCustomerBasket(String customerId, PR_ABasketItem basketItem, final ResourceRequestCallback<PR_ABasketItem> callback);

    void removeBasketItemsFromCustomerBasket(String customerId, List<String> basketItemIds, final ResourceRequestCallback<List<PR_ABasketItem>> callback);

    void clearBasket(String customerId, final ResourceRequestCallback<PR_ABasket> callback);

    void updateBasketDeliveryFees(String customerId, PR_ABasket basket, final ResourceRequestCallback<PR_ABasket> callback);

    void validateCart(String customerId, PR_ABasketComment basketComment, final ResourceRequestCallback<Void> callback);

    void addBasketItemToSellerBasket(String sellerId, PR_ABasketItem basketItem, final ResourceRequestCallback<PR_ABasketItem> callback);

    void updateBasketItemInSellerBasket(String sellerId, PR_ABasketItem basketItem, final ResourceRequestCallback<PR_ABasketItem> callback);

    void removeBasketItemFromSellerBasket(String sellerId, PR_ABasketItem basketItem, final ResourceRequestCallback<PR_ABasketItem> callback);

    void removeBasketItemsFromSellerBasket(String customerId, List<String> basketItemIds, final ResourceRequestCallback<List<PR_ABasketItem>> callback);

    void clearSellerBasket(String sellerId, final ResourceRequestCallback<PR_ABasket> callback);


    void linkSellerBasketToCustomer(String customerId, String sellerId, List<String> project, final ResourceRequestCallback<PR_ABasket> callback);
}
