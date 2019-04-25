package ninja.cyplay.com.playretail_api.model.singleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AProduct;
import ninja.cyplay.com.apilibrary.models.business.PR_Basket;
import ninja.cyplay.com.playretail_api.model.business.CustomerPreview;
import ninja.cyplay.com.playretail_api.model.business.ProductOpinion;
import ninja.cyplay.com.playretail_api.model.business.Sku;
import ninja.cyplay.com.playretail_api.model.business.Product;

/**
 * Created by damien on 13/05/15.
 */
public class CustomerContext {

    private CustomerPreview customer;

    private List<PR_AProduct> wishlist;

    private Map<String, ProductOpinion> productOpinionMap;

    private PR_Basket basket;

    public boolean isCustomerIdentified() {
        return customer != null;
    }

    public void clearCustomerContext() {
        customer = null;
        wishlist = null;
        productOpinionMap = null;
        basket = null;
    }

    public CustomerPreview getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerPreview customer) {
        this.customer = customer;
    }

    public List<PR_AProduct> getWishlist() {
        return wishlist;
    }

    public void setWishlist(List<PR_AProduct> wishlist) {
        this.wishlist = wishlist;
    }

    public Map<String, ProductOpinion> getProductOpinionMap() {
        return productOpinionMap;
    }

    public void setProductOpinionMap(Map<String, ProductOpinion> productOpinionMap) {
        this.productOpinionMap = productOpinionMap;
    }

    public PR_Basket getBasket() {
        return basket;
    }

    public void setBasket(PR_Basket basket) {
        this.basket = basket;
    }

    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    public Boolean isProductLike(Product PRProduct) {
        if (PRProduct != null && productOpinionMap != null)
            if (productOpinionMap.containsKey(String.valueOf(PRProduct.getSkid()))) {
                ProductOpinion PRProductOpinion = productOpinionMap.get(String.valueOf(PRProduct.getSkid()));
                if (PRProductOpinion.opinion == 1)
                    return true;
            }
        return false;
    }

    public Boolean isProductDislike(Product PRProduct) {
        if (PRProduct != null && productOpinionMap != null)
            if (productOpinionMap.containsKey(String.valueOf(PRProduct.getSkid()))) {
                ProductOpinion PRProductOpinion = productOpinionMap.get(String.valueOf(PRProduct.getSkid()));
                if (PRProductOpinion.opinion == -1)
                    return true;
            }
        return false;
    }

    public Boolean isProductInWishlist(Product PRProduct) {
        if (PRProduct != null && wishlist != null)
            return wishlist.contains(PRProduct);
        return false;
    }

    public void deleteLike(Product PRProduct) {
        if (PRProduct != null && productOpinionMap != null)
            if (productOpinionMap.containsKey(String.valueOf(PRProduct.getSkid()))) {
                ProductOpinion PRProductOpinion = productOpinionMap.get(String.valueOf(PRProduct.getSkid()));
                if (PRProductOpinion.opinion == 1)
                    productOpinionMap.remove(PRProductOpinion);
            }
    }

    public void deleteDislike(Product PRProduct) {
        if (PRProduct != null && productOpinionMap != null)
            if (productOpinionMap.containsKey(String.valueOf(PRProduct.getSkid()))) {
                ProductOpinion PRProductOpinion = productOpinionMap.get(String.valueOf(PRProduct.getSkid()));
                if (PRProductOpinion.opinion == -1)
                    productOpinionMap.remove(PRProductOpinion);
            }
    }

    public void addLike(Product PRProduct) {
        deleteDislike(PRProduct);
        if (PRProduct != null)
            addOpinion(PRProduct, 1);
    }

    public void addDislike(Product PRProduct) {
        deleteLike(PRProduct);
        if (productOpinionMap == null)
            productOpinionMap = new HashMap<String, ProductOpinion>();
        if (PRProduct != null)
            addOpinion(PRProduct, -1);
    }

    private void addOpinion(Product PRProduct, int opinion) {
        if (productOpinionMap == null)
            productOpinionMap = new HashMap<String, ProductOpinion>();
        ProductOpinion PRProductOpinion = new ProductOpinion();
        PRProductOpinion.opinion = opinion;
        productOpinionMap.put(String.valueOf(PRProduct.getSkid()), PRProductOpinion);
    }

    public void addProductToWishlist(PR_AProduct PRProduct) {
        if (PRProduct != null)
            if (wishlist == null)
                wishlist = new ArrayList<>();
            if (!wishlist.contains(PRProduct))
                wishlist.add(PRProduct);
    }

    public void deleteProductFromWishlist(PR_AProduct PRProduct) {
        if (PRProduct != null && wishlist != null)
            if (wishlist.contains(PRProduct))
                wishlist.remove(PRProduct);
    }

    // -------------------------------------------------------------------------------------------
    //
    // -------------------------------------------------------------------------------------------

    public Sku getSkuFromProduct(Product PRProduct, Sku sku) {
        if (PRProduct.getSkl() == null)
            PRProduct.skl = new ArrayList<>();
        if (PRProduct.getSkl().contains(sku))
            return PRProduct.getSkl().get(PRProduct.getSkl().indexOf(sku));
        else
            PRProduct.getSkl().add(sku);
        return sku;
    }

    public Sku getSavedSkuFromBasket(Product PRProduct, Sku sku) {
        //TODO redo that
//        if (basket == null)
//            basket = new ArrayList<>();
//        if (!basket.contains(PRProduct))
//            basket.add(PRProduct);
//        return getSkuFromProduct((Product)basket.get(basket.indexOf(PRProduct)), sku);
        return null;
    }

    public void clearBasket() {
        if (basket != null && basket.getItems() != null)
            basket.getItems().clear();
    }

}