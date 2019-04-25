package ninja.cyplay.com.playretail_api.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;
import ninja.cyplay.com.apilibrary.utils.Constants;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.app.BaseFragment;
import ninja.cyplay.com.playretail_api.model.business.Shop;
import ninja.cyplay.com.playretail_api.model.singleton.ShopList;
import ninja.cyplay.com.playretail_api.ui.activity.DeviceRegistrationActivity;
import ninja.cyplay.com.playretail_api.ui.activity.LoginActivityListActivity;
import ninja.cyplay.com.playretail_api.ui.presenter.GetConfigPresenter;
import ninja.cyplay.com.playretail_api.ui.presenter.GetShopsPresenter;
import ninja.cyplay.com.playretail_api.ui.view.GetConfigView;
import ninja.cyplay.com.playretail_api.ui.view.GetShopsView;

/**
 * Created by damien on 27/04/15.
 */
public class SplashScreenFragment extends BaseFragment implements GetConfigView, GetShopsView {

    @Inject
    ShopList shopList;

    @Inject
    GetConfigPresenter getConfigPresenter;

    @Inject
    GetShopsPresenter getShopsPresenter;

    @InjectView(R.id.splash_progress_bar)
    ProgressBar progressBar;

    @InjectView(R.id.reload_view)
    View reloadView;

    // -------------------------------------------------------------------------------------------
    //                                      Lifecycle
    // -------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateDesign();
        getConfigPresenter.setView(this);
        getShopsPresenter.setView(this);
        getConfigPresenter.initialize();
    }

    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    private void updateDesign() {
        // #FA6900 cyplay orange to match logo
        if (progressBar != null) {
            if (progressBar.getProgressDrawable() != null)
                progressBar.getProgressDrawable().setColorFilter(
                        Color.parseColor(Constants.CYPLAY_ORANGE), PorterDuff.Mode.SRC_IN);
            if (progressBar.getIndeterminateDrawable() != null)
                progressBar.getIndeterminateDrawable().setColorFilter(
                        Color.parseColor(Constants.CYPLAY_ORANGE), PorterDuff.Mode.SRC_IN);
        }
    }

    private void displaySellerList() {
        Intent intent = new Intent(getActivity(), LoginActivityListActivity.class);
//        Intent intent = new Intent(getActivity(), SellersActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        getActivity().finish();
    }

    private void displayDeviceRegistration() {
        Intent intent = new Intent(getActivity(), DeviceRegistrationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        getActivity().finish();
    }

    // -------------------------------------------------------------------------------------------
    //                                      View Impl
    // -------------------------------------------------------------------------------------------

    @Override
    public void showLoading() {
        if (progressBar != null)
            progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        if (progressBar != null)
            progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onShopsSuccess(List<Shop> PRShops) {
        shopList.setShops(PRShops);
        displaySellerList();
    }

    @Override
    public void onShopsError() {
        showReload();
    }

    @Override
    public void showReload() {
        if (reloadView!= null)
            reloadView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideReload() {
        if (reloadView != null)
            reloadView.setVisibility(View.GONE);
    }

    @OnClick(R.id.reload_button)
    public void reload(View view) {
        getConfigPresenter.onReloadClick();
    }

    @Override
    public void onConfigSuccess() {
        displaySellerList();
    }

    @Override
    public void goToDeviceRegistration() {
        displayDeviceRegistration();
    }

}
