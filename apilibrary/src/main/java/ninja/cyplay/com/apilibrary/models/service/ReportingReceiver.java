package ninja.cyplay.com.apilibrary.models.service;

import android.content.BroadcastReceiver;

import io.realm.Realm;
import io.realm.RealmResults;
import ninja.cyplay.com.apilibrary.models.business.reporting.ReportingResource;

/**
 * Created by romainlebouc on 11/10/16.
 */
public abstract class ReportingReceiver<TReportingResource extends ReportingResource> extends BroadcastReceiver {

    public final static int REPORT_DATA_SENDING_THRESHOLD = 10;
    public final static int MAX_RETRY_COUNT = 12;

    protected void onSyncSuccess(Realm realm, RealmResults<TReportingResource> reportingData) {
        realm.beginTransaction();
        reportingData.deleteAllFromRealm();
        realm.commitTransaction();

    }

    protected void onSyncError(Realm realm, RealmResults<TReportingResource> reportingData) {
        realm.beginTransaction();
        for (ReportingResource reportData : reportingData) {
            reportData.incSyncRetryCount();
            if (reportData.getSyncRetryCount() > MAX_RETRY_COUNT) {
                reportData.deleteFromRealm();
            }

        }
        realm.commitTransaction();

    }

}
