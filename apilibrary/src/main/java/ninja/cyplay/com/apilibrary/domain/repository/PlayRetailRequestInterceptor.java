package ninja.cyplay.com.apilibrary.domain.repository;

import java.util.Locale;

import ninja.cyplay.com.apilibrary.models.singleton.APIValue;
import ninja.cyplay.com.apilibrary.utils.ClientUtil;
import ninja.cyplay.com.apilibrary.utils.CompatUtils;
import ninja.cyplay.com.apilibrary.utils.Constants;
import retrofit.RequestInterceptor;

public class PlayRetailRequestInterceptor implements RequestInterceptor {

    public final static String AUTHORIZATION = "Authorization";
    public final static String API_KEY_PARAM = "Apikey";
    public final static String SHOP_ID_PARAM = "SHOPID";
    public final static String REQUEST_TIME_PARAM = "REQTIME";
    public final static String LANGUAGE_PARAM = "LANGUAGE";

    APIValue apiValue;

    public PlayRetailRequestInterceptor() {
    }

    public PlayRetailRequestInterceptor(APIValue apiValue) {
        this.apiValue = apiValue;
    }

    @Override
    public void intercept(RequestFacade request) {
        request.addHeader(API_KEY_PARAM, Constants.API_KEY);
        request.addHeader(REQUEST_TIME_PARAM, String.valueOf(System.currentTimeMillis()));
        request.addHeader(SHOP_ID_PARAM, ClientUtil.getShopId());
        request.addHeader(LANGUAGE_PARAM, CompatUtils.toLanguageTag(Locale.getDefault()));

        // Oauth2
        if (apiValue != null && apiValue.getOAuth2Credentials() != null && apiValue.getOAuth2Credentials().getAccess_token() != null)
            request.addHeader(AUTHORIZATION, apiValue.getOAuth2Credentials().getToken_type() + " " + apiValue.getOAuth2Credentials().getAccess_token());
    }

}