package ninja.cyplay.com.playretail_api.ui.presenter;

import java.util.List;

import ninja.cyplay.com.playretail_api.model.business.Seller;
import ninja.cyplay.com.playretail_api.ui.view.GetSellersView;

/**
 * Created by damien on 28/04/15.
 */
public interface GetSellersPresenter extends Presenter<GetSellersView> {

    public void getSellers();

    public void onSellers(List<Seller> PRSellers);

}
