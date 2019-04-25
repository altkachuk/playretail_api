package ninja.cyplay.com.apilibrary.models.abstractmodels;

import ninja.cyplay.com.apilibrary.models.APIResource;
import ninja.cyplay.com.apilibrary.models.ReadOnlyModelField;

/**
 * Created by wentongwang on 24/03/2017.
 */
@APIResource(name = PR_ADeliveryMode.API_RESOURCE_NAME)
public class PR_ADeliveryMode implements ResourceId {

    public final static transient String API_RESOURCE_NAME = "deliverymodes";

    @ReadOnlyModelField
    protected String id;

    @Override
    public String getId() {
        return id;
    }
}
