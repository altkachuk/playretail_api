package ninja.cyplay.com.apilibrary.models.abstractmodels;

import ninja.cyplay.com.apilibrary.models.APIResource;
import ninja.cyplay.com.apilibrary.models.ReadOnlyModelField;

/**
 * Created by romainlebouc on 08/12/2016.
 */
@APIResource(name = PR_ASeller.API_RESOURCE_NAME)
public class PR_AAddress implements ResourceId {
    public final static transient String API_RESOURCE_NAME = "addresses";

    @ReadOnlyModelField
    protected String id;

    @Override
    public String getId() {
        return id;
    }
}
