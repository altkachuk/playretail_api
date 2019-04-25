package ninja.cyplay.com.apilibrary.models.business;

import java.util.HashMap;

import ninja.cyplay.com.apilibrary.models.business.charts.PR_ChartAxis;
import ninja.cyplay.com.apilibrary.models.business.charts.PR_ChartLabel;
import ninja.cyplay.com.apilibrary.models.business.charts.PR_ChartLegend;
import ninja.cyplay.com.apilibrary.models.business.charts.PR_ChartOffset;
import ninja.cyplay.com.apilibrary.models.business.charts.PR_ChartPieHole;
import ninja.cyplay.com.apilibrary.utils.IOSColorConverter;

/**
 * Created by damien on 30/05/16.
 */
public class PR_Chart {

    public String title;
    public String type;

    public PR_ChartPieHole hole;

    public PR_ChartAxis rightAxis;
    public PR_ChartAxis leftAxis;
    public PR_ChartAxis xAxis;

    public Boolean drawValueAboveBar;
    public Boolean interactionEnabled;
    public String gridBackgroundColor;
    public Boolean showsFilterButtons;
    public String detailedDescription;

    public PR_ChartLabel noDataLabel;
    public Integer maxFractionDigits;
    public String noDataMessage;
    public String subtitle;
    public PR_ChartOffset offsets;
    public String backgroundColor;
    public PR_ChartLegend legend;

    public HashMap<String, PR_DataSet> datasets;

    // -------------------------------------------------------------------------------------------
    //
    // -------------------------------------------------------------------------------------------

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public PR_ChartPieHole getHole() {
        return hole;
    }

    public PR_ChartAxis getRightAxis() {
        return rightAxis;
    }

    public PR_ChartAxis getLeftAxis() {
        return leftAxis;
    }

    public PR_ChartAxis getxAxis() {
        return xAxis;
    }

    public Boolean getDrawValueAboveBar() {
        return drawValueAboveBar;
    }

    public Boolean getInteractionEnabled() {
        return interactionEnabled;
    }

    public String getGridBackgroundColor() {
        return gridBackgroundColor;
    }

    public Boolean getShowsFilterButtons() {
        return showsFilterButtons;
    }

    public String getDetailedDescription() {
        return detailedDescription;
    }

    public PR_ChartLabel getNoDataLabel() {
        return noDataLabel;
    }

    public Integer getMaxFractionDigits() {
        return maxFractionDigits;
    }

    public String getNoDataMessage() {
        return noDataMessage;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public PR_ChartOffset getOffsets() {
        return offsets;
    }

    public String getBackgroundColor() {
        return IOSColorConverter.fromRGBAtoARGB(backgroundColor);
    }

    public PR_ChartLegend getLegend() {
        return legend;
    }

    public HashMap<String, PR_DataSet> getDatasets() {
        return datasets;
    }
}
