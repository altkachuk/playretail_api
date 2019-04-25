package ninja.cyplay.com.playretail_api.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.squareup.otto.Bus;

import javax.inject.Inject;

import butterknife.InjectView;
import ninja.cyplay.com.playretail_api.model.business.CustomerDetails;
import ninja.cyplay.com.apilibrary.models.business.FormCustomerInfo;
import ninja.cyplay.com.apilibrary.models.business.FormSection;
import ninja.cyplay.com.playretail_api.model.events.CustomerCreatedEvent;
import ninja.cyplay.com.playretail_api.model.events.CustomerEditEvent;
import ninja.cyplay.com.apilibrary.models.singleton.APIValue;
import ninja.cyplay.com.apilibrary.utils.Constants;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.app.BaseFragment;
import ninja.cyplay.com.playretail_api.model.singleton.DisplayedFormFieldsManager;
import ninja.cyplay.com.playretail_api.ui.custom.form.FormSectionCardView;
import ninja.cyplay.com.playretail_api.ui.custom.MessageProgressDialog;
import ninja.cyplay.com.playretail_api.ui.presenter.UpdateCustomerInfoPresenter;
import ninja.cyplay.com.playretail_api.ui.view.CreateCustomerView;
import ninja.cyplay.com.playretail_api.ui.view.UpdateCustomerInfoView;

/**
 * Created by damien on 07/01/16.
 */
public class FormDynamicFragment extends BaseFragment implements UpdateCustomerInfoView, CreateCustomerView {

    @Inject
    APIValue apiValue;

    @Inject
    Bus bus;

    @Inject
    UpdateCustomerInfoPresenter updateCustomerInfoPresenter;

    @InjectView(R.id.scroll_view)
    ScrollView scrollView;

    @InjectView(R.id.form_container)
    LinearLayout formContainer;

    private MessageProgressDialog progress;

    private FormCustomerInfo formCustomerInfo;

    // check if creation form
    private boolean editMode = false;

    private View phoneView;
    private View emailView;
    private View addressView;

    // -------------------------------------------------------------------------------------------
    //                                      Lifecycle
    // -------------------------------------------------------------------------------------------

        @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_form_dynamic, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // create loading view
        progress = new MessageProgressDialog(getActivity());
        // reset when we enter the form
        DisplayedFormFieldsManager.getInstance().reset();
        // get infos
        if (apiValue != null && apiValue.getPRConfig() != null && apiValue.getPRConfig().getPRFeature() != null)
            formCustomerInfo = apiValue.getPRConfig().getPRFeature().getCustomer_info();
        if (getActivity() != null && getActivity().getIntent() != null && getActivity().getIntent().hasExtra(Constants.EXTRA_CUSTOMER_FORM_MODE_EDIT))
            editMode = getActivity().getIntent().getBooleanExtra(Constants.EXTRA_CUSTOMER_FORM_MODE_EDIT, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateCustomerInfoPresenter.setView(this);
        if (formCustomerInfo != null && formCustomerInfo.getSections() != null) {
            for (int i = 0; i < formCustomerInfo.getSections().size(); i++) {
                FormSection formSection = formCustomerInfo.getSections().get(i);
                FormSectionCardView formSectionCardView = new FormSectionCardView(getActivity());
//                formSectionCardView.setEditMode(editMode);
                formSectionCardView.setSection(formSection, formCustomerInfo.getFields());
                DisplayedFormFieldsManager.getInstance().addField(formSectionCardView);
                saveViewForSCroll(formSection, formSectionCardView);
                formContainer.addView(formSectionCardView);
            }
        }
        updateScrollPosition();
    }

    private void saveViewForSCroll(FormSection section, View v) {
        if (section.getTag() != null && section.getTag().equals("emails"))
            emailView = v;
        else if (section.getTag() != null && section.getTag().equals("phone_numbers"))
            phoneView = v;
        else if (section.getTag() != null && section.getTag().equals("address"))
            addressView = v;
    }

    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    public void saveCustomer(CustomerDetails customerDetails) {
        if (editMode) {
            if (customerDetails != null)
                updateCustomerInfoPresenter.updateCustomerInfo(customerDetails.EAN, customerDetails);
        }
        else  {
            // Create customer here
            //TODO Create presenter
        }
    }

    private void updateScrollPosition() {
        if (getActivity() != null && getActivity().getIntent() != null
                && getActivity().getIntent().hasExtra(Constants.EXTRA_CUSTOMER_EDIT_FOCUS_ID)) {
            switch (getActivity().getIntent().getIntExtra(Constants.EXTRA_CUSTOMER_EDIT_FOCUS_ID, -1)) {
                case Constants.EXTRA_FORM_EDIT_ADDRESS:
                    scrollToView(addressView);
                    break;
                case Constants.EXTRA_FORM_EDIT_MAIL:
                    scrollToView(emailView);
                    break;
                case Constants.EXTRA_FORM_EDIT_PHONE:
                    scrollToView(phoneView);
                    break;
            }
        }
    }

    private void scrollToView(final View v) {
        // Scroll when the view is fully generated
        scrollView.post(new Runnable() {
            public void run() {
                if (v != null) {
                    scrollView.smoothScrollTo(0, v.getTop());
                    v.requestFocus();
                }
            }
        });
    }

    // -------------------------------------------------------------------------------------------
    //                                      View Impl
    // -------------------------------------------------------------------------------------------

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
    public void onCreateCustomerSuccess() {
        //TODO success
        bus.post(new CustomerCreatedEvent());
        getActivity().finish();
    }

    @Override
    public void onCreateCustomerError() {
        //TODO failure
    }

    @Override
    public void onUpdateSuccess() {
        bus.post(new CustomerEditEvent());
        getActivity().finish();
        //Toast.makeText(getActivity(), getString(R.string.update_customer_success), Toast.LENGTH_LONG).show();
    }

}
