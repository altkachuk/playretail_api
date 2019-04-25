package ninja.cyplay.com.playretail_api.ui.presenter;

import android.content.Context;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.inject.Inject;

import ninja.cyplay.com.apilibrary.domain.interactor.SellerInteractor;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.exception.BaseException;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ACustomerDetails;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ACustomerPreview;
import ninja.cyplay.com.playretail_api.model.business.CustomerDetails;
import ninja.cyplay.com.playretail_api.model.business.CustomerPreview;
import ninja.cyplay.com.playretail_api.model.business.Seller;
import ninja.cyplay.com.apilibrary.models.singleton.APIValue;
import ninja.cyplay.com.playretail_api.model.singleton.SellerContext;
import ninja.cyplay.com.apilibrary.utils.AeSimpleSHA1;
import ninja.cyplay.com.apilibrary.utils.Constants;
import ninja.cyplay.com.apilibrary.utils.LogUtils;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.app.BasePresenter;
import ninja.cyplay.com.playretail_api.ui.view.AuthenticationView;

/**
 * Created by damien on 29/04/15.
 */
public class AuthenticationPresenterImpl extends BasePresenter implements AuthenticationPresenter {

    @Inject
    APIValue apiValue;

    @Inject
    SellerContext sellerContext;

    private Context context;
    private AuthenticationView authenticationView;
    private SellerInteractor sellerInteractor;

    public AuthenticationPresenterImpl(Context context, SellerInteractor sellerInteractor) {
        super(context);
        this.sellerInteractor = sellerInteractor;
        this.context = context;
    }

    @Override
    public void setView(AuthenticationView view) {
        authenticationView = view;
    }

    @Override
    public void getOauth2Details(String username) {
        authenticationView.showLoading();
        sellerInteractor.getOauth2Details(username, new SellerInteractor.GetOauth2DetailsCallback() {
            @Override
            public void onSuccess(String client_id, String client_secret) {
                // save client id and secret to do request in the future
                apiValue.setClient_id(client_id);
                apiValue.setClient_secret(client_secret);
                authenticationView.hideLoading();
                authenticationView.onGetOauth2DetailsSuccess();
            }

            @Override
            public void onError(BaseException e) {
                doError(e);
            }
        });
    }

    @Override
    public void getSellerDetails(final Seller seller) {
        if (seller != null) {
            authenticationView.showLoading();
            sellerInteractor.getSeller(seller.getUn(), new SellerInteractor.GetSellerCallback() {

                @Override
                public void onSuccess(List<PR_ACustomerPreview> customerHistory, String firebaseToken) {
                    authenticationView.hideLoading();
                    apiValue.setFirebaseToken(firebaseToken);
                    sellerContext.setSeller(seller);
                    sellerContext.setCustomer_history((List<CustomerPreview>)(List<?>)customerHistory);
                    authenticationView.onGetSellerSuccess();
                }

                @Override
                public void onError(BaseException e) {
                    doError(e);
                }
            });
        }
    }


    @Override
    public void getAccessToken(String username, String password) {
        authenticationView.showLoading();
        sellerInteractor.accessToken(username, password, apiValue.getClient_id(), apiValue.getClient_secret(),
                new SellerInteractor.AccessTokenCallback() {
                    @Override
                    public void onSuccess(String access_token) {
                        // save client id and secret to do request in the future
                        apiValue.setAccessToken(access_token);
                        authenticationView.hideLoading();
                        authenticationView.onAccessTokenSuccess();
                    }

                    @Override
                    public void onError(BaseException e) {
                        doError(e);
                    }
                });
    }

    @Override
    public void invalidateToken() {
        authenticationView.showLoading();
        sellerInteractor.invalidateToken(new SellerInteractor.InvalidateTokenCallback() {
            @Override
            public void onSuccess() {
                // Remove token
                apiValue.setAccessToken(null);
                authenticationView.hideLoading();
                authenticationView.onInvalidateTokenSuccess();
            }
            @Override
            public void onError(BaseException e) {
                doError(e);
            }
        });
    }

    private void doError(BaseException e) {
        Log.e(LogUtils.generateTag(AuthenticationPresenterImpl.this), "Error on Authentification presenter");
        authenticationView.hideLoading();
        switch(e.getType()) {
            case BUSINESS:
                if (e.getResponse() != null && e.getResponse().getStatus() != null)
                    authenticationView.displaySttPopUp(e.getResponse().getDetail(),e.getResponse().getStatus());
                break;

            case TECHNICAL:
                authenticationView.displayPopUp(context.getString(R.string.reload_error_msg));
                break;
        }
        authenticationView.onError();
    }

    // --------------------------------------------------------------------------------------------
    // --------------------------------------------------------------------------------------------
    // --------------------------------------------------------------------------------------------

    @Override
    public void doLogin(final Seller PRSeller, String pwd) {
        authenticationView.showLoading();
        sellerInteractor.authenticate(PRSeller.getUn(), getHash(PRSeller, pwd), apiValue.getDeviceId(), new SellerInteractor.AuthenticateCallback() {

            @Override
            public void onSuccess(String sid, String firebaseToken, List<PR_ACustomerDetails> cdh) {
                // Set current seller in context
                sellerContext.setSeller(PRSeller);
                sellerContext.setCustomer_history((List<CustomerPreview>)(List<?>)cdh);

                // save session id etc to be able to do request in the future
                apiValue.setSid(sid);
                apiValue.setFirebaseToken(firebaseToken);

                authenticationView.hideLoading();
                authenticationView.onAuthenticationSuccess();
            }

            @Override
            public void onError(BaseException e) {
                Log.e(LogUtils.generateTag(AuthenticationPresenterImpl.this), "Error on Authentification presenter");
                authenticationView.hideLoading();

                switch(e.getType()){
                    case BUSINESS:
                        if (e.getResponse() != null && e.getResponse().getStatus() != null)
                            authenticationView.displaySttPopUp(e.getResponse().getDetail(),e.getResponse().getStatus());
                        break;

                    case TECHNICAL:
                        authenticationView.displayPopUp(context.getString(R.string.reload_error_msg));
                        break;
                }
                authenticationView.onError();
            }
        });
    }

    private String getHash(Seller PRSeller, String pwd) {
        String hash = "";
        try {
            String hashedPass = AeSimpleSHA1.SHA1(PRSeller.getChal() + pwd);
            hash = AeSimpleSHA1.SHA1(apiValue.getChal() + Constants.LOGIN_SECRET + hashedPass);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return hash;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void onViewCreate() {

    }

    @Override
    public void onViewResume() {
        //Do nothing
    }

    @Override
    public void onViewDestroy() {
        //Do nothing
    }

}
