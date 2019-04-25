package ninja.cyplay.com.playretail_api.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.ui.viewholder.CustomerCompletionViewHolder;

/**
 * Created by damien on 12/06/15.
 */
public class CustomerCompletionRecyclerAdapter  extends RecyclerView.Adapter<CustomerCompletionViewHolder> {

    private List<String> customers;

    public CustomerCompletionRecyclerAdapter(List<String> customers) {
        this.customers = customers;
    }

    public void setCustomers(List<String> customers) {
        if (this.customers == null)
            this.customers = new ArrayList<>();
        this.customers.clear();
        if(customers != null)
            this.customers.addAll(customers);
        this.notifyDataSetChanged();
    }

    @Override
    public CustomerCompletionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_customer_completion, parent, false);
        CustomerCompletionViewHolder holder = new CustomerCompletionViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(CustomerCompletionViewHolder holder, int position) {
        String customer = customers.get(position);
        holder.name.setText(customer);
    }

    @Override
    public int getItemCount() {
        return customers != null ? customers.size() : 0;
    }
}
