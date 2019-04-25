package ninja.cyplay.com.playretail_api.ui.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class PreSelectedDateFragment extends DialogFragment {

    private int yyyy;
    private int mm;
    private int dd;

    DatePickerDialog.OnDateSetListener listener;

    public PreSelectedDateFragment(int yyyy, int mm, int dd) {
        this.yyyy = yyyy;
        this.mm = mm;
        this.dd = dd;
    }

    public void setListener(DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new DatePickerDialog(getActivity(), listener, yyyy, mm, dd);
    }

}