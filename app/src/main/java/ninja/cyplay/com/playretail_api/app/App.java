package ninja.cyplay.com.playretail_api.app;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.crashlytics.android.Crashlytics;

import java.io.IOException;

import dagger.ObjectGraph;
import io.fabric.sdk.android.Fabric;
import ninja.cyplay.com.apilibrary.MVPCleanArchitectureApplication;
import ninja.cyplay.com.apilibrary.utils.ClientUtil;
import ninja.cyplay.com.playretail_api.BuildConfig;
import ninja.cyplay.com.playretail_api.app.di.RootModule;
import ninja.cyplay.com.playretail_api.utils.ClientSpec;
import ninja.cyplay.com.playretail_api.utils.DisplayUtils;
import ninja.cyplay.com.playretail_api.utils.StringUtils;

/**
 * Created by damien on 17/11/15.
 */
public class App extends MVPCleanArchitectureApplication {

    private ObjectGraph objectGraph;

    @Override
    public void onCreate() {
        super.onCreate();

        // Fabric
        Fabric.with(this, new Crashlytics());

        // Set the host URL for Network layer, coming from the gradle build
        //ClientUtil.setClientUrl(BuildConfig.HOST);

        // Client Specifications
        ClientSpec.feedFactory();

        // Init String utils context
        StringUtils.setContext(getApplicationContext());

        // Display Spec
        DisplayUtils.setContext(getApplicationContext());

        objectGraph = ObjectGraph.create(new RootModule(this));
        objectGraph.inject(this);

        // init Firebase
        /*Firebase.setAndroidContext(this);
        // init Cache
        CacheObject.init(getApplicationContext());
        try {
            CacheObject.getCacheManager().clear();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
//
//        // Call to initialize New Relic.
//        NewRelic.withApplicationToken(Constants.KEY_NEWRELIC).start(this);
//
        // Reporting Service
//        if (!ServiceHelper.isMyServiceRunning(this, ReportingService.class)) {
//            Intent serviceIntent = new Intent(MVPCleanArchitectureApplication.this, ReportingService.class);
//            startService(serviceIntent);
//        }
    }

    // 65K Multidex
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
