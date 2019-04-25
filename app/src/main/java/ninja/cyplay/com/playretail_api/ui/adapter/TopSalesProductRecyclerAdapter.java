package ninja.cyplay.com.playretail_api.ui.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import ninja.cyplay.com.playretail_api.model.business.Product;
import ninja.cyplay.com.playretail_api.utils.ImageUtils;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.ui.viewholder.TopSalesProductViewHolder;

/**
 * Created by damien on 12/06/15.
 */
public class TopSalesProductRecyclerAdapter extends RecyclerView.Adapter<TopSalesProductViewHolder> {

    private List<Product> topSalesPRProducts;
    private Activity activity;

    public TopSalesProductRecyclerAdapter(Activity activity, List<Product> topSalesPRProducts) {
        this.activity = activity;
        this.topSalesPRProducts = topSalesPRProducts;
    }

    public void setTopSalesPRProducts(List<Product> topSalesPRProducts) {
        for (Product p : topSalesPRProducts)
            p.setProduct_type(Product.PRODUCT_TYPE.PRODUCT_TYPE_RECURING);
        this.topSalesPRProducts = topSalesPRProducts;
    }

    // method that add recommended products to the top sales bandeau
    public void addTopRecommendedProducts(List<Product> topRecommendedPRProducts) {
        for (Product p : topRecommendedPRProducts)
            p.setProduct_type(Product.PRODUCT_TYPE.PRODUCT_TYPE_RECO);
        Set<Product> newTopList = new LinkedHashSet<Product>(topRecommendedPRProducts);
        newTopList.addAll(this.topSalesPRProducts);
        this.topSalesPRProducts = new ArrayList<>(newTopList);
    }

    @Override
    public TopSalesProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_top_sales, parent, false);
        TopSalesProductViewHolder holder = new TopSalesProductViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(TopSalesProductViewHolder holder, int position) {
        Product PRProduct = topSalesPRProducts.get(position);
        if (PRProduct.isRecoProduct())
                holder.setAsRecommendedProduct();
        else{
            if(PRProduct.isRecurringProduct())
                holder.setAsRecurringProduct();
        }
        holder.textView.setText(PRProduct.pn != null && PRProduct.pn.length() > 0 ? PRProduct.pn : PRProduct.cat);
        Picasso.with(activity)
                .load(ImageUtils.getImageUrl(PRProduct.getImg(), "sd"))
                .placeholder(R.drawable.img_placeholder)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return topSalesPRProducts != null ? topSalesPRProducts.size() : 0;
    }
}
