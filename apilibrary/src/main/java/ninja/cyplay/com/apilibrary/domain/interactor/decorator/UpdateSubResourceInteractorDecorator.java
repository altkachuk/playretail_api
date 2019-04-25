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
 * Created by romainlebouc on 06/06/2017.
 */


public abstract class UpdateSubResourceInteractorDecorator<SubResource extends ResourceId> extends AbstractInteractor {


    //--------------------------------------------------------------------------------------------//
    //    UPDATE
    //--------------------------------------------------------------------------------------------//

    public UpdateSubResourceInteractorDecorator(InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor) {
        super(interactorExecutor, mainThreadExecutor);
    }


    public void updateSubResource(String resourceId,
                                  SubResource resource,
                                  ResourceRequestCallback callback) {
        final WeakReference<ResourceRequest> updateResourceRequest = new WeakReference<>(new ResourceRequest().body(resource).id(resourceId).secondLevelId(resource.getId()));
        final WeakReference<ResourceRequestCallback> updateResourceCallback = new WeakReference<>(callback);
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                UpdateSubResourceInteractorDecorator.this.doUpdateResource(updateResourceRequest.get(),
                        updateResourceCallback.get(),
                        getCreateResourceGetter()
                );
            }
        });
    }

    protected abstract ResourceGetter getCreateResourceGetter();

}
