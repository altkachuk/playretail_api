package ninja.cyplay.com.playretail_api.ui.custom.form;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;
import ninja.cyplay.com.apilibrary.models.business.PR_FormRow;
import ninja.cyplay.com.apilibrary.utils.LogUtils;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.ui.fragment.DatePickerFragment;
import ninja.cyplay.com.playretail_api.ui.fragment.PreSelectedDateFragment;
import ninja.cyplay.com.playretail_api.utils.CustomerObjectFormMapper;
import ninja.cyplay.com.playretail_api.utils.FormHelper;
import ninja.cyplay.com.playretail_api.utils.StringUtils;

/**
 * Created by damien on 08/01/16.
 */
public class FormDatePickerLayoutLayout extends FormSubViewLayout implements DatePickerDialog.OnDateSetListener {

    @Optional
    @InjectView(R.id.date_textview)
    EditText dateEditText;

    @Optional
    @InjectView(R.id.text_input_layout)
    TextInputLayout textInputLayout;

    private final static SimpleDateFormat IN_FORMAT = new SimpleDateFormat("yyyyMMdd");
    private final static SimpleDateFormat OUT_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    private PR_FormRow row;

    private boolean isloaded = false;

    private int yyyy = -1;
    private int mm = -1;
    private int dd = -1;

    public FormDatePickerLayoutLayout(Context context) {
        super(context);
        init(context);
    }

    public FormDatePickerLayoutLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FormDatePickerLayoutLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.form_date_picker_layout, this);
        ButterKnife.inject(this);
        isloaded = true;
        updateInfo();
    }

    private void updateInfo() {
        if (isloaded && row != null) {
            dateEditText.setEnabled(!FormHelper.isDisabled(descriptor));
            // set hint
            textInputLayout.setHint(StringUtils.getStringResourceByName(row.getTag())
                    + (FormHelper.isRequired(descriptor) ? "*" : ""));
            // noKeyboard
            dateEditText.setInputType(InputType.TYPE_NULL);
            // show picker on focus/click
            dateEditText.setOnFocusChangeListener(new OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        showDatePicker();
                    }
                }
            });
            dateEditText.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!FormHelper.isDisabled(descriptor))
                        showDatePicker();
                }
            });
            // set assignedValue from model
            String value = CustomerObjectFormMapper.getFieldByName(customerDetails, row.getTag());
            if (value != null && value.length() == 8) {
                onDateSet(null,
                        Integer.parseInt(value.substring(0, 4)),
                        Integer.parseInt(value.substring(4, 6)),
                        Integer.parseInt(value.substring(6, 8)));
            }
        }
    }

    public void setRow(PR_FormRow row) {
        this.row = row;
        updateInfo();
    }

    // -------------------------------------------------------------------------------------------
    //                                      View Impl
    // -------------------------------------------------------------------------------------------

    public void onDateSet(DatePicker view, int y, int m, int d) {
        this.yyyy = y;
        this.mm = m;
        this.dd = d;
        dateEditText.setText(dateFormat(y, m, d));
    }

    public void showDatePicker() {
        // Hide keyboard
        try {
            InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(((Activity) getContext()).getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) { }
        // Diplay date Picker
        try {
            DialogFragment newFragment = null;
            if (yyyy != -1 && mm != -1 && dd != -1) {
                // If date known
                newFragment = new PreSelectedDateFragment(yyyy, mm, dd);
                ((PreSelectedDateFragment)newFragment).setListener(this);
            }
            else {
                // no date
                newFragment = new DatePickerFragment();
                ((DatePickerFragment)newFragment).setListner(this);
            }
            // show date picker
            newFragment.show(((FragmentActivity) getContext()).getSupportFragmentManager(), "DatePicker");
        } catch (ClassCastException e) {
            Log.e(LogUtils.generateTag(this), "Can't get fragment manager");
        }
    }

    private String parseDateToString(int y, int m, int d) {
        StringBuilder builder = new StringBuilder();
        if (y > 0 && m > 0 && d > 0) {
            builder.append(y);
            builder.append(String.format("%02d", (m + 1)));
            builder.append(String.format("%02d", d));

        }
        return builder.toString();
    }

    private String dateFormat(int y, int m, int d) {
        try {
            Date parseDate = IN_FORMAT.parse(parseDateToString(y, m, d));
            return (OUT_FORMAT.format(parseDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    // -------------------------------------------------------------------------------------------
    //                                      View Impl
    // -------------------------------------------------------------------------------------------

    @Override
    public boolean runValidation() {
        if (row == null || descriptor == null)
            return true;
        if (FormHelper.isRequired(descriptor) && FormHelper.isEditTextEmpty(dateEditText)) {
            dateEditText.setError(getContext().getString(R.string.form_required_error));
            return false;
        }
        if (customerDetails != null && row != null)
            CustomerObjectFormMapper.setFieldByName(customerDetails, row.getTag(), parseDateToString(yyyy, mm, dd));
        dateEditText.setError(null);
        return true;
    }

    @Override
    public void setValue(String val) {

    }

    @Override
    public String getValue() {
        return dateEditText.getText().toString();
    }
}