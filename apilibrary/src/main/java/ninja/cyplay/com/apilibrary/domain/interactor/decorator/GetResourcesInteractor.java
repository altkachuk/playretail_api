package ninja.cyplay.com.apilibrary.domain.interactor.decorator;

import java.util.List;
import java.util.Map;

import ninja.cyplay.com.apilibrary.domain.interactor.callback.PaginatedResourceRequestCallback;
import ninja.cyplay.com.apilibrary.models.abstractmodels.ResourceId;

/**
 * Created by romainlebouc on 02/01/2017.
 */

public interface GetResourcesInteractor<Resource extends ResourceId> {

    void getResources(Map<String, String> parameters,
                      List<String> project,
                             PaginatedResourceRequestCallback<Resource> callback);
}
