package ninja.cyplay.com.apilibrary.models.abstractmodels;

import ninja.cyplay.com.apilibrary.models.APIResource;

/**
 * Created by wentongwang on 26/06/2017.
 */
@APIResource(name = PR_AFeedback.API_RESOURCE_NAME)
public class PR_AFeedback implements ResourceId {

    public final static transient String API_RESOURCE_NAME = "feedback";

    @Override
    public String getId() {
        return null;
    }
}
