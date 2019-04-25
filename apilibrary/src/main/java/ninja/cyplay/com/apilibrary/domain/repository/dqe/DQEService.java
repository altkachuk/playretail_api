package ninja.cyplay.com.apilibrary.domain.repository.dqe;

import java.util.Map;

import ninja.cyplay.com.apilibrary.models.abstractmodels.DQEResult;
import retrofit.http.GET;
import retrofit.http.QueryMap;

/**
 * Created by wentongwang on 08/06/2017.
 */

public interface DQEService {

    @GET("/SINGLEV2")
    Map<String, DQEResult> getAddress(@QueryMap Map<String, String> params);

}
