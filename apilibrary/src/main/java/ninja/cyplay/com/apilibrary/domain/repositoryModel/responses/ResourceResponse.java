package ninja.cyplay.com.apilibrary.domain.repositoryModel.responses;


/**
 * Created by romainlebouc on 09/08/16.
 */
public class ResourceResponse<Resource> extends BaseResponse {

    public Resource resource;

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }
}
