package ninja.cyplay.com.apilibrary.domain.repository;


import java.util.List;

import ninja.cyplay.com.apilibrary.domain.repositoryModel.exception.BaseException;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.requests.AccessTokenRequest;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.requests.CatalogueLevelRequest;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.requests.GetConfigRequest;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.requests.GetDataOutRequest;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.requests.GetEventsRequest;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.requests.ProvisionDeviceRequestBody;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.requests.ResourceRequest;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.requests.ShopStatisticsRequest;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.responses.FilteredPaginatedResourcesResponse;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.responses.PaginatedResourcesResponse;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.responses.ResourceResponse;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.responses.ShopStatisticsResponse;
import ninja.cyplay.com.apilibrary.models.abstractmodels.ActionEvent;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AAddress;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ABarCodeInfo;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ABasket;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ABasketComment;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ABasketItem;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ABasketOffer;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ACatalogueLevel;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AConfig;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ACustomer;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ADelivery;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ADeliveryMode;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ADevice;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ADeviceConfiguration;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AEvent;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AFee;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AOffer;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_APayment;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_APaymentShare;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_APaymentTransaction;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AProduct;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AProductFilter;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AProductReview;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AProductReviewAttribute;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AProductShare;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AProductSuggestion;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AQuotation;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AShop;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AFeedback;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ATicket;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AUser;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AWishlistItem;
import ninja.cyplay.com.apilibrary.models.abstractmodels.WSReport;
import ninja.cyplay.com.apilibrary.models.abstractmodels.v2.Contact;
import ninja.cyplay.com.apilibrary.models.abstractmodels.v2.PR_AData;
import ninja.cyplay.com.apilibrary.models.business.OAuth2Credentials;
import ninja.cyplay.com.apilibrary.models.business.PasswordReplacement;


/**
 * Created by damien on 27/04/15.
 */
public interface PlayRetailRepository {

    ResourceResponse<PR_AConfig> getConfig(GetConfigRequest getConfigRequest) throws BaseException;

    ResourceResponse<PR_ADeviceConfiguration> getDeviceConfiguration(ResourceRequest getSellersRequest) throws BaseException;

    PaginatedResourcesResponse<PR_AShop> getShops(ResourceRequest resourcesRequest) throws BaseException;

    ResourceResponse<PR_ADevice> provisionDevice(ProvisionDeviceRequestBody provisionDeviceRequest) throws BaseException;

    ResourceResponse<PR_ADevice> updateProvisionDeviceStore(ProvisionDeviceRequestBody provisionDeviceRequest) throws BaseException;

    ResourceResponse<OAuth2Credentials> accessToken(AccessTokenRequest accessTokenRequest) throws BaseException;

    ResourceResponse<OAuth2Credentials> refreshAccessToken(AccessTokenRequest accessTokenRequest) throws BaseException;

    ResourceResponse<PR_AUser> replaceUserPassword(final ResourceRequest<PasswordReplacement> resourceRequest) throws BaseException;

    ResourceResponse<PR_AUser> notifyLostUserPassword(final ResourceRequest<PasswordReplacement> resourceRequest) throws BaseException;

    ResourceResponse<Void> invalidateToken(AccessTokenRequest accessTokenRequest) throws BaseException;

    ResourceResponse<PR_ACatalogueLevel> catalogueLevel(CatalogueLevelRequest request) throws BaseException;

    FilteredPaginatedResourcesResponse<PR_AProduct, PR_AProductFilter> getProductsByIds(ResourceRequest request) throws BaseException;

    FilteredPaginatedResourcesResponse<PR_AProduct, PR_AProductFilter> getProductsFromSkuIds(ResourceRequest request) throws BaseException;

    PaginatedResourcesResponse<PR_ACustomer> customerSearch(ResourceRequest request) throws BaseException;

    PaginatedResourcesResponse<PR_ACustomer> getCustomers(ResourceRequest request) throws BaseException;

    FilteredPaginatedResourcesResponse<PR_AProduct, PR_AProductFilter> productSearch(ResourceRequest request) throws BaseException;

    ResourceResponse<PR_AProduct> getProduct(ResourceRequest request) throws BaseException;

    FilteredPaginatedResourcesResponse<PR_AProduct, PR_AProductFilter> getProducts(ResourceRequest request) throws BaseException;

    PaginatedResourcesResponse<String> customerCompletion(ResourceRequest request) throws BaseException;

    ResourceResponse<PR_AProductSuggestion> suggestProducts(ResourceRequest request) throws BaseException;

    ResourceResponse<PR_ACustomer> getCustomerInfo(ResourceRequest request) throws BaseException;

    ResourceResponse<PR_ACustomer> saveCustomerInfo(ResourceRequest<PR_ACustomer> request) throws BaseException;

    ResourceResponse<PR_ACustomer> createCustomerInfo(ResourceRequest<PR_ACustomer> request) throws BaseException;

    PaginatedResourcesResponse<PR_ATicket> getSalesHistory(ResourceRequest request) throws BaseException;

    List<ActionEvent> saveActionEvents(final ResourceRequest<List<ActionEvent>> resourceRequest) throws BaseException;

    List<String> saveWebserviceTimes(ResourceRequest<List<WSReport>> request) throws BaseException;

    ResourceResponse<PR_AWishlistItem> addProductToWishlist(ResourceRequest<PR_AWishlistItem> request) throws BaseException;

    ResourceResponse<PR_AWishlistItem> deleteProductFromWishlist(ResourceRequest<PR_AWishlistItem> request) throws BaseException;

    ResourceResponse<PR_ABasket> getSellerCart(ResourceRequest request) throws BaseException;

    ResourceResponse<Void> getCustomerCart(final ResourceRequest request) throws BaseException;

    ResourceResponse<PR_AAddress> addAddressToCustomer(final ResourceRequest<PR_AAddress> request) throws BaseException;

    ResourceResponse<PR_AAddress> updateCustomerAddress(final ResourceRequest<PR_AAddress> request) throws BaseException;

    PaginatedResourcesResponse<PR_APayment> getCustomerPayments(ResourceRequest<PR_APayment> request);

    ResourceResponse<PR_APayment> getCustomerPayment(final ResourceRequest<PR_APayment> request);

    ResourceResponse<PR_APayment> createCustomerPayment(final ResourceRequest<PR_APayment> request);

    ResourceResponse<Void> validateCart(final ResourceRequest<PR_ABasketComment> request);

    ResourceResponse<PR_APayment> updateCustomerPayment(final ResourceRequest<PR_APayment> request);

    ResourceResponse<PR_APaymentTransaction> addTransactionToCustomerPayment(final ResourceRequest<PR_APaymentTransaction> request);

    ResourceResponse<PR_APaymentTransaction> updateCustomerPaymentTransaction(final ResourceRequest<PR_APaymentTransaction> request);


    //Cart
    ResourceResponse<PR_ABasketItem> addBasketItemToCustomerBasket(final ResourceRequest<PR_ABasketItem> request);

    ResourceResponse<PR_ABasketItem> updateBasketItemInCustomerBasket(final ResourceRequest<PR_ABasketItem> request);

    ResourceResponse<PR_ABasketItem> removeBasketItemFromCustomerBasket(final ResourceRequest<PR_ABasketItem> request);

    List<PR_ABasketItem> removeBasketItemsFromCustomerBasket(final ResourceRequest<PR_ABasketItem> request);

    ResourceResponse<PR_ABasket> clearBasket(final ResourceRequest<PR_ABasket> request);

    ResourceResponse<PR_ABasket> updateBasketDeliveryFees(final ResourceRequest<PR_ABasket> request);

    // Customer Offer
    ResourceResponse<PR_ABasketOffer> activeCustomerOffer(ResourceRequest<PR_ABasketOffer> request) throws BaseException;

    ResourceResponse<PR_ABasketOffer> inActiveCustomerOffer(ResourceRequest syncBasketOfferRequest) throws BaseException;


    // Seller basket
    ResourceResponse<PR_ABasketItem> addBasketItemToSellerBasket(final ResourceRequest<PR_ABasketItem> request);

    ResourceResponse<PR_ABasketItem> updateBasketItemInSellerBasket(final ResourceRequest<PR_ABasketItem> request);

    ResourceResponse<PR_ABasketItem> removeBasketItemFromSellerBasket(final ResourceRequest<PR_ABasketItem> request);

    List<PR_ABasketItem> removeBasketItemsFromSellerBasket(final ResourceRequest<PR_ABasketItem> request);

    ResourceResponse<PR_ABasket> clearSellerBasket(final ResourceRequest<PR_ABasket> request);

    ResourceResponse<PR_ABasket> linkSellerBasketToCustomer(final ResourceRequest<PR_ABasket> request);


    ResourceResponse<PR_ABarCodeInfo> getCorrespondence(ResourceRequest request) throws BaseException;

    PaginatedResourcesResponse<PR_AEvent> getEvents(GetEventsRequest request) throws BaseException;

    ShopStatisticsResponse getShopStatistics(ShopStatisticsRequest request) throws BaseException;

    ResourceResponse<PR_AProductShare> shareProducts(ResourceRequest<PR_AProductShare> request) throws BaseException;

    PaginatedResourcesResponse<PR_AOffer> getOffers(ResourceRequest<PR_AOffer> request) throws BaseException;

    PaginatedResourcesResponse<PR_AFee> getFees(final ResourceRequest<PR_AFee> request) throws BaseException;

    ResourceResponse<PR_AProductReview> addProductReview(final ResourceRequest<PR_AProductReview> request) throws BaseException;

    PaginatedResourcesResponse<PR_AProductReview> getProductReviews(final ResourceRequest<PR_AProductReview> request) throws BaseException;

    ResourceResponse<PR_AProductReview> updateProductReview(final ResourceRequest<PR_AProductReview> request) throws BaseException;

    PaginatedResourcesResponse<PR_AProductReviewAttribute> getProductReviewAttributes(final ResourceRequest<PR_AProductReviewAttribute> request) throws BaseException;

    PaginatedResourcesResponse<PR_ADeliveryMode> getDeliveryMode(final ResourceRequest<PR_ADeliveryMode> request) throws BaseException;

    ResourceResponse<PR_ADelivery> updateDelivery(final ResourceRequest<PR_ADelivery> request) throws BaseException;

    ResourceResponse<PR_APaymentShare> createPaymentShare(final ResourceRequest<PR_APaymentShare> request) throws BaseException;

    ResourceResponse<PR_AFeedback> sendFeedback(final ResourceRequest<PR_AFeedback> request) throws BaseException;

    ResourceResponse<PR_AQuotation> createQuotation(final ResourceRequest<PR_AQuotation> request) throws BaseException;

    PaginatedResourcesResponse<PR_AQuotation> getQuotations(ResourceRequest resourcesRequest) throws BaseException;

    ResourceResponse<PR_ABasketItem> addProductToQuotation(final ResourceRequest<PR_ABasketItem> request);

    ResourceResponse<PR_AQuotation> updateQuotation(final ResourceRequest<PR_AQuotation> request) throws BaseException;

    ResourceResponse<PR_ABasketItem> updateBasketItemInQuotationBasket(final ResourceRequest<PR_ABasketItem> request);

    ResourceResponse<PR_ABasket> getQuotationCart(final ResourceRequest request) throws BaseException;

    ResourceResponse<PR_ABasket> clearQuotationBasket(final ResourceRequest<PR_ABasket> request);

    ResourceResponse<PR_ABasketItem> removeBasketItemFromQuotationBasket(final ResourceRequest<PR_ABasketItem> request);

    List<PR_ABasketItem> removeBasketItemsFromQuotationBasket(final ResourceRequest<PR_ABasketItem> request);

    ResourceResponse<PR_AData> getDataOut(GetDataOutRequest request) throws BaseException;

    List<Contact> sendDataIn(ResourceRequest<List<Contact>> request) throws BaseException;
}
