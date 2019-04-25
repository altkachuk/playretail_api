package ninja.cyplay.com.apilibrary.domain.repositoryModel.requests;

import java.util.Locale;

public class ProvisionDeviceRequestBody {

    final String id;
    final String shop_id;
    String language;

    public ProvisionDeviceRequestBody(String udid, String shop_id) {
        this.id = udid;
        this.shop_id = shop_id;
        this.language = "en";
        try {
            this.language = Locale.getDefault().getLanguage();
        }
        catch(Exception e) { }
    }

    public String getId() {
        return id;
    }
}