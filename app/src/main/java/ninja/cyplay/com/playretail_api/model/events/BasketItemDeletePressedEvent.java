package ninja.cyplay.com.playretail_api.model.events;

import ninja.cyplay.com.apilibrary.models.business.PR_BasketItem;

/**
 * Created by damien on 26/05/15.
 */
public class BasketItemDeletePressedEvent {

    PR_BasketItem item;

    public BasketItemDeletePressedEvent(PR_BasketItem item) {
        this.item = item;
    }

    public PR_BasketItem getItem() {
        return item;
    }

    public void setItem(PR_BasketItem item) {
        this.item = item;
    }
}
