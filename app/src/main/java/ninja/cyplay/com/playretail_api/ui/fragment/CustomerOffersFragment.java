package ninja.cyplay.com.playretail_api.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import ninja.cyplay.com.playretail_api.model.events.GetCustomerInfoEvent;
import ninja.cyplay.com.playretail_api.model.events.OfferTabEvent;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.app.BaseFragment;
import ninja.cyplay.com.playretail_api.ui.activity.CustomerActivity;
import ninja.cyplay.com.playretail_api.ui.adapter.OfferAdapter;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by damien on 07/05/15.
 */
public class CustomerOffersFragment extends BaseFragment {

    @Inject
    public Bus bus;

    @InjectView(R.id.list_view)
    StickyListHeadersListView listView;

    private OfferAdapter adapter;

    private List<Offer> offers;

    private CustomerDetails PRCustomerDetails;

    private CustomerOffersHeaderFragment headerFragment;

    // -------------------------------------------------------------------------------------------
    //                                      Lifecycle
    // -------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_customer_offers, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bus.register(this);
        adapter = new OfferAdapter(getActivity(), offers);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView.setAdapter(adapter);
        headerFragment = (CustomerOffersHeaderFragment) getChildFragmentManager().findFragmentById(R.id.fragmment_header);
        loadOffers();
    }

    public void onDestroyView() {
        super.onDestroyView();
        try {
            // removing fragment on destroy to avoid duplicated id
            FragmentManager fm = getChildFragmentManager();
            Fragment fragment = (fm.findFragmentById(R.id.fragmment_header));
            FragmentTransaction ft = fm.beginTransaction();
            ft.remove(fragment);
            ft.commitAllowingStateLoss();
        } catch (Exception e) {}
    }

    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    private void loadOffers() {
        offers = ((CustomerActivity)getActivity()).offers;
        PRCustomerDetails = ((CustomerActivity)getActivity()).customerDetails;
        if (offers != null) {
            adapter.setOffers(this.offers);
            adapter.notifyDataSetChanged();
        }
        headerFragment.setCustomerDetails(PRCustomerDetails);
    }

    // -------------------------------------------------------------------------------------------
    //                                       Event(s)
    // -------------------------------------------------------------------------------------------

    @Subscribe
    public void getCustomerInfoEvent(GetCustomerInfoEvent event) {
        loadOffers();
    }

    @Subscribe
    public void onTabSelect(OfferTabEvent customerInfoTabEvent) {
        pagesViews.addPageView(className);
    }

}
