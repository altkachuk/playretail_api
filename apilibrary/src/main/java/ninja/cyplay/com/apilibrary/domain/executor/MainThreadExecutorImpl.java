package ninja.cyplay.com.apilibrary.domain.executor;


import android.os.Handler;
import android.os.Looper;

/**
 * Main Thread dev.cyplay.com.playretail.domain.executor using classic Looper.getMainLooper() from Android
 *
 */
public class MainThreadExecutorImpl implements MainThreadExecutor {

    private Handler handler;

    public MainThreadExecutorImpl() {
        this.handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void execute(Runnable runnable) {
        handler.post(runnable);
    }
}
