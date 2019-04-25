package ninja.cyplay.com.playretail_api.ui.custom;

import android.content.Context;
import android.graphics.BitmapShader;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import ninja.cyplay.com.playretail_api.R;

public class SquareFrameLayoutWithShadow extends FrameLayout {

    public SquareFrameLayoutWithShadow(Context context) {
        super(context);
        init();
    }

    public SquareFrameLayoutWithShadow(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SquareFrameLayoutWithShadow(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private Paint paintBorder;

    private void init() {
        paintBorder = new Paint(Paint.ANTI_ALIAS_FLAG);
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            // Do something for ICE_CREAM_SANDWICH and above versions
            this.setLayerType(LAYER_TYPE_SOFTWARE, paintBorder);
        }
        else{
            // do something for phones running an SDK before ICE_CREAM_SANDWICH
            this.setLayerType(LAYER_TYPE_SOFTWARE, null);
        }
        paintBorder.setShadowLayer(30.f, 0, 3.f, Color.BLACK);
//        paintBorder.setShadowLayer(5.0f, 0.0f, 3.0f, Color.BLACK);

//        paintBorder = new Paint();
//        paintBorder.setAntiAlias(true);
//        this.setLayerType(LAYER_TYPE_SOFTWARE, paintBorder);
//        paintBorder.setShadowLayer(5.0f, 0.0f, 3.0f, Color.BLACK);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int widthDesc = MeasureSpec.getMode(widthMeasureSpec);
        int heightDesc = MeasureSpec.getMode(heightMeasureSpec);
        int size = 0;
        if (widthDesc == MeasureSpec.UNSPECIFIED
                && heightDesc == MeasureSpec.UNSPECIFIED) {
            size = getContext().getResources().getDimensionPixelSize(R.dimen.form_circle_dimens_default); // Use your own default size, for example 125dp
        } else if ((widthDesc == MeasureSpec.UNSPECIFIED || heightDesc == MeasureSpec.UNSPECIFIED)
                && !(widthDesc == MeasureSpec.UNSPECIFIED && heightDesc == MeasureSpec.UNSPECIFIED)) {
            //Only one of the dimensions has been specified so we choose the dimension that has a assignedValue (in the case of unspecified, the assignedValue assigned is 0)
            size = width > height ? width : height;
        } else {
            //In all other cases both dimensions have been specified so we choose the smaller of the two
            size = width > height ? height : width;
        }
        setMeasuredDimension(size, size);
    }
}