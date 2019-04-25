package ninja.cyplay.com.playretail_api.model.singleton;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ninja.cyplay.com.apilibrary.MVPCleanArchitectureApplication;
import ninja.cyplay.com.apilibrary.models.business.PR_Basket;
import ninja.cyplay.com.apilibrary.models.business.PR_BasketItem;
import ninja.cyplay.com.playretail_api.model.business.CustomerPreview;
import ninja.cyplay.com.playretail_api.model.business.CustomerDetails;
import ninja.cyplay.com.playretail_api.model.business.Seller;
import ninja.cyplay.com.playretail_api.model.business.Sku;
import ninja.cyplay.com.apilibrary.models.singleton.TweetCacheManager;
import ninja.cyplay.com.playretail_api.model.business.Product;

/**
 * Created by damien on 29/04/15.
 */
public class SellerContext {

    @Inject
    TweetCacheManager tweetCacheManager;

    private Seller seller;

    private List<CustomerPreview> customer_history = new ArrayList<>();

    private PR_Basket basket;

    public SellerContext(Context context) {
        ((MVPCleanArchitectureApplication)context).inject(this);
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
        tweetCacheManager.setCurrentUserName(seller.getUn());
    }

    public List<CustomerPreview> getCustomer_history() {
        return customer_history;
    }

    public List<String> getCustomer_historyNames() {
        List<String> names = new ArrayList<String>();
        if (customer_history != null) {
            for (CustomerPreview cp : customer_history) {
                names.add(cp.getFormatName());
            }
        }
        return names;
    }

    public PR_Basket getBasket() {
        return basket;
    }

    public void setBasket(PR_Basket basket) {
        this.basket = basket;
    }

    public void setCustomer_history(List<CustomerPreview> cl) {
        // transform a customer details to customer preview
        if (customer_history == null)
            customer_history = new ArrayList<>();
        if (!customer_history.isEmpty())
            customer_history.clear();
        this.customer_history = cl;
    }


    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    private boolean bringCustomerToTop(CustomerPreview customer) {
        if (customer_history != null) {
            for (int i = 0; i < customer_history.size(); i++) {
                if (customer.getEAN() != null && customer.getEAN().equals(customer_history.get(i).getEAN())) {
                    customer_history.remove(i);
                    customer_history.add(0, customer);
                    return true;
                }
            }
        }
        return false;
    }

    public void addCustomer(CustomerPreview customer) {
        if (customer_history == null)
            customer_history = new ArrayList<>();
        if (!bringCustomerToTop(customer))
            customer_history.add(0, customer);
    }

    public void clearContext() {
        this.seller = null;
        this.customer_history = null;
        this.basket = null;
        tweetCacheManager.clearCache();
    }

    private Sku getSkuFromProduct(Product product, Sku sku) {
        if (product.getSkl() == null)
            product.skl = new ArrayList<>();
        if (product.getSkl().contains(sku))
            return product.getSkl().get(product.getSkl().indexOf(sku));
        else
            product.getSkl().add(sku);
        return sku;
    }

    public Sku getSavedSkuFromBasket(Product product, Sku sku) {
        if (basket == null)
            basket = new PR_Basket();
        if (basket.getItems() == null)
            basket.setItems(new ArrayList<PR_BasketItem>());
        //TODO change that
        return null;
//        if (!basket.getItems().contains(product))
//            basket.getItems().add(product);
//        return getSkuFromProduct(basket.getItems().get(basket.indexOf(product)), sku);
    }

    public void clearBasket() {
        if (basket != null)
            basket.getItems().clear();
    }

}
