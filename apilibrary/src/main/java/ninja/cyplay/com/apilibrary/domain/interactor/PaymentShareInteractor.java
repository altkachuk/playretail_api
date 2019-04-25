package ninja.cyplay.com.apilibrary.domain.interactor;

import ninja.cyplay.com.apilibrary.domain.executor.InteractorExecutor;
import ninja.cyplay.com.apilibrary.domain.executor.MainThreadExecutor;
import ninja.cyplay.com.apilibrary.domain.interactor.callback.ResourceRequestCallback;
import ninja.cyplay.com.apilibrary.domain.interactor.decorator.CreateResourceInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.decorator.CreateResourceInteractorDecorator;
import ninja.cyplay.com.apilibrary.domain.repository.PlayRetailRepository;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.requests.ResourceRequest;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.responses.ResourceResponse;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_APaymentShare;

/**
 * Created by wentongwang on 30/03/2017.
 */

public class PaymentShareInteractor implements CreateResourceInteractor<PR_APaymentShare> {

    private final CreateResourceInteractorDecorator<PR_APaymentShare> createResourceInteractorDecorator;

    public PaymentShareInteractor(InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor, final PlayRetailRepository repository) {

        this.createResourceInteractorDecorator = new CreateResourceInteractorDecorator<PR_APaymentShare>(interactorExecutor, mainThreadExecutor) {
            @Override
            protected ResourceGetter getCreateResourceGetter() {
                return new ResourceGetter() {
                    @Override
                    public ResourceResponse getResource(ResourceRequest resourceRequest) {
                        return repository.createPaymentShare(resourceRequest);
                    }
                };
            }
        };
    }


    @Override
    public void addResource(PR_APaymentShare pr_aPaymentShare, ResourceRequestCallback callback) {
        createResourceInteractorDecorator.addResource(pr_aPaymentShare, callback);
    }
}
