package ninja.cyplay.com.playretail_api.ui.custom.form;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;
import ninja.cyplay.com.apilibrary.models.business.PR_FormRow;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.utils.CustomerObjectFormMapper;
import ninja.cyplay.com.playretail_api.utils.FormHelper;
import ninja.cyplay.com.playretail_api.utils.StringUtils;

/**
 * Created by damien on 07/01/16.
 */
public class FormEditTextInputLayoutLayout extends FormSubViewLayout {

    @Optional
    @InjectView(R.id.form_edit_text)
    EditText editText;

    @Optional
    @InjectView(R.id.text_input_layout)
    TextInputLayout textInputLayout;

    private boolean isloaded = false;

    public FormEditTextInputLayoutLayout(Context context) {
        super(context);
        init(context);
    }

    public FormEditTextInputLayoutLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FormEditTextInputLayoutLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.form_text_input_layout, this);
        ButterKnife.inject(this);
        isloaded = true;
        updateInfo();
    }

    private void updateInfo() {
        if (isloaded && row != null) {
            // is Editable
            editText.setEnabled(!FormHelper.isDisabled(descriptor));
            // hint
            textInputLayout.setHint(StringUtils.getStringResourceByName(row.getTag())
                    + (FormHelper.isRequired(descriptor) ? "*" : ""));

            // set default assignedValue
            if (assignedValue != null)
                editText.setText(assignedValue);
            else {
                try {
                    String value = CustomerObjectFormMapper.getFieldByName(customerDetails, row.getTag());
                    if (value != null)
                        editText.setText(value);
                } catch (Exception e) { }
            }
        }
    }

    @Override
    public void setRow(PR_FormRow row) {
        super.setRow(row);
        updateInfo();
    }

    public void setInputType(int type) {
        editText.setInputType(type);
    }

    // -------------------------------------------------------------------------------------------
    //                                      View Impl
    // -------------------------------------------------------------------------------------------

    @Override
    public boolean runValidation() {
        if (row == null || descriptor == null)
            return true;
        if (FormHelper.isRequired(descriptor) && FormHelper.isEditTextEmpty(editText)) {
            editText.setError(getContext().getString(R.string.form_required_error));
            return false;
        }
        if (!FormHelper.testPattern(row, editText.getText().toString())) {
            editText.setError("Validator: [" + row.getValidator() + "] failed");
            return false;
        }
        if (customerDetails != null)
            CustomerObjectFormMapper.setFieldByName(customerDetails, row.getTag(), getValue());
//        if (required && FormValidatorHelper.isEmpty(editText)) {
//            editText.setError(getContext().getString(R.string.form_required_error));
//            return false;
//        }
//        editText.setError(null);
        return true;
    }

    @Override
    public void setValue(String val) {
        assignedValue = val;
        updateInfo();
    }

    @Override
    public String getValue() {
        return editText.getText().toString();
    }

}