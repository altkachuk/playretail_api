package ninja.cyplay.com.apilibrary.domain.repositoryModel.responses;

import java.util.List;


/**
 * Created by romainlebouc on 09/08/16.
 */
public class PaginatedResourcesResponse<Resource> extends BaseResponse {

    public List<Resource> results;
    public Integer count;
    public String next;
    public String previous;

    public List<Resource> getResults() {
        return results;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public void setResults(List<Resource> results) {
        this.results = results;
    }
}
