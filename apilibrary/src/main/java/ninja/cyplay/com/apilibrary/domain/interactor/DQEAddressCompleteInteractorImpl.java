package ninja.cyplay.com.apilibrary.domain.interactor;

import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ninja.cyplay.com.apilibrary.domain.executor.InteractorExecutor;
import ninja.cyplay.com.apilibrary.domain.executor.MainThreadExecutor;
import ninja.cyplay.com.apilibrary.domain.interactor.callback.ResourceRequestCallback;
import ninja.cyplay.com.apilibrary.domain.repository.dqe.DQERepository;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.exception.BaseException;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.requests.ResourceRequest;
import ninja.cyplay.com.apilibrary.models.abstractmodels.DQEResult;
import ninja.cyplay.com.apilibrary.utils.LogUtils;

/**
 * Created by wentongwang on 08/06/2017.
 */

public class DQEAddressCompleteInteractorImpl extends AbstractInteractor implements DQEAddressCompleteInteractor {

    private DQERepository repository;

    public DQEAddressCompleteInteractorImpl(InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor, DQERepository repository) {
        super(interactorExecutor, mainThreadExecutor);
        this.repository = repository;
    }

    @Override
    public void setBaseUrl(String baseUrl){
        repository.initRepository(baseUrl);
    }


    @Override
    public void getAddress(Map<String, String> params, final ResourceRequestCallback<List<DQEResult>> callback) {


        final WeakReference<ResourceRequest> getAddressRequest = new WeakReference<ResourceRequest>(new ResourceRequest().filter(params));
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                try {
                    final Map<String, DQEResult> saveDisplayTimesResponse = repository.getAddress(getAddressRequest.get());
                    getMainThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (callback != null) {

                                if (saveDisplayTimesResponse != null && !saveDisplayTimesResponse.isEmpty()) {
                                    List<DQEResult> results = new ArrayList<>();

                                    for (DQEResult dqeResult : saveDisplayTimesResponse.values()) {
                                        results.add(dqeResult);
                                    }
                                    callback.onSuccess(results);
                                } else {
                                    callback.onSuccess(null);
                                }

                            } else {
                                Log.i(DQEAddressCompleteInteractorImpl.class.getName(), "Request callback is None");
                            }

                        }
                    });
                } catch (final BaseException e) {
                    Log.e(LogUtils.generateTag(this), "Error on DQEAddressCompleteInteractorImpl interactor");
                    getMainThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (callback != null) {
                                callback.onError(e);
                            } else {
                                Log.i(DQEAddressCompleteInteractorImpl.class.getName(), "Request callback is None");
                            }
                        }
                    });
                }
            }
        });
    }
}
