package ninja.cyplay.com.playretail_api.ui.presenter;

import ninja.cyplay.com.apilibrary.models.business.EScanFilters;
import ninja.cyplay.com.playretail_api.ui.view.ScannerView;

/**
 * Created by damien on 29/04/15.
 */
public interface ScannerPresenter extends Presenter<ScannerView> {

    public void startScan();
    public void checkScanCode(String scanner_string, EScanFilters eScanFilters);


}
