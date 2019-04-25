package ninja.cyplay.com.apilibrary.domain.interactor;

import java.util.List;

import ninja.cyplay.com.apilibrary.domain.interactor.callback.PaginatedResourceRequestCallback;
import ninja.cyplay.com.apilibrary.domain.interactor.callback.ResourceRequestCallback;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_Sale;

/**
 * Created by andre on 28-Feb-19.
 */

public interface SaleInteractor {

    void getAllSales(final PaginatedResourceRequestCallback<PR_Sale> callback);
    void removeSale(String id, final ResourceRequestCallback<PR_Sale> callback);
}
