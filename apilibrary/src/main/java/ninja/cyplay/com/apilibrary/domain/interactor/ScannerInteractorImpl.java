package ninja.cyplay.com.apilibrary.domain.interactor;


import java.lang.ref.WeakReference;

import ninja.cyplay.com.apilibrary.domain.executor.InteractorExecutor;
import ninja.cyplay.com.apilibrary.domain.executor.MainThreadExecutor;
import ninja.cyplay.com.apilibrary.domain.interactor.callback.ResourceRequestCallback;
import ninja.cyplay.com.apilibrary.domain.repository.PlayRetailRepository;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.requests.ResourceRequest;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.responses.ResourceResponse;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ABarCodeInfo;
import ninja.cyplay.com.apilibrary.models.business.EScanFilters;


/**
 * Created by damien on 29/04/15.
 */
public class ScannerInteractorImpl extends AbstractInteractor implements ScannerInteractor {

    private PlayRetailRepository repository;

    EScanFilters eScanFilters;
    String scannedString;

    public ScannerInteractorImpl(InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor, PlayRetailRepository repository) {
        super(interactorExecutor, mainThreadExecutor);
        this.repository = repository;
    }


    @Override
    public void execute(String scannedString, EScanFilters eScanFilters, ResourceRequestCallback<PR_ABarCodeInfo> callback) {
        final WeakReference<ResourceRequestCallback<PR_ABarCodeInfo>> resourceRequestCallback = new WeakReference<>(callback);
        final WeakReference<ResourceRequest> request = new WeakReference<ResourceRequest>(new ResourceRequest().id(scannedString));
        this.eScanFilters = eScanFilters;
        this.scannedString = scannedString;
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                ScannerInteractorImpl.this.doGetResource(request.get(),
                        resourceRequestCallback.get(),
                        new ResourceGetter() {
                            @Override
                            public ResourceResponse getResource(ResourceRequest resourceRequest) {
                                return repository.getCorrespondence(request.get());
                            }
                        });
            }
        });
    }

}
