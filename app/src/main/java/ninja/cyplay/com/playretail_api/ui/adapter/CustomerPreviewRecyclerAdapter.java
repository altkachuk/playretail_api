package ninja.cyplay.com.playretail_api.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ninja.cyplay.com.playretail_api.model.business.CustomerPreview;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.ui.viewholder.CustomePreviewViewHolder;

/**
 * Created by damien on 12/06/15.
 */
public class CustomerPreviewRecyclerAdapter extends RecyclerView.Adapter<CustomePreviewViewHolder> {

    private List<CustomerPreview> customers;

    public CustomerPreviewRecyclerAdapter(List<CustomerPreview> customers) {
        this.customers = customers;
    }

    public void setCustomers(List<CustomerPreview> customers) {
        if (this.customers == null)
            this.customers = new ArrayList<CustomerPreview>();
        this.customers.clear();
        this.customers.addAll(customers);
        this.notifyDataSetChanged();
    }

    @Override
    public CustomePreviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_customer_preview, parent, false);
        CustomePreviewViewHolder holder = new CustomePreviewViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(CustomePreviewViewHolder holder, int position) {
        CustomerPreview PRCustomerPreview = customers.get(position);
        holder.name.setText(PRCustomerPreview.getFormatName());
    }

    @Override
    public int getItemCount() {
        return customers != null ? customers.size() : 0;
    }
}
