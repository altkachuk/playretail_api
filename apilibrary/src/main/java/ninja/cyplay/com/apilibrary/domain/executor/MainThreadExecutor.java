package ninja.cyplay.com.apilibrary.domain.executor;

/**
 * The interactors callbacks are executed in a different thread, in Android the ui thread to perform
 * view changes
 *
 */
public interface MainThreadExecutor {

    void execute(Runnable runnable);
}
