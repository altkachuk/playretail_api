package ninja.cyplay.com.playretail_api.ui.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;
import ninja.cyplay.com.playretail_api.model.business.Seller;
import ninja.cyplay.com.playretail_api.ui.component.CircleTransform;
import ninja.cyplay.com.playretail_api.utils.ColorUtils;
import ninja.cyplay.com.apilibrary.utils.Constants;
import ninja.cyplay.com.playretail_api.R;

public class SellerInitialsView extends LinearLayout {

    @Optional
    @InjectView(R.id.seller_bullet)
    TextView sellerBullet;

    @Optional
    @InjectView(R.id.seller_image)
    ImageView sellerImageView;

    private Seller PRSeller;

//    private BitmapPool mPool;

    public void init() {
        ColorUtils.setBackgroundColor(sellerBullet, ColorUtils.FeatureColor.PRIMARY_LIGHT);
        if (PRSeller != null) {
            sellerBullet.setText(PRSeller.getFn().charAt(0) + "" + PRSeller.getName().charAt(0));
            ColorUtils.setTextColor(sellerBullet, ColorUtils.FeatureColor.PRIMARY_DARK);
//            sellerName.setText(seller.getFn() + " " + seller.getName());
            sellerImageView.setVisibility(GONE);

            Picasso.with(getContext())
                .load(Constants.WEBSERVICE_URL + Constants.SELLER_PICTURE_ROUTE + PRSeller.getUn() + "/image/")
                .fit().centerCrop()
                .transform(new CircleTransform())
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded (final Bitmap bitmap, Picasso.LoadedFrom from) {
                        sellerImageView.setImageBitmap(bitmap);
                        sellerBullet.setVisibility(GONE);
                        sellerImageView.setVisibility(VISIBLE);
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                        sellerBullet.setVisibility(VISIBLE);
                        sellerImageView.setVisibility(GONE);
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
        }
    }

    public void setPRSeller(Seller PRSeller) {
        this.PRSeller = PRSeller;
        if (this.PRSeller != null)
            init();
    }

    public void setSellerImage(String imagePath) {
        if (imagePath != null && imagePath.length() > 0) {

            Picasso.with(getContext())
                    .load(imagePath)
                    .fit().centerCrop()
                    .transform(new CircleTransform())
                    .into(sellerImageView);

            sellerBullet.setVisibility(GONE);
            sellerImageView.setVisibility(VISIBLE);
        }
    }

    public SellerInitialsView(Context context) {
        super(context);
        ctor(context);
    }

    public SellerInitialsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ctor(context);
    }

    public SellerInitialsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        ctor(context);
    }

    private void ctor(Context context) {
        LayoutInflater.from(context).inflate(R.layout.seller_initials_view, this);
        ButterKnife.inject(this);
//        mPool = Glide.get(context).getBitmapPool();
    }



}