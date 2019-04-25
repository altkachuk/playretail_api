package ninja.cyplay.com.playretail_api.ui.presenter;

import android.app.Activity;

import ninja.cyplay.com.apilibrary.models.business.ECustomerActions;
import ninja.cyplay.com.playretail_api.model.business.Product;
import ninja.cyplay.com.playretail_api.ui.view.OpinionView;

/**
 * Created by damien on 15/05/15.
 */
public interface OpinionPresenter extends Presenter<OpinionView> {

    public void executeAction(Activity activity, ECustomerActions action, Product PRProduct);

    public void customerFound();

    public void updateOpinionsViews(Product PRProduct);

}
