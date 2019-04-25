package ninja.cyplay.com.apilibrary.models.business.reporting;

import io.realm.RealmObject;

public class ReportData extends RealmObject implements ReportingResource {

    String report_id;
    Double total_time;
    int syncRetryCount;

    public ReportData() { }

    public ReportData(String report_id, Double total_time) {
        this.total_time = total_time;
        this.report_id = report_id;
    }

    public String getReport_id() {
        return report_id;
    }

    public double getTotal_time() {
        return total_time;
    }

    public int getSyncRetryCount() {
        return syncRetryCount;
    }

    public int incSyncRetryCount(){
        syncRetryCount =  syncRetryCount+1;
        return syncRetryCount;
    }

}





