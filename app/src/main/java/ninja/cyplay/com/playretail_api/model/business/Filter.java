package ninja.cyplay.com.playretail_api.model.business;

import org.parceler.Parcel;

import java.util.List;

import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AFilter;

/**
 * Created by anishosni on 10/06/2015.
 */
@Parcel
public class Filter extends PR_AFilter {

    List<String> values;
    String type;
    String spec;
    String display_name;

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

}

