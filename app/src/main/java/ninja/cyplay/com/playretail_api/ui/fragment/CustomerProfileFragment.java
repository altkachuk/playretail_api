package ninja.cyplay.com.playretail_api.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import ninja.cyplay.com.playretail_api.model.business.CustomerDetails;
import ninja.cyplay.com.playretail_api.model.business.Offer;
import ninja.cyplay.com.playretail_api.model.business.Recommendation;
import ninja.cyplay.com.playretail_api.model.events.CustomerInfoTabEvent;
import ninja.cyplay.com.playretail_api.model.events.GetCustomerInfoEvent;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.app.BaseFragment;
import ninja.cyplay.com.playretail_api.ui.activity.CustomerActivity;
import ninja.cyplay.com.playretail_api.ui.custom.CustomerEditProfileCardView;
import ninja.cyplay.com.playretail_api.ui.custom.CustomerProfileBasicInfoCardView;
import ninja.cyplay.com.playretail_api.ui.custom.CustomerProfileRecapCardView;

/**
 * Created by damien on 06/05/15.
 */
public class CustomerProfileFragment extends BaseFragment {

    @Inject
    public Bus bus;

    @InjectView(R.id.customer_basic_info_container)
    CustomerProfileBasicInfoCardView customerProfileBasicInfoCardView;

    @InjectView(R.id.customer_recap_container)
    CustomerProfileRecapCardView customerProfileRecapCardView;

    @InjectView(R.id.customer_edit_info_container)
    CustomerEditProfileCardView customerEditProfileCardView;

    private CustomerDetails customerDetails;
    private Recommendation recommendation;
    private List<Offer> offers;

    // -------------------------------------------------------------------------------------------
    //                                      Lifecycle
    // -------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_customer_profile, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bus.register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getCustomerInfo();
        showCustomerInfo();
    }

    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    private void getCustomerInfo() {
        this.customerDetails = ((CustomerActivity)getActivity()).customerDetails;
        this.offers = ((CustomerActivity)getActivity()).offers;
        this.recommendation = ((CustomerActivity)getActivity()).recommendation;
    }

    private void showCustomerInfo() {
        if (customerProfileBasicInfoCardView != null) {
            customerProfileBasicInfoCardView.setPRCustomerDetails(customerDetails);
            customerProfileBasicInfoCardView.showCustomerInformations();
        }
        if (customerProfileRecapCardView != null) {
            if (getActivity() != null)
                customerProfileRecapCardView.setActivity(getActivity());
            customerProfileRecapCardView.setPRRecommendation(recommendation);
            customerProfileRecapCardView.setOffers(offers);
            customerProfileRecapCardView.showCustomerInformations();
        }
        if (customerEditProfileCardView != null) {
            if (getActivity() != null)
                customerEditProfileCardView.setActivity(getActivity());
            customerEditProfileCardView.setPRCustomerDetails(customerDetails);
            customerEditProfileCardView.showCustomerInformations();
        }
    }

    // -------------------------------------------------------------------------------------------
    //                                       Event(s)
    // -------------------------------------------------------------------------------------------

    @Subscribe
    public void getCustomerInfoEvent(GetCustomerInfoEvent event) {
            getCustomerInfo();
            showCustomerInfo();
    }

    @Subscribe
    public void onTabSelect(CustomerInfoTabEvent customerInfoTabEvent) {
        pagesViews.addPageView(className);
    }

}
