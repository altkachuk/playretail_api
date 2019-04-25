package ninja.cyplay.com.playretail_api.ui.presenter;

import android.content.Context;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import ninja.cyplay.com.apilibrary.domain.interactor.ClientInteractor;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.exception.BaseException;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AShop;
import ninja.cyplay.com.playretail_api.model.business.Shop;
import ninja.cyplay.com.apilibrary.models.singleton.APIValue;
import ninja.cyplay.com.apilibrary.utils.LogUtils;
import ninja.cyplay.com.playretail_api.app.BasePresenter;
import ninja.cyplay.com.playretail_api.ui.view.GetShopsView;

/**
 * Created by damien on 29/04/15.
 */
public class GetShopsPresenterImpl extends BasePresenter implements GetShopsPresenter {

    @Inject
    APIValue apiValue;

    private Context context;
    private GetShopsView    getShopsView;
    private ClientInteractor clientInteractor;

    public GetShopsPresenterImpl(Context context, ClientInteractor clientInteractor) {
        super(context);
        this.clientInteractor = clientInteractor;
        this.context = context;
    }

    @Override
    public void setView(GetShopsView view) {
        this.getShopsView = view;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void getShops() {
        getShopsView.showLoading();
        clientInteractor.getShops(apiValue.getDeviceId(), new ClientInteractor.GetShopsCallback() {

            @Override
            public void onSuccess(List<PR_AShop> PRShops) {
                getShopsView.hideLoading();
                getShopsView.onShopsSuccess((List<Shop>)(List<?>)PRShops);
            }

            @Override
            public void onError(BaseException e) {
                getShopsView.hideLoading();
                Log.e(LogUtils.generateTag(this), "Error on presenter Shops");
                switch (e.getType()){
                    case BUSINESS:
                        if (e.getResponse() != null && e.getResponse().getStatus() != null)
                            getShopsView.displaySttPopUp(e.getResponse().getDetail(),e.getResponse().getStatus());
                        break;
                    case TECHNICAL:
                        getShopsView.onError();
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

}

