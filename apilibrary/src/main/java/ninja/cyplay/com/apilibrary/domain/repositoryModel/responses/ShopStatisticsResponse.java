package ninja.cyplay.com.apilibrary.domain.repositoryModel.responses;

import java.util.List;

import ninja.cyplay.com.apilibrary.models.business.PR_Chart;

/**
 * Created by damien on 30/05/16.
 */
public class ShopStatisticsResponse extends BaseResponse {

    List<PR_Chart> values;

    public List<PR_Chart> getValues() {
        return values;
    }

    public void setValues(List<PR_Chart> values) {
        this.values = values;
    }

}
