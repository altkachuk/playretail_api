package ninja.cyplay.com.apilibrary.domain.interactor;

import java.util.List;

import ninja.cyplay.com.apilibrary.domain.interactor.callback.ResourceRequestCallback;
import ninja.cyplay.com.apilibrary.models.abstractmodels.ActionEvent;

/**
 * Created by romainlebouc on 22/09/16.
 */
public interface ActionEventInteractor {

    void saveActionEvents(List<ActionEvent> pageViews, final ResourceRequestCallback<List<ActionEvent>> callback);
}
