package ninja.cyplay.com.playretail_api.ui.presenter;

import android.content.Context;
import android.util.Log;

import javax.inject.Inject;

import ninja.cyplay.com.apilibrary.domain.interactor.ClientInteractor;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.exception.BaseException;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AFeature;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AShopConfig;
import ninja.cyplay.com.apilibrary.models.business.PR_Config;
import ninja.cyplay.com.apilibrary.models.business.PR_Feature;
import ninja.cyplay.com.apilibrary.models.business.PR_ShopConfig;
import ninja.cyplay.com.apilibrary.models.singleton.APIValue;
import ninja.cyplay.com.apilibrary.utils.Constants;
import ninja.cyplay.com.apilibrary.utils.LogUtils;
import ninja.cyplay.com.playretail_api.app.BasePresenter;
import ninja.cyplay.com.playretail_api.ui.view.GetConfigView;
import ninja.cyplay.com.playretail_api.utils.ConfigHelper;

public class GetConfigPresenterImpl extends BasePresenter implements GetConfigPresenter {

    @Inject
    APIValue apiValue;

    private Context context;
    private GetConfigView   getConfigView;
    private ClientInteractor clientInteractor;

    public GetConfigPresenterImpl(Context context, ClientInteractor clientInteractor) {
        super(context);
        this.clientInteractor = clientInteractor;
        this.context = context;
    }

    @Override
    public void onConfig(PR_Config PRConfig) {
        Log.d(LogUtils.generateTag(this), "Config found");
        apiValue.setPRConfig(PRConfig);
    }

    @Override
    public void onReloadClick() {
        getConfigView.hideReload();
        getConfig();
    }

    @Override
    public void initialize() {
        getConfig();
    }

    private void getConfig() {
        getConfigView.showLoading();
        clientInteractor.getConfig(apiValue.getDeviceId(), Constants.APP_VERSION, new ClientInteractor.GetConfigCallback() {
            @Override
            public void onSuccess(PR_AFeature feature, PR_AShopConfig shopConfig) {
                getConfigView.hideLoading();
                PR_Config config = new PR_Config((PR_Feature)feature, (PR_ShopConfig)shopConfig);
                ConfigHelper.getInstance().setFeature((PR_Feature)feature);
                ConfigHelper.getInstance().setConfig(config);
                onConfig(config);
                getConfigView.onConfigSuccess();
            }

            @Override
            public void onError(BaseException e) {
                Log.e(LogUtils.generateTag(GetConfigPresenterImpl.this), "Error on GetConfigPresenterImpl");
                getConfigView.hideLoading();
                //Manage error on startup
                switch (e.getType()){
                    case BUSINESS:
                        if (e.getResponse() != null && e.getResponse().getStatus() != null) {
                            if (e.getResponse().getStatus().equals("11")) {
                                // device not provisionned
                                getConfigView.goToDeviceRegistration();
                            }
                            else
                                getConfigView.displaySttPopUp(e.getResponse().getDetail(), e.getResponse().getStatus());
                        }
                        else
                            getConfigView.showReload();
                        break;

                    case TECHNICAL:
                        getConfigView.showReload();
                        break;

                    default:
                        getConfigView.showReload();
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
        //Do nothing
    }

    @Override
    public void onViewDestroy() {
        //Do nothing
    }

    @Override
    public void setView(GetConfigView view) {
        this.getConfigView = view;
    }
}
