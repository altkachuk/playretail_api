package ninja.cyplay.com.playretail_api.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.payleven.payment.PaymentResult;
import ninja.cyplay.com.playretail_api.R;

/**
 * Created by damien on 16/07/15.
 */
public class ReceipCardView extends FrameLayout {

    @InjectView(R.id.receip_date)
    TextView date;

    @InjectView(R.id.receip_cvm)
    TextView cvm;

    @InjectView(R.id.receip_card_brand)
    TextView card_brand;


    @InjectView(R.id.receip_amout)
    TextView amount;

    @InjectView(R.id.receip_currency)
    TextView currency;

    @InjectView(R.id.receip_pos_entry)
    TextView pos_entry;


    @InjectView(R.id.receip_auth_code)
    TextView auth_code;

    @InjectView(R.id.receip_card_number)
    TextView card_num;

    @InjectView(R.id.receip_pan_seq)
    TextView pan_seq;

    @InjectView(R.id.receip_aid)
    TextView aid;

    @InjectView(R.id.receip_ref)
    TextView reference;

    private PaymentResult paymentResult;

    public ReceipCardView(Context context) {
        super(context);
        init(context);
    }

    public ReceipCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ReceipCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.card_view_receip, this);
        ButterKnife.inject(this);
    }

    public void setPaymentResult(PaymentResult paymentResult) {
        this.paymentResult = paymentResult;
    }

    public void updateInfo() {
        if (paymentResult != null) {

            date.setText(paymentResult.getDate().toString());

            switch (paymentResult.getAdditionalData().getCvm()) {
                case NONE:
                    cvm.setText("No verification");
                    break;
                case PIN:
                    cvm.setText("Pin verification");
                    break;
                case PIN_AND_SIGNATURE:
                    cvm.setText("Pin and Signature verification");
                    break;
                case SIGNATURE:
                    cvm.setText("Signature verification");
                    break;
            }
            card_brand.setText(paymentResult.getAdditionalData().getCardBrandName());
            amount.setText(String.valueOf(paymentResult.getAmount()));
            currency.setText(paymentResult.getCurrency().toString());
            switch (paymentResult.getAdditionalData().getPosEntryMode()) {
                case ICC:
                    pos_entry.setText("ICC");
                    break;
                case MAGNETIC_READER:
                    pos_entry.setText("Magnetic Reader");
                    break;
                case MANUAL:
                    pos_entry.setText("Manual");
                    break;
            }

            auth_code.setText(paymentResult.getAdditionalData().getAuthCode());
            card_num.setText("****" + paymentResult.getAdditionalData().getPan());
            pan_seq.setText(paymentResult.getAdditionalData().getPanSequence());
            aid.setText(paymentResult.getAdditionalData().getAid());

            reference.setText(paymentResult.getId());
        }
    }
}
