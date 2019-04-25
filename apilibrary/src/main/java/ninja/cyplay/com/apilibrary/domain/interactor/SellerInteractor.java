package ninja.cyplay.com.apilibrary.domain.interactor;

import ninja.cyplay.com.apilibrary.domain.interactor.callback.ResourceRequestCallback;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AUser;
import ninja.cyplay.com.apilibrary.models.business.OAuth2Credentials;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ADeviceConfiguration;

/**
 * Created by damien on 29/04/15.
 */
public interface SellerInteractor {

    void accessToken(String username, String password, String client_id, String client_secret, final ResourceRequestCallback<OAuth2Credentials> callback);

    void invalidateToken(String client_id, String client_secret, String token, final ResourceRequestCallback<Void> callback);

    void getDeviceConfiguration(String udid, final ResourceRequestCallback<PR_ADeviceConfiguration> callback);

    void replaceUserPassword(String userid, final String oldPassword, final String newPassword, final ResourceRequestCallback<PR_AUser> callback);

    void notifyLostUserPassword(String userid, final ResourceRequestCallback<PR_AUser> callback);


}
