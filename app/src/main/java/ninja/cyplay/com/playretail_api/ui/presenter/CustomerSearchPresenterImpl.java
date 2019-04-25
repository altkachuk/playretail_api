package ninja.cyplay.com.playretail_api.ui.presenter;

import android.content.Context;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import ninja.cyplay.com.apilibrary.domain.interactor.CustomerInteractor;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.exception.BaseException;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ACustomerPreview;
import ninja.cyplay.com.playretail_api.model.business.CustomerPreview;
import ninja.cyplay.com.apilibrary.models.singleton.APIValue;
import ninja.cyplay.com.apilibrary.utils.LogUtils;
import ninja.cyplay.com.playretail_api.app.BasePresenter;
import ninja.cyplay.com.playretail_api.ui.view.CustomerSearchView;

/**
 * Created by damien on 05/05/15.
 */
public class CustomerSearchPresenterImpl extends BasePresenter implements CustomerSearchPresenter {

    @Inject
    APIValue apiValue;

    private Context context;
    private CustomerSearchView  customerSearchView;
    private CustomerInteractor customerInteractor;

    public CustomerSearchPresenterImpl(Context context, CustomerInteractor customerInteractor) {
        super(context);
        this.context = context;
        this.customerInteractor = customerInteractor;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void setView(CustomerSearchView view) {
        this.customerSearchView = view;
    }

    @Override
    public void searchCustomer(String customer) {
        customerSearchView.showLoading();
        customerInteractor.searchFromName(apiValue.getSid(), apiValue.getDeviceId(), customer, new CustomerInteractor.SearchFromNameCallback() {

            @Override
            public void onSuccess(List<PR_ACustomerPreview> customers) {
                customerSearchView.hideLoading();
                customerSearchView.onSearchSuccess((List<CustomerPreview>)(List<?>)customers);
            }

            @Override
            public void onError(BaseException e) {
                Log.e(LogUtils.generateTag(this), "Error on CustomerSearchPresenterImpl");
                customerSearchView.hideLoading();

                switch(e.getType()){
                    case BUSINESS:
                        if (e.getResponse() != null && e.getResponse().getStatus() != null)
                            customerSearchView.displaySttPopUp(e.getResponse().getDetail(),e.getResponse().getStatus());
                        break;

                    case TECHNICAL:
                        customerSearchView.onError();
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
