package ninja.cyplay.com.playretail_api.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.daimajia.swipe.util.Attributes;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import ninja.cyplay.com.apilibrary.MVPCleanArchitectureApplication;
import ninja.cyplay.com.playretail_api.model.business.Product;
import ninja.cyplay.com.playretail_api.model.singleton.CustomerContext;
import ninja.cyplay.com.playretail_api.utils.ColorUtils;
import ninja.cyplay.com.playretail_api.utils.ImageUtils;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.ui.viewholder.ProductViewHolder;

public class PlaylistProductRecyclerAdapter extends RecyclerSwipeAdapter<ProductViewHolder> {

    @Inject
    CustomerContext customerContext;

    private Activity activity;
    private List<Product> product;

    boolean disableLeftSwipe = true;
    boolean disableRightSwipe = false;

    public PlaylistProductRecyclerAdapter(Activity activity, List<Product> product) {
        this.activity = activity;
        this.product = product;
        // For injection
        ((MVPCleanArchitectureApplication)activity.getApplication()).inject(this);
        // only one swipe open at a time
        setMode(Attributes.Mode.Single);
    }

    public PlaylistProductRecyclerAdapter() {
    }

    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    public void setProduct(List<Product> product) {
        this.product = product;
    }

    public void loadProducts(List<Product> completelist  , int number_of_product, Product.PRODUCT_TYPE type) {
        if (completelist != null ) {
            List<Product> playlist;
            // set type for products
            for(Product p: completelist)
                p.setProduct_type(type);
            if(number_of_product<completelist.size())
                playlist= completelist.subList(0, number_of_product);
            else
                playlist = completelist;
            Set<Product> set = new LinkedHashSet<Product>(this.product);
            set.addAll(playlist);
            this.product = new ArrayList<Product>(set);
            notifyDataSetChanged();
        }
    }

    public void setDisableLeftSwipe(boolean disableLeftSwipe) {
        this.disableLeftSwipe = disableLeftSwipe;
    }

    public void setDisableRightSwipe(boolean disableRightSwipe) {
        this.disableRightSwipe = disableRightSwipe;
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

        final Product PRProduct = product.get(i);

        mItemManger.bindView(holder.itemView, i);

        holder.initProductDesign();

        // add color code depends on product type
        if (PRProduct.isRecoProduct())
            holder.setAsRecommendedProduct();
        else{
            if(PRProduct.isRecurringProduct())
                holder.setAsRecurringProduct();
        }
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

        holder.resetColorIcons();
        if (customerContext != null && customerContext.isProductLike(PRProduct))
            holder.productLike();
        else if (customerContext != null && customerContext.isProductDislike(PRProduct))
            holder.productDislike();

        // content binding
        if (PRProduct != null) {
            holder.cat.setText(PRProduct.cat);
            holder.pn.setText(PRProduct.pn);
            holder.bd.setText(PRProduct.skd);
            Picasso.with(activity)
                    .load(ImageUtils.getImageUrl(PRProduct.getImg(), "sd"))
                    .placeholder(R.drawable.img_placeholder)
                    .into(holder.productCellPicture);
        }
    }

    @Override
    public int getItemCount() {
        return product != null ? product.size() : 0;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe_layout;
    }
}