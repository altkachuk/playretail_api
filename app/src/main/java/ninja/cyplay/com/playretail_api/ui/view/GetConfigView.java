package ninja.cyplay.com.playretail_api.ui.view;

public interface GetConfigView extends View {

    void showLoading();

    void hideLoading();

    void showReload();

    void hideReload();

    void onConfigSuccess();

    void goToDeviceRegistration();


}
