package ninja.cyplay.com.apilibrary.domain.executor;

import ninja.cyplay.com.apilibrary.domain.interactor.Interactor;

/**
 * Interface for create executors, the executors will only execute Interactors
 *
 */
public interface InteractorExecutor {

    void run(Interactor interactor);
}
