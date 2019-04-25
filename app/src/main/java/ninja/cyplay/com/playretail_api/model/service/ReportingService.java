
package ninja.cyplay.com.playretail_api.model.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ninja.cyplay.com.apilibrary.MVPCleanArchitectureApplication;
import ninja.cyplay.com.apilibrary.models.business.SugarORM.PageView;
import ninja.cyplay.com.apilibrary.models.business.SugarORM.ReportData;
import ninja.cyplay.com.apilibrary.utils.Constants;
import ninja.cyplay.com.apilibrary.utils.LogUtils;
import ninja.cyplay.com.playretail_api.ui.presenter.SavePagesViewsPresenter;
import ninja.cyplay.com.playretail_api.ui.presenter.SaveReportingWebServicesPresenter;
import ninja.cyplay.com.playretail_api.ui.view.SavePagesViewsView;
import ninja.cyplay.com.playretail_api.ui.view.SaveReportingWebServicesView;

/**
 * Created by damien on 25/05/15.
 */
public class ReportingService extends Service implements SavePagesViewsView, SaveReportingWebServicesView {

    @Inject
    SavePagesViewsPresenter savePagesViewsPresenter;

    @Inject
    SaveReportingWebServicesPresenter reportingWebServicesPresenter;

    private List<PageView> pagesViewsList = new ArrayList<>();

    private List<ReportData> reportDataList= new ArrayList<>();

    // Create the Handler object§§
    private Handler handler = new Handler();

    public ReportingService() {
    }

    // -------------------------------------------------------------------------------------------
    //                                      Lifecycle
    // -------------------------------------------------------------------------------------------

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LogUtils.generateTag(this), "Service Start");
        // Execute a runnable task as soon as possible
        handler.post(runnableCode);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Dagger Injections
        ((MVPCleanArchitectureApplication) getApplication()).inject(this);
        savePagesViewsPresenter.setView(this);
        reportingWebServicesPresenter.setView(this);
    }

    @Override
    public void onDestroy () {
        handler.removeCallbacks(runnableCode);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    private Runnable runnableCode = new Runnable() {
        @Override
        public void run() {
            Log.i(LogUtils.generateTag(this), "Reporting running");

            // do Reporting
            try {
                savePagesViews();
                saveReportingWebService();
            } catch (Exception e) {}

            // Repeat this runnable code block again every 1 minute
            // Define the task to be run here
            handler.postDelayed(runnableCode, Constants.SYNC_PAGEVIEW_DELAY);
        }
    };

    private void savePagesViews() {
        pagesViewsList = PageView.listAll(PageView.class);
        if (pagesViewsList != null && pagesViewsList.size() > 0) {
            for (int i = 0; i < pagesViewsList.size(); i++)
                pagesViewsList.get(i).deSerializeFields();
            savePagesViewsPresenter.savePagesViews(pagesViewsList);
        }
        Log.i(LogUtils.generateTag(this), "Reporting PagesViews - " + pagesViewsList.size() + " items");
    }

    private void saveReportingWebService() {
        reportDataList = ReportData.listAll(ReportData.class);
        if (reportDataList != null && reportDataList.size() > 0) {
            for (int i = 0; i < reportDataList.size(); i++) {
                ReportData reportData = reportDataList.get(i);
                if (reportData.getReport_id() == null || reportData.getReport_id().length() == 0) {
                    reportDataList.remove(reportData);
                    reportData.delete();
                }
            }
            reportingWebServicesPresenter.saveReportingWebServices(reportDataList);
        }
        Log.i(LogUtils.generateTag(this), "Reporting WebServices - " + reportDataList.size() + " items");
    }

    private void clearPageView() {
        if (pagesViewsList != null && pagesViewsList.size() > 0) {
            for (int i = 0; i < pagesViewsList.size(); i++)
                pagesViewsList.get(i).delete();
            pagesViewsList.clear();
        }
    }

    private void clearReportingWebSercices() {
        if (reportDataList != null && reportDataList.size() > 0) {
            for (int i = 0; i < reportDataList.size(); i++)
                reportDataList.get(i).delete();
            reportDataList.clear();
        }
    }

    // -------------------------------------------------------------------------------------------
    //                                      View Impl
    // -------------------------------------------------------------------------------------------

    @Override
    public void onSavingPagesViewsSuccess() {
        clearPageView();
    }

    @Override
    public void onSavingPagesViewsError() {

    }

    @Override
    public void onSavingReportingWebServicesSuccess() {
        clearReportingWebSercices();
    }

    @Override
    public void onSavingReportingWebServicesError() {

    }

    @Override
    public void displaySttPopUp(String message, String stt) {

    }

    @Override
    public void displayPopUp(String message) {

    }

    @Override
    public void onError() {

    }
}