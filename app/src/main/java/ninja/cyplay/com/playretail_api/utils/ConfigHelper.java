package ninja.cyplay.com.playretail_api.utils;

import ninja.cyplay.com.apilibrary.models.business.PR_Config;
import ninja.cyplay.com.apilibrary.models.business.PR_Feature;
import ninja.cyplay.com.playretail_api.model.business.EFeature;

/**
 * Created by damien on 04/03/16.
 */
public class ConfigHelper {

    private static ConfigHelper INSTANCE = null;

    private PR_Feature feature;
    private PR_Config config;

    // private __ctor__
    private ConfigHelper() { }

    public static ConfigHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ConfigHelper();
        }
        return INSTANCE;
    }

    public void setFeature(PR_Feature feature) {
        this.feature = feature;
    }

    public PR_Feature getFeature() {
        return feature;
    }

    public PR_Config getConfig() {
        return config;
    }

    public void setConfig(PR_Config PRConfig) {
        this.config = PRConfig;
    }

    public boolean isFeatureActivated(EFeature f) {
        switch (f) {

            case SCAN:
                if (feature != null && feature.getModules() != null && feature.getModules().getScan() != null)
                    return feature.getModules().getScan().getEnabled();
                break;

            case BASKET:
                if (feature != null && feature.getModules() != null && feature.getModules().getBasket() != null)
                    return feature.getModules().getBasket().getEnabled();
                break;

            case TWEETS:
                if (feature != null && feature.getModules() != null && feature.getModules().getTweets() != null)
                    return feature.getModules().getTweets().getEnabled();
                break;

            case BEACON:
                if (feature != null && feature.getModules() != null && feature.getModules().getBeacon() != null)
                    return feature.getModules().getBeacon().getEnabled();
                break;
        }
        return false;
    }

}
