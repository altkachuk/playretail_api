package ninja.cyplay.com.playretail_api.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Optional;
import ninja.cyplay.com.apilibrary.models.business.ECustomerActions;
import ninja.cyplay.com.playretail_api.model.business.Product;
import ninja.cyplay.com.apilibrary.utils.Constants;
import ninja.cyplay.com.playretail_api.utils.ImageUtils;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.ui.activity.FullScreenImageGalleryActivity;
import ninja.cyplay.com.playretail_api.ui.activity.ProductActivity;
import ninja.cyplay.com.playretail_api.ui.presenter.OpinionPresenter;
import ninja.cyplay.com.playretail_api.utils.ColorUtils;

/**
 * Created by damien on 08/03/16.
 */
public class ProductShortDescriptionFragment extends OpinionBaseFragment {

    @Optional
    @InjectView(R.id.icon_gallery_top_left)
    ImageView iconGalleryTopLeft;

    @Optional
    @InjectView(R.id.product_image_view)
    ImageView producImageView;

    @Optional
    @InjectView(R.id.product_name)
    TextView name;

    @Optional
    @InjectView(R.id.product_cat)
    TextView cat;

    @Optional
    @InjectView(R.id.product_bd)
    TextView bd;

    @Optional
    @InjectView(R.id.button_like)
    ToggleButton like;

    @Optional
    @InjectView(R.id.button_dislike)
    ToggleButton dislike;


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
        return inflater.inflate(R.layout.fragment_product_short_description, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        product = ((ProductActivity) getActivity()).product;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //update design
        updateDesign();

        opinionPresenter.setView(this);

        like.setOnClickListener(new LikeClickListener());
        dislike.setOnClickListener(new DislikeClickListener());
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

    private void updateDesign() {
        ColorUtils.setTextColor(bd, ColorUtils.FeatureColor.NEUTRAL_DARK);
        ColorUtils.setTextColor(cat, ColorUtils.FeatureColor.PRIMARY_LIGHT);
    }

    public void showProductInformations() {
        if (product != null) {
            fillProductInformations();
            producImageView.setClickable(true);
            producImageView.setOnClickListener(new ImageClickListener());
            updateOpinionViews();
        }
    }

    private void fillProductInformations() {
        if (product != null) {
            name.setText(product.pn);
            cat.setText(product.cat);
            bd.setText(product.bd);

            if (product.imgs != null && product.imgs.size() > 1
                    && product.imgs.get(0) != null && product.imgs.get(0).length() > 0)
                iconGalleryTopLeft.setVisibility(View.VISIBLE);
            else
                iconGalleryTopLeft.setVisibility(View.GONE);

            Picasso.with(getContext())
                    .load(ImageUtils.getImageUrl(product.imgs.get(0), "sd"))
                    .placeholder(R.drawable.img_placeholder)
                    .into(producImageView);
        }
    }

    private void onButtonClick(ToggleButton button, ECustomerActions action) {
        boolean on = button.isChecked();
        if (!on)
            opinionPresenter.executeAction(getActivity(), action, product);
        else
            setSelectedState(button);
    }

    private void setSelectedState(ToggleButton button) {
        if (button == like) {
            dislike.setChecked(false);
            email.setChecked(false);
            wishlist.setChecked(false);
        }
        else if (button == dislike) {
            like.setChecked(false);
            email.setChecked(false);
            wishlist.setChecked(false);
        }
        else if (button == wishlist) {
            like.setChecked(false);
            dislike.setChecked(false);
            email.setChecked(false);
        }
        else if (button == email) {
            like.setChecked(false);
            dislike.setChecked(false);
            wishlist.setChecked(false);
        }

    }

    public void updateOpinionViews() {
        if (product != null)
            removePreviousOpinions();
        opinionPresenter.updateOpinionsViews(product);
    }

    private void removePreviousOpinions() {
        ColorUtils.removeColorFilter(dislike);
        ColorUtils.removeColorFilter(like);
    }

    // -------------------------------------------------------------------------------------------
    //                                      View Impl
    // -------------------------------------------------------------------------------------------

    @Override
    public void onLikeSuccess() {
        ColorUtils.applyLightningColorFilter(like, ColorUtils.FeatureColor.PRIMARY_LIKE);
        ColorUtils.removeColorFilter(dislike);
        like.setOnClickListener(null);
        dislike.setChecked(false);
        dislike.setOnClickListener(null);
        dislike.setOnClickListener(new DislikeClickListener());
    }

    @Override
    public void onDisLikeSuccess() {
        ColorUtils.applyLightningColorFilter(dislike, ColorUtils.FeatureColor.PRIMARY_LIKE);
        ColorUtils.removeColorFilter(like);
        dislike.setOnClickListener(null);
        like.setChecked(false);
        like.setOnClickListener(null);
        like.setOnClickListener(new LikeClickListener());
    }

    // -------------------------------------------------------------------------------------------
    //                                       Event(s)
    // -------------------------------------------------------------------------------------------

    private class ImageClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), FullScreenImageGalleryActivity.class);
            intent.putExtra(Constants.EXTRA_PRODUCT, Parcels.wrap(product));
            getActivity().startActivity(intent);
        }
    }

    private class LikeClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            onButtonClick(like, ECustomerActions.DO_LIKE);
        }
    }

    private class DislikeClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            onButtonClick(dislike, ECustomerActions.DO_DISLIKE);
        }
    }

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
