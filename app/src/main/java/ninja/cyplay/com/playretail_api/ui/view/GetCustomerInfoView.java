package ninja.cyplay.com.playretail_api.ui.view;

import java.util.List;

import ninja.cyplay.com.playretail_api.model.business.CustomerDetails;
import ninja.cyplay.com.playretail_api.model.business.Offer;
import ninja.cyplay.com.playretail_api.model.business.Product;
import ninja.cyplay.com.playretail_api.model.business.Recommendation;

/**
 * Created by damien on 06/05/15.
 */
public interface GetCustomerInfoView extends View {

    void showLoading();

    void hideLoading();

    void onCustomerInfoSuccess(String cid, CustomerDetails PRCustomerDetails, Recommendation PRRecommendation, List<Offer> offers, List<Product> wishlist);
}
