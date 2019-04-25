package ninja.cyplay.com.apilibrary.models.abstractmodels;

import ninja.cyplay.com.apilibrary.models.APIResource;
import ninja.cyplay.com.apilibrary.models.ReadOnlyModelField;

/**
 * Created by romainlebouc on 06/04/2017.
 */
@APIResource(name = PR_ABasketOffer.API_RESOURCE_NAME)
public class PR_ABasketOffer implements ResourceId{

    public final static transient String API_RESOURCE_NAME = "basket_offers";

    @ReadOnlyModelField
    protected String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
