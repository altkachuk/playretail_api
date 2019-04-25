package ninja.cyplay.com.apilibrary.domain.interactor;

import java.util.HashMap;
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
import ninja.cyplay.com.apilibrary.domain.interactor.decorator.UpdateResourceInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.decorator.UpdateResourceInteractorDecorator;
import ninja.cyplay.com.apilibrary.domain.repository.PlayRetailRepository;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.requests.ResourceRequest;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.responses.PaginatedResourcesResponse;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.responses.ResourceResponse;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AProductReview;


/**
 * Created by romainlebouc on 02/01/2017.
 */

public class ProductReviewInteractor implements CreateResourceInteractor<PR_AProductReview>,
        GetResourcesInteractor<PR_AProductReview>,
        UpdateResourceInteractor<PR_AProductReview> {

    private final CreateResourceInteractorDecorator<PR_AProductReview> createResourceInteractorDecorator;
    private final GetResourcesInteractorDecorator<PR_AProductReview> getResourcesInteractorDecorator;
    private final UpdateResourceInteractorDecorator<PR_AProductReview> updateResourceInteractorDecorator;

    public ProductReviewInteractor(InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor, final PlayRetailRepository repository) {

        createResourceInteractorDecorator = new CreateResourceInteractorDecorator(interactorExecutor, mainThreadExecutor) {

            @Override
            protected ResourceGetter getCreateResourceGetter() {
                return new ResourceGetter() {
                    @Override
                    public ResourceResponse getResource(ResourceRequest resourceRequest) {
                        return repository.addProductReview(resourceRequest);
                    }
                };
            }
        };

        getResourcesInteractorDecorator = new GetResourcesInteractorDecorator<PR_AProductReview>(interactorExecutor, mainThreadExecutor) {
            @Override
            protected PaginatedResourceGetter getGetResourceGetter() {
                return new PaginatedResourceGetter() {
                    @Override
                    public PaginatedResourcesResponse getPaginatedResource(ResourceRequest resourceRequest) {
                        return repository.getProductReviews(resourceRequest);
                    }
                };
            }
        };

        updateResourceInteractorDecorator = new UpdateResourceInteractorDecorator<PR_AProductReview>(interactorExecutor, mainThreadExecutor) {
            @Override
            protected ResourceGetter getCreateResourceGetter() {
                return new ResourceGetter() {
                    @Override
                    public ResourceResponse getResource(ResourceRequest resourceRequest) {
                        return repository.updateProductReview(resourceRequest);
                    }
                };
            }
        };


    }

    @Override
    public void addResource(PR_AProductReview pr_aProductReview, ResourceRequestCallback callback) {
        createResourceInteractorDecorator.addResource(pr_aProductReview, callback);
    }

    @Override
    public void getResources(Map<String, String> filters,
                             List<String> project,
                             PaginatedResourceRequestCallback<PR_AProductReview> callback) {
        getResourcesInteractorDecorator.getResources(filters,project, callback, false);
    }

    @Override
    public void updateResource(PR_AProductReview productReview, ResourceRequestCallback callback) {
        updateResourceInteractorDecorator.updateResource(productReview, callback);
    }

    public static Map<String, String> getParametersMap(String customerId, String productId, String skuId) {
        Map<String, String> result = new HashMap<>();
        result.put("customer_id", customerId);
        result.put("product_id", productId);
        result.put("sku_id", skuId);
        return result;
    }


}
