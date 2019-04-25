package ninja.cyplay.com.playretail_api.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import ninja.cyplay.com.playretail_api.utils.ColorUtils;
import ninja.cyplay.com.playretail_api.R;

/**
 * Created by damien on 11/06/15.
 */
public class FullScreenView extends RelativeLayout {

//    @Optional
//    @InjectView(R.id.progress_bar)
    ProgressBar progressBar;

//    @Optional
//    @InjectView(R.id.error_text_view)
    TextView errorTextView;

//    @Optional
//    @InjectView(R.id.loading_view)
    RelativeLayout loadingView;

//    @Optional
//    @InjectView(R.id.error_view)
    LinearLayout errorView;

//    @Optional
//    @InjectView(R.id.error_view_button)
    Button reloadButton;


    public FullScreenView(Context context) {
        super(context);
        init(context);
    }

    public FullScreenView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FullScreenView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.custom_full_screen_loading_view, this);
        ButterKnife.inject(this);
        updateDesign();
        disableLoadingViewClick();
    }

    private void updateDesign() {
        ColorUtils.setProgressBarColor(progressBar, ColorUtils.FeatureColor.PRIMARY_LIGHT);
    }

    public void showLoading() {
        errorView.setVisibility(GONE);
        loadingView.setVisibility(VISIBLE);
    }

    public void hideLoading() {
        loadingView.setVisibility(GONE);
    }

    public void showError() {
        this.showError(getContext().getString(R.string.reload_error_msg));
    }

    public void showError(String message) {
        errorTextView.setText(message);
        errorView.setVisibility(VISIBLE);
        loadingView.setVisibility(GONE);
    }

    public void hideError() {
        errorView.setVisibility(GONE);
    }

    public void showReloadButton() {
        reloadButton.setVisibility(VISIBLE);
    }

    public void hideReloadButton() {
        reloadButton.setVisibility(GONE);
    }

    public Button getReloadButton() {
        return reloadButton;
    }

    // -------------------------------------------------------------------------------------------
    //                                      Action(s)
    // -------------------------------------------------------------------------------------------

    private void disableLoadingViewClick() {
        if (loadingView != null) {
            loadingView.setClickable(true);
            loadingView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Do nothing
                }
            });
        }
    }

}
