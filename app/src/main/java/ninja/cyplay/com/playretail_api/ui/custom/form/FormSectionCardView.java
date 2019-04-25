package ninja.cyplay.com.playretail_api.ui.custom.form;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;
import ninja.cyplay.com.apilibrary.models.business.FormField;
import ninja.cyplay.com.apilibrary.models.business.FormSection;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.utils.FormHelper;

/**
 * Created by damien on 07/01/16.
 */
public class FormSectionCardView extends FormSubViewLayout {

    @Optional
    @InjectView(R.id.section_title_text_view)
    TextView sectionTitle;

    @Optional
    @InjectView(R.id.fields_container)
    LinearLayout fieldsContainer;

    private boolean isloaded = false;

    private FormSection section;
    private List<FormField> sectionFields;
    private List<FormField> allFields;

    public FormSectionCardView(Context context) {
        super(context);
        init(context);
    }

    public FormSectionCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FormSectionCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.card_view_form_section, this);
        ButterKnife.inject(this);
        isloaded = true;
        updateInfo();
    }

    public void setSection(FormSection section, List<FormField> allFields) {
        this.section = section;
        this.allFields = allFields;
        updateInfo();
    }

    private void updateInfo() {
        if (isloaded && section != null) {
            // fill card
            sectionTitle.setText(section.getLabel());
            sectionFields = FormHelper.findSectionFields(section, allFields);
            if (section.isMultivalued()) {
                FormMultivalueLayoutLayout formMultivalueLayout = new FormMultivalueLayoutLayout(getContext());
//                formMultivalueLayout.setEditMode(editMode);
                formMultivalueLayout.setSection(section);
                formMultivalueLayout.setField(FormHelper.findFieldByTag(allFields, section.getRowTag()));
                formMultivalueLayout.updateInfo();
                fieldsContainer.addView(formMultivalueLayout);
            }
            else if (sectionFields != null) {
                for (int i = 0; i < sectionFields.size(); i++) {
                    FormField field = sectionFields.get(i);
//                    FormHelper.addRightFieldType(getContext(), fieldsContainer, field, editMode);
                }
            }
        }
    }

    // -------------------------------------------------------------------------------------------
    //                                      View Impl
    // -------------------------------------------------------------------------------------------

    @Override
    public boolean runValidation() {
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