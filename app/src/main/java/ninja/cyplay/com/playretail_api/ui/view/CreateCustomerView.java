package ninja.cyplay.com.playretail_api.ui.view;

/**
 * Created by damien on 20/01/16.
 */
public interface CreateCustomerView extends View {

    public void showLoading();

    public void hideLoading();

    public void onCreateCustomerSuccess();

    public void onCreateCustomerError();

}