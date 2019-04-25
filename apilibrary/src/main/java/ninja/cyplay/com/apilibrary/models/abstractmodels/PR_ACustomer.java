package ninja.cyplay.com.apilibrary.models.abstractmodels;

import ninja.cyplay.com.apilibrary.models.APIResource;

/**
 * Created by damien on 18/12/15.
 */
@APIResource(name = PR_ACustomer.API_RESOURCE_NAME)
public class PR_ACustomer implements ResourceId{

    public final static transient String API_RESOURCE_NAME = "customers";

    protected String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
