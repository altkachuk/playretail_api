package ninja.cyplay.com.playretail_api.ui.presenter;

import ninja.cyplay.com.playretail_api.model.business.CustomerDetails;
import ninja.cyplay.com.playretail_api.ui.view.UpdateCustomerInfoView;

/**
 * Created by damien on 06/05/15.
 */
public interface UpdateCustomerInfoPresenter extends Presenter<UpdateCustomerInfoView> {

    public void updateCustomerInfo(String EAN, CustomerDetails PRCustomerDetails);

}