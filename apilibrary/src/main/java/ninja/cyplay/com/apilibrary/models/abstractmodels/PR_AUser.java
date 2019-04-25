package ninja.cyplay.com.apilibrary.models.abstractmodels;

import ninja.cyplay.com.apilibrary.models.APIResource;

/**
 * Created by romainlebouc on 01/02/2017.
 */

@APIResource(name = PR_AUser.API_RESOURCE_NAME)
public class PR_AUser implements ResourceId {

    public final static transient String API_RESOURCE_NAME = "users";
    String id;

    public String getId() {
        return id;
    }
}
