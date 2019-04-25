package ninja.cyplay.com.playretail_api.ui.presenter;

import android.content.Context;
import android.util.Log;

import javax.inject.Inject;

import ninja.cyplay.com.apilibrary.domain.interactor.ClientInteractor;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.exception.BaseException;
import ninja.cyplay.com.apilibrary.models.singleton.APIValue;
import ninja.cyplay.com.apilibrary.utils.LogUtils;
import ninja.cyplay.com.playretail_api.app.BasePresenter;
import ninja.cyplay.com.playretail_api.ui.view.ProvisionDeviceView;

/**
 * Created by damien on 29/04/15.
 */
public class ProvisionDevicePresenterImpl extends BasePresenter implements ProvisionDevicePresenter {

    @Inject
    APIValue apiValue;

    private Context context;
    private ProvisionDeviceView provisionDeviceView;
    private ClientInteractor clientInteractor;

    public ProvisionDevicePresenterImpl(Context context, ClientInteractor clientInteractor) {
        super(context);
        this.context = context;
        this.clientInteractor = clientInteractor;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void setView(ProvisionDeviceView view) {
        this.provisionDeviceView = view;
    }

    @Override
    public void provisionDevice(long storeId) {

        provisionDeviceView.showLoading();
        clientInteractor.provisionDevice(apiValue.getDeviceId(), String.valueOf(storeId), new ClientInteractor.ProvisionDeviceCallback() {

            @Override
            public void onSuccess() {
                provisionDeviceView.hideLoading();
                provisionDeviceView.onProvisionDeviceSuccess();
            }

            @Override
            public void onError(BaseException e) {
                provisionDeviceView.hideLoading();
                Log.e(LogUtils.generateTag(this), "Error on presenter privision device");
                switch (e.getType()) {
                    case BUSINESS:
                        if (e.getResponse() != null && e.getResponse().getStatus() != null)
                            provisionDeviceView.displaySttPopUp(e.getResponse().getDetail(),e.getResponse().getStatus());
                        break;

                    case TECHNICAL:
                        provisionDeviceView.onError();
                        break;
                }
            }

        });
    }

    @Override
    public void onViewCreate() {

    }

    @Override
    public void onViewResume() {

    }

    @Override
    public void onViewDestroy() {

    }

}
