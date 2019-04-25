package ninja.cyplay.com.playretail_api.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.app.BaseFragment;
import ninja.cyplay.com.playretail_api.model.singleton.PaylevenApi;
import ninja.cyplay.com.playretail_api.ui.custom.ReceipCardView;
import ninja.cyplay.com.playretail_api.ui.custom.ReceipExtraCardView;

/**
 * Created by damien on 15/07/15.
 */
public class ReceipFragment extends BaseFragment {

    @Inject
    PaylevenApi paylevenApi;

    @InjectView(R.id.receip_card_view)
    ReceipCardView receipCardView;

    @InjectView(R.id.receip_extra_card_view)
    ReceipExtraCardView receipExtraCardView;

    // -------------------------------------------------------------------------------------------
    //                                      Lifecycle
    // -------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_receip, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initReceip();
        initExtraReceip();
    }

    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    private void initReceip() {
        if (paylevenApi.isInit() && paylevenApi.getLastPaymentResult() != null) {
            receipCardView.setPaymentResult(paylevenApi.getLastPaymentResult());
            receipCardView.updateInfo();
        }
    }

    private void initExtraReceip() {
        receipExtraCardView.setActivity(getActivity());
        receipExtraCardView.updateInfo();
    }

    // -------------------------------------------------------------------------------------------
    //                                      Action(s)
    // -------------------------------------------------------------------------------------------

    @OnClick(R.id.email_button)
    void onEmailClick() {
        Toast.makeText(getActivity(), "Email clicked", Toast.LENGTH_SHORT).show();
    }

}
