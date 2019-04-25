package ninja.cyplay.com.playretail_api.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ninja.cyplay.com.playretail_api.model.business.Product;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.app.BaseFragment;
import ninja.cyplay.com.playretail_api.ui.activity.ProductActivity;

/**
 * Created by damien on 05/05/15.
 */
public class ProductSummaryFragment extends BaseFragment {

    private ProductShortDescriptionFragment productShortDescriptionFragment;
//    private ProductShareActionBarFragment productShareActionBarFragment;
    private ProductLongDescriptionFragment productLongDescriptionFragment;

    private Product product;

    // -------------------------------------------------------------------------------------------
    //                                      Lifecycle
    // -------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_summary, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        product = ((ProductActivity) getActivity()).product;
        setRetainInstance(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        productShortDescriptionFragment = (ProductShortDescriptionFragment) getChildFragmentManager().findFragmentById(R.id.fragment_product_short_description);
//        productShareActionBarFragment = (ProductShareActionBarFragment) getChildFragmentManager().findFragmentById(R.id.fragment_product_share_action_bar);
        productLongDescriptionFragment = (ProductLongDescriptionFragment) getChildFragmentManager().findFragmentById(R.id.fragment_product_long_description);
    }

}
