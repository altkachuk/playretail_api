package ninja.cyplay.com.playretail_api.model.business;

import org.parceler.Parcel;

import java.io.Serializable;

/**
 * Created by damien on 25/02/15.
 */
@Parcel
public class Ska implements Serializable {

    public String sks;
    public Double skp;
    public Integer shid;

//            "sks":"1",
//            "skp":550,
//            "shid":1

    //TODO Remove this if not demo
    public Float lat;
    public Float lon;
    public String adr;
    public String name;
    public Integer dat;
}
