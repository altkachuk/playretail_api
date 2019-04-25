package ninja.cyplay.com.playretail_api.ui.presenter;

import android.content.Context;

import java.util.List;

import javax.inject.Inject;

import ninja.cyplay.com.apilibrary.domain.interactor.CustomerInteractor;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.exception.BaseException;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ASalesHistory;
import ninja.cyplay.com.playretail_api.model.business.SalesHistory;
import ninja.cyplay.com.apilibrary.models.singleton.APIValue;
import ninja.cyplay.com.playretail_api.app.BasePresenter;
import ninja.cyplay.com.playretail_api.ui.view.GetSalesHistoryView;

/**
 * Created by damien on 06/05/15.
 */
public class GetSalesHistoryPresenterImpl extends BasePresenter implements GetSalesHistoryPresenter {

    @Inject
    APIValue apiValue;

    private Context context;
    private GetSalesHistoryView     getSalesHistoryView;
    private CustomerInteractor customerInteractor;

    public GetSalesHistoryPresenterImpl(Context context, CustomerInteractor customerInteractor) {
        super(context);
        this.context = context;
        this.customerInteractor = customerInteractor;
    }

    @Override
    public void getSalesHistoryFromEAN(String EAN) {
        getSalesHistoryView.showLoading();
        customerInteractor.getCustomerSalesHistory(apiValue.getSid(), apiValue.getDeviceId(), EAN, new CustomerInteractor.GetCustomerSalesHistoryCallback() {

            @Override
            public void onSuccess(Integer cnt, List<PR_ASalesHistory> salesHistories) {
                getSalesHistoryView.hideLoading();
                getSalesHistoryView.onSalesHistorySuccess(cnt, (List<SalesHistory>)(List<?>)salesHistories);
            }

            @Override
            public void onError(BaseException e) {
                getSalesHistoryView.hideLoading();

                switch (e.getType()) {
                    case BUSINESS:
                        if (e.getResponse() != null && e.getResponse().getStatus() != null)
                            getSalesHistoryView.onSalesHistoryError();
//                            getSalesHistoryView.displaySttPopUp(e.getResponse().getDetail(), e.getResponse().getStatus());
                        break;
                    case TECHNICAL:
                        getSalesHistoryView.onSalesHistoryError();
                        break;
                }

            }

        });
    }

    @Override
    public void initialize() {

    }

    @Override
    public void setView(GetSalesHistoryView view) {
        getSalesHistoryView = view;
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
