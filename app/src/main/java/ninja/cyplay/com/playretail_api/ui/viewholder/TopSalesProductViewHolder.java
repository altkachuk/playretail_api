package ninja.cyplay.com.playretail_api.ui.viewholder;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;
import ninja.cyplay.com.playretail_api.utils.ColorUtils;
import ninja.cyplay.com.playretail_api.R;

/**
 * Created by damien on 04/05/15.
 */
public class TopSalesProductViewHolder extends RecyclerView.ViewHolder {

    @Optional
    @InjectView(R.id.image_view)
    public ImageView imageView;

    @Optional
    @InjectView(R.id.text_view)
    public TextView textView;

    @Optional
    @InjectView(R.id.icon_recurring_product)
    public ImageView iconRecurringProduct;


    public TopSalesProductViewHolder(View view) {
        super(view);
        ButterKnife.inject(this, view);
    }

    public void setAsRecommendedProduct() {
        textView.setBackgroundColor(Color.parseColor(ColorUtils.FeatureColor.getColorHex(ColorUtils.FeatureColor.PRIMARY_LIGHT)));
    }
    public void setAsRecurringProduct() {
        iconRecurringProduct.setVisibility(View.VISIBLE);
    }
}
