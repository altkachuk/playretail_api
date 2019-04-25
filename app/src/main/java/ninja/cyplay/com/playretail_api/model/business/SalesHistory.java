package ninja.cyplay.com.playretail_api.model.business;

import android.util.Log;

import org.parceler.Parcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ASalesHistory;

@Parcel
public class SalesHistory extends PR_ASalesHistory {

    private transient final static SimpleDateFormat IN_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    public String date;
    public Integer sta;
    public List<Product> pld;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getSta() {
        return sta;
    }

    public void setSta(Integer sta) {
        this.sta = sta;
    }

    public List<Product> getPl() {
        return pld;
    }

    public Date getDateObject() {
        Date result = null;
        if (date != null) {
            try {
                result = IN_FORMAT.parse(date);
            } catch (ParseException e) {
                Log.e(SalesHistory.class.getName(), e.getMessage());
            }
        }
        return result;

    }

}