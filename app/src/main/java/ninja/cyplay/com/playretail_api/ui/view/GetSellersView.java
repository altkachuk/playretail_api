package ninja.cyplay.com.playretail_api.ui.view;

import java.util.List;

import ninja.cyplay.com.playretail_api.model.business.Seller;

/**
 * Created by damien on 28/04/15.
 */
public interface GetSellersView extends View {

    void showLoading();

    void hideLoading();

    void onSellersSuccess(List<Seller> sellers);

    void onSellersError();

}
