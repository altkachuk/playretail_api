package ninja.cyplay.com.apilibrary.models.abstractmodels.v2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andre on 15-Mar-19.
 */

public class Contact {

    String id;
    List<Company> company;
    Country country;
    List<ContactImage> images;
    String first_name;
    String last_name;
    String code;
    String source;
    String email;
    String phone;
    String address1;
    String address2;
    String zip;
    String city;
    String state;
    String comment;
    List<Wishlist> wishlist;

    // -------------------------------------------------------------------------------------------
    // Export Data

    List<Order> orders;
    List<String> wishes;
    Boolean is_updated;

    // Export Data
    // -------------------------------------------------------------------------------------------

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Company> getCompany() {
        return company;
    }

    public void setCompany(List<Company> company) {
        this.company = company;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public List<ContactImage> getImages() {
        return images;
    }

    public void setImages(List<ContactImage> images) {
        this.images = images;
    }

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String firstName) {
        this.first_name = firstName;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String lastName) {
        this.last_name = lastName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<Wishlist> getWishlist() {
        return wishlist;
    }

    public void setWishlist(List<Wishlist> wishlist) {
        this.wishlist = wishlist;
    }

    public List<Order> getOrders() {
        if (orders == null)
            return new ArrayList<>();
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<String> getWishes() {
        return wishes;
    }

    public void setWishes(List<String> wishes) {
        this.wishes = wishes;
    }

    public Boolean getIsUpdated() {
        return is_updated;
    }

    public void setIsUpdated(Boolean isUpdated) {
        this.is_updated = isUpdated;
    }
}
