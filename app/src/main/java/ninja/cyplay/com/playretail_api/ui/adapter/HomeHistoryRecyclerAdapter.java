package ninja.cyplay.com.playretail_api.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ninja.cyplay.com.playretail_api.model.business.CustomerPreview;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.ui.viewholder.CustomerHistoryViewHolder;

/**
 * Created by damien on 12/06/15.
 */
public class HomeHistoryRecyclerAdapter extends RecyclerView.Adapter<CustomerHistoryViewHolder> {

    private List<CustomerPreview> customers;

    public HomeHistoryRecyclerAdapter(List<CustomerPreview> customers) {
        this.customers = customers;
    }

    public void setCustomers(List<CustomerPreview> customers) {
        this.customers = customers;
    }

    @Override
    public CustomerHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_customer_history, parent, false);
        CustomerHistoryViewHolder holder = new CustomerHistoryViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(CustomerHistoryViewHolder holder, int position) {
        CustomerPreview customer = customers.get(position);

        holder.name.setText(customer.getFormatName());
        if (customer.cd != null) {
            if (customer.cd.ad3 != null) {
                holder.location.setVisibility(View.VISIBLE);
                holder.location.setText(customer.cd.ad3);
            }
            else
                holder.location.setVisibility(View.GONE);
            holder.location_city.setText(customer.cd.zc + " " + customer.cd.cit);
        }
        holder.inHouse.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return customers != null ? customers.size() : 0;
    }

}
