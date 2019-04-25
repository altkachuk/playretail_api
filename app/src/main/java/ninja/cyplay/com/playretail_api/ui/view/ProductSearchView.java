package ninja.cyplay.com.playretail_api.ui.view;

import java.util.List;

import ninja.cyplay.com.playretail_api.model.business.Product;

/**
 * Created by damien on 05/05/15.
 */
public interface ProductSearchView extends View {

    void showLoading();

    void hideLoading();

    void onSearchSuccess(List<Product> PRProducts);

}