package ninja.cyplay.com.playretail_api.model.business;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ACustomerDetails;

@Parcel
public class CustomerDetails extends PR_ACustomerDetails {

    public String stat;
    public String ab;
    public String ad1;
    public String ad2;
    public String ad3;
    public String name;
    public String EAN;
    public String dob;
    public String pdc;
    public String mn;
    public String pn;
    public String wn;
    public String zc;
    public String cc;
    public String infononachat;
    public String pc;
    public String ctry;
    public String civ;
    public String cit;
    // public Integer ew;
    public String lvd;
    public List<String> tb;
    public String email;
    public List<String> emails;
    public String fn;
    public String lvst;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomerDetails that = (CustomerDetails) o;

        if (stat != null ? !stat.equals(that.stat) : that.stat != null) return false;
        if (ab != null ? !ab.equals(that.ab) : that.ab != null) return false;
        if (ad1 != null ? !ad1.equals(that.ad1) : that.ad1 != null) return false;
        if (ad2 != null ? !ad2.equals(that.ad2) : that.ad2 != null) return false;
        if (ad3 != null ? !ad3.equals(that.ad3) : that.ad3 != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (EAN != null ? !EAN.equals(that.EAN) : that.EAN != null) return false;
        if (dob != null ? !dob.equals(that.dob) : that.dob != null) return false;
        if (pdc != null ? !pdc.equals(that.pdc) : that.pdc != null) return false;
        if (mn != null ? !mn.equals(that.mn) : that.mn != null) return false;
        if (pn != null ? !pn.equals(that.pn) : that.pn != null) return false;
        if (wn != null ? !wn.equals(that.wn) : that.wn != null) return false;
        if (zc != null ? !zc.equals(that.zc) : that.zc != null) return false;
        if (cc != null ? !cc.equals(that.cc) : that.cc != null) return false;
        if (infononachat != null ? !infononachat.equals(that.infononachat) : that.infononachat != null)
            return false;
        if (pc != null ? !pc.equals(that.pc) : that.pc != null) return false;
        if (ctry != null ? !ctry.equals(that.ctry) : that.ctry != null) return false;
        if (civ != null ? !civ.equals(that.civ) : that.civ != null) return false;
        if (cit != null ? !cit.equals(that.cit) : that.cit != null) return false;
        if (lvd != null ? !lvd.equals(that.lvd) : that.lvd != null) return false;
        if (tb != null ? !tb.equals(that.tb) : that.tb != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (emails != null ? !emails.equals(that.emails) : that.emails != null) return false;
        if (fn != null ? !fn.equals(that.fn) : that.fn != null) return false;
        return lvst != null ? lvst.equals(that.lvst) : that.lvst == null;

    }

    @Override
    public int hashCode() {
        int result = stat != null ? stat.hashCode() : 0;
        result = 31 * result + (ab != null ? ab.hashCode() : 0);
        result = 31 * result + (ad1 != null ? ad1.hashCode() : 0);
        result = 31 * result + (ad2 != null ? ad2.hashCode() : 0);
        result = 31 * result + (ad3 != null ? ad3.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (EAN != null ? EAN.hashCode() : 0);
        result = 31 * result + (dob != null ? dob.hashCode() : 0);
        result = 31 * result + (pdc != null ? pdc.hashCode() : 0);
        result = 31 * result + (mn != null ? mn.hashCode() : 0);
        result = 31 * result + (pn != null ? pn.hashCode() : 0);
        result = 31 * result + (wn != null ? wn.hashCode() : 0);
        result = 31 * result + (zc != null ? zc.hashCode() : 0);
        result = 31 * result + (cc != null ? cc.hashCode() : 0);
        result = 31 * result + (infononachat != null ? infononachat.hashCode() : 0);
        result = 31 * result + (pc != null ? pc.hashCode() : 0);
        result = 31 * result + (ctry != null ? ctry.hashCode() : 0);
        result = 31 * result + (civ != null ? civ.hashCode() : 0);
        result = 31 * result + (cit != null ? cit.hashCode() : 0);
        result = 31 * result + (lvd != null ? lvd.hashCode() : 0);
        result = 31 * result + (tb != null ? tb.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (emails != null ? emails.hashCode() : 0);
        result = 31 * result + (fn != null ? fn.hashCode() : 0);
        result = 31 * result + (lvst != null ? lvst.hashCode() : 0);
        return result;
    }

    // response empty constructor for Parceler
    public CustomerDetails() {
    }

    // copy constructor
    public CustomerDetails(CustomerDetails c) {
        this.cit = c.cit;
        this.stat = c.stat;
        this.ab = c.ab;
        this.ad1 = c.ad1;
        this.ad2 = c.ad2;
        this.ad3 = c.ad3;
        this.name = c.name;
        this.EAN = c.EAN;
        this.dob = c.dob;
        this.pdc = c.pdc;
        this.mn = c.mn;
        this.pn = c.pn;
        this.wn = c.wn;
        this.zc = c.zc;
        this.cc = c.cc;
        this.pc = c.pc;
        this.ctry = c.ctry;
        this.civ = c.civ;
        this.lvd = c.lvd;
        // note: instantiating with size() gives `this.tb` enough capacity to hold everything
        if (c.tb != null)
            this.tb = new ArrayList<>(c.tb);
        else
            this.tb = new ArrayList<>();
        this.email = c.email;
        if (c.emails != null)
            this.emails = new ArrayList<>(c.emails);
        else
            this.emails = new ArrayList<>();
        this.fn = c.fn;
        this.lvst = c.lvst;
    }
}
