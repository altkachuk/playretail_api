package ninja.cyplay.com.playretail_api.ui.view;

/**
 * Created by damien on 27/05/15.
 */
public interface BasketView extends View {

    void onSellerBasketSuccess();

    void onSellerBasketError();

    void onSyncSuccess();

    void onSyncError();

    void onDeleteSuccess();

    void onDeleteError();

    void showLoading();

    void hideLoading();

    void displayPopUp(String message);

}
