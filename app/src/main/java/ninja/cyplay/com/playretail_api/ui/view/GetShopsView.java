package ninja.cyplay.com.playretail_api.ui.view;

import java.util.List;

import ninja.cyplay.com.playretail_api.model.business.Shop;

/**
 * Created by damien on 29/04/15.
 */
public interface GetShopsView extends View {

    void showLoading();

    void hideLoading();

    void onShopsSuccess(List<Shop> PRShops);

    void onShopsError();

}