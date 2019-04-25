package ninja.cyplay.com.playretail_api.model.business;

import android.content.Context;

import org.parceler.Parcel;

import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ACustomerPreview;
import ninja.cyplay.com.playretail_api.utils.StringUtils;

@Parcel
public class CustomerPreview extends PR_ACustomerPreview {

    public String cid;

    public CustomerPreviewDetails cd = new CustomerPreviewDetails();

    public String getFormatName() {
        StringBuilder builder = new StringBuilder();
        if (cd != null) {
            builder.append(cd.civ != null && cd.civ.length()  > 0   ? StringUtils.getStringResourceByName(cd.civ) + " "  : "");
            builder.append(cd.civ != null && cd.fn.length()   > 0   ? cd.fn  + " "  : "");
            builder.append(cd.civ != null && cd.name.length() > 0   ? cd.name       : "");
        }
        return builder.toString();
    }

    public String getEAN() {
        if (cd != null)
            return cd.EAN;
        return null;
    }

    public CustomerPreview() {}

    public CustomerPreview(String lastName, String EAN, String cit, String firstName, String zc, String address, String civility, String ctry) {
        this.cd.name = lastName;
        this.cd.EAN = EAN;
        this.cd.cit = cit;
        this.cd.fn = firstName;
        this.cd.zc = zc;
        this.cd.ad3 = address;
        this.cd.civ= civility;
        this.cd.ctry = ctry;
    }

    public CustomerPreview(String cid, CustomerDetails PRCustomerDetails) {
        this.cid = cid;
        this.cd.name = PRCustomerDetails.name;
        this.cd.EAN = PRCustomerDetails.EAN;
        this.cd.cit = PRCustomerDetails.cit;
        this.cd.fn = PRCustomerDetails.fn;
        this.cd.zc = PRCustomerDetails.zc;
        this.cd.ad3 = PRCustomerDetails.ad3;
        this.cd.civ= PRCustomerDetails.civ;
        this.cd.ctry = PRCustomerDetails.ctry;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }
}



