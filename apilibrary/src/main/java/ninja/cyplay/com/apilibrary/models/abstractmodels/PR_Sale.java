package ninja.cyplay.com.apilibrary.models.abstractmodels;

import ninja.cyplay.com.apilibrary.models.APIResource;

/**
 * Created by andre on 28-Feb-19.
 */
@APIResource(name = PR_Sale.API_RESOURCE_NAME)
public class PR_Sale implements ResourceId {

    public final static transient String API_RESOURCE_NAME = "sales";

    protected String id;

    public String getId() {
        return id;
    }
}
