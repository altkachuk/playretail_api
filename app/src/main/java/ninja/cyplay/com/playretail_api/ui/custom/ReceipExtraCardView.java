package ninja.cyplay.com.playretail_api.ui.custom;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;
import ninja.cyplay.com.apilibrary.MVPCleanArchitectureApplication;
import ninja.cyplay.com.playretail_api.model.singleton.CustomerContext;
import ninja.cyplay.com.playretail_api.model.singleton.SellerContext;
import ninja.cyplay.com.playretail_api.R;

/**
 * Created by damien on 17/07/15.
 */
public class ReceipExtraCardView extends FrameLayout {

    @Inject
    SellerContext sellerContext;

    @Inject
    CustomerContext customerContext;

    @Optional
    @InjectView(R.id.receip_seller_name)
    TextView seller_name;

    @Optional
    @InjectView(R.id.receip_customer_name)
    TextView customer_name;

    private Activity activity;

    public ReceipExtraCardView(Context context) {
        super(context);
        init(context);
    }

    public ReceipExtraCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ReceipExtraCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.card_view_receip_extra, this);
        ButterKnife.inject(this);
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
        ((MVPCleanArchitectureApplication)activity.getApplication()).inject(this);
    }

    public void updateInfo() {
        if (sellerContext != null && sellerContext.getSeller() != null)
            seller_name.setText(sellerContext.getSeller().getFormatName());

        if (customerContext != null && customerContext.isCustomerIdentified())
            customer_name.setText(customerContext.getCustomer().getFormatName());
        else
            customer_name.setText("Unknown");
    }
}
