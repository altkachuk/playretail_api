package ninja.cyplay.com.playretail_api.ui.presenter;

import android.content.Context;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import ninja.cyplay.com.apilibrary.domain.interactor.ProductInteractor;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.exception.BaseException;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AProduct;
import ninja.cyplay.com.playretail_api.model.business.Product;
import ninja.cyplay.com.apilibrary.models.singleton.APIValue;
import ninja.cyplay.com.apilibrary.utils.LogUtils;
import ninja.cyplay.com.playretail_api.app.BasePresenter;
import ninja.cyplay.com.playretail_api.ui.view.ProductsRelatedView;

/**
 * Created by damien on 05/05/15.
 */
public class GetProductsPresenterImpl extends BasePresenter implements GetProductsPresenter {

    @Inject
    APIValue apiValue;

    private Context context;
    private ProductInteractor productInteractor;
    private ProductsRelatedView productsRelatedView;

    public GetProductsPresenterImpl(Context context, ProductInteractor productInteractor) {
        super(context);
        this.context = context;
        this.productInteractor = productInteractor;
    }

    @Override
    public void getRelatedProducts(List<String> rp) {

        productsRelatedView.showLoading();
        productInteractor.getProductsFromIds(apiValue.getSid(), apiValue.getDeviceId(), rp, new ProductInteractor.GetProductFromIdsCallback() {

            @Override
            public void onSuccess(List<PR_AProduct> rp) {
                productsRelatedView.hideLoading();
                productsRelatedView.onProductsRelatedSuccess((List<Product>)(List<?>)rp);
            }

            @Override
            public void onError(BaseException e) {
                Log.e(LogUtils.generateTag(this), "Error on GetProductsPresenterImpl");
                productsRelatedView.hideLoading();

                switch (e.getType()) {
                    case BUSINESS:
                        if (e.getResponse() != null && e.getResponse().getStatus() != null)
                            productsRelatedView.displaySttPopUp(e.getResponse().getDetail(), e.getResponse().getStatus());
                        break;
                    case TECHNICAL:
                        productsRelatedView.onError();
                        break;
                }
            }

        });
    }

    @Override
    public void initialize() {

    }

    @Override
    public void setView(ProductsRelatedView view) {
        productsRelatedView = view;
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
