package ninja.cyplay.com.playretail_api.ui.presenter;

import android.content.Context;

import java.util.List;

import javax.inject.Inject;

import ninja.cyplay.com.apilibrary.domain.interactor.ReportingInteractor;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.exception.BaseException;
import ninja.cyplay.com.apilibrary.models.business.SugarORM.PageView;
import ninja.cyplay.com.apilibrary.models.singleton.APIValue;
import ninja.cyplay.com.playretail_api.app.BasePresenter;
import ninja.cyplay.com.playretail_api.ui.view.SavePagesViewsView;

/**
 * Created by damien on 25/05/15.
 */
public class SavePagesViewsPresenterImpl extends BasePresenter implements SavePagesViewsPresenter {

    @Inject
    APIValue apiValue;

    private Context context;
    private SavePagesViewsView savePagesViewsView;
    private ReportingInteractor reportingInteractor;

    public SavePagesViewsPresenterImpl(Context context, ReportingInteractor reportingInteractor) {
        super(context);
        this.context = context;
        this.reportingInteractor = reportingInteractor;
    }

    @Override
    public void savePagesViews(List<PageView> pl) {
        if (apiValue == null || apiValue.getPRConfig() == null || apiValue.getPRConfig().getPRShopConfig() == null
                || pl == null || pl.size() == 0)
            return;
        reportingInteractor.savePagesViews(apiValue.getDeviceId(), String.valueOf(apiValue.getPRConfig().getPRShopConfig().store_id), pl, new ReportingInteractor.savePagesViewsCallback() {

            @Override
            public void onSuccess() {
                savePagesViewsView.onSavingPagesViewsSuccess();
            }

            @Override
            public void onError(BaseException e) {
                savePagesViewsView.onSavingPagesViewsError();
            }

        });
    }

    @Override
    public void initialize() {

    }

    @Override
    public void setView(SavePagesViewsView view) {
        this.savePagesViewsView = view;
    }

    @Override
    public void onViewCreate() {

    }

    @Override
    public void onViewResume() {

    }

    @Override
    public void onViewDestroy() {

    }

}
