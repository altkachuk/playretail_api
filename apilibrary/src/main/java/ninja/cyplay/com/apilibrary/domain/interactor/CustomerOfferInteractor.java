package ninja.cyplay.com.apilibrary.domain.interactor;

import ninja.cyplay.com.apilibrary.domain.executor.InteractorExecutor;
import ninja.cyplay.com.apilibrary.domain.executor.MainThreadExecutor;
import ninja.cyplay.com.apilibrary.domain.interactor.callback.ResourceRequestCallback;
import ninja.cyplay.com.apilibrary.domain.interactor.decorator.CreateSubResourceInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.decorator.CreateSubResourceInteractorDecorator;
import ninja.cyplay.com.apilibrary.domain.interactor.decorator.DeleteSubResourceInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.decorator.DeleteSubResourceInteractorDecorator;
import ninja.cyplay.com.apilibrary.domain.repository.PlayRetailRepository;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.requests.ResourceRequest;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.responses.ResourceResponse;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ABasketOffer;

/**
 * Created by wentongwang on 04/04/2017.
 */

public class CustomerOfferInteractor implements CreateSubResourceInteractor<PR_ABasketOffer>,
        DeleteSubResourceInteractor<PR_ABasketOffer> {

    private final CreateSubResourceInteractorDecorator<PR_ABasketOffer> createSubResourceInteractorDecorator;
    private final DeleteSubResourceInteractorDecorator<PR_ABasketOffer> deleteSubResourceInteractorDecorator;

    public CustomerOfferInteractor(InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor, final PlayRetailRepository repository) {

        this.createSubResourceInteractorDecorator = new CreateSubResourceInteractorDecorator<PR_ABasketOffer>(interactorExecutor, mainThreadExecutor) {
            @Override
            protected ResourceGetter getCreateResourceGetter() {
                return new ResourceGetter() {
                    @Override
                    public ResourceResponse getResource(ResourceRequest resourceRequest) {
                        return repository.activeCustomerOffer(resourceRequest);
                    }
                };
            }
        };
        this.deleteSubResourceInteractorDecorator = new DeleteSubResourceInteractorDecorator<PR_ABasketOffer>(interactorExecutor, mainThreadExecutor) {
            @Override
            protected ResourceGetter getCreateResourceGetter() {

                return new ResourceGetter() {
                    @Override
                    public ResourceResponse getResource(ResourceRequest resourceRequest) {
                        return repository.inActiveCustomerOffer(resourceRequest);
                    }
                };

            }
        };
    }

    @Override
    public void addSubResource(String resourceId, PR_ABasketOffer pr_aBasketOffer, ResourceRequestCallback callback) {
        createSubResourceInteractorDecorator.addSubResource(resourceId, pr_aBasketOffer, callback);
    }

    @Override
    public void deleteSubResource(String resourceId, PR_ABasketOffer pr_aBasketOffer, ResourceRequestCallback callback) {
        deleteSubResourceInteractorDecorator.deleteSubResource(resourceId, pr_aBasketOffer, callback);
    }
}
