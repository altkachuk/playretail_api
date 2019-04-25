package ninja.cyplay.com.playretail_api.ui.presenter;

import java.util.List;

import ninja.cyplay.com.playretail_api.ui.view.ProductsRelatedView;

/**
 * Created by damien on 05/05/15.
 */
public interface GetProductsPresenter extends Presenter<ProductsRelatedView> {

    public void getRelatedProducts(List<String> rp);

}
