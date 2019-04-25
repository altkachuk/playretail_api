package ninja.cyplay.com.playretail_api.ui.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import ninja.cyplay.com.playretail_api.model.business.Offer;
import ninja.cyplay.com.playretail_api.model.business.CustomerDetails;
import ninja.cyplay.com.playretail_api.model.business.CustomerPreview;
import ninja.cyplay.com.playretail_api.model.business.Product;
import ninja.cyplay.com.apilibrary.models.business.EScanFilters;
import ninja.cyplay.com.playretail_api.model.business.Recommendation;
import ninja.cyplay.com.apilibrary.utils.Constants;
import ninja.cyplay.com.apilibrary.utils.DialogUtils;
import ninja.cyplay.com.apilibrary.utils.LogUtils;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.app.BaseFragment;
import ninja.cyplay.com.playretail_api.ui.activity.CustomerActivity;
import ninja.cyplay.com.playretail_api.ui.activity.ProductActivity;
import ninja.cyplay.com.playretail_api.ui.custom.MessageProgressDialog;
import ninja.cyplay.com.playretail_api.ui.presenter.GetCustomerInfoPresenter;
import ninja.cyplay.com.playretail_api.ui.presenter.GetProductPresenter;
import ninja.cyplay.com.playretail_api.ui.presenter.ScannerPresenter;
import ninja.cyplay.com.playretail_api.ui.view.GetCustomerInfoView;
import ninja.cyplay.com.playretail_api.ui.view.GetProductView;
import ninja.cyplay.com.playretail_api.ui.view.ScannerView;

public class BarCodeScannerFragment extends BaseFragment implements ZXingScannerView.ResultHandler,
        ScannerView, GetCustomerInfoView, GetProductView {

    @Inject
    ScannerPresenter        scannerPresenter;

    @Inject
    GetCustomerInfoPresenter getCustomerInfoPresenter;

    @Inject
    GetProductPresenter getProductPresenter;

    private MessageProgressDialog loadingDialog;

    private ZXingScannerView mScannerView;

    private EScanFilters scanFilter = EScanFilters.SCAN_ALL;

    // -------------------------------------------------------------------------------------------
    //                                      Lifecycle
    // -------------------------------------------------------------------------------------------

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getActivity().getIntent();
        if (i.hasExtra(Constants.EXTRA_SCAN_FILTER))
            scanFilter = (EScanFilters) i.getSerializableExtra(Constants.EXTRA_SCAN_FILTER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_bar_code_scanner, container, false);
        mScannerView = new ZXingScannerView(this.getActivity());   // Programmatically initialize the scanner view
        //setContentView(mScannerView);
        List<BarcodeFormat> formats = new ArrayList<>();
        formats.add(BarcodeFormat.EAN_13);
        formats.add(BarcodeFormat.EAN_8);
        mScannerView.setFormats(formats);
        return mScannerView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        scannerPresenter.initialize();
        getCustomerInfoPresenter.initialize();
        getProductPresenter.initialize();

        scannerPresenter.setView(this);
        getCustomerInfoPresenter.setView(this);
        getProductPresenter.setView(this);

        initLoadingDialog();
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        Log.d(LogUtils.generateTag(this), rawResult.getText()); // Prints scan results
        Log.d(LogUtils.generateTag(this), rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)
        scannerPresenter.checkScanCode(rawResult.getText(), scanFilter);
//        barCodeScannerPresenter.onCodeScanned(rawResult);
    }

    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    private void initLoadingDialog() {
        // initialize
        loadingDialog = new MessageProgressDialog(getActivity());
    }

    // -------------------------------------------------------------------------------------------
    //                                      View Impl 1
    // -------------------------------------------------------------------------------------------

    @Override
    public void showLoading() {
        if (loadingDialog != null)
            loadingDialog.show();
    }

    @Override
    public void hideLoading() {
        if (loadingDialog != null)
            loadingDialog.dismiss();
    }

    @Override
    public void startScanningWindow() { }

    @Override
    public void displayPopUp(String message) {
        DialogUtils.showDialog(getActivity(), getString(R.string.warning), message);
    }

    public void showNewCustomerWindow() {
        // Show the popup
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.add_newcustomer_popup_title)
                .setMessage(R.string.add_newcustomer_popup_text)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity().getBaseContext(), "new customer popup", Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }

    @Override
    public void onGetCorrespondanceSuccess(String code, int eanc) {

        switch (scanFilter) {

            case SCAN_ALL:
                if (eanc == 1) {
                    // client
                    getCustomerInfoPresenter.getCustomerInfoFromEAN(code);
                } else if (eanc == 2) {
                    // product or other
                    getProductPresenter.getProductInfoFromSkid(code);
                    //searchProduct(callback);
                } else if (eanc == 3) {
                    // non existing code (maybe a new customer)
                    //TODO do the right action here
                    showNewCustomerWindow();
                }
                break;

            case SCAN_PROD:

                if (eanc == 1) {
                    // client
                    onScanError();
                } else if (eanc == 2) {
                    // product or other
                    getProductPresenter.getProductInfoFromSkid(code);
                    //searchProduct(callback);
                } else if (eanc == 3) {
                    // non existing code (maybe a new customer)
                    onScanError();
                }
                break;

            case SCAN_CUSTOMER:

                if (eanc == 1) {
                    // client
                    getCustomerInfoPresenter.getCustomerInfoFromEAN(code);
                } else if (eanc == 2) {
                    // product or other
                    onScanError();
                    //searchProduct(callback);
                } else if (eanc == 3) {
                    // non existing code (maybe a new customer)
                    //TODO do the right action here
                    showNewCustomerWindow();
                }
                break;

        }
    }


    // -------------------------------------------------------------------------------------------
    //                                      View Impl 2
    // -------------------------------------------------------------------------------------------

    @Override
    public void onCustomerInfoSuccess(String cid, CustomerDetails PRCustomerDetails, Recommendation PRRecommendation, List<Offer> offers, List<Product> wishlist) {
        if (getActivity().getIntent() != null && getActivity().getIntent().hasExtra(Constants.EXTRA_SEARCH_CUSTOMER_CONTEXT)) {
            Intent returnIntent = new Intent();
            getActivity().setResult(Activity.RESULT_OK, returnIntent);
            getActivity().finish();
        }
        else {
            Intent intent = new Intent(getActivity(), CustomerActivity.class);
            intent.putExtra(Constants.EXTRA_CUSTOMER_PREVIEW, Parcels.wrap(new CustomerPreview(cid, PRCustomerDetails)));
            startActivity(intent);
            getActivity().finish();
        }
    }

    @Override
    public void onProductSuccess(Product PRProduct) {
        Intent intent = new Intent(getActivity(), ProductActivity.class);
        intent.putExtra(Constants.EXTRA_PRODUCT, Parcels.wrap(PRProduct));
        startActivity(intent);
        getActivity().finish();
    }


    @Override
    public void onError() {
        String msg = getActivity().getBaseContext().getString(R.string.reload_error_msg);
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
        mScannerView.startCamera();          // Start camera on resume
    }

    public void onScanError() {

        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.barcode_notfound_popup_title)
                .setMessage(R.string.barcode_notfound_popup_text)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }

}