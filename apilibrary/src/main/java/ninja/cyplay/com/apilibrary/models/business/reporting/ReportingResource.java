package ninja.cyplay.com.apilibrary.models.business.reporting;

import io.realm.RealmModel;

/**
 * Created by romainlebouc on 11/10/16.
 */
public interface ReportingResource extends RealmModel{

    int getSyncRetryCount();

    int incSyncRetryCount();

    void deleteFromRealm();
}
