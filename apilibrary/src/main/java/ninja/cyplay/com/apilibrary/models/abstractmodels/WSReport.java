package ninja.cyplay.com.apilibrary.models.abstractmodels;

import java.util.ArrayList;
import java.util.List;

import ninja.cyplay.com.apilibrary.models.APIResource;
import ninja.cyplay.com.apilibrary.models.business.reporting.ReportData;

/**
 * Created by romainlebouc on 29/08/16.
 */
@APIResource(name = WSReport.API_RESOURCE_NAME)
public class WSReport {

    public final static transient String API_RESOURCE_NAME = "wsreport";
    public final static transient String REPORT_ID_KEY = "report_id";

    String id;
    Double total_time;

    public WSReport() { }

    public WSReport(String id, Double total_time) {
        this.total_time = total_time;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getTotal_time() {
        return total_time;
    }

    public void setTotal_time(double total_time) {
        this.total_time = total_time;
    }

    public static List<WSReport> getListFromReportDatas(List<ReportData> reportDatas){
        List<WSReport> result = new ArrayList<>();
        if ( reportDatas !=null){
            for (ReportData reportData : reportDatas){
                if ( reportData.getReport_id() !=null){
                    result.add(new WSReport(reportData.getReport_id(), reportData.getTotal_time()));
                }
            }
        }
        return result;
    }

}
