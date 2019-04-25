package ninja.cyplay.com.playretail_api.ui.component;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import ninja.cyplay.com.playretail_api.R;

public class MarginDecoration extends RecyclerView.ItemDecoration {

    private int marginLeft;
    private int marginRight;
    private int marginTop;
    private int marginBottom;

    public MarginDecoration(Context context) {
        marginLeft = context.getResources().getDimensionPixelSize(R.dimen.default_grid_margin);
        marginRight = context.getResources().getDimensionPixelSize(R.dimen.default_grid_margin);
        marginTop = context.getResources().getDimensionPixelSize(R.dimen.default_grid_margin);
        marginBottom = context.getResources().getDimensionPixelSize(R.dimen.default_grid_margin);
    }

    public MarginDecoration(Context context, int left, int right, int top, int bottom) {
        if (context != null) {
            Resources r = context.getResources();
            marginLeft = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, left, r.getDisplayMetrics());
            marginRight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, right, r.getDisplayMetrics());
            marginTop = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, top, r.getDisplayMetrics());
            marginBottom = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, bottom, r.getDisplayMetrics());
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(marginLeft, marginTop, marginRight, marginBottom);
    }
}