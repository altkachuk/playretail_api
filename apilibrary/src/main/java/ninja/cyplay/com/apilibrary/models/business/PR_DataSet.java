package ninja.cyplay.com.apilibrary.models.business;

import java.util.ArrayList;
import java.util.List;

import ninja.cyplay.com.apilibrary.models.business.charts.PR_ChartLabel;
import ninja.cyplay.com.apilibrary.utils.IOSColorConverter;

/**
 * Created by damien on 30/05/16.
 */
public class PR_DataSet {

    public List<String> colors;
    public PR_ChartLabel label;
    public ArrayList<String> xVals;
    public ArrayList<Integer> yVals;

    public Boolean drawCircleHole;
    public Float cubicIntensity;
    public Integer lineWidth;

    public List<String> getColors() {
        List<String> fixedColors = new ArrayList<>();
        for (int i = 0 ; colors != null && i < colors.size() ; i++)
            fixedColors.add(IOSColorConverter.fromRGBAtoARGB(colors.get(i)));
        return fixedColors;
    }

    public PR_ChartLabel getLabel() {
        return label;
    }

    public ArrayList<String> getxVals() {
        return xVals;
    }

    public ArrayList<Integer> getyVals() {
        return yVals;
    }

}
