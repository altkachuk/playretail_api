package ninja.cyplay.com.playretail_api.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.otto.Bus;

import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;
import ninja.cyplay.com.apilibrary.models.business.PR_FormDescriptor;
import ninja.cyplay.com.apilibrary.models.business.PR_FormRow;
import ninja.cyplay.com.apilibrary.models.business.PR_FormSection;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.app.BaseFragment;
import ninja.cyplay.com.playretail_api.model.events.FormNextPageEvent;
import ninja.cyplay.com.playretail_api.ui.custom.form.FormRepetableContainer;
import ninja.cyplay.com.playretail_api.ui.custom.form.FormSubViewLayout;
import ninja.cyplay.com.playretail_api.utils.ColorUtils;
import ninja.cyplay.com.playretail_api.utils.FormHelper;
import ninja.cyplay.com.playretail_api.utils.StringUtils;

/**
 * Created by damien on 17/03/16.
 */
public class FormSectionFragment extends BaseFragment {

    @Inject
    Bus bus;

    @InjectView(R.id.title_text_view)
    TextView title;

    @InjectView(R.id.form_section_title_underline_view)
    View underlineTitleView;

    @InjectView(R.id.form_container)
    LinearLayout formLLContainer;

    @InjectView(R.id.next_button)
    Button nextButton;

    // Is it the last page of the form
    private boolean isLast = false;
    // Section Object
    private PR_FormSection section;
    // All rows of the section
    private List<PR_FormRow> rows;
    // Position of the section in the form
    private int position;

    // -------------------------------------------------------------------------------------------
    //                                      Lifecycle
    // -------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_form_section, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateDesign();
        populateForm();
        nextButton.setText(getString(isLast ? R.string.form_save_button : R.string.form_next_button));
    }

    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    private void updateDesign() {
        ColorUtils.setTextColor(title, ColorUtils.FeatureColor.PRIMARY_DARK);
        ColorUtils.setBackgroundColor(underlineTitleView, ColorUtils.FeatureColor.PRIMARY_DARK);
    }

    public void populateForm() {
        if (section != null) {
            // Section tag
            title.setText(StringUtils.getStringResourceByName(section.getTag()));
            // Loop through the fields of the section
            if (section.getRowsDescriptors() != null) {
                for (int i = 0 ; i < section.getRowsDescriptors().size() ; i++) {
                    // Get Descriptor
                    PR_FormDescriptor descriptor = section.getRowsDescriptors().get(i);
                    FormSubViewLayout subViewLayout = null;
                    if (descriptor != null) {
                        // Get Row 'tagged' by the descriptor
                        PR_FormRow row = FormHelper.getRowForTag(descriptor.getTag());
                        // Add the correct view from the row's tag
                        if (row != null && row.getType() != null)
                            subViewLayout = FormHelper.getViewFromTypeName(getActivity(), row.getType());
                        // If view found
                        if (subViewLayout != null) {
                            subViewLayout.setDescriptor(descriptor);
                            subViewLayout.setRow(row);
                            formLLContainer.addView(subViewLayout);
                        }
                    }
                }
            }
            // if the section has only a template we create a repetable field
            else if (section.getTemplateRowDescriptor() != null) {
                // Turn this into a list of repetable item
                PR_FormRow row = FormHelper.getRowForTag(section.getTemplateRowDescriptor().getTag());
                if (row != null && row.getType() != null) {
                    FormRepetableContainer formRepetableContainer = new FormRepetableContainer(getContext());
                    formRepetableContainer.setDescriptor(section.getTemplateRowDescriptor());

                    formRepetableContainer.setRow(row);
                    formLLContainer.addView(formRepetableContainer);
                }
            }
        }
    }

    public boolean runValidations() {
        Boolean validationPassed = true;
        for (int i = 0; i < formLLContainer.getChildCount(); i++) {
            View view = formLLContainer.getChildAt(i);
            if (view instanceof FormSubViewLayout) {
                if (!((FormSubViewLayout) view).runValidation())
                    validationPassed = false;
            }
        }
        return validationPassed;
    }

    // -------------------------------------------------------------------------------------------
    //                                      Action(s)
    // -------------------------------------------------------------------------------------------

    @OnClick(R.id.next_button)
    public void onNextPressed() {
        bus.post(new FormNextPageEvent(this.position));
    }

    // -------------------------------------------------------------------------------------------
    //                                      Get/Setter(s)
    // -------------------------------------------------------------------------------------------


    public void setSection(PR_FormSection section) {
        this.section = section;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setIsLast(boolean isLast) {
        this.isLast = isLast;
    }


}
