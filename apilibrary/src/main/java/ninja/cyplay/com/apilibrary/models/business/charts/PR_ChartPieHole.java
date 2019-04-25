package ninja.cyplay.com.apilibrary.models.business.charts;

import ninja.cyplay.com.apilibrary.utils.IOSColorConverter;

public class PR_ChartPieHole {

        public Boolean enabled;
        public Float radiusPercent;
        public String color;

        public Boolean getEnabled() {
            return enabled;
        }

        public Float getRadiusPercent() {
            return radiusPercent;
        }

        public String getColor() {
            return IOSColorConverter.fromRGBAtoARGB(color);
        }
    }