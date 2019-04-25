package ninja.cyplay.com.playretail_api.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ninja.cyplay.com.playretail_api.model.business.Product;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.ui.fragment.ProductModelsListFragment;
import ninja.cyplay.com.playretail_api.ui.fragment.ProductSummaryFragment;
import ninja.cyplay.com.playretail_api.ui.fragment.ProductsRelatedFragment;

public class ProductPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private Product PRProduct;

    private ProductSummaryFragment productSummaryFragment;
    private ProductModelsListFragment productModelsListFragment;
    private ProductsRelatedFragment productRelatedFragment;

    public ProductPagerAdapter(FragmentManager fm, Context context, Product PRProduct) {
        super(fm);
        this.context = context;
        this.PRProduct = PRProduct;
        productSummaryFragment = new ProductSummaryFragment();
        productModelsListFragment = new ProductModelsListFragment();
        productRelatedFragment = new ProductsRelatedFragment();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return productSummaryFragment;
            case 1:
                return productModelsListFragment;
            case 2:
                return productRelatedFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = context.getResources().getString(R.string.product_summary);
                break;
            case 1:
                title = context.getResources().getString(R.string.product_model);
                break;
            case 2:
                title = context.getResources().getString(R.string.product_associate)
                        + "("
                        + (PRProduct != null && PRProduct.rp != null ? String.format("%d", PRProduct.rp.size()) : "0")
                        + ")";
                break;
        }
        return title;
    }

}