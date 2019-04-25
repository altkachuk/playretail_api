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

public abstract class CreateResourceInteractorDecorator<Resource extends ResourceId> extends AbstractInteractor {


    public CreateResourceInteractorDecorator(InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor) {
        super(interactorExecutor, mainThreadExecutor);
    }

    //--------------------------------------------------------------------------------------------//
    //    CREATE
    //--------------------------------------------------------------------------------------------//


    public void addResource(Resource resource,
                            ResourceRequestCallback callback) {
        final WeakReference<ResourceRequest> addResourceRequest = new WeakReference<>(new ResourceRequest().body(resource));
        final WeakReference<ResourceRequestCallback> addResourceCallback = new WeakReference<>(callback);
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                CreateResourceInteractorDecorator.this.doAddResource(addResourceRequest.get(),
                        addResourceCallback.get(),
                        getCreateResourceGetter()
                );
            }
        });
    }

    protected abstract ResourceGetter getCreateResourceGetter();


}
