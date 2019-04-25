package ninja.cyplay.com.playretail_api.ui.view;

/**
 * Created by damien on 29/06/15.
 */
public interface PaymentView extends View {

    void showLoading();

    void showLoadingWithMessage(String message);

    void showBluetoothError();

    void hideLoading();

    void onPaymentSuccess();

    void onPaymentError();

    void onRegisterSuccess();

    void onDevicePrepared();

    void onDevicePreparationError();

    void onRegisterError();

    void displayPopUp(String message);

    void askSignature();

    void signatureError();
}
