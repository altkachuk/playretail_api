package ninja.cyplay.com.playretail_api.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;

import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.app.BaseActivity;
import ninja.cyplay.com.playretail_api.ui.fragment.DeviceRegistrationFragment;

/**
 * Created by damien on 29/04/15.
 */
public class DeviceRegistrationActivity extends BaseActivity {

    private DeviceRegistrationFragment  deviceRegistrationFragment;
    private final static String DEVICE_REGISTRATION_FRAGMENT = "DEVICE_REGISTRATION_FRAGMENT";

    // -------------------------------------------------------------------------------------------
    //                                      Lifecycle
    // -------------------------------------------------------------------------------------------

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(this).inflate(R.layout.activity_device_registration, null);
        setContentView(view);

        if (savedInstanceState != null && savedInstanceState.containsKey(DEVICE_REGISTRATION_FRAGMENT ))
            deviceRegistrationFragment = (DeviceRegistrationFragment) getSupportFragmentManager().getFragment(savedInstanceState, DEVICE_REGISTRATION_FRAGMENT );
        else
            deviceRegistrationFragment = new DeviceRegistrationFragment();

        initFragment();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (deviceRegistrationFragment != null && getSupportFragmentManager().findFragmentById(R.id.container) == deviceRegistrationFragment)
            getSupportFragmentManager().putFragment(outState, DEVICE_REGISTRATION_FRAGMENT , deviceRegistrationFragment);
    }

    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    private void initFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container, deviceRegistrationFragment);
        transaction.commit();
    }
}
