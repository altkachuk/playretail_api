package ninja.cyplay.com.apilibrary.domain.module;

import dagger.Module;
import dagger.Provides;
import ninja.cyplay.com.apilibrary.domain.executor.InteractorExecutor;
import ninja.cyplay.com.apilibrary.domain.executor.InteractorExecutorImpl;
import ninja.cyplay.com.apilibrary.domain.executor.MainThreadExecutor;
import ninja.cyplay.com.apilibrary.domain.executor.MainThreadExecutorImpl;

@Module
public class ExecutorModule {

    private final InteractorExecutor interactorExecutor;
    private final MainThreadExecutor mainThreadExecutor;

    public ExecutorModule() {
        interactorExecutor = new InteractorExecutorImpl();
        mainThreadExecutor = new MainThreadExecutorImpl();
    }

    @Provides
    public InteractorExecutor provideExecutor() {
        return interactorExecutor;
    }

    @Provides
    public MainThreadExecutor provideMainThreadExecutor() {
        return mainThreadExecutor;
    }
}
