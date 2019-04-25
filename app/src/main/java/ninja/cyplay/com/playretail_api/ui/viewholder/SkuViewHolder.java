package ninja.cyplay.com.playretail_api.ui.viewholder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;
import ninja.cyplay.com.playretail_api.R;

/**
 * Created by damien on 05/05/15.
 */
public class SkuViewHolder extends RecyclerView.ViewHolder {

    @Optional
    @InjectView(R.id.card_view)
    public CardView cardView;

    @Optional
    @InjectView(R.id.sku_add_to_basket_button)
    public TextView addToBasketButton;

    @Optional
    @InjectView(R.id.sku_price)
    public TextView price;

    @Optional
    @InjectView(R.id.sku_name)
    public TextView name;

    @Optional
    @InjectView(R.id.sku_ean)
    public TextView ean;

    @Optional
    @InjectView(R.id.sku_stock)
    public TextView stock;

    @Optional
    @InjectView(R.id.button_more_option)
    public View moreOptions;

    public SkuViewHolder(View view) {
        super(view);
        ButterKnife.inject(this, view);
    }
}
