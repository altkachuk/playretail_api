package ninja.cyplay.com.playretail_api.ui.presenter;

import ninja.cyplay.com.playretail_api.ui.view.HomeTweetView;

/**
 * Created by anishosni on 17/06/15.
 */
public interface HomeTweetPresenter extends Presenter<HomeTweetView> {

    public void initSocket();

    public void disconnectSocket(DisconnectTweetSocketCallback callback);

    public void markTweetAsRead(String uid);

    interface DisconnectTweetSocketCallback {
        void onDisconnect();
    }

}