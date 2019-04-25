package ninja.cyplay.com.apilibrary.domain.component;

import dagger.Component;
import ninja.cyplay.com.apilibrary.domain.executor.InteractorExecutor;
import ninja.cyplay.com.apilibrary.domain.executor.MainThreadExecutor;
import ninja.cyplay.com.apilibrary.domain.module.ExecutorModule;

@Component(
        modules = {
                ExecutorModule.class
        }
)
public interface ExecutorComponent {

    InteractorExecutor provideExecutor(); //provision method
    MainThreadExecutor provideMainThreadExecutor(); //provision method
}
