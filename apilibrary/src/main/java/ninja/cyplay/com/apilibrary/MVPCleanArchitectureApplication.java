package ninja.cyplay.com.apilibrary;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.factory.FactoryHelper;
import ninja.cyplay.com.apilibrary.models.service.ActionEventAlarmReceiver;
import ninja.cyplay.com.apilibrary.models.service.ReportDataAlarmReceiver;

/**
 * Created by damien on 17/11/15.
 */
public class MVPCleanArchitectureApplication extends Application {

    private RealmConfiguration realmConfiguration;

    public static MVPCleanArchitectureApplication get(Context context) {
        return (MVPCleanArchitectureApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Register all response models in the Model Factory
        FactoryHelper.initialize();

        initRealm();

        //initReporting();
        initActionEventSynchronisation();

        initRealmConfiguration(this.getApplicationContext());

    }

    protected void initRealm(){
        // Create realm for local database
        Realm.init(getApplicationContext());
        RealmConfiguration realmConfig = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(realmConfig);
    }

    private void initReporting() {
        // Run service
        AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), ReportDataAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
        // try cancel old if the old one still running
        alarmManager.cancel(pendingIntent);
        //Reporting webservices every 5 minutes
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                60 * 1000,
                2 * 60 * 1000, pendingIntent);
    }

    private void initActionEventSynchronisation() {
        // Run service
        AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), ActionEventAlarmReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
        // try cancel old if the old one still running
        alarmManager.cancel(pendingIntent);
        //Reporting webservices every 5 minutes
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                0,
                2 * 60 * 1000, pendingIntent);

    }

    protected void initRealmConfiguration(Context context) {
        realmConfiguration = new RealmConfiguration.Builder()
                .name("default.realm")
                .deleteRealmIfMigrationNeeded()
                .build();
    }

    public RealmConfiguration getRealmConfiguration() {
        return realmConfiguration;
    }
}