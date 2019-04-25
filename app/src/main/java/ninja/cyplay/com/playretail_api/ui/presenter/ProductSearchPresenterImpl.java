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
import ninja.cyplay.com.playretail_api.ui.view.ProductSearchView;

/**
 * Created by damien on 05/05/15.
 */
public class ProductSearchPresenterImpl extends BasePresenter implements ProductSearchPresenter {

    @Inject
    APIValue apiValue;

    private Context context;
    private ProductSearchView  productSearchView;
    private ProductInteractor productInteractor;

    public ProductSearchPresenterImpl(Context context, ProductInteractor productInteractor) {
        super(context);
        this.context = context;
        this.productInteractor = productInteractor;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void setView(ProductSearchView view) {
        this.productSearchView = view;
    }

    @Override
    public void searchProduct(String product) {
        productSearchView.showLoading();
        productInteractor.searchFromName(apiValue.getSid(), apiValue.getDeviceId(), product, new ProductInteractor.SearchFromNameCallback() {

            @Override
            public void onSuccess(List<PR_AProduct> PRProducts) {
                productSearchView.hideLoading();
                productSearchView.onSearchSuccess((List<Product>)(List<?>)PRProducts);
            }

            @Override
            public void onError(BaseException e) {
                Log.e(LogUtils.generateTag(this), "Error on ProductSearchPresenterImpl");
                productSearchView.hideLoading();

                switch(e.getType()){
                    case BUSINESS:
                        if (e.getResponse() != null && e.getResponse().getStatus() != null)
                            productSearchView.displaySttPopUp(e.getResponse().getDetail(),e.getResponse().getStatus());
                        break;

                    case TECHNICAL:
                        productSearchView.onError();
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
