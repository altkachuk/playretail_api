package ninja.cyplay.com.playretail_api.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.otto.Bus;

import org.parceler.Parcels;

import java.text.DecimalFormat;
import java.util.List;

import javax.inject.Inject;

import ninja.cyplay.com.apilibrary.MVPCleanArchitectureApplication;
import ninja.cyplay.com.playretail_api.model.business.CartItem;
import ninja.cyplay.com.playretail_api.model.business.Product;
import ninja.cyplay.com.playretail_api.model.business.Sku;
import ninja.cyplay.com.playretail_api.model.events.BasketItemPlusPressedEvent;
import ninja.cyplay.com.apilibrary.models.singleton.APIValue;
import ninja.cyplay.com.playretail_api.utils.ColorUtils;
import ninja.cyplay.com.apilibrary.utils.Constants;
import ninja.cyplay.com.playretail_api.utils.SkuUtils;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.model.business.EFeature;
import ninja.cyplay.com.playretail_api.ui.activity.SkaListActivity;
import ninja.cyplay.com.playretail_api.ui.presenter.BasketPresenter;
import ninja.cyplay.com.playretail_api.ui.view.BasketView;
import ninja.cyplay.com.playretail_api.ui.viewholder.SkuViewHolder;
import ninja.cyplay.com.playretail_api.utils.ConfigHelper;

/**
 * Created by damien on 15/06/15.
 */
public class SkusRecyclerAdapter extends RecyclerView.Adapter<SkuViewHolder> {

    @Inject
    Bus bus;

    @Inject
    APIValue apiValue;

    @Inject
    BasketPresenter basketPresenter;

    private Activity activity;
    private Product PRProduct;
    private List<Sku> skus;
    private BasketView basketView;

    private String store_id = null;

    private final DecimalFormat df = new DecimalFormat("0.00##");

    public SkusRecyclerAdapter(Activity activity, List<Sku> skus, Product PRProduct, BasketView basketView) {
        this.activity = activity;
        this.skus = skus;
        this.PRProduct = PRProduct;
        this.basketView = basketView;

        ((MVPCleanArchitectureApplication)activity.getApplication()).inject(this);
        basketPresenter.setView(basketView);
        if (apiValue != null && apiValue.getPRConfig() != null
                && apiValue.getPRConfig().getPRShopConfig() != null)
            this.store_id = apiValue.getPRConfig().getPRShopConfig().store_id;
    }

    @Override
    public SkuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_sku, parent, false);
        SkuViewHolder holder = new SkuViewHolder(v);
        ColorUtils.setBackgroundColor(holder.addToBasketButton, ColorUtils.FeatureColor.PRIMARY_LIGHT);
        return holder;
    }

    @Override
    public void onBindViewHolder(final SkuViewHolder holder, int position) {
        final Sku sku = skus.get(position);

        //TOTO remove this if not demo
        if (sku.skp != null)
            holder.price.setText(df.format(sku.skp) + " €");
        Double skp = SkuUtils.getSkpForShopId(sku, store_id);
        if (skp > 0)
            holder.price.setText(df.format(skp) + " €");

        String stock = SkuUtils.getSksForShopId(sku, store_id);
        if (stock != null)
            holder.stock.setText("Stock: " + stock);
        else
            holder.stock.setText("");

        holder.ean.setText(String.valueOf(sku.skid));
        holder.name.setText(activity.getString(R.string.product_model_word) + " " + sku.skd);
        holder.price.setClickable(true);
        holder.addToBasketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bus.post(new BasketItemPlusPressedEvent(new CartItem(PRProduct, sku)));
                YoYo.with(Techniques.Pulse).duration(500).playOn(holder.addToBasketButton);
            }
        });
        setCellAction(holder, sku);
        updateBasketInteractionVisibility(holder);
    }

    private void updateBasketInteractionVisibility(SkuViewHolder holder) {
        if (ConfigHelper.getInstance().isFeatureActivated(EFeature.BASKET))
        {
            holder.addToBasketButton.setVisibility(View.GONE);
            holder.moreOptions.setVisibility(View.GONE);
        }
    }

    private void setCellAction(final SkuViewHolder holder, final Sku sku) {
        holder.moreOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                basketPresenter.moreOption(activity, PRProduct, sku);
            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, SkaListActivity.class);
                intent.putExtra(Constants.EXTRA_PRODUCT, Parcels.wrap(PRProduct));
                intent.putExtra(Constants.EXTRA_SKU, Parcels.wrap(sku));
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return skus != null ? skus.size() : 0;
    }

}

