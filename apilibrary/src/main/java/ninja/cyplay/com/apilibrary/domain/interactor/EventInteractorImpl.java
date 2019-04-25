package ninja.cyplay.com.apilibrary.domain.interactor;

import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.Date;

import ninja.cyplay.com.apilibrary.domain.executor.InteractorExecutor;
import ninja.cyplay.com.apilibrary.domain.executor.MainThreadExecutor;
import ninja.cyplay.com.apilibrary.domain.interactor.callback.PaginatedResourceRequestCallback;
import ninja.cyplay.com.apilibrary.domain.repository.PlayRetailRepository;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.exception.BaseException;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.requests.GetEventsRequest;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.responses.PaginatedResourcesResponse;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AEvent;
import ninja.cyplay.com.apilibrary.utils.LogUtils;

/**
 * Created by romainlebouc on 26/04/16.
 */
public class EventInteractorImpl extends AbstractInteractor implements EventInteractor {


    private PlayRetailRepository repository;


    public EventInteractorImpl(InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor, PlayRetailRepository repository) {
        super(interactorExecutor, mainThreadExecutor);
        this.repository = repository;
    }

    @Override
    public void getEvents(String sellerId,
                          Date after,
                          Date before,
                          PaginatedResourceRequestCallback<PR_AEvent> callback) {
        final WeakReference<PaginatedResourceRequestCallback<PR_AEvent>> getEventsCallback = new WeakReference<>(callback);
        final WeakReference<GetEventsRequest> getEventsRequest = new WeakReference<>(new GetEventsRequest(sellerId, after, before));
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                try {
                    final PaginatedResourcesResponse<PR_AEvent> getEventsResponse = repository.getEvents(getEventsRequest.get());
                    getMainThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (getEventsCallback.get() != null) {
                                getEventsCallback.get().onSuccess(getEventsResponse.getResults(),
                                        getEventsResponse.getCount(),
                                        getEventsResponse.getPrevious(),
                                        getEventsResponse.getNext());
                            }else {
                                Log.i(EventInteractorImpl.class.getName(),"Request callback is None" );
                            }


                        }
                    });
                } catch (final BaseException e) {
                    Log.e(LogUtils.generateTag(this), "Error on GetEvents interactor");
                    getMainThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (getEventsCallback.get() != null) {
                                getEventsCallback.get().onError(e);
                            }else {
                                Log.i(EventInteractorImpl.class.getName(),"Request callback is None" );
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void getCustomerEvents(String customerId, PaginatedResourceRequestCallback<PR_AEvent> callback) {
        final WeakReference<PaginatedResourceRequestCallback<PR_AEvent>> getCustomerEventsCallback = new WeakReference<>(callback);
        final WeakReference<GetEventsRequest> getCustomerEventsRequest = new WeakReference<>(new GetEventsRequest(customerId, "CUSTOMER"));
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                try {
                    final PaginatedResourcesResponse<PR_AEvent> getEventsResponse = repository.getEvents(getCustomerEventsRequest.get());
                    getMainThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (getCustomerEventsCallback.get() != null) {
                                getCustomerEventsCallback.get().onSuccess(getEventsResponse.getResults(),
                                        getEventsResponse.getCount(),
                                        getEventsResponse.getPrevious(),
                                        getEventsResponse.getNext());
                            }else {
                                Log.i(EventInteractorImpl.class.getName(),"Request callback is None" );
                            }
                        }
                    });
                } catch (final BaseException e) {
                    Log.e(LogUtils.generateTag(this), "Error on getCustomerEvents interactor");
                    getMainThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (getCustomerEventsCallback.get() != null) {
                                getCustomerEventsCallback.get().onError(e);
                            }else {
                                Log.i(EventInteractorImpl.class.getName(),"Request callback is None" );
                            }
                        }
                    });
                }
            }
        });
    }


}
