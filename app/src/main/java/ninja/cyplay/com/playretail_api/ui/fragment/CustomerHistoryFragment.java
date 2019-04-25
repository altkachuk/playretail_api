package ninja.cyplay.com.playretail_api.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.parceler.Parcels;

import javax.inject.Inject;

import butterknife.InjectView;
import ninja.cyplay.com.playretail_api.model.singleton.SellerContext;
import ninja.cyplay.com.apilibrary.utils.Constants;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.app.BaseFragment;
import ninja.cyplay.com.playretail_api.ui.activity.CustomerActivity;
import ninja.cyplay.com.playretail_api.ui.adapter.HomeHistoryRecyclerAdapter;
import ninja.cyplay.com.playretail_api.ui.listener.RecyclerItemClickListener;


/**
 * Created by damien on 30/04/15.
 */
public class CustomerHistoryFragment extends BaseFragment {

    @Inject
    SellerContext sellerContext;

    @InjectView(R.id.no_history_text)
    TextView noHistoryText;

    @InjectView(R.id.recycler_view)
    RecyclerView recyclerView;

    private HomeHistoryRecyclerAdapter adapter;

    // -------------------------------------------------------------------------------------------
    //                                      Lifecycle
    // -------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_customer_history, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new HomeHistoryRecyclerAdapter(sellerContext.getCustomer_history());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fillInformations();
        initRecycler();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sellerContext != null
                && sellerContext.getCustomer_history() != null
                && sellerContext.getCustomer_history().size() > 0)
            noHistoryText.setVisibility(View.GONE);
        else
            noHistoryText.setVisibility(View.VISIBLE);

        if (adapter != null)
            adapter.notifyDataSetChanged();
    }

    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    private void initRecycler() {
//        RecyclerView.LayoutManager layoutManager = new WrapContentLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        // use this setting to improve performance if you know that changes
        recyclerView.setHasFixedSize(true);
        // in content do not change the layout size of the RecyclerView
        // use a linear layout manager
        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.addItemDecoration(new DividerItemDecoration(
//                ContextCompat.getDrawable(getActivity(), R.drawable.recycler_separator), false, false));
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new CustomerItemClick()));
    }

    public void fillInformations() {
        if (sellerContext != null) {
            adapter.setCustomers(sellerContext.getCustomer_history());
            adapter.notifyDataSetChanged();
        }
    }

    // -------------------------------------------------------------------------------------------
    //                                      Action(s)
    // -------------------------------------------------------------------------------------------

    private class CustomerItemClick implements RecyclerItemClickListener.OnItemClickListener {

        @Override
        public void onItemClick(View view, int position) {
            // TODO Handle item click
            Intent intent = new Intent(getActivity(), CustomerActivity.class);
            intent.putExtra(Constants.EXTRA_CUSTOMER_PREVIEW, Parcels.wrap(sellerContext.getCustomer_history().get(position)));
            startActivity(intent);
        }
    }

}
