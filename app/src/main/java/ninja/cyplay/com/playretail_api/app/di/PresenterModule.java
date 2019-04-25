package ninja.cyplay.com.playretail_api.app.di;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import ninja.cyplay.com.apilibrary.domain.interactor.BasketInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.CatalogueInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.ClientInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.CustomerInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.OpinionInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.PaymentInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.ProductInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.ReportingInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.ScannerInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.SellerInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.TweetInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.WishlistInteractor;
import ninja.cyplay.com.playretail_api.ui.presenter.AuthenticationPresenter;
import ninja.cyplay.com.playretail_api.ui.presenter.AuthenticationPresenterImpl;
import ninja.cyplay.com.playretail_api.ui.presenter.BasketPresenter;
import ninja.cyplay.com.playretail_api.ui.presenter.BasketPresenterImpl;
import ninja.cyplay.com.playretail_api.ui.presenter.CataloguePresenter;
import ninja.cyplay.com.playretail_api.ui.presenter.CataloguePresenterImpl;
import ninja.cyplay.com.playretail_api.ui.presenter.CreateCustomerInfoPresenter;
import ninja.cyplay.com.playretail_api.ui.presenter.CreateCustomerInfoPresenterImpl;
import ninja.cyplay.com.playretail_api.ui.presenter.CropPicturePresenter;
import ninja.cyplay.com.playretail_api.ui.presenter.CropPicturePresenterImpl;
import ninja.cyplay.com.playretail_api.ui.presenter.CustomerCompletionPresenter;
import ninja.cyplay.com.playretail_api.ui.presenter.CustomerCompletionPresenterImpl;
import ninja.cyplay.com.playretail_api.ui.presenter.CustomerSearchPresenter;
import ninja.cyplay.com.playretail_api.ui.presenter.CustomerSearchPresenterImpl;
import ninja.cyplay.com.playretail_api.ui.presenter.GetConfigPresenter;
import ninja.cyplay.com.playretail_api.ui.presenter.GetConfigPresenterImpl;
import ninja.cyplay.com.playretail_api.ui.presenter.GetCustomerInfoPresenter;
import ninja.cyplay.com.playretail_api.ui.presenter.GetCustomerInfoPresenterImpl;
import ninja.cyplay.com.playretail_api.ui.presenter.GetProductPresenter;
import ninja.cyplay.com.playretail_api.ui.presenter.GetProductPresenterImpl;
import ninja.cyplay.com.playretail_api.ui.presenter.GetProductsPresenter;
import ninja.cyplay.com.playretail_api.ui.presenter.GetProductsPresenterImpl;
import ninja.cyplay.com.playretail_api.ui.presenter.GetSalesHistoryPresenter;
import ninja.cyplay.com.playretail_api.ui.presenter.GetSalesHistoryPresenterImpl;
import ninja.cyplay.com.playretail_api.ui.presenter.GetSellersPresenter;
import ninja.cyplay.com.playretail_api.ui.presenter.GetSellersPresenterImpl;
import ninja.cyplay.com.playretail_api.ui.presenter.GetShopsPresenter;
import ninja.cyplay.com.playretail_api.ui.presenter.GetShopsPresenterImpl;
import ninja.cyplay.com.playretail_api.ui.presenter.HomeTweetPresenter;
import ninja.cyplay.com.playretail_api.ui.presenter.HomeTweetPresenterImpl;
import ninja.cyplay.com.playretail_api.ui.presenter.OpinionPresenter;
import ninja.cyplay.com.playretail_api.ui.presenter.OpinionPresenterImpl;
import ninja.cyplay.com.playretail_api.ui.presenter.PaymentPresenter;
import ninja.cyplay.com.playretail_api.ui.presenter.PaymentPresenterImpl;
import ninja.cyplay.com.playretail_api.ui.presenter.ProductSearchPresenter;
import ninja.cyplay.com.playretail_api.ui.presenter.ProductSearchPresenterImpl;
import ninja.cyplay.com.playretail_api.ui.presenter.ProvisionDevicePresenter;
import ninja.cyplay.com.playretail_api.ui.presenter.ProvisionDevicePresenterImpl;
import ninja.cyplay.com.playretail_api.ui.presenter.SavePagesViewsPresenter;
import ninja.cyplay.com.playretail_api.ui.presenter.SavePagesViewsPresenterImpl;
import ninja.cyplay.com.playretail_api.ui.presenter.SaveReportingWebServicesPresenter;
import ninja.cyplay.com.playretail_api.ui.presenter.SaveReportingWebServicesPresenterImpl;
import ninja.cyplay.com.playretail_api.ui.presenter.ScannerPresenter;
import ninja.cyplay.com.playretail_api.ui.presenter.ScannerPresenterImpl;
import ninja.cyplay.com.playretail_api.ui.presenter.UpdateCustomerInfoPresenter;
import ninja.cyplay.com.playretail_api.ui.presenter.UpdateCustomerInfoPresenterImpl;

/**
 * @author glomadrian
 */
@Module(
        complete = false,
        library = true
)
public class PresenterModule {

    @Provides
    public GetConfigPresenter provideGetConfigPresenter(Context context, ClientInteractor clientInteractor) {
        return new GetConfigPresenterImpl(context, clientInteractor);
    }

    @Provides
    public GetSellersPresenter provideGetSellersPresenter(Context context, SellerInteractor sellerInteractor) {
        return new GetSellersPresenterImpl(context, sellerInteractor);
    }

    @Provides
    public GetShopsPresenter proviceGetShopsPresenter(Context context, ClientInteractor clientInteractor) {
        return new GetShopsPresenterImpl(context, clientInteractor);
    }

    @Provides
    public ProvisionDevicePresenter provideProvisionDevicePresenter(Context context, ClientInteractor clientInteractor) {
        return new ProvisionDevicePresenterImpl(context, clientInteractor);
    }

    @Provides
    AuthenticationPresenter provideAuthenticationPresenter(Context context, SellerInteractor sellerInteractor) {
        return new AuthenticationPresenterImpl(context, sellerInteractor);
    }

    @Provides
    CataloguePresenter cataloguePresenter(Context context, CatalogueInteractor catalogueInteractor) {
        return new CataloguePresenterImpl(context, catalogueInteractor);
    }

    @Provides
    GetProductsPresenter getProductsPresenter(Context context, ProductInteractor productInteractor) {
        return new GetProductsPresenterImpl(context, productInteractor);
    }

    @Provides
    GetProductPresenter getProductPresenter(Context context, ProductInteractor productInteractor) {
        return new GetProductPresenterImpl(context, productInteractor);
    }

    @Provides
    CustomerCompletionPresenter customerCompletionPresenter(Context context, CustomerInteractor customerInteractor) {
        return new CustomerCompletionPresenterImpl(context, customerInteractor);
    }

    @Provides
    CustomerSearchPresenter customerSearchPresenter(Context context, CustomerInteractor customerInteractor) {
        return new CustomerSearchPresenterImpl(context, customerInteractor);
    }

    @Provides
    ProductSearchPresenter productSearchPresenter(Context context, ProductInteractor productInteractor) {
        return new ProductSearchPresenterImpl(context, productInteractor);
    }

    @Provides
    UpdateCustomerInfoPresenter updateCustomerInfoPresenter(Context context, CustomerInteractor customerInteractor) {
        return new UpdateCustomerInfoPresenterImpl(context, customerInteractor);
    }

    @Provides
    GetCustomerInfoPresenter getCustomerInfoPresenter(Context context, CustomerInteractor customerInteractor) {
        return new GetCustomerInfoPresenterImpl(context, customerInteractor);
    }

    @Provides
    CreateCustomerInfoPresenter createCustomerInfoPresenter(Context context, CustomerInteractor customerInteractor) {
        return new CreateCustomerInfoPresenterImpl(context, customerInteractor);
    }

    @Provides
    GetSalesHistoryPresenter getSalesHistoryPresenter(Context context, CustomerInteractor customerInteractor) {
        return new GetSalesHistoryPresenterImpl(context, customerInteractor);
    }

    @Provides
    CropPicturePresenter cropPicturePresenter(Context context) {
        return new CropPicturePresenterImpl(context);
    }

    @Provides
    ScannerPresenter scannerPresenter(Context context, ScannerInteractor scannerInteractor) {
        return new ScannerPresenterImpl(context, scannerInteractor);
    }

    @Provides
    OpinionPresenter opinionPresenter(Context context, OpinionInteractor opinionInteractor, WishlistInteractor wishlistInteractor) {
        return new OpinionPresenterImpl(context, opinionInteractor, wishlistInteractor);
    }

    @Provides
    SavePagesViewsPresenter savePagesViewsPresenter(Context context, ReportingInteractor reportingInteractor) {
        return new SavePagesViewsPresenterImpl(context, reportingInteractor);
    }

    @Provides
    SaveReportingWebServicesPresenter saveReportingWebServicesPresenter(Context context, ReportingInteractor reportingInteractor) {
        return new SaveReportingWebServicesPresenterImpl(context, reportingInteractor);
    }

    @Provides
    BasketPresenter sellerBasketPresenter(Context context, BasketInteractor sellerInteractor) {
        return new BasketPresenterImpl(context, sellerInteractor);
    }

    @Provides
    HomeTweetPresenter homeTweetPresenter(Context context, TweetInteractor tweetInteractor) {
        return new HomeTweetPresenterImpl(context, tweetInteractor);
    }

    @Provides
    PaymentPresenter paymentPresenter(Context context, PaymentInteractor paymentInteractor) {
        return new PaymentPresenterImpl(context, paymentInteractor);
    }

}
