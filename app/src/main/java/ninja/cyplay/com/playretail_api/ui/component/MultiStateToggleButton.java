package ninja.cyplay.com.playretail_api.ui.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import ninja.cyplay.com.playretail_api.utils.ColorUtils;
import ninja.cyplay.com.apilibrary.utils.ScreenUtils;
import ninja.cyplay.com.playretail_api.R;

public class MultiStateToggleButton extends ToggleButton {

    private static final String TAG = "MultiStateToggleButton";
    private final Context context;
    List<Button> buttons;

    public MultiStateToggleButton(Context context) {
        super(context, null);
        this.context = context;
        if (this.isInEditMode()) {
            return;
        }
    }

    public MultiStateToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        if (this.isInEditMode()) {
            return;
        }
        int[] set = {
                android.R.attr.entries
        };
        TypedArray a = context.obtainStyledAttributes(attrs, set);
        CharSequence[] texts = a.getTextArray(0);
        a.recycle();

        setElements(texts);
    }

    public void setElements(CharSequence[] texts) {
        // TODO: Add an exception
        if (texts == null || texts.length < 2) {
            Log.d(TAG, "Minimum quantity: 2");
            return;
        }

        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout mainLayout = (LinearLayout) inflater.inflate(R.layout.view_multi_state_toggle_button, this, true);
        mainLayout.removeAllViews();

        this.buttons = new ArrayList<Button>();
        for (int i = 0; i < texts.length; i++) {
            Button b = null;
            if (i == 0) {
                b = (Button) inflater.inflate(R.layout.view_left_toggle_button, mainLayout, false);
            } else if (i == texts.length - 1) {
                b = (Button) inflater.inflate(R.layout.view_right_toggle_button, mainLayout, false);
            } else {
                b = (Button) inflater.inflate(R.layout.view_center_toggle_button, mainLayout, false);
            }
            b.setText(texts[i]);
            final int position = i;
            b.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    setValue(position);
                }

            });
            mainLayout.addView(b);
            this.buttons.add(b);
        }
        mainLayout.setBackgroundResource(R.drawable.button_section_shape);
        GradientDrawable gradientDrawable = (GradientDrawable) mainLayout.getBackground();
        gradientDrawable.setStroke((int) ScreenUtils.convertDpToPixel(3.f, context),
                (Color.parseColor(ColorUtils.FeatureColor.getColorHex(ColorUtils.FeatureColor.PRIMARY_DARK))));
        mainLayout.invalidate();
//        ColorUtils.setBackgroundColor(mainLayout, ColorUtils.FeatureColor.PRIMARY_DARK);

    }

    public void setElements(List<String> texts) {
        setElements(texts.toArray(new String[texts.size()]));
    }

    public void setElements(int arrayResourceId) {
        setElements(this.getResources().getStringArray(arrayResourceId));
    }

    public void setButtonState(Button button, boolean selected) {
        if (button == null) {
            return;
        }
        button.setSelected(selected);
        if (selected) {
            // button.setBackgroundResource(R.drawable.button_pressed);
            button.setBackgroundColor(Color.parseColor(ColorUtils.FeatureColor.getColorHex(ColorUtils.FeatureColor.PRIMARY_DARK)));
            // ColorUtils.setBackgroundColor(button, ColorUtils.FeatureColor.PRIMARY_DARK);
//            button.setTextAppearance(this.context, R.style.WhiteText);
            button.setTextColor(Color.parseColor(ColorUtils.FeatureColor.getColorHex(ColorUtils.FeatureColor.PRIMARY_LIGHT)));
        } else {
            button.setBackgroundColor(Color.parseColor(ColorUtils.FeatureColor.getColorHex(ColorUtils.FeatureColor.PRIMARY_LIGHT)));
            //button.setBackgroundResource(R.drawable.button_not_pressed);
            // button.setTextAppearance(this.context, R.style.BlackNormalText);
            ColorUtils.setButtonColor(button, ColorUtils.FeatureColor.PRIMARY_DARK);

        }
    }

    public int getValue() {
        if (this.buttons != null) {
            for (int i = 0; i < this.buttons.size(); i++) {
                if (buttons.get(i).isSelected()) {
                    return i;
                }
            }
        }
        return -1;
    }

    public void setValue(int state) {

        if (this.buttons != null) {
            for (int i = 0; i < this.buttons.size(); i++) {
                if (i == state) {
                    setButtonState(buttons.get(i), true);
                } else {
                    setButtonState(buttons.get(i), false);

                }
            }
        }
        super.setValue(state);
    }


}
