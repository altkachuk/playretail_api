package ninja.cyplay.com.apilibrary.domain.interactor;

import android.util.Log;

import java.lang.ref.WeakReference;

import ninja.cyplay.com.apilibrary.domain.executor.InteractorExecutor;
import ninja.cyplay.com.apilibrary.domain.executor.MainThreadExecutor;
import ninja.cyplay.com.apilibrary.domain.interactor.callback.ResourceRequestCallback;
import ninja.cyplay.com.apilibrary.domain.repository.PlayRetailRepository;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.exception.BaseException;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.requests.ResourceRequest;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.responses.ResourceResponse;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AProductShare;
import ninja.cyplay.com.apilibrary.utils.LogUtils;

/**
 * Created by romainlebouc on 24/08/16.
 */
public class ProductShareInteractorImpl extends AbstractInteractor implements ProductShareInteractor {

    private PlayRetailRepository repository;

    public ProductShareInteractorImpl(InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor, PlayRetailRepository retailRepository) {
        super(interactorExecutor, mainThreadExecutor);
        this.repository = retailRepository;
    }

    @Override
    public void shareProducts(PR_AProductShare productShare, ResourceRequestCallback callback) {
        final WeakReference<ResourceRequest<PR_AProductShare>> shareProductsRequest = new WeakReference<ResourceRequest<PR_AProductShare>>(new ResourceRequest().body(productShare));
        final WeakReference<ResourceRequestCallback> shareProductsCallback = new WeakReference<>(callback);
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                try {
                    final ResourceResponse<PR_AProductShare> createUpdateDeleteResourceResponse = repository.shareProducts(shareProductsRequest.get());
                    getMainThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (shareProductsCallback.get() != null) {
                                shareProductsCallback.get().onSuccess(createUpdateDeleteResourceResponse.getResource());
                            } else {
                                Log.i(ProductShareInteractorImpl.class.getName(), "Request callback is None");
                            }
                        }
                    });
                } catch (final BaseException e) {
                    Log.e(LogUtils.generateTag(this), "Error on GetCustomerInfoImpl interactor");
                    getMainThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (shareProductsCallback.get() != null) {
                                shareProductsCallback.get().onError(e);
                            } else {
                                Log.i(ProductShareInteractorImpl.class.getName(), "Request callback is None");
                            }
                        }
                    });
                }
            }
        });
    }


}
