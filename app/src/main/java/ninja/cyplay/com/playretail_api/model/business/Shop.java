package ninja.cyplay.com.playretail_api.model.business;

import org.parceler.Parcel;

import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AShop;

@Parcel
public class Shop extends PR_AShop {

    String store_id;
    String name;
    String country;
    String adr;
    Double lat;
    Double lon;

    public String getStid() {
        return store_id;
    }

    public String getCountry() {
        return country;
    }

    public String getName() {
        return name;
    }

    public String getAdr() {
        return adr;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }
}