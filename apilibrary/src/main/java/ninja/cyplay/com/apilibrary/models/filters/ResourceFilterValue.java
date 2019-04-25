package ninja.cyplay.com.apilibrary.models.filters;

import org.parceler.Parcel;

/**
 * Created by romainlebouc on 05/09/16.
 */
@Parcel
public class ResourceFilterValue {

    protected String label;
    protected Integer count;
    protected String key;
    protected String unit;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getValue(){
        String criteria = label;
        if ( unit !=null){
            criteria += " " + unit;
        }
        return criteria;
    }



    @Override
    public int hashCode() {
        int result = label != null ? label.hashCode() : 0;
        result = 31 * result + (key != null ? key.hashCode() : 0);
        return result;
    }
}
