package ninja.cyplay.com.playretail_api.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by damien on 03/03/15.
 */
public class WSquareImageView extends ImageView {
    public WSquareImageView(Context context) {
        super(context);
    }

    public WSquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WSquareImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
