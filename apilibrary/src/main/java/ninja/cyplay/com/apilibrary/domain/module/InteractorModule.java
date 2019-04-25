package ninja.cyplay.com.apilibrary.domain.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import ninja.cyplay.com.apilibrary.domain.executor.InteractorExecutor;
import ninja.cyplay.com.apilibrary.domain.executor.MainThreadExecutor;
import ninja.cyplay.com.apilibrary.domain.interactor.ActionEventInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.ActionEventInteractorImpl;
import ninja.cyplay.com.apilibrary.domain.interactor.AddressInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.BasketInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.BasketInteractorImpl;
import ninja.cyplay.com.apilibrary.domain.interactor.CatalogueInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.CatalogueInteractorImpl;
import ninja.cyplay.com.apilibrary.domain.interactor.ClientInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.ClientInteractorImpl;
import ninja.cyplay.com.apilibrary.domain.interactor.CustomerInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.CustomerInteractorImpl;
import ninja.cyplay.com.apilibrary.domain.interactor.CustomerOfferInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.DQEAddressCompleteInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.DQEAddressCompleteInteractorImpl;
import ninja.cyplay.com.apilibrary.domain.interactor.DeliveryModeInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.EventInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.EventInteractorImpl;
import ninja.cyplay.com.apilibrary.domain.interactor.FeeInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.FeeInteractorImpl;
import ninja.cyplay.com.apilibrary.domain.interactor.GetQuotationsInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.GetQuotationsInteractorImpl;
import ninja.cyplay.com.apilibrary.domain.interactor.SynchronizationInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.SynchronizationInteractorImpl;
import ninja.cyplay.com.apilibrary.domain.interactor.OfferInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.OfferInteractorImpl;
import ninja.cyplay.com.apilibrary.domain.interactor.PaymentInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.PaymentInteractorImpl;
import ninja.cyplay.com.apilibrary.domain.interactor.PaymentShareInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.ProductInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.ProductInteractorImpl;
import ninja.cyplay.com.apilibrary.domain.interactor.ProductReviewAttributeInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.ProductReviewInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.ProductShareInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.ProductShareInteractorImpl;
import ninja.cyplay.com.apilibrary.domain.interactor.QuotationInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.ReportingInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.ReportingInteractorImpl;
import ninja.cyplay.com.apilibrary.domain.interactor.ScannerInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.ScannerInteractorImpl;
import ninja.cyplay.com.apilibrary.domain.interactor.SellerInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.SellerInteractorImpl;
import ninja.cyplay.com.apilibrary.domain.interactor.ShopStatisticsInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.ShopStatisticsInteractorImpl;
import ninja.cyplay.com.apilibrary.domain.interactor.FeedbackInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.TweetInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.TweetSocketIOInteractorImpl;
import ninja.cyplay.com.apilibrary.domain.interactor.WishlistInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.WishlistInteractorImpl;
import ninja.cyplay.com.apilibrary.domain.repository.dqe.DQERepository;
import ninja.cyplay.com.apilibrary.domain.repository.PlayRetailRepository;
import ninja.cyplay.com.apilibrary.models.singleton.APIValue;

@Module
public class InteractorModule {

    private APIValue apiValue;

    public InteractorModule(APIValue apiValue) {
        this.apiValue = apiValue;
    }

    @Provides
    EventInteractor event(InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor, PlayRetailRepository retailRepository) {
        return new EventInteractorImpl(interactorExecutor, mainThreadExecutor, retailRepository);
    }

    @Provides
    SellerInteractor authentication(InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor, PlayRetailRepository retailRepository) {
        return new SellerInteractorImpl(interactorExecutor, mainThreadExecutor, retailRepository);
    }

    @Provides
    CatalogueInteractor catalogueLevel(InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor, PlayRetailRepository retailRepository) {
        return new CatalogueInteractorImpl(interactorExecutor, mainThreadExecutor, retailRepository);
    }

    @Provides
    ClientInteractor clientInteractor(InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor, PlayRetailRepository retailRepository) {
        return new ClientInteractorImpl(interactorExecutor, mainThreadExecutor, retailRepository);
    }

    @Provides
    ProductInteractor productInteractor(InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor, PlayRetailRepository retailRepository, Context context) {
        return new ProductInteractorImpl(interactorExecutor, mainThreadExecutor, retailRepository, context);
    }

    @Provides
    FeeInteractor feeInteractor(InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor, PlayRetailRepository retailRepository) {
        return new FeeInteractorImpl(interactorExecutor, mainThreadExecutor, retailRepository);
    }

    @Provides
    ActionEventInteractor actionEventInteractor(InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor, PlayRetailRepository retailRepository) {
        return new ActionEventInteractorImpl(interactorExecutor, mainThreadExecutor, retailRepository);
    }

    @Provides
    OfferInteractor offerInteractor(InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor, PlayRetailRepository retailRepository) {
        return new OfferInteractorImpl(interactorExecutor, mainThreadExecutor, retailRepository);
    }

    @Provides
    CustomerInteractor customerInteractor(InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor, PlayRetailRepository retailRepository) {
        return new CustomerInteractorImpl(interactorExecutor, mainThreadExecutor, retailRepository);
    }

    @Provides
    AddressInteractor addressInteractor(InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor, PlayRetailRepository retailRepository) {
        return new AddressInteractor(interactorExecutor, mainThreadExecutor, retailRepository);
    }


    @Provides
    ProductShareInteractor productShareInteractor(InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor, PlayRetailRepository retailRepository) {
        return new ProductShareInteractorImpl(interactorExecutor, mainThreadExecutor, retailRepository);
    }

    @Provides
    ProductReviewAttributeInteractor productReviewAttributeInteractor(InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor, PlayRetailRepository retailRepository) {
        return new ProductReviewAttributeInteractor(interactorExecutor, mainThreadExecutor, retailRepository);
    }

    @Provides
    ProductReviewInteractor productReviewInteractor(InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor, PlayRetailRepository retailRepository) {
        return new ProductReviewInteractor(interactorExecutor, mainThreadExecutor, retailRepository);
    }

    @Provides
    WishlistInteractor wishlistInteractor(InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor, PlayRetailRepository retailRepository) {
        return new WishlistInteractorImpl(interactorExecutor, mainThreadExecutor, retailRepository);
    }

    @Provides
    ScannerInteractor scanner(InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor, PlayRetailRepository retailRepository) {
        return new ScannerInteractorImpl(interactorExecutor, mainThreadExecutor, retailRepository);
    }

    @Provides
    ReportingInteractor reportingInteractor(InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor, PlayRetailRepository retailRepository) {
        return new ReportingInteractorImpl(interactorExecutor, mainThreadExecutor, retailRepository);
    }

    @Provides
    BasketInteractor basketInteractor(InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor, PlayRetailRepository retailRepository) {
        return new BasketInteractorImpl(interactorExecutor, mainThreadExecutor, retailRepository);
    }

    @Provides
    TweetInteractor tweetInteractor(Context context, InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor) {
        return new TweetSocketIOInteractorImpl(context, interactorExecutor, mainThreadExecutor);
    }

    @Provides
    PaymentInteractor paymentInteractor(Context context, InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor, PlayRetailRepository retailRepository) {
        return new PaymentInteractorImpl(context, interactorExecutor, mainThreadExecutor, retailRepository);
    }

    @Provides
    ShopStatisticsInteractor shopStatisticsInteractor(InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor, PlayRetailRepository retailRepository) {
        return new ShopStatisticsInteractorImpl(interactorExecutor, mainThreadExecutor, retailRepository);
    }

    @Provides
    DeliveryModeInteractor deliveryModeInteractor(InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor, PlayRetailRepository retailRepository) {
        return new DeliveryModeInteractor(interactorExecutor, mainThreadExecutor, retailRepository);
    }

    @Provides
    PaymentShareInteractor paymentShareInteractor(InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor, PlayRetailRepository retailRepository) {
        return new PaymentShareInteractor(interactorExecutor, mainThreadExecutor, retailRepository);
    }

    @Provides
    CustomerOfferInteractor customerOfferInteractor(InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor, PlayRetailRepository retailRepository) {
        return new CustomerOfferInteractor(interactorExecutor, mainThreadExecutor, retailRepository);
    }

    @Provides
    DQEAddressCompleteInteractor dQEAddressCompleteInteractor(InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor, DQERepository retailRepository) {
        return new DQEAddressCompleteInteractorImpl(interactorExecutor, mainThreadExecutor, retailRepository);
    }

    @Provides
    FeedbackInteractor suggestionInteractor(InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor, PlayRetailRepository retailRepository) {
        return new FeedbackInteractor(interactorExecutor, mainThreadExecutor, retailRepository);
    }

    @Provides
    QuotationInteractor quotationInteractor(InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor, PlayRetailRepository retailRepository) {
        return new QuotationInteractor(interactorExecutor, mainThreadExecutor, retailRepository);
    }

    @Provides
    GetQuotationsInteractor getQuotationsInteractor(Context context, InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor,
                                                    PlayRetailRepository retailRepository) {
        return new GetQuotationsInteractorImpl(context, interactorExecutor,
                mainThreadExecutor, retailRepository);
    }

    @Provides
    SynchronizationInteractor importInteractor(InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor, PlayRetailRepository retailRepository) {
        return new SynchronizationInteractorImpl(interactorExecutor, mainThreadExecutor, retailRepository, apiValue);
    }

}
