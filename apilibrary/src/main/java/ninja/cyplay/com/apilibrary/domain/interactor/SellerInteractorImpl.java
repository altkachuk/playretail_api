package ninja.cyplay.com.apilibrary.domain.interactor;

import android.util.Log;

import java.lang.ref.WeakReference;

import ninja.cyplay.com.apilibrary.domain.executor.InteractorExecutor;
import ninja.cyplay.com.apilibrary.domain.executor.MainThreadExecutor;
import ninja.cyplay.com.apilibrary.domain.interactor.callback.ResourceRequestCallback;
import ninja.cyplay.com.apilibrary.domain.repository.PlayRetailRepository;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.exception.BaseException;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.requests.AccessTokenRequest;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.requests.ResourceRequest;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.responses.ResourceResponse;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ADeviceConfiguration;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AUser;
import ninja.cyplay.com.apilibrary.models.business.OAuth2Credentials;
import ninja.cyplay.com.apilibrary.models.business.PasswordReplacement;
import ninja.cyplay.com.apilibrary.utils.LogUtils;

/**
 * Created by damien on 29/04/15.
 */
public class SellerInteractorImpl extends AbstractInteractor implements SellerInteractor {

    private PlayRetailRepository repository;

    public SellerInteractorImpl(InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor, PlayRetailRepository repository) {
        super(interactorExecutor, mainThreadExecutor);
        this.repository = repository;
    }


    // -------------------------------------------------------------------------------------------
    //
    // -------------------------------------------------------------------------------------------


    @Override
    public void accessToken(String username, String password, String client_id, String client_secret, ResourceRequestCallback<OAuth2Credentials> callback) {
        final ResourceRequestCallback<OAuth2Credentials> accessTokenCallback = callback;
        final WeakReference<AccessTokenRequest> accessTokenRequest = new WeakReference<AccessTokenRequest>(new AccessTokenRequest(username, password, client_id, client_secret, AccessTokenRequest.PASSWORD_GRANT_TYPE));
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                try {
                    final ResourceResponse<OAuth2Credentials> OAuth2Credentials = repository.accessToken(accessTokenRequest.get());
                    getMainThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (accessTokenCallback != null) {
                                accessTokenCallback.onSuccess(OAuth2Credentials.getResource());
                            }
                        }
                    });
                } catch (final BaseException e) {
                    Log.e(LogUtils.generateTag(this), "Error on accessToken interactor");
                    getMainThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (accessTokenCallback != null) {
                                accessTokenCallback.onError(e);
                            }
                        }
                    });
                }
            }
        });
    }


    @Override
    public void replaceUserPassword(String userid, final String oldPassword, final String newPassword, final ResourceRequestCallback<PR_AUser> callback) {
        PasswordReplacement passwordReplacement = new PasswordReplacement(oldPassword, newPassword);
        final WeakReference<ResourceRequest<PasswordReplacement>> accessTokenRequest = new WeakReference<ResourceRequest<PasswordReplacement>>(new ResourceRequest<PasswordReplacement>().id(userid).body(passwordReplacement));
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                try {
                    final ResourceResponse<PR_AUser> replaceUserPassword = repository.replaceUserPassword(accessTokenRequest.get());
                    getMainThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (callback != null) {
                                callback.onSuccess(replaceUserPassword.getResource());
                            }
                        }
                    });
                } catch (final BaseException e) {
                    Log.e(LogUtils.generateTag(this), "Error on accessToken interactor");
                    getMainThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (callback != null) {
                                callback.onError(e);
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void notifyLostUserPassword(String userid, final ResourceRequestCallback<PR_AUser> callback) {
        PasswordReplacement passwordReplacement = new PasswordReplacement("","");
        final WeakReference<ResourceRequest<PasswordReplacement>> notifyLostUserPasswordRequest = new WeakReference<ResourceRequest<PasswordReplacement>>(new ResourceRequest<PasswordReplacement>().id(userid).body(passwordReplacement));
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                try {
                    final ResourceResponse<PR_AUser> notifyLostUserPassword = repository.notifyLostUserPassword(notifyLostUserPasswordRequest.get());
                    getMainThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (callback != null) {
                                callback.onSuccess(notifyLostUserPassword.getResource());
                            }
                        }
                    });
                } catch (final BaseException e) {
                    Log.e(LogUtils.generateTag(this), "Error on replaceUserPassword interactor");
                    getMainThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (callback != null) {
                                callback.onError(e);
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
    public void invalidateToken(String client_id,
                                String client_secret,
                                String token,
                                final ResourceRequestCallback<Void> callback) {
        final AccessTokenRequest invalidateTokenRequest = new AccessTokenRequest(client_id, client_secret, token);
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                try {
                    repository.invalidateToken(invalidateTokenRequest);
                    getMainThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (callback != null) {
                                callback.onSuccess(null);
                            } else {
                                Log.i(SellerInteractorImpl.class.getName(), "Request callback is None");
                            }
                        }
                    });
                } catch (final BaseException e) {
                    Log.e(LogUtils.generateTag(this), "Error on invalidateToken interactor");
                    getMainThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (callback != null) {
                                callback.onError(e);
                            } else {
                                Log.i(SellerInteractorImpl.class.getName(), "Request callback is None");
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
    public void getDeviceConfiguration(String udid, ResourceRequestCallback<PR_ADeviceConfiguration> callback) {
        final WeakReference<ResourceRequestCallback<PR_ADeviceConfiguration>> getDeviceConfigurationCallback = new WeakReference<ResourceRequestCallback<PR_ADeviceConfiguration>>(callback);
        final WeakReference<ResourceRequest> getDeviceConfigurationRequest = new WeakReference<ResourceRequest>(new ResourceRequest().id(udid));
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                SellerInteractorImpl.this.doGetResource(getDeviceConfigurationRequest.get(),
                        getDeviceConfigurationCallback.get(),
                        new ResourceGetter() {
                            @Override
                            public ResourceResponse getResource(ResourceRequest resourceRequest) {
                                return repository.getDeviceConfiguration(getDeviceConfigurationRequest.get());
                            }
                        });
            }
        });
    }

}
