package ninja.cyplay.com.apilibrary.domain.repositoryModel.responses;

/**
 * Created by damien on 10/08/16.
 */
public class CreateUpdateDeleteResourceResponse extends BaseResponse {

    public String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
