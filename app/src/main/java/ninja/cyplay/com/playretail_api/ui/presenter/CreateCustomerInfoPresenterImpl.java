package ninja.cyplay.com.playretail_api.ui.presenter;

import android.content.Context;

import javax.inject.Inject;

import ninja.cyplay.com.apilibrary.domain.interactor.CustomerInteractor;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.exception.BaseException;
import ninja.cyplay.com.playretail_api.model.business.CustomerDetails;
import ninja.cyplay.com.apilibrary.models.singleton.APIValue;
import ninja.cyplay.com.playretail_api.app.BasePresenter;
import ninja.cyplay.com.playretail_api.ui.view.CreateCustomerView;

/**
 * Created by damien on 20/01/16.
 */
public class CreateCustomerInfoPresenterImpl extends BasePresenter implements CreateCustomerInfoPresenter {

    @Inject
    APIValue apiValue;

    private Context context;
    private CreateCustomerView createCustomerView;
    private CustomerInteractor customerInteractor;

    public CreateCustomerInfoPresenterImpl(Context context, CustomerInteractor customerInteractor) {
        super(context);
        this.context = context;
        this.customerInteractor = customerInteractor;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void setView(CreateCustomerView view) {
        this.createCustomerView = view;
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
    public void createCustomer(CustomerDetails customerDetails) {
        createCustomerView.showLoading();
        //TODO Check NULL ? EAN ?
        customerInteractor.createCustomerInfo(apiValue.getSid(), apiValue.getDeviceId(), null, customerDetails, new CustomerInteractor.CreateCustomerInfoCallback() {

            @Override
            public void onSuccess() {
                createCustomerView.hideLoading();
                createCustomerView.onCreateCustomerSuccess();
            }

            @Override
            public void onError(BaseException e) {
                createCustomerView.hideLoading();
                switch (e.getType()) {
                    case BUSINESS:
                        if (e.getResponse() != null && e.getResponse().getStatus() != null)
                            createCustomerView.displaySttPopUp(e.getResponse().getDetail(), e.getResponse().getStatus());
                        break;
                    case TECHNICAL:
                        createCustomerView.onError();
                        break;
                }
            }
        });
    }

}
