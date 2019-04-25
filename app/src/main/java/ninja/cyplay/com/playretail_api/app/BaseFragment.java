package ninja.cyplay.com.playretail_api.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.ButterKnife;
import ninja.cyplay.com.playretail_api.model.singleton.PagesViews;
import ninja.cyplay.com.apilibrary.utils.DialogUtils;
import ninja.cyplay.com.playretail_api.R;

public abstract class BaseFragment extends Fragment {

    @Inject
    public PagesViews pagesViews;

    protected String className = this.getClass().getSimpleName();

    // -------------------------------------------------------------------------------------------
    //                                      Lifecycle
    // -------------------------------------------------------------------------------------------

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependencies();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        injectViews(view);
    }

    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    protected void injectDependencies() {
        App mvpCleanArchitectureApplication = (App) getActivity().getApplication();
        mvpCleanArchitectureApplication.inject(this);
    }

    private void injectViews(View view) {
        ButterKnife.inject(this, view);
    }


    //this is the defaul error msg; override this method if you need to show it differently
    public void onError() {
        if (getActivity() != null && getActivity().getBaseContext() != null) {
            String msg = getActivity().getBaseContext().getString(R.string.reload_error_msg);
            Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
        }
    }

    public void displayPopUp(String message) {
        DialogUtils.showDialog(getActivity(), getString(R.string.warning), message);
    }

    public void displaySttPopUp(String message, String stt) {

        try {
            int stt_code = Integer.parseInt(stt);
            switch (stt_code) {
                // app version warning (n-1)
                case 1:     versionWarning(message);
                            break;
                // app version error (n-2)
                case 2:     versionError(message);
                            break;
                //session deleted
                case 10:    sessionWarnning(message);
                            break;
                // seller or shop deleted
                case 12:    sessionError(message);
                            break;
                default:    displayPopUp(message);
                            break;
            }
        }
        catch (Exception e) {
            displayPopUp(message);
        }
    }

    public void versionWarning(String msg) {
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.app_version_warning_popup_title)
                .setMessage(msg)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }

    //TODO implement this server side
    public void versionError(String msg) {
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.app_version_error_popup_title)
                .setMessage(msg)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void sessionWarnning(String msg) {
        new AlertDialog.Builder(getActivity())
                .setTitle(getString(R.string.warning))
                .setMessage(msg)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = getActivity().getBaseContext().getPackageManager()
                                .getLaunchIntentForPackage(getActivity().getBaseContext().getPackageName());
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        //go to authentification activity
//                        Intent intent = new Intent(getActivity(), SplashScreenAcivity.class);
//                        startActivity(intent);
//                        getActivity().finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void sessionError(String msg) {
        new AlertDialog.Builder(getActivity())
                .setTitle(getString(R.string.warning))
                .setMessage(msg)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //quit the app
                        System.exit(0);
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}
