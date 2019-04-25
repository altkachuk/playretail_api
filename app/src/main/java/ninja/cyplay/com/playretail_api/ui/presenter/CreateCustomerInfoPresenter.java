package ninja.cyplay.com.playretail_api.ui.presenter;

import ninja.cyplay.com.playretail_api.model.business.CustomerDetails;
import ninja.cyplay.com.playretail_api.ui.view.CreateCustomerView;

/**
 * Created by damien on 20/01/16.
 */
public interface CreateCustomerInfoPresenter extends Presenter<CreateCustomerView> {

    public void createCustomer(CustomerDetails customerDetails);

}
