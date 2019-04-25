package ninja.cyplay.com.playretail_api.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;

import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.app.BaseActivity;
import ninja.cyplay.com.playretail_api.ui.fragment.AuthenticationFragment;
import ninja.cyplay.com.playretail_api.ui.fragment.BarCodeScannerFragment;

public class BarCodeScannerActivity extends BaseActivity {

    private BarCodeScannerFragment barCodeScannerFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(this).inflate(R.layout.activity_bar_code_scanner, null);
        setContentView(view);
        barCodeScannerFragment = (BarCodeScannerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
    }

}