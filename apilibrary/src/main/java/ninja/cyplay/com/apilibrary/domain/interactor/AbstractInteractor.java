package ninja.cyplay.com.apilibrary.domain.interactor;


import android.util.Log;

import ninja.cyplay.com.apilibrary.domain.executor.InteractorExecutor;
import ninja.cyplay.com.apilibrary.domain.executor.MainThreadExecutor;
import ninja.cyplay.com.apilibrary.domain.interactor.callback.FilteredPaginatedResourceRequestCallback;
import ninja.cyplay.com.apilibrary.domain.interactor.callback.PaginatedResourceRequestCallback;
import ninja.cyplay.com.apilibrary.domain.interactor.callback.ResourceRequestCallback;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.exception.BaseException;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.exception.ExceptionType;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.requests.ResourceRequest;

import ninja.cyplay.com.apilibrary.domain.repositoryModel.responses.FilteredPaginatedResourcesResponse;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.responses.PaginatedResourcesResponse;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.responses.ResourceResponse;


/**
 * @author glomadrian
 */
public abstract class AbstractInteractor {

    private InteractorExecutor interactorExecutor;
    private MainThreadExecutor mainThreadExecutor;

    public AbstractInteractor(InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor) {
        this.interactorExecutor = interactorExecutor;
        this.mainThreadExecutor = mainThreadExecutor;
    }

    public InteractorExecutor getInteractorExecutor() {
        return interactorExecutor;
    }

    public void setInteractorExecutor(InteractorExecutor interactorExecutor) {
        this.interactorExecutor = interactorExecutor;
    }

    public MainThreadExecutor getMainThreadExecutor() {
        return mainThreadExecutor;
    }

    public void setMainThreadExecutor(MainThreadExecutor mainThreadExecutor) {
        this.mainThreadExecutor = mainThreadExecutor;
    }

    public void doGetPaginatedResource(ResourceRequest resourceRequest,
                                       final PaginatedResourceRequestCallback paginatedResourceRequestCallback,
                                       PaginatedResourceGetter paginatedResourceGetter) {
        try {
            final PaginatedResourcesResponse paginatedResourcesResponse = paginatedResourceGetter.getPaginatedResource(resourceRequest);
            getMainThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    if (paginatedResourceRequestCallback != null) {
                        paginatedResourceRequestCallback.onSuccess(paginatedResourcesResponse.getResults(),
                                paginatedResourcesResponse.getCount(),
                                paginatedResourcesResponse.getPrevious(),
                                paginatedResourcesResponse.getNext());
                    } else {
                        Log.i(AbstractInteractor.class.getName(), "Request callback is None");
                    }
                }
            });
        } catch (final BaseException e) {
            getMainThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    if (paginatedResourceRequestCallback != null) {
                        paginatedResourceRequestCallback.onError(e);
                    } else {
                        Log.i(AbstractInteractor.class.getName(), "Request callback is None");
                    }


                }
            });
        }
    }


    public void doGetFilteredPaginatedResource(ResourceRequest resourceRequest,
                                               final FilteredPaginatedResourceRequestCallback paginatedResourceRequestCallback,
                                               FilteredPaginatedResourceGetter paginatedResourceGetter) {
        try {
            final FilteredPaginatedResourcesResponse filteredPaginatedResourcesResponse = paginatedResourceGetter.getFilteredPaginatedResource(resourceRequest);
            getMainThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    if (paginatedResourceRequestCallback != null) {

                        paginatedResourceRequestCallback.onSuccess(filteredPaginatedResourcesResponse.getResults(),
                                filteredPaginatedResourcesResponse.getCount(),
                                filteredPaginatedResourcesResponse.getPrevious(),
                                filteredPaginatedResourcesResponse.getNext(),
                                filteredPaginatedResourcesResponse.getFilters());
                    } else {
                        Log.i(AbstractInteractor.class.getName(), "Request callback is None");
                    }

                }
            });
        } catch (final BaseException e) {
            getMainThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    if (paginatedResourceRequestCallback != null) {
                        paginatedResourceRequestCallback.onError(e);
                    } else {
                        Log.i(AbstractInteractor.class.getName(), "Request callback is None");
                    }

                }
            });
        }
    }

    public void doGetResource(ResourceRequest resourceRequest,
                              final ResourceRequestCallback resourceRequestCallback,
                              ResourceGetter resourceGetter) {
        try {
            final ResourceResponse resourceResponse = resourceGetter.getResource(resourceRequest);

            getMainThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    if (resourceRequestCallback != null) {
                        resourceRequestCallback.onSuccess(resourceResponse.getResource());
                    } else {
                        Log.i(AbstractInteractor.class.getName(), "Request callback is None");
                    }
                }
            });
        } catch (final BaseException e) {
            getMainThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    if (resourceRequestCallback != null) {
                        resourceRequestCallback.onError(e);
                    } else {
                        Log.i(AbstractInteractor.class.getName(), "Request callback is None");
                    }
                }
            });
        }

    }

    public void doAddResource(ResourceRequest resourceRequest,
                              final ResourceRequestCallback resourceRequestCallback,
                              ResourceGetter resourceGetter) {
        try {
            final ResourceResponse resourceResponse = resourceGetter.getResource(resourceRequest);
            getMainThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    if (resourceRequestCallback != null) {
                        resourceRequestCallback.onSuccess(resourceResponse.getResource());
                    } else {
                        Log.i(AbstractInteractor.class.getName(), "Request callback is None");
                    }
                }
            });
        } catch (final BaseException e) {
            getMainThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    if (resourceRequestCallback != null) {
                        resourceRequestCallback.onError(e);
                    } else {
                        Log.i(AbstractInteractor.class.getName(), "Request callback is None");
                    }
                }
            });
        }


    }

    public void doDeleteResource(ResourceRequest resourceRequest,
                              final ResourceRequestCallback resourceRequestCallback,
                              ResourceGetter resourceGetter) {
        try {
            final ResourceResponse resourceResponse = resourceGetter.getResource(resourceRequest);
            getMainThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    if (resourceRequestCallback != null) {
                        resourceRequestCallback.onSuccess(resourceResponse.getResource());
                    } else {
                        Log.i(AbstractInteractor.class.getName(), "Request callback is None");
                    }
                }
            });
        } catch (final BaseException e) {
            getMainThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    if (resourceRequestCallback != null) {
                        resourceRequestCallback.onError(e);
                    } else {
                        Log.i(AbstractInteractor.class.getName(), "Request callback is None");
                    }
                }
            });
        }


    }


    public void doUpdateResource(ResourceRequest resourceRequest,
                                 final ResourceRequestCallback resourceRequestCallback,
                                 ResourceGetter resourceGetter) {
        try {
            final ResourceResponse resourceResponse = resourceGetter.getResource(resourceRequest);
            getMainThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    if (resourceRequestCallback != null) {
                        resourceRequestCallback.onSuccess(resourceResponse.getResource());
                    } else {
                        Log.i(AbstractInteractor.class.getName(), "Request callback is None");
                    }
                }
            });
        } catch (final BaseException e) {
            getMainThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    if (resourceRequestCallback != null) {
                        resourceRequestCallback.onError(e);
                    } else {
                        Log.i(AbstractInteractor.class.getName(), "Request callback is None");
                    }
                }
            });
        }


    }

}
