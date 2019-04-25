package ninja.cyplay.com.apilibrary.domain.executor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import ninja.cyplay.com.apilibrary.domain.interactor.Interactor;

public class InteractorExecutorImpl implements InteractorExecutor {

    private static final int CORE_POOL_SIZE = 5;
    private static final int MAX_POOL_SIZE = 15;
    private static final int KEEP_ALIVE_TIME = 120;
    private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
    private static final BlockingQueue<Runnable> WORK_QUEUE = new LinkedBlockingQueue<>();

    private ThreadPoolExecutor threadPoolExecutor;

    public InteractorExecutorImpl() {
        threadPoolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, TIME_UNIT, WORK_QUEUE);
    }

    @Override
    public void run(Interactor interactor) {
        if (interactor != null) {
            threadPoolExecutor.submit(interactor);
        }
    }

}
