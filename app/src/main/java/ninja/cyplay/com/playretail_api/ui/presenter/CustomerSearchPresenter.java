package ninja.cyplay.com.playretail_api.ui.presenter;

import ninja.cyplay.com.playretail_api.ui.view.CustomerSearchView;

/**
 * Created by damien on 05/05/15.
 */
public interface CustomerSearchPresenter extends Presenter<CustomerSearchView> {

    public void searchCustomer(String customerName);

}
