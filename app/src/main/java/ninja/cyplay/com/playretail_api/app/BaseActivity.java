package ninja.cyplay.com.playretail_api.app;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.afollestad.materialdialogs.AlertDialogWrapper;

import javax.inject.Inject;

import butterknife.ButterKnife;
import ninja.cyplay.com.apilibrary.MVPCleanArchitectureApplication;
import ninja.cyplay.com.apilibrary.models.singleton.APIValue;
import ninja.cyplay.com.playretail_api.model.singleton.PagesViews;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.ui.FullScreenView;
import ninja.cyplay.com.apilibrary.utils.ActivityHelper;
import ninja.cyplay.com.playretail_api.utils.ColorUtils;
import ninja.cyplay.com.apilibrary.utils.DialogUtils;

/**
 * Base activity for doing the Dependency
 *
 * @author glomadrian
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Inject
    APIValue apiValue;

    @Inject
    PagesViews pagesViews;

//    @Optional
//    @InjectView(R.id.full_screen_view)
    FullScreenView fullScreenView;

    protected String className = this.getClass().getSimpleName();

    // -------------------------------------------------------------------------------------------
    //                                      Lifecycle
    // -------------------------------------------------------------------------------------------

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // lock orientation if phone or tablet etc
         ActivityHelper.initialize(this);

        injectDependencies();
        injectViews();
        updateDesign();
        savePageView();
    }

    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    private void injectDependencies() {
        MVPCleanArchitectureApplication mvpCleanArchitectureApplication = (MVPCleanArchitectureApplication) getApplication();
        mvpCleanArchitectureApplication.inject(this);
    }

    public void injectViews() {
        ButterKnife.inject(this);
    }

    private void updateDesign() {
        // updateActionbar color
        if (apiValue != null && apiValue.getPRConfig() != null) {
            ColorUtils.setActionBarBackgroundColor(getSupportActionBar(), ColorUtils.FeatureColor.PRIMARY_DARK);
            ColorUtils.settatusBarColor(getWindow(), ColorUtils.FeatureColor.PRIMARY_DARK);
        }
    }

    public void savePageView() {
        pagesViews.addPageView(className);
    }

    public void showErrorView() {
        if (fullScreenView!= null)
            fullScreenView.showError();
    }

    // -------------------------------------------------------------------------------------------
    //                                      Override(s)
    // -------------------------------------------------------------------------------------------

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //this is the defaul error msg; override this method if you need to show it differently
    public void onError() {
        String msg = getBaseContext().getString(R.string.reload_error_msg);
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    public void displayPopUp(String message) {
        DialogUtils.showDialog(this, getString(R.string.warning), message);
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
        new AlertDialogWrapper.Builder(this)
                .setTitle(R.string.app_version_warning_popup_title)
                .setMessage(msg)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }

    //TODO implement this server side
    public void versionError(String msg) {
        new AlertDialogWrapper.Builder(this)
                .setTitle(R.string.app_version_error_popup_title)
                .setMessage(msg)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void sessionWarnning(String msg) {
        new AlertDialogWrapper.Builder(this)
                .setTitle(getString(R.string.warning))
                .setMessage(msg)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO -> do back to first screen (without naming it)
                        Intent i = getBaseContext().getPackageManager()
                                .getLaunchIntentForPackage(getBaseContext().getPackageName());
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        //go to authentification activity
//                        Intent intent = new Intent(BaseActivity.this, SplashScreenAcivity.class);
//                        startActivity(intent);
//                        finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void sessionError(String msg) {
        new AlertDialogWrapper.Builder(this)
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
