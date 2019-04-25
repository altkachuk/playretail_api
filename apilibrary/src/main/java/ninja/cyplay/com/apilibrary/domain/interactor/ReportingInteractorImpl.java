package ninja.cyplay.com.apilibrary.domain.interactor;

import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.List;

import ninja.cyplay.com.apilibrary.domain.executor.InteractorExecutor;
import ninja.cyplay.com.apilibrary.domain.executor.MainThreadExecutor;
import ninja.cyplay.com.apilibrary.domain.repository.PlayRetailRepository;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.exception.BaseException;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.requests.ResourceRequest;
import ninja.cyplay.com.apilibrary.models.abstractmodels.WSReport;
import ninja.cyplay.com.apilibrary.utils.LogUtils;

/**
 * Created by damien on 25/05/15.
 */
public class ReportingInteractorImpl extends AbstractInteractor implements ReportingInteractor {

    private PlayRetailRepository repository;

    public ReportingInteractorImpl(InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor, PlayRetailRepository repository) {
        super(interactorExecutor, mainThreadExecutor);
        this.repository = repository;
    }


    // -------------------------------------------------------------------------------------------
    //
    // -------------------------------------------------------------------------------------------


    @Override
    public void saveWebServiceTimes(List<WSReport> ws_tt_l, ReportingInteractor.saveDisplayTimesCallback callback) {
        final WeakReference<ResourceRequest<List<WSReport>>> saveDisplayTimesRequest = new WeakReference<ResourceRequest<List<WSReport>>>(new ResourceRequest().body(ws_tt_l));
        final WeakReference<ReportingInteractor.saveDisplayTimesCallback> saveDisplayTimesCallback = new WeakReference<ReportingInteractor.saveDisplayTimesCallback>(callback);
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                try {
                    final List<String> saveDisplayTimesResponse = repository.saveWebserviceTimes(saveDisplayTimesRequest.get());
                    getMainThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (saveDisplayTimesCallback.get() != null) {
                                saveDisplayTimesCallback.get().onSuccess(saveDisplayTimesResponse);
                            }else {
                                Log.i(ReportingInteractorImpl.class.getName(),"Request callback is None" );
                            }
                        }
                    });
                } catch (final BaseException e) {
                    Log.e(LogUtils.generateTag(this), "Error on ReportingInteractorImpl interactor");
                    getMainThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (saveDisplayTimesCallback.get() != null) {
                                saveDisplayTimesCallback.get().onError(e);
                            }else {
                                Log.i(ReportingInteractorImpl.class.getName(),"Request callback is None" );
                            }
                        }
                    });
                }
            }
        });
    }

}
