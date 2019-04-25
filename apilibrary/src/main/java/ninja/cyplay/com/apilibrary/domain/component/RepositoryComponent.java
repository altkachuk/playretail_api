package ninja.cyplay.com.apilibrary.domain.component;

import dagger.Component;
import ninja.cyplay.com.apilibrary.domain.module.RepositoryModule;
import ninja.cyplay.com.apilibrary.domain.repository.dqe.DQERepository;
import ninja.cyplay.com.apilibrary.domain.repository.PlayRetailRepository;
import retrofit.RequestInterceptor;

/**
 * Created by damien on 21/04/16.
 */
@Component(
        modules = {
                RepositoryModule.class
        },
        dependencies = {
                ApplicationComponent.class,
                ExecutorComponent.class
        }
)
public interface RepositoryComponent {

    PlayRetailRepository providePlayRetailRepository();
    RequestInterceptor provideRequestInterceptor();
    DQERepository provideDQEApiRepository();
}