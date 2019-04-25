package ninja.cyplay.com.apilibrary.models.abstractmodels;

import ninja.cyplay.com.apilibrary.models.APIResource;

/**
 * Created by damien on 04/01/16.
 */
@APIResource(name = PR_ATicket.API_RESOURCE_NAME)
public class PR_ATicket {
    public final static transient String API_RESOURCE_NAME = "sales";
    String id;
    public String getId() {
        return id;
    }
}
