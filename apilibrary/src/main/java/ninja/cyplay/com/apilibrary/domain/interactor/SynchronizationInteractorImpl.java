package ninja.cyplay.com.apilibrary.domain.interactor;

import android.util.Base64;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.List;

import ninja.cyplay.com.apilibrary.domain.executor.InteractorExecutor;
import ninja.cyplay.com.apilibrary.domain.executor.MainThreadExecutor;
import ninja.cyplay.com.apilibrary.domain.http.HttpGetRequest;
import ninja.cyplay.com.apilibrary.domain.interactor.callback.ResourceRequestCallback;
import ninja.cyplay.com.apilibrary.domain.repository.PlayRetailRepository;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.exception.BaseException;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.requests.ResourceRequest;

import ninja.cyplay.com.apilibrary.models.abstractmodels.v2.Contact;
import ninja.cyplay.com.apilibrary.models.singleton.APIValue;
import ninja.cyplay.com.apilibrary.utils.LogUtils;

/**
 * Created by damien on 22/05/15.
 */
public class SynchronizationInteractorImpl extends AbstractInteractor implements SynchronizationInteractor {

    static public final String DATA_OUT_URL = "http://pxkapp.pixika.com/data-out/";

    private PlayRetailRepository repository;
    private APIValue apiValue;

    public SynchronizationInteractorImpl(InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor, PlayRetailRepository repository, APIValue apiValue) {
        super(interactorExecutor, mainThreadExecutor);
        this.repository = repository;
        this.apiValue = apiValue;
    }


    // -------------------------------------------------------------------------------------------
    //
    // -------------------------------------------------------------------------------------------


    @Override
    public void getDataOut(final ResourceRequestCallback<String> callback) {
        final ResourceRequestCallback<String> getDataOutCallback = callback;

        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                HttpGetRequest httpGetRequest = new HttpGetRequest();
                httpGetRequest.setListener(new HttpGetRequest.GetRequestListener() {
                    @Override
                    public void onComplete(final String text) {
                        getMainThreadExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                getDataOutCallback.onSuccess(text);
                            }
                        });
                    }

                    @Override
                    public void onError() {
                        Log.e(LogUtils.generateTag(this), "Error on GetDataOut interactor");
                        getMainThreadExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                getDataOutCallback.onError(new BaseException());
                            }
                        });
                    }
                });
                // concatenate username and password with colon for authentication
                String credentials = apiValue.getUsername() + ":" + apiValue.getPassword();
                // create Base64 encodet string
                String basic =
                        "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                httpGetRequest.execute(DATA_OUT_URL, basic);
            }
        });
    }


    // -------------------------------------------------------------------------------------------
    //
    // -------------------------------------------------------------------------------------------


    @Override
    public void sendDataIn(List<Contact> customersIn, final ResourceRequestCallback<List<Contact>> callback) {
        final WeakReference<ResourceRequest<List<Contact>>> sendDataInRequest = new WeakReference<ResourceRequest<List<Contact>>>(new ResourceRequest().body(customersIn));
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                try {
                    final List<Contact> sendDataInResponse = repository.sendDataIn(sendDataInRequest.get());
                    getMainThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            callback.onSuccess(sendDataInResponse);
                        }
                    });
                } catch (final BaseException e) {
                    Log.e(LogUtils.generateTag(this), "Error on ReportingInteractorImpl interactor");
                    getMainThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            callback.onError(e);
                        }
                    });
                }
            }
        });
    }
}

