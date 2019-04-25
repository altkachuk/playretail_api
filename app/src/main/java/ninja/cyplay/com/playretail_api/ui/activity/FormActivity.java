package ninja.cyplay.com.playretail_api.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.squareup.otto.Subscribe;
import com.squareup.otto.Bus;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.Optional;
import ninja.cyplay.com.apilibrary.models.business.PR_Form;
import ninja.cyplay.com.apilibrary.utils.Constants;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.app.BaseActivity;
import ninja.cyplay.com.playretail_api.model.business.CustomerDetails;
import ninja.cyplay.com.playretail_api.model.events.CustomerCreatedEvent;
import ninja.cyplay.com.playretail_api.model.events.CustomerEditEvent;
import ninja.cyplay.com.playretail_api.model.events.FormNextPageEvent;
import ninja.cyplay.com.playretail_api.model.events.FormPageSelectEvent;
import ninja.cyplay.com.playretail_api.ui.custom.FormCircleProgressButton;
import ninja.cyplay.com.playretail_api.ui.custom.MessageProgressDialog;
import ninja.cyplay.com.playretail_api.ui.fragment.FormSectionFragment;
import ninja.cyplay.com.playretail_api.ui.presenter.CreateCustomerInfoPresenter;
import ninja.cyplay.com.playretail_api.ui.presenter.UpdateCustomerInfoPresenter;
import ninja.cyplay.com.playretail_api.ui.view.CreateCustomerView;
import ninja.cyplay.com.playretail_api.ui.view.UpdateCustomerInfoView;
import ninja.cyplay.com.playretail_api.utils.ColorUtils;
import ninja.cyplay.com.playretail_api.utils.FormHelper;

/**
 * Created by damien on 17/03/16.
 */
public class FormActivity extends BaseActivity implements ViewPager.OnPageChangeListener, UpdateCustomerInfoView, CreateCustomerView {

    @Inject
    Bus bus;

    @Inject
    UpdateCustomerInfoPresenter updateCustomerInfoPresenter;

    @Inject
    CreateCustomerInfoPresenter createCustomerInfoPresenter;

    @Optional
    @InjectView(R.id.form_progress_layout)
    LinearLayout formProgressLayout;

    @Optional
    @InjectView(R.id.view_pager)
    ViewPager viewPager;

    private FormPagerAdapter adapter;
    private PR_Form form;
    private List<FormCircleProgressButton> formButtons;

    // All sections
    private List<FormSectionFragment> sectionFragments;

    // Customer object
    private CustomerDetails customerDetails = null;
    private Boolean editMode = false;

    // Loading view
    private MessageProgressDialog progress;

    // -------------------------------------------------------------------------------------------
    //                                      Lifecycle
    // -------------------------------------------------------------------------------------------

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        injectViews();

        // create loading view
        progress = new MessageProgressDialog(this);

        // Set presenters
        updateCustomerInfoPresenter.setView(this);
        createCustomerInfoPresenter.setView(this);

        // Design stuff
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        updateDesign();

        // Get Extra Object
        if (getIntent() != null) {
            if (getIntent().hasExtra(Constants.EXTRA_CUSTOMER))
            customerDetails = Parcels.unwrap(getIntent().getParcelableExtra(Constants.EXTRA_CUSTOMER));
            if (getIntent().hasExtra(Constants.EXTRA_CUSTOMER_FORM_MODE_EDIT))
                editMode = getIntent().getBooleanExtra(Constants.EXTRA_CUSTOMER_FORM_MODE_EDIT, false);
        }
        if (customerDetails == null)
            customerDetails = new CustomerDetails();

        if (editMode && getSupportActionBar() != null)
            getSupportActionBar().setTitle(getString(R.string.customerCreationForm));
        else
            getSupportActionBar().setTitle(getString(R.string.customerUpdateForm));


        // Get the form
        form = FormHelper.getFormFromKey(editMode ? Constants.FORM_UPDATE_KEY : Constants.FORM_CREATE_KEY);

        // Fill header with progress of the form
        populateCircleProgressHeader();

        // View pager
        viewPager.addOnPageChangeListener(this);
        adapter = new FormPagerAdapter(getSupportFragmentManager());
        if (sectionFragments != null)
            viewPager.setOffscreenPageLimit(sectionFragments.size());
        viewPager.setAdapter(adapter);

        // Set first button as selected
        if (formButtons != null && formButtons.size() > 0)
            formButtons.get(0).setIsActive(true);

    }

    @Override
    protected void onStart() {
        super.onStart();
        bus.register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        bus.unregister(this);
    }

    // -------------------------------------------------------------------------------------------
    //                                      Override(s)
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
                saveOrEdit();
                return true;

            case android.R.id.home:
                showConfirmationDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    private void updateDesign() {
        ColorUtils.setBackgroundColor(formProgressLayout, ColorUtils.FeatureColor.PRIMARY_DARK);
    }

    private void populateCircleProgressHeader() {
        // Circle Buttons in header for sections
        if (form != null && form.getSectionsDescriptors() != null) {
            formButtons = new ArrayList<>();
            for (int i = 0; i < form.getSectionsDescriptors().size(); i++) {
                final FormCircleProgressButton button = new FormCircleProgressButton(this);
                // Set Button info
                button.setPosition(i);
                button.setSection(FormHelper.getSectionForTag(form.getSectionsDescriptors().get(i).getTag()));
                button.updateInfo();

                // Dynamic size of buttons
                Float buttonWeight = (1.0f / form.getSectionsDescriptors().size());
                button.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, buttonWeight));

                // Make button clickable
                button.setClickable(true);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bus.post(new FormPageSelectEvent(button.getPosition()));
                    }
                });
                // Store button in a list
                formButtons.add(button);
                // Add Layout to view
                formProgressLayout.addView(button);
            }
        }
    }

    public CustomerDetails getCustomerDetails() {
        return customerDetails;
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
//                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void saveOrEdit() {
        boolean allGood = true;
        for (int i = 0 ; i < sectionFragments.size() ; i++) {
            if (!sectionFragments.get(i).runValidations()) {
                formButtons.get(i).setState(FormCircleProgressButton.ButtonState.ERROR);
                allGood = false;
            }
            else
                formButtons.get(i).setState(FormCircleProgressButton.ButtonState.VALID);
        }
        // All fields are OK
        if (allGood) {
            if (editMode)
                updateCustomerInfoPresenter.updateCustomerInfo(customerDetails.EAN, customerDetails);
            else
                createCustomerInfoPresenter.createCustomer(customerDetails);

        }
        else {
            Toast.makeText(this, "Save or Edit KO", Toast.LENGTH_SHORT).show();
        }
    }

    // -------------------------------------------------------------------------------------------
    //                                      View Impl
    // -------------------------------------------------------------------------------------------

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
//        formButtons.get(position).setState(FormCircleProgressButton.ButtonState.ACTIVE);
        formButtons.get(position).setIsActive(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onCreateCustomerSuccess() {
        bus.post(new CustomerCreatedEvent());
        finish();
    }

    @Override
    public void onCreateCustomerError() {

    }

    @Override
    public void showLoading() {
        if (progress != null)
            progress.show();
    }

    @Override
    public void hideLoading() {
        if (progress != null)
            progress.dismiss();
    }

    @Override
    public void onUpdateSuccess() {
        bus.post(new CustomerEditEvent());
        finish();
    }

    // -------------------------------------------------------------------------------------------
    //                                      Private Class
    // -------------------------------------------------------------------------------------------

    private class FormPagerAdapter extends FragmentPagerAdapter {

        public FormPagerAdapter(FragmentManager fm) {
            super(fm);
            // Populate adapter with fragments
            if (form != null) {
                sectionFragments = new ArrayList<>();
                if (form.getSectionsDescriptors() != null) {

                    for (int i = 0 ; i < form.getSectionsDescriptors().size() ; i++) {
                        FormSectionFragment fragment = new FormSectionFragment();
                        String sectionTag = form.getSectionsDescriptors().get(i).getTag();
                        fragment.setSection(FormHelper.getSectionForTag(sectionTag));
                        fragment.setPosition(i);
                        if (i + 1 == form.getSectionsDescriptors().size())
                            fragment.setIsLast(true);
                        sectionFragments.add(fragment);
                    }
                }
            }
        }

        @Override
        public Fragment getItem(int pos) {
            if (sectionFragments != null && sectionFragments.size() > pos)
                return sectionFragments.get(pos);
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (form != null
                    && form.getSectionsDescriptors() != null
                    && form.getSectionsDescriptors().size() > position) {
                return form.getSectionsDescriptors().get(position).getTag();
            }
            return "";
        }

        @Override
        public int getCount() {
            return form != null && form.getSectionsDescriptors() != null ?
                    form.getSectionsDescriptors().size() : 0;
        }
    }

    // -------------------------------------------------------------------------------------------
    //                                       Event(s)
    // -------------------------------------------------------------------------------------------

    private void goToPageXIfFormValid(int page) {
        // don't run check if page doesn't change
        if (page == viewPager.getCurrentItem())
            return;
        if (sectionFragments != null && sectionFragments.size() > page) {
            if (sectionFragments.get(viewPager.getCurrentItem()).runValidations()) {
                // Page is Valid
                formButtons.get(viewPager.getCurrentItem()).setState(FormCircleProgressButton.ButtonState.VALID);
                // Go to the next page

                formButtons.get(viewPager.getCurrentItem()).setIsActive(false);
                viewPager.setCurrentItem(page);
            }
            else {
                // Something is not Ok in the Form
                formButtons.get(viewPager.getCurrentItem()).setState(FormCircleProgressButton.ButtonState.ERROR);
            }
        }
    }

    @Subscribe
    public void onNextButtonEvent(FormNextPageEvent formNextPageEvent) {
        // Run checks first
        if (adapter.getCount() > formNextPageEvent.getPosition() + 1) {
            goToPageXIfFormValid(formNextPageEvent.getPosition() + 1);
        }
        else if (adapter.getCount() == formNextPageEvent.getPosition() + 1) // is it the last page?
            saveOrEdit();
    }

    @Subscribe
    public void onPageSelectEvent(FormPageSelectEvent formPageSelectEvent) {
        // Run checks first
        if (adapter.getCount() > formPageSelectEvent.getPosition()) {
            goToPageXIfFormValid(formPageSelectEvent.getPosition());
        }
    }

}
