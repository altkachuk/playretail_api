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

public abstract class DeleteSubResourceInteractorDecorator<Resource extends ResourceId> extends AbstractInteractor {


    public DeleteSubResourceInteractorDecorator(InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor) {
        super(interactorExecutor, mainThreadExecutor);
    }

    public void deleteSubResource(
            String resourceId,
            Resource resource,
            ResourceRequestCallback callback) {
        final WeakReference<ResourceRequest> addResourceRequest = new WeakReference<>(new ResourceRequest().id(resourceId).secondLevelId(resource.getId()));
        final WeakReference<ResourceRequestCallback> addResourceCallback = new WeakReference<>(callback);
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                DeleteSubResourceInteractorDecorator.this.doDeleteResource(addResourceRequest.get(),
                        addResourceCallback.get(),
                        getCreateResourceGetter()
                );
            }
        });
    }

    protected abstract ResourceGetter getCreateResourceGetter();


}
