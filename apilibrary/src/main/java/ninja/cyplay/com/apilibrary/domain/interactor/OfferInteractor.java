package ninja.cyplay.com.apilibrary.domain.interactor;

import java.util.List;
import java.util.Map;

import ninja.cyplay.com.apilibrary.domain.interactor.callback.PaginatedResourceRequestCallback;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AOffer;
import ninja.cyplay.com.apilibrary.models.meta.ResourceFieldSorting;

/**
 * Created by romainlebouc on 02/09/16.
 */
public interface OfferInteractor {

    void getOffers(Map<String, String> filters,
                   int offset,
                   int limit,
                   List<String> projection,
                   ResourceFieldSorting resourceFieldSortings,
                   PaginatedResourceRequestCallback<PR_AOffer> callback);
}
