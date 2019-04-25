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

/**
 * Created by damien on 25/06/15.
 */
public class ProductViewPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private Product PRProduct;

    private final ProductSummaryFragment productSummaryFragment;
    private final ProductModelsListFragment productModelsListFragment;
    private final ProductsRelatedFragment productRelatedFragment;

    public ProductViewPagerAdapter(FragmentManager fm, Context context, Product PRProduct) {
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
                title = context.getResources().getString(R.string.product_associate);
                break;
        }
        return title;
    }

    @Override
    public int getCount() {
        if (PRProduct == null || PRProduct.rp == null || PRProduct.rp.size() == 0)
            return 2;
        return 3;
    }
}