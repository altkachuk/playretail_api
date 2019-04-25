package ninja.cyplay.com.apilibrary.domain.interactor;

import ninja.cyplay.com.apilibrary.domain.repositoryModel.requests.ResourceRequest;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.responses.PaginatedResourcesResponse;

/**
 * Created by romainlebouc on 18/08/16.
 */
public interface PaginatedResourceGetter<AbstractResource>{
    PaginatedResourcesResponse<AbstractResource> getPaginatedResource(ResourceRequest resourceRequest);
}