package ninja.cyplay.com.apilibrary.domain.interactor;

import java.util.List;
import java.util.Map;

import ninja.cyplay.com.apilibrary.domain.interactor.callback.FilteredPaginatedResourceRequestCallback;
import ninja.cyplay.com.apilibrary.domain.interactor.callback.PaginatedResourceRequestCallback;
import ninja.cyplay.com.apilibrary.domain.interactor.callback.ResourceRequestCallback;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AProduct;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AProductFilter;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AProductSuggestion;
import ninja.cyplay.com.apilibrary.models.filters.ResourceFilter;
import ninja.cyplay.com.apilibrary.models.filters.ResourceFilterValue;
import ninja.cyplay.com.apilibrary.models.meta.ResourceFieldSorting;

/**
 * Created by damien on 22/05/15.
 */
public interface ProductInteractor {

    void suggestProducts(String cname, int productsSuggestionLimit, final ResourceRequestCallback<PR_AProductSuggestion> callback);

    void searchByName(String cname,
                      int offset,
                      int limit,
                      List<String> projection,
                      ResourceFieldSorting resourceFieldSorting,
                      List<ResourceFilter<ResourceFilter, ResourceFilterValue>> resourceFilters,
                      FilteredPaginatedResourceRequestCallback<PR_AProduct, PR_AProductFilter> callback);

    void getProductsFromIds(List<String> rps,
                            List<String> projection,
                            final FilteredPaginatedResourceRequestCallback<PR_AProduct, PR_AProductFilter> callback);

    void getProductsFromSkuIds(List<String> skuIds,
                               List<String> projection,
                               final FilteredPaginatedResourceRequestCallback<PR_AProduct, PR_AProductFilter> callback);

    void getProductsForAssortment(String assortment,
                                         List<String> projection,
                                         ResourceFieldSorting resourceFieldSortings,

                                         final FilteredPaginatedResourceRequestCallback<PR_AProduct, PR_AProductFilter> callback) ;
    void getProductsForBrand(String brand,
                             int offset,
                             int limit,
                             List<String> projection,
                             ResourceFieldSorting resourceFieldSortings,
                             List<ResourceFilter<ResourceFilter, ResourceFilterValue>> resourceFilters,
                             final FilteredPaginatedResourceRequestCallback<PR_AProduct, PR_AProductFilter> callback);

    void getProductsForFamily(String familyId,
                              int offset,
                              int limit,
                              List<String> projection,
                              ResourceFieldSorting resourceFieldSortings,
                              List<ResourceFilter<ResourceFilter, ResourceFilterValue>> resourceFilters,
                              final FilteredPaginatedResourceRequestCallback<PR_AProduct, PR_AProductFilter> callback);

    void getProductFromBarCode(String ean, final ResourceRequestCallback<PR_AProduct> callback);

    void getProduct(String id, List<String> project, final ResourceRequestCallback<PR_AProduct> callback);

    void getProductSku(String productId, String skuId, List<String> project, final ResourceRequestCallback<PR_AProduct> callback);



}
