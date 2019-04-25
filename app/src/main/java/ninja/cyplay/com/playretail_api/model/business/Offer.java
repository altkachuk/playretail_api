package ninja.cyplay.com.playretail_api.model.business;

import android.util.Log;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AOffer;
import ninja.cyplay.com.apilibrary.utils.LogUtils;

public class Offer extends PR_AOffer {

    private transient final static SimpleDateFormat IN_FORMAT = new SimpleDateFormat("yyyyMMdd");

    public Integer sc;
    public String ed;
    public Integer cnt;
    public Integer ot;
    public String sd;
    public String title;
    public String desc;

    public Date getEdDateObject() {
        Date result = null;
        if (ed != null) {
            try {
                result = IN_FORMAT.parse(ed);
            } catch (ParseException e) {
                Log.e(LogUtils.generateTag(this), e.getMessage());
            }
        }
        return result;
    }

    public Date getSdDateObject() {
        Date result = null;
        if (sd != null) {
            try {
                result = IN_FORMAT.parse(sd);
            } catch (ParseException e) {
                Log.e(LogUtils.generateTag(this), e.getMessage());
            }
        }
        return result;
    }
}
