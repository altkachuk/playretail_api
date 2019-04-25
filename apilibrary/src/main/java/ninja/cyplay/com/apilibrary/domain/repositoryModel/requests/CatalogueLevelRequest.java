package ninja.cyplay.com.apilibrary.domain.repositoryModel.requests;

import java.util.List;

import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AFilterCheck;

public class CatalogueLevelRequest  {

    String category;

    List<PR_AFilterCheck> filters;

    public CatalogueLevelRequest(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<PR_AFilterCheck> getFilters() {
        return filters;
    }

    public void setFilters(List<PR_AFilterCheck> filters) {
        this.filters = filters;
    }
}