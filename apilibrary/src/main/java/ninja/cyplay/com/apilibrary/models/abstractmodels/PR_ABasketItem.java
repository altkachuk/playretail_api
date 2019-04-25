package ninja.cyplay.com.apilibrary.models.abstractmodels;

import ninja.cyplay.com.apilibrary.models.APIResource;
import ninja.cyplay.com.apilibrary.models.ReadOnlyModelField;

/**
 * Created by romainlebouc on 24/01/2017.
 */
@APIResource(name = PR_ABasketItem.API_RESOURCE_NAME)
public class PR_ABasketItem {
    public final static transient String API_RESOURCE_NAME = "items";

    @ReadOnlyModelField
    protected String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
