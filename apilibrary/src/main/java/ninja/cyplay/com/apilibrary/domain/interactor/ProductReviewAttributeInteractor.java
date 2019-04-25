package ninja.cyplay.com.apilibrary.domain.interactor;

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
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AProductReviewAttribute;


/**
 * Created by romainlebouc on 02/01/2017.
 */

public class ProductReviewAttributeInteractor implements GetResourcesInteractor<PR_AProductReviewAttribute> {

    private final GetResourcesInteractorDecorator<PR_AProductReviewAttribute> getResourcesInteractorDecorator;

    public ProductReviewAttributeInteractor(InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor, final PlayRetailRepository repository) {

        getResourcesInteractorDecorator = new GetResourcesInteractorDecorator<PR_AProductReviewAttribute>(interactorExecutor, mainThreadExecutor) {
            @Override
            protected PaginatedResourceGetter getGetResourceGetter() {
                return new PaginatedResourceGetter() {
                    @Override
                    public PaginatedResourcesResponse getPaginatedResource(ResourceRequest resourceRequest) {
                        return repository.getProductReviewAttributes(resourceRequest);
                    }
                };
            }
        };

    }

    @Override
    public void getResources(Map<String, String> filters,
                             List<String> project,
                             PaginatedResourceRequestCallback<PR_AProductReviewAttribute> callback) {
        getResourcesInteractorDecorator.getResources(filters,project, callback, false);
    }


}
