package ninja.cyplay.com.playretail_api.ui.presenter;

import java.util.List;

import ninja.cyplay.com.playretail_api.model.business.Category;
import ninja.cyplay.com.playretail_api.model.business.Filter;
import ninja.cyplay.com.playretail_api.model.business.FilterCheck;
import ninja.cyplay.com.playretail_api.model.business.Product;
import ninja.cyplay.com.playretail_api.ui.view.CatalogueView;

/**
 * Created by damien on 04/05/15.
 */
public interface CataloguePresenter extends Presenter<CatalogueView> {

    public void searchCatalogue(String catelogueLevel, String label);

    public void filterProducts(String catelogueLevel, List<FilterCheck> filters);

    public void onCategories(Category PRCategory, List<Category> categories, List<Filter> PRFilters, List<Product> topPRProducts);

    public void onProducts(Category PRCategory, List<Product> PRProducts, List<Filter> PRFilters, List<Product> topPRProducts);

}