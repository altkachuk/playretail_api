package ninja.cyplay.com.playretail_api.ui.presenter;

import android.content.Context;

import javax.inject.Inject;

import ninja.cyplay.com.apilibrary.domain.interactor.ProductInteractor;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.exception.BaseException;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AProduct;
import ninja.cyplay.com.playretail_api.model.business.Product;
import ninja.cyplay.com.apilibrary.models.singleton.APIValue;
import ninja.cyplay.com.playretail_api.app.BasePresenter;
import ninja.cyplay.com.playretail_api.ui.view.GetProductView;

/**
 * Created by anishosni on 05/05/15.
 */
public class GetProductPresenterImpl extends BasePresenter implements GetProductPresenter {

    @Inject
    APIValue apiValue;

    private Context context;
    private ProductInteractor productInteractor;
    private GetProductView getProductView;

    public GetProductPresenterImpl(Context context, ProductInteractor productInteractor) {
        super(context);
        this.context = context;
        this.productInteractor = productInteractor;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void setView(GetProductView view) {
        getProductView =view;
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

    @Override
    public void getProductInfoFromSkid(String skid) {

        getProductView.showLoading();
        productInteractor.getProductFromEAN(apiValue.getSid(), apiValue.getDeviceId(), skid, new ProductInteractor.GetProductFromEANCallback() {

            @Override
            public void onSuccess(PR_AProduct product) {
                // Success
                getProductView.hideLoading();
                getProductView.onProductSuccess((Product)product);
            }

            @Override
            public void onError(BaseException e) {
                getProductView.hideLoading();

                switch (e.getType()){
                    case BUSINESS:
                        if (e.getResponse() != null && e.getResponse().getStatus() != null)
                            getProductView.displaySttPopUp(e.getResponse().getDetail(),e.getResponse().getStatus());
                        break;
                    case TECHNICAL:
                        getProductView.onError();
                        break;
                }
            }

        });
    }

}
