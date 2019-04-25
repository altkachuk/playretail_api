package ninja.cyplay.com.playretail_api.ui.presenter;

import android.content.Context;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import ninja.cyplay.com.apilibrary.domain.interactor.SellerInteractor;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.exception.BaseException;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ASeller;
import ninja.cyplay.com.playretail_api.model.business.Seller;
import ninja.cyplay.com.apilibrary.models.singleton.APIValue;
import ninja.cyplay.com.apilibrary.utils.LogUtils;
import ninja.cyplay.com.playretail_api.app.BasePresenter;
import ninja.cyplay.com.playretail_api.ui.view.GetSellersView;

/**
 * Created by damien on 28/04/15.
 */
public class GetSellersPresenterImpl extends BasePresenter implements GetSellersPresenter {

    @Inject
    APIValue apiValue;

    private Context context;
    private GetSellersView  getSellersView;
    private SellerInteractor sellerInteractor;

    public GetSellersPresenterImpl(Context context, SellerInteractor sellerInteractor) {
        super(context);
        this.sellerInteractor = sellerInteractor;
        this.context = context;
    }

    @Override
    public void setView(GetSellersView view) {
        this.getSellersView = view;
    }

    @Override
    public void onSellers(List<Seller> PRSellers) {
        getSellersView.onSellersSuccess(PRSellers);
    }

    @Override
    public void initialize() {
    }

    public void getSellers() {
        getSellersView.showLoading();
        sellerInteractor.getSellers(apiValue.getDeviceId(), new SellerInteractor.GetSellersCallback() {

            @Override
            public void onSuccess(String chal, List<PR_ASeller> PRSellers) {
                getSellersView.hideLoading();
                apiValue.setChal(chal);
                onSellers((List<Seller>)(List<?>)PRSellers);
            }

            @Override
            public void onError(BaseException e) {
                Log.e(LogUtils.generateTag(this), "Error on interactor Seller");
                getSellersView.hideLoading();

                switch (e.getType()){
                    case BUSINESS:
                        if (e.getResponse() != null && e.getResponse().getStatus() != null)
                            getSellersView.displaySttPopUp(e.getResponse().getDetail(),e.getResponse().getStatus());
                        break;
                    case TECHNICAL:
                        getSellersView.onError();
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
