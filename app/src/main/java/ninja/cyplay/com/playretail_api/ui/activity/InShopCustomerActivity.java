package ninja.cyplay.com.playretail_api.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;

import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.app.BaseActivity;
import ninja.cyplay.com.playretail_api.ui.fragment.InShopCustomerFragment;

/**
 * Created by damien on 06/07/15.
 */
public class InShopCustomerActivity extends BaseActivity {

    private InShopCustomerFragment inShopCustomerFragment;

    // -------------------------------------------------------------------------------------------
    //                                      Lifecycle
    // -------------------------------------------------------------------------------------------

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(this).inflate(R.layout.activity_in_shop_customer, null);
        setContentView(view);
        // show back button
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        inShopCustomerFragment = (InShopCustomerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
    }

}
