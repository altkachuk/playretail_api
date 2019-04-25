package ninja.cyplay.com.playretail_api.ui.presenter;

import android.content.Context;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import ninja.cyplay.com.apilibrary.domain.interactor.CustomerInteractor;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.exception.BaseException;
import ninja.cyplay.com.apilibrary.models.singleton.APIValue;
import ninja.cyplay.com.apilibrary.utils.LogUtils;
import ninja.cyplay.com.playretail_api.app.BasePresenter;
import ninja.cyplay.com.playretail_api.ui.view.CustomerCompletionView;

/**
 * Created by damien on 05/05/15.
 */
public class CustomerCompletionPresenterImpl extends BasePresenter implements CustomerCompletionPresenter {

    @Inject
    APIValue apiValue;

    private Context context;
    private CustomerCompletionView  customerCompletionView;
    private CustomerInteractor customerInteractor;

    public CustomerCompletionPresenterImpl(Context context, CustomerInteractor customerInteractor) {
        super(context);
        this.context = context;
        this.customerInteractor = customerInteractor;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void setView(CustomerCompletionView view) {
        this.customerCompletionView = view;
    }

    @Override
    public void completionCustomer(String customer) {
        customerCompletionView.showLoading();
        customerInteractor.completionFromName(apiValue.getSid(), apiValue.getDeviceId(), customer, new CustomerInteractor.CompletionFromNameCallback() {

            @Override
            public void onSuccess(List<String> customers) {
                customerCompletionView.hideLoading();
                customerCompletionView.onCompletionSuccess(customers);
            }

            @Override
            public void onError(BaseException e) {
                Log.e(LogUtils.generateTag(this), "Error on CustomerCompletionPresenterImpl");
                customerCompletionView.hideLoading();

                switch(e.getType()){
                    case BUSINESS:
                        if (e.getResponse() != null && e.getResponse().getStatus() != null)
                            customerCompletionView.displaySttPopUp(e.getResponse().getDetail(),e.getResponse().getStatus());
                        break;

                    case TECHNICAL:
                        customerCompletionView.onError();
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
