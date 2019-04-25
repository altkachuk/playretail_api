package ninja.cyplay.com.playretail_api.ui.presenter;

import ninja.cyplay.com.apilibrary.models.business.PR_Config;
import ninja.cyplay.com.playretail_api.ui.view.GetConfigView;

/**
 * Created by damien on 27/04/15.
 */
public interface GetConfigPresenter extends Presenter<GetConfigView> {

    public void onConfig(PR_Config PRConfig);

    public void onReloadClick();

}
