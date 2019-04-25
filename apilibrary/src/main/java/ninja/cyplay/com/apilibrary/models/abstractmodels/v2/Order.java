package ninja.cyplay.com.apilibrary.models.abstractmodels.v2;

import java.util.List;

/**
 * Created by andre on 23-Mar-19.
 */

public class Order {

    String date_created;
    List<OrderItem> items;

    public String getDateCreated() {
        return date_created;
    }

    public void setDateCreated(String dateCreated) {
        this.date_created = dateCreated;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
}
