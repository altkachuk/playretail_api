package ninja.cyplay.com.apilibrary.domain.interactor;

import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.List;

import ninja.cyplay.com.apilibrary.domain.executor.InteractorExecutor;
import ninja.cyplay.com.apilibrary.domain.executor.MainThreadExecutor;
import ninja.cyplay.com.apilibrary.domain.interactor.callback.ResourceRequestCallback;
import ninja.cyplay.com.apilibrary.domain.repository.PlayRetailRepository;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.exception.BaseException;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.requests.ResourceRequest;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.requests.ValidateCartRequest;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.responses.ResourceResponse;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ABasket;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ABasketComment;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ABasketItem;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_APayment;
import ninja.cyplay.com.apilibrary.utils.LogUtils;

/**
 * Created by damien on 28/05/15.
 */
public class BasketInteractorImpl extends AbstractInteractor implements BasketInteractor {

    private PlayRetailRepository repository;

    public BasketInteractorImpl(InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor, PlayRetailRepository repository) {
        super(interactorExecutor, mainThreadExecutor);
        this.repository = repository;
    }


    // -------------------------------------------------------------------------------------------
    //
    // -------------------------------------------------------------------------------------------


    @Override
    public void addBasketItemToCustomerBasket(String customerId, PR_ABasketItem basketItem, final ResourceRequestCallback<PR_ABasketItem> callback) {
        final ResourceRequest addBasketItemToCustomerBasketRequest = new ResourceRequest().id(customerId).body(basketItem);

        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                if (callback != null && addBasketItemToCustomerBasketRequest != null) {
                    BasketInteractorImpl.this.doGetResource(addBasketItemToCustomerBasketRequest,
                            callback,
                            new ResourceGetter() {
                                @Override
                                public ResourceResponse getResource(ResourceRequest resourceRequest) {
                                    return repository.addBasketItemToCustomerBasket(addBasketItemToCustomerBasketRequest);
                                }
                            });
                }

            }
        });
    }

    @Override
    public <Item extends PR_ABasketItem> void addBasketItemsToCustomerBasket(String customerId, List<Item> basketItems, final ResourceRequestCallback<List<PR_ABasketItem>> callback) {
        ;
    }

    @Override
    public void updateBasketItemInCustomerBasket(String customerId, PR_ABasketItem basketItem, final ResourceRequestCallback<PR_ABasketItem> callback) {
        final WeakReference<ResourceRequestCallback<PR_ABasketItem>> updateBasketItemInCustomerBasketCallback = new WeakReference<ResourceRequestCallback<PR_ABasketItem>>(callback);
        final WeakReference<ResourceRequest> updateBasketItemInCustomerBasketRequest = new WeakReference<ResourceRequest>(new ResourceRequest().id(customerId).secondLevelId(basketItem.getId()).body(basketItem));
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {

                BasketInteractorImpl.this.doGetResource(updateBasketItemInCustomerBasketRequest.get(),
                        updateBasketItemInCustomerBasketCallback.get(),
                        new ResourceGetter() {
                            @Override
                            public ResourceResponse getResource(ResourceRequest resourceRequest) {
                                return repository.updateBasketItemInCustomerBasket(updateBasketItemInCustomerBasketRequest.get());
                            }
                        });


            }
        });
    }


    @Override
    public void removeBasketItemFromCustomerBasket(String customerId, PR_ABasketItem basketItem, final ResourceRequestCallback<PR_ABasketItem> callback) {
        final WeakReference<ResourceRequestCallback<PR_ABasketItem>> removeBasketItemFromCustomerBasketCallback = new WeakReference<ResourceRequestCallback<PR_ABasketItem>>(callback);
        final WeakReference<ResourceRequest> removeBasketItemFromCustomerBasketRequest = new WeakReference<ResourceRequest>(new ResourceRequest().id(customerId).secondLevelId(basketItem.getId()).body(basketItem));
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {

                BasketInteractorImpl.this.doGetResource(removeBasketItemFromCustomerBasketRequest.get(),
                        removeBasketItemFromCustomerBasketCallback.get(),
                        new ResourceGetter() {
                            @Override
                            public ResourceResponse getResource(ResourceRequest resourceRequest) {
                                return repository.removeBasketItemFromCustomerBasket(removeBasketItemFromCustomerBasketRequest.get());
                            }
                        });

            }
        });
    }

    @Override
    public void removeBasketItemsFromCustomerBasket(String customerId, List<String> basketItemIds, final ResourceRequestCallback<List<PR_ABasketItem>> callback) {
        final WeakReference<ResourceRequestCallback<List<PR_ABasketItem>>> removeBasketItemFromCustomerBasketCallback = new WeakReference<ResourceRequestCallback<List<PR_ABasketItem>>>(callback);
        final WeakReference<ResourceRequest> removeBasketItemFromCustomerBasketRequest = new WeakReference<ResourceRequest>(new ResourceRequest().id(customerId).filter("id", basketItemIds));
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {

                try {
                    final List<PR_ABasketItem> response = repository.removeBasketItemsFromCustomerBasket(removeBasketItemFromCustomerBasketRequest.get());
                    getMainThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (removeBasketItemFromCustomerBasketCallback.get() != null) {
                                removeBasketItemFromCustomerBasketCallback.get().onSuccess(response);
                            }
                        }
                    });
                } catch (final BaseException e) {
                    getMainThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (removeBasketItemFromCustomerBasketCallback.get() != null) {
                                removeBasketItemFromCustomerBasketCallback.get().onError(e);
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void clearBasket(String customerId, ResourceRequestCallback<PR_ABasket> callback) {
        final WeakReference<ResourceRequestCallback<PR_ABasket>> clearBasketCallback = new WeakReference<ResourceRequestCallback<PR_ABasket>>(callback);
        final WeakReference<ResourceRequest> clearBasketRequest = new WeakReference<ResourceRequest>(new ResourceRequest().id(customerId));
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {

                BasketInteractorImpl.this.doGetResource(clearBasketRequest.get(),
                        clearBasketCallback.get(),
                        new ResourceGetter() {
                            @Override
                            public ResourceResponse getResource(ResourceRequest resourceRequest) {
                                return repository.clearBasket(clearBasketRequest.get());
                            }
                        });

            }
        });
    }

    @Override
    public void updateBasketDeliveryFees(String customerId, PR_ABasket basket, ResourceRequestCallback<PR_ABasket> callback) {
        final WeakReference<ResourceRequestCallback<PR_ABasket>> updateBasketDeliveryFeesCallback = new WeakReference<ResourceRequestCallback<PR_ABasket>>(callback);
        final WeakReference<ResourceRequest> updateBasketDeliveryFeesRequest = new WeakReference<ResourceRequest>(new ResourceRequest().id(customerId).body(basket));
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {

                BasketInteractorImpl.this.doGetResource(updateBasketDeliveryFeesRequest.get(),
                        updateBasketDeliveryFeesCallback.get(),
                        new ResourceGetter() {
                            @Override
                            public ResourceResponse getResource(ResourceRequest resourceRequest) {
                                return repository.updateBasketDeliveryFees(updateBasketDeliveryFeesRequest.get());
                            }
                        });

            }
        });
    }


    //Seller Basket

    @Override
    public void addBasketItemToSellerBasket(String sellerId, PR_ABasketItem basketItem, final ResourceRequestCallback<PR_ABasketItem> callback) {
        final ResourceRequest addBasketItemToSellerBasketRequest = new ResourceRequest().id(sellerId).body(basketItem);


        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                if (callback != null && addBasketItemToSellerBasketRequest != null) {
                    BasketInteractorImpl.this.doGetResource(addBasketItemToSellerBasketRequest,
                            callback,
                            new ResourceGetter() {
                                @Override
                                public ResourceResponse getResource(ResourceRequest resourceRequest) {
                                    return repository.addBasketItemToSellerBasket(addBasketItemToSellerBasketRequest);
                                }
                            });
                }

            }
        });
    }

    @Override
    public void updateBasketItemInSellerBasket(String sellerId, PR_ABasketItem basketItem, ResourceRequestCallback<PR_ABasketItem> callback) {
        final WeakReference<ResourceRequestCallback<PR_ABasketItem>> updateBasketItemInSellerBasketCallback = new WeakReference<ResourceRequestCallback<PR_ABasketItem>>(callback);
        final WeakReference<ResourceRequest> updateBasketItemInSellerBasketRequest = new WeakReference<ResourceRequest>(new ResourceRequest().id(sellerId).secondLevelId(basketItem.getId()).body(basketItem));
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {

                BasketInteractorImpl.this.doGetResource(updateBasketItemInSellerBasketRequest.get(),
                        updateBasketItemInSellerBasketCallback.get(),
                        new ResourceGetter() {
                            @Override
                            public ResourceResponse getResource(ResourceRequest resourceRequest) {
                                return repository.updateBasketItemInSellerBasket(updateBasketItemInSellerBasketRequest.get());
                            }
                        });


            }
        });
    }

    @Override
    public void removeBasketItemFromSellerBasket(String sellerId, PR_ABasketItem basketItem, ResourceRequestCallback<PR_ABasketItem> callback) {
        final WeakReference<ResourceRequestCallback<PR_ABasketItem>> removeBasketItemFromSellerBasketCallback = new WeakReference<ResourceRequestCallback<PR_ABasketItem>>(callback);
        final WeakReference<ResourceRequest> removeBasketItemFromSellerBasketRequest = new WeakReference<ResourceRequest>(new ResourceRequest().id(sellerId).secondLevelId(basketItem.getId()).body(basketItem));
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {

                BasketInteractorImpl.this.doGetResource(removeBasketItemFromSellerBasketRequest.get(),
                        removeBasketItemFromSellerBasketCallback.get(),
                        new ResourceGetter() {
                            @Override
                            public ResourceResponse getResource(ResourceRequest resourceRequest) {
                                return repository.removeBasketItemFromSellerBasket(removeBasketItemFromSellerBasketRequest.get());
                            }
                        });

            }
        });
    }


    @Override
    public void removeBasketItemsFromSellerBasket(String customerId, List<String> basketItemIds, final ResourceRequestCallback<List<PR_ABasketItem>> callback) {
        final WeakReference<ResourceRequestCallback<List<PR_ABasketItem>>> removeBasketItemsFromSellerBasketCallback = new WeakReference<ResourceRequestCallback<List<PR_ABasketItem>>>(callback);
        final WeakReference<ResourceRequest> removeBasketItemFromCustomerBasketRequest = new WeakReference<ResourceRequest>(new ResourceRequest().id(customerId).filter("id", basketItemIds));
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                try {
                    final List<PR_ABasketItem> response = repository.removeBasketItemsFromSellerBasket(removeBasketItemFromCustomerBasketRequest.get());
                    getMainThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (removeBasketItemsFromSellerBasketCallback.get() != null) {
                                removeBasketItemsFromSellerBasketCallback.get().onSuccess(response);
                            }
                        }
                    });
                } catch (final BaseException e) {
                    getMainThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (removeBasketItemsFromSellerBasketCallback.get() != null) {
                                removeBasketItemsFromSellerBasketCallback.get().onError(e);
                            }
                        }
                    });
                }
            }
        });
    }


    @Override
    public void clearSellerBasket(String sellerId, ResourceRequestCallback<PR_ABasket> callback) {
        final WeakReference<ResourceRequestCallback<PR_ABasket>> clearBasketCallback = new WeakReference<ResourceRequestCallback<PR_ABasket>>(callback);
        final WeakReference<ResourceRequest> clearBasketRequest = new WeakReference<ResourceRequest>(new ResourceRequest().id(sellerId));
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {

                BasketInteractorImpl.this.doGetResource(clearBasketRequest.get(),
                        clearBasketCallback.get(),
                        new ResourceGetter() {
                            @Override
                            public ResourceResponse getResource(ResourceRequest resourceRequest) {
                                return repository.clearSellerBasket(clearBasketRequest.get());
                            }
                        });

            }
        });
    }

    @Override
    public void linkSellerBasketToCustomer(String customerId, String sellerId, List<String> project, ResourceRequestCallback<PR_ABasket> callback) {
        final WeakReference<ResourceRequestCallback<PR_ABasket>> linkSellerBasketToCustomerCallback = new WeakReference<ResourceRequestCallback<PR_ABasket>>(callback);
        final WeakReference<ResourceRequest> linkSellerBasketToCustomerRequest = new WeakReference<ResourceRequest>(new ResourceRequest().project(project).id(customerId).secondLevelId(sellerId).body(new PR_ABasket()));
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {

                BasketInteractorImpl.this.doGetResource(linkSellerBasketToCustomerRequest.get(),
                        linkSellerBasketToCustomerCallback.get(),
                        new ResourceGetter() {
                            @Override
                            public ResourceResponse getResource(ResourceRequest resourceRequest) {
                                return repository.linkSellerBasketToCustomer(linkSellerBasketToCustomerRequest.get());
                            }
                        });

            }
        });
    }


    // -------------------------------------------------------------------------------------------
    //
    // -------------------------------------------------------------------------------------------

    @Override
    public void getSellerCart(String sellerUserName, final ResourceRequestCallback<PR_ABasket> callback) {
        final ResourceRequest sellerBasketRequest = new ResourceRequest().id(sellerUserName);
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                BasketInteractorImpl.super.doGetResource(sellerBasketRequest,
                        callback,
                        new ResourceGetter() {
                            @Override
                            public ResourceResponse getResource(ResourceRequest resourceRequest) {
                                return repository.getSellerCart(sellerBasketRequest);
                            }
                        });
            }
        });
    }

    // -------------------------------------------------------------------------------------------
    //
    // -------------------------------------------------------------------------------------------

    @Override
    public void getCustomerCart(String customerId, final ResourceRequestCallback<PR_ABasket> callback) {
        final ResourceRequest customerBasketRequest = new ResourceRequest().id(customerId);
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                BasketInteractorImpl.super.doGetResource(customerBasketRequest,
                        callback,
                        new ResourceGetter() {
                            @Override
                            public ResourceResponse getResource(ResourceRequest resourceRequest) {
                                return repository.getCustomerCart(customerBasketRequest);
                            }
                        });
            }
        });
    }

    // -------------------------------------------------------------------------------------------
    // VALIDATE CART
    // -------------------------------------------------------------------------------------------

    @Override
    public void validateCart(String customerId, PR_ABasketComment basket, final ResourceRequestCallback<Void> callback) {
        final WeakReference<ResourceRequest> validateCartRequest = new WeakReference<ResourceRequest>(new ResourceRequest().id(customerId).body(basket));

        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                try {
                    repository.validateCart(validateCartRequest.get());
                    getMainThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (callback != null) {
                                callback.onSuccess(null);
                            } else {
                                Log.i(BasketInteractorImpl.class.getName(), "Request callback is None");
                            }
                        }
                    });
                } catch (final BaseException e) {
                    Log.e(LogUtils.generateTag(this), "Error on validateCart interactor");
                    getMainThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (callback != null) {
                                callback.onError(e);
                            } else {
                                Log.i(SellerInteractorImpl.class.getName(), "Request callback is None");
                            }
                        }
                    });
                }


            }
        });
    }

}