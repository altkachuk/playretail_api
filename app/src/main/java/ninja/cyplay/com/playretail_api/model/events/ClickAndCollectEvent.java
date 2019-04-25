package ninja.cyplay.com.playretail_api.model.events;

import ninja.cyplay.com.playretail_api.model.business.Product;

/**
 * Created by damien on 18/06/15.
 */
public class ClickAndCollectEvent {

    public ClickAndCollectEvent(Product product) {
        this.product = product;
    }

    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}
