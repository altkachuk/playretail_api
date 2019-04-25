package ninja.cyplay.com.apilibrary.domain.repository;

import com.squareup.okhttp.Authenticator;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.Proxy;

import ninja.cyplay.com.apilibrary.domain.repositoryModel.requests.AccessTokenRequest;
import ninja.cyplay.com.apilibrary.models.business.OAuth2Credentials;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.responses.ResourceResponse;
import ninja.cyplay.com.apilibrary.models.singleton.APIValue;
import ninja.cyplay.com.apilibrary.utils.ActivityHelper;
import ninja.cyplay.com.apilibrary.utils.ClientUtil;

/**
 * Created by romainlebouc on 10/08/16.
 */
public class TokenAuthenticator implements Authenticator {

    private final APIValue apiValue;
    private final RetrofitPlayRetailAPIRepository repository;

    public TokenAuthenticator(APIValue apiValue, RetrofitPlayRetailAPIRepository repository){
        this.apiValue = apiValue;
        this.repository = repository;
    }
    @Override
    public Request authenticate(Proxy proxy, Response response) throws IOException {

        // If we have a 401 error, we try to refresh the token
        if ( apiValue.getOAuth2Credentials() !=null
                && !isTokenRequest(response.request())){
            AccessTokenRequest accessTokenRequest = new AccessTokenRequest(ClientUtil.getClientId(),
                    ClientUtil.getClientSecret(),
                    AccessTokenRequest.PASSWORD_REFRESH_TOKEN,
                    apiValue.getOAuth2Credentials().getRefresh_token());

            final ResourceResponse<OAuth2Credentials> oAuth2Credentials = repository.refreshAccessToken(accessTokenRequest);
            apiValue.setOAuth2Credentials(oAuth2Credentials.getResource());

            // Add new header to rejected request and retry it
            return response.request().newBuilder()
                    .header(PlayRetailRequestInterceptor.AUTHORIZATION, apiValue.getOAuth2Credentials().getAuthorization())
                    .build();
        // If we still have an error after when refreshing the token, the we go back to login page
        }else if ( apiValue.getOAuth2Credentials() !=null
                && isTokenRequest(response.request())){
            apiValue.clearOAuth2Credentials();
            ActivityHelper.goToRestartActivity();
        }
        return null;

    }

    @Override
    public Request authenticateProxy(Proxy proxy, Response response) throws IOException {
        // Null indicates no attempt to authenticate.
        return null;
    }

    /**
     * Detect if a request is the request to id or refresh a Token
     * @param request
     * @return
     */
    private boolean isTokenRequest(Request request){
        return request.url().getPath().endsWith("auth/token/");
    }
}