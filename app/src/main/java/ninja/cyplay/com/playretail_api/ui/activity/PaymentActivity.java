package ninja.cyplay.com.playretail_api.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;

import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.app.BaseActivity;
import ninja.cyplay.com.playretail_api.ui.fragment.PaymentFragment;

/**
 * Created by damien on 29/06/15.
 */
public class PaymentActivity extends BaseActivity {

    private PaymentFragment         paymentFragment;

    // -------------------------------------------------------------------------------------------
    //                                      Lifecycle
    // -------------------------------------------------------------------------------------------

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(this).inflate(R.layout.activity_payment, null);
        setContentView(view);

        // show back button
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        paymentFragment = (PaymentFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
    }

}
