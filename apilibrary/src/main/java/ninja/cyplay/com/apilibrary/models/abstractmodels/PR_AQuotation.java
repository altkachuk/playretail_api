package ninja.cyplay.com.apilibrary.models.abstractmodels;

import ninja.cyplay.com.apilibrary.models.APIResource;

/**
 * Created by wentongwang on 07/07/2017.
 */
@APIResource(name = PR_AProduct.API_RESOURCE_NAME)
public class PR_AQuotation implements ResourceId {

    public final static transient String API_RESOURCE_NAME = "quotations";

    private String id;

    @Override
    public String getId() {
        return id;
    }
}
