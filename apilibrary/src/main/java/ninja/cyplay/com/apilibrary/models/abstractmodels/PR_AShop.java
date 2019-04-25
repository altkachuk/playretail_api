package ninja.cyplay.com.apilibrary.models.abstractmodels;

import ninja.cyplay.com.apilibrary.models.APIResource;

/**
 * Created by damien on 17/12/15.
 */
@APIResource(name = PR_AShop.API_RESOURCE_NAME)
public class PR_AShop {

    public final static transient String API_RESOURCE_NAME = "shops";

    public String id;

    public String getId() {
        return id;
    }
}
