package ninja.cyplay.com.playretail_api.ui.presenter;

import android.content.Context;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import ninja.cyplay.com.apilibrary.domain.interactor.CustomerInteractor;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.exception.BaseException;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ABasket;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ACustomerDetails;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AOffer;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AProduct;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AProductOpinion;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ARecommendation;
import ninja.cyplay.com.apilibrary.models.business.PR_Basket;
import ninja.cyplay.com.apilibrary.models.singleton.APIValue;
import ninja.cyplay.com.playretail_api.app.BasePresenter;
import ninja.cyplay.com.playretail_api.model.business.CustomerDetails;
import ninja.cyplay.com.playretail_api.model.business.CustomerPreview;
import ninja.cyplay.com.playretail_api.model.business.Offer;
import ninja.cyplay.com.playretail_api.model.business.Product;
import ninja.cyplay.com.playretail_api.model.business.ProductOpinion;
import ninja.cyplay.com.playretail_api.model.business.Recommendation;
import ninja.cyplay.com.playretail_api.model.singleton.CustomerContext;
import ninja.cyplay.com.playretail_api.model.singleton.SellerContext;
import ninja.cyplay.com.playretail_api.ui.view.GetCustomerInfoView;

/**
 * Created by damien on 06/05/15.
 */
public class GetCustomerInfoPresenterImpl extends BasePresenter implements GetCustomerInfoPresenter {

    @Inject
    APIValue apiValue;

    @Inject
    CustomerContext customerContext;

    @Inject
    SellerContext sellerContext;

    private Context context;
    private GetCustomerInfoView getCustomerInfoView;
    private CustomerInteractor customerInteractor;

    public GetCustomerInfoPresenterImpl(Context context, CustomerInteractor customerInteractor) {
        super(context);
        this.context = context;
        this.customerInteractor = customerInteractor;
    }

    @Override
    public void getCustomerInfoFromEAN(String EAN) {
        getCustomerInfoView.showLoading();
        customerInteractor.getCustomerInfo(apiValue.getSid(), apiValue.getDeviceId(), EAN, new CustomerInteractor.GetCustomerInfoCallback() {

            @Override
            public void onSuccess(String cid, PR_ACustomerDetails prACustomerDetails, PR_ARecommendation recommendation, List<PR_AOffer> offers, List<PR_AProduct> wishlist, Map<String, PR_AProductOpinion> productOpinionMap, PR_ABasket basket) {

                    // Create Customer Context
                    CustomerPreview PRCustomerPreview = new CustomerPreview(cid, (CustomerDetails)prACustomerDetails);
                    customerContext.setCustomer(PRCustomerPreview);
                    customerContext.setWishlist(wishlist);
                    customerContext.setProductOpinionMap((Map<String, ProductOpinion>)(Map<String, ?>)productOpinionMap);
                    customerContext.setBasket((PR_Basket) basket);
                    // Add Customer to History
                    sellerContext.addCustomer(PRCustomerPreview);
                    // Success
                    getCustomerInfoView.hideLoading();
                    getCustomerInfoView.onCustomerInfoSuccess(cid, (CustomerDetails)prACustomerDetails, (Recommendation)recommendation, (List<Offer>)(List<?>)offers, (List<Product>)(List<?>)wishlist);
                }

            @Override
            public void onError(BaseException e) {
                getCustomerInfoView.hideLoading();

                switch (e.getType()){
                    case BUSINESS:
                        if (e.getResponse() != null && e.getResponse().getStatus() != null)
                            getCustomerInfoView.displaySttPopUp(e.getResponse().getDetail(),e.getResponse().getStatus());
                        break;
                    case TECHNICAL:
                        getCustomerInfoView.onError();
                        break;
                }
            }

        });
    }

    @Override
    public void initialize() {

    }

    @Override
    public void setView(GetCustomerInfoView view) {
        getCustomerInfoView = view;
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
