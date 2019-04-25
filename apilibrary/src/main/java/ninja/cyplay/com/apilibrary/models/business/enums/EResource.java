package ninja.cyplay.com.apilibrary.models.business.enums;

import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ACatalogueLevel;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ACustomer;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AOffer;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AProduct;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ASeller;

/**
 * Created by romainlebouc on 23/09/16.
 */

public enum EResource {

    SELLER(PR_ASeller.API_RESOURCE_NAME),
    PRODUCT(PR_AProduct.API_RESOURCE_NAME),
    CUSTOMER(PR_ACustomer.API_RESOURCE_NAME),
    OFFER(PR_AOffer.API_RESOURCE_NAME),
    CATALOG(PR_ACatalogueLevel.API_RESOURCE_NAME);

    private final String code;

    EResource(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}