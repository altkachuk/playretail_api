package ninja.cyplay.com.apilibrary.domain.interactor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ninja.cyplay.com.apilibrary.domain.executor.InteractorExecutor;
import ninja.cyplay.com.apilibrary.domain.executor.MainThreadExecutor;
import ninja.cyplay.com.apilibrary.domain.interactor.callback.PaginatedResourceRequestCallback;
import ninja.cyplay.com.apilibrary.domain.interactor.decorator.GetResourcesInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.decorator.GetResourcesInteractorDecorator;
import ninja.cyplay.com.apilibrary.domain.repository.PlayRetailRepository;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.requests.ResourceRequest;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.responses.PaginatedResourcesResponse;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ADeliveryMode;

/**
 * Created by wentongwang on 24/03/2017.
 */

public class DeliveryModeInteractor implements GetResourcesInteractor<PR_ADeliveryMode> {


    private final GetResourcesInteractorDecorator<PR_ADeliveryMode> getResourcesInteractorDecorator;

    public DeliveryModeInteractor(InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor, final PlayRetailRepository repository) {
        getResourcesInteractorDecorator = new GetResourcesInteractorDecorator<PR_ADeliveryMode>(interactorExecutor, mainThreadExecutor) {
            @Override
            protected PaginatedResourceGetter getGetResourceGetter() {
                return new PaginatedResourceGetter() {
                    @Override
                    public PaginatedResourcesResponse getPaginatedResource(ResourceRequest resourceRequest) {
                        return repository.getDeliveryMode(resourceRequest);
                    }
                };
            }
        };
    }


    @Override
    public void getResources(Map<String, String> parameters, List<String> project, PaginatedResourceRequestCallback<PR_ADeliveryMode> callback) {
        getResourcesInteractorDecorator.getResources(parameters, project, callback, false);
    }


    public static Map<String, String> getParametersMap(String status) {
        Map<String, String> result = new HashMap<>();
        result.put("status", status);
        return result;
    }
}
