package ninja.cyplay.com.apilibrary.models.business;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by damien on 07/01/16.
 */
public class FormField {

    @SerializedName("default")
    public String def;
    @SerializedName("create")
    public int createFlags;
    @SerializedName("edit")
    public int editFlags;

    public String label;
    public String tag;
    public List<String> values;
    public String type;
    public int recordableOnce;

    public String getDef() {
        return def;
    }

    public int getCreateFlags() {
        return createFlags;
    }

    public int getEditFlags() {
        return editFlags;
    }

    public String getLabel() {
        return label;
    }

    public String getTag() {
        return tag;
    }

    public List<String> getValues() {
        return values;
    }

    public String getType() {
        return type;
    }

    public int getRecordableOnce() {
        return recordableOnce;
    }
}
