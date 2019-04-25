package ninja.cyplay.com.playretail_api.app;

import android.content.Context;

import ninja.cyplay.com.apilibrary.MVPCleanArchitectureApplication;

/**
 * @author glomadrian
 */
public class BasePresenter {

    public BasePresenter(Context context) {
        ((MVPCleanArchitectureApplication) context.getApplicationContext()).inject(this);
    }
}
