package ninja.cyplay.com.playretail_api.ui.presenter;

import ninja.cyplay.com.playretail_api.ui.view.View;

public interface Presenter<T extends View> {

    void initialize();

    void setView(T view);

    void onViewCreate();

    void onViewResume();

    void onViewDestroy();

}

