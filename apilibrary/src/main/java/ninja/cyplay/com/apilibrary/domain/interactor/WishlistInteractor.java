package ninja.cyplay.com.apilibrary.domain.interactor;

import ninja.cyplay.com.apilibrary.domain.interactor.callback.ResourceRequestCallback;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_AWishlistItem;

/**
 * Created by damien on 21/05/15.
 */
public interface WishlistInteractor {

    void addProductWishlist(String customerId,
                            PR_AWishlistItem wishlistItem,
                            String shopId,
                            final ResourceRequestCallback<PR_AWishlistItem> callback);


    void deleteProductWishlist(String customerId,
                               String wishlistitemId,
                               final ResourceRequestCallback<PR_AWishlistItem> callback);

}
