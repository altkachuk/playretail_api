package ninja.cyplay.com.apilibrary.models.abstractmodels;

import ninja.cyplay.com.apilibrary.models.APIResource;
import ninja.cyplay.com.apilibrary.models.ReadOnlyModelField;

/**
 * Created by romainlebouc on 30/01/2017.
 */
@APIResource(name = PR_AFee.API_RESOURCE_NAME)
public class PR_AFee implements ResourceId {
    public final static transient String API_RESOURCE_NAME = "fees";

    @ReadOnlyModelField
    protected String id;

    @Override
    public String getId() {
        return id;
    }
}
