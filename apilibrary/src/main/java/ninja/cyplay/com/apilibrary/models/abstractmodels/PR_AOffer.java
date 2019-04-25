package ninja.cyplay.com.apilibrary.models.abstractmodels;

import ninja.cyplay.com.apilibrary.models.APIResource;

/**
 * Created by romainlebouc on 02/09/16.
 */
@APIResource(name = PR_AOffer.API_RESOURCE_NAME)
public class PR_AOffer {

    public final static transient String API_RESOURCE_NAME = "offers";

    String id;

    public String getId() {
        return id;
    }

}
