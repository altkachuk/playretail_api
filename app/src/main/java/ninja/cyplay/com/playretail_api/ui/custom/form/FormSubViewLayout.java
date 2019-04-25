package ninja.cyplay.com.playretail_api.ui.custom.form;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import ninja.cyplay.com.apilibrary.models.business.PR_FormDescriptor;
import ninja.cyplay.com.apilibrary.models.business.PR_FormRow;
import ninja.cyplay.com.playretail_api.model.business.CustomerDetails;
import ninja.cyplay.com.playretail_api.ui.activity.FormActivity;
import ninja.cyplay.com.playretail_api.ui.activity.FormDynamicActivity;

/**
 * Created by damien on 11/01/16.
 */
public abstract class FormSubViewLayout extends FrameLayout implements FormField {

    protected CustomerDetails customerDetails = null;
    protected PR_FormDescriptor descriptor = null;
    protected PR_FormRow row = null;
    protected String assignedValue;

    public FormSubViewLayout(Context context) {
        super(context);
        subview_initialize();
    }

    public FormSubViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        subview_initialize();
    }

    public FormSubViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        subview_initialize();
    }

    public void subview_initialize() {
        if (getContext() instanceof FormDynamicActivity) {
            customerDetails = ((FormDynamicActivity)getContext()).getCustomerDetails_copy();
        }
        if (getContext() instanceof FormActivity) {
            customerDetails = ((FormActivity)getContext()).getCustomerDetails();
        }
    }

    public PR_FormRow getRow() {
        return row;
    }

    public void setRow(PR_FormRow row) {
        this.row = row;
    }

    public PR_FormDescriptor getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(PR_FormDescriptor descriptor) {
        this.descriptor = descriptor;
    }

    public CustomerDetails getCustomerDetails() {
        return customerDetails;
    }

    public void setCustomerDetails(CustomerDetails customerDetails) {
        this.customerDetails = customerDetails;
    }
}
