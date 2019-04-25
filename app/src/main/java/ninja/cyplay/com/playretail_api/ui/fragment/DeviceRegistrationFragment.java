package ninja.cyplay.com.playretail_api.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Optional;
import ninja.cyplay.com.playretail_api.model.business.Shop;
import ninja.cyplay.com.apilibrary.utils.DialogUtils;
import ninja.cyplay.com.apilibrary.utils.LogUtils;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.app.BaseFragment;
import ninja.cyplay.com.playretail_api.ui.activity.SplashScreenAcivity;
import ninja.cyplay.com.playretail_api.ui.presenter.GetShopsPresenter;
import ninja.cyplay.com.playretail_api.ui.presenter.ProvisionDevicePresenter;
import ninja.cyplay.com.playretail_api.ui.view.GetShopsView;
import ninja.cyplay.com.playretail_api.ui.view.ProvisionDeviceView;

/**
 * Created by damien on 29/04/15.
 */
public class DeviceRegistrationFragment extends BaseFragment implements GetShopsView, ProvisionDeviceView {

    @Inject
    GetShopsPresenter getShopsPresenter;

    @Inject
    ProvisionDevicePresenter provisionDevicePresenter;

    @InjectView(R.id.config_countries_spinner)
    Spinner ctrys_spinner;

    @InjectView(R.id.config_shops_spinner)
    Spinner shops_spinner;

    @Optional
    @InjectView(R.id.progress_bar)
    ProgressBar progressBar;

    @Optional
    @InjectView(R.id.page_content)
    View pageContent;

    private List<Shop> PRShops;

    private LinkedHashMap<Long, String> filtered_shops;

    private final static String PARCEL_SHOPS = "PARCEL_SHOPS";

    // -------------------------------------------------------------------------------------------
    //                                      Lifecycle
    // -------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_device_registration, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getShopsPresenter.setView(this);
        provisionDevicePresenter.setView(this);
        provisionDevicePresenter.initialize();
        pageContent.setVisibility(View.GONE);
        if (savedInstanceState == null && filtered_shops == null) {
            Log.i(LogUtils.generateTag(this), "First time running");
            getShopsPresenter.getShops();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Parcelable parcelable = Parcels.wrap(PRShops);
        outState.putParcelable(PARCEL_SHOPS, parcelable);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.containsKey(PARCEL_SHOPS)) {
            Log.i(LogUtils.generateTag(this), "onViewStateRestored");
            //Get parcelable from bundle
            Parcelable parcelable = savedInstanceState.getParcelable(PARCEL_SHOPS);
            PRShops = Parcels.unwrap(parcelable);
            fillCountriesSpinner();
        }
    }

    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    public void fillCountriesSpinner() {
        if (PRShops != null) {
            List<String> countries_names = new ArrayList<>();
            for (Shop PRShop : PRShops) {
                countries_names.add("" + PRShop.getCountry());
                ctrys_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                        String selected = arg0.getItemAtPosition(arg2).toString();
                        fillShopsSpinner(PRShops, selected);
                    }

                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                });
            }
            ctrys_spinner.setAdapter(new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_spinner_dropdown_item,
                    countries_names));
        }
    }

    public void fillShopsSpinner(List<Shop> PRShops, String country) {

        filtered_shops = new LinkedHashMap<>();
        for (Shop PRShop : PRShops) {
            if (PRShop.getCountry() != null && PRShop.getCountry().equals(country)) {
                filtered_shops.put(Long.parseLong(PRShop.getStid()), PRShop.getName());
            }
        }
        shops_spinner.setAdapter(new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                new ArrayList<>(filtered_shops.values())));
    }

    public Long getShopBySpinnerIndex(LinkedHashMap<Long, String> hMap, int index) {
        if (hMap != null && hMap.keySet() != null && index < hMap.keySet().size())
            return (Long) hMap.keySet().toArray()[index];
        return 0L;
    }

    private void startActivityFromClass(Class activityClass) {
        Intent intent = new Intent(getActivity(), activityClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        getActivity().finish();
    }

    // -------------------------------------------------------------------------------------------
    //                                      View Impl
    // -------------------------------------------------------------------------------------------

    @Override
    public void showLoading() {
        if (progressBar != null)
            progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        if (progressBar != null)
            progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onShopsSuccess(List<Shop> PRShops) {
        showLoading();
        this.PRShops = PRShops;
        fillCountriesSpinner();
        pageContent.setVisibility(View.VISIBLE);
        hideLoading();
    }

    @Override
    public void onShopsError() {

    }

    @Override
    public void onProvisionDeviceSuccess() {
        startActivityFromClass(SplashScreenAcivity.class);
    }

    @Override
    public void onProvisionDeviceError() {

    }

    @Override
    public void displayPopUp(String message) {
        DialogUtils.showDialog(getActivity(), getString(R.string.warning), message);
    }

    // -------------------------------------------------------------------------------------------
    //                                      Action(s)
    // -------------------------------------------------------------------------------------------

    @OnClick(R.id.attach_button)
    public void attachDevice(View view) {
        Long shop_id = getShopBySpinnerIndex(filtered_shops, (int) shops_spinner.getSelectedItemId());
        provisionDevicePresenter.provisionDevice(shop_id);
    }

}
