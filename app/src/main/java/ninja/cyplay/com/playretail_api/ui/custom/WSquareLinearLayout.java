package ninja.cyplay.com.playretail_api.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by damien on 02/07/15.
 */
public class WSquareLinearLayout extends LinearLayout {

    public WSquareLinearLayout(Context context) {
        super(context);
    }

    public WSquareLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WSquareLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
