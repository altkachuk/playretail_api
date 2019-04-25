package ninja.cyplay.com.apilibrary.domain.interactor;

import ninja.cyplay.com.apilibrary.domain.repositoryModel.requests.ResourceRequest;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.responses.ResourceResponse;

/**
 * Created by romainlebouc on 18/08/16.
 */
public interface ResourceGetter<AbstractResource>{
    ResourceResponse<AbstractResource> getResource(ResourceRequest resourceRequest);
}