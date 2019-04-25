package ninja.cyplay.com.apilibrary.domain.interactor;

import java.util.List;
import java.util.Map;

import ninja.cyplay.com.apilibrary.domain.executor.InteractorExecutor;
import ninja.cyplay.com.apilibrary.domain.executor.MainThreadExecutor;
import ninja.cyplay.com.apilibrary.domain.interactor.callback.PaginatedResourceRequestCallback;
import ninja.cyplay.com.apilibrary.domain.interactor.callback.ResourceRequestCallback;
import ninja.cyplay.com.apilibrary.domain.interactor.decorator.CreateResourceInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.decorator.CreateResourceInteractorDecorator;
import ninja.cyplay.com.apilibrary.domain.interactor.decorator.GetResourcesInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.decorator.GetResourcesInteractorDecorator;
import ninja.cyplay.com.apilibrary.domain.repository.PlayRetailRepository;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.requests.ResourceRequest;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.responses.PaginatedResourcesResponse;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.responses.ResourceResponse;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AQuotation;

/**
 * Created by wentongwang on 07/07/2017.
 */

public class QuotationInteractor implements CreateResourceInteractor<PR_AQuotation>, GetResourcesInteractor<PR_AQuotation> {

    private final CreateResourceInteractorDecorator<PR_AQuotation> createResourceInteractorDecorator;
    private final GetResourcesInteractorDecorator<PR_AQuotation> getResourcesInteractorDecorator;

    public QuotationInteractor(InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor, final PlayRetailRepository retailRepository) {
        this.createResourceInteractorDecorator = new CreateResourceInteractorDecorator<PR_AQuotation>(interactorExecutor, mainThreadExecutor) {
            @Override
            protected ResourceGetter getCreateResourceGetter() {
                return new ResourceGetter() {
                    @Override
                    public ResourceResponse getResource(ResourceRequest resourceRequest) {
                        return retailRepository.createQuotation(resourceRequest);
                    }
                };
            }
        };

        this.getResourcesInteractorDecorator = new GetResourcesInteractorDecorator<PR_AQuotation>(interactorExecutor, mainThreadExecutor) {
            @Override
            protected PaginatedResourceGetter getGetResourceGetter() {
                return new PaginatedResourceGetter() {
                    @Override
                    public PaginatedResourcesResponse getPaginatedResource(ResourceRequest resourceRequest) {
                        return retailRepository.getProductReviews(resourceRequest);
                    }
                };
            }
        };

    }

    @Override
    public void addResource(PR_AQuotation pr_aQuotation, ResourceRequestCallback callback) {
        createResourceInteractorDecorator.addResource(pr_aQuotation, callback);
    }

    @Override
    public void getResources(Map<String, String> parameters, List<String> project, PaginatedResourceRequestCallback<PR_AQuotation> callback) {
        getResourcesInteractorDecorator.getResources(parameters, project, callback, false);
    }
}
