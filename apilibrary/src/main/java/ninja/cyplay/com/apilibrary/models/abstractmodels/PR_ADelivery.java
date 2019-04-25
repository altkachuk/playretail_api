package ninja.cyplay.com.apilibrary.models.abstractmodels;

import ninja.cyplay.com.apilibrary.models.APIResource;
import ninja.cyplay.com.apilibrary.models.ReadOnlyModelField;

/**
 * Created by romainlebouc on 29/11/2016.
 */
@APIResource(name = PR_ADelivery.API_RESOURCE_NAME)
public class PR_ADelivery implements ResourceId {

    public final static transient String API_RESOURCE_NAME = "delivery";

    @ReadOnlyModelField
    protected String id;

    @Override
    public String getId() {
        return id;
    }
}
