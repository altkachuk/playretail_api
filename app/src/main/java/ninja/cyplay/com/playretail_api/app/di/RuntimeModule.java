package ninja.cyplay.com.playretail_api.app.di;

import android.content.Context;

import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ninja.cyplay.com.apilibrary.models.singleton.APIValue;
import ninja.cyplay.com.apilibrary.models.singleton.TweetCacheManager;
import ninja.cyplay.com.playretail_api.model.singleton.CustomerContext;
import ninja.cyplay.com.playretail_api.model.singleton.PagesViews;
import ninja.cyplay.com.playretail_api.model.singleton.SellerContext;
import ninja.cyplay.com.playretail_api.model.singleton.ShopList;
import ninja.cyplay.com.playretail_api.model.singleton.PaylevenApi;

/**
 * Created by damien on 28/04/15.
 */
@Module(
        complete = false,
        library = true
)
public class RuntimeModule {

    @Provides
    @Singleton
    public APIValue provideApiValue(Context context) {
        return new APIValue(context);
    }


    @Provides
    @Singleton
    public TweetCacheManager provideTweetCacheManager(Context context) {
        return new TweetCacheManager();
    }

    @Provides
    @Singleton
    public SellerContext provideSellerContext(Context context) {
        return new SellerContext(context);
    }

    @Provides
    @Singleton
    public CustomerContext provideCustomerContext() { return new CustomerContext(); }

    @Provides
    @Singleton
    public Bus provideBus() { return new Bus(); }

    @Provides
    @Singleton
    public PagesViews pagesViews(Context context) {
        return new PagesViews(context);
    }

    @Provides
    @Singleton
    public ShopList shops() {
        return new ShopList();
    }

    @Provides
    @Singleton
    public PaylevenApi providePaylevenApi() {
        return new PaylevenApi();
    }

}

