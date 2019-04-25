package ninja.cyplay.com.playretail_api.ui.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.viewpagerindicator.CirclePageIndicator;

import org.parceler.Parcels;

import butterknife.InjectView;
import butterknife.Optional;
import ninja.cyplay.com.playretail_api.model.business.Product;
import ninja.cyplay.com.apilibrary.utils.Constants;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.app.BaseActivity;
import ninja.cyplay.com.playretail_api.ui.adapter.FullScreenImageAdapter;

/**
 * Created by damien on 05/05/15.
 */
public class FullScreenImageGalleryActivity extends BaseActivity {

    @Optional
    @InjectView(R.id.pager)
    ViewPager pager;

    @Optional
    @InjectView(R.id.circles_page_indicator)
    CirclePageIndicator circlePageIndicator;

    @Optional
    @InjectView(R.id.gallery_close_button)
    ImageView closeButton;

    private FullScreenImageAdapter adapter;

    private Product PRProduct;

    // -------------------------------------------------------------------------------------------
    //                                      Lifecycle
    // -------------------------------------------------------------------------------------------

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(this).inflate(R.layout.activity_full_screen_image_gallery, null);
        setContentView(view);
        injectViews();

        // Get product from Extra
        getProduct();

        // set adapter etc
        initGalleryAdapter();

        // close button
        closeButton.setClickable(true);
        closeButton.setOnClickListener(new ImageCloseClickListener());
    }

    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    private void getProduct() {
        if (getIntent() != null && getIntent().hasExtra(Constants.EXTRA_PRODUCT))
            PRProduct = Parcels.unwrap(getIntent().getParcelableExtra(Constants.EXTRA_PRODUCT));
    }

    void initGalleryAdapter() {
        // init
        if (PRProduct != null) {
            adapter = new FullScreenImageAdapter(FullScreenImageGalleryActivity.this, PRProduct.getImgs());
            pager.setAdapter(adapter);
            circlePageIndicator.setViewPager(pager);
        }
    }

    // -------------------------------------------------------------------------------------------
    //                                      Action(s)
    // -------------------------------------------------------------------------------------------

    private class ImageCloseClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
        }
    }

}
