package ninja.cyplay.com.playretail_api.ui.presenter;

import android.content.Context;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

import javax.inject.Inject;

import de.payleven.payment.Device;
import de.payleven.payment.DevicePreparationListener;
import de.payleven.payment.GeoLocation;
import de.payleven.payment.PairedDevice;
import de.payleven.payment.Payleven;
import de.payleven.payment.PaylevenError;
import de.payleven.payment.PaylevenFactory;
import de.payleven.payment.PaylevenRegistrationListener;
import de.payleven.payment.PaymentListener;
import de.payleven.payment.PaymentRequest;
import de.payleven.payment.PaymentResult;
import de.payleven.payment.PaymentTask;
import de.payleven.payment.SignatureResponseHandler;
import ninja.cyplay.com.apilibrary.domain.interactor.PaymentInteractor;
import ninja.cyplay.com.apilibrary.models.singleton.APIValue;
import ninja.cyplay.com.apilibrary.utils.Constants;
import ninja.cyplay.com.playretail_api.app.BasePresenter;
import ninja.cyplay.com.playretail_api.model.singleton.PaylevenApi;
import ninja.cyplay.com.playretail_api.ui.view.PaymentView;

/**
 * Created by damien on 29/06/15.
 */
public class PaymentPresenterImpl extends BasePresenter implements PaymentPresenter, PaymentListener{

    @Inject
    APIValue apiValue;

    @Inject
    PaylevenApi paylevenApi;

    private Context context;
    private PaymentView paymentView;
    private PaymentInteractor paymentInteractor;

    // Device selected

    public PaymentPresenterImpl(Context context, PaymentInteractor paymentInteractor) {
        super(context);
        this.context = context;
        this.paymentInteractor = paymentInteractor;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void setView(PaymentView view) {
        this.paymentView = view;
    }

    @Override
    public void register(Context applicationContext) {
        if (!paylevenApi.isInit()) {
            paymentView.showLoading();
                PaylevenFactory.registerAsync(
                    applicationContext,
                    Constants.PAYLEVEN_VENDOR_EMAIL,
                    Constants.PAYLEVEN_VENDOR_PWD,
                    Constants.PAYLEVEN_API_KEY,
                    new PaylevenRegistrationListener() {
                        @Override
                        public void onRegistered(Payleven payleven) {
                            paylevenApi.setPayleven(payleven);
                            paylevenApi.setInit(true);
                            paymentView.hideLoading();
                            paymentView.onRegisterSuccess();
                        }

                        @Override
                        public void onError(PaylevenError error) {
                            paylevenApi.setPayleven(null);
                            paylevenApi.setInit(false);
                            // Process the error
                            paymentView.hideLoading();
                            paymentView.displayPopUp(error.getMessage());
                            paymentView.onRegisterError();
                        }
                    });
        }
        else
            paymentView.onRegisterSuccess();

    }

    @Override
    public List<PairedDevice> getPairedDevices() {
        if (paylevenApi.isInit())
            return paylevenApi.getPayleven().getPairedDevices();
        return null;
    }

    @Override
    public void prepareDevice(PairedDevice pairedDevice) {
        paymentView.showLoadingWithMessage("Preparation of the Payment Terminal");
        if (paylevenApi.isInit()) {
            paylevenApi.getPayleven().prepareDevice(pairedDevice, new DevicePreparationListener() {
                        @Override
                        public void onDone(Device preparedDevice) {
                            paymentView.hideLoading();
                            paylevenApi.setPreparedDeviceSelected(preparedDevice);
                            paymentView.onDevicePrepared();
                        }

                        @Override
                        public void onError(PaylevenError error) {
                            paymentView.hideLoading();
                            //Show error message
                            paymentView.displayPopUp(error.getMessage());
                            paymentView.onDevicePreparationError();
                        }
                    }
            );
        }
        else {
            paymentView.hideLoading();
            paymentView.onError();
        }
    }

    private GeoLocation getLocation() {
        // By Default : Paris
        float lat = 48.8534100f;
        float lon = 2.3488000f;
        if (apiValue != null && apiValue.getPRConfig() != null && apiValue.getPRConfig().getPRShopConfig() != null) {
            if (apiValue.getPRConfig().getPRShopConfig().lat != null)
                lat = apiValue.getPRConfig().getPRShopConfig().lat;
            if (apiValue.getPRConfig().getPRShopConfig().lon != null)
                lon = apiValue.getPRConfig().getPRShopConfig().lon;
        }
        return new GeoLocation(lat, lon);
    }

    @Override
    public void startPayment(String transactionId, BigDecimal amount, Currency currency) {
        GeoLocation location = getLocation();
        if (paylevenApi.getPreparedDeviceSelected() == null)
            paymentView.showBluetoothError();
        else {
            paymentView.showLoadingWithMessage("Waiting for Payment ...");
            GeoLocation currentLocation = location;
            //TODO REMOVE THIS TEST ONLY
            amount = new BigDecimal(1);
            final PaymentRequest paymentRequest = new PaymentRequest(amount, currency, transactionId, currentLocation);
            PaymentTask paymentTask = paylevenApi.getPayleven().createPaymentTask(paymentRequest, paylevenApi.getPreparedDeviceSelected());
            paymentTask.startAsync(this);
        }
    }

    @Override
    public void sendBillViaEmail(String email) {

    }

    // -------------------------------------------------------------------------------------------
    //                                      Payment
    // -------------------------------------------------------------------------------------------

    @Override
    public void onPaymentComplete(PaymentResult paymentResult) {
        //Handle payment result
        switch (paymentResult.getState()) {
            case APPROVED:
                paylevenApi.setLastPaymentResult(paymentResult);
//                paymentView.displayPopUp("Payment Approved");
                paymentView.onPaymentSuccess();
                break;
            case CANCELED:
                paymentView.displayPopUp("Payment Canceled");
                paymentView.onPaymentError();
                break;
            case DECLINED:
                paymentView.displayPopUp("Payment Declined");
                paymentView.onPaymentError();
                break;
        }
        paymentView.hideLoading();
    }

    private SignatureResponseHandler signatureResponseHandler;
    @Override
    public void onSignatureRequested(SignatureResponseHandler signatureResponseHandler) {
        //Provide signature image to SignatureResponseHandler
        //This method is called when a bank card requires
        //singnture verification instead of a pin
        this.signatureResponseHandler = signatureResponseHandler;
        paymentView.hideLoading();
        paymentView.askSignature();
    }

    @Override
    public void signatureApproved() {
        if (signatureResponseHandler != null && paylevenApi != null && paylevenApi.getSignatureBitmap() != null)
            signatureResponseHandler.confirmSignature(paylevenApi.getSignatureBitmap());
        else
            paymentView.signatureError();
    }

    @Override
    public void signatureDeclined() {
        if (signatureResponseHandler != null && paylevenApi != null && paylevenApi.getSignatureBitmap() != null)
            signatureResponseHandler.declineSignature(paylevenApi.getSignatureBitmap());
        else
            paymentView.signatureError();
        paymentView.displayPopUp("Payment Canceled");
    }

    @Override
    public void onError(PaylevenError paylevenError) {
        //Show error message
        paymentView.hideLoading();
        paymentView.displayPopUp(paylevenError.getMessage());
        paymentView.onPaymentError();
    }

    // -------------------------------------------------------------------------------------------
    //
    // -------------------------------------------------------------------------------------------

    @Override
    public void onViewCreate() {

    }

    @Override
    public void onViewResume() {

    }

    @Override
    public void onViewDestroy() {

    }

}
