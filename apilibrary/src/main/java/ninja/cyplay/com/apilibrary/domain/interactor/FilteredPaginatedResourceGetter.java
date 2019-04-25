package ninja.cyplay.com.apilibrary.domain.interactor;

import ninja.cyplay.com.apilibrary.domain.repositoryModel.requests.ResourceRequest;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.responses.FilteredPaginatedResourcesResponse;

/**
 * Created by romainlebouc on 01/09/16.
 */
public interface FilteredPaginatedResourceGetter <AbstractResource, AbstractFilter>{
    FilteredPaginatedResourcesResponse<AbstractResource, AbstractFilter> getFilteredPaginatedResource(ResourceRequest resourceRequest);
}