package ninja.cyplay.com.apilibrary.domain.interactor;

import ninja.cyplay.com.apilibrary.domain.executor.InteractorExecutor;
import ninja.cyplay.com.apilibrary.domain.executor.MainThreadExecutor;
import ninja.cyplay.com.apilibrary.domain.interactor.callback.ResourceRequestCallback;
import ninja.cyplay.com.apilibrary.domain.interactor.decorator.CreateResourceInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.decorator.CreateResourceInteractorDecorator;
import ninja.cyplay.com.apilibrary.domain.repository.PlayRetailRepository;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.requests.ResourceRequest;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.responses.ResourceResponse;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AFeedback;

/**
 * Created by wentongwang on 26/06/2017.
 */

public class FeedbackInteractor implements CreateResourceInteractor<PR_AFeedback> {

    private final CreateResourceInteractorDecorator<PR_AFeedback> createResourceInteractorDecorator;

    public FeedbackInteractor(InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor, final PlayRetailRepository repository) {

        this.createResourceInteractorDecorator = new CreateResourceInteractorDecorator<PR_AFeedback>(interactorExecutor, mainThreadExecutor) {
            @Override
            protected ResourceGetter getCreateResourceGetter() {
                return new ResourceGetter() {
                    @Override
                    public ResourceResponse getResource(ResourceRequest resourceRequest) {
                        return repository.sendFeedback(resourceRequest);
                    }
                };
            }
        };
    }


    @Override
    public void addResource(PR_AFeedback suggestion, ResourceRequestCallback callback) {
        createResourceInteractorDecorator.addResource(suggestion, callback);
    }
}
