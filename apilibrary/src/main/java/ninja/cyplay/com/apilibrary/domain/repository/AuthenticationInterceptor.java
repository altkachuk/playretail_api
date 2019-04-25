package ninja.cyplay.com.apilibrary.domain.repository;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import ninja.cyplay.com.apilibrary.models.singleton.APIValue;

/**
 * Created by romainlebouc on 10/08/16.
 */


class AuthenticationInterceptor implements Interceptor {

    private final APIValue apiValue;

    public AuthenticationInterceptor(APIValue apiValue){
        this.apiValue = apiValue;
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request originalRequest = chain.request();

        // Nothing to add to intercepted request if:
        // a) Authorization value is empty because user is not logged in yet
        // b) There is already a header with updated Authorization value
        if (apiValue.getOAuth2Credentials() == null  /*|| alreadyHasAuthorizationHeader(originalRequest)*/) {
            return chain.proceed(originalRequest);
        }

        // Add authorization header with updated authorization value to intercepted request
        Request authorisedRequest = originalRequest.newBuilder()
                .header(PlayRetailRequestInterceptor.AUTHORIZATION,  apiValue.getOAuth2Credentials().getAuthorization())
                .build();
        return chain.proceed(authorisedRequest);
    }
}