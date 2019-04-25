package ninja.cyplay.com.playretail_api.ui.presenter;

import ninja.cyplay.com.playretail_api.ui.view.GetSalesHistoryView;

/**
 * Created by damien on 06/05/15.
 */
public interface GetSalesHistoryPresenter extends Presenter<GetSalesHistoryView> {

    public void getSalesHistoryFromEAN(String EAN);

}
