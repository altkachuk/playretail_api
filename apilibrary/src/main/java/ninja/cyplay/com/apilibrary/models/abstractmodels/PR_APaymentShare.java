package ninja.cyplay.com.apilibrary.models.abstractmodels;

import ninja.cyplay.com.apilibrary.models.APIResource;

/**
 * Created by wentongwang on 29/03/2017.
 */
@APIResource(name = PR_APaymentShare.API_RESOURCE_NAME)
public class PR_APaymentShare implements ResourceId {
    public final static transient String API_RESOURCE_NAME = "payment_shares";

    private String id;

    @Override
    public String getId() {
        return id;
    }
}
