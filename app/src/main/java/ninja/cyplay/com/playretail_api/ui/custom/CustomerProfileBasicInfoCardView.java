package ninja.cyplay.com.playretail_api.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;
import ninja.cyplay.com.playretail_api.model.business.CustomerDetails;
import ninja.cyplay.com.playretail_api.R;

/**
 * Created by damien on 17/06/15.
 */
public class CustomerProfileBasicInfoCardView extends FrameLayout {

    @Optional
    @InjectView(R.id.pc_txtview)
    TextView customerPoints;

    @Optional
    @InjectView(R.id.pm_txtview)
    TextView customerPm;

    @Optional
    @InjectView(R.id.date_textView)
    TextView customerLastVisit;

    @Optional
    @InjectView(R.id.axe_textView)
    TextView customerAxes;

    @Optional
    @InjectView(R.id.type_textView)
    TextView customerType;

    private CustomerDetails PRCustomerDetails;

    private boolean isloaded = false;

    private final static SimpleDateFormat IN_FORMAT = new SimpleDateFormat("yyyyMMdd");
    private final static SimpleDateFormat OUT_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

    public CustomerProfileBasicInfoCardView(Context context) {
        super(context);
        init(context);
    }

    public CustomerProfileBasicInfoCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomerProfileBasicInfoCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.card_view_customer_basic_profile, this);
        ButterKnife.inject(this);
        isloaded = true;
        showCustomerInformations();
    }

    public void setPRCustomerDetails(CustomerDetails PRCustomerDetails) {
        this.PRCustomerDetails = PRCustomerDetails;
    }

    public void showCustomerInformations() {
        if (isloaded && PRCustomerDetails != null) {
            customerPoints.setText(PRCustomerDetails.pc);
            customerPm.setText(PRCustomerDetails.ab + "€");
            customerAxes.setText(PRCustomerDetails.cc);
            customerType.setText(PRCustomerDetails.infononachat);
            try {
                Date parseDate = IN_FORMAT.parse(PRCustomerDetails.lvd);
                customerLastVisit.setText(OUT_FORMAT.format(parseDate));
            } catch (Exception e) {
                customerLastVisit.setText("00/00/0000");
            }
        }
    }

}
