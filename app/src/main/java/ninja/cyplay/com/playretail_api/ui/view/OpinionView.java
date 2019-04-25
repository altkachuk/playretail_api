package ninja.cyplay.com.playretail_api.ui.view;

/**
 * Created by damien on 15/05/15.
 */
public interface OpinionView extends View {

    void showLoading();

    void hideLoading();

    void onLikeSuccess();

    void onDisLikeSuccess();

    void onAddToWishlistSuccess();

    void onDeleteFromWishlistSuccess();



}
