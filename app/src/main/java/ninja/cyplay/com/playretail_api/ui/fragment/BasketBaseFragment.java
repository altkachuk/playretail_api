package ninja.cyplay.com.playretail_api.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import javax.inject.Inject;

import ninja.cyplay.com.apilibrary.utils.DialogUtils;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.app.BaseFragment;
import ninja.cyplay.com.playretail_api.ui.custom.MessageProgressDialog;
import ninja.cyplay.com.playretail_api.ui.presenter.BasketPresenter;
import ninja.cyplay.com.playretail_api.ui.view.BasketView;

/**
 * Created by damien on 01/06/15.
 */
public class BasketBaseFragment extends BaseFragment implements BasketView {

    @Inject
    BasketPresenter basketPresenter;

    private MessageProgressDialog progress;

    // -------------------------------------------------------------------------------------------
    //                                      Lifecycle
    // -------------------------------------------------------------------------------------------

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        basketPresenter.initialize();

        // initialize
        progress = new MessageProgressDialog(getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        basketPresenter.setView(this);
    }

    // -------------------------------------------------------------------------------------------
    //                                      View Impl
    // -------------------------------------------------------------------------------------------

    @Override
    public void onSellerBasketSuccess() {

    }

    @Override
    public void onSellerBasketError() {
        Toast.makeText(getActivity(), R.string.generic_connection_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSyncSuccess() {

    }

    @Override
    public void onSyncError() {
        Toast.makeText(getActivity(), R.string.generic_connection_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteSuccess() {

    }

    @Override
    public void onDeleteError() {
        Toast.makeText(getActivity(), R.string.generic_connection_error, Toast.LENGTH_SHORT).show();
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
    public void displayPopUp(String message) {
        DialogUtils.showDialog(getActivity(), getString(R.string.warning), message);
    }
}
