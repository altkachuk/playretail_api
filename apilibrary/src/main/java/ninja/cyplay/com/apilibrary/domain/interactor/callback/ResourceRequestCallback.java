package ninja.cyplay.com.apilibrary.domain.interactor.callback;

/**
 * Created by romainlebouc on 09/08/16.
 */
public interface ResourceRequestCallback<Resource> extends RequestCallback {

    void onSuccess(Resource resource);

}
