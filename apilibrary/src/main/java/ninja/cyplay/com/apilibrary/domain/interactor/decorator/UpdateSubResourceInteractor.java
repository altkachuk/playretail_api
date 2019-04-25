package ninja.cyplay.com.apilibrary.domain.interactor.decorator;

import ninja.cyplay.com.apilibrary.domain.interactor.callback.ResourceRequestCallback;
import ninja.cyplay.com.apilibrary.models.abstractmodels.ResourceId;

/**
 * Created by romainlebouc on 06/06/2017.
 */

public interface UpdateSubResourceInteractor<SubResource extends ResourceId> {

    void updateSubResource(String resourceId,
                           SubResource resource,
                           ResourceRequestCallback callback);
}
