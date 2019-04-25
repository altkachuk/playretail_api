package ninja.cyplay.com.playretail_api.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.parceler.Parcels;

import ninja.cyplay.com.playretail_api.model.business.CustomerDetails;
import ninja.cyplay.com.apilibrary.utils.Constants;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.app.BaseActivity;
import ninja.cyplay.com.playretail_api.model.singleton.DisplayedFormFieldsManager;
import ninja.cyplay.com.playretail_api.ui.fragment.FormDynamicFragment;

/**
 * Created by damien on 07/01/16.
 */
public class FormDynamicActivity extends BaseActivity {

    FormDynamicFragment formDynamicFragment;

//    private final static String FORM_DYNAMIC_FRAGMENT = "FORM_DYNAMIC_FRAGMENT";

    private CustomerDetails customerDetails = null;
    private CustomerDetails customerDetails_copy = null;

    // -------------------------------------------------------------------------------------------
    //                                      Lifecycle
    // -------------------------------------------------------------------------------------------

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // GetCustomer Before view is loaded otherwise fragments can't reach it
        if (getIntent() != null && getIntent().hasExtra(Constants.EXTRA_CUSTOMER)) {
            customerDetails = Parcels.unwrap(getIntent().getParcelableExtra(Constants.EXTRA_CUSTOMER));
            customerDetails_copy = new CustomerDetails(customerDetails);
        }
        // Instanciate view
        View view = LayoutInflater.from(this).inflate(R.layout.activity_form_dynamic, null);
        setContentView(view);

        formDynamicFragment = (FormDynamicFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_form);

        // show back button
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        if (savedInstanceState != null && savedInstanceState.containsKey(PAYMENT_FRAGMENT))
//            paymentFragment = (PaymentFragment) getSupportFragmentManager().getFragment(savedInstanceState, PAYMENT_FRAGMENT);
//        else
//            paymentFragment = new PaymentFragment();
//
//        initFragment();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        if (paymentFragment != null && getSupportFragmentManager().findFragmentById(R.id.container) == paymentFragment)
//            getSupportFragmentManager().putFragment(outState, PAYMENT_FRAGMENT, paymentFragment);
    }

    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    public CustomerDetails getCustomerDetails() {
        return customerDetails;
    }

    public CustomerDetails getCustomerDetails_copy() {
        return customerDetails_copy;
    }

    private void initFragment() {
//        FragmentManager manager = getSupportFragmentManager();
//        FragmentTransaction transaction = manager.beginTransaction();
//        transaction.replace(R.id.container, paymentFragment);
//        transaction.commit();
    }


    private void showConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.customer_information_back_title)
                .setMessage(R.string.customer_information_back)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    // -------------------------------------------------------------------------------------------
    //                                       Event(s)
    // -------------------------------------------------------------------------------------------

    @Override
    public void onBackPressed() {
        showConfirmationDialog();
        return;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_customer_information, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_edit_customer_save:
                if (DisplayedFormFieldsManager.getInstance().runFieldChecks())
                    formDynamicFragment.saveCustomer(customerDetails_copy);
//                {
//                    finish();
//                }
                else
                    Toast.makeText(FormDynamicActivity.this, "Error on save", Toast.LENGTH_SHORT).show();
//                    finish();
                return true;

            case android.R.id.home:
                showConfirmationDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}