package ninja.cyplay.com.playretail_api.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

import ninja.cyplay.com.playretail_api.utils.ImageUtils;
import ninja.cyplay.com.playretail_api.R;
import uk.co.senab.photoview.PhotoViewAttacher;

public class FullScreenImageAdapter extends PagerAdapter {

    private Activity activity;
    private List<String> imgs;
    private LayoutInflater inflater;

    // constructor
    public FullScreenImageAdapter(Activity activity, List<String> imgs) {
        this.activity = activity;
        this.imgs = imgs;
    }

    @Override
    public int getCount() {
        return imgs != null ? imgs.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.full_screen_image_view, container, false);
        final ImageView imageView = (ImageView) viewLayout.findViewById(R.id.image_view);

        String img = imgs.get(position);

        final PhotoViewAttacher photoViewAttacherttacher = new PhotoViewAttacher(imageView);

        Picasso.with(activity)
            .load(ImageUtils.getImageUrl(img, "hd"))
            .into(new Target() {
                @Override
                public void onBitmapLoaded (final Bitmap bitmap, Picasso.LoadedFrom from) {
                    imageView.setImageBitmap(bitmap);
                    photoViewAttacherttacher.update();
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }

            });

        ((ViewPager) container).addView(viewLayout);

        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);
    }
}