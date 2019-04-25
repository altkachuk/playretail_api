package ninja.cyplay.com.apilibrary.domain.interactor.decorator;

import ninja.cyplay.com.apilibrary.domain.interactor.callback.ResourceRequestCallback;
import ninja.cyplay.com.apilibrary.models.abstractmodels.ResourceId;

/**
 * Created by romainlebouc on 02/01/2017.
 */

public interface CreateResourceInteractor<Resource extends ResourceId> {

    void addResource(Resource resource,
                     ResourceRequestCallback callback);
}
