package ninja.cyplay.com.playretail_api.ui.presenter;

import android.content.Context;

import java.util.List;

import javax.inject.Inject;

import ninja.cyplay.com.apilibrary.domain.interactor.ReportingInteractor;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.exception.BaseException;
import ninja.cyplay.com.apilibrary.models.business.SugarORM.ReportData;
import ninja.cyplay.com.apilibrary.models.singleton.APIValue;
import ninja.cyplay.com.playretail_api.app.BasePresenter;
import ninja.cyplay.com.playretail_api.ui.view.SaveReportingWebServicesView;

/**
 * Created by damien on 26/05/15.
 */
public class SaveReportingWebServicesPresenterImpl extends BasePresenter implements SaveReportingWebServicesPresenter {

    @Inject
    APIValue apiValue;

    private Context context;
    private SaveReportingWebServicesView savePagesViewsView;
    private ReportingInteractor reportingInteractor;

    public SaveReportingWebServicesPresenterImpl(Context context, ReportingInteractor reportingInteractor) {
        super(context);
        this.context = context;
        this.reportingInteractor = reportingInteractor;
    }

    @Override
    public void saveReportingWebServices(List<ReportData> reportDataList) {
        if (apiValue == null || reportDataList == null || reportDataList.size() == 0)
            return;
        reportingInteractor.saveDisplayTimes(apiValue.getDeviceId(), reportDataList, new ReportingInteractor.saveDisplayTimesCallback() {

            @Override
            public void onSuccess() {
                savePagesViewsView.onSavingReportingWebServicesSuccess();
            }

            @Override
            public void onError(BaseException e) {
                savePagesViewsView.onSavingReportingWebServicesError();
            }

        });
    }

    @Override
    public void initialize() {

    }

    @Override
    public void setView(SaveReportingWebServicesView view) {
        this.savePagesViewsView = view;
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