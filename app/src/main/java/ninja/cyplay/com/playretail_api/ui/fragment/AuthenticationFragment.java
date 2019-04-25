package ninja.cyplay.com.playretail_api.ui.fragment;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.parceler.Parcels;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnTextChanged;
import ninja.cyplay.com.playretail_api.model.business.Seller;
import ninja.cyplay.com.apilibrary.models.singleton.APIValue;
import ninja.cyplay.com.playretail_api.ui.activity.AuthenticationActivity;
import ninja.cyplay.com.playretail_api.utils.ColorUtils;
import ninja.cyplay.com.apilibrary.utils.Constants;
import ninja.cyplay.com.apilibrary.utils.DialogUtils;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.app.BaseFragment;
import ninja.cyplay.com.playretail_api.ui.activity.HomeActivity;
import ninja.cyplay.com.playretail_api.ui.presenter.AuthenticationPresenter;
import ninja.cyplay.com.playretail_api.ui.view.AuthenticationView;

/**
 * Created by damien on 29/04/15.
 */
public class AuthenticationFragment extends BaseFragment implements AuthenticationView {

    @Inject
    APIValue apiValue;

    @InjectView(R.id.authentication_top_header)
    View authenticationTopHeader;

    @InjectView(R.id.authentication_pin_code)
    EditText authenticationPinCode;

    @InjectView(R.id.seller_initials)
    TextView sellerInitials;

    @InjectView(R.id.password_custom_view_layout)
    LinearLayout passwordCustomLayout;

    @Inject
    AuthenticationPresenter authenticationPresenter;

    private Seller seller;

    private int passLength = Constants.DEFAULT_PASS_LENGTH; // response is 4

    // -------------------------------------------------------------------------------------------
    //                                      Lifecycle
    // -------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_authentication, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity().getIntent() != null && getActivity().getIntent().hasExtra(Constants.EXTRA_SELLER))
            seller = Parcels.unwrap(getActivity().getIntent().getParcelableExtra(Constants.EXTRA_SELLER));
        if (getArguments() != null && getArguments().containsKey(Constants.EXTRA_SELLER))
            seller = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_SELLER));

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        authenticationPresenter.setView(this);
        updateDesign();
        if (seller != null) {
            displaySellerInfo();
            addSellerLoginListeners();
            setPasswordRestrictions();
            populateCustomPasswordLayout();
        }
        showKeyboardForPwd();
    }

    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    private void tryLogin() {
//        authenticationPresenter.doLogin(seller, authenticationPinCode.getText().toString());
        authenticationPresenter.getOauth2Details(seller.getUn());
    }

    void updateDesign() {
        ColorUtils.setBackgroundColor(authenticationTopHeader, ColorUtils.FeatureColor.PRIMARY_DARK);
        ColorUtils.setBackgroundColor(sellerInitials, ColorUtils.FeatureColor.PRIMARY_LIGHT);
//        ColorUtils.setTextColor(sellerInitials, ColorUtils.FeatureColor.PRIMARY_DARK);
    }

    private void displaySellerInfo() {
        if (((AppCompatActivity)getActivity()).getSupportActionBar() != null)
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(seller.getFormatName());
        sellerInitials.setText(seller.getInitials());
    }

    private void addSellerLoginListeners() {
        authenticationPinCode.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    tryLogin();
                    return true;
                }
                return false;
            }
        });
    }

    private void setPasswordRestrictions() {
        //TODO check if the password size will be sent in the config or not
//        if (apiValue.getPRConfig().getPRFeature() != null
//                && apiValue.getPRConfig().getPRFeature().getPassword() != null)
//            passLength = apiValue.getPRConfig().getPRFeature().getPassword().getLength();
        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(passLength);
        authenticationPinCode.setFilters(filterArray);
    }

    private void populateCustomPasswordLayout() {
        for (int i = 0; i < passLength; i++) // limit from server config instead of 6
        {
            TextView pinLetterTextView = new TextView(getActivity());
            LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            float scale = getResources().getDisplayMetrics().density;
            int dpAsPixels = (int) (5 * scale + 0.5f);
            llp.setMargins(dpAsPixels, dpAsPixels, dpAsPixels, dpAsPixels);
            pinLetterTextView.setLayoutParams(llp);
            pinLetterTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 80);
            pinLetterTextView.setText("-");
            ColorUtils.setTextColor(pinLetterTextView, ColorUtils.FeatureColor.PRIMARY_LIGHT);

            // show keyboard if tap on iputs
            authenticationPinCode.setFocusable(true);
            authenticationPinCode.setFocusableInTouchMode(true);
            pinLetterTextView.setClickable(true);
            pinLetterTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showKeyboardForPwd();
                }
            });

            passwordCustomLayout.addView(pinLetterTextView);
        }

    }

    void showKeyboardForPwd() {
        authenticationPinCode.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.showSoftInput(authenticationPinCode, 0);
//        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
    }

    private void resetPwdField() {
        for (int i = 0; i < passLength; i++)
            ((TextView) passwordCustomLayout.getChildAt(i)).setText("-");
        authenticationPinCode.setText("");
    }

    // -------------------------------------------------------------------------------------------
    //                                      View Impl
    // -------------------------------------------------------------------------------------------

    @Override
    public void showLoading() {
        Animation spinAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_loop);
        sellerInitials.startAnimation(spinAnim);
    }

    @Override
    public void hideLoading() {
        sellerInitials.clearAnimation();
    }

    @Override
    public void onAuthenticationSuccess() {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onGetOauth2DetailsSuccess() {
        authenticationPresenter.getAccessToken(seller.getUn(), authenticationPinCode.getText().toString());
    }

    @Override
    public void onAccessTokenSuccess() {
        authenticationPresenter.getSellerDetails(seller);
    }

    @Override
    public void onInvalidateTokenSuccess() {

    }

    @Override
    public void onGetSellerSuccess() {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        if (getActivity() instanceof AuthenticationActivity)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        if (getActivity() instanceof AuthenticationActivity)
            getActivity().finish();
    }

    @Override
    public void onError() {
        resetPwdField();
    }

    @Override
    public void displayPopUp(String message) {
        DialogUtils.showDialog(getActivity(), getString(R.string.warning), message);
    }

    // -------------------------------------------------------------------------------------------
    //                                      Action(s)
    // -------------------------------------------------------------------------------------------

    @OnTextChanged(R.id.authentication_pin_code)
    void onPasswordTextChanged(CharSequence text) {
        for (int i = 0 ; i < passLength ; i++) {
            TextView letter = (TextView) passwordCustomLayout.getChildAt(i);
            letter.setText(i < text.length() ? "\u2022" : "-");
        }
        if (text.length() == passLength)
            tryLogin();
    }

}
