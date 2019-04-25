package ninja.cyplay.com.apilibrary.domain.interactor;

import java.util.List;

import ninja.cyplay.com.apilibrary.domain.interactor.callback.RequestCallback;
import ninja.cyplay.com.apilibrary.models.business.PR_Chart;

/**
 * Created by damien on 30/05/16.
 */
public interface ShopStatisticsInteractor {

    void getStats(final GetStatsCallback callback);

    interface GetStatsCallback extends RequestCallback {

        void onSuccess(List<PR_Chart> charts);

    }

}
