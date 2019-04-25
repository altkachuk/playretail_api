package ninja.cyplay.com.playretail_api.ui.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

/**
 * Created by anishosni on 28/05/15.
 */
public class DatePickerFragment extends DialogFragment {

    DatePickerDialog.OnDateSetListener listner;

    public DatePickerFragment() {
    }

    public void setListner(DatePickerDialog.OnDateSetListener listner) {
        this.listner = listner;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the response date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), listner, year, month, day);
    }
}

