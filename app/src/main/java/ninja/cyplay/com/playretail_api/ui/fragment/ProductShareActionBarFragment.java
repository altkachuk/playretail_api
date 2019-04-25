package ninja.cyplay.com.playretail_api.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Optional;
import ninja.cyplay.com.apilibrary.models.business.ECustomerActions;
import ninja.cyplay.com.playretail_api.model.business.Product;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.ui.activity.ProductActivity;
import ninja.cyplay.com.playretail_api.utils.ColorUtils;

/**
 * Created by damien on 08/03/16.
 */
public class ProductShareActionBarFragment extends OpinionBaseFragment {

    @Optional
    @InjectView(R.id.button_wishlist)
    ToggleButton wishlist;

    @Optional
    @InjectView(R.id.button_email)
    ToggleButton email;

    private Product product;

    // -------------------------------------------------------------------------------------------
    //                                      Lifecycle
    // -------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_share_action_bar, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        product = ((ProductActivity) getActivity()).product;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        wishlist.setOnClickListener(new WishlistClickListener());
        email.setOnClickListener(new EmailClickListener());
        showProductInformations();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateOpinionViews();
    }

    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    public void showProductInformations() {
        if (product != null) {
            updateOpinionViews();
        }
    }

    public void updateOpinionViews() {
        if (product != null)
            removePreviousOpinions();
        opinionPresenter.updateOpinionsViews(product);
    }

    private void removePreviousOpinions() {
        ColorUtils.removeColorFilter(wishlist);
    }

    private void onButtonClick(ToggleButton button, ECustomerActions action) {
        boolean on = button.isChecked();
        if (!on)
            opinionPresenter.executeAction(getActivity(), action, product);
        else
            setSelectedState(button);
    }

    private void setSelectedState(ToggleButton button) {
        if (button == wishlist) {
            email.setChecked(false);
        }
        else if (button == email) {
            wishlist.setChecked(false);
        }
    }

    // -------------------------------------------------------------------------------------------
    //                                      Action(s)
    // -------------------------------------------------------------------------------------------

    @Optional
    @OnClick(R.id.button_share_screen)
    void shareScreenProduct() {
        if (getActivity() != null)
            Toast.makeText(getActivity(), "Share on Screen", Toast.LENGTH_SHORT).show();
    }

    private class WishlistClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            onButtonClick(wishlist, ECustomerActions.DO_WISHLIST);
        }
    }


    private class EmailClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            setSelectedState(email);
//            onButtonClick(email, ECustomerActions.DO_DISLIKE);
        }
    }

}
