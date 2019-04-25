package ninja.cyplay.com.apilibrary.domain.repositoryModel.responses;

import java.util.List;

/**
 * Created by romainlebouc on 01/09/16.
 */
public class FilteredPaginatedResourcesResponse<Resource, Filter> extends PaginatedResourcesResponse<Resource> {

    public List<Filter> filters;

    public List<Filter> getFilters() {
        return filters;
    }

    public void setFilters(List<Filter> filters) {
        this.filters = filters;
    }
}
