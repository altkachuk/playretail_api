package ninja.cyplay.com.playretail_api.ui.view;

/**
 * Created by damien on 29/04/15.
 */
public interface ProvisionDeviceView extends View {

    void showLoading();

    void hideLoading();

    void onProvisionDeviceSuccess();

    void onProvisionDeviceError();

}