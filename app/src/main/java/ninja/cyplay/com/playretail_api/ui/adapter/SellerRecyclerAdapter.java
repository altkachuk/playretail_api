package ninja.cyplay.com.playretail_api.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ninja.cyplay.com.playretail_api.model.business.Seller;
import ninja.cyplay.com.playretail_api.utils.ColorUtils;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.ui.viewholder.SellerViewHolder;

/**
 * Created by damien on 12/06/15.
 */
public class SellerRecyclerAdapter extends RecyclerView.Adapter<SellerViewHolder> {

    private List<Seller> sellers;

    public SellerRecyclerAdapter(List<Seller> sellers) {
        this.sellers = sellers;
    }

    public void setSellers(List<Seller> sellers) {
        this.sellers = sellers;
    }

    @Override
    public SellerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_seller, parent, false);
        SellerViewHolder holder = new SellerViewHolder(v);
        ColorUtils.setBackgroundColor(holder.initials, ColorUtils.FeatureColor.PRIMARY_LIGHT);
//        ColorUtils.setTextColor(holder.initials, ColorUtils.FeatureColor.PRIMARY_DARK);
        ColorUtils.setTextColor(holder.firstName, ColorUtils.FeatureColor.PRIMARY_DARK);
        ColorUtils.setTextColor(holder.lastName, ColorUtils.FeatureColor.PRIMARY_DARK);
        return holder;
    }

    @Override
    public void onBindViewHolder(SellerViewHolder holder, int position) {
        Seller seller = sellers.get(position);
        holder.firstName.setText(seller.getFn());
        holder.lastName.setText(seller.getName());
        holder.initials.setText(seller.getInitials());
    }

    @Override
    public int getItemCount() {
        return sellers != null ? sellers.size() : 0;
    }
}
