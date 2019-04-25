package ninja.cyplay.com.playretail_api.ui.presenter;

import ninja.cyplay.com.playretail_api.model.business.Seller;
import ninja.cyplay.com.playretail_api.ui.view.AuthenticationView;

/**
 * Created by damien on 29/04/15.
 */
public interface AuthenticationPresenter extends Presenter<AuthenticationView> {

    public void getOauth2Details(String username);

    public void getAccessToken(String username, String password);

    public void getSellerDetails(Seller seller);

    public void invalidateToken();

    // Old Method
    public void doLogin(Seller PRSeller, String pwd);

}