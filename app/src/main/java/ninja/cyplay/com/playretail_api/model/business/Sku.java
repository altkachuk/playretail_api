package ninja.cyplay.com.playretail_api.model.business;

import org.parceler.Parcel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by romainlebouc on 08/07/2014.
 */
@Parcel
public class Sku implements Serializable {
    public String skd;
    public String skid;
    public Boolean sks;
    public Double skp;
    public List<Ska> ska;
    public Integer quantity;

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Sku)) return false;
        Sku otherSku= (Sku) other;
        return this.skid.equals(otherSku.skid);
    }

}
