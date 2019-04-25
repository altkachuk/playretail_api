package ninja.cyplay.com.apilibrary.domain.interactor;

import java.util.List;
import java.util.Map;

import ninja.cyplay.com.apilibrary.domain.executor.InteractorExecutor;
import ninja.cyplay.com.apilibrary.domain.executor.MainThreadExecutor;
import ninja.cyplay.com.apilibrary.domain.interactor.callback.PaginatedResourceRequestCallback;
import ninja.cyplay.com.apilibrary.domain.interactor.decorator.GetResourcesInteractorDecorator;
import ninja.cyplay.com.apilibrary.domain.repository.PlayRetailRepository;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.requests.ResourceRequest;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.responses.PaginatedResourcesResponse;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AFee;

/**
 * Created by romainlebouc on 30/01/2017.
 */

public class FeeInteractorImpl implements FeeInteractor {


    private final GetResourcesInteractorDecorator<PR_AFee> getResourcesInteractorDecorator;

    public FeeInteractorImpl(InteractorExecutor interactorExecutor,
                             MainThreadExecutor mainThreadExecutor,
                             final PlayRetailRepository repository) {

        getResourcesInteractorDecorator = new GetResourcesInteractorDecorator<PR_AFee>(interactorExecutor, mainThreadExecutor) {
            @Override
            protected PaginatedResourceGetter getGetResourceGetter() {
                return new PaginatedResourceGetter() {
                    @Override
                    public PaginatedResourcesResponse getPaginatedResource(ResourceRequest resourceRequest) {
                        return repository.getFees(resourceRequest);
                    }
                };
            }
        };

    }

    @Override
    public void getResources(Map<String, String> filters,
                             List<String> project,
                             PaginatedResourceRequestCallback<PR_AFee> callback) {
        getResourcesInteractorDecorator.getResources(filters, project, callback, false);
    }

}
