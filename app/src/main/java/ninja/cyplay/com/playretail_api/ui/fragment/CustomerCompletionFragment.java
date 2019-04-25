package ninja.cyplay.com.playretail_api.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.InjectView;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.app.BaseFragment;
import ninja.cyplay.com.playretail_api.ui.activity.CustomerSearchActivity;
import ninja.cyplay.com.playretail_api.ui.adapter.CustomerCompletionRecyclerAdapter;
import ninja.cyplay.com.playretail_api.ui.listener.RecyclerItemClickListener;

/**
 * Created by damien on 06/05/15.
 */
public class CustomerCompletionFragment extends BaseFragment {

    @InjectView(R.id.recycler_view)
    RecyclerView recyclerView;

    private List<String> customers;
    private CustomerCompletionRecyclerAdapter adapter;

    // -------------------------------------------------------------------------------------------
    //                                      Lifecycle
    // -------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_customer_completion, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new CustomerCompletionRecyclerAdapter(customers);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new CustomerCompletionItemClickListener()));
    }

    public void loadCustomers(List<String> customers) {
        this.customers = customers;
        if (adapter != null)
            adapter.setCustomers(this.customers);
    }

    // -------------------------------------------------------------------------------------------
    //                                      Action(s)
    // -------------------------------------------------------------------------------------------

    private class CustomerCompletionItemClickListener implements RecyclerItemClickListener.OnItemClickListener {

        @Override
        public void onItemClick(View view, int position) {
            // TODO Handle item click
            ((CustomerSearchActivity) getActivity()).searchCustomerFromCompleteName(customers.get(position));
        }
    }

}