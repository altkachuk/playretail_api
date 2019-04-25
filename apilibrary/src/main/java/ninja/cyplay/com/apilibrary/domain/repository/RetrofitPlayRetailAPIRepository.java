package ninja.cyplay.com.apilibrary.domain.repository;

import android.content.Context;

import com.google.gson.ExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.CipherSuite;
import com.squareup.okhttp.ConnectionSpec;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.TlsVersion;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ninja.cyplay.com.apilibrary.domain.repositoryModel.deserializer.PR_CreateUpdateDeleteResourceResponseDeserializer;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.deserializer.PR_FilteredPaginatedResourceDeserializer;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.deserializer.PR_GetShopStatisticsDeserializer;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.deserializer.PR_PaginatedResourceDeserializer;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.deserializer.PR_ResourceDeserializer;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.exception.BaseException;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.factory.ExclusionStrategyFactory;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.factory.ModelFactory;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.requests.AccessTokenRequest;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.requests.CatalogueLevelRequest;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.requests.GetConfigRequest;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.requests.GetDataOutRequest;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.requests.GetEventsRequest;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.requests.ProvisionDeviceRequestBody;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.requests.ResourceRequest;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.requests.ShopStatisticsRequest;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.responses.BaseResponse;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.responses.CreateUpdateDeleteResourceResponse;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.responses.FilteredPaginatedResourcesResponse;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.responses.PaginatedResourcesResponse;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.responses.ResourceResponse;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.responses.ShopStatisticsResponse;
import ninja.cyplay.com.apilibrary.models.APIResource;
import ninja.cyplay.com.apilibrary.models.abstractmodels.ActionEvent;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AAddress;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ABarCodeInfo;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ABasket;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ABasketComment;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ABasketItem;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ABasketOffer;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ACatalogueLevel;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AConfig;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ACustomer;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ADelivery;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ADeliveryMode;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ADevice;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ADeviceConfiguration;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AEvent;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AFee;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AOffer;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_APayment;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_APaymentShare;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_APaymentTransaction;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AProduct;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AProductFilter;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AProductReview;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AProductReviewAttribute;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AProductShare;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AProductSuggestion;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AQuotation;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AShop;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AFeedback;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ATicket;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AUser;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AWishlistItem;
import ninja.cyplay.com.apilibrary.models.abstractmodels.WSReport;
import ninja.cyplay.com.apilibrary.models.abstractmodels.v2.Contact;
import ninja.cyplay.com.apilibrary.models.abstractmodels.v2.PR_AData;
import ninja.cyplay.com.apilibrary.models.business.OAuth2Credentials;
import ninja.cyplay.com.apilibrary.models.business.PasswordReplacement;
import ninja.cyplay.com.apilibrary.models.business.reporting.ReportData;
import ninja.cyplay.com.apilibrary.models.singleton.APIValue;
import ninja.cyplay.com.apilibrary.utils.ClientUtil;
import ninja.cyplay.com.apilibrary.utils.RealmHelper;
import ninja.cyplay.com.apilibrary.utils.SafeOkHttpClientUtil;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;
import retrofit.mime.TypedByteArray;

public class RetrofitPlayRetailAPIRepository implements PlayRetailRepository {

    protected String endpoint;
    protected RequestInterceptor requestInterceptor;
    protected RetrofitPlayRetailService playRetailAPI;
    protected Context context;
    protected APIValue apiValue;

    public final static int HTTP_STATUS_UNAUTHORIZED = 401;
    public final static String GSON_BUILDER_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm";

    public RetrofitPlayRetailAPIRepository(Context context,
                                           String endpoint,
                                           RequestInterceptor requestInterceptor,
                                           APIValue apiValue) {
        this.context = context;
        this.endpoint = endpoint;
        this.requestInterceptor = requestInterceptor;
        this.apiValue = apiValue;
        init();
    }

    protected void init() {
        OkHttpClient safeOkHttpClient;
        switch (ClientUtil.getSslCertificateSignatureType()) {
            case CA:
                safeOkHttpClient = new OkHttpClient();
                //to trust all certs
                safeOkHttpClient.setSslSocketFactory(SafeOkHttpClientUtil.createAllTrustSSLSocketFactory());
                break;
            case SELF:
            default:
                safeOkHttpClient = SafeOkHttpClientUtil.getSafeOkHttpClient(context);
        }

        safeOkHttpClient.setRetryOnConnectionFailure(true);


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                    .tlsVersions(TlsVersion.TLS_1_2)
                    .cipherSuites(
                            CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                            CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                            CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256,
                            CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA,
                            CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA,
                            CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA,
                            CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA,
                            CipherSuite.TLS_ECDHE_ECDSA_WITH_RC4_128_SHA,
                            CipherSuite.TLS_ECDHE_RSA_WITH_RC4_128_SHA,
                            CipherSuite.TLS_DHE_RSA_WITH_AES_128_CBC_SHA,
                            CipherSuite.TLS_DHE_DSS_WITH_AES_128_CBC_SHA,
                            CipherSuite.TLS_DHE_RSA_WITH_AES_256_CBC_SHA)
                    .build();
            safeOkHttpClient.setConnectionSpecs(Collections.singletonList(spec));
        }

        int cacheSize = 64 * 1024 * 1024; // 64 MiB
        File cacheDirectory = new File(context.getCacheDir().getAbsolutePath(), "HttpCache");
        Cache cache = new Cache(cacheDirectory, cacheSize);
        safeOkHttpClient.setCache(cache);

        //TODO Change remove this or Define real results
        safeOkHttpClient.setConnectTimeout(60, TimeUnit.SECONDS); // connect timeout
        safeOkHttpClient.setReadTimeout(60, TimeUnit.SECONDS);    // socket timeout

        safeOkHttpClient.networkInterceptors().add(new AuthenticationInterceptor(apiValue));
        safeOkHttpClient.setAuthenticator(new TokenAuthenticator(apiValue, RetrofitPlayRetailAPIRepository.this));

        // Interceptor for reporting webservices
        safeOkHttpClient.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request request = chain.request();
                Response response = chain.proceed(request);
                String requestTime = request.header(PlayRetailRequestInterceptor.REQUEST_TIME_PARAM);
                String reportId = response.header(WSReport.REPORT_ID_KEY);
                if (requestTime != null && reportId != null) {
                    try {
                        double totalTime = System.currentTimeMillis() - Long.valueOf(requestTime);
                        RealmHelper.saveObjectToRealm(context, new ReportData(reportId,
                                totalTime / 1000));
                    } catch (NumberFormatException e) {
                    }
                }


                return response;
            }
        });

        GsonConverter gsonConverter = new GsonConverter(initGson());
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(endpoint)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(new RestAdapter.Log() {
                    @Override
                    public void log(String msg) {
                        ClientUtil.updateMessage(msg);
                        //showLogMessage(context, msg);
                    }
                })
                .setClient(new OkClient(safeOkHttpClient))
                .setConverter(gsonConverter)
                .setRequestInterceptor(requestInterceptor)
                .build();

        playRetailAPI = restAdapter.create(RetrofitPlayRetailService.class);
    }

    protected Gson initGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        // Serialize null values;
        gsonBuilder.serializeNulls();

        // Exclusion strategy
        Collection<ExclusionStrategy> exclusionStrategies = ExclusionStrategyFactory.getInstance().getExclusionStrategies();
        ExclusionStrategy[] exclusionStrategiesArray = new ExclusionStrategy[exclusionStrategies.size()];
        exclusionStrategies.toArray(exclusionStrategiesArray);
        gsonBuilder.setExclusionStrategies(exclusionStrategiesArray);

        // Register an adapter to manage the date types as long values
        gsonBuilder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return new Date(json.getAsJsonPrimitive().getAsLong());
            }
        });

        gsonBuilder.setDateFormat(GSON_BUILDER_DATE_FORMAT);

        // Register type adapter from resources
        for (final Class klass : ModelFactory.getInstance().getModelClasses()) {
            // Resource Response
            ParameterizedType resourceResponseParameterizedType = new ParameterizedType() {
                @Override
                public Type[] getActualTypeArguments() {
                    return new Type[]{klass};
                }

                @Override
                public Type getOwnerType() {
                    return null;
                }

                @Override
                public Type getRawType() {
                    return ResourceResponse.class;
                }
            };
            gsonBuilder.registerTypeAdapter(resourceResponseParameterizedType, new PR_ResourceDeserializer<>(klass));
            // Paginated Resource Response
            ParameterizedType paginatedResourceResponseParameterizedType = new ParameterizedType() {
                @Override
                public Type[] getActualTypeArguments() {
                    return new Type[]{klass};
                }

                @Override
                public Type getOwnerType() {
                    return null;
                }

                @Override
                public Type getRawType() {
                    return PaginatedResourcesResponse.class;
                }
            };
            gsonBuilder.registerTypeAdapter(paginatedResourceResponseParameterizedType,
                    new PR_PaginatedResourceDeserializer<>(klass));
            // Filtered Resource Response

            final APIResource annotation = (APIResource) klass.getAnnotation(APIResource.class);
            if (annotation != null && !APIResource.NONE.class.getName().equals(annotation.filterclass().getName())) {
                ParameterizedType filteredPaginatedResourceResponseParameterizedType = new ParameterizedType() {
                    @Override
                    public Type[] getActualTypeArguments() {
                        return new Type[]{klass, annotation.filterclass()};
                    }

                    @Override
                    public Type getOwnerType() {
                        return null;
                    }

                    @Override
                    public Type getRawType() {
                        return FilteredPaginatedResourcesResponse.class;
                    }
                };
                gsonBuilder.registerTypeAdapter(filteredPaginatedResourceResponseParameterizedType,
                        new PR_FilteredPaginatedResourceDeserializer<>(klass, annotation.filterclass()));

            }

        }


        // CREATE / UPDATE / DELETE (customer/pageViews/etc)
        Gson result = gsonBuilder.registerTypeAdapter(CreateUpdateDeleteResourceResponse.class, new PR_CreateUpdateDeleteResourceResponseDeserializer())

                // GET
                .registerTypeAdapter(new TypeToken<ResourceResponse<PR_ADeviceConfiguration>>() {
                }.getType(), new PR_ResourceDeserializer<>(PR_ADeviceConfiguration.class))
                .registerTypeAdapter(new TypeToken<PaginatedResourcesResponse<String>>() {
                }.getType(), new PR_PaginatedResourceDeserializer<>(String.class))
                .registerTypeAdapter(ShopStatisticsResponse.class, new PR_GetShopStatisticsDeserializer())
                .create();
        return result;
    }

    public interface APIResourceCall<T> {
        T callApi();
    }

    public <T extends BaseResponse> T call(APIResourceCall<T> apiResourceCall,
                                           Class<T> entityClass) throws BaseException {
        T paginatedResourcesResponse;
        try {
            paginatedResourcesResponse = apiResourceCall.callApi();
            return paginatedResourcesResponse;
        } catch (RetrofitError retrofitError) {
            throw getException(retrofitError, BaseException.class, null, entityClass);
        }
    }

    private <S extends BaseResponse, T extends BaseException> T getException(
            RetrofitError retrofitError, Class<T> exceptionType, S response, Class<S> responseType) {
        T exception = null;
        try {
            String json = null;
            // Instanciate Exception
            exception = exceptionType.newInstance();
            try {
                // Get body of the response as a String
                if (retrofitError.getResponse() != null && retrofitError.getResponse().getBody() != null)
                    json = new String(((TypedByteArray) retrofitError.getResponse().getBody()).getBytes());
                // assign Response from body if status or message
                response = new Gson().fromJson(json, responseType);
            } catch (Exception e) {
            }
            // set Stacktrace to know the error type
            exception.setStackTrace(retrofitError.getStackTrace());
            // set Response to the exception
            if (retrofitError.getResponse() != null && retrofitError.getResponse().getStatus() == HTTP_STATUS_UNAUTHORIZED) {
                exception.setAuthenticationResponse(response);
            } else if (response == null) {
                exception.setResponse(new BaseResponse(retrofitError.getKind().toString(), retrofitError.getMessage()));
            } else {
                exception.setResponse(response);
            }

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (exception.getResponse() == null)
            exception.setResponse(new BaseResponse());
        return exception;
    }

    @Override
    public ResourceResponse<PR_AConfig> getConfig(final GetConfigRequest getConfigRequest) throws BaseException {
        return this.call(new APIResourceCall<ResourceResponse>() {
                             @Override
                             public ResourceResponse callApi() {
                                 return playRetailAPI.getConfig(
                                         getConfigRequest.getUdid(),
                                         getConfigRequest.getApp_version(),
                                         String.valueOf(android.os.Build.VERSION.SDK_INT),
                                         getConfigRequest.getLanguage(),
                                         getConfigRequest.getShop_id());
                             }
                         },
                ResourceResponse.class);
    }

    @Override
    public ResourceResponse<PR_ADeviceConfiguration> getDeviceConfiguration(final ResourceRequest getSellersRequest) throws BaseException {
        return this.call(new APIResourceCall<ResourceResponse>() {
                             @Override
                             public ResourceResponse callApi() {
                                 return playRetailAPI.getDeviceConf(getSellersRequest.getId());
                             }
                         },
                ResourceResponse.class);
    }

    @Override
    public PaginatedResourcesResponse<PR_AShop> getShops(final ResourceRequest resourcesRequest) throws BaseException {
        return this.call(new APIResourceCall() {
                             @Override
                             public PaginatedResourcesResponse callApi() {
                                 return playRetailAPI.getShops(resourcesRequest.getParams());

                             }
                         },
                PaginatedResourcesResponse.class);
    }

    @Override
    public ResourceResponse<PR_ADevice> provisionDevice(final ProvisionDeviceRequestBody provisionDeviceRequest) throws BaseException {
        return this.call(new APIResourceCall<ResourceResponse>() {
                             @Override
                             public ResourceResponse callApi() {
                                 return playRetailAPI.provisionDevice(provisionDeviceRequest);
                             }
                         },
                ResourceResponse.class);
    }

    @Override
    public ResourceResponse<PR_ADevice> updateProvisionDeviceStore(final ProvisionDeviceRequestBody provisionDeviceRequest) throws BaseException {
        return this.call(new APIResourceCall<ResourceResponse>() {
                             @Override
                             public ResourceResponse callApi() {
                                 return playRetailAPI.provisionUpdateDeviceStore(provisionDeviceRequest.getId(), provisionDeviceRequest);
                             }
                         },
                ResourceResponse.class);
    }

    @Override
    public ResourceResponse<OAuth2Credentials> accessToken(final AccessTokenRequest accessTokenRequest) throws BaseException {
        return this.call(new APIResourceCall<ResourceResponse>() {
                             @Override
                             public ResourceResponse callApi() {
                                 return playRetailAPI.getAccessToken(
                                         accessTokenRequest.getClient_id(),
                                         accessTokenRequest.getClient_secret(),
                                         accessTokenRequest.getGrant_type(),
                                         accessTokenRequest.getUsername(),
                                         accessTokenRequest.getPassword(),
                                         accessTokenRequest.getScope());
                             }
                         },
                ResourceResponse.class);
    }


    @Override
    public ResourceResponse<OAuth2Credentials> refreshAccessToken(final AccessTokenRequest accessTokenRequest) throws BaseException {
        return this.call(new APIResourceCall<ResourceResponse>() {
                             @Override
                             public ResourceResponse callApi() {
                                 return playRetailAPI.refreshAccessToken(
                                         accessTokenRequest.getClient_id(),
                                         accessTokenRequest.getClient_secret(),
                                         accessTokenRequest.getGrant_type(),
                                         accessTokenRequest.getRefresh_token());
                             }
                         }, ResourceResponse.class
        );
    }

    @Override
    public ResourceResponse<PR_AUser> replaceUserPassword(final ResourceRequest<PasswordReplacement> resourceRequest) throws BaseException {
        return this.call(new APIResourceCall<ResourceResponse>() {
                             @Override
                             public ResourceResponse callApi() {
                                 return playRetailAPI.replaceUserPassword(resourceRequest.getId(), resourceRequest.getBody());
                             }
                         }, ResourceResponse.class
        );
    }

    @Override
    public ResourceResponse<PR_AUser> notifyLostUserPassword(final ResourceRequest<PasswordReplacement> resourceRequest) throws BaseException {
        return this.call(new APIResourceCall<ResourceResponse>() {
                             @Override
                             public ResourceResponse callApi() {
                                 return playRetailAPI.notifyLostUserPassword(resourceRequest.getId(), resourceRequest.getBody());
                             }
                         }, ResourceResponse.class
        );
    }


    @Override
    public ResourceResponse<Void> invalidateToken(final AccessTokenRequest accessTokenRequest) throws BaseException {
        return this.call(new APIResourceCall<ResourceResponse>() {
                             @Override
                             public ResourceResponse<Void> callApi() {
                                 return playRetailAPI.invalidateToken(
                                         accessTokenRequest.getClient_id(),
                                         accessTokenRequest.getClient_secret(),
                                         accessTokenRequest.getToken()
                                 );
                             }
                         },
                ResourceResponse.class);
    }

    @Override
    public ResourceResponse<PR_ACatalogueLevel> catalogueLevel(final CatalogueLevelRequest catalogueLevelRequest) throws BaseException {
        return this.call(new APIResourceCall<ResourceResponse>() {
                             @Override
                             public ResourceResponse callApi() {
                                 return playRetailAPI.getCatalogueLevel(catalogueLevelRequest.getCategory(), "id,name");
                             }
                         },
                ResourceResponse.class);
    }

    @Override
    public FilteredPaginatedResourcesResponse<PR_AProduct, PR_AProductFilter> getProductsByIds(final ResourceRequest getProductsByIdRequest) throws BaseException {
        return this.call(new APIResourceCall<FilteredPaginatedResourcesResponse>() {
                             @Override
                             public FilteredPaginatedResourcesResponse callApi() {
                                 return playRetailAPI.getProducts((List<String>) getProductsByIdRequest.getMultiParams().get("id"),
                                         getProductsByIdRequest.getParams(),
                                         (List<String>) getProductsByIdRequest.getMultiParams().get("skus__specs"),
                                         (List<String>) getProductsByIdRequest.getMultiParams().get("specs"));
                             }
                         },
                FilteredPaginatedResourcesResponse.class);
    }

    @Override
    public FilteredPaginatedResourcesResponse<PR_AProduct, PR_AProductFilter> getProductsFromSkuIds(final ResourceRequest getProductsByIdRequest) throws BaseException {
        return this.call(new APIResourceCall<FilteredPaginatedResourcesResponse>() {
                             @Override
                             public FilteredPaginatedResourcesResponse callApi() {
                                 return playRetailAPI.getProductsFromSkuIds((List<String>) getProductsByIdRequest.getMultiParams().get("skus__id"), getProductsByIdRequest.getParams());

                             }
                         },
                FilteredPaginatedResourcesResponse.class);
    }

    @Override
    public FilteredPaginatedResourcesResponse<PR_AProduct, PR_AProductFilter> getProducts(final ResourceRequest request) throws BaseException {
        return this.call(new APIResourceCall<FilteredPaginatedResourcesResponse>() {
                             @Override
                             public FilteredPaginatedResourcesResponse callApi() {
                                 return playRetailAPI.getProducts(request.getParams(),
                                         (List<String>) request.getMultiParams().get("availabilities__price"),
                                         (List<String>) request.getMultiParams().get("skus__specs"),
                                         (List<String>) request.getMultiParams().get("specs"));
                             }
                         },
                FilteredPaginatedResourcesResponse.class);


    }

    @Override
    public PaginatedResourcesResponse<PR_ACustomer> customerSearch(final ResourceRequest customerSearchRequest) throws BaseException {
        return this.call(new APIResourceCall<PaginatedResourcesResponse>() {
                             @Override
                             public PaginatedResourcesResponse callApi() {
                                 return playRetailAPI.customerSearch(customerSearchRequest.getParams());
                             }
                         },
                PaginatedResourcesResponse.class);


    }

    public PaginatedResourcesResponse<PR_ACustomer> getCustomers(final ResourceRequest request) throws BaseException {
        return this.call(new APIResourceCall<PaginatedResourcesResponse>() {
                             @Override
                             public PaginatedResourcesResponse callApi() {
                                 return playRetailAPI.getCustomers(request.getParams());
                             }
                         },
                PaginatedResourcesResponse.class);

    }

    @Override
    public FilteredPaginatedResourcesResponse<PR_AProduct, PR_AProductFilter> productSearch(final ResourceRequest productSearchRequest) throws BaseException {

        return this.call(new APIResourceCall<FilteredPaginatedResourcesResponse>() {
                             @Override
                             public FilteredPaginatedResourcesResponse callApi() {
                                 return playRetailAPI.productSearch(productSearchRequest.getParams(),
                                         (List<String>) productSearchRequest.getMultiParams().get("availabilities__price"),
                                         (List<String>) productSearchRequest.getMultiParams().get("skus__specs"),
                                         (List<String>) productSearchRequest.getMultiParams().get("specs"));
                             }
                         },
                FilteredPaginatedResourcesResponse.class);

    }

    @Override
    public ResourceResponse<PR_AProduct> getProduct(final ResourceRequest request) throws BaseException {
        return this.call(new APIResourceCall<ResourceResponse>() {
                             @Override
                             public ResourceResponse callApi() {
                                 return playRetailAPI.getProduct(request.getId(), request.getParams());
                             }
                         },
                ResourceResponse.class);

    }


    @Override
    public PaginatedResourcesResponse<String> customerCompletion(final ResourceRequest customerCompletionRequest) throws BaseException {

        return this.call(new APIResourceCall<PaginatedResourcesResponse>() {
                             @Override
                             public PaginatedResourcesResponse callApi() {
                                 return playRetailAPI.customerCompletion(customerCompletionRequest.getParams());
                             }
                         },
                PaginatedResourcesResponse.class);
    }

    @Override
    public ResourceResponse<PR_AProductSuggestion> suggestProducts(final ResourceRequest request) throws BaseException {
        return this.call(new APIResourceCall<ResourceResponse>() {
                             @Override
                             public ResourceResponse callApi() {
                                 return playRetailAPI.productCompletion(request.getParams());
                             }
                         },
                ResourceResponse.class);
    }

    @Override
    public ResourceResponse<PR_ACustomer> getCustomerInfo(final ResourceRequest getCustomerInfoRequest) throws BaseException {
        return this.call(new APIResourceCall<ResourceResponse>() {
                             @Override
                             public ResourceResponse callApi() {
                                 return playRetailAPI.getCustomerInfo(getCustomerInfoRequest.getId(), getCustomerInfoRequest.getParams());
                             }
                         },
                ResourceResponse.class);
    }

    @Override
    public ResourceResponse<PR_ACustomer> saveCustomerInfo(final ResourceRequest<PR_ACustomer> updateCustomerInfoRequest) throws BaseException {
        return this.call(new APIResourceCall<ResourceResponse>() {
                             @Override
                             public ResourceResponse callApi() {
                                 return playRetailAPI.saveCustomerInfo(updateCustomerInfoRequest.getId(), updateCustomerInfoRequest.getBody());
                             }
                         },
                ResourceResponse.class);
    }

    public ResourceResponse<PR_ACustomer> createCustomerInfo(final ResourceRequest<PR_ACustomer> request) throws BaseException {
        return this.call(new APIResourceCall<ResourceResponse>() {
                             @Override
                             public ResourceResponse callApi() {
                                 return playRetailAPI.createCustomerInfo(request.getBody());
                             }
                         },
                ResourceResponse.class);


    }

    @Override
    public PaginatedResourcesResponse<PR_ATicket> getSalesHistory(final ResourceRequest getSalesHistoryRequest) throws BaseException {
        return this.call(new APIResourceCall<PaginatedResourcesResponse>() {
                             @Override
                             public PaginatedResourcesResponse callApi() {
                                 return playRetailAPI.getSalesHistory(getSalesHistoryRequest.getParams());
                             }
                         },
                PaginatedResourcesResponse.class);
    }

    @Override
    public List<ActionEvent> saveActionEvents(final ResourceRequest<List<ActionEvent>> resourceRequest) throws BaseException {
        List<ActionEvent> response;
        try {
            response = playRetailAPI.saveActionEvents(resourceRequest.getBody());
            return response;
        } catch (RetrofitError retrofitError) {
            throw new BaseException();
        }
    }

    @Override
    public List<String> saveWebserviceTimes(final ResourceRequest<List<WSReport>> resourceRequest) throws BaseException {
        List<String> response;
        try {
            response = playRetailAPI.saveWebserviceTimes(resourceRequest.getBody());
            return response;
        } catch (RetrofitError retrofitError) {
            throw new BaseException();
        }
    }


    @Override
        public ResourceResponse<PR_AWishlistItem> addProductToWishlist(final ResourceRequest<PR_AWishlistItem> request) throws BaseException {
        return this.call(new APIResourceCall<ResourceResponse>() {
                             @Override
                             public ResourceResponse callApi() {
                                 return playRetailAPI.addProductToWishlist(request.getId(),
                                         request.getBody());
                             }
                         },
                ResourceResponse.class);
    }

    @Override
    public ResourceResponse<PR_AWishlistItem> deleteProductFromWishlist(final ResourceRequest<PR_AWishlistItem> request) throws BaseException {
        return this.call(new APIResourceCall<ResourceResponse>() {
                             @Override
                             public ResourceResponse callApi() {
                                 return playRetailAPI.deleteProductFromWishlist(request.getId(),
                                         request.getSecondLevelId());
                             }
                         },
                ResourceResponse.class);
    }

    public ResourceResponse<PR_ABasket> getSellerCart(final ResourceRequest request) throws BaseException {
        return this.call(new APIResourceCall<ResourceResponse>() {
                             @Override
                             public ResourceResponse callApi() {
                                 return playRetailAPI.getSellerCart(request.getId());
                             }
                         },
                ResourceResponse.class);
    }


    @Override
    public ResourceResponse<Void> getCustomerCart(final ResourceRequest request) throws BaseException {
        return this.call(new APIResourceCall<ResourceResponse>() {
                             @Override
                             public ResourceResponse callApi() {
                                 return playRetailAPI.getCustomerCart(request.getId());
                             }
                         },
                ResourceResponse.class);
    }

    @Override
    public ResourceResponse<PR_AAddress> addAddressToCustomer(final ResourceRequest<PR_AAddress> request) throws BaseException {
        return this.call(new APIResourceCall<ResourceResponse>() {
                             @Override
                             public ResourceResponse callApi() {
                                 return playRetailAPI.addAddressToCustomer(request.getId(), request.getBody());
                             }
                         },
                ResourceResponse.class);
    }

    @Override
    public ResourceResponse<PR_AAddress> updateCustomerAddress(final ResourceRequest<PR_AAddress> request) throws BaseException {
        return this.call(new APIResourceCall<ResourceResponse>() {
                             @Override
                             public ResourceResponse callApi() {
                                 return playRetailAPI.updateCustomerAddress(request.getId(), request.getSecondLevelId(), request.getBody());
                             }
                         },
                ResourceResponse.class);
    }

    @Override
    public PaginatedResourcesResponse<PR_APayment> getCustomerPayments(final ResourceRequest<PR_APayment> request) {
        return this.call(new APIResourceCall<PaginatedResourcesResponse>() {
                             @Override
                             public PaginatedResourcesResponse callApi() {
                                 return playRetailAPI.getCustomerPayments(request.getId(), request.getParams());
                             }
                         },
                PaginatedResourcesResponse.class);
    }

    @Override
    public ResourceResponse<PR_APayment> getCustomerPayment(final ResourceRequest<PR_APayment> request) {
        return this.call(new APIResourceCall<ResourceResponse>() {
                             @Override
                             public ResourceResponse callApi() {
                                 return playRetailAPI.getCustomerPayment(request.getId(), request.getSecondLevelId());
                             }
                         },
                ResourceResponse.class);
    }


    @Override
    public ResourceResponse<PR_APayment> createCustomerPayment(final ResourceRequest<PR_APayment> request) {
        return this.call(new APIResourceCall<ResourceResponse>() {
                             @Override
                             public ResourceResponse callApi() {
                                 /*if ( request.getBody() !=null){
                                     return playRetailAPI.createCustomerPayment(request.getCustomerId(), request.getBody());
                                 }else{*/
                                 return playRetailAPI.createCustomerPaymentFromBasket(request.getId(), request.getBody());
                                 //}

                             }
                         },
                ResourceResponse.class);
    }

    @Override
    public ResourceResponse<Void> validateCart(final ResourceRequest<PR_ABasketComment> request) {
        return this.call(new APIResourceCall<ResourceResponse>() {
                             @Override
                             public ResourceResponse callApi() {
                                 /*if ( request.getBody() !=null){
                                     return playRetailAPI.createCustomerPayment(request.getCustomerId(), request.getBody());
                                 }else{*/
                                 return playRetailAPI.validateCart(request.getId(), request.getBody());
                                 //}

                             }
                         },
                ResourceResponse.class);
    }

    @Override
    public ResourceResponse<PR_APayment> updateCustomerPayment(final ResourceRequest<PR_APayment> request) {
        return this.call(new APIResourceCall<ResourceResponse>() {
                             @Override
                             public ResourceResponse callApi() {
                                 return playRetailAPI.updateCustomerPayment(request.getId(), request.getSecondLevelId(), request.getBody());
                             }
                         },
                ResourceResponse.class);
    }

    @Override
    public ResourceResponse<PR_APaymentTransaction> addTransactionToCustomerPayment(final ResourceRequest<PR_APaymentTransaction> request) {
        return this.call(new APIResourceCall<ResourceResponse>() {
                             @Override
                             public ResourceResponse callApi() {
                                 return playRetailAPI.addTransactionToCustomerPayment(request.getId(), request.getSecondLevelId(), request.getBody());
                             }
                         },
                ResourceResponse.class);
    }


    @Override
    public ResourceResponse<PR_APaymentTransaction> updateCustomerPaymentTransaction(final ResourceRequest<PR_APaymentTransaction> request) {
        return this.call(new APIResourceCall<ResourceResponse>() {
                             @Override
                             public ResourceResponse callApi() {
                                 return playRetailAPI.updateCustomerPaymentTransaction(request.getId(),
                                         request.getSecondLevelId(),
                                         request.getThirdLevelId(),
                                         request.getBody());
                             }
                         },
                ResourceResponse.class);
    }


    @Override
    public ResourceResponse<PR_ABasketItem> addBasketItemToCustomerBasket(final ResourceRequest<PR_ABasketItem> request) {
        return this.call(new APIResourceCall<ResourceResponse>() {
                             @Override
                             public ResourceResponse callApi() {
                                 return playRetailAPI.addBasketItemToCustomerBasket(request.getId(),
                                         request.getBody());
                             }
                         },
                ResourceResponse.class);
    }

    @Override
    public ResourceResponse<PR_ABasketItem> updateBasketItemInCustomerBasket(final ResourceRequest<PR_ABasketItem> request) {
        return this.call(new APIResourceCall<ResourceResponse>() {
                             @Override
                             public ResourceResponse callApi() {
                                 return playRetailAPI.updateBasketItemInCustomerBasket(request.getId(),
                                         request.getSecondLevelId(),
                                         request.getBody());
                             }
                         },
                ResourceResponse.class);
    }

    @Override
    public ResourceResponse<PR_ABasketItem> removeBasketItemFromCustomerBasket(final ResourceRequest<PR_ABasketItem> request) {
        return this.call(new APIResourceCall<ResourceResponse>() {
                             @Override
                             public ResourceResponse callApi() {
                                 return playRetailAPI.removeBasketItemFromCustomerBasket(request.getId(),
                                         request.getSecondLevelId());
                             }
                         },
                ResourceResponse.class);
    }

    @Override
    public List<PR_ABasketItem> removeBasketItemsFromCustomerBasket(final ResourceRequest<PR_ABasketItem> request) {
        List<PR_ABasketItem> response;
        try {
            response = playRetailAPI.removeBasketItemsFromCustomerBasket(request.getId(), request.getMultiParams().get("id"));
            return response;
        } catch (RetrofitError retrofitError) {
            throw new BaseException();
        }
    }

    @Override
    public ResourceResponse<PR_ABasket> clearBasket(final ResourceRequest<PR_ABasket> request) {
        return this.call(new APIResourceCall<ResourceResponse>() {
                             @Override
                             public ResourceResponse callApi() {
                                 return playRetailAPI.clearBasket(request.getId());
                             }
                         },
                ResourceResponse.class);
    }

    @Override
    public ResourceResponse<PR_ABasket> updateBasketDeliveryFees(final ResourceRequest<PR_ABasket> request) {
        return this.call(new APIResourceCall<ResourceResponse>() {
                             @Override
                             public ResourceResponse callApi() {
                                 return playRetailAPI.updateBasketDeliveryFees(request.getId(), request.getBody());
                             }
                         },
                ResourceResponse.class);
    }

    @Override
    public ResourceResponse<PR_ABasketOffer> activeCustomerOffer(final ResourceRequest<PR_ABasketOffer> request) throws BaseException {
        return this.call(new APIResourceCall<ResourceResponse>() {
                             @Override
                             public ResourceResponse callApi() {
                                 return playRetailAPI.activeCustomerOffer(request.getId(), request.getBody());
                             }
                         },
                ResourceResponse.class);
    }

    @Override
    public ResourceResponse<PR_ABasketOffer> inActiveCustomerOffer(final ResourceRequest request) throws BaseException {
        return this.call(new APIResourceCall<ResourceResponse>() {
                             @Override
                             public ResourceResponse callApi() {
                                 return playRetailAPI.inActiveCustomerOffer(request.getId(), request.getSecondLevelId());
                             }
                         },
                ResourceResponse.class);
    }

    @Override
    public ResourceResponse<PR_ABasketItem> addBasketItemToSellerBasket(final ResourceRequest<PR_ABasketItem> request) {
        return this.call(new APIResourceCall<ResourceResponse>() {
                             @Override
                             public ResourceResponse callApi() {
                                 return playRetailAPI.addBasketItemToSellerBasket(request.getId(),
                                         request.getBody());
                             }
                         },
                ResourceResponse.class);
    }

    @Override
    public ResourceResponse<PR_ABasketItem> updateBasketItemInSellerBasket(final ResourceRequest<PR_ABasketItem> request) {
        return this.call(new APIResourceCall<ResourceResponse>() {
                             @Override
                             public ResourceResponse callApi() {
                                 return playRetailAPI.updateBasketItemInSellerBasket(request.getId(),
                                         request.getSecondLevelId(),
                                         request.getBody());
                             }
                         },
                ResourceResponse.class);
    }

    @Override
    public ResourceResponse<PR_ABasketItem> removeBasketItemFromSellerBasket(final ResourceRequest<PR_ABasketItem> request) {
        return this.call(new APIResourceCall<ResourceResponse>() {
                             @Override
                             public ResourceResponse callApi() {
                                 return playRetailAPI.removeBasketItemFromSellerBasket(request.getId(),
                                         request.getSecondLevelId());
                             }
                         },
                ResourceResponse.class);
    }

    @Override
    public List<PR_ABasketItem> removeBasketItemsFromSellerBasket(final ResourceRequest<PR_ABasketItem> request) {
        List<PR_ABasketItem> response;
        try {
            response = playRetailAPI.removeBasketItemsFromSellerBasket(request.getId(), request.getMultiParams().get("id"));
            return response;
        } catch (RetrofitError retrofitError) {
            throw new BaseException();
        }
    }

    @Override
    public ResourceResponse<PR_ABasket> clearSellerBasket(final ResourceRequest<PR_ABasket> request) {
        return this.call(new APIResourceCall<ResourceResponse>() {
                             @Override
                             public ResourceResponse callApi() {
                                 return playRetailAPI.clearSellerBasket(request.getId());
                             }
                         },
                ResourceResponse.class);
    }

    @Override
    public ResourceResponse<PR_ABasket> linkSellerBasketToCustomer(final ResourceRequest<PR_ABasket> request) {
        return this.call(new APIResourceCall<ResourceResponse>() {
                             @Override
                             public ResourceResponse callApi() {
                                 return playRetailAPI.linkSellerBasketToCustomer(request.getId(),
                                         request.getSecondLevelId(),
                                         request.getParams(),
                                         request.getBody());
                             }
                         },
                ResourceResponse.class);
    }


    @Override
    public ResourceResponse<PR_ABarCodeInfo> getCorrespondence(final ResourceRequest request) throws BaseException {
        return this.call(new APIResourceCall<ResourceResponse>() {
                             @Override
                             public ResourceResponse callApi() {
                                 return playRetailAPI.getCorrespondence(request.getId());
                             }
                         },
                ResourceResponse.class);


    }


    @Override
    public ShopStatisticsResponse getShopStatistics(final ShopStatisticsRequest request) throws BaseException {
        return this.call(new APIResourceCall<ShopStatisticsResponse>() {
                             @Override
                             public ShopStatisticsResponse callApi() {
                                 return playRetailAPI.getShopStatistics();
                             }
                         },
                ShopStatisticsResponse.class);
    }

    @Override
    public ResourceResponse<PR_AProductShare> shareProducts(final ResourceRequest<PR_AProductShare> request) throws BaseException {
        return this.call(new APIResourceCall<ResourceResponse>() {
                             @Override
                             public ResourceResponse callApi() {
                                 return playRetailAPI.shareProducts(request.getBody());
                             }
                         },
                ResourceResponse.class);
    }

    private final static String GET_EVENTS_DATE_FORMAT_PATTERN = "yyyy-MM-dd";
    private final static SimpleDateFormat GET_EVENTS_DATE_FORMAT = new SimpleDateFormat(GET_EVENTS_DATE_FORMAT_PATTERN);

    @Override
    public PaginatedResourcesResponse<PR_AEvent> getEvents(final GetEventsRequest getEventsRequest) throws BaseException {
        return this.call(new APIResourceCall<PaginatedResourcesResponse>() {
                             @Override
                             public PaginatedResourcesResponse callApi() {
                                 PaginatedResourcesResponse<PR_AEvent> getEventsResponse;
                                 String after = getEventsRequest.getAfter() != null ? GET_EVENTS_DATE_FORMAT.format(getEventsRequest.getAfter()) : null;
                                 String before = getEventsRequest.getBefore() != null ? GET_EVENTS_DATE_FORMAT.format(getEventsRequest.getBefore()) : null;

                                 getEventsResponse = playRetailAPI.getEvents(getEventsRequest.getSellerId(),
                                         after,
                                         before,
                                         getEventsRequest.getAttendeeId(),
                                         getEventsRequest.getAttendeeType());
                                 return getEventsResponse;
                             }
                         },
                PaginatedResourcesResponse.class);

    }


    @Override
    public PaginatedResourcesResponse<PR_AOffer> getOffers(final ResourceRequest<PR_AOffer> request) throws BaseException {
        return this.call(new APIResourceCall<PaginatedResourcesResponse>() {
                             @Override
                             public PaginatedResourcesResponse callApi() {
                                 return playRetailAPI.getOffers(request.getParams());
                             }
                         },
                PaginatedResourcesResponse.class);
    }

    @Override
    public PaginatedResourcesResponse<PR_AFee> getFees(final ResourceRequest<PR_AFee> request) throws BaseException {
        return this.call(new APIResourceCall<PaginatedResourcesResponse>() {
                             @Override
                             public PaginatedResourcesResponse callApi() {
                                 return playRetailAPI.getFees(request.getParams());
                             }
                         },
                PaginatedResourcesResponse.class);
    }

    @Override
    public ResourceResponse<PR_AProductReview> addProductReview(final ResourceRequest<PR_AProductReview> request) throws BaseException {
        return this.call(new APIResourceCall<ResourceResponse>() {
                             @Override
                             public ResourceResponse callApi() {
                                 return playRetailAPI.addProductReview(request.getBody());
                             }
                         },
                ResourceResponse.class);
    }

    @Override
    public PaginatedResourcesResponse<PR_AProductReview> getProductReviews(final ResourceRequest<PR_AProductReview> request) throws BaseException {
        return this.call(new APIResourceCall<PaginatedResourcesResponse>() {
                             @Override
                             public PaginatedResourcesResponse callApi() {
                                 return playRetailAPI.getProductReviews(request.getParams());
                             }
                         },
                PaginatedResourcesResponse.class);
    }

    @Override
    public ResourceResponse<PR_AProductReview> updateProductReview(final ResourceRequest<PR_AProductReview> request) throws BaseException {
        return this.call(new APIResourceCall<ResourceResponse>() {
                             @Override
                             public ResourceResponse callApi() {
                                 return playRetailAPI.updateProductReview(request.getId(), request.getBody());
                             }
                         },
                ResourceResponse.class);
    }


    @Override
    public PaginatedResourcesResponse<PR_AProductReviewAttribute> getProductReviewAttributes(final ResourceRequest<PR_AProductReviewAttribute> request) throws BaseException {
        return this.call(new APIResourceCall<PaginatedResourcesResponse>() {
                             @Override
                             public PaginatedResourcesResponse callApi() {
                                 return playRetailAPI.getProductReviewAttributes(request.getParams());
                             }
                         },
                PaginatedResourcesResponse.class);
    }

    @Override
    public PaginatedResourcesResponse<PR_ADeliveryMode> getDeliveryMode(final ResourceRequest<PR_ADeliveryMode> request) throws BaseException {
        return this.call(new APIResourceCall<PaginatedResourcesResponse>() {
                             @Override
                             public PaginatedResourcesResponse<PR_ADeliveryMode> callApi() {
                                 return playRetailAPI.getDeliveryModes(request.getParams());
                             }
                         },
                PaginatedResourcesResponse.class);
    }

    @Override
    public ResourceResponse<PR_ADelivery> updateDelivery(final ResourceRequest<PR_ADelivery> request) throws BaseException {
        return this.call(new APIResourceCall<ResourceResponse>() {
                             @Override
                             public ResourceResponse callApi() {
                                 return playRetailAPI.updateDeliveryMode(request.getId(),
                                         request.getSecondLevelId(),
                                         request.getThirdLevelId(),
                                         request.getBody());
                             }
                         },
                ResourceResponse.class);
    }

    @Override
    public ResourceResponse<PR_APaymentShare> createPaymentShare(final ResourceRequest<PR_APaymentShare> request) throws BaseException {
        return this.call(new APIResourceCall<ResourceResponse>() {
                             @Override
                             public ResourceResponse callApi() {
                                 return playRetailAPI.createPaymentShare(request.getBody());
                             }
                         },
                ResourceResponse.class);
    }

    @Override
    public ResourceResponse<PR_AFeedback> sendFeedback(final ResourceRequest<PR_AFeedback> request) throws BaseException {
        return this.call(new APIResourceCall<ResourceResponse>() {
                             @Override
                             public ResourceResponse callApi() {
                                 return playRetailAPI.sendFeedback(request.getBody());
                             }
                         },
                ResourceResponse.class);
    }

    @Override
    public ResourceResponse<PR_AQuotation> createQuotation(final ResourceRequest<PR_AQuotation> request) throws BaseException {
        return this.call(new APIResourceCall<ResourceResponse>() {
                             @Override
                             public ResourceResponse callApi() {
                                 return playRetailAPI.createQuotation(request.getBody());
                             }
                         },
                ResourceResponse.class);
    }

    @Override
    public PaginatedResourcesResponse<PR_AQuotation> getQuotations(final ResourceRequest resourcesRequest) throws BaseException {
        return this.call(new APIResourceCall() {
                             @Override
                             public PaginatedResourcesResponse callApi() {
                                 return playRetailAPI.getQuotations(resourcesRequest.getParams());
                             }
                         },
                PaginatedResourcesResponse.class);
    }

    @Override
    public ResourceResponse<PR_ABasketItem> addProductToQuotation(final ResourceRequest<PR_ABasketItem> request) {
        return this.call(new APIResourceCall<ResourceResponse>() {
                             @Override
                             public ResourceResponse callApi() {
                                 return playRetailAPI.addProductToQuotation(request.getId(),
                                         request.getBody());
                             }
                         },
                ResourceResponse.class);
    }

    @Override
    public ResourceResponse<PR_AQuotation> updateQuotation(final ResourceRequest<PR_AQuotation> request) throws BaseException {
        return this.call(new APIResourceCall<ResourceResponse>() {
                             @Override
                             public ResourceResponse callApi() {
                                 return playRetailAPI.updateQuotation(request.getId(),
                                         request.getBody());
                             }
                         },
                ResourceResponse.class);
    }

    @Override
    public ResourceResponse<PR_ABasketItem> updateBasketItemInQuotationBasket(final ResourceRequest<PR_ABasketItem> request) {
        return this.call(new APIResourceCall<ResourceResponse>() {
                             @Override
                             public ResourceResponse callApi() {
                                 return playRetailAPI.updateBasketItemInQuotationBasket(request.getId(),
                                         request.getSecondLevelId(),
                                         request.getBody());
                             }
                         },
                ResourceResponse.class);
    }

    @Override
    public ResourceResponse<PR_ABasket> getQuotationCart(final ResourceRequest request) throws BaseException {
        return this.call(new APIResourceCall<ResourceResponse>() {
                             @Override
                             public ResourceResponse callApi() {
                                 return playRetailAPI.getQuotationCart(request.getId());
                             }
                         },
                ResourceResponse.class);
    }

    @Override
    public ResourceResponse<PR_ABasket> clearQuotationBasket(final ResourceRequest<PR_ABasket> request) {
        return this.call(new APIResourceCall<ResourceResponse>() {
                             @Override
                             public ResourceResponse callApi() {
                                 return playRetailAPI.clearQuotationBasket(request.getId());
                             }
                         },
                ResourceResponse.class);
    }

    @Override
    public ResourceResponse<PR_ABasketItem> removeBasketItemFromQuotationBasket(final ResourceRequest<PR_ABasketItem> request) {
        return this.call(new APIResourceCall<ResourceResponse>() {
                             @Override
                             public ResourceResponse callApi() {
                                 return playRetailAPI.removeBasketItemFromQuotationBasket(request.getId(),
                                         request.getSecondLevelId());
                             }
                         },
                ResourceResponse.class);
    }

    @Override
    public List<PR_ABasketItem> removeBasketItemsFromQuotationBasket(ResourceRequest<PR_ABasketItem> request) {
        List<PR_ABasketItem> response;
        try {
            response = playRetailAPI.removeBasketItemsFromQuotationBasket(request.getId(), request.getMultiParams().get("id"));
            return response;
        } catch (RetrofitError retrofitError) {
            throw new BaseException();
        }
    }

    @Override
    public ResourceResponse<PR_AData> getDataOut(GetDataOutRequest request) throws BaseException {
        return this.call(new APIResourceCall<ResourceResponse>() {
                             @Override
                             public ResourceResponse callApi() {
                                 return playRetailAPI.getDataOut();
                             }
                         },
                ResourceResponse.class);
    }

    @Override
    public List<Contact> sendDataIn(final ResourceRequest<List<Contact>> resourceRequest) throws BaseException {
        List<Contact> response;
        try {
            response = playRetailAPI.sendDataIn(resourceRequest.getBody());
            return response;
        } catch (RetrofitError retrofitError) {
            throw new BaseException();
        }
    }
}