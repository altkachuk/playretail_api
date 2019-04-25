package ninja.cyplay.com.playretail_api.ui.presenter;

import ninja.cyplay.com.playretail_api.ui.view.CustomerCompletionView;

/**
 * Created by damien on 05/05/15.
 */
public interface CustomerCompletionPresenter extends Presenter<CustomerCompletionView> {

    public void completionCustomer(String customer);

}
