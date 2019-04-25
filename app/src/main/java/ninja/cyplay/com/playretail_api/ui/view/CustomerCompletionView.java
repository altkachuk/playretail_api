package ninja.cyplay.com.playretail_api.ui.view;

import java.util.List;

/**
 * Created by damien on 05/05/15.
 */
public interface CustomerCompletionView extends View {

    void showLoading();

    void hideLoading();

    void onCompletionSuccess(List<String> customers);


}