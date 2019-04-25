package ninja.cyplay.com.apilibrary.domain.interactor.decorator;

import ninja.cyplay.com.apilibrary.domain.interactor.callback.ResourceRequestCallback;
import ninja.cyplay.com.apilibrary.models.abstractmodels.ResourceId;

/**
 * Created by romainlebouc on 02/01/2017.
 */

public interface DeleteSubResourceInteractor<SubResource extends ResourceId> {

    void deleteSubResource(
            String resourceId,
            SubResource resource,
            ResourceRequestCallback callback);
}
