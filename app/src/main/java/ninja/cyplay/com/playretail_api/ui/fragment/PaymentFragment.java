package ninja.cyplay.com.playretail_api.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Currency;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;
import de.payleven.payment.PairedDevice;
import ninja.cyplay.com.playretail_api.model.business.CartItem;
import ninja.cyplay.com.apilibrary.models.singleton.APIValue;
import ninja.cyplay.com.apilibrary.utils.Constants;
import ninja.cyplay.com.apilibrary.utils.DialogUtils;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.app.BaseFragment;
import ninja.cyplay.com.playretail_api.ui.activity.ReceipActivity;
import ninja.cyplay.com.playretail_api.ui.activity.SignatureActivity;
import ninja.cyplay.com.playretail_api.ui.adapter.PairedDeviceSpinnerAdapter;
import ninja.cyplay.com.playretail_api.ui.custom.MessageProgressDialog;
import ninja.cyplay.com.playretail_api.ui.presenter.BasketPresenter;
import ninja.cyplay.com.playretail_api.ui.presenter.PaymentPresenter;
import ninja.cyplay.com.playretail_api.ui.view.PaymentView;

/**
 * Created by damien on 29/06/15.
 */
public class PaymentFragment extends BaseFragment implements PaymentView {

    @Inject
    APIValue apiValue;

    @Inject
    PaymentPresenter paymentPresenter;

    @Inject
    BasketPresenter basketPresenter;

    @InjectView(R.id.recap_text_view)
    TextView recapTextView;

    @InjectView(R.id.total_price)
    TextView totalPrice;

    @InjectView(R.id.device_paired_spinner)
    Spinner devicePairedSpinner;

    @InjectView(R.id.reload_paired_device_list)
    ImageView reloadDeviceImageView;

    private MessageProgressDialog progress;

    private List<PairedDevice> pairedDevices;

    private PairedDeviceSpinnerAdapter pairedDeviceAdapter;

    private final static DecimalFormat df = new DecimalFormat("0.00##");

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_payment, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pairedDeviceAdapter = new PairedDeviceSpinnerAdapter(getActivity(), pairedDevices);
        paymentPresenter.initialize();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initLoadingDialog();
        paymentPresenter.setView(this);
        paymentPresenter.register(getActivity().getApplicationContext());
        devicePairedSpinner.setAdapter(pairedDeviceAdapter);
        showRecapBasket();
        updatePrice();
    }

    @Override
    public void onResume() {
        super.onResume();
        List<PairedDevice> pairedDeviceList = paymentPresenter.getPairedDevices();
        if (pairedDeviceList != null && pairedDeviceList.size() > 0)
            initDeviceSpinner(pairedDeviceList);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.RESULT_SIGNATURE_ACTIVITY) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    paymentPresenter.signatureApproved();
                    break;
                case Activity.RESULT_CANCELED:
                    paymentPresenter.signatureDeclined();
                    break;
            }
        }
    }

    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    private void showRecapBasket() {
        StringBuilder builder = new StringBuilder();
        List<CartItem> cartItemList = basketPresenter.getCartItems();
        if (cartItemList != null) {
            for (int i = 0 ; i < cartItemList.size() ; i++) {
                CartItem item = cartItemList.get(i);
                if (item.product != null
                        && item.sku != null
                        && item.sku.quantity != null
                        && item.sku.quantity > 0) {
                    builder.append("<b>");
                    builder.append(item.sku.quantity);
                    builder.append(" x ");
                    builder.append("</b>");
                    builder.append(item.sku.skd);
                    builder.append("<br>");
                    builder.append("&nbsp;&nbsp;&nbsp;&nbsp;");
                    builder.append(item.product.pn);
                    if (i + 1 < cartItemList.size())
                        builder.append("<br><br>");
                }
            }
        }
        recapTextView.setText(Html.fromHtml(builder.toString()));
    }

    private void initLoadingDialog() {
        // initialize
        progress = new MessageProgressDialog(getActivity());
    }

    private void updatePrice() {
        Double total_price = basketPresenter.getTotalPrice();
        if (total_price > 0)
            totalPrice.setText(df.format(total_price) + " €");
        else
            totalPrice.setText("N/A");
//        }
    }

    private void initDeviceSpinner(final List<PairedDevice> pairedDeviceList) {
        if (pairedDeviceList != null && pairedDeviceList.size() > 0) {
            pairedDevices = pairedDeviceList;
//            for (int i = 0 ; i < pairedDeviceList.size(); i++)
//                pairedDevices.add(pairedDeviceList.get(i).getName());
            pairedDeviceAdapter.setValues(pairedDevices);
            pairedDeviceAdapter.notifyDataSetChanged();
        }
        else
            DialogUtils.showBluetoothDialog(getActivity());
    }

    // -------------------------------------------------------------------------------------------
    //                                      Action(s)
    // -------------------------------------------------------------------------------------------

    @OnClick(R.id.valid_device_selection_button)
    void onDeviceSelectedClick() {
        if (pairedDevices != null && pairedDevices.size() > devicePairedSpinner.getSelectedItemPosition()) {
            paymentPresenter.prepareDevice(pairedDevices.get(devicePairedSpinner.getSelectedItemPosition()));
        }
        else
            showBluetoothError();
    }

    @OnClick(R.id.reload_paired_device_list)
    void reloadDevicePairedList() {
        Animation spinAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_once);
        reloadDeviceImageView.startAnimation(spinAnim);
        initDeviceSpinner(paymentPresenter.getPairedDevices());
    }

    // -------------------------------------------------------------------------------------------
    //                                      View Impl
    // -------------------------------------------------------------------------------------------

    @Override
    public void showLoading() {
        if (progress != null)
            progress.show();
    }

    @Override
    public void showLoadingWithMessage(String message) {
        if (progress != null)
            progress.showWithMessage(message);
    }

    @Override
    public void showBluetoothError() {
        DialogUtils.showBluetoothDialog(getActivity());
    }

    @Override
    public void hideLoading() {
        if (progress != null)
            progress.dismiss();
    }

    @Override
    public void onPaymentSuccess() {
        new MaterialDialog.Builder(getActivity())
                .title("Payment completed")
                .content("Do you want to see the receip?")
                .positiveText("Receip")
                .negativeText("Finish")
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        Intent intent = new Intent(getActivity(), ReceipActivity.class);
                        startActivity(intent);
                    }
                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        Intent intent = new Intent();
                        getActivity().setResult(Activity.RESULT_OK, intent);
                        getActivity().finish();
                    }
                })
                .show();
    }

    @Override
    public void onPaymentError() {

    }

    @Override
    public void onRegisterSuccess() {
        initDeviceSpinner(paymentPresenter.getPairedDevices());
    }

    @Override
    public void onDevicePrepared() {
        // Do Payment
        paymentPresenter.startPayment(
                apiValue.getDeviceId() + "__" + new Date().getTime(),
                BigDecimal.valueOf(basketPresenter.getTotalPrice()),
                Currency.getInstance("EUR")
        );
    }

    @Override
    public void onDevicePreparationError() {

    }

    @Override
    public void onRegisterError() {

    }

    @Override
    public void askSignature() {
        Intent intent = new Intent(getActivity(), SignatureActivity.class);
        startActivityForResult(intent, Constants.RESULT_SIGNATURE_ACTIVITY);
    }

    @Override
    public void signatureError() {

    }
}
