package ninja.cyplay.com.playretail_api.ui.presenter;

import java.util.List;

import ninja.cyplay.com.apilibrary.models.business.SugarORM.PageView;
import ninja.cyplay.com.playretail_api.ui.view.SavePagesViewsView;

/**
 * Created by damien on 25/05/15.
 */
public interface SavePagesViewsPresenter extends Presenter<SavePagesViewsView> {

    public void savePagesViews(List<PageView> pl);

}
