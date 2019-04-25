package ninja.cyplay.com.playretail_api.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.daimajia.swipe.util.Attributes;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import javax.inject.Inject;

import ninja.cyplay.com.apilibrary.MVPCleanArchitectureApplication;
import ninja.cyplay.com.apilibrary.models.business.PR_BasketItem;
import ninja.cyplay.com.apilibrary.models.singleton.APIValue;
import ninja.cyplay.com.playretail_api.utils.ColorUtils;
import ninja.cyplay.com.playretail_api.utils.ImageUtils;
import ninja.cyplay.com.playretail_api.utils.SkuUtils;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.ui.viewholder.BasketViewHolder;


/**
 * Created by damien on 12/06/15.
 */
public class BasketRecyclerAdapter extends RecyclerSwipeAdapter<BasketViewHolder> {

    @Inject
    APIValue apiValue;

    private final Activity activity;
    private List<PR_BasketItem> items;
    private String store_id;

    public BasketRecyclerAdapter(Activity activity, List<PR_BasketItem> cartItems) {
        this.activity = activity;
        this.items = cartItems;
        // For injection
        ((MVPCleanArchitectureApplication)activity.getApplication()).inject(this);
        // only one swipe open at a time
        setMode(Attributes.Mode.Single);

        if (apiValue != null && apiValue.getPRConfig() != null
                && apiValue.getPRConfig().getPRShopConfig() != null)
            this.store_id = apiValue.getPRConfig().getPRShopConfig().store_id;
    }

    public void setItems(List<PR_BasketItem> items) {
        this.items = items;
    }

    @Override
    public int getSwipeLayoutResourceId(int i) {
        return R.id.swipe_layout;
    }


    @Override
    public int getItemCount() {
        return items != null ? items.size(): 0;
    }

    @Override
    public BasketViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_basket, parent, false);
        return new BasketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BasketViewHolder holder, int i) {
        mItemManger.bindView(holder.itemView, i);
        final PR_BasketItem item = items.get(i);

        // to be able to link add/remove quantity and delete
        holder.setContext(activity);
        holder.setBasketItem(item);

        ColorUtils.setTextColor(holder.cat, ColorUtils.FeatureColor.PRIMARY_LIGHT);
        ColorUtils.setBackgroundColor(holder.quantity, ColorUtils.FeatureColor.PRIMARY_LIGHT);
        ColorUtils.setTextColor(holder.size, ColorUtils.FeatureColor.NEUTRAL_DARK);
        ColorUtils.setTextColor(holder.price, ColorUtils.FeatureColor.PRIMARY_LIGHT);

        if (item != null) {
            holder.cat.setText(item.getCategory());

            holder.pn.setText(item.getDisplay_name());
            if (item.getImg() != null)
                Picasso.with(activity)
                        .load(ImageUtils.getImageUrl(item.getImg(), "sd"))
                        .placeholder(R.drawable.img_placeholder)
                        .into(holder.productCellPicture);

//            holder.size.setText(item.sku.skd != null ? item.sku.skd : "");
            holder.quantity.setText(String.format("%d", (item.getQuantity() != null ? item.getQuantity() : 0)));
            holder.price.setText(item.getUnit_price() + " €");
//            final DecimalFormat df = new DecimalFormat("0.00##");
//            Double skp = SkuUtils.getSkpForShopId(item.sku, store_id);
//            if (skp > 0)
//                holder.price.setText(df.format(skp) + " €");
        }
    }

}
