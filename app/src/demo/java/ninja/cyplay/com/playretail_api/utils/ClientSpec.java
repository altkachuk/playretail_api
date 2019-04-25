package ninja.cyplay.com.playretail_api.utils;

import java.util.HashMap;

import ninja.cyplay.com.apilibrary.domain.repositoryModel.factory.ModelFactory;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ACategory;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ACustomerDetails;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ACustomerPreview;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AFilter;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AOffer;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AProduct;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AProductOpinion;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ARecommendation;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ASalesHistory;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ASeller;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AShop;
import ninja.cyplay.com.playretail_api.model.business.CustomerDetails;
import ninja.cyplay.com.playretail_api.model.business.CustomerPreview;
import ninja.cyplay.com.playretail_api.model.business.Offer;
import ninja.cyplay.com.playretail_api.model.business.Seller;
import ninja.cyplay.com.playretail_api.model.business.Category;
import ninja.cyplay.com.playretail_api.model.business.Filter;
import ninja.cyplay.com.playretail_api.model.business.Product;
import ninja.cyplay.com.playretail_api.model.business.ProductOpinion;
import ninja.cyplay.com.playretail_api.model.business.Recommendation;
import ninja.cyplay.com.playretail_api.model.business.SalesHistory;
import ninja.cyplay.com.playretail_api.model.business.Shop;

/**
 * Created by damien on 10/03/16.
 */
public class ClientSpec {

    public static void feedFactory() {
        ModelFactory.getInstance().register(PR_AShop.class.getName(), Shop.class);

        ModelFactory.getInstance().register(PR_AShop.class.getName(), Shop.class);

        ModelFactory.getInstance().register(PR_ASeller.class.getName(), Seller.class);

        ModelFactory.getInstance().register(PR_ACustomerDetails.class.getName(), CustomerDetails.class);
        ModelFactory.getInstance().register(PR_ACustomerPreview.class.getName(), CustomerPreview.class);

        ModelFactory.getInstance().register(PR_AOffer.class.getName(), Offer.class);
        ModelFactory.getInstance().register(PR_AProductOpinion.class.getName(), ProductOpinion.class);
        ModelFactory.getInstance().register(PR_ARecommendation.class.getName(), Recommendation.class);
        ModelFactory.getInstance().register(PR_ASalesHistory.class.getName(), SalesHistory.class);

        ModelFactory.getInstance().register(PR_ACategory.class.getName(), Category.class);
        ModelFactory.getInstance().register(PR_AFilter.class.getName(), Filter.class);
        ModelFactory.getInstance().register(PR_AProduct.class.getName(), Product.class);
    }

    // Map For Mapping between Form fields and Customer object

    private final static HashMap<String, String> fieldMapping = new HashMap<String, String>() {{
        put("firstNameRowTag", "fn");
        put("lastNameRowTag", "name");
        put("civilityRowTag", "civ");
        put("birthdateRowTag", "dob");
        put("addressRowTag", "ad3");
        put("mobileNumberRowTag", "mn");
        put("zipCodeRowTag", "zc");
        put("emailRowTag", "emails");
    }};

    public static HashMap<String, String> getFieldMapping() {
        return fieldMapping;
    }
}
