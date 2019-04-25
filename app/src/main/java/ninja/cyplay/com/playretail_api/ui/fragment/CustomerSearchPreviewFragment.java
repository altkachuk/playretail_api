package ninja.cyplay.com.playretail_api.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.parceler.Parcels;

import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import ninja.cyplay.com.playretail_api.model.business.CustomerPreview;
import ninja.cyplay.com.playretail_api.model.business.Offer;
import ninja.cyplay.com.playretail_api.model.business.CustomerDetails;
import ninja.cyplay.com.playretail_api.model.business.Product;
import ninja.cyplay.com.playretail_api.model.business.Recommendation;
import ninja.cyplay.com.apilibrary.utils.Constants;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.app.BaseFragment;
import ninja.cyplay.com.playretail_api.ui.activity.CustomerActivity;
import ninja.cyplay.com.playretail_api.ui.activity.CustomerSearchActivity;
import ninja.cyplay.com.playretail_api.ui.adapter.CustomerPreviewRecyclerAdapter;
import ninja.cyplay.com.playretail_api.ui.listener.RecyclerItemClickListener;
import ninja.cyplay.com.playretail_api.ui.presenter.GetCustomerInfoPresenter;
import ninja.cyplay.com.playretail_api.ui.view.GetCustomerInfoView;

/**
 * Created by damien on 06/05/15.
 */
public class CustomerSearchPreviewFragment extends BaseFragment implements GetCustomerInfoView {

    @Inject
    GetCustomerInfoPresenter getCustomerInfoPresenter;

    @InjectView(R.id.recycler_view)
    RecyclerView recyclerView;

    private List<CustomerPreview> customers;

    private CustomerPreviewRecyclerAdapter adapter;

    // -------------------------------------------------------------------------------------------
    //                                      Lifecycle
    // -------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_customer_search_preview, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new CustomerPreviewRecyclerAdapter(customers);
        getCustomerInfoPresenter.initialize();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getCustomerInfoPresenter.setView(this);
        initRecycler();
    }

    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------


    private void initRecycler() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new CustomerSearchItemClickListener()));
    }

    public void loadCustomers(List<CustomerPreview> customers) {
        this.customers = customers;
        if (adapter != null)
            adapter.setCustomers(this.customers);
    }

    // -------------------------------------------------------------------------------------------
    //                                      View Impl
    // -------------------------------------------------------------------------------------------

    @Override
    public void showLoading() {
        ((CustomerSearchActivity) getActivity()).showLoading();
    }

    @Override
    public void hideLoading() {
        ((CustomerSearchActivity) getActivity()).hideLoading();
    }

    @Override
    public void onCustomerInfoSuccess(String cid, CustomerDetails PRCustomerDetails, Recommendation PRRecommendation, List<Offer> offers, List<Product> wishlist) {
        Intent returnIntent = new Intent();
        getActivity().setResult(Activity.RESULT_OK, returnIntent);
        getActivity().finish();
    }

    @Override
    public void displayPopUp(String message) {
        ((CustomerSearchActivity) getActivity()).displayPopUp(message);
    }

    // -------------------------------------------------------------------------------------------
    //                                      Action(s)
    // -------------------------------------------------------------------------------------------

    private class CustomerSearchItemClickListener implements RecyclerItemClickListener.OnItemClickListener {

        @Override
        public void onItemClick(View view, int position) {
            // TODO Handle item click
            CustomerPreview PRCustomerPreview = customers.get(position);
            if (getActivity().getIntent() != null && getActivity().getIntent().hasExtra(Constants.EXTRA_SEARCH_CUSTOMER_CONTEXT))
                getCustomerInfoPresenter.getCustomerInfoFromEAN(PRCustomerPreview.getEAN());
            else {
                Intent intent = new Intent(getActivity(), CustomerActivity.class);
                intent.putExtra(Constants.EXTRA_CUSTOMER_PREVIEW, Parcels.wrap(PRCustomerPreview));
                startActivity(intent);
            }
        }
    }

}
