package ninja.cyplay.com.playretail_api.ui.view;

import ninja.cyplay.com.playretail_api.model.business.Product;

/**
 * Created by damien on 06/05/15.
 */
public interface GetProductView extends View {

    void showLoading();

    void hideLoading();

    void onProductSuccess(Product PRProduct);


}
