package ninja.cyplay.com.playretail_api.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import ninja.cyplay.com.apilibrary.utils.LogUtils;

/**
 * Created by damien on 18/03/16.
 */
public class SmallestWHSquareTextView  extends TextView {

    public SmallestWHSquareTextView(Context context) {
        super(context);
    }

    public SmallestWHSquareTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SmallestWHSquareTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        if (widthMode != MeasureSpec.UNSPECIFIED) {
            Log.d(LogUtils.generateTag(this), "Width: " + widthMeasureSpec + " - Height: " + heightMeasureSpec);
            if (widthMeasureSpec > heightMeasureSpec)
                super.onMeasure(heightMeasureSpec, heightMeasureSpec);
            else
                super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        }
        else
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
