package ninja.cyplay.com.playretail_api.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import ninja.cyplay.com.apilibrary.models.business.ECustomerActions;
import ninja.cyplay.com.playretail_api.model.events.CustomerContextFoundEvent;
import ninja.cyplay.com.playretail_api.model.events.DislikeProductCellEvent;
import ninja.cyplay.com.playretail_api.model.events.LikeProductCellEvent;
import ninja.cyplay.com.apilibrary.utils.DialogUtils;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.app.BaseFragment;
import ninja.cyplay.com.playretail_api.ui.custom.MessageProgressDialog;
import ninja.cyplay.com.playretail_api.ui.presenter.OpinionPresenter;
import ninja.cyplay.com.playretail_api.ui.view.OpinionView;

/**
 * Created by damien on 20/05/15.
 */
public class OpinionBaseFragment extends BaseFragment implements OpinionView {

    @Inject
    Bus bus;

    @Inject
    OpinionPresenter opinionPresenter;

    private Object busEventListener;
    private MessageProgressDialog progress;

    // -------------------------------------------------------------------------------------------
    //                                      Lifecycle
    // -------------------------------------------------------------------------------------------

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // like / dislike events
        busEventListener = new Object() {

            @Subscribe
            public void onLikeEvent(LikeProductCellEvent event) {
                opinionPresenter.executeAction(getActivity(), ECustomerActions.DO_LIKE, event.getProduct());
            }

            @Subscribe
            public void onDisLikeEvent(DislikeProductCellEvent event) {
                opinionPresenter.executeAction(getActivity(), ECustomerActions.DO_DISLIKE, event.getPRProduct());
            }

            @Subscribe
            public void onCustomerContextFound(CustomerContextFoundEvent event) {
                opinionPresenter.customerFound();
            }

        };

        // registration
        bus.register(busEventListener);
        opinionPresenter.initialize();

        // initialize
        progress = new MessageProgressDialog(getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bus.unregister(busEventListener);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        opinionPresenter.setView(this);
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
    public void onLikeSuccess() {
    }

    @Override
    public void onDisLikeSuccess() {
    }

    @Override
    public void onAddToWishlistSuccess() {

    }

    @Override
    public void onDeleteFromWishlistSuccess() {

    }


    @Override
    public void displayPopUp(String message) {
        DialogUtils.showDialog(getActivity(), getString(R.string.warning), message);
    }
}
