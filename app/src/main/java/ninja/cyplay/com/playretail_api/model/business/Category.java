package ninja.cyplay.com.playretail_api.model.business;

import org.parceler.Parcel;

import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ACategory;

/**
 * Created by romainlebouc on 12/07/2014.
 */
@Parcel
public class Category extends PR_ACategory {

    String cat_code;
    String cat_type;
    String cat_lab;
    String cat_image;

    public String getCat_code() {
        return cat_code;
    }

    public void setCat_code(String cat_code) {
        this.cat_code = cat_code;
    }

    public String getCat_type() {
        return cat_type;
    }

    public void setCat_type(String cat_type) {
        this.cat_type = cat_type;
    }

    public String getCat_lab() {
        return cat_lab;
    }

    public void setCat_lab(String cat_lab) {
        this.cat_lab = cat_lab;
    }

    public String getCat_image() {
        return cat_image;
    }

    public void setCat_image(String cat_image) {
        this.cat_image = cat_image;
    }

}
