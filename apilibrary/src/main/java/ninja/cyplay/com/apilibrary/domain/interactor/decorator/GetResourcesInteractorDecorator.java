package ninja.cyplay.com.apilibrary.domain.interactor.decorator;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;

import ninja.cyplay.com.apilibrary.domain.executor.InteractorExecutor;
import ninja.cyplay.com.apilibrary.domain.executor.MainThreadExecutor;
import ninja.cyplay.com.apilibrary.domain.interactor.AbstractInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.Interactor;
import ninja.cyplay.com.apilibrary.domain.interactor.PaginatedResourceGetter;
import ninja.cyplay.com.apilibrary.domain.interactor.callback.PaginatedResourceRequestCallback;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.requests.ResourceRequest;
import ninja.cyplay.com.apilibrary.models.abstractmodels.ResourceId;

/**
 * Created by romainlebouc on 02/01/2017.
 */

public abstract class GetResourcesInteractorDecorator<Resource extends ResourceId> extends AbstractInteractor {


    //--------------------------------------------------------------------------------------------//
    //    GET
    //--------------------------------------------------------------------------------------------//

    public GetResourcesInteractorDecorator(InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor) {
        super(interactorExecutor, mainThreadExecutor);
    }

    public void getResources(Map<String, String> parameters,
                             List<String> project,
                             PaginatedResourceRequestCallback<Resource> callback,
                             boolean paginated) {
        final WeakReference<ResourceRequest> getResourcesRequest = new WeakReference<>(new ResourceRequest().project(project).filter(parameters).paginate(paginated));
        final WeakReference<PaginatedResourceRequestCallback<Resource>> getResourcesCallback = new WeakReference<>(callback);
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                GetResourcesInteractorDecorator.this.doGetPaginatedResource(getResourcesRequest.get(),
                        getResourcesCallback.get(),
                        getGetResourceGetter()
                );
            }
        });
    }

    public void getResources(Map<String, String> parameters,
                             List<String> project,
                             PaginatedResourceRequestCallback<Resource> callback) {
        this.getResources(parameters, project, callback, true);
    }

    protected abstract PaginatedResourceGetter getGetResourceGetter();


}
