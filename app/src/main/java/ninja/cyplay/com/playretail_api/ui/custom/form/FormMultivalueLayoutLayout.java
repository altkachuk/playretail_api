package ninja.cyplay.com.playretail_api.ui.custom.form;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Optional;
import ninja.cyplay.com.apilibrary.models.business.FormField;
import ninja.cyplay.com.apilibrary.models.business.FormSection;
import ninja.cyplay.com.playretail_api.R;

/**
 * Created by damien on 08/01/16.
 */
public class FormMultivalueLayoutLayout extends FormSubViewLayout {

    @Optional
    @InjectView(R.id.linear_layout)
    LinearLayout linearLayout;

//    @Optional
//    @InjectView(R.id.add_a_field_layout)
//    View addAField;

    private FormField field;
    private FormSection section;

    private boolean isloaded = false;

    public FormMultivalueLayoutLayout(Context context) {
        super(context);
        init(context);
    }

    public FormMultivalueLayoutLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FormMultivalueLayoutLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.form_multivalue_layout, this);
        ButterKnife.inject(this);
        isloaded = true;
        updateInfo();
    }

    public void updateInfo() {
        if (isloaded && section != null && field != null) {
            onAddFieldClick();
            onAddFieldClick();
        }
    }

    public void setField(FormField field) {
        this.field = field;
    }

    public void setSection(FormSection section) {
        this.section = section;
    }

    // -------------------------------------------------------------------------------------------
    //                                      Action(s)
    // -------------------------------------------------------------------------------------------

    @OnClick(R.id.add_a_field_layout)
    void onAddFieldClick() {
        if (isloaded && section != null && field != null) {
//            if (section.isCreatable())
//                FormHelper.addRightFieldTypeNoValidationCheck(getContext(), linearLayout, field, editMode);
        }
    }

    // -------------------------------------------------------------------------------------------
    //                                      View Impl
    // -------------------------------------------------------------------------------------------

    @Override
    public boolean runValidation() {
        if (linearLayout != null && linearLayout.getChildCount() > 0)
            for (int i = 0 ; i < linearLayout.getChildCount() ; i++) {
                // change ?  multivalue should be an array of a repeated data
                FormSubViewLayout subViewLayout = (FormSubViewLayout) linearLayout.getChildAt(i);
//                if (!subViewLayout.runValidation())
//                    return false;
            }
        return true;
    }

    @Override
    public void setValue(String val) {

    }

    @Override
    public String getValue() {
        return null;
    }
}