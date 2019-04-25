package ninja.cyplay.com.playretail_api.utils;

import ninja.cyplay.com.apilibrary.domain.repositoryModel.factory.ModelFactory;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AProduct;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ARecommendation;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ASalesHistory;
import ninja.cyplay.com.playretail_api.model.business.Product;
import ninja.cyplay.com.playretail_api.model.business.Recommendation;
import ninja.cyplay.com.playretail_api.model.business.SalesHistory;

/**
 * Created by damien on 10/03/16.
 */
public class ClientSpec {

    public static void feedFactory() {
        ModelFactory.getInstance().register(PR_AProduct.class.getName(), Product.class);
        ModelFactory.getInstance().register(PR_ARecommendation.class.getName(), Recommendation.class);
        ModelFactory.getInstance().register(PR_ASalesHistory.class.getName(), SalesHistory.class);
    }

}
