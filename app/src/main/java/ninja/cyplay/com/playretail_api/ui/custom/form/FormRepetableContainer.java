package ninja.cyplay.com.playretail_api.ui.custom.form;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import ninja.cyplay.com.apilibrary.models.business.PR_FormRow;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.ui.listener.IFormDeleteItem;
import ninja.cyplay.com.playretail_api.utils.CustomerObjectFormMapper;

/**
 * Created by damien on 31/03/16.
 */
public class FormRepetableContainer extends FormSubViewLayout implements IFormDeleteItem {

    @InjectView(R.id.root)
    LinearLayout container;

    private boolean isloaded = false;

    private List<String> allValues;


    public FormRepetableContainer(Context context) {
        super(context);
        init(context);
    }

    public FormRepetableContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FormRepetableContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.form_repetable_container, this);
        ButterKnife.inject(this);
        isloaded = true;
        allValues = new ArrayList<>();
        updateInfo();
    }

    private void updateInfo() {
        if (isloaded && row != null) {
            List<String> values = CustomerObjectFormMapper.getFieldByName(customerDetails, row.getTag());
            if (values != null) {
                for (int i = 0 ; i < values.size() ; i++) {
                    addFieldWithValue(values.get(i));
                }
            }
        }
    }

    public void setRow(PR_FormRow row) {
        super.setRow(row);
        updateInfo();
    }

    private void addFieldWithValue(String val) {
        FormRepetableLayout repetableLayout = new FormRepetableLayout(getContext());
        repetableLayout.setDescriptor(descriptor);
        repetableLayout.setiFormDeleteItem(this);
        if (val != null)
            repetableLayout.setValue(val);
        repetableLayout.setRow(row);
        container.addView(repetableLayout);
    }

    // -------------------------------------------------------------------------------------------
    //                                      Action(s)
    // -------------------------------------------------------------------------------------------

    @OnClick(R.id.add_field_button)
    public void addField() {
        addFieldWithValue(null);
    }

    // -------------------------------------------------------------------------------------------
    //                                      View Impl (FormSubViewLayout)
    // -------------------------------------------------------------------------------------------

    @Override
    public boolean runValidation() {
        Boolean validationPassed = true;
        // empty of previous validation values
        allValues.clear();
        // manually apply the same check to each children
        // To set the result as an array
        for (int i = 0; i < container.getChildCount(); i++) {
            View view = container.getChildAt(i);
            if (view instanceof FormSubViewLayout) {
                String v = ((FormSubViewLayout) view).getValue();
                if (v != null && v.length() > 0)
                    allValues.add(v);
                // apply validator on View.getValue(), then add it to an array of result
                if (!((FormSubViewLayout) view).runValidation())
                    validationPassed = false;
            }
        }
        return validationPassed;
    }

    @Override
    public void setValue(String val) {

    }

    @Override
    public String getValue() {
        return null;
    }

    public List<String> getAllValues() {
        return allValues;
    }

    // -------------------------------------------------------------------------------------------
    //                                      View Impl (IFormDeleteItem)
    // -------------------------------------------------------------------------------------------

    @Override
    public void onDeleteRowClick(View view) {
        container.removeView(view);
    }
}