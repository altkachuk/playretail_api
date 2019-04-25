package ninja.cyplay.com.playretail_api.ui.presenter;

import ninja.cyplay.com.playretail_api.ui.view.GetProductView;

/**
 * Created by damien on 05/05/15.
 */
public interface GetProductPresenter extends Presenter<GetProductView>{

    public void getProductInfoFromSkid(String skid);

}
