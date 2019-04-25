package ninja.cyplay.com.apilibrary.domain.interactor;

import java.util.List;

import ninja.cyplay.com.apilibrary.domain.interactor.callback.RequestCallback;
import ninja.cyplay.com.apilibrary.models.abstractmodels.WSReport;

/**
 * Created by damien on 25/05/15.
 */
public interface ReportingInteractor {

    void saveWebServiceTimes(List<WSReport> wsReports, final saveDisplayTimesCallback callback);

    interface saveDisplayTimesCallback  extends RequestCallback {

        void onSuccess(List<String> ids);

    }

}
