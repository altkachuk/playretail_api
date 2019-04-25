package ninja.cyplay.com.playretail_api.ui.viewholder;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SwipeLayout;
import com.squareup.otto.Bus;

import org.parceler.Parcels;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Optional;
import ninja.cyplay.com.apilibrary.MVPCleanArchitectureApplication;
import ninja.cyplay.com.playretail_api.model.business.Product;
import ninja.cyplay.com.playretail_api.model.events.ClickAndCollectEvent;
import ninja.cyplay.com.playretail_api.model.events.DislikeProductCellEvent;
import ninja.cyplay.com.playretail_api.model.events.LikeProductCellEvent;
import ninja.cyplay.com.playretail_api.utils.ColorUtils;
import ninja.cyplay.com.apilibrary.utils.Constants;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.ui.activity.ProductActivity;

/**
 * Created by damien on 04/05/15.
 */
public class ProductViewHolder extends RecyclerView.ViewHolder {

    @Inject
    Bus bus;

    @Optional
    @InjectView(R.id.swipe_layout)
    public SwipeLayout swipeLayout;

    @Optional
    @InjectView(R.id.product_cell_cat)
    public TextView cat;

    @Optional
    @InjectView(R.id.product_cell_bd)
    public TextView bd;

    @Optional
    @InjectView(R.id.product_cell_pn)
    public TextView pn;

    @Optional
    @InjectView(R.id.product_cell_liked)
    public ImageView productLiked;

    @Optional
    @InjectView(R.id.button_pickpup)
    public ImageView pickUp;

    @Optional
    @InjectView(R.id.product_cell_picture)
    public ImageView productCellPicture;

    @Optional
    @InjectView(R.id.button_like)
    public ImageView like;

    @Optional
    @InjectView(R.id.button_dislike)
    public ImageView dislike;

    @Optional
    @InjectView(R.id.right_swipe_view)
    public LinearLayout rightView;

    @Optional
    @InjectView(R.id.left_swipe_view)
    public LinearLayout leftView;

    @Optional
    @InjectView(R.id.product_content_layout)
    public LinearLayout contentView;

    @Optional
    @InjectView(R.id.icon_recurring_product)
    public ImageView iconRecurringProduct;

    private Product product;

    private Activity activity;

    public ProductViewHolder(View view) {
        super(view);
        ButterKnife.inject(this, view);
        swipeLayout.addDrag(SwipeLayout.DragEdge.Left, leftView);
        swipeLayout.addDrag(SwipeLayout.DragEdge.Right, rightView);
        // Disable background swipe view click
        leftView.setClickable(true);
        leftView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //do nothing
            }
        });
        rightView.setClickable(true);
        rightView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //do nothing
            }
        });
    }

    public void setContext(Activity activity) {
        if (activity != null) {
            this.activity = activity;
            ((MVPCleanArchitectureApplication)activity.getApplication()).inject(this);
        }
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void activateClick() {
        if (contentView != null) {
            contentView.setClickable(true);
            contentView.setOnClickListener(new ProductClickListener());
        }
    }

    public void productLike() {
        productLiked.setVisibility(View.VISIBLE);
        if (activity != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                productLiked.setImageDrawable(activity.getDrawable(R.drawable.ic_item_liked));
            else
                productLiked.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_item_liked));
        }
        ColorUtils.applyLightningColorFilter(productLiked, ColorUtils.FeatureColor.PRIMARY_LIKE);

        ColorUtils.applyLightningColorFilter(like, ColorUtils.FeatureColor.PRIMARY_LIKE);
        ColorUtils.removeColorFilter(dislike);
    }

    public void productDislike() {
        productLiked.setVisibility(View.VISIBLE);
        if (activity != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                productLiked.setImageDrawable(activity.getDrawable(R.drawable.ic_item_dislike));
            else
                productLiked.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_item_dislike));
        }
        ColorUtils.removeColorFilter(productLiked);

        ColorUtils.applyLightningColorFilter(dislike, ColorUtils.FeatureColor.PRIMARY_LIKE);
        ColorUtils.removeColorFilter(like);
    }

    public void resetColorIcons() {
        productLiked.setVisibility(View.GONE);
        // in case of re-use
        ColorUtils.removeColorFilter(dislike);
        ColorUtils.removeColorFilter(like);
    }

    // Color Code method for the products
    public void initProductDesign() {
        swipeLayout.setBackgroundColor(Color.TRANSPARENT);
        iconRecurringProduct.setVisibility(View.GONE);
    }
    public void setAsRecommendedProduct() {
        swipeLayout.setBackgroundColor(Color.parseColor(ColorUtils.FeatureColor.getColorHex(ColorUtils.FeatureColor.PRIMARY_LIGHT)));
    }
    public void setAsRecurringProduct() {
        iconRecurringProduct.setVisibility(View.VISIBLE);
    }

    // -------------------------------------------------------------------------------------------
    //                                      Action(s)
    // -------------------------------------------------------------------------------------------

    @Optional
    @OnClick(R.id.button_like)
    void onLikeClick() {
        if (product != null) {
            YoYo.with(Techniques.Pulse).duration(300).playOn(like);
            bus.post(new LikeProductCellEvent(product));
        }
    }

    @Optional
    @OnClick(R.id.button_dislike)
    void onDislikeClick() {
        if (product != null) {
            YoYo.with(Techniques.Pulse).duration(300).playOn(dislike);
            bus.post(new DislikeProductCellEvent(product));
        }
    }

    @Optional
    @OnClick(R.id.button_pickpup)
    void onPickUpClick() {
        if (product != null) {
            YoYo.with(Techniques.Pulse).duration(300).playOn(pickUp);
            bus.post(new ClickAndCollectEvent(product));
        }
    }

    private class ProductClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(activity, ProductActivity.class);
            intent.putExtra(Constants.EXTRA_PRODUCT, Parcels.wrap(product));
            activity.startActivity(intent);
        }
    }

}
