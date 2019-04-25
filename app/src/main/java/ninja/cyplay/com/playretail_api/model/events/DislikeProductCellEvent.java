package ninja.cyplay.com.playretail_api.model.events;

import ninja.cyplay.com.playretail_api.model.business.Product;

/**
 * Created by damien on 19/05/15.
 */
public class DislikeProductCellEvent {

    private Product product;

    public DislikeProductCellEvent(Product product) {
        this.product = product;
    }

    public Product getPRProduct() {
        return product;
    }

    public void setPRProduct(Product product) {
        this.product = product;
    }
}
