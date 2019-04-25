package ninja.cyplay.com.playretail_api.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by damien on 03/03/15.
 */
public class SmallestWHSquareImageView extends ImageView {
    public SmallestWHSquareImageView(Context context) {
        super(context);
    }

    public SmallestWHSquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SmallestWHSquareImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (widthMeasureSpec > heightMeasureSpec)
            super.onMeasure(heightMeasureSpec, heightMeasureSpec);
        else
            super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
