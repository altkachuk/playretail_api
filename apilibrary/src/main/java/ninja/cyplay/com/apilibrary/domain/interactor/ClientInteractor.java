package ninja.cyplay.com.apilibrary.domain.interactor;

import java.util.HashMap;
import java.util.List;

import ninja.cyplay.com.apilibrary.domain.interactor.callback.PaginatedResourceRequestCallback;
import ninja.cyplay.com.apilibrary.domain.interactor.callback.ResourceRequestCallback;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AConfig;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ADevice;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AShop;

/**
 * Created by damien on 22/05/15.
 */
public interface ClientInteractor {

    void getConfig(String udid, String app_version, String shopId, final ResourceRequestCallback<PR_AConfig> pr_aConfigResourceRequestCallback);

    void getShops(String udid,
                  boolean all,
                  List<String> fields,
                  List<String> sortBys,
                  HashMap<String,String> filters,
                  final PaginatedResourceRequestCallback<PR_AShop> callback);

    void provisionDevice(String udid, String shopId, final ResourceRequestCallback<PR_ADevice> callback);

    void updateDeviceStore(String udid, String shopId, final ResourceRequestCallback<PR_ADevice> callback);

}
