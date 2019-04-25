package ninja.cyplay.com.playretail_api.ui.view;


import java.util.List;

import ninja.cyplay.com.playretail_api.model.business.SalesHistory;

/**
 * Created by damien on 06/05/15.
 */
public interface GetSalesHistoryView extends View {

    void showLoading();

    void hideLoading();

    void onSalesHistorySuccess(Integer cnt, List<SalesHistory> salesHistories);

    void onSalesHistoryError();

}
