package ninja.cyplay.com.playretail_api.ui.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.otto.Bus;

import java.text.DecimalFormat;
import java.util.List;

import javax.inject.Inject;

import ninja.cyplay.com.apilibrary.MVPCleanArchitectureApplication;
import ninja.cyplay.com.playretail_api.model.business.CartItem;
import ninja.cyplay.com.playretail_api.model.business.Product;
import ninja.cyplay.com.playretail_api.model.business.Shop;
import ninja.cyplay.com.playretail_api.model.business.Ska;
import ninja.cyplay.com.playretail_api.model.business.Sku;
import ninja.cyplay.com.playretail_api.model.events.BasketItemPlusPressedEvent;
import ninja.cyplay.com.apilibrary.models.singleton.APIValue;
import ninja.cyplay.com.playretail_api.model.singleton.ShopList;
import ninja.cyplay.com.playretail_api.utils.ColorUtils;
import ninja.cyplay.com.apilibrary.utils.GeoUtil;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.model.business.EFeature;
import ninja.cyplay.com.playretail_api.ui.viewholder.SkaViewHolder;
import ninja.cyplay.com.playretail_api.utils.ConfigHelper;

/**
 * Created by damien on 15/06/15.
 */
public class SkaRecyclerAdapter extends RecyclerView.Adapter<SkaViewHolder> {

    @Inject
    Bus bus;

    @Inject
    APIValue apiValue;

    @Inject
    ShopList shopList;

    private Sku sku;
    private Product PRProduct;
    private List<Ska> skaList;

    private Activity activity;

    private String currentStore_id = null;
    private Shop currentPRShop = null;

    private final DecimalFormat df = new DecimalFormat("0.00##");

    public SkaRecyclerAdapter(Activity activity, List<Ska> skas, Sku sku, Product PRProduct) {
        this.activity = activity;
        this.skaList = skas;
        this.sku = sku;
        this.PRProduct = PRProduct;
        ((MVPCleanArchitectureApplication)activity.getApplication()).inject(this);
        if (apiValue != null && apiValue.getPRConfig() != null
                && apiValue.getPRConfig().getPRShopConfig() != null)
            this.currentStore_id = apiValue.getPRConfig().getPRShopConfig().store_id;
        if (currentStore_id != null)
            currentPRShop = shopList.getShopFromId(currentStore_id);
    }

    @Override
    public SkaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_ska, parent, false);
        SkaViewHolder holder = new SkaViewHolder(v);
        ColorUtils.setBackgroundColor(holder.action, ColorUtils.FeatureColor.PRIMARY_LIGHT);
        return holder;
    }

    @Override
    public void onBindViewHolder(SkaViewHolder holder, int position) {
        Ska ska = skaList.get(position);
        if (ska != null) {
            holder.stock.setText("Stock: " + ska.sks);
            setSkaPrice(holder, ska);
            setBuyButton(holder, ska);
            setInfoWithShop(holder, ska);
            updateBasketInteractionVisibility(holder);
        }
    }

    @Override
    public int getItemCount() {
        return skaList != null ? skaList.size() : 0;
    }


    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    private void setInfoWithShop(SkaViewHolder holder, Ska ska) {
        Shop PRShop = shopList.getShopFromId(String.valueOf(ska.shid));
        if (PRShop != null)
            holder.shopName.setText(PRShop.getName());
        else
            holder.shopName.setText("");

        if (PRShop != null && currentPRShop != null
                && currentPRShop.getLat() != null && currentPRShop.getLon() != null
                && PRShop.getLat() != null && PRShop.getLon() != null) {
            float distance = GeoUtil.distFrom(currentPRShop.getLat().floatValue(), currentPRShop.getLon().floatValue(),
                    PRShop.getLat().floatValue(), PRShop.getLon().floatValue());
            if (distance < 0)
                distance *= -1;
            if (distance == 0)
                holder.distance.setText(activity.getString(R.string.current_shop));
            else
                holder.distance.setText(new DecimalFormat("#.##").format((distance / 1000)) + " (km)");
        }
        else
            holder.distance.setText("");
    }

    private void updateBasketInteractionVisibility(SkaViewHolder holder) {
        if (ConfigHelper.getInstance().isFeatureActivated(EFeature.BASKET))
            holder.action.setVisibility(View.GONE);
    }

    private void setSkaPrice(SkaViewHolder holder, Ska ska) {
        if (ska.skp != null) {
            StringBuilder priceBuilder = new StringBuilder();
            priceBuilder.append(df.format(ska.skp));
            priceBuilder.append(" €");
            holder.price.setText(priceBuilder.toString());
        }
    }

    private void setBuyButton(final SkaViewHolder holder, final Ska ska) {
        if (currentStore_id.equals(String.valueOf(ska.shid)))
            holder.action.setText(activity.getString(R.string.product_sku_buy));
        else if (ska.shid == 99)
            holder.action.setText(activity.getString(R.string.product_sku_command));
        else
            holder.action.setText(activity.getString(R.string.product_sku_reserve));

        holder.action.setClickable(true);
        holder.action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentStore_id.equals(String.valueOf(ska.shid)))
                    bus.post(new BasketItemPlusPressedEvent(new CartItem(PRProduct, sku)));
                else if (ska.shid == 99)
                    Toast.makeText(activity, activity.getString(R.string.product_ordered), Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(activity, activity.getString(R.string.product_reserved), Toast.LENGTH_SHORT).show();
                YoYo.with(Techniques.Pulse).duration(500).playOn(holder.action);
            }
        });
    }
}
