package ninja.cyplay.com.apilibrary.models.business.reporting;

import org.parceler.Parcel;

import io.realm.RealmObject;

/**
 * Created by romainlebouc on 22/09/16.
 */
@Parcel
public class ActionEventContentData extends RealmObject {
    protected String k;
    protected String v;

    public ActionEventContentData() {

    }

    public ActionEventContentData(String k, String v) {
        this.k = k;
        this.v = v;
    }

    public String getK() {
        return k;
    }

    public String getV() {
        return v;
    }
}
