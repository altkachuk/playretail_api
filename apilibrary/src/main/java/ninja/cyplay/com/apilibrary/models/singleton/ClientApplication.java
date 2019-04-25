package ninja.cyplay.com.apilibrary.models.singleton;

import android.app.Application;

/**
 * Created by romainlebouc on 10/08/16.
 */

public class ClientApplication {

    private Application application;
    // Activity that will be started after a crash, after a log out, etc...
    private Class restartActivity;

    private static ClientApplication INSTANCE = null;

    // Private __ctor__
    private ClientApplication() { }

    public static ClientApplication getInstance() {
        if (INSTANCE == null)
            INSTANCE = new ClientApplication();
        return INSTANCE;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Class getRestartActivity() {
        return restartActivity;
    }

    public void setRestartActivity(Class restartActivity) {
        this.restartActivity = restartActivity;
    }
}