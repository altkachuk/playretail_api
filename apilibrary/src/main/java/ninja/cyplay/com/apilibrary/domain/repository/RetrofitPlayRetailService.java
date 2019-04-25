package ninja.cyplay.com.apilibrary.domain.repository;

import java.util.List;
import java.util.Map;

import ninja.cyplay.com.apilibrary.domain.repositoryModel.requests.ProvisionDeviceRequestBody;
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
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ASeller;
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
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.PATCH;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.QueryMap;

public interface RetrofitPlayRetailService {

    String SHOP_VIEW_API_VERSION = "2.0";
    String SHOP_VIEW_API_VERSION_V21 = "2.1";
    String SHOP_VIEW_API_PREFIX = "/shopview/" + SHOP_VIEW_API_VERSION;
    String SHOP_VIEW_API_PREFIX_V21 = "/shopview/" + SHOP_VIEW_API_VERSION_V21;


    String OAUTH_API_PREFIX = "/auth";

    @FormUrlEncoded
    @POST(OAUTH_API_PREFIX + "/token/")
    ResourceResponse<OAuth2Credentials> getAccessToken(
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret,
            @Field("grant_type") String grantType,
            @Field("username") String username,
            @Field("password") String password,
            @Field("scope") String scope);

    @FormUrlEncoded
    @POST(OAUTH_API_PREFIX + "/revoke_token/")
    ResourceResponse<Void> invalidateToken(
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret,
            @Field("token") String token);

    @FormUrlEncoded
    @POST(OAUTH_API_PREFIX + "/token/")
    ResourceResponse<OAuth2Credentials> refreshAccessToken(
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret,
            @Field("grant_type") String grantType,
            @Field("refresh_token") String refreshToken
    );

    @PUT(SHOP_VIEW_API_PREFIX + "/users/{user_id}/replacepassword")
    ResourceResponse<PR_AUser> replaceUserPassword(@Path("user_id") String userId,
                                                   @Body PasswordReplacement replacement);

    @PUT(SHOP_VIEW_API_PREFIX + "/users/{user_id}/notifylostpassword")
    ResourceResponse<PR_AUser> notifyLostUserPassword(@Path("user_id") String ushaerId,
                                                   @Body PasswordReplacement replacement);


    @GET(SHOP_VIEW_API_PREFIX + "/" + PR_AConfig.API_RESOURCE_NAME + "/{id}/")
    ResourceResponse<PR_AConfig> getConfig(@Path("id") String udid,
                                           @Query("app_version") String appVersion,
                                           @Query("os_version") String activationIosVersion,
                                           @Query("lang") String lang,
                                           @Query("shop_id") String shopId);

    @GET(SHOP_VIEW_API_PREFIX + "/" + PR_ADevice.API_RESOURCE_NAME + "/{id}/conf/")
    ResourceResponse<PR_ADeviceConfiguration> getDeviceConf(@Path("id") String id);

    @GET(SHOP_VIEW_API_PREFIX + "/" + PR_AShop.API_RESOURCE_NAME + "/")
    PaginatedResourcesResponse<PR_AShop> getShops(@QueryMap Map<String, String> params);

    @POST(SHOP_VIEW_API_PREFIX + "/" + PR_ADevice.API_RESOURCE_NAME + "/")
    ResourceResponse<PR_ADevice> provisionDevice(@Body ProvisionDeviceRequestBody request);

    @PATCH(SHOP_VIEW_API_PREFIX + "/" + PR_ADevice.API_RESOURCE_NAME + "/{id}/")
    ResourceResponse<PR_ADevice> provisionUpdateDeviceStore(
            @Path("id") String udid,
            @Body ProvisionDeviceRequestBody request
    );

    @GET(SHOP_VIEW_API_PREFIX + "/" + PR_ACatalogueLevel.API_RESOURCE_NAME + "/{pk}/")
    ResourceResponse<PR_ACatalogueLevel> getCatalogueLevel(@Path("pk") String udid, @Query("fields") String fields);

    @GET(SHOP_VIEW_API_PREFIX + "/" + PR_AProduct.API_RESOURCE_NAME + "/")
    FilteredPaginatedResourcesResponse<PR_AProduct, PR_AProductFilter> getProducts(@QueryMap Map<String, String> options,
                                                                                   @Query("availabilities__price") List<String> prices,
                                                                                   @Query("skus__specs") List<String> skusSpecs,
                                                                                   @Query("specs") List<String> specs);

    @GET(SHOP_VIEW_API_PREFIX + "/" + PR_AProduct.API_RESOURCE_NAME + "/{id}/")
    ResourceResponse<PR_AProduct> getProduct(@Path("id") String id, @QueryMap Map<String, String> options);

    @GET(SHOP_VIEW_API_PREFIX + "/" + PR_AProduct.API_RESOURCE_NAME + "/")
    FilteredPaginatedResourcesResponse<PR_AProduct, PR_AProductFilter> getProducts(@Query("id") List<String> ids,
                                                                                   @QueryMap Map<String, String> params,
                                                                                   @Query("skus__specs") List<String> skusSpecs,
                                                                                   @Query("specs") List<String> specs);

    @GET(SHOP_VIEW_API_PREFIX + "/" + PR_AProduct.API_RESOURCE_NAME + "/")
    FilteredPaginatedResourcesResponse<PR_AProduct, PR_AProductFilter> getProductsFromSkuIds(@Query("skus__id") List<String> skuIds,
                                                                                             @QueryMap Map<String, String> params);

    @GET(SHOP_VIEW_API_PREFIX + "/" + PR_AProduct.API_RESOURCE_NAME + "/search")
    FilteredPaginatedResourcesResponse<PR_AProduct, PR_AProductFilter> productSearch(@QueryMap Map<String, String> params,
                                                                                     @Query("availabilities__price") List<String> prices,
                                                                                     @Query("skus__specs") List<String> skusSpecs,
                                                                                     @Query("specs") List<String> specs);

    @GET(SHOP_VIEW_API_PREFIX + "/" + PR_AProduct.API_RESOURCE_NAME + "/autocomplete")
    ResourceResponse<PR_AProductSuggestion> productCompletion(@QueryMap Map<String, String> params);

    @GET(SHOP_VIEW_API_PREFIX + "/" + PR_ACustomer.API_RESOURCE_NAME + "/search")
    PaginatedResourcesResponse<PR_ACustomer> customerSearch(@QueryMap Map<String, String> options);

    @GET(SHOP_VIEW_API_PREFIX + "/" + PR_ACustomer.API_RESOURCE_NAME + "/autocomplete/")
    PaginatedResourcesResponse<String> customerCompletion(@QueryMap Map<String, String> params);

    @GET(SHOP_VIEW_API_PREFIX_V21 + "/" + PR_ACustomer.API_RESOURCE_NAME + "/{id}/")
    ResourceResponse<PR_ACustomer> getCustomerInfo(@Path("id") String id, @QueryMap Map<String, String> params);

    @GET(SHOP_VIEW_API_PREFIX + "/" + PR_ATicket.API_RESOURCE_NAME + "/")
    PaginatedResourcesResponse<PR_ATicket> getSalesHistory(@QueryMap Map<String, String> params);

    @PATCH(SHOP_VIEW_API_PREFIX + "/" + WSReport.API_RESOURCE_NAME + "/")
    List<String> saveWebserviceTimes(@Body List<WSReport> reportDatas);

    /***
     * CUSTOMERS
     ****/
    @PATCH(SHOP_VIEW_API_PREFIX + "/" + PR_ACustomer.API_RESOURCE_NAME + "/{id}/")
    ResourceResponse<PR_ACustomer> saveCustomerInfo(
            @Path("id") String _id,
            @Body PR_ACustomer request
    );

    @GET(SHOP_VIEW_API_PREFIX + "/" + PR_ACustomer.API_RESOURCE_NAME + "/")
    PaginatedResourcesResponse<PR_ACustomer> getCustomers(@QueryMap Map<String, String> options);

    @POST(SHOP_VIEW_API_PREFIX + "/" + PR_ACustomer.API_RESOURCE_NAME + "/")
    ResourceResponse<PR_ACustomer> createCustomerInfo(
            @Body PR_ACustomer request
    );

    @POST(SHOP_VIEW_API_PREFIX + "/" + PR_ACustomer.API_RESOURCE_NAME + "/{customer_id}/wishlistitems/")
    ResourceResponse<PR_AWishlistItem> addProductToWishlist(
            @Path("customer_id") String customerId,
            @Body PR_AWishlistItem wishlistItem
    );

    @DELETE(SHOP_VIEW_API_PREFIX + "/" + PR_ACustomer.API_RESOURCE_NAME + "/{customer_id}/wishlistitems/{wishlistitem_id}/")
    ResourceResponse<PR_AWishlistItem> deleteProductFromWishlist(
            @Path("customer_id") String customerId,
            @Path("wishlistitem_id") String wishlistItemId
    );


    // PAYMENT GET
    @GET(SHOP_VIEW_API_PREFIX + "/" + PR_ACustomer.API_RESOURCE_NAME + "/{customer_id}/payments/{payment_id}/")
    ResourceResponse<PR_APayment> getCustomerPayment(
            @Path("customer_id") String customerId,
            @Path("payment_id") String paymentId

    );

    // PAYMENTS GET
    @GET(SHOP_VIEW_API_PREFIX + "/" + PR_ACustomer.API_RESOURCE_NAME + "/{customer_id}/payments/")
    PaginatedResourcesResponse<PR_APayment> getCustomerPayments(
            @Path("customer_id") String customerId ,
            @QueryMap Map<String, String> params
    );


    // PAYMENT CREATION
    @POST(SHOP_VIEW_API_PREFIX + "/" + PR_ACustomer.API_RESOURCE_NAME + "/{customer_id}/payments/")
    ResourceResponse<PR_APayment> createCustomerPayment(
            @Path("customer_id") String customerId,
            @Body PR_APayment payment
    );

    @POST(SHOP_VIEW_API_PREFIX + "/" + PR_ACustomer.API_RESOURCE_NAME + "/{customer_id}/payments/?from_basket=True")
    ResourceResponse<PR_APayment> createCustomerPaymentFromBasket(
            @Path("customer_id") String customerId,
            @Body PR_APayment payment
    );

    @POST(SHOP_VIEW_API_PREFIX + "/" + PR_ACustomer.API_RESOURCE_NAME + "/{customer_id}/validate_cart/")
    ResourceResponse<Void> validateCart(
            @Path("customer_id") String customerId,
            @Body PR_ABasketComment comment
    );

    // PAYMENT UPDATE
    @PATCH(SHOP_VIEW_API_PREFIX + "/" + PR_ACustomer.API_RESOURCE_NAME + "/{customer_id}/payments/{payment_id}/")
    ResourceResponse<PR_APayment> updateCustomerPayment(
            @Path("customer_id") String customerId,
            @Path("payment_id") String paymentId,
            @Body PR_APayment payment
    );

    @POST(SHOP_VIEW_API_PREFIX + "/" + PR_ACustomer.API_RESOURCE_NAME + "/{customer_id}/payments/{payment_id}/transactions/")
    ResourceResponse<PR_APaymentTransaction> addTransactionToCustomerPayment(
            @Path("customer_id") String customerId,
            @Path("payment_id") String paymentId,
            @Body PR_APaymentTransaction paymentTransaction
    );

    @PATCH(SHOP_VIEW_API_PREFIX + "/" + PR_ACustomer.API_RESOURCE_NAME + "/{customer_id}/payments/{payment_id}/transactions/{transaction_id}/")
    ResourceResponse<PR_APaymentTransaction> updateCustomerPaymentTransaction(
            @Path("customer_id") String customerId,
            @Path("payment_id") String paymentId,
            @Path("transaction_id") String transactionId,
            @Body PR_APaymentTransaction paymentTransaction
    );

    // Shipping address
    @POST(SHOP_VIEW_API_PREFIX + "/" + PR_ACustomer.API_RESOURCE_NAME + "/{customer_id}/" + PR_AAddress.API_RESOURCE_NAME + "/")
    ResourceResponse<PR_AAddress> addAddressToCustomer(
            @Path("customer_id") String customerId,
            @Body PR_AAddress address
    );

    @PATCH(SHOP_VIEW_API_PREFIX + "/" + PR_ACustomer.API_RESOURCE_NAME + "/{customer_id}/" + PR_AAddress.API_RESOURCE_NAME + "/{address_id}/")
    ResourceResponse<PR_AAddress> updateCustomerAddress(
            @Path("customer_id") String customerId,
            @Path("address_id") String addressId,
            @Body PR_AAddress address
    );


    @POST(SHOP_VIEW_API_PREFIX + "/" + PR_AProductShare.API_RESOURCE_NAME + "/")
    ResourceResponse<PR_AProductShare> shareProducts(
            @Body PR_AProductShare productShare
    );

    //Delivery Mode
    @GET(SHOP_VIEW_API_PREFIX + "/" + PR_ADeliveryMode.API_RESOURCE_NAME + "/")
    PaginatedResourcesResponse<PR_ADeliveryMode> getDeliveryModes(
            @QueryMap Map<String, String> params
    );


    @PATCH(SHOP_VIEW_API_PREFIX + "/" + PR_ACustomer.API_RESOURCE_NAME + "/{customer_id}/" + PR_APayment.API_RESOURCE_NAME + "/{payment_id}/" + "delivery_items" + "/{delivery_item_id}/" + PR_ADelivery.API_RESOURCE_NAME + "/")
    ResourceResponse<PR_ADelivery> updateDeliveryMode(
            @Path("customer_id") String customerId,
            @Path("payment_id") String paymentId,
            @Path("delivery_item_id") String deliveryItemId,
            @Body PR_ADelivery delivery
    );

    //CUSTOMER OFFERS
    @POST(SHOP_VIEW_API_PREFIX_V21 + "/" + PR_ACustomer.API_RESOURCE_NAME + "/{customer_id}/" + "cart/" + PR_ABasketOffer.API_RESOURCE_NAME + "/")
    ResourceResponse<PR_ABasketOffer> activeCustomerOffer(
            @Path("customer_id") String customerId,
            @Body PR_ABasketOffer basketOffer
    );

    @DELETE(SHOP_VIEW_API_PREFIX_V21 + "/" + PR_ACustomer.API_RESOURCE_NAME + "/{customer_id}/" + "cart/" + PR_ABasketOffer.API_RESOURCE_NAME + "/{id}/")
    ResourceResponse<PR_ABasketOffer> inActiveCustomerOffer(
            @Path("customer_id") String customerId,
            @Path("id") String id
    );


    // CUSTOMER CART
    @GET(SHOP_VIEW_API_PREFIX_V21 + "/" + PR_ACustomer.API_RESOURCE_NAME + "/{customer_id}/cart/" )
    ResourceResponse<PR_ABasket> getCustomerCart(
            @Path("customer_id") String customerId
    );

    @POST(SHOP_VIEW_API_PREFIX + "/" + PR_ACustomer.API_RESOURCE_NAME + "/{customer_id}/cart/" + PR_ABasketItem.API_RESOURCE_NAME + "/")
    ResourceResponse<PR_ABasketItem> addBasketItemToCustomerBasket(
            @Path("customer_id") String customerId,
            @Body PR_ABasketItem basketItem
    );


    @PATCH(SHOP_VIEW_API_PREFIX + "/" + PR_ACustomer.API_RESOURCE_NAME + "/{customer_id}/cart/" + PR_ABasketItem.API_RESOURCE_NAME + "/{basket_item_id}/")
    ResourceResponse<PR_ABasketItem> updateBasketItemInCustomerBasket(
            @Path("customer_id") String customerId,
            @Path("basket_item_id") String basketItemId,
            @Body PR_ABasketItem basketItem
    );

    @DELETE(SHOP_VIEW_API_PREFIX + "/" + PR_ACustomer.API_RESOURCE_NAME + "/{customer_id}/cart/" + PR_ABasketItem.API_RESOURCE_NAME + "/{basket_item_id}/")
    ResourceResponse<PR_ABasketItem> removeBasketItemFromCustomerBasket(
            @Path("customer_id") String customerId,
            @Path("basket_item_id") String basketItemId

    );

    @DELETE(SHOP_VIEW_API_PREFIX_V21 + "/" + PR_ACustomer.API_RESOURCE_NAME + "/{customer_id}/cart/" + PR_ABasketItem.API_RESOURCE_NAME + "/")
    List<PR_ABasketItem> removeBasketItemsFromCustomerBasket(
            @Path("customer_id") String customerId,
            @Query("id") List<String> basketItemIds
    );

    @DELETE(SHOP_VIEW_API_PREFIX_V21 + "/" + PR_ACustomer.API_RESOURCE_NAME + "/{customer_id}/cart/")
    ResourceResponse<PR_ABasket> clearBasket(
            @Path("customer_id") String customerId
    );

    @PATCH(SHOP_VIEW_API_PREFIX_V21 + "/" + PR_ACustomer.API_RESOURCE_NAME + "/{customer_id}/cart/")
    ResourceResponse<PR_ABasket> updateBasketDeliveryFees(
            @Path("customer_id") String customerId,
            @Body PR_ABasket basket
    );

    // SELLER CAR

    @GET(SHOP_VIEW_API_PREFIX_V21 + "/" + PR_ASeller.API_RESOURCE_NAME + "/{seller_id}/cart/")
    ResourceResponse<PR_ABasket> getSellerCart(
            @Path("seller_id") String sellerId
    );

    @POST(SHOP_VIEW_API_PREFIX_V21 + "/" + PR_ASeller.API_RESOURCE_NAME + "/{seller_id}/cart/" + PR_ABasketItem.API_RESOURCE_NAME + "/")
    ResourceResponse<PR_ABasketItem> addBasketItemToSellerBasket(
            @Path("seller_id") String sellerId,
            @Body PR_ABasketItem basketItem
    );


    @PATCH(SHOP_VIEW_API_PREFIX_V21 + "/" + PR_ASeller.API_RESOURCE_NAME + "/{seller_id}/cart/" + PR_ABasketItem.API_RESOURCE_NAME + "/{basket_item_id}/")
    ResourceResponse<PR_ABasketItem> updateBasketItemInSellerBasket(
            @Path("seller_id") String sellerId,
            @Path("basket_item_id") String basketItemId,
            @Body PR_ABasketItem basketItem
    );

    @DELETE(SHOP_VIEW_API_PREFIX_V21 + "/" + PR_ASeller.API_RESOURCE_NAME + "/{seller_id}/cart/" + PR_ABasketItem.API_RESOURCE_NAME + "/{basket_item_id}/")
    ResourceResponse<PR_ABasketItem> removeBasketItemFromSellerBasket(
            @Path("seller_id") String sellerId,
            @Path("basket_item_id") String basketItemId
    );

    @DELETE(SHOP_VIEW_API_PREFIX_V21 + "/" + PR_ASeller.API_RESOURCE_NAME + "/{seller_id}/cart/" + PR_ABasketItem.API_RESOURCE_NAME + "/")
    List<PR_ABasketItem> removeBasketItemsFromSellerBasket(
            @Path("seller_id") String customerId,
            @Query("id") List<String> basketItemIds
    );


    @DELETE(SHOP_VIEW_API_PREFIX_V21 + "/" + PR_ASeller.API_RESOURCE_NAME + "/{seller_id}/cart/")
    ResourceResponse<PR_ABasket> clearSellerBasket(
            @Path("seller_id") String sellerId
    );

    @POST(SHOP_VIEW_API_PREFIX_V21 + "/" + PR_ACustomer.API_RESOURCE_NAME + "/{customer_id}/cart/")
    ResourceResponse<PR_ABasket> linkSellerBasketToCustomer(
            @Path("customer_id") String customerId,
            @Query("from_seller_id") String sellerId,
            @QueryMap Map<String, String> params,
            @Body PR_ABasket basket
    );

    // !END BASKET

    // Shipping address
    @POST(SHOP_VIEW_API_PREFIX + "/" + PR_AProductReview.API_RESOURCE_NAME + "/")
    ResourceResponse<PR_AProductReview> addProductReview(
            @Body PR_AProductReview productReview
    );

    @GET(SHOP_VIEW_API_PREFIX + "/" + PR_AProductReview.API_RESOURCE_NAME + "/")
    PaginatedResourcesResponse<PR_AProductReview> getProductReviews(
            @QueryMap Map<String, String> params
    );

    @PUT(SHOP_VIEW_API_PREFIX + "/" + PR_AProductReview.API_RESOURCE_NAME + "/{review_id}/")
    ResourceResponse<PR_AProductReview> updateProductReview(
            @Path("review_id") String reviewId,
            @Body PR_AProductReview productReview
    );

    @GET(SHOP_VIEW_API_PREFIX + "/" + PR_AProductReviewAttribute.API_RESOURCE_NAME + "/")
    PaginatedResourcesResponse<PR_AProductReviewAttribute> getProductReviewAttributes(
            @QueryMap Map<String, String> params
    );

    @GET(SHOP_VIEW_API_PREFIX + "/" + PR_ABarCodeInfo.API_RESOURCE_NAME + "/{barcode}/")
    ResourceResponse<PR_ABarCodeInfo> getCorrespondence(@Path("barcode") String barcode);

    @GET(SHOP_VIEW_API_PREFIX + "/" + PR_AEvent.API_RESOURCE_NAME + "/")
    PaginatedResourcesResponse<PR_AEvent> getEvents(@Query("seller_id") String cid,
                                                    @Query("after") String after,
                                                    @Query("before") String before,
                                                    @Query("attendee__id") String attendeeId,
                                                    @Query("attendee__type") String attendeeType);

    @GET(SHOP_VIEW_API_PREFIX_V21 + "/" + PR_AOffer.API_RESOURCE_NAME + "/")
    PaginatedResourcesResponse<PR_AOffer> getOffers(@QueryMap Map<String, String> params);

    @POST(SHOP_VIEW_API_PREFIX + "/" + ActionEvent.API_RESOURCE_NAME + "/")
    List<ActionEvent> saveActionEvents(@Body List<ActionEvent> request);

    @GET(SHOP_VIEW_API_PREFIX + "/" + PR_AFee.API_RESOURCE_NAME + "/")
    PaginatedResourcesResponse<PR_AFee> getFees(@QueryMap Map<String, String> params);

    @GET(SHOP_VIEW_API_PREFIX + "/shopstatistics/")
    ShopStatisticsResponse getShopStatistics();

    @POST(SHOP_VIEW_API_PREFIX + "/" + PR_APaymentShare.API_RESOURCE_NAME + "/")
    ResourceResponse<PR_APaymentShare> createPaymentShare(
            @Body PR_APaymentShare paymentShare
    );

    // bug and suggestion
    @POST(SHOP_VIEW_API_PREFIX_V21 + "/" + PR_AFeedback.API_RESOURCE_NAME + "/")
    ResourceResponse<PR_AFeedback> sendFeedback(
            @Body PR_AFeedback feedback
    );


    // START QUOTATION

    @POST(SHOP_VIEW_API_PREFIX_V21 + "/" + PR_AQuotation.API_RESOURCE_NAME + "/")
    ResourceResponse<PR_AQuotation> createQuotation(
            @Body PR_AQuotation request
    );

    @GET(SHOP_VIEW_API_PREFIX_V21 + "/" + PR_AQuotation.API_RESOURCE_NAME + "/")
    PaginatedResourcesResponse<PR_AQuotation> getQuotations(@QueryMap Map<String, String> params);

    //Quotation CART
    @POST(SHOP_VIEW_API_PREFIX_V21 + "/quotations/{quotation_id}/cart/" + PR_ABasketItem.API_RESOURCE_NAME + "/")
    ResourceResponse<PR_ABasketItem> addProductToQuotation(
            @Path("quotation_id") String quotationId,
            @Body PR_ABasketItem pr_aBasketItem
    );

    @PATCH(SHOP_VIEW_API_PREFIX_V21 + "/" + PR_AQuotation.API_RESOURCE_NAME + "/{quotation_id}/")
    ResourceResponse<PR_AQuotation> updateQuotation(
            @Path("quotation_id") String quotationId,
            @Body PR_AQuotation quotation
    );

    @PATCH(SHOP_VIEW_API_PREFIX_V21 + "/" + PR_AQuotation.API_RESOURCE_NAME + "/{quotation_id}/cart/" + PR_ABasketItem.API_RESOURCE_NAME + "/{basket_item_id}/")
    ResourceResponse<PR_ABasketItem> updateBasketItemInQuotationBasket(
            @Path("quotation_id") String quotationId,
            @Path("basket_item_id") String basketItemId,
            @Body PR_ABasketItem basketItem
    );

    @GET(SHOP_VIEW_API_PREFIX_V21 + "/" + PR_AQuotation.API_RESOURCE_NAME + "/{quotation_id}/cart/" )
    ResourceResponse<PR_ABasket> getQuotationCart(
            @Path("quotation_id") String quotationId
    );

    @DELETE(SHOP_VIEW_API_PREFIX_V21 + "/" + PR_AQuotation.API_RESOURCE_NAME + "/{quotation_id}/cart/")
    ResourceResponse<PR_ABasket> clearQuotationBasket(
            @Path("quotation_id") String quotation_id
    );

    @DELETE(SHOP_VIEW_API_PREFIX_V21 + "/" + PR_AQuotation.API_RESOURCE_NAME + "/{quotation_id}/cart/" + PR_ABasketItem.API_RESOURCE_NAME + "/{basket_item_id}/")
    ResourceResponse<PR_ABasketItem> removeBasketItemFromQuotationBasket(
            @Path("quotation_id") String quotationId,
            @Path("basket_item_id") String basketItemId

    );

    @DELETE(SHOP_VIEW_API_PREFIX_V21 + "/" + PR_AQuotation.API_RESOURCE_NAME + "/{quotation_id}/cart/" + PR_ABasketItem.API_RESOURCE_NAME + "/")
    List<PR_ABasketItem> removeBasketItemsFromQuotationBasket(
            @Path("quotation_id") String quotationId,
            @Query("id") List<String> basketItemIds
    );

    @GET("/data-out/")
    ResourceResponse<PR_AData> getDataOut();

    @POST("/data-in/")
    List<Contact> sendDataIn(@Body List<Contact> contacts);

//END QUOTATION

}
