package ninja.cyplay.com.playretail_api.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

public class SovietHorizontalScrollView extends HorizontalScrollView
{
    public SovietHorizontalScrollView(Context context)
    {
        super(context);
    }

    public SovietHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SovietHorizontalScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getChildCount() <= 0 || getChildAt(0) instanceof ViewGroup != true)
            return;
        final ViewGroup view = (ViewGroup) getChildAt(0);
        if (view.getMeasuredWidth() <= getMeasuredWidth())
            return;
        final LayoutParams params = (LayoutParams) view.getLayoutParams();
        view.setLayoutParams(new LayoutParams(params.width, params.height, params.gravity & ~Gravity.CENTER_HORIZONTAL));
    }

}