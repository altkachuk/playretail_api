package ninja.cyplay.com.apilibrary.domain.interactor;

import android.content.Context;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;

import ninja.cyplay.com.apilibrary.domain.executor.InteractorExecutor;
import ninja.cyplay.com.apilibrary.domain.executor.MainThreadExecutor;
import ninja.cyplay.com.apilibrary.domain.interactor.callback.PaginatedResourceRequestCallback;
import ninja.cyplay.com.apilibrary.domain.interactor.callback.ResourceRequestCallback;
import ninja.cyplay.com.apilibrary.domain.repository.PlayRetailRepository;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.exception.BaseException;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.requests.ResourceRequest;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.responses.PaginatedResourcesResponse;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.responses.ResourceResponse;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ABasket;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ABasketItem;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AQuotation;

/**
 * Created by wentongwang on 07/07/2017.
 */

public class GetQuotationsInteractorImpl extends AbstractInteractor implements GetQuotationsInteractor {
    private PlayRetailRepository repository;
    private Context context;

    public GetQuotationsInteractorImpl(Context context, InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor, PlayRetailRepository repository) {
        super(interactorExecutor, mainThreadExecutor);
        this.context = context;
        this.repository = repository;
    }

    @Override
    public void getQuotationsByCustomer(HashMap<String, String> filters, List<String> project, List<String> sortBys, PaginatedResourceRequestCallback<PR_AQuotation> callback) {
        final WeakReference<ResourceRequest> getQuotationsRequest = new WeakReference<>(new ResourceRequest().paginate(false).project(project).sort(sortBys).filter(filters));
        final PaginatedResourceRequestCallback<PR_AQuotation> getQuotationsRequestCallback = callback;
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                GetQuotationsInteractorImpl.this.doGetPaginatedResource(getQuotationsRequest.get(),
                        getQuotationsRequestCallback,
                        new PaginatedResourceGetter() {
                            @Override
                            public PaginatedResourcesResponse getPaginatedResource(ResourceRequest resourceRequest) {
                                return repository.getQuotations(getQuotationsRequest.get());
                            }
                        }
                );
            }
        });
    }

    @Override
    public void addBasketItemToQuotationBasket(String quotationId, PR_ABasketItem basketItem, final ResourceRequestCallback<PR_ABasketItem> callback) {
        final ResourceRequest addBasketItemToCustomerBasketRequest = new ResourceRequest().id(quotationId).body(basketItem);

        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                if (callback != null && addBasketItemToCustomerBasketRequest != null) {
                    GetQuotationsInteractorImpl.this.doGetResource(addBasketItemToCustomerBasketRequest,
                            callback,
                            new ResourceGetter() {
                                @Override
                                public ResourceResponse getResource(ResourceRequest resourceRequest) {
                                    return repository.addProductToQuotation(addBasketItemToCustomerBasketRequest);
                                }
                            });
                }

            }
        });
    }

    @Override
    public void updateQuotation(String quotationId, PR_AQuotation quotation, ResourceRequestCallback<PR_AQuotation> callback) {
        final WeakReference<ResourceRequestCallback<PR_AQuotation>> updateQuotationCallback = new WeakReference<>(callback);
        final WeakReference<ResourceRequest> updateQuotationRequest = new WeakReference<ResourceRequest>(new ResourceRequest().id(quotationId).body(quotation));
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {

                GetQuotationsInteractorImpl.this.doGetResource(updateQuotationRequest.get(),
                        updateQuotationCallback.get(),
                        new ResourceGetter() {
                            @Override
                            public ResourceResponse getResource(ResourceRequest resourceRequest) {
                                return repository.updateQuotation(updateQuotationRequest.get());
                            }
                        });

            }
        });
    }

    @Override
    public void updateBasketItemInQuotationBasket(String quotationId, PR_ABasketItem basketItem, final ResourceRequestCallback<PR_ABasketItem> callback) {
        final WeakReference<ResourceRequestCallback<PR_ABasketItem>> updateBasketItemInQuotationBasket = new WeakReference<>(callback);
        final WeakReference<ResourceRequest> updateBasketItemInCustomerQuotationRequest = new WeakReference<>(new ResourceRequest().id(quotationId).secondLevelId(basketItem.getId()).body(basketItem));

        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {

                GetQuotationsInteractorImpl.this.doGetResource(updateBasketItemInCustomerQuotationRequest.get(),
                        updateBasketItemInQuotationBasket.get(),
                        new ResourceGetter() {
                            @Override
                            public ResourceResponse getResource(ResourceRequest resourceRequest) {
                                return repository.updateBasketItemInQuotationBasket(updateBasketItemInCustomerQuotationRequest.get());
                            }
                        });


            }
        });
    }


    @Override
    public void getQuotationCart(String quotationId, final ResourceRequestCallback<PR_ABasket> callback) {
        final ResourceRequest customerBasketRequest = new ResourceRequest().id(quotationId);
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                GetQuotationsInteractorImpl.super.doGetResource(customerBasketRequest,
                        callback,
                        new ResourceGetter() {
                            @Override
                            public ResourceResponse getResource(ResourceRequest resourceRequest) {
                                return repository.getQuotationCart(customerBasketRequest);
                            }
                        });
            }
        });
    }

    @Override
    public void clearBasket(String quotationId, ResourceRequestCallback<PR_ABasket> callback) {
        final WeakReference<ResourceRequestCallback<PR_ABasket>> clearBasketCallback = new WeakReference<>(callback);
        final WeakReference<ResourceRequest> clearBasketRequest = new WeakReference<>(new ResourceRequest().id(quotationId));
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                GetQuotationsInteractorImpl.this.doGetResource(clearBasketRequest.get(),
                        clearBasketCallback.get(),
                        new ResourceGetter() {
                            @Override
                            public ResourceResponse getResource(ResourceRequest resourceRequest) {
                                return repository.clearQuotationBasket(clearBasketRequest.get());
                            }
                        });
            }
        });
    }

    @Override
    public void removeBasketItemsFromCustomerBasket(String quotationId, List<String> basketItemIds, ResourceRequestCallback<List<PR_ABasketItem>> callback) {
        final WeakReference<ResourceRequestCallback<List<PR_ABasketItem>>> removeBasketItemFromCustomerBasketCallback = new WeakReference<ResourceRequestCallback<List<PR_ABasketItem>>>(callback);
        final WeakReference<ResourceRequest> removeBasketItemFromCustomerBasketRequest = new WeakReference<ResourceRequest>(new ResourceRequest().id(quotationId).filter("id", basketItemIds));
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {

                try {
                    final List<PR_ABasketItem> response = repository.removeBasketItemsFromQuotationBasket(removeBasketItemFromCustomerBasketRequest.get());
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
    public void removeBasketItemFromQuotationBasket(String quotationId, PR_ABasketItem basketItem, ResourceRequestCallback<PR_ABasketItem> callback) {
        final WeakReference<ResourceRequestCallback<PR_ABasketItem>> removeBasketItemFromQuotationBasketCallback = new WeakReference<ResourceRequestCallback<PR_ABasketItem>>(callback);
        final WeakReference<ResourceRequest> removeBasketItemFromQuotationBasketRequest = new WeakReference<ResourceRequest>(new ResourceRequest().id(quotationId).secondLevelId(basketItem.getId()).body(basketItem));
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {

                GetQuotationsInteractorImpl.this.doGetResource(removeBasketItemFromQuotationBasketRequest.get(),
                        removeBasketItemFromQuotationBasketCallback.get(),
                        new ResourceGetter() {
                            @Override
                            public ResourceResponse getResource(ResourceRequest resourceRequest) {
                                return repository.removeBasketItemFromQuotationBasket(removeBasketItemFromQuotationBasketRequest.get());
                            }
                        });

            }
        });
    }
}
