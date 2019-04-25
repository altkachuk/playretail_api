package ninja.cyplay.com.apilibrary.domain.interactor.decorator;

import java.lang.ref.WeakReference;

import ninja.cyplay.com.apilibrary.domain.executor.InteractorExecutor;
import ninja.cyplay.com.apilibrary.domain.executor.MainThreadExecutor;
import ninja.cyplay.com.apilibrary.domain.interactor.AbstractInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.Interactor;
import ninja.cyplay.com.apilibrary.domain.interactor.ResourceGetter;
import ninja.cyplay.com.apilibrary.domain.interactor.callback.ResourceRequestCallback;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.requests.ResourceRequest;
import ninja.cyplay.com.apilibrary.models.abstractmodels.ResourceId;

/**
 * Created by romainlebouc on 02/01/2017.
 */

public abstract class GetResourceInteractorDecorator<Resource extends ResourceId> extends AbstractInteractor {


    //--------------------------------------------------------------------------------------------//
    //    GET
    //--------------------------------------------------------------------------------------------//

    public GetResourceInteractorDecorator(InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor) {
        super(interactorExecutor, mainThreadExecutor);
    }


    public void getResource(String id,
                            ResourceRequestCallback<Resource> callback) {
        final WeakReference<ResourceRequest> getResourceRequest = new WeakReference<ResourceRequest>(new ResourceRequest().id(id));
        final WeakReference<ResourceRequestCallback<Resource>> getResourceCallback = new WeakReference<ResourceRequestCallback<Resource>>(callback);
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                GetResourceInteractorDecorator.this.doGetResource(getResourceRequest.get(),
                        getResourceCallback.get(),
                        getGetResourceGetter()
                );
            }
        });
    }

    protected abstract ResourceGetter getGetResourceGetter();


}
