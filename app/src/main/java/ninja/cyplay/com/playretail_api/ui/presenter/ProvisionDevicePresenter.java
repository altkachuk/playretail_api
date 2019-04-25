package ninja.cyplay.com.playretail_api.ui.presenter;

import ninja.cyplay.com.playretail_api.ui.view.ProvisionDeviceView;

/**
 * Created by damien on 29/04/15.
 */
public interface ProvisionDevicePresenter extends Presenter<ProvisionDeviceView> {

    public void provisionDevice(long storeId);

}
