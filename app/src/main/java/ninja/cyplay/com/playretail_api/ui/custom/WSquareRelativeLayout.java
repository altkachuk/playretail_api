package ninja.cyplay.com.playretail_api.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by damien on 09/03/15.
 */
public class WSquareRelativeLayout extends RelativeLayout {

    public WSquareRelativeLayout(Context context) {
        super(context);
    }

    public WSquareRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WSquareRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
