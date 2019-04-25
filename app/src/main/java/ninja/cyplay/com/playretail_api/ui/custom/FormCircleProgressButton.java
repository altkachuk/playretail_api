package ninja.cyplay.com.playretail_api.ui.custom;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;
import ninja.cyplay.com.apilibrary.models.business.PR_FormSection;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.utils.ColorUtils;
import ninja.cyplay.com.playretail_api.utils.ImageUtils;

/**
 * Created by damien on 18/03/16.
 */
public class FormCircleProgressButton extends LinearLayout {

    @Optional
    @InjectView(R.id.circle_frame_layout)
    View frameLayout;

    @Optional
    @InjectView(R.id.icon_image_view)
    ImageView iconImageView;

    private Boolean isloaded = false;
    private Boolean isActive= false;

    private ButtonState state = ButtonState.DEFAULT;

    private Integer position;
    private PR_FormSection section;

    public FormCircleProgressButton(Context context) {
        super(context);
        init(context);
    }

    public FormCircleProgressButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FormCircleProgressButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.form_circle_progress_button, this);
        ButterKnife.inject(this);
        isloaded = true;
        updateDesign();
        updateInfo();
    }

    private void updateDesign() {
//        ColorUtils.applyLightningColorFilter(iconImageView, ColorUtils.FeatureColor.PRIMARY_DARK);
//        ColorUtils.setTextColor(circleTextView, ColorUtils.FeatureColor.PRIMARY_DARK);
    }

    public void updateInfo() {
        if (isloaded) {
            updateViewFromState();
            if (section != null && section.getIcon() != null)
            Picasso.with(getContext())
                    .load(ImageUtils.getImageUrl(section.getIcon(), "icon"))
                    .placeholder(R.drawable.img_placeholder)
                    .into(iconImageView);
        }
    }

    // -------------------------------------------------------------------------------------------
    //                                      Get/Setter(s)
    // -------------------------------------------------------------------------------------------

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
        updateInfo();
    }

    public PR_FormSection getSection() {
        return section;
    }

    public void setSection(PR_FormSection section) {
        this.section = section;
        updateInfo();
    }

    public ButtonState getState() {
        return state;
    }

    public void setState(ButtonState state) {
        this.state = state;
        updateViewFromState();
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
        updateViewFromState();
    }

    private void updateViewFromState() {
        switch (state) {
            case VALID:
                ColorUtils.applyLightningColorFilter(iconImageView, Color.parseColor("#4caf50"));
                break;
            case ERROR:
                ColorUtils.applyLightningColorFilter(iconImageView, Color.parseColor("#ce2029"));
                break;
            default:
                ColorUtils.applyLightningColorFilter(iconImageView, ColorUtils.FeatureColor.PRIMARY_DARK);
                break;
        }
        if (isActive)
            ColorUtils.setBackgroundColor(frameLayout, ColorUtils.FeatureColor.PRIMARY_LIGHT);
        else
            ColorUtils.setBackgroundColor(frameLayout, Color.parseColor("#FFFFFF"));
    }

    // -------------------------------------------------------------------------------------------
    //                                      Extras
    // -------------------------------------------------------------------------------------------

    public enum ButtonState {
        DEFAULT,
        ACTIVE,
        VALID,
        ERROR
    }

}
