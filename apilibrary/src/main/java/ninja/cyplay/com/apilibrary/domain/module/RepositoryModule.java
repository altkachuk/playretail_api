
package ninja.cyplay.com.apilibrary.domain.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import ninja.cyplay.com.apilibrary.domain.repository.dqe.DQEApiRepository;
import ninja.cyplay.com.apilibrary.domain.repository.dqe.DQERepository;
import ninja.cyplay.com.apilibrary.domain.repository.PlayRetailRepository;
import ninja.cyplay.com.apilibrary.domain.repository.PlayRetailRequestInterceptor;
import ninja.cyplay.com.apilibrary.domain.repository.RetrofitPlayRetailAPIRepository;
import ninja.cyplay.com.apilibrary.models.singleton.APIValue;
import ninja.cyplay.com.apilibrary.utils.ClientUtil;
import retrofit.RequestInterceptor;

@Module
public class RepositoryModule {

    final RequestInterceptor interceptor;
    final PlayRetailRepository playRetailRepository;
    final DQERepository dqeApiRepository;

    public RepositoryModule(Context context, APIValue apiValue) {
        interceptor = new PlayRetailRequestInterceptor(apiValue);
        playRetailRepository =
                new RetrofitPlayRetailAPIRepository(context, ClientUtil.getClientUrl(), interceptor, apiValue);
        dqeApiRepository = new DQEApiRepository(context, interceptor);
    }

    @Provides
    public PlayRetailRepository providePlayRetailRepository() {
        return playRetailRepository;
    }

    @Provides
    public RequestInterceptor provideRequestInterceptor() {
        return interceptor;
    }

    @Provides
    public DQERepository provideDQEApiRepository() {
        return dqeApiRepository;
    }

}
