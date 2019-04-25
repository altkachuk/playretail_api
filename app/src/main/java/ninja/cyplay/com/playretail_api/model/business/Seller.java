package ninja.cyplay.com.playretail_api.model.business;

import org.parceler.Parcel;

import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ASeller;

@Parcel
public class Seller extends PR_ASeller {

    public String fn;
    public String un;
    public String name;
    public String chal;

    public String getInitials() {
        StringBuilder builder = new StringBuilder();
        if (fn != null && fn.length() > 0)
            builder.append(fn.charAt(0));
        if (name != null && name.length() > 0)
            builder.append(name.charAt(0));
        return builder.toString();
    }

    public String getFormatName() {
        StringBuilder builder = new StringBuilder();
        builder.append(fn != null && fn.length() > 0 ? fn + " "  : "");
        builder.append(name != null && name.length() > 0 ? name : "");
        return builder.toString();
    }

    public String getFn() {
        return fn;
    }

    public String getUn() {
        return un;
    }

    public String getName() {
        return name;
    }

    public String getChal() {
        return chal;
    }
}