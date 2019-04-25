package ninja.cyplay.com.apilibrary.domain.interactor;

import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;

import ninja.cyplay.com.apilibrary.domain.executor.InteractorExecutor;
import ninja.cyplay.com.apilibrary.domain.executor.MainThreadExecutor;
import ninja.cyplay.com.apilibrary.domain.interactor.callback.PaginatedResourceRequestCallback;
import ninja.cyplay.com.apilibrary.domain.interactor.callback.ResourceRequestCallback;
import ninja.cyplay.com.apilibrary.domain.repository.PlayRetailRepository;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.exception.BaseException;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.requests.GetConfigRequest;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.requests.ProvisionDeviceRequestBody;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.requests.ResourceRequest;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.responses.PaginatedResourcesResponse;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.responses.ResourceResponse;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AConfig;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ADevice;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AShop;
import ninja.cyplay.com.apilibrary.utils.LogUtils;

/**
 * Created by damien on 22/05/15.
 */
public class ClientInteractorImpl extends AbstractInteractor implements ClientInteractor {

    private PlayRetailRepository repository;


    public ClientInteractorImpl(InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor, PlayRetailRepository repository) {
        super(interactorExecutor, mainThreadExecutor);
        this.repository = repository;
    }


    // -------------------------------------------------------------------------------------------
    //
    // -------------------------------------------------------------------------------------------


    @Override
    public void getConfig(String udid, String app_version, String shopId, ResourceRequestCallback<PR_AConfig> callback) {
        final ResourceRequestCallback<PR_AConfig> getConfigCallback = callback;
        final GetConfigRequest getConfigRequest = new GetConfigRequest(udid, app_version, shopId);
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                try {
                    final ResourceResponse<PR_AConfig> getConfigResponse = repository.getConfig(getConfigRequest);
                    getMainThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            getConfigCallback.onSuccess(getConfigResponse.getResource());
                        }
                    });
                } catch (final BaseException e) {
                    Log.e(LogUtils.generateTag(this), "Error on GetConfig interactor", e);
                    getMainThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            getConfigCallback.onError(e);
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
    public void getShops(String udid,
                         boolean all,
                         List<String> fields,
                         List<String> sortBys,
                         HashMap<String, String> filters,
                         PaginatedResourceRequestCallback<PR_AShop> callback) {
        final WeakReference<PaginatedResourceRequestCallback<PR_AShop>> getShopsCallback = new WeakReference<>(callback);
        final WeakReference<ResourceRequest> getShopResourcesRequest = new WeakReference<>(new ResourceRequest().paginate(false).project(fields).sort(sortBys).filter(filters));
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {


                ClientInteractorImpl.super.doGetPaginatedResource(getShopResourcesRequest.get(),
                        getShopsCallback.get(),
                        new PaginatedResourceGetter() {
                            @Override
                            public PaginatedResourcesResponse getPaginatedResource(ResourceRequest resourceRequest) {
                                return repository.getShops(getShopResourcesRequest.get());
                            }
                        });
            }
        });
    }

    // -------------------------------------------------------------------------------------------
    //
    // -------------------------------------------------------------------------------------------


    @Override
    public void provisionDevice(String udid, String shopId, ResourceRequestCallback<PR_ADevice> callback) {
        final WeakReference<ResourceRequestCallback<PR_ADevice>> provisionDeviceCallback = new WeakReference<>(callback);
        final WeakReference<ProvisionDeviceRequestBody> provisionDeviceRequest = new WeakReference<>(new ProvisionDeviceRequestBody(udid, shopId));
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                try {
                    final ResourceResponse<PR_ADevice> provisionDeviceResponse = repository.provisionDevice(provisionDeviceRequest.get());
                    getMainThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (provisionDeviceCallback.get() != null) {
                                provisionDeviceCallback.get().onSuccess(provisionDeviceResponse.getResource());
                            } else {
                                Log.i(ClientInteractorImpl.class.getName(), "Request callback is None");
                            }
                        }
                    });
                } catch (final BaseException e) {
                    Log.e(LogUtils.generateTag(this), "Error on provisionDevice interactor");
                    getMainThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (provisionDeviceCallback.get() != null) {
                                provisionDeviceCallback.get().onError(e);
                            } else {
                                Log.i(ClientInteractorImpl.class.getName(), "Request callback is None");
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
    public void updateDeviceStore(String udid, String shopId, final ResourceRequestCallback<PR_ADevice> callback) {
        final WeakReference<ResourceRequestCallback<PR_ADevice>> updateDeviceStoreCallback = new WeakReference<>(callback);
        final WeakReference<ProvisionDeviceRequestBody> updateDeviceStoreRequest = new WeakReference<>(new ProvisionDeviceRequestBody(udid, shopId));
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                try {
                    final ResourceResponse<PR_ADevice> provisionDeviceResponse = repository.updateProvisionDeviceStore(updateDeviceStoreRequest.get());
                    getMainThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (updateDeviceStoreCallback.get() != null) {
                                updateDeviceStoreCallback.get().onSuccess(provisionDeviceResponse.getResource());
                            } else {
                                Log.i(ClientInteractorImpl.class.getName(), "Request callback is None");
                            }
                        }
                    });
                } catch (final BaseException e) {
                    Log.e(LogUtils.generateTag(this), "Error on doUpdateDeviceStore interactor");
                    getMainThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (updateDeviceStoreCallback.get() != null) {
                                updateDeviceStoreCallback.get().onError(e);
                            } else {
                                Log.i(ClientInteractorImpl.class.getName(), "Request callback is None");
                            }
                        }
                    });
                }
            }
        });
    }

}

