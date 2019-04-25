package ninja.cyplay.com.apilibrary.domain.component;

import dagger.Component;
import ninja.cyplay.com.apilibrary.domain.interactor.ActionEventInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.BasketInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.CatalogueInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.ClientInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.CustomerInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.CustomerOfferInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.DQEAddressCompleteInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.DeliveryModeInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.EventInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.FeeInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.GetQuotationsInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.OfferInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.PaymentInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.PaymentShareInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.ProductInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.ProductReviewAttributeInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.ProductReviewInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.ProductShareInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.QuotationInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.ReportingInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.ScannerInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.SellerInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.AddressInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.ShopStatisticsInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.FeedbackInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.TweetInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.WishlistInteractor;
import ninja.cyplay.com.apilibrary.domain.module.InteractorModule;

/**
 * Created by damien on 21/04/16.
 */

@Component(
        modules = {
                InteractorModule.class
        },
        dependencies = {
                ApplicationComponent.class,
                ExecutorComponent.class,
                RepositoryComponent.class
        }
)
public interface InteractorComponent {

    SellerInteractor authentication();
    EventInteractor event();
    CatalogueInteractor catalogueLevel();
    ClientInteractor clientInteractor();
    ProductInteractor productInteractor();
    FeeInteractor feeInteractor();
    ActionEventInteractor actionEventInteractor();
    OfferInteractor offerInteractor();
    CustomerInteractor customerInteractor();
    ProductShareInteractor productShareInteractor();
    AddressInteractor addressInteractor();
    ProductReviewInteractor productReviewInteractor();
    ProductReviewAttributeInteractor productReviewAttributeInteractor();
    WishlistInteractor wishlistInteractor();
    ScannerInteractor scanner();
    ReportingInteractor reportingInteractor();
    BasketInteractor basketInteractor();
    TweetInteractor tweetInteractor();
    PaymentInteractor paymentInteractor();
    ShopStatisticsInteractor shopStatisticsInteractor();
    DeliveryModeInteractor deliveryModeInteractor();
    PaymentShareInteractor paymentShareInteractor();
    CustomerOfferInteractor customerOfferInteractor();
    DQEAddressCompleteInteractor dQEAddressCompleteInteractor();
    FeedbackInteractor suggestionInteractor();
    QuotationInteractor quotationInteractor();
    GetQuotationsInteractor getQuotationsInteractor();
}