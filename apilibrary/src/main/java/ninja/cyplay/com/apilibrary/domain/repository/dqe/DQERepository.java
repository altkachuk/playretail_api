package ninja.cyplay.com.apilibrary.domain.repository.dqe;

import java.util.Map;

import ninja.cyplay.com.apilibrary.domain.repositoryModel.exception.BaseException;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.requests.ResourceRequest;
import ninja.cyplay.com.apilibrary.models.abstractmodels.DQEResult;

/**
 * Created by wentongwang on 08/06/2017.
 */

public interface DQERepository {

    void initRepository(String baseUrl);

    Map<String, DQEResult> getAddress(ResourceRequest request) throws BaseException;
}
