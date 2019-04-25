package ninja.cyplay.com.playretail_api.ui.custom;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Optional;
import ninja.cyplay.com.playretail_api.model.business.Offer;
import ninja.cyplay.com.playretail_api.model.business.Product;
import ninja.cyplay.com.playretail_api.model.business.Recommendation;
import ninja.cyplay.com.playretail_api.utils.ImageUtils;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.ui.activity.CustomerActivity;

/**
 * Created by damien on 17/06/15.
 */
public class CustomerProfileRecapCardView  extends LinearLayout {

    @Optional
    @InjectView(R.id.offre_imageView)
    ImageView offre;

    @Optional
    @InjectView(R.id.playlist_imageView)
    ImageView playlist;

    private Activity activity;

    private List<Offer> offers;
    private Recommendation PRRecommendation;

    private boolean isloaded = false;

    public CustomerProfileRecapCardView(Context context) {
        super(context);
        init(context);
    }

    public CustomerProfileRecapCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomerProfileRecapCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.card_view_customer_profile_recap, this);
        ButterKnife.inject(this);
        isloaded = true;
        showCustomerInformations();
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

    public void setPRRecommendation(Recommendation PRRecommendation) {
        this.PRRecommendation = PRRecommendation;
    }

    public void showCustomerInformations() {
        if (isloaded) {
            showFirstOffer();
            showFirstReco();
        }
    }

    private void showFirstOffer() {
        if (offers != null && offers.size() > 0) {
            Offer first_offer = offers.get(0);
            if (first_offer != null) {
                int imageId = -1;
                int type = first_offer.ot;
                switch (type) {
                    case 1:
                        imageId = R.drawable.sample_offer;
                        break;
                    case 2:
                        imageId = R.drawable.sample_offer_gold;
                        break;
                    case 3:
                        imageId = R.drawable.sample_offer_anniversary;
                        break;
                    case 4:
                        imageId = R.drawable.sample_offer_special;
                        break;
                    case 5:
                        imageId = R.drawable.sample_offer_gift;
                        break;
                }
                Picasso.with(activity)
                        .load(imageId)
                        .placeholder(R.drawable.img_placeholder)
                        .into(offre);

            }
        }
    }

    private void showFirstReco() {
        if (PRRecommendation != null && PRRecommendation.rl != null && PRRecommendation.rl.size() > 0) {
            Product first__product = PRRecommendation.rl.get(0);
            if (first__product != null) {
                Picasso.with(activity)
                        .load(ImageUtils.getImageUrl(first__product.img, "sd"))
                        .placeholder(R.drawable.img_placeholder)
                        .into(playlist);
            }
        }
    }

    // -------------------------------------------------------------------------------------------
    //                                      Action(s)
    // -------------------------------------------------------------------------------------------

    @OnClick(R.id.playlist_imageView)
    public void playlistAction() {
        if (activity != null)
            ((CustomerActivity)activity).showPagerFromPosition(1);
    }

    @OnClick(R.id.offre_imageView)
    public void offreAction() {
        if (activity != null)
            ((CustomerActivity)activity).showPagerFromPosition(3);
    }

}