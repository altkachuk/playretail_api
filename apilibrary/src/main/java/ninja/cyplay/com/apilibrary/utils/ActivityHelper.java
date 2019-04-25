package ninja.cyplay.com.apilibrary.utils;

import android.app.Activity;
import android.app.Application;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.inputmethod.InputMethodManager;

import ninja.cyplay.com.apilibrary.R;
import ninja.cyplay.com.apilibrary.models.singleton.ClientApplication;

public class ActivityHelper {

    @SuppressWarnings("WrongConstant")
    public static void initialize(Activity activity) {
        if (activity.getResources().getBoolean(R.bool.portrait_only)){
            activity.setRequestedOrientation(1 /*ActivityInfo.SCREEN_ORIENTATION_PORTRAIT*/);
        }else{
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager!=null && activity.getCurrentFocus() !=null){
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(), 0);
        }

    }

    public static void goToRestartActivity(){
        Class<Activity> restartActivity = ClientApplication.getInstance().getRestartActivity();
        Application application = ClientApplication.getInstance().getApplication();
        if ( application !=null && restartActivity !=null){
            Intent loginIntent = new Intent(application.getApplicationContext(), restartActivity);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            application.startActivity(loginIntent);
        }
    }



}