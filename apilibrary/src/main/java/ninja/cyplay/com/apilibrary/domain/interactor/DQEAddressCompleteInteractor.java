package ninja.cyplay.com.apilibrary.domain.interactor;

import java.util.List;
import java.util.Map;

import ninja.cyplay.com.apilibrary.domain.interactor.callback.ResourceRequestCallback;
import ninja.cyplay.com.apilibrary.models.abstractmodels.DQEResult;


/**
 * Created by wentongwang on 08/06/2017.
 */

public interface DQEAddressCompleteInteractor {

    void setBaseUrl(String baseUrl);

    void getAddress(Map<String, String> params, final ResourceRequestCallback<List<DQEResult>> callback);
}
