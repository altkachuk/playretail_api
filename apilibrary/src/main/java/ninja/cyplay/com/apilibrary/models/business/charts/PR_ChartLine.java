package ninja.cyplay.com.apilibrary.models.business.charts;

import ninja.cyplay.com.apilibrary.utils.IOSColorConverter;

public class PR_ChartLine {
    public Float width;
    public Boolean enabled;
    public String color;

    public Float getWidth() {
        return width;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public String getColor() {
        return IOSColorConverter.fromRGBAtoARGB(color);
    }

}

