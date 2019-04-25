package ninja.cyplay.com.apilibrary.domain.interactor.callback;

import ninja.cyplay.com.apilibrary.domain.repositoryModel.exception.BaseException;

/**
 * Created by romainlebouc on 09/08/16.
 */
public interface RequestCallback {

    void onError(BaseException e);
}
