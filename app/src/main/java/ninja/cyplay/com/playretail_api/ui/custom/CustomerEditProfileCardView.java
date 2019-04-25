package ninja.cyplay.com.playretail_api.ui.custom;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;

import org.parceler.Parcels;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Optional;
import ninja.cyplay.com.playretail_api.model.business.CustomerDetails;
import ninja.cyplay.com.playretail_api.ui.activity.FormActivity;
import ninja.cyplay.com.playretail_api.utils.ColorUtils;
import ninja.cyplay.com.apilibrary.utils.Constants;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.ui.activity.FormDynamicActivity;

/**
 * Created by damien on 17/06/15.
 */
public class CustomerEditProfileCardView extends FrameLayout {

    @Optional
    @InjectView(R.id.customer_mail)
    ImageView customerMail;

    @Optional
    @InjectView(R.id.customer_phone)
    ImageView customerPhone;

    @Optional
    @InjectView(R.id.customer_home)
    ImageView customerAddress;

    private Activity activity;

    private CustomerDetails customerDetails;

    private boolean isloaded = false;

    public CustomerEditProfileCardView(Context context) {
        super(context);
        init(context);
    }

    public CustomerEditProfileCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomerEditProfileCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.card_view_customer_edit_profile, this);
        ButterKnife.inject(this);
        isloaded = true;
        showCustomerInformations();
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setPRCustomerDetails(CustomerDetails PRCustomerDetails) {
        this.customerDetails = PRCustomerDetails;
    }

    public void showCustomerInformations() {
        if (isloaded && customerDetails != null) {
            if (customerDetails.mn != null && !customerDetails.mn.isEmpty())
                ColorUtils.applyLightningColorFilter(customerPhone, ColorUtils.FeatureColor.PRIMARY_LIKE);
            if (customerDetails.email != null && !customerDetails.email.isEmpty())
                ColorUtils.applyLightningColorFilter(customerMail, ColorUtils.FeatureColor.PRIMARY_LIKE);
            if ((customerDetails.ad1 != null && !customerDetails.ad1.isEmpty())
                    || (customerDetails.ad2 != null && !customerDetails.ad2.isEmpty())
                    || (customerDetails.ad3 != null && !customerDetails.ad3.isEmpty()))
                ColorUtils.applyLightningColorFilter(customerAddress, ColorUtils.FeatureColor.PRIMARY_LIKE);
        }
    }

    @OnClick(R.id.customer_edit)
    public void showEditFragment() {
        startCustomerEditActivity(Constants.EXTRA_FORM_EDIT);
    }

    @OnClick(R.id.customer_home)
    public void addressAction() {
        startCustomerEditActivity(Constants.EXTRA_FORM_EDIT_ADDRESS);
    }

    @OnClick(R.id.customer_mail)
    public void emailAction() {
        startCustomerEditActivity(Constants.EXTRA_FORM_EDIT_MAIL);
    }

    @OnClick(R.id.customer_phone)
    public void phoneAction() {
        startCustomerEditActivity(Constants.EXTRA_FORM_EDIT_PHONE);
    }

    void startCustomerEditActivity(int focus) {
        Intent intent = new Intent(activity, FormActivity.class);
//        Intent intent = new Intent(activity, CustomerEditActivity.class);
        intent.putExtra(Constants.EXTRA_CUSTOMER_EDIT_FOCUS_ID, focus);
        intent.putExtra(Constants.EXTRA_CUSTOMER, Parcels.wrap(customerDetails));
        intent.putExtra(Constants.EXTRA_CUSTOMER_FORM_MODE_EDIT, true);
        activity.startActivity(intent);
    }

}