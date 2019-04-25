package ninja.cyplay.com.playretail_api.ui.presenter;

import java.util.List;

import ninja.cyplay.com.apilibrary.models.business.SugarORM.ReportData;
import ninja.cyplay.com.playretail_api.ui.view.SaveReportingWebServicesView;

/**
 * Created by damien on 26/05/15.
 */
public interface SaveReportingWebServicesPresenter  extends Presenter<SaveReportingWebServicesView> {

    public void saveReportingWebServices(List<ReportData> reportDataList);
}

