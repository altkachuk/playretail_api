package ninja.cyplay.com.apilibrary;

import android.content.Context;

/**
 * Created by wentongwang on 21/06/2017.
 */

public class TestApplication extends MVPCleanArchitectureApplication {

    @Override
    protected void initRealm() {
        //the robolectric not support realm
        //so do not init realm
        //TODO:if need, should create the mock for realm
    }

    @Override
    protected void initRealmConfiguration(Context context) {

    }
}
