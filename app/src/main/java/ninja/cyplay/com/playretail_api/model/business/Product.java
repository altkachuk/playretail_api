package ninja.cyplay.com.playretail_api.model.business;

import org.parceler.Parcel;

import java.util.List;

import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AProduct;

@Parcel
public class Product extends PR_AProduct {

    public String bd;
    public String om;
    public String img;

    public String oc;

    public String skd;
    public List<String> imgs;
    public List<Sku> skl;

    public String pn;
    public String skid;
    public String desc;

    public String code;
    public String cat;
    public List<String> rp;

    public Integer sc;

    //new filed
    //TODO check this
    public int family;

    public Product() {}

    public PRODUCT_TYPE product_type;

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Product)) return false;
        Product otherProduct = (Product) other;
        return this.getSkid() != null && this.getSkid().equals(otherProduct.getSkid());
    }
    //Whenever a.equals(b), then a.hashCode() must be same as b.hashCode().
//    @Override
//    public int hashCode() {
//        return this.getSkid();
//    }

    public String getBd() {
        return bd;
    }

    public void setBd(String bd) {
        this.bd = bd;
    }

    public String getOm() {
        return om;
    }

    public void setOm(String om) {
        this.om = om;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getOc() {
        return oc;
    }

    public void setOc(String oc) {
        this.oc = oc;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getSkd() {
        return skd;
    }

    public void setSkd(String skd) {
        this.skd = skd;
    }

    public Integer getSc() {
        return sc;
    }

    public void setSc(Integer sc) {
        this.sc = sc;
    }

    public List<Sku> getSkl() {
        return skl;
    }

    public void setSkl(List<Sku> skl) {
        this.skl = skl;
    }

    public String getPn() {
        return pn;
    }

    public void setPn(String pn) {
        this.pn = pn;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSkid() {
        return skid;
    }

    public void setSkid(String skid) {
        this.skid = skid;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<String> getRp() {
        return rp;
    }

    public void setRp(List<String> rp) {
        this.rp = rp;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }

    // Methods (setter and getters used for product type)

    public void setProduct_type(PRODUCT_TYPE product_type) {
        this.product_type = product_type;
    }

    public boolean isRecoProduct(){
        return this.product_type == PRODUCT_TYPE.PRODUCT_TYPE_RECO;
    }
    public boolean isRecurringProduct(){
        return this.product_type == PRODUCT_TYPE.PRODUCT_TYPE_RECURING;
    }
    public boolean isTopSalesProduct(){
        return this.product_type == PRODUCT_TYPE.PRODUCT_TYPE_TOPSALES;
    }

    // Enum for product types
    public enum PRODUCT_TYPE {
        PRODUCT_TYPE_TOPSALES,
        PRODUCT_TYPE_RECO,
        PRODUCT_TYPE_RECURING
    };

}