package ninja.cyplay.com.apilibrary.models.service;

import android.content.Context;
import android.content.Intent;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import ninja.cyplay.com.apilibrary.MVPCleanArchitectureApplication;
import ninja.cyplay.com.apilibrary.domain.component.InteractorComponent;
import ninja.cyplay.com.apilibrary.domain.component.InteractorComponentInstance;
import ninja.cyplay.com.apilibrary.domain.interactor.callback.ResourceRequestCallback;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.exception.BaseException;
import ninja.cyplay.com.apilibrary.models.abstractmodels.ActionEvent;
import ninja.cyplay.com.apilibrary.models.business.reporting.ActionEventData;

/**
 * Created by romainlebouc on 22/09/16.
 */
public class ActionEventAlarmReceiver extends ReportingReceiver <ActionEventData>{

    public final static int REPORT_DATA_SENDING_THRESHOLD = 1;

    protected RealmResults<ActionEventData> getReportingData( Realm realm ) {
        return realm.where(ActionEventData.class).findAll();
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        final Realm realm = Realm.getInstance(MVPCleanArchitectureApplication.get(context).getRealmConfiguration());
        final RealmResults<ActionEventData> reportingData = this.getReportingData(realm);

        if (reportingData.size() >= REPORT_DATA_SENDING_THRESHOLD) {
            InteractorComponent interactorComponent = InteractorComponentInstance.getInstance().getInteractorComponent();
            if (interactorComponent != null) {
                InteractorComponentInstance.getInstance().getInteractorComponent().actionEventInteractor().saveActionEvents(ActionEvent.getListFromActionEventDatas(reportingData),
                        new ResourceRequestCallback<List<ActionEvent>>() {
                            @Override
                            public void onSuccess(List<ActionEvent> ids) {
                                ActionEventAlarmReceiver.this.onSyncSuccess(realm,reportingData );
                            }

                            @Override
                            public void onError(BaseException e) {
                                ActionEventAlarmReceiver.this.onSyncError(realm, reportingData );
                            }
                        });

            }
        }

    }
}
