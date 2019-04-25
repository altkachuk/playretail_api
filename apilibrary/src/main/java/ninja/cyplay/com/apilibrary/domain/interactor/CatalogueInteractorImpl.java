package ninja.cyplay.com.apilibrary.domain.interactor;

import android.util.Log;

import ninja.cyplay.com.apilibrary.domain.executor.InteractorExecutor;
import ninja.cyplay.com.apilibrary.domain.executor.MainThreadExecutor;
import ninja.cyplay.com.apilibrary.domain.interactor.callback.ResourceRequestCallback;
import ninja.cyplay.com.apilibrary.domain.repository.PlayRetailRepository;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.exception.BaseException;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.requests.CatalogueLevelRequest;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.responses.ResourceResponse;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ACatalogueLevel;
import ninja.cyplay.com.apilibrary.utils.LogUtils;

/**
 * Created by damien on 04/05/15.
 */
public class CatalogueInteractorImpl extends AbstractInteractor implements CatalogueInteractor {

    private PlayRetailRepository repository;


    public CatalogueInteractorImpl(InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor, PlayRetailRepository repository) {
        super(interactorExecutor, mainThreadExecutor);
        this.repository = repository;
    }

    // -------------------------------------------------------------------------------------------
    //
    // -------------------------------------------------------------------------------------------


    @Override
    public void getCatalogueLevel(String catalogueLevel, ResourceRequestCallback<PR_ACatalogueLevel> callback) {
        final ResourceRequestCallback<PR_ACatalogueLevel> getCatalogueLevelCallback = callback;
        final CatalogueLevelRequest catalogueLevelRequest = new CatalogueLevelRequest(catalogueLevel);
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                try {
                    final ResourceResponse<PR_ACatalogueLevel> catalogueLevelResponse = repository.catalogueLevel(catalogueLevelRequest);
                    getMainThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (getCatalogueLevelCallback != null) {
                                getCatalogueLevelCallback.onSuccess(catalogueLevelResponse.getResource());
                            } else {
                                Log.i(CatalogueInteractorImpl.class.getName(), "Request callback is None");
                            }
                        }
                    });
                } catch (final BaseException e) {
                    Log.e(LogUtils.generateTag(this), "Error on CatalogueLevel interactor");
                    getMainThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (getCatalogueLevelCallback != null) {
                                getCatalogueLevelCallback.onError(e);
                            } else {
                                Log.i(CatalogueInteractorImpl.class.getName(), "Request callback is None");
                            }
                        }
                    });
                }
            }
        });
    }


}
