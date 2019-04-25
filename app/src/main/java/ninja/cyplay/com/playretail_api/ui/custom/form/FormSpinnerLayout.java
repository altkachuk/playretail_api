package ninja.cyplay.com.playretail_api.ui.custom.form;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.farbod.labelledspinner.LabelledSpinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;
import ninja.cyplay.com.apilibrary.models.business.PR_FormRow;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.utils.CustomerObjectFormMapper;
import ninja.cyplay.com.playretail_api.utils.FormHelper;
import ninja.cyplay.com.playretail_api.utils.StringUtils;

/**
 * Created by damien on 08/01/16.
 */
public class FormSpinnerLayout extends FormSubViewLayout {

    @Optional
    @InjectView(R.id.form_spinner)
    LabelledSpinner labelledSpinner;

    private PR_FormRow row;

    private List<String> displayedList;

    private boolean isloaded = false;

    public FormSpinnerLayout(Context context) {
        super(context);
        init(context);
    }

    public FormSpinnerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FormSpinnerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.form_spinner_layout, this);
        ButterKnife.inject(this);
        displayedList = new ArrayList<>();
        isloaded = true;
        updateInfo();
    }

    private void updateInfo() {
        if (row != null && row.getValues() != null
                && displayedList != null && displayedList.size() == 0)
            for (int i = 0 ; i < row.getValues().size() ; i++)
                displayedList.add(StringUtils.getStringResourceByName(row.getValues().get(i)));
        if (isloaded && row != null) {
            // set Editable
            labelledSpinner.setEnabled(FormHelper.isEditableOnce(descriptor));
            // set hint
            labelledSpinner.setLabelText(StringUtils.getStringResourceByName(row.getTag())
                    + (FormHelper.isRequired(descriptor) ? "*" : ""));
            // Set values
            labelledSpinner.setItemsArray(displayedList);
            // set assignedValue from model
            String value = CustomerObjectFormMapper.getFieldByName(customerDetails, row.getTag());
            if (value != null && row.getValues().contains(value))
                labelledSpinner.setSelection(row.getValues().indexOf(value));
            // select the default assignedValue
            else if (row.getValues() != null && row.getValues().contains(row.getDefaultValue()))
                labelledSpinner.setSelection(row.getValues().indexOf(row.getDefaultValue()));
        }
    }

    public void setRow(PR_FormRow row) {
        this.row = row;
        updateInfo();
    }

    // -------------------------------------------------------------------------------------------
    //                                      View Impl
    // -------------------------------------------------------------------------------------------

    @Override
    public boolean runValidation() {
        if (row == null || descriptor == null)
            return true;
        if (customerDetails != null)
            CustomerObjectFormMapper.setFieldByName(customerDetails, row.getTag(), getValue());
        return true;
    }

    @Override
    public void setValue(String val) {

    }

    @Override
    public String getValue() {
        if (row != null && row.getValues() != null)
            return row.getValues().get(labelledSpinner.getSpinner().getSelectedItemPosition());
        return "";
    }
}