package ninja.cyplay.com.apilibrary.domain.interactor;

import java.util.Date;

import ninja.cyplay.com.apilibrary.domain.interactor.callback.PaginatedResourceRequestCallback;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AEvent;

/**
 * Created by romainlebouc on 26/04/16.
 */
public interface EventInteractor {

    void getEvents(String sellerId,
                   Date after,
                   Date before,
                   final PaginatedResourceRequestCallback<PR_AEvent> callback);

    void getCustomerEvents(String customerId,
                           final PaginatedResourceRequestCallback<PR_AEvent> callback);

}
