package ninja.cyplay.com.apilibrary.domain.repository.dqe;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;

import ninja.cyplay.com.apilibrary.domain.repositoryModel.exception.BaseException;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.requests.ResourceRequest;
import ninja.cyplay.com.apilibrary.models.abstractmodels.DQEResult;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by wentongwang on 08/06/2017.
 */

public class DQEApiRepository implements DQERepository {

    private RequestInterceptor requestInterceptor;
    private DQEService dqeService;


    public final static String DEFAULT_BASE_URL = "https://prod2.dqe-software.com";
    public final static String GSON_BUILDER_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm";

    public DQEApiRepository(Context context, RequestInterceptor requestInterceptor) {
        this.requestInterceptor = requestInterceptor;
    }

    @Override
    public void initRepository(String baseUrl) {
        //if this url is null, use default url
        if (baseUrl == null || baseUrl.isEmpty()) {
            baseUrl = DEFAULT_BASE_URL;
        }

        GsonConverter gsonConverter = new GsonConverter(initGson());
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(baseUrl)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setClient(new OkClient())
                .setConverter(gsonConverter)
                .setRequestInterceptor(requestInterceptor)
                .build();

        dqeService = restAdapter.create(DQEService.class);
    }

    private Gson initGson() {
        return new GsonBuilder()
                .setDateFormat(GSON_BUILDER_DATE_FORMAT)
                .create();
    }


    @Override
    public Map<String, DQEResult> getAddress(ResourceRequest request) throws BaseException {
        Map<String, DQEResult> response;
        try {
            response = dqeService.getAddress(request.getParams());
            return response;
        } catch (RetrofitError retrofitError) {
            throw new BaseException();
        }
    }
}
