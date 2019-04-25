package ninja.cyplay.com.playretail_api.ui.presenter;

import ninja.cyplay.com.playretail_api.ui.view.ProductSearchView;

/**
 * Created by damien on 05/05/15.
 */
public interface ProductSearchPresenter extends Presenter<ProductSearchView> {

    public void searchProduct(String productName);

}
