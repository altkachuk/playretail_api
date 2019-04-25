package ninja.cyplay.com.apilibrary.domain.interactor;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;

import ninja.cyplay.com.apilibrary.domain.executor.InteractorExecutor;
import ninja.cyplay.com.apilibrary.domain.executor.MainThreadExecutor;
import ninja.cyplay.com.apilibrary.domain.interactor.callback.PaginatedResourceRequestCallback;
import ninja.cyplay.com.apilibrary.domain.repository.PlayRetailRepository;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.requests.ResourceRequest;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.responses.PaginatedResourcesResponse;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AOffer;
import ninja.cyplay.com.apilibrary.models.meta.ResourceFieldSorting;

/**
 * Created by romainlebouc on 02/09/16.
 */
public class OfferInteractorImpl extends AbstractInteractor implements OfferInteractor {

    private PlayRetailRepository repository;

    public OfferInteractorImpl(InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor, PlayRetailRepository repository) {
        super(interactorExecutor, mainThreadExecutor);
        this.repository = repository;
    }

    // -------------------------------------------------------------------------------------------
    //
    // -------------------------------------------------------------------------------------------

    @Override
    public void getOffers(Map<String, String> filters, int offset, int limit, List<String> projection, ResourceFieldSorting resourceFieldSortings, PaginatedResourceRequestCallback<PR_AOffer> callback) {
        final WeakReference<ResourceRequest> getOffersRequest = new WeakReference<ResourceRequest>(new ResourceRequest().filter(filters).paginate(offset, limit).project(projection));
        if (resourceFieldSortings != null) {
            getOffersRequest.get().sort(resourceFieldSortings.getSortingRequest());
        }
        final WeakReference<PaginatedResourceRequestCallback<PR_AOffer>> getOffersRequestCallback = new WeakReference<PaginatedResourceRequestCallback<PR_AOffer>>(callback);
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                OfferInteractorImpl.this.doGetPaginatedResource(getOffersRequest.get(),
                        getOffersRequestCallback.get(),
                        new PaginatedResourceGetter() {
                            @Override
                            public PaginatedResourcesResponse getPaginatedResource(ResourceRequest resourceRequest) {
                                return repository.getOffers(getOffersRequest.get());
                            }
                        }
                );
            }
        });
    }


}
