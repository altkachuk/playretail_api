package ninja.cyplay.com.playretail_api.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.daimajia.swipe.util.Attributes;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import ninja.cyplay.com.apilibrary.MVPCleanArchitectureApplication;
import ninja.cyplay.com.playretail_api.model.business.Product;
import ninja.cyplay.com.playretail_api.model.singleton.CustomerContext;
import ninja.cyplay.com.playretail_api.utils.ColorUtils;
import ninja.cyplay.com.playretail_api.utils.ImageUtils;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.ui.viewholder.ProductViewHolder;

public class ProductRecyclerAdapter extends RecyclerSwipeAdapter<ProductViewHolder> {

    @Inject
    CustomerContext customerContext;

    private Activity activity;
    private List<Product> products;

    boolean disableLeftSwipe = true;
    boolean disableRightSwipe = false;

    boolean showProductCategory = true;

    public ProductRecyclerAdapter(Activity activity, List<Product> products) {
        this.activity = activity;
        this.products = products;
        // For injection
        ((MVPCleanArchitectureApplication)activity.getApplication()).inject(this);
        // only one swipe open at a time
        setMode(Attributes.Mode.Single);
    }

    public ProductRecyclerAdapter() {
    }

    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void setDisableLeftSwipe(boolean disableLeftSwipe) {
        this.disableLeftSwipe = disableLeftSwipe;
    }

    public void setDisableRightSwipe(boolean disableRightSwipe) {
        this.disableRightSwipe = disableRightSwipe;
    }

    public void setShowProductCategory(boolean showProductCategory) {
        this.showProductCategory = showProductCategory;
    }

    // -------------------------------------------------------------------------------------------
    //                                      Adapter Impl
    // -------------------------------------------------------------------------------------------

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int i) {
        mItemManger.bindView(holder.itemView, i);
        final Product PRProduct = products.get(i);

        // to be able to link likes and dislikes
        holder.setProduct(PRProduct);
        holder.setContext(activity);
        holder.activateClick();

        ColorUtils.setTextColor(holder.cat, ColorUtils.FeatureColor.PRIMARY_LIGHT);
        ColorUtils.setTextColor(holder.bd, ColorUtils.FeatureColor.NEUTRAL_DARK);

        if (disableLeftSwipe && disableRightSwipe)
            holder.swipeLayout.setSwipeEnabled(false);
        else {
            holder.swipeLayout.setRightSwipeEnabled(!disableRightSwipe);
            holder.swipeLayout.setLeftSwipeEnabled(!disableLeftSwipe);
        }

        if (!showProductCategory)
            holder.cat.setVisibility(View.GONE);

        holder.resetColorIcons();
        if (customerContext != null && customerContext.isProductLike(PRProduct))
            holder.productLike();
        else if (customerContext != null && customerContext.isProductDislike(PRProduct))
            holder.productDislike();

        if (PRProduct != null) {
            holder.cat.setText(PRProduct.cat);
            holder.cat.invalidate();
            holder.pn.setText(PRProduct.pn);
            holder.pn.invalidate();
            holder.bd.setText(PRProduct.skd);
            if (PRProduct.getImgs() != null && PRProduct.getImgs().size() > 0)
                Picasso.with(activity)
                        .load(ImageUtils.getImageUrl(PRProduct.getImgs().get(0), "sd"))
                        .placeholder(R.drawable.img_placeholder)
                        .into(holder.productCellPicture);
//                Glide.with(activity)
//                        .load(ImageUtils.getImageUrl(product.getImgs().get(0), "sd"))
//                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                        .placeholder(R.drawable.img_placeholder)
//                        .into(holder.productCellPicture);
        }
    }

    @Override
    public int getItemCount() {
        return products != null ? products.size() : 0;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe_layout;
    }
}