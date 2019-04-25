package ninja.cyplay.com.apilibrary.domain.interactor;

import ninja.cyplay.com.apilibrary.domain.interactor.callback.ResourceRequestCallback;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AProductShare;

/**
 * Created by romainlebouc on 24/08/16.
 */
public interface ProductShareInteractor {

    void shareProducts(PR_AProductShare productShare, final ResourceRequestCallback callback);

}
