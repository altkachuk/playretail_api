package ninja.cyplay.com.playretail_api.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;
import ninja.cyplay.com.playretail_api.R;

public class SellerViewHolder extends RecyclerView.ViewHolder {

    @Optional
    @InjectView(R.id.sellerCellFirstName)
    public TextView firstName;

    @Optional
    @InjectView(R.id.sellerCellLastName)
    public TextView lastName;

    @Optional
    @InjectView(R.id.sellerCellInitials)
    public TextView initials;

    public SellerViewHolder(View view) {
        super(view);
        ButterKnife.inject(this, view);
    }
}
