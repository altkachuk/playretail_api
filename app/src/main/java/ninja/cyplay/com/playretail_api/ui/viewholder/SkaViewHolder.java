package ninja.cyplay.com.playretail_api.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;
import ninja.cyplay.com.playretail_api.R;

/**
 * Created by damien on 10/06/15.
 */
public class SkaViewHolder extends RecyclerView.ViewHolder {

    @Optional
    @InjectView(R.id.sku_action_button)
    public TextView action;

    @Optional
    @InjectView(R.id.sku_distance)
    public TextView distance;

    @Optional
    @InjectView(R.id.sku_price)
    public TextView price;

    @Optional
    @InjectView(R.id.sku_shop_name)
    public TextView shopName;

    @Optional
    @InjectView(R.id.sku_stock)
    public TextView stock;

    public SkaViewHolder(View view) {
        super(view);
        ButterKnife.inject(this, view);
    }
}
