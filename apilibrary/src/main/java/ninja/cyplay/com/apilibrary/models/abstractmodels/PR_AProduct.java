package ninja.cyplay.com.apilibrary.models.abstractmodels;

import ninja.cyplay.com.apilibrary.models.APIResource;

/**
 * Created by damien on 18/12/15.
 */
@APIResource(name = PR_AProduct.API_RESOURCE_NAME, filterclass = PR_AProductFilter.class)
public class PR_AProduct {
    public final static transient String API_RESOURCE_NAME = "products";

}
