package ninja.cyplay.com.playretail_api.app.di;

import android.content.Context;
import android.view.LayoutInflater;

import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ninja.cyplay.com.apilibrary.MVPCleanArchitectureApplication;
import ninja.cyplay.com.apilibrary.domain.dependencyinjection.ExecutorModule;
import ninja.cyplay.com.apilibrary.domain.dependencyinjection.InteractorModule;
import ninja.cyplay.com.apilibrary.domain.dependencyinjection.RepositoryModule;
import ninja.cyplay.com.apilibrary.domain.interactor.BasketInteractorImpl;
import ninja.cyplay.com.apilibrary.domain.interactor.CatalogueInteractorImpl;
import ninja.cyplay.com.apilibrary.domain.interactor.ClientInteractorImpl;
import ninja.cyplay.com.apilibrary.domain.interactor.CustomerInteractorImpl;
import ninja.cyplay.com.apilibrary.domain.interactor.OpinionInteractorImpl;
import ninja.cyplay.com.apilibrary.domain.interactor.PaymentInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.ProductInteractorImpl;
import ninja.cyplay.com.apilibrary.domain.interactor.ReportingInteractorImpl;
import ninja.cyplay.com.apilibrary.domain.interactor.ScannerInteractorImpl;
import ninja.cyplay.com.apilibrary.domain.interactor.SellerInteractorImpl;
import ninja.cyplay.com.apilibrary.domain.interactor.TweetSocketIOInteractorImpl;
import ninja.cyplay.com.apilibrary.domain.interactor.WishlistInteractorImpl;
import ninja.cyplay.com.apilibrary.domain.repository.PlayRetailRequestInterceptor;
import ninja.cyplay.com.playretail_api.model.singleton.CustomerContext;
import ninja.cyplay.com.playretail_api.model.singleton.PagesViews;
import ninja.cyplay.com.playretail_api.model.singleton.SellerContext;
import ninja.cyplay.com.playretail_api.model.singleton.ShopList;
import ninja.cyplay.com.playretail_api.app.App;
import ninja.cyplay.com.playretail_api.model.service.ReportingService;
import ninja.cyplay.com.playretail_api.ui.activity.AuthenticationActivity;
import ninja.cyplay.com.playretail_api.ui.activity.BarCodeScannerActivity;
import ninja.cyplay.com.playretail_api.ui.activity.BasketActivity;
import ninja.cyplay.com.playretail_api.ui.activity.CatalogueActivity;
import ninja.cyplay.com.playretail_api.ui.activity.CatalogueFilterActivity;
import ninja.cyplay.com.playretail_api.ui.activity.ContactManagerActivity;
import ninja.cyplay.com.playretail_api.ui.activity.CropActivity;
import ninja.cyplay.com.playretail_api.ui.activity.CustomerActivity;
import ninja.cyplay.com.playretail_api.ui.activity.CustomerHistoryActivity;
import ninja.cyplay.com.playretail_api.ui.activity.CustomerSearchActivity;
import ninja.cyplay.com.playretail_api.ui.activity.DeviceRegistrationActivity;
import ninja.cyplay.com.playretail_api.ui.activity.FormActivity;
import ninja.cyplay.com.playretail_api.ui.activity.FormDynamicActivity;
import ninja.cyplay.com.playretail_api.ui.activity.FullScreenImageGalleryActivity;
import ninja.cyplay.com.playretail_api.ui.activity.HomeActivity;
import ninja.cyplay.com.playretail_api.ui.activity.InShopCustomerActivity;
import ninja.cyplay.com.playretail_api.ui.activity.LoginActivityDetailActivity;
import ninja.cyplay.com.playretail_api.ui.activity.LoginActivityListActivity;
import ninja.cyplay.com.playretail_api.ui.activity.PaymentActivity;
import ninja.cyplay.com.playretail_api.ui.activity.ProductActivity;
import ninja.cyplay.com.playretail_api.ui.activity.ProductPreviewActivity;
import ninja.cyplay.com.playretail_api.ui.activity.ReceipActivity;
import ninja.cyplay.com.playretail_api.ui.activity.SellersActivity;
import ninja.cyplay.com.playretail_api.ui.activity.SignatureActivity;
import ninja.cyplay.com.playretail_api.ui.activity.SkaListActivity;
import ninja.cyplay.com.playretail_api.ui.activity.SplashScreenAcivity;
import ninja.cyplay.com.playretail_api.ui.activity.StatisticsActivity;
import ninja.cyplay.com.playretail_api.ui.activity.TweetsActivity;
import ninja.cyplay.com.playretail_api.ui.adapter.BasketRecyclerAdapter;
import ninja.cyplay.com.playretail_api.ui.adapter.HomeRecyclerAdapter;
import ninja.cyplay.com.playretail_api.ui.adapter.PlaylistProductRecyclerAdapter;
import ninja.cyplay.com.playretail_api.ui.adapter.ProductRecyclerAdapter;
import ninja.cyplay.com.playretail_api.ui.adapter.SalesHistoryAdapter;
import ninja.cyplay.com.playretail_api.ui.adapter.SkaRecyclerAdapter;
import ninja.cyplay.com.playretail_api.ui.adapter.SkusRecyclerAdapter;
import ninja.cyplay.com.playretail_api.ui.custom.form.FormSectionCardView;
import ninja.cyplay.com.playretail_api.ui.adapter.TweetAdapter;
import ninja.cyplay.com.playretail_api.ui.custom.ReceipExtraCardView;
import ninja.cyplay.com.playretail_api.ui.fragment.AuthenticationFragment;
import ninja.cyplay.com.playretail_api.ui.fragment.BarCodeScannerFragment;
import ninja.cyplay.com.playretail_api.ui.fragment.BasketFragment;
import ninja.cyplay.com.playretail_api.ui.fragment.CatalogueCategoriesFragment;
import ninja.cyplay.com.playretail_api.ui.fragment.CatalogueFilterFragment;
import ninja.cyplay.com.playretail_api.ui.fragment.CatalogueProductsFragment;
import ninja.cyplay.com.playretail_api.ui.fragment.ContactManagerFragment;
import ninja.cyplay.com.playretail_api.ui.fragment.CropFragment;
import ninja.cyplay.com.playretail_api.ui.fragment.CustomerCompletionFragment;
import ninja.cyplay.com.playretail_api.ui.fragment.CustomerHistoryFragment;
import ninja.cyplay.com.playretail_api.ui.fragment.CustomerOffersFragment;
import ninja.cyplay.com.playretail_api.ui.fragment.CustomerOffersHeaderFragment;
import ninja.cyplay.com.playretail_api.ui.fragment.CustomerPlaylistFragment;
import ninja.cyplay.com.playretail_api.ui.fragment.CustomerProfileFragment;
import ninja.cyplay.com.playretail_api.ui.fragment.CustomerSalesHistoryFragment;
import ninja.cyplay.com.playretail_api.ui.fragment.CustomerSearchPreviewFragment;
import ninja.cyplay.com.playretail_api.ui.fragment.CustomerWishlistFragment;
import ninja.cyplay.com.playretail_api.ui.fragment.DeviceRegistrationFragment;
import ninja.cyplay.com.playretail_api.ui.fragment.FormDynamicFragment;
import ninja.cyplay.com.playretail_api.ui.fragment.FormSectionFragment;
import ninja.cyplay.com.playretail_api.ui.fragment.HomeFragment;
import ninja.cyplay.com.playretail_api.ui.fragment.HomeTweetsFragment;
import ninja.cyplay.com.playretail_api.ui.fragment.HomeTweetsFragmentFirebase;
import ninja.cyplay.com.playretail_api.ui.fragment.HomeTweetsFragmentSocketIO;
import ninja.cyplay.com.playretail_api.ui.fragment.InShopCustomerFragment;
import ninja.cyplay.com.playretail_api.ui.fragment.LoginActivityDetailFragment;
import ninja.cyplay.com.playretail_api.ui.fragment.OpinionBaseFragment;
import ninja.cyplay.com.playretail_api.ui.fragment.PaymentFragment;
import ninja.cyplay.com.playretail_api.ui.fragment.ProductLongDescriptionFragment;
import ninja.cyplay.com.playretail_api.ui.fragment.ProductModelsListFragment;
import ninja.cyplay.com.playretail_api.ui.fragment.ProductShareActionBarFragment;
import ninja.cyplay.com.playretail_api.ui.fragment.ProductShortDescriptionFragment;
import ninja.cyplay.com.playretail_api.ui.fragment.ProductSummaryFragment;
import ninja.cyplay.com.playretail_api.ui.fragment.ProductsRelatedFragment;
import ninja.cyplay.com.playretail_api.ui.fragment.ReceipFragment;
import ninja.cyplay.com.playretail_api.ui.fragment.SellersFragment;
import ninja.cyplay.com.playretail_api.ui.fragment.SignatureFragment;
import ninja.cyplay.com.playretail_api.ui.fragment.SkaListFragment;
import ninja.cyplay.com.playretail_api.ui.fragment.SplashScreenFragment;
import ninja.cyplay.com.playretail_api.ui.fragment.StatisticsFragment;
import ninja.cyplay.com.playretail_api.ui.fragment.TweetsFragment;
import ninja.cyplay.com.playretail_api.ui.fragment.TweetsFragmentFirebase;
import ninja.cyplay.com.playretail_api.ui.fragment.TweetsFragmentSocketIO;
import ninja.cyplay.com.playretail_api.ui.presenter.AuthenticationPresenterImpl;
import ninja.cyplay.com.playretail_api.ui.presenter.BasketPresenterImpl;
import ninja.cyplay.com.playretail_api.ui.presenter.CataloguePresenterImpl;
import ninja.cyplay.com.playretail_api.ui.presenter.CreateCustomerInfoPresenterImpl;
import ninja.cyplay.com.playretail_api.ui.presenter.CropPicturePresenterImpl;
import ninja.cyplay.com.playretail_api.ui.presenter.CustomerCompletionPresenterImpl;
import ninja.cyplay.com.playretail_api.ui.presenter.CustomerSearchPresenterImpl;
import ninja.cyplay.com.playretail_api.ui.presenter.GetConfigPresenterImpl;
import ninja.cyplay.com.playretail_api.ui.presenter.GetCustomerInfoPresenterImpl;
import ninja.cyplay.com.playretail_api.ui.presenter.GetProductPresenterImpl;
import ninja.cyplay.com.playretail_api.ui.presenter.GetProductsPresenterImpl;
import ninja.cyplay.com.playretail_api.ui.presenter.GetSalesHistoryPresenterImpl;
import ninja.cyplay.com.playretail_api.ui.presenter.GetSellersPresenterImpl;
import ninja.cyplay.com.playretail_api.ui.presenter.GetShopsPresenterImpl;
import ninja.cyplay.com.playretail_api.ui.presenter.HomeTweetPresenterImpl;
import ninja.cyplay.com.playretail_api.ui.presenter.OpinionPresenterImpl;
import ninja.cyplay.com.playretail_api.ui.presenter.PaymentPresenterImpl;
import ninja.cyplay.com.playretail_api.ui.presenter.ProductSearchPresenterImpl;
import ninja.cyplay.com.playretail_api.ui.presenter.ProvisionDevicePresenterImpl;
import ninja.cyplay.com.playretail_api.ui.presenter.SavePagesViewsPresenterImpl;
import ninja.cyplay.com.playretail_api.ui.presenter.SaveReportingWebServicesPresenterImpl;
import ninja.cyplay.com.playretail_api.ui.presenter.ScannerPresenterImpl;
import ninja.cyplay.com.playretail_api.ui.presenter.UpdateCustomerInfoPresenterImpl;
import ninja.cyplay.com.playretail_api.ui.viewholder.BasketViewHolder;
import ninja.cyplay.com.playretail_api.ui.viewholder.ProductViewHolder;
import ninja.cyplay.com.playretail_api.ui.viewholder.TweetViewHolder;

/**
 * Created by damien on 28/04/15.
 */
@Module(
    includes = {
            RuntimeModule.class,
            ExecutorModule.class,
            InteractorModule.class,
            RepositoryModule.class,
            PresenterModule.class,
    },
    injects = {
            App.class,
            MVPCleanArchitectureApplication.class,

            // Runtime Modules
            Bus.class,
            ShopList.class,
            SellerContext.class,
            CustomerContext.class,
            PagesViews.class,
            ReportingService.class,
            PlayRetailRequestInterceptor.class,

            // View holder (For Bus/CustomerContext injection)
            ProductViewHolder.class,
            BasketViewHolder.class,
            SalesHistoryAdapter.class,
            SkusRecyclerAdapter.class,
            SkaRecyclerAdapter.class,
            BasketRecyclerAdapter.class,
            ProductRecyclerAdapter.class,
            HomeRecyclerAdapter.class,
            TweetAdapter.class,
            TweetViewHolder.class,


            // Card View
            ReceipExtraCardView.class,
            FormSectionCardView.class,


            // Impl Interactor
            ClientInteractorImpl.class,
            SellerInteractorImpl.class,
            CatalogueInteractorImpl.class,
            CustomerInteractorImpl.class,
            WishlistInteractorImpl.class,
            OpinionInteractorImpl.class,
            ProductInteractorImpl.class,
            ScannerInteractorImpl.class,
            ReportingInteractorImpl.class,
            BasketInteractorImpl.class,
            TweetSocketIOInteractorImpl.class,
            PaymentInteractor.class,


            // Impl Presenter
            GetConfigPresenterImpl.class,
            GetSellersPresenterImpl.class,
            GetShopsPresenterImpl.class,
            ProvisionDevicePresenterImpl.class,
            AuthenticationPresenterImpl.class,
            CataloguePresenterImpl.class,
            GetProductsPresenterImpl.class,
            CustomerCompletionPresenterImpl.class,
            CustomerSearchPresenterImpl.class,
            GetCustomerInfoPresenterImpl.class,
            CreateCustomerInfoPresenterImpl.class,
            GetSalesHistoryPresenterImpl.class,
            CropPicturePresenterImpl.class,
            ScannerPresenterImpl.class,
            OpinionPresenterImpl.class,
            CustomerInteractorImpl.class,
            GetProductPresenterImpl.class,
            SavePagesViewsPresenterImpl.class,
            SaveReportingWebServicesPresenterImpl.class,
            BasketPresenterImpl.class,
            UpdateCustomerInfoPresenterImpl.class,
            ProductSearchPresenterImpl.class,
            HomeTweetPresenterImpl.class,
            PaymentPresenterImpl.class,


            // Activities
            SplashScreenAcivity.class,
            SellersActivity.class,
            DeviceRegistrationActivity.class,
            AuthenticationActivity.class,
            HomeActivity.class,
            ContactManagerActivity.class,
            CustomerHistoryActivity.class,
            CatalogueActivity.class,
            CatalogueFilterActivity.class,
            ProductActivity.class,
            FullScreenImageGalleryActivity.class,
            CustomerSearchActivity.class,
            CustomerActivity.class,
            StatisticsActivity.class,
            CropActivity.class,
            BasketActivity.class,
            SkaListActivity.class,
            TweetsActivity.class,
            ProductActivity.class,
            InShopCustomerActivity.class,
            PaymentActivity.class,
            SignatureActivity.class,
            ReceipActivity.class,
            BarCodeScannerActivity.class,
            FormDynamicActivity.class,
            FormActivity.class,


            // Fragments
            SplashScreenFragment.class,
            SellersFragment.class,
            DeviceRegistrationFragment.class,
            AuthenticationFragment.class,
            HomeFragment.class,
            HomeTweetsFragment.class,
            HomeTweetsFragmentFirebase.class,
            HomeTweetsFragmentSocketIO.class,
            CustomerHistoryFragment.class,
            InShopCustomerFragment.class,
            ContactManagerFragment.class,
            CatalogueCategoriesFragment.class,
            CatalogueProductsFragment.class,
            CatalogueFilterFragment.class,
            ProductSummaryFragment.class,
            ProductShortDescriptionFragment.class,
            ProductShareActionBarFragment.class,
            ProductModelsListFragment.class,
            ProductLongDescriptionFragment.class,
            ProductsRelatedFragment.class,
            CustomerCompletionFragment.class,
            CustomerSearchPreviewFragment.class,
            CustomerProfileFragment.class,
            CustomerPlaylistFragment.class,
            CustomerWishlistFragment.class,
            CustomerOffersFragment.class,
            CustomerOffersHeaderFragment.class,
            CustomerSalesHistoryFragment.class,
            StatisticsFragment.class,
            CropFragment.class,
            BasketFragment.class,
            OpinionBaseFragment.class,
            SkaListFragment.class,
            InShopCustomerFragment.class,
            TweetsFragment.class,
            TweetsFragmentSocketIO.class,
            TweetsFragmentFirebase.class,
            PaymentFragment.class,
            SignatureFragment.class,
            ReceipFragment.class,
            BarCodeScannerFragment.class,
            FormDynamicFragment.class,
            FormSectionFragment.class,


            // Adapters
            PlaylistProductRecyclerAdapter.class,

            ProductPreviewActivity.class,
            LoginActivityListActivity.class,
            LoginActivityDetailFragment.class,
            LoginActivityDetailActivity.class,

    },
    library = true
)
public class RootModule {

    private final Context context;

    public RootModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public Context provideApplicationContext() {
        return context;
    }

    @Provides
    public LayoutInflater provideLayoutInflater() {
        return LayoutInflater.from(context);
    }
}
