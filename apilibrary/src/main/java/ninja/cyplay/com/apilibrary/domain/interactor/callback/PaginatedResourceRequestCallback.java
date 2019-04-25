package ninja.cyplay.com.apilibrary.domain.interactor.callback;

import java.util.List;

/**
 * Created by romainlebouc on 09/08/16.
 */
public interface PaginatedResourceRequestCallback<Resource> extends RequestCallback {

    void onSuccess(List<Resource> resource, Integer count, String previous, String next);
}
