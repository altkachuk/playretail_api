package ninja.cyplay.com.playretail_api.ui.presenter;

import ninja.cyplay.com.playretail_api.ui.view.GetCustomerInfoView;

/**
 * Created by damien on 06/05/15.
 */
public interface GetCustomerInfoPresenter extends Presenter<GetCustomerInfoView> {

    public void getCustomerInfoFromEAN(String EAN);

}