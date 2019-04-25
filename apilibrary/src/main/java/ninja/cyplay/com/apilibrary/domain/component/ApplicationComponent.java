package ninja.cyplay.com.apilibrary.domain.component;

import android.content.Context;

import dagger.Component;
import ninja.cyplay.com.apilibrary.MVPCleanArchitectureApplication;
import ninja.cyplay.com.apilibrary.domain.module.ApplicationModule;

@Component(
        modules = {
                ApplicationModule.class
        }
)
public interface ApplicationComponent {
    MVPCleanArchitectureApplication application(); //provision method
    Context applicationContext(); //provision method
}
