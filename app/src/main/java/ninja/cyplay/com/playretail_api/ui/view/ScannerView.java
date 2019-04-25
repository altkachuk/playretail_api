package ninja.cyplay.com.playretail_api.ui.view;

/**
 * Created by anishosni on 28/04/15.
 */
public interface ScannerView extends View {

    void showLoading();

    void hideLoading();

    void startScanningWindow();

    void onGetCorrespondanceSuccess(String code, int ean);


}
