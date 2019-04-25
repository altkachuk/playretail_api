package ninja.cyplay.com.apilibrary.domain.interactor;

import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import ninja.cyplay.com.apilibrary.domain.executor.InteractorExecutor;
import ninja.cyplay.com.apilibrary.domain.executor.MainThreadExecutor;
import ninja.cyplay.com.apilibrary.domain.interactor.callback.PaginatedResourceRequestCallback;
import ninja.cyplay.com.apilibrary.domain.interactor.callback.ResourceRequestCallback;
import ninja.cyplay.com.apilibrary.domain.repository.PlayRetailRepository;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.exception.BaseException;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.exception.ExceptionType;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.requests.ResourceRequest;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.responses.PaginatedResourcesResponse;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.responses.ResourceResponse;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ACustomer;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ATicket;
import ninja.cyplay.com.apilibrary.utils.LogUtils;

/**
 * Created by damien on 22/05/15.
 */
public class CustomerInteractorImpl extends AbstractInteractor implements CustomerInteractor {

    private PlayRetailRepository repository;

    private final static List<String> CUSTOMER_SEARCH_PROJECTION = Arrays.asList("id",
            "ean_card",
            "loyalty",
            "details",
            "professional_details");

    public CustomerInteractorImpl(InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor, PlayRetailRepository repository) {
        super(interactorExecutor, mainThreadExecutor);
        this.repository = repository;
    }

    // -------------------------------------------------------------------------------------------
    //
    // -------------------------------------------------------------------------------------------

    @Override
    public void completionFromName(String cname, PaginatedResourceRequestCallback<String> callback) {
        final WeakReference<ResourceRequest> customerCompletionRequest = new WeakReference<ResourceRequest>(new ResourceRequest().search(cname));
        final WeakReference<PaginatedResourceRequestCallback<String>> completionFromNameCallback = new WeakReference<PaginatedResourceRequestCallback<String>>(callback);
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                CustomerInteractorImpl.this.doGetPaginatedResource(customerCompletionRequest.get(),
                        completionFromNameCallback.get(),
                        new PaginatedResourceGetter() {
                            @Override
                            public PaginatedResourcesResponse getPaginatedResource(ResourceRequest resourceRequest) {
                                return repository.customerCompletion(customerCompletionRequest.get());
                            }
                        }
                );
            }
        });
    }

    // -------------------------------------------------------------------------------------------
    //
    // -------------------------------------------------------------------------------------------

    @Override
    public void searchFromName(String cname, PaginatedResourceRequestCallback<PR_ACustomer> callback) {
        final WeakReference<ResourceRequest> customerSearchRequest = new WeakReference<ResourceRequest>(new ResourceRequest().search(cname).project(CUSTOMER_SEARCH_PROJECTION).paginate(false));
        final WeakReference<PaginatedResourceRequestCallback<PR_ACustomer>> searchFromNameCallback = new WeakReference<PaginatedResourceRequestCallback<PR_ACustomer>>(callback);
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                CustomerInteractorImpl.this.doGetPaginatedResource(customerSearchRequest.get(),
                        searchFromNameCallback.get(),
                        new PaginatedResourceGetter() {
                            @Override
                            public PaginatedResourcesResponse getPaginatedResource(ResourceRequest resourceRequest) {
                                return repository.customerSearch(customerSearchRequest.get());
                            }
                        });
            }
        });
    }

    @Override
    public void searchFromFirstNameLastNameZipCode(String firstName,
                                                   String lastName,
                                                   String zipCode,
                                                   PaginatedResourceRequestCallback<PR_ACustomer> callback) {
        final WeakReference<ResourceRequest> customerSearchRequest = new WeakReference<ResourceRequest>(new ResourceRequest().filter("details__first_name", firstName)
                .filter("details__last_name", lastName)
                .filter("details__mail__zipcode", zipCode)
                .project(CUSTOMER_SEARCH_PROJECTION));
        final WeakReference<PaginatedResourceRequestCallback<PR_ACustomer>> searchFromNameCallback = new WeakReference<PaginatedResourceRequestCallback<PR_ACustomer>>(callback);
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                CustomerInteractorImpl.this.doGetPaginatedResource(customerSearchRequest.get(),
                        searchFromNameCallback.get(),
                        new PaginatedResourceGetter() {
                            @Override
                            public PaginatedResourcesResponse getPaginatedResource(ResourceRequest resourceRequest) {
                                return repository.customerSearch(customerSearchRequest.get());
                            }
                        });
            }
        });
    }

    @Override
    public void search(Map<String, String> parameters, PaginatedResourceRequestCallback<PR_ACustomer> callback) {
        final WeakReference<ResourceRequest> customerSearchRequest = new WeakReference<ResourceRequest>(new ResourceRequest().filter(parameters).project(CUSTOMER_SEARCH_PROJECTION));
        final WeakReference<PaginatedResourceRequestCallback<PR_ACustomer>> searchFromNameCallback = new WeakReference<PaginatedResourceRequestCallback<PR_ACustomer>>(callback);
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                CustomerInteractorImpl.this.doGetPaginatedResource(customerSearchRequest.get(),
                        searchFromNameCallback.get(),
                        new PaginatedResourceGetter() {
                            @Override
                            public PaginatedResourcesResponse getPaginatedResource(ResourceRequest resourceRequest) {
                                return repository.customerSearch(customerSearchRequest.get());
                            }
                        });
            }
        });
    }

    // -------------------------------------------------------------------------------------------
    //
    // -------------------------------------------------------------------------------------------

    @Override
    public void getUpdatedCustomers(final PaginatedResourceRequestCallback<PR_ACustomer> callback) {

    }

    // -------------------------------------------------------------------------------------------
    //
    // -------------------------------------------------------------------------------------------

    @Override
    public void getCustomerInfo(String id, List<String> projection, ResourceRequestCallback<PR_ACustomer> callback) {
        final WeakReference<ResourceRequest> getCustomerInfoRequest = new WeakReference<ResourceRequest>(new ResourceRequest().id(id).project(projection));
        final WeakReference<ResourceRequestCallback<PR_ACustomer>> getCustomerInfoCallback = new WeakReference<ResourceRequestCallback<PR_ACustomer>>(callback);
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                CustomerInteractorImpl.this.doGetResource(getCustomerInfoRequest.get(),
                        getCustomerInfoCallback.get(),
                        new ResourceGetter() {
                            @Override
                            public ResourceResponse getResource(ResourceRequest resourceRequest) {
                                return repository.getCustomerInfo(getCustomerInfoRequest.get());
                            }
                        });
            }
        });

    }

    // -------------------------------------------------------------------------------------------
    //
    // -------------------------------------------------------------------------------------------

    public void getCustomerInfoFromEAN(String ean, final ResourceRequestCallback<PR_ACustomer> callback) {
        List<String> projection = new ArrayList<>();
        projection.add("id");
        final WeakReference<ResourceRequest> getCustomerFromEanRequest = new WeakReference<ResourceRequest>(new ResourceRequest().filter("ean_card", ean).project(projection));
        final WeakReference<ResourceRequestCallback<PR_ACustomer>> getCustomerFromEanCallback = new WeakReference<ResourceRequestCallback<PR_ACustomer>>(callback);
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                try {
                    final PaginatedResourcesResponse<PR_ACustomer> getCustomerResponse = repository.getCustomers(getCustomerFromEanRequest.get());
                    getMainThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (getCustomerResponse.getResults() != null && getCustomerResponse.getResults().size() > 0) {
                                if (getCustomerFromEanCallback.get() != null) {
                                    getCustomerFromEanCallback.get().onSuccess(getCustomerResponse.getResults().get(0));
                                } else {
                                    Log.i(CustomerInteractorImpl.class.getName(), "Request callback is None");
                                }
                            } else {
                                BaseException baseException = new BaseException();
                                baseException.setType(ExceptionType.BUSINESS);
                                if (getCustomerFromEanCallback.get() != null) {
                                    getCustomerFromEanCallback.get().onError(baseException);
                                } else {
                                    Log.i(CustomerInteractorImpl.class.getName(), "Request callback is None");
                                }
                            }
                        }
                    });
                } catch (final BaseException e) {
                    Log.e(LogUtils.generateTag(this), "Error on GetProducts interactor");
                    getMainThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (getCustomerFromEanCallback.get() != null) {
                                getCustomerFromEanCallback.get().onError(e);
                            } else {
                                Log.i(CustomerInteractorImpl.class.getName(), "Request callback is None");
                            }
                        }
                    });
                }
            }
        });
    }

    // -------------------------------------------------------------------------------------------
    //
    // -------------------------------------------------------------------------------------------

    @Override
    public void updateCustomerInfo(String id, PR_ACustomer customer, final ResourceRequestCallback callback) {
        final WeakReference<ResourceRequest<PR_ACustomer>> updateCustomerInfoRequest = new WeakReference<ResourceRequest<PR_ACustomer>>(new ResourceRequest<PR_ACustomer>().id(id).body(customer));
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                try {
                    final ResourceResponse<PR_ACustomer> updateCustomerInfoResponse = repository.saveCustomerInfo(updateCustomerInfoRequest.get());
                    getMainThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (callback != null) {
                                callback.onSuccess(updateCustomerInfoResponse.getResource());
                            } else {
                                Log.i(CustomerInteractorImpl.class.getName(), "Request callback is None");
                            }
                        }
                    });
                } catch (final BaseException e) {
                    Log.e(LogUtils.generateTag(this), "Error on GetCustomerInfoImpl interactor");
                    getMainThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (callback != null) {
                                callback.onError(e);
                            } else {
                                Log.i(CustomerInteractorImpl.class.getName(), "Request callback is None");
                            }
                        }
                    });
                }
            }
        });
    }

    // -------------------------------------------------------------------------------------------
    //
    // -------------------------------------------------------------------------------------------

    @Override
    public void createCustomerInfo(PR_ACustomer customer, final ResourceRequestCallback<PR_ACustomer> callback) {
        final WeakReference<ResourceRequest<PR_ACustomer>> createCustomerInfoRequest = new WeakReference<ResourceRequest<PR_ACustomer>>(new ResourceRequest<PR_ACustomer>().body(customer));
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                try {
                    final ResourceResponse<PR_ACustomer> createCustomerInfoResponse = repository.createCustomerInfo(createCustomerInfoRequest.get());
                    getMainThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (callback != null) {
                                callback.onSuccess(createCustomerInfoResponse.getResource());
                            } else {
                                Log.i(CustomerInteractorImpl.class.getName(), "Request callback is None");
                            }
                        }
                    });
                } catch (final BaseException e) {
                    Log.e(LogUtils.generateTag(this), "Error on GetCustomerInfoImpl interactor");
                    getMainThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (callback != null) {
                                callback.onError(e);
                            } else {
                                Log.i(CustomerInteractorImpl.class.getName(), "Request callback is None");
                            }
                        }
                    });
                }
            }
        });
    }

    // -------------------------------------------------------------------------------------------
    //
    // -------------------------------------------------------------------------------------------

    @Override
    public void getCustomerSalesHistory(String customerId, PaginatedResourceRequestCallback<PR_ATicket> callback) {
        final WeakReference<ResourceRequest> getSalesHistoryRequest = new WeakReference<ResourceRequest>(new ResourceRequest().filter("customer_id", customerId).paginate(false));
        final WeakReference<PaginatedResourceRequestCallback<PR_ATicket>> getCustomerSalesHistoryCallback = new WeakReference<PaginatedResourceRequestCallback<PR_ATicket>>(callback);
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                CustomerInteractorImpl.this.doGetPaginatedResource(getSalesHistoryRequest.get(),
                        getCustomerSalesHistoryCallback.get(),
                        new PaginatedResourceGetter() {
                            @Override
                            public PaginatedResourcesResponse getPaginatedResource(ResourceRequest resourceRequest) {
                                return repository.getSalesHistory(getSalesHistoryRequest.get());
                            }
                        }
                );
            }
        });
    }


}
