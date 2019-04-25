package ninja.cyplay.com.playretail_api.ui.presenter;

import android.content.Context;

import java.util.List;

import javax.inject.Inject;

import ninja.cyplay.com.apilibrary.domain.interactor.CatalogueInteractor;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.exception.BaseException;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ACategory;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AFilter;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AFilterCheck;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AProduct;
import ninja.cyplay.com.playretail_api.model.business.Filter;
import ninja.cyplay.com.playretail_api.model.business.Category;
import ninja.cyplay.com.playretail_api.model.business.FilterCheck;
import ninja.cyplay.com.playretail_api.model.business.Product;
import ninja.cyplay.com.apilibrary.models.singleton.APIValue;
import ninja.cyplay.com.playretail_api.app.BasePresenter;
import ninja.cyplay.com.playretail_api.ui.view.CatalogueView;

/**
 * Created by damien on 04/05/15.
 */

public class CataloguePresenterImpl extends BasePresenter implements CataloguePresenter {

    @Inject
    APIValue apiValue;

    private Context context;
    private CatalogueView       catalogueView;
    private CatalogueInteractor catalogueInteractor;

    public CataloguePresenterImpl(Context context, CatalogueInteractor catalogueInteractor) {
        super(context);
        this.context = context;
        this.catalogueInteractor = catalogueInteractor;
    }

    @Override
    public void searchCatalogue(String level, final String label) {
        catalogueView.showLoading();
        catalogueInteractor.getCatalogueLevel(apiValue.getSid(), apiValue.getDeviceId(), level, new CatalogueInteractor.GetCatalogueLevelCallback() {

            @Override
            public void onSuccess(PR_ACategory prACategory,
                                  List<PR_ACategory> categories,
                                  List<PR_AProduct> topPRProducts,
                                  List<PR_AProduct> prAProducts,
                                  List<PR_AFilter> prAFilters) {
                catalogueView.hideLoading();
                if (prACategory != null) {
                    ((Category)prACategory).setCat_lab(label);
                    getCatalogLevelSuccess(prACategory, categories, topPRProducts, prAProducts, prAFilters);
                }
            }

            @Override
            public void onError(BaseException e) {
                catalogueView.hideLoading();

                switch (e.getType()) {

                    case BUSINESS:
                    if (e.getResponse() != null && e.getResponse().getStatus() != null)
                        catalogueView.displaySttPopUp(e.getResponse().getDetail(),e.getResponse().getStatus());
                    break;

                    case TECHNICAL:
                        catalogueView.onError();
                        break;
                }
            }
        });
    }

    @Override
    public void filterProducts(String level, List<FilterCheck> filters) {
        catalogueView.showLoading();
        catalogueInteractor.getCatalogueLevel(apiValue.getSid(), apiValue.getDeviceId(), level, (List<PR_AFilterCheck>)(List<?>)filters, new CatalogueInteractor.GetCatalogueLevelCallback() {

            @Override
            public void  onSuccess(PR_ACategory prACategory,
                                   List<PR_ACategory> categories,
                                   List<PR_AProduct> topPRProducts,
                                   List<PR_AProduct> prAProducts,
                                   List<PR_AFilter> prAFilters) {
                catalogueView.hideLoading();
                getCatalogLevelSuccess(prACategory, categories, topPRProducts, prAProducts, prAFilters);
            }

            @Override
            public void onError(BaseException e) {
                catalogueView.hideLoading();

                switch (e.getType()){
                    case BUSINESS:
                        if (e.getResponse() != null && e.getResponse().getStatus() != null)
                            catalogueView.displaySttPopUp(e.getResponse().getDetail(),e.getResponse().getStatus());
                        break;

                    case TECHNICAL:
                        catalogueView.onError();
                        break;
                }
            }
        });
    }

    private void getCatalogLevelSuccess(PR_ACategory prACategory,
                                        List<PR_ACategory> categories,
                                        List<PR_AProduct> topPRProducts,
                                        List<PR_AProduct> prAProducts,
                                        List<PR_AFilter> prAFilters) {
        if (prACategory != null) {
            if (((Category)prACategory).getCat_type() != null && ((Category)prACategory).getCat_type().equals("0"))
                onCategories(((Category)prACategory),
                        (List<Category>)(List<?>)categories,
                        (List<Filter>)(List<?>)prAFilters,
                        (List<Product>)(List<?>)topPRProducts);
            else
                onProducts(((Category)prACategory),
                        (List<Product>)(List<?>)prAProducts,
                        (List<Filter>)(List<?>)prAFilters,
                        (List<Product>)(List<?>)topPRProducts);
        }

    }

    @Override
    public void onCategories(Category PRCategory, List<Category> categories,List<Filter> filtres, List<Product> topPRProducts) {
        catalogueView.onCategoriesSuccess(PRCategory, categories, filtres, topPRProducts);
    }

    @Override
    public void onProducts(Category PRCategory, List<Product> PRProducts, List<Filter> filtres, List<Product> topPRProducts) {
        catalogueView.onProductsSuccesss(PRCategory, PRProducts, filtres, topPRProducts);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void setView(CatalogueView view) {
        catalogueView = view;
    }

    @Override
    public void onViewCreate() {

    }

    @Override
    public void onViewResume() {

    }

    @Override
    public void onViewDestroy() {

    }

}
