package ninja.cyplay.com.apilibrary.domain.interactor;

import ninja.cyplay.com.apilibrary.domain.interactor.callback.ResourceRequestCallback;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ABarCodeInfo;
import ninja.cyplay.com.apilibrary.models.business.EScanFilters;

/**
 * Created by damien on 05/05/15.
 */
public interface ScannerInteractor {

    void execute(String scannedString, EScanFilters eScanFilters, final ResourceRequestCallback<PR_ABarCodeInfo> callback);

}
