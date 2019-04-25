package ninja.cyplay.com.apilibrary.domain.interactor;

import android.util.Log;

import java.lang.ref.WeakReference;

import ninja.cyplay.com.apilibrary.domain.executor.InteractorExecutor;
import ninja.cyplay.com.apilibrary.domain.executor.MainThreadExecutor;
import ninja.cyplay.com.apilibrary.domain.repository.PlayRetailRepository;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.exception.BaseException;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.requests.ShopStatisticsRequest;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.responses.ShopStatisticsResponse;
import ninja.cyplay.com.apilibrary.utils.LogUtils;

/**
 * Created by damien on 30/05/16.
 */
public class ShopStatisticsInteractorImpl extends AbstractInteractor implements ShopStatisticsInteractor {

    private PlayRetailRepository repository;

    public ShopStatisticsInteractorImpl(InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor, PlayRetailRepository repository) {
        super(interactorExecutor, mainThreadExecutor);
        this.repository = repository;
    }


    // -------------------------------------------------------------------------------------------
    //
    // -------------------------------------------------------------------------------------------


    @Override
    public void getStats(GetStatsCallback callback) {
        final WeakReference<ShopStatisticsInteractor.GetStatsCallback> statsCallback = new WeakReference<GetStatsCallback>(callback);
        final WeakReference<ShopStatisticsRequest> shopStatisticsRequest = new WeakReference<ShopStatisticsRequest>(new ShopStatisticsRequest());
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                try {
                    final ShopStatisticsResponse shopStatisticsResponse = repository.getShopStatistics(shopStatisticsRequest.get());
                    getMainThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (statsCallback.get() != null) {
                                statsCallback.get().onSuccess(shopStatisticsResponse.getValues());
                            } else {
                                Log.i(ShopStatisticsInteractorImpl.class.getName(), "Request callback is None");
                            }
                        }
                    });
                } catch (final BaseException e) {
                    Log.e(LogUtils.generateTag(this), "Error on accessToken interactor");
                    getMainThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (statsCallback.get() != null) {
                                statsCallback.get().onError(e);
                            } else {
                                Log.i(ShopStatisticsInteractorImpl.class.getName(), "Request callback is None");
                            }
                        }
                    });
                }
            }
        });
    }

}
