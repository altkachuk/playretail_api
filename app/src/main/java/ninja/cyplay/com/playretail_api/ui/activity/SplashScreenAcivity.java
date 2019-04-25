package ninja.cyplay.com.playretail_api.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;

import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.app.BaseActivity;
import ninja.cyplay.com.playretail_api.ui.fragment.SplashScreenFragment;

/**
 * Created by anishosni on 18/02/15.
 */

public class SplashScreenAcivity extends BaseActivity {

    private SplashScreenFragment    splashScreenFragment;

    // -------------------------------------------------------------------------------------------
    //                                      Lifecycle
    // -------------------------------------------------------------------------------------------

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(this).inflate(R.layout.activity_splash, null);
        setContentView(view);
        splashScreenFragment = (SplashScreenFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
    }

}
