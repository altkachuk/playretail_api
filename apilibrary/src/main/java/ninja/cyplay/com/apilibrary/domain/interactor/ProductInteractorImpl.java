package ninja.cyplay.com.apilibrary.domain.interactor;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonArray;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ninja.cyplay.com.apilibrary.domain.executor.InteractorExecutor;
import ninja.cyplay.com.apilibrary.domain.executor.MainThreadExecutor;
import ninja.cyplay.com.apilibrary.domain.interactor.callback.FilteredPaginatedResourceRequestCallback;
import ninja.cyplay.com.apilibrary.domain.interactor.callback.PaginatedResourceRequestCallback;
import ninja.cyplay.com.apilibrary.domain.interactor.callback.ResourceRequestCallback;
import ninja.cyplay.com.apilibrary.domain.repository.PlayRetailRepository;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.exception.BaseException;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.exception.ExceptionType;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.requests.ResourceRequest;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.responses.FilteredPaginatedResourcesResponse;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.responses.PaginatedResourcesResponse;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.responses.ResourceResponse;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AProduct;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AProductFilter;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AProductSuggestion;
import ninja.cyplay.com.apilibrary.models.filters.ResourceFilter;
import ninja.cyplay.com.apilibrary.models.filters.ResourceFilterValue;
import ninja.cyplay.com.apilibrary.models.meta.ResourceFieldSorting;
import ninja.cyplay.com.apilibrary.utils.LogUtils;

/**
 * Created by damien on 22/05/15.
 */
public class ProductInteractorImpl extends AbstractInteractor implements ProductInteractor {

    private PlayRetailRepository repository;
    private Context context;

    public ProductInteractorImpl(InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor, PlayRetailRepository repository, Context context) {
        super(interactorExecutor, mainThreadExecutor);
        this.repository = repository;
        this.context = context;
    }


    // -------------------------------------------------------------------------------------------
    //
    // -------------------------------------------------------------------------------------------

    @Override
    public void suggestProducts(String cname, int productsSuggestionLimit, ResourceRequestCallback<PR_AProductSuggestion> callback) {
        final WeakReference<ResourceRequest> productSuggestionResourceRequest = new WeakReference<>(new ResourceRequest().search(cname).filter("products_suggestion_limit", String.valueOf(productsSuggestionLimit)));
        final WeakReference<ResourceRequestCallback<PR_AProductSuggestion>> productSuggestionResourceRequestCallback = new WeakReference<>(callback);
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                ProductInteractorImpl.super.doGetResource(productSuggestionResourceRequest.get(),
                        productSuggestionResourceRequestCallback.get(),
                        new ResourceGetter() {
                            @Override
                            public ResourceResponse getResource(ResourceRequest resourceRequest) {
                                return repository.suggestProducts(productSuggestionResourceRequest.get());
                            }
                        });


            }
        });
    }

    // -------------------------------------------------------------------------------------------
    //
    // -------------------------------------------------------------------------------------------


    @Override
    public void searchByName(String cname,
                             int offset,
                             int limit,
                             List<String> projection,
                             ResourceFieldSorting resourceFieldSorting,
                             List<ResourceFilter<ResourceFilter, ResourceFilterValue>> resourceFilters,
                             FilteredPaginatedResourceRequestCallback<PR_AProduct, PR_AProductFilter> callback) {
        final WeakReference<ResourceRequest> productSearchRequest = new WeakReference<ResourceRequest>(new ResourceRequest().
                search(cname).
                paginate(offset, limit).
                project(projection));
        filterResource(productSearchRequest.get(), resourceFilters);
        if (resourceFieldSorting != null) {
            productSearchRequest.get().sort(resourceFieldSorting.getSortingRequest());
        }


        final WeakReference<FilteredPaginatedResourceRequestCallback<PR_AProduct, PR_AProductFilter>> searchFromNameCallback = new WeakReference<>(callback);
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                ProductInteractorImpl.super.doGetFilteredPaginatedResource(productSearchRequest.get(),
                        searchFromNameCallback.get(),
                        new FilteredPaginatedResourceGetter() {
                            @Override
                            public FilteredPaginatedResourcesResponse getFilteredPaginatedResource(ResourceRequest resourceRequest) {
                                return repository.productSearch(productSearchRequest.get());
                            }
                        });


            }
        });
    }

    // -------------------------------------------------------------------------------------------
    //
    // -------------------------------------------------------------------------------------------


    public void getProductsForFamily(String familyId,
                                     int offset,
                                     int limit,
                                     List<String> projection,
                                     ResourceFieldSorting resourceFieldSortings,
                                     List<ResourceFilter<ResourceFilter, ResourceFilterValue>> resourceFilters,
                                     final FilteredPaginatedResourceRequestCallback<PR_AProduct, PR_AProductFilter> callback) {
        final WeakReference<ResourceRequest> getProductsByCategoryRequest = new WeakReference<>(new ResourceRequest()
                .filter("family_ids", familyId)
                .filter("include_sub_families", "True")
                .project(projection)
                .paginate(offset, limit));
        filterResource(getProductsByCategoryRequest.get(), resourceFilters);
        if (resourceFieldSortings != null) {
            getProductsByCategoryRequest.get().sort(resourceFieldSortings.getSortingRequest());
        }
        final WeakReference<FilteredPaginatedResourceRequestCallback<PR_AProduct, PR_AProductFilter>> getProductFromCategoryCallback = new WeakReference<FilteredPaginatedResourceRequestCallback<PR_AProduct, PR_AProductFilter>>(callback);
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                ProductInteractorImpl.this.doGetFilteredPaginatedResource(
                        getProductsByCategoryRequest.get(),
                        getProductFromCategoryCallback.get(),
                        new FilteredPaginatedResourceGetter() {
                            @Override
                            public FilteredPaginatedResourcesResponse getFilteredPaginatedResource(ResourceRequest resourceRequest) {
                                return repository.getProducts(getProductsByCategoryRequest.get());
                            }
                        }
                );
            }
        });
    }


    // -------------------------------------------------------------------------------------------
    //
    // -------------------------------------------------------------------------------------------

    @Override
    public void getProductsFromIds(List<String> productIds, List<String> projection, FilteredPaginatedResourceRequestCallback<PR_AProduct, PR_AProductFilter> callback) {
        final WeakReference<ResourceRequest> getProductsByIdRequest = new WeakReference<ResourceRequest>(new ResourceRequest().filter("id", productIds).paginate(false).project(projection));
        final WeakReference<FilteredPaginatedResourceRequestCallback<PR_AProduct, PR_AProductFilter>> getProductFromIdsCallback = new WeakReference<>(callback);
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {

                ProductInteractorImpl.super.doGetFilteredPaginatedResource(getProductsByIdRequest.get(),
                        getProductFromIdsCallback.get(),
                        new FilteredPaginatedResourceGetter() {
                            @Override
                            public FilteredPaginatedResourcesResponse getFilteredPaginatedResource(ResourceRequest resourceRequest) {
                                return repository.getProductsByIds(getProductsByIdRequest.get());
                            }
                        });

            }
        });
    }


    // -------------------------------------------------------------------------------------------
    //
    // -------------------------------------------------------------------------------------------


    @Override
    public void getProductsFromSkuIds(List<String> skuIds, List<String> projection, final FilteredPaginatedResourceRequestCallback<PR_AProduct, PR_AProductFilter> callback) {
        final WeakReference<ResourceRequest> getProductsFromSkuIdsRequest = new WeakReference<>(new ResourceRequest().filter("skus__id", skuIds).paginate(false).project(projection));
        final WeakReference<FilteredPaginatedResourceRequestCallback<PR_AProduct, PR_AProductFilter>> getProductFromSkuIdsCallback = new WeakReference<>(callback);
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                ProductInteractorImpl.super.doGetFilteredPaginatedResource(getProductsFromSkuIdsRequest.get(),
                        getProductFromSkuIdsCallback.get(),
                        new FilteredPaginatedResourceGetter() {
                            @Override
                            public FilteredPaginatedResourcesResponse getFilteredPaginatedResource(ResourceRequest resourceRequest) {
                                return repository.getProductsFromSkuIds(getProductsFromSkuIdsRequest.get());
                            }
                        });


            }
        });
    }

    // -------------------------------------------------------------------------------------------
    //
    // -------------------------------------------------------------------------------------------


    @Override
    public void getProductsForBrand(String brand,
                                    int offset,
                                    int limit,
                                    List<String> projection,
                                    ResourceFieldSorting resourceFieldSortings,
                                    List<ResourceFilter<ResourceFilter, ResourceFilterValue>> resourceFilters,
                                    final FilteredPaginatedResourceRequestCallback<PR_AProduct, PR_AProductFilter> callback) {
        final WeakReference<ResourceRequest> getProductsByBrandRequest = new WeakReference<>(new ResourceRequest().filter("brand", brand).project(projection).paginate(offset, limit));
        if (resourceFieldSortings != null) {
            getProductsByBrandRequest.get().sort(resourceFieldSortings.getSortingRequest());
        }
        filterResource(getProductsByBrandRequest.get(), resourceFilters);
        final WeakReference<FilteredPaginatedResourceRequestCallback<PR_AProduct, PR_AProductFilter>> getProductFromBrandCallback = new WeakReference<>(callback);
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                ProductInteractorImpl.this.doGetFilteredPaginatedResource(
                        getProductsByBrandRequest.get(),
                        getProductFromBrandCallback.get(),
                        new FilteredPaginatedResourceGetter() {
                            @Override
                            public FilteredPaginatedResourcesResponse getFilteredPaginatedResource(ResourceRequest resourceRequest) {
                                return repository.getProducts(getProductsByBrandRequest.get());
                            }
                        }
                );
            }
        });
    }

    @Override
    public void getProductsForAssortment(String assortment,
                                         List<String> projection,
                                         ResourceFieldSorting resourceFieldSortings,
                                         final FilteredPaginatedResourceRequestCallback<PR_AProduct, PR_AProductFilter> callback) {
        final WeakReference<ResourceRequest> getProductsByBrandRequest = new WeakReference<>(new ResourceRequest().filter("assortment", assortment).project(projection).paginate(false));
        if (resourceFieldSortings != null) {
            getProductsByBrandRequest.get().sort(resourceFieldSortings.getSortingRequest());
        }
        final WeakReference<FilteredPaginatedResourceRequestCallback<PR_AProduct, PR_AProductFilter>> getProductFromBrandCallback = new WeakReference<>(callback);
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                ProductInteractorImpl.this.doGetFilteredPaginatedResource(
                        getProductsByBrandRequest.get(),
                        getProductFromBrandCallback.get(),
                        new FilteredPaginatedResourceGetter() {
                            @Override
                            public FilteredPaginatedResourcesResponse getFilteredPaginatedResource(ResourceRequest resourceRequest) {
                                return repository.getProducts(getProductsByBrandRequest.get());
                            }
                        }
                );
            }
        });
    }


    // -------------------------------------------------------------------------------------------
    //
    // -------------------------------------------------------------------------------------------


    @Override
    public void getProductFromBarCode(String ean, ResourceRequestCallback<PR_AProduct> callback) {
        List<String> projection = new ArrayList<>();
        projection.add("id");
        projection.add("name");
        final WeakReference<ResourceRequest> getProducFromBarCodeRequest = new WeakReference<>(new ResourceRequest().filter("skus__ean", ean).project(projection));
        final ResourceRequestCallback<PR_AProduct> getProductFromBarCodeCallback = callback;
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                try {
                    final PaginatedResourcesResponse<PR_AProduct> getProductResponse = repository.getProducts(getProducFromBarCodeRequest.get());
                    getMainThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (getProductResponse.getResults() != null && getProductResponse.getResults().size() > 0) {
                                if (getProductFromBarCodeCallback != null) {
                                    getProductFromBarCodeCallback.onSuccess(getProductResponse.getResults().get(0));
                                } else {
                                    Log.i(ProductInteractorImpl.class.getName(), "Request callback is None");
                                }
                            } else {
                                BaseException baseException = new BaseException();
                                baseException.setType(ExceptionType.BUSINESS);
                                if (getProductFromBarCodeCallback != null) {
                                    getProductFromBarCodeCallback.onError(baseException);
                                } else {
                                    Log.i(ProductInteractorImpl.class.getName(), "Request callback is None");
                                }
                            }
                        }
                    });
                } catch (final BaseException e) {
                    Log.e(LogUtils.generateTag(this), "Error on GetProducts interactor");
                    getMainThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (getProductFromBarCodeCallback != null) {
                                getProductFromBarCodeCallback.onError(e);
                            } else {
                                Log.i(ProductInteractorImpl.class.getName(), "Request callback is None");
                            }
                        }
                    });
                }
            }
        });
    }

    // -------------------------------------------------------------------------------------------
    //
    // -------------------------------------------------------------------------------------------

    @Override
    public void getProduct(String id, List<String> project, final ResourceRequestCallback<PR_AProduct> callback) {
        final WeakReference<ResourceRequest> getProductRequest = new WeakReference<>(new ResourceRequest().id(id).project(project));
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                ProductInteractorImpl.this.doGetResource(
                        getProductRequest.get(),
                        callback,
                        new ResourceGetter() {
                            @Override
                            public ResourceResponse getResource(ResourceRequest resourceRequest) {
                                return repository.getProduct(getProductRequest.get());
                            }
                        });
            }
        });
    }


    // -------------------------------------------------------------------------------------------
    //
    // -------------------------------------------------------------------------------------------

    @Override
    public void getProductSku(String productId, String skuId, List<String> project, final ResourceRequestCallback<PR_AProduct> callback) {
        final WeakReference<ResourceRequest> getProductSkuRequest = new WeakReference<>(new ResourceRequest().id(productId).project(project).filter("skus__id", skuId));
        final WeakReference<ResourceRequestCallback<PR_AProduct>> getProductSkuCallback = new WeakReference<>(callback);
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                ProductInteractorImpl.this.doGetResource(
                        getProductSkuRequest.get(),
                        getProductSkuCallback.get(),
                        new ResourceGetter() {
                            @Override
                            public ResourceResponse getResource(ResourceRequest resourceRequest) {
                                return repository.getProduct(getProductSkuRequest.get());
                            }
                        });
            }
        });
    }


    // -------------------------------------------------------------------------------------------
    //
    // -------------------------------------------------------------------------------------------


    private void filterResource(ResourceRequest resourceRequest, List<ResourceFilter<ResourceFilter, ResourceFilterValue>> resourceFilters) {
        if (resourceFilters != null) {
            for (ResourceFilter<ResourceFilter, ResourceFilterValue> resourceFilter : resourceFilters) {
                int level = resourceFilter.getLevel();

                if (level == ResourceFilter.TYPE_LEVEL_ATTRIBUTE) {
                    if (resourceFilter.getValues().size() > 1) {
                        List<String> values = new ArrayList<>();
                        for (ResourceFilterValue resourceFilterValue : resourceFilter.getValues()) {
                            values.add(resourceFilterValue.getLabel());
                        }
                        resourceRequest.filter(resourceFilter.getKey(), values);
                    } else {
                        for (ResourceFilterValue resourceFilterValue : resourceFilter.getValues()) {
                            resourceRequest.filter(resourceFilter.getKey(), resourceFilterValue.getLabel());
                        }
                    }

                } else if (level == ResourceFilter.TYPE_LEVEL_DICT_VALUE) {
                    for (ResourceFilterValue resourceFilterValue : resourceFilter.getValues()) {

                        JsonArray jsonArray = new JsonArray();

                        String resourceFilterKey = resourceFilter.getKey();
                        String resourceFilterValueKey = resourceFilterValue.getKey();

                        if (resourceFilterKey != null && resourceFilterValueKey != null) {
                            String param = resourceFilterKey.substring(0, resourceFilterKey.indexOf(ResourceFilter.TYPE_LEVEL_DICT_SPEC_SUFFIX));
                            jsonArray.add(resourceFilter.getLabel(context));
                            jsonArray.add(resourceFilterValue.getLabel());
                            resourceRequest.filter(param, Arrays.asList(new String[]{jsonArray.toString()}));
                        }
                    }
                }

            }
        }

    }

}
