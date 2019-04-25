package ninja.cyplay.com.apilibrary.domain.interactor;

import ninja.cyplay.com.apilibrary.domain.interactor.callback.ResourceRequestCallback;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ACatalogueLevel;

/**
 * Created by damien on 04/05/15.
 */
public interface CatalogueInteractor {

    void getCatalogueLevel( String catalogueLevel, final ResourceRequestCallback<PR_ACatalogueLevel> getCatalogueLevelCallback);

}
