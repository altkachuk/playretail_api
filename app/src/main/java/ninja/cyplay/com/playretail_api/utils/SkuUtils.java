package ninja.cyplay.com.playretail_api.utils;

import java.text.DecimalFormat;

import ninja.cyplay.com.playretail_api.model.business.Ska;
import ninja.cyplay.com.playretail_api.model.business.Sku;
import ninja.cyplay.com.apilibrary.utils.Constants;

/**
 * Created by damien on 10/06/15.
 */
public class SkuUtils {

    public static Double getSkpForShopId(Sku sku, String store_id) {
        if (sku.skp != null)
            return sku.skp / Constants.PRICE_DIVISOR; //TODO Demo purpose only
        if (sku.ska != null) {
            for (int i = 0 ; i < sku.ska.size() ; i++) {
                Ska ska = sku.ska.get(i);
                if (ska.shid != null && ska.skp != null && store_id != null
                        && String.valueOf(ska.shid).equals(store_id)) {
                    return ska.skp / Constants.PRICE_DIVISOR;
                }
            }
        }
        return 0d;
    }

    public static String getSksForShopId(Sku sku, String store_id) {
        if (sku.ska != null) {
            for (int i = 0 ; i < sku.ska.size() ; i++) {
                Ska ska = sku.ska.get(i);
                if (ska.shid != null && ska.sks != null && store_id != null
                        && String.valueOf(ska.shid).equals(store_id)) {
                    return ska.sks;
                }
            }
        }
        return null;
    }

    public final static DecimalFormat priceFormat = new DecimalFormat("0.00##");


    public static Double getPriceForSku(Sku sku) {
        String store_id = null;
        if (ConfigHelper.getInstance() != null
                && ConfigHelper.getInstance().getConfig() != null
                && ConfigHelper.getInstance().getConfig().getPRShopConfig() != null
                && ConfigHelper.getInstance().getConfig().getPRShopConfig().store_id != null)
            store_id = ConfigHelper.getInstance().getConfig().getPRShopConfig().store_id;
        if (store_id != null) {
            if (sku.skp != null)
                return (sku.skp / Constants.PRICE_DIVISOR);
            int skp = SkuUtils.getSkpForShopId(sku, store_id).intValue();
            if (skp > 0)
                return (double) (skp / Constants.PRICE_DIVISOR);
        }
        return 0D;
    }
}
