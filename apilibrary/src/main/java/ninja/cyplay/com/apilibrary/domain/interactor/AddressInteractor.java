package ninja.cyplay.com.apilibrary.domain.interactor;

import ninja.cyplay.com.apilibrary.domain.executor.InteractorExecutor;
import ninja.cyplay.com.apilibrary.domain.executor.MainThreadExecutor;
import ninja.cyplay.com.apilibrary.domain.interactor.callback.ResourceRequestCallback;
import ninja.cyplay.com.apilibrary.domain.interactor.decorator.CreateSubResourceInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.decorator.CreateSubResourceInteractorDecorator;
import ninja.cyplay.com.apilibrary.domain.interactor.decorator.UpdateSubResourceInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.decorator.UpdateSubResourceInteractorDecorator;
import ninja.cyplay.com.apilibrary.domain.repository.PlayRetailRepository;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.requests.ResourceRequest;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.responses.ResourceResponse;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AAddress;

/**
 * Created by romainlebouc on 09/12/2016.
 */

public class AddressInteractor implements CreateSubResourceInteractor<PR_AAddress>,
        UpdateSubResourceInteractor<PR_AAddress> {

    private final CreateSubResourceInteractorDecorator<PR_AAddress> createSubResourceInteractorDecorator;
    private final UpdateSubResourceInteractorDecorator<PR_AAddress> updateSubResourceInteractorDecorator;

    public AddressInteractor(InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor, final PlayRetailRepository repository) {

        this.createSubResourceInteractorDecorator = new CreateSubResourceInteractorDecorator<PR_AAddress>(interactorExecutor, mainThreadExecutor) {
            @Override
            protected ResourceGetter getCreateResourceGetter() {
                return new ResourceGetter() {
                    @Override
                    public ResourceResponse getResource(ResourceRequest resourceRequest) {
                        return repository.addAddressToCustomer(resourceRequest);
                    }
                };
            }
        };

        this.updateSubResourceInteractorDecorator = new UpdateSubResourceInteractorDecorator<PR_AAddress>(interactorExecutor, mainThreadExecutor) {
            @Override
            protected ResourceGetter getCreateResourceGetter() {
                return new ResourceGetter() {
                    @Override
                    public ResourceResponse getResource(ResourceRequest resourceRequest) {
                        return repository.updateCustomerAddress(resourceRequest);
                    }
                };
            }
        };
    }

    @Override
    public void addSubResource(String resourceId, PR_AAddress address, ResourceRequestCallback callback) {
        createSubResourceInteractorDecorator.addSubResource(resourceId, address, callback);
    }

    @Override
    public void updateSubResource(String resourceId, PR_AAddress address, ResourceRequestCallback callback) {
        updateSubResourceInteractorDecorator.updateSubResource(resourceId, address, callback);
    }
}
