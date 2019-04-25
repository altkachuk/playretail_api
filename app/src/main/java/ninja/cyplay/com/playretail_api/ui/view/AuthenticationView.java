package ninja.cyplay.com.playretail_api.ui.view;

/**
 * Created by damien on 29/04/15.
 */
public interface AuthenticationView extends View {

    void showLoading();

    void hideLoading();

    void onAuthenticationSuccess();

    void onGetOauth2DetailsSuccess();

    void onAccessTokenSuccess();

    void onInvalidateTokenSuccess();

    void onGetSellerSuccess();

}
