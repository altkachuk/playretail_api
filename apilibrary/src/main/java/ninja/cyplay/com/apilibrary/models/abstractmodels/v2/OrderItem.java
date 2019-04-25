package ninja.cyplay.com.apilibrary.models.abstractmodels.v2;

/**
 * Created by andre on 23-Mar-19.
 */

public class OrderItem {

    String reference;
    Double price;
    Integer quantity;

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
