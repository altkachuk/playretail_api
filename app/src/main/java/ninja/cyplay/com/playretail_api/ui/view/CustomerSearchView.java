package ninja.cyplay.com.playretail_api.ui.view;

import java.util.List;

import ninja.cyplay.com.playretail_api.model.business.CustomerPreview;

/**
 * Created by damien on 05/05/15.
 */
public interface CustomerSearchView extends View {

    void showLoading();

    void hideLoading();

    void onSearchSuccess(List<CustomerPreview> customers);

}