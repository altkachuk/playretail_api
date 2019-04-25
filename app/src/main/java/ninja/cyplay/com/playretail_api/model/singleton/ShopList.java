package ninja.cyplay.com.playretail_api.model.singleton;

import java.util.List;

import ninja.cyplay.com.playretail_api.model.business.Shop;

/**
 * Created by damien on 10/06/15.
 */
public class ShopList {

    private List<Shop> shops;

    public List<Shop> getShops() {
        return shops;
    }

    public void setShops(List<Shop> shops) {
        this.shops = shops;
    }

    public Shop getShopFromId(String shop_id) {
        if (shops != null) {
            for (int i = 0 ; i < shops.size() ; i++) {
                if (shops.get(i).getStid() != null)
                    if (shops.get(i).getStid().equals(shop_id))
                        return shops.get(i);
            }
        }
        return null;
    }

}
