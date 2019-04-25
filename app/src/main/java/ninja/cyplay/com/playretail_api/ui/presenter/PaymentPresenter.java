package ninja.cyplay.com.playretail_api.ui.presenter;

import android.content.Context;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

import de.payleven.payment.PairedDevice;
import ninja.cyplay.com.playretail_api.ui.view.PaymentView;

/**
 * Created by damien on 29/06/15.
 */

public interface PaymentPresenter extends Presenter<PaymentView> {

    void register(Context applicationContext);

    List<PairedDevice> getPairedDevices();

    void prepareDevice(PairedDevice pairedDevice);

    void startPayment(String transactionId, BigDecimal amount, Currency currency);

    void signatureApproved();

    void signatureDeclined();

    void sendBillViaEmail(String email);

}
