package ninja.cyplay.com.playretail_api.ui.presenter;

import android.content.Context;

import javax.inject.Inject;

import ninja.cyplay.com.apilibrary.domain.interactor.CustomerInteractor;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.exception.BaseException;
import ninja.cyplay.com.playretail_api.model.business.CustomerDetails;
import ninja.cyplay.com.apilibrary.models.singleton.APIValue;
import ninja.cyplay.com.playretail_api.model.singleton.CustomerContext;
import ninja.cyplay.com.playretail_api.model.singleton.SellerContext;
import ninja.cyplay.com.playretail_api.app.BasePresenter;
import ninja.cyplay.com.playretail_api.ui.view.UpdateCustomerInfoView;

/**
 * Created by damien on 06/05/15.
 */
public class UpdateCustomerInfoPresenterImpl extends BasePresenter implements UpdateCustomerInfoPresenter {

    @Inject
    APIValue apiValue;

    @Inject
    CustomerContext customerContext;

    @Inject
    SellerContext sellerContext;

    private Context context;
    private UpdateCustomerInfoView updateCustomerInfoView;
    private CustomerInteractor customerInteractor;

    public UpdateCustomerInfoPresenterImpl(Context context, CustomerInteractor customerInteractor) {
        super(context);
        this.context = context;
        this.customerInteractor = customerInteractor;
    }

    @Override
    public void updateCustomerInfo(String EAN, CustomerDetails PRCustomerDetails) {
        updateCustomerInfoView.showLoading();
        customerInteractor.updateCustomerInfo(apiValue.getSid(), apiValue.getDeviceId(), EAN, PRCustomerDetails, new CustomerInteractor.UpdateCustomerInfoCallback() {

            @Override
            public void onSuccess() {
                // Success
                updateCustomerInfoView.hideLoading();
                updateCustomerInfoView.onUpdateSuccess();
            }

            @Override
            public void onError(BaseException e) {
                updateCustomerInfoView.hideLoading();

                switch (e.getType()) {
                    case BUSINESS:
                        if (e.getResponse() != null && e.getResponse().getStatus() != null)
                            updateCustomerInfoView.displaySttPopUp(e.getResponse().getDetail(), e.getResponse().getStatus());
                        break;
                    case TECHNICAL:
                        updateCustomerInfoView.onError();
                        break;
                }
            }
        });
    }

    @Override
    public void initialize() {

    }

    @Override
    public void setView(UpdateCustomerInfoView view) {
        updateCustomerInfoView = view;
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
