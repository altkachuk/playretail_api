package ninja.cyplay.com.apilibrary.domain.interactor;

import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.List;

import ninja.cyplay.com.apilibrary.domain.executor.InteractorExecutor;
import ninja.cyplay.com.apilibrary.domain.executor.MainThreadExecutor;
import ninja.cyplay.com.apilibrary.domain.interactor.callback.ResourceRequestCallback;
import ninja.cyplay.com.apilibrary.domain.repository.PlayRetailRepository;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.exception.BaseException;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.requests.ResourceRequest;
import ninja.cyplay.com.apilibrary.models.abstractmodels.ActionEvent;
import ninja.cyplay.com.apilibrary.utils.LogUtils;


/**
 * Created by romainlebouc on 22/09/16.
 */
public class ActionEventInteractorImpl extends AbstractInteractor implements ActionEventInteractor {
    private PlayRetailRepository repository;


    public ActionEventInteractorImpl(InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor, PlayRetailRepository repository) {
        super(interactorExecutor, mainThreadExecutor);
        this.repository = repository;
    }


    @Override
    public void saveActionEvents(List<ActionEvent> actionEvents, final ResourceRequestCallback<List<ActionEvent>> callback) {
        final WeakReference<ResourceRequest<List<ActionEvent>>> saveActionEventsRequest = new WeakReference<ResourceRequest<List<ActionEvent>>>(new ResourceRequest().body(actionEvents));
        final WeakReference<ResourceRequestCallback<List<ActionEvent>>> saveActionEventsCallback = new WeakReference<>(callback);
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                try {
                    final List<ActionEvent> saveDisplayTimesResponse = repository.saveActionEvents(saveActionEventsRequest.get());
                    getMainThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (saveActionEventsCallback.get() != null) {
                                saveActionEventsCallback.get().onSuccess(saveDisplayTimesResponse);
                            }else {
                                Log.i(ActionEventInteractorImpl.class.getName(),"Request callback is None" );
                            }

                        }
                    });
                } catch (final BaseException e) {
                    Log.e(LogUtils.generateTag(this), "Error on ActionEventInteractorImpl interactor");
                    getMainThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (saveActionEventsCallback.get() != null) {
                                saveActionEventsCallback.get().onError(e);
                            }else {
                                Log.i(ActionEventInteractorImpl.class.getName(),"Request callback is None" );
                            }
                        }
                    });
                }
            }
        });
    }


}
