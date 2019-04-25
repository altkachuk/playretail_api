package ninja.cyplay.com.apilibrary.models.service;

import android.content.Context;
import android.content.Intent;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import ninja.cyplay.com.apilibrary.MVPCleanArchitectureApplication;
import ninja.cyplay.com.apilibrary.domain.component.InteractorComponent;
import ninja.cyplay.com.apilibrary.domain.component.InteractorComponentInstance;
import ninja.cyplay.com.apilibrary.domain.interactor.ReportingInteractor;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.exception.BaseException;
import ninja.cyplay.com.apilibrary.models.abstractmodels.WSReport;
import ninja.cyplay.com.apilibrary.models.business.reporting.ReportData;

public class ReportDataAlarmReceiver extends ReportingReceiver<ReportData> {


    protected RealmResults<ReportData> getReportingData(Realm realm) {
        return realm.where(ReportData.class).findAll();
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        //TODO : do that on background thread
        final  Realm realm = Realm.getInstance(MVPCleanArchitectureApplication.get(context).getRealmConfiguration());
        // Get All Report Data
        final RealmResults<ReportData> reportDatas = this.getReportingData(realm);

        if (reportDatas.size() >= REPORT_DATA_SENDING_THRESHOLD) {
            InteractorComponent interactorComponent = InteractorComponentInstance.getInstance().getInteractorComponent();
            if (interactorComponent != null) {
                InteractorComponentInstance.getInstance().getInteractorComponent().reportingInteractor().saveWebServiceTimes(WSReport.getListFromReportDatas(reportDatas), new ReportingInteractor.saveDisplayTimesCallback() {

                    @Override
                    public void onError(BaseException e) {
                        ReportDataAlarmReceiver.this.onSyncError(realm,
                                reportDatas );
                    }

                    @Override
                    public void onSuccess(List<String> ids) {
                        ReportDataAlarmReceiver.this.onSyncSuccess(realm,
                                reportDatas );

                    }
                });
            }

        }
    }

}