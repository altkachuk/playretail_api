package ninja.cyplay.com.apilibrary.domain.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import ninja.cyplay.com.apilibrary.MVPCleanArchitectureApplication;

@Module
public class ApplicationModule {

    private final MVPCleanArchitectureApplication application;

    public ApplicationModule(MVPCleanArchitectureApplication application) {
        this.application = application;
    }

    @Provides
    public MVPCleanArchitectureApplication application() {
        return this.application;
    }

    @Provides
    public Context applicationContext() {
        return this.application;
    }

}
