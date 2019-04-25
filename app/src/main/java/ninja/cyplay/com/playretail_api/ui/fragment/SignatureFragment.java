package ninja.cyplay.com.playretail_api.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.app.BaseFragment;
import ninja.cyplay.com.playretail_api.model.singleton.PaylevenApi;
import ninja.cyplay.com.playretail_api.ui.component.DrawingView;

/**
 * Created by damien on 08/07/15.
 */
public class SignatureFragment extends BaseFragment {

    @Inject
    PaylevenApi paylevenApi;

    @InjectView(R.id.signature_container)
    View signatureContainer;

    @InjectView(R.id.signature_drawing_view)
    DrawingView signatureDrawingView;

    @InjectView(R.id.valid_button)
    Button valid;

    @InjectView(R.id.reset_button)
    Button reset;

    @InjectView(R.id.decline_button)
    Button decline;

    // -------------------------------------------------------------------------------------------
    //                                      Lifecycle
    // -------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signature, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    // -------------------------------------------------------------------------------------------
    //                                      Action(s)
    // -------------------------------------------------------------------------------------------

    @OnClick(R.id.valid_button)
    void validClick() {
        YoYo.with(Techniques.Pulse)
                .duration(500)
                .playOn(valid);
        paylevenApi.setSignatureBitmap(signatureContainer.getDrawingCache());
        Intent intent = new Intent();
        getActivity().setResult(Activity.RESULT_OK, intent);
        getActivity().finish();
    }

    @OnClick(R.id.reset_button)
    void resetClick() {
        YoYo.with(Techniques.Pulse)
                .duration(500)
                .playOn(reset);
        signatureDrawingView.reset();
    }

    @OnClick(R.id.decline_button)
    void declineClick() {
        YoYo.with(Techniques.Pulse)
                .duration(500)
                .playOn(decline);
        paylevenApi.setSignatureBitmap(signatureContainer.getDrawingCache());
        Intent intent = new Intent();
        getActivity().setResult(Activity.RESULT_CANCELED, intent);
        getActivity().finish();
    }

}
