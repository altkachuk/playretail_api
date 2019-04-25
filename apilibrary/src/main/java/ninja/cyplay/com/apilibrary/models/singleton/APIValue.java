package ninja.cyplay.com.apilibrary.models.singleton;

import android.content.Context;
import android.provider.Settings;

import ninja.cyplay.com.apilibrary.models.business.OAuth2Credentials;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AConfig;

/**
 * Created by damien on 28/04/15.
 */
public class APIValue {

    // Requirement to do Requests
    private final String deviceId;

    private OAuth2Credentials OAuth2Credentials;

    public String username = "test_seller1";
    public String password = "eBt!";

    // Config from server
    private PR_AConfig config;

    private final Context context;

    public APIValue(Context context) {
        this.context = context;
        deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public PR_AConfig getConfig() {
        return config;
    }

    public void setConfig(PR_AConfig config) {
        this.config = config;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public Context getContext() {
        return context;
    }

    public OAuth2Credentials getOAuth2Credentials() {
        return OAuth2Credentials;
    }

    public void setOAuth2Credentials(OAuth2Credentials OAuth2Credentials) {
        this.OAuth2Credentials = OAuth2Credentials;
    }

    public void clearOAuth2Credentials(){
        setOAuth2Credentials(null);

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
