package ninja.cyplay.com.apilibrary.models.business.charts;

import ninja.cyplay.com.apilibrary.utils.IOSColorConverter;

public class PR_ChartLabel {

    public String font;
    public Boolean enabled;
    public String fontSize;
    public String color;
    public Integer width;
    public Integer height;
    public Integer labelsToSkip;
    public String position;
    public Integer spaceBetweenLabels;

    public String getFont() {
        return font;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public String getFontSize() {
        return fontSize;
    }

    public String getColor() {
        return IOSColorConverter.fromRGBAtoARGB(color);
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getLabelsToSkip() {
        return labelsToSkip;
    }

    public String getPosition() {
        return position;
    }

    public Integer getSpaceBetweenLabels() {
        return spaceBetweenLabels;
    }
}
