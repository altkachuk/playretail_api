package ninja.cyplay.com.apilibrary.domain.repositoryModel.factory;

import ninja.cyplay.com.apilibrary.models.abstractmodels.ActionEvent;
import ninja.cyplay.com.apilibrary.models.abstractmodels.WSReport;
import ninja.cyplay.com.apilibrary.models.business.OAuth2Credentials;

/**
 * Created by damien on 16/12/15.
 */
public class FactoryHelper {

    public static void initialize() {
        initializeModels();
    }

    private static void initializeModels() {
        ModelFactory.getInstance().register(OAuth2Credentials.class, OAuth2Credentials.class);
        ModelFactory.getInstance().register(WSReport.class, WSReport.class);
        ModelFactory.getInstance().register(ActionEvent.class, ActionEvent.class);
    }

}
