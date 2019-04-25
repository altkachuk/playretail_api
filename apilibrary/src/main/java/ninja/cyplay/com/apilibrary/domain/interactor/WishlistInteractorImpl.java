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
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AWishlistItem;
import ninja.cyplay.com.apilibrary.utils.LogUtils;

/**
 * Created by damien on 21/05/15.
 */
public class WishlistInteractorImpl extends AbstractInteractor implements WishlistInteractor {

    private PlayRetailRepository repository;


    public WishlistInteractorImpl(InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor, PlayRetailRepository repository) {
        super(interactorExecutor, mainThreadExecutor);
        this.repository = repository;
    }


    // -------------------------------------------------------------------------------------------
    //
    // -------------------------------------------------------------------------------------------

    @Override
    public void addProductWishlist(String customerId,
                                   PR_AWishlistItem pr_aWishlistItem,
                                   String shopId,
                                   ResourceRequestCallback<PR_AWishlistItem> callback) {
        final WeakReference<ResourceRequest<PR_AWishlistItem>> request = new WeakReference<ResourceRequest<PR_AWishlistItem>>(new ResourceRequest<PR_AWishlistItem>().id(customerId).body(pr_aWishlistItem));
        final WeakReference<ResourceRequestCallback<PR_AWishlistItem>> addProductCallback = new WeakReference<>(callback);
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                WishlistInteractorImpl.super.doGetResource(request.get(),
                        addProductCallback.get(),
                        new ResourceGetter() {
                            @Override
                            public ResourceResponse getResource(ResourceRequest resourceRequest) {
                                return repository.addProductToWishlist(request.get());
                            }
                        });


            }
        });
    }

    // -------------------------------------------------------------------------------------------
    //
    // -------------------------------------------------------------------------------------------


    @Override
    public void deleteProductWishlist(String customerId,
                                      String wishlistitemId,
                                      ResourceRequestCallback<PR_AWishlistItem> callback) {
        final WeakReference<ResourceRequest<PR_AWishlistItem>> request = new WeakReference<ResourceRequest<PR_AWishlistItem>>(new ResourceRequest<PR_AWishlistItem>().id(customerId).secondLevelId(wishlistitemId));
        final WeakReference<ResourceRequestCallback<PR_AWishlistItem>> deleteProductCallback = new WeakReference<>(callback);
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                WishlistInteractorImpl.super.doGetResource(request.get(),
                        deleteProductCallback.get(),
                        new ResourceGetter() {
                            @Override
                            public ResourceResponse getResource(ResourceRequest resourceRequest) {
                                return repository.deleteProductFromWishlist(request.get());
                            }
                        });

            }
        });
    }


}
