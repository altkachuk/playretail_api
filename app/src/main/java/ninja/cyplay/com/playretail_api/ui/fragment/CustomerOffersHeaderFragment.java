package ninja.cyplay.com.playretail_api.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.InjectView;
import ninja.cyplay.com.playretail_api.model.business.CustomerDetails;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.app.BaseFragment;
import ninja.cyplay.com.playretail_api.utils.ColorUtils;

/**
 * Created by damien on 08/03/16.
 */
public class CustomerOffersHeaderFragment extends BaseFragment {

    @InjectView(R.id.points_value_text_view)
    TextView pointsValue;

    @InjectView(R.id.freq_value_text_view)
    TextView freqValue;

    private CustomerDetails customerDetails;

    // -------------------------------------------------------------------------------------------
    //                                      Lifecycle
    // -------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_customer_offers_header, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateDesign();
    }

    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    public void setCustomerDetails(CustomerDetails customerDetails) {
        this.customerDetails = customerDetails;
        updateView();
    }

    private void updateDesign() {
        ColorUtils.setTextColor(pointsValue, ColorUtils.FeatureColor.PRIMARY_DARK);
        ColorUtils.setTextColor(freqValue, ColorUtils.FeatureColor.PRIMARY_DARK);
    }

    private void updateView() {
        if (customerDetails != null) {
            pointsValue.setText(customerDetails.pc);
            freqValue.setText(customerDetails.pdc);
        }
    }

}
