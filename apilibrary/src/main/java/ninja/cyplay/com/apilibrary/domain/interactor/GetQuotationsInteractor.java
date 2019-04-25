package ninja.cyplay.com.apilibrary.domain.interactor;

import java.util.HashMap;
import java.util.List;

import ninja.cyplay.com.apilibrary.domain.interactor.callback.PaginatedResourceRequestCallback;
import ninja.cyplay.com.apilibrary.domain.interactor.callback.ResourceRequestCallback;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ABasket;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ABasketItem;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AQuotation;

/**
 * Created by wentongwang on 07/07/2017.
 */

public interface GetQuotationsInteractor {

    void getQuotationsByCustomer(HashMap<String, String> filters,
                                 List<String> project,
                                 List<String> sortBys,
                                 PaginatedResourceRequestCallback<PR_AQuotation> callback);

    void updateQuotation(String quotationId, PR_AQuotation quotation, ResourceRequestCallback<PR_AQuotation> callback);

    void updateBasketItemInQuotationBasket(String quotationId, PR_ABasketItem basketItem, final ResourceRequestCallback<PR_ABasketItem> callback);

    void getQuotationCart(String quotationId, final ResourceRequestCallback<PR_ABasket> callback);

    void clearBasket(String quotationId, final ResourceRequestCallback<PR_ABasket> callback);

    void removeBasketItemsFromCustomerBasket(String quotationId, List<String> basketItemIds, final ResourceRequestCallback<List<PR_ABasketItem>> callback);

    void addBasketItemToQuotationBasket(String quotationId, PR_ABasketItem basketItem, final ResourceRequestCallback<PR_ABasketItem> callback);

    void removeBasketItemFromQuotationBasket(String quotationId, PR_ABasketItem basketItem, final ResourceRequestCallback<PR_ABasketItem> callback);
}
