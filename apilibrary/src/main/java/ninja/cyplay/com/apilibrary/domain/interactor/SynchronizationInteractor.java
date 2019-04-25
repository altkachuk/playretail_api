package ninja.cyplay.com.apilibrary.domain.interactor;

import java.util.List;

import ninja.cyplay.com.apilibrary.domain.interactor.callback.ResourceRequestCallback;
import ninja.cyplay.com.apilibrary.models.abstractmodels.v2.Contact;

/**
 * Created by damien on 22/05/15.
 */
public interface SynchronizationInteractor {

    void getDataOut(final ResourceRequestCallback<String> callback);
    void sendDataIn(List<Contact> customersIn, final ResourceRequestCallback<List<Contact>> callback);

}
