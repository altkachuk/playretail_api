package ninja.cyplay.com.playretail_api.ui.custom.form;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import ninja.cyplay.com.apilibrary.models.business.PR_FormRow;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.ui.listener.IFormDeleteItem;
import ninja.cyplay.com.playretail_api.utils.FormHelper;

/**
 * Created by damien on 31/03/16.
 */
public class FormRepetableLayout extends FormSubViewLayout {

    IFormDeleteItem iFormDeleteItem;

    @InjectView(R.id.input_container)
    LinearLayout viewContainer;

    private boolean isloaded = false;

    private FormSubViewLayout item;

    private String assignedValue;

    public FormRepetableLayout(Context context) {
        super(context);
        init(context);
    }

    public FormRepetableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FormRepetableLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.form_repetable_layout, this);
        ButterKnife.inject(this);
        isloaded = true;
        updateInfo();
    }

    private void updateInfo() {
        if (isloaded && row != null) {
            item = FormHelper.getViewFromTypeName(getContext(), row.getType());
            item.setRow(row);
            if (assignedValue != null)
                item.setValue(assignedValue);
            item.setDescriptor(descriptor);
            viewContainer.addView(item);
        }
    }

    @Override
    public void setRow(PR_FormRow row) {
        super.setRow(row);
        updateInfo();
    }

    // -------------------------------------------------------------------------------------------
    //                                      View Impl
    // -------------------------------------------------------------------------------------------

    @Override
    public boolean runValidation() {
        if (row == null || descriptor == null)
            return true;
        return item.runValidation();
//        if (FormHelper.isRequired(descriptor) && FormHelper.isEditTextEmpty(editText)) {
//            editText.setError(getContext().getString(R.string.form_required_error));
//            return false;
//        }
//        if (!FormHelper.testPattern(row, editText.getText().toString())) {
//            editText.setError("Validator: [" + row.getValidator() + "] failed");
//            return false;
//        }
//        if (customerDetails != null)
//            CustomerObjectFormMapper.setFieldByName(customerDetails, row.getTag(), editText.getText().toString());
//        if (required && FormValidatorHelper.isEmpty(editText)) {
//            editText.setError(getContext().getString(R.string.form_required_error));
//            return false;
//        }
//        editText.setError(null);
    }

    @Override
    public void setValue(String val) {
        this.assignedValue = val;
    }

    @Override
    public String getValue() {
        return (item != null) ? item.getValue() : "";
    }

    public IFormDeleteItem getiFormDeleteItem() {
        return iFormDeleteItem;
    }

    public void setiFormDeleteItem(IFormDeleteItem iFormDeleteItem) {
        this.iFormDeleteItem = iFormDeleteItem;
    }

    // -------------------------------------------------------------------------------------------
    //                                      Action(s)
    // -------------------------------------------------------------------------------------------

    @OnClick(R.id.editable_remove_button)
    public void onRemoveClick() {
        iFormDeleteItem.onDeleteRowClick(this);
    }

}
