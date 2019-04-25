package ninja.cyplay.com.playretail_api.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import ninja.cyplay.com.playretail_api.R;

/**
 * Created by damien on 21/05/15.
 */
public class CatalogueFilterCheckbox extends FrameLayout {

    @InjectView(R.id.filter_label)
    public TextView label;

    @InjectView(R.id.filter_checkbox)
    public CheckBox checkBox;

    public CatalogueFilterCheckbox(Context context) {
        this(context, null);
        LayoutInflater.from(context).inflate(R.layout.custom_catalogue_filter_checkbox, this);
        ButterKnife.inject(this);
    }

    public CatalogueFilterCheckbox(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CatalogueFilterCheckbox(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setLabel(String labelStr) {
        label.setText(labelStr);
    }

    public String getLabel() {
        return label.getText().toString();
    }

    public boolean isChecked() {
        return checkBox.isChecked();
    }

    @OnClick(R.id.filter_label)
    void onLabelClick() {
        checkBox.setChecked(!checkBox.isChecked());
    }

}
