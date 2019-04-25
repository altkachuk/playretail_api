package ninja.cyplay.com.playretail_api.ui.view;

import java.util.List;

import ninja.cyplay.com.playretail_api.model.business.Filter;
import ninja.cyplay.com.playretail_api.model.business.Category;
import ninja.cyplay.com.playretail_api.model.business.Product;

/**
 * Created by damien on 04/05/15.
 */
public interface CatalogueView extends View {

    void showLoading();

    void hideLoading();

    void onProductsSuccesss(Category PRCategory, List<Product> PRProducts, List<Filter> filtres, List<Product> topPRProducts);

    void onCategoriesSuccess(Category PRCategory, List<Category> categories, List<Filter> filtres, List<Product> topPRProducts);

}
