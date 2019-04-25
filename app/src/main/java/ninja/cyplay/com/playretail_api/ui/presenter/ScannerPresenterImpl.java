package ninja.cyplay.com.playretail_api.ui.presenter;

import android.content.Context;

import javax.inject.Inject;

import ninja.cyplay.com.apilibrary.domain.interactor.ScannerInteractor;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.exception.BaseException;
import ninja.cyplay.com.apilibrary.models.business.EScanFilters;
import ninja.cyplay.com.apilibrary.models.singleton.APIValue;
import ninja.cyplay.com.playretail_api.app.BasePresenter;
import ninja.cyplay.com.playretail_api.ui.view.ScannerView;

/**
 * Created by anishosni on 29/04/15.
 */
public class ScannerPresenterImpl extends BasePresenter implements ScannerPresenter {

    @Inject
    APIValue apiValue;

    private Context context;
    private ScannerView scannerView;
    private ScannerInteractor scannerInteractor;

    public ScannerPresenterImpl(Context context, ScannerInteractor scannerInteractor) {
        super(context);
        this.context = context;
        this.scannerInteractor = scannerInteractor;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void setView(ScannerView view) {
        this.scannerView = view;
    }

    @Override
    public void startScan() {
        scannerView.startScanningWindow();
    }

    @Override
    public void checkScanCode(String scanner_string, final EScanFilters eScanFilters) {

        scannerView.showLoading();
        // TODO (Client Specific) clean and prepare the search text query
        String searchtext = scanner_string;
        scannerInteractor.execute(apiValue.getSid(), apiValue.getDeviceId(), String.valueOf(searchtext),eScanFilters, new ScannerInteractor.Callback(){

            @Override
            public void onSuccess(String code, int eanc) {
                scannerView.onGetCorrespondanceSuccess(code, eanc);
            }

            @Override
            public void onError(BaseException e) {
                scannerView.hideLoading();

                switch(e.getType()){
                    case BUSINESS:
                        if (e.getResponse() != null && e.getResponse().getStatus() != null)
                            scannerView.displaySttPopUp(e.getResponse().getDetail(), e.getResponse().getStatus());
                            break;
                    case TECHNICAL:
                        scannerView.onError();
                        break;
                }
            }
        });
    }

    @Override
    public void onViewCreate() {

    }

    @Override
    public void onViewResume() {

    }

    @Override
    public void onViewDestroy() {

    }

}
