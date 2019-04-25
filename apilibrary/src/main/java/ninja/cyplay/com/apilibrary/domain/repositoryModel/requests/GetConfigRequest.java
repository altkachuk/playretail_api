package ninja.cyplay.com.apilibrary.domain.repositoryModel.requests;

import java.util.Locale;

/**
 * Created by damien on 27/04/15.
 */
public class GetConfigRequest {
    private String udid;
    private String language;
    private String app_version;
    private String shop_id;

    public GetConfigRequest(String udid, String app_version) {
        this.udid = udid;
        this.app_version = app_version;
        try {
            this.language = Locale.getDefault().getLanguage();
        } catch (Exception e) {
            this.language = "en";
        }
    }

    public GetConfigRequest(String udid, String app_version, String shopId) {
        this.udid = udid;
        this.app_version = app_version;
        this.shop_id = shopId;
        try {
            this.language = Locale.getDefault().getLanguage();
        } catch (Exception e) {
            this.language = "en";
        }

        this.language = "en";
    }

    public String getUdid() {
        return udid;
    }

    public String getLanguage() {
        return language;
    }

    public String getApp_version() {
        return app_version;
    }

    public String getShop_id() {
        return shop_id;
    }
}
