package ninja.cyplay.com.playretail_api.ui.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.afollestad.materialdialogs.AlertDialogWrapper;

import javax.inject.Inject;

import ninja.cyplay.com.apilibrary.domain.interactor.OpinionInteractor;
import ninja.cyplay.com.apilibrary.domain.interactor.WishlistInteractor;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.exception.BaseException;
import ninja.cyplay.com.apilibrary.models.business.ECustomerActions;
import ninja.cyplay.com.playretail_api.model.business.Product;
import ninja.cyplay.com.playretail_api.model.singleton.CustomerContext;
import ninja.cyplay.com.apilibrary.utils.Constants;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.app.BasePresenter;
import ninja.cyplay.com.playretail_api.ui.activity.CustomerSearchActivity;
import ninja.cyplay.com.playretail_api.ui.view.OpinionView;

/**
 * Created by damien on 15/05/15.
 */
public class OpinionPresenterImpl extends BasePresenter implements OpinionPresenter {

    @Inject
    CustomerContext customerContext;

    private Context context;
    private OpinionView opinionView;
    private OpinionInteractor opinionInteractor;
    private WishlistInteractor wishlistInteractor;

    // To do last action if customer found
    private Product product = null;
    private ECustomerActions action = null;

    public OpinionPresenterImpl(Context context, OpinionInteractor opinionInteractor, WishlistInteractor wishlistInteractor) {
        super(context);
        this.context = context;
        this.opinionInteractor = opinionInteractor;
        this.wishlistInteractor = wishlistInteractor;
    }

    @Override
    public void setView(OpinionView view) {
        this.opinionView = view;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void executeAction(Activity activity, ECustomerActions action, Product product) {
        if (customerContext != null && customerContext.isCustomerIdentified()) {
            switch (action) {
                case DO_LIKE:
                    like(product);
                    break;
                case DO_DISLIKE:
                    dislike(product);
                    break;
                case DO_WISHLIST:
                    wishlist(product);
                    break;
            }
        }
        else
            askToIdentifyCustomer(activity, product, action);
    }

    @Override
    public void customerFound() {
        if (this.product != null && this.action != null) {
            executeAction(null, action, product);
            this.product = null;
            this.action = null;
        }
    }

    private void askToIdentifyCustomer(final Activity activity, final Product product, final ECustomerActions action) {
        try {
            this.product = product;
            this.action = action;
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            Intent intent = new Intent(activity, CustomerSearchActivity.class);
                            intent.putExtra(Constants.EXTRA_SEARCH_CUSTOMER_CONTEXT, true);
                            activity.startActivityForResult(intent, Constants.RESULT_CUSTOMER_SEARCH_ACTIVITY);
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                }
            };
            new AlertDialogWrapper.Builder(activity)
            .setMessage(activity.getString(R.string.no_client_context))
            .setPositiveButton(activity.getString(R.string.yes_thx), dialogClickListener)
            .setNegativeButton(activity.getString(R.string.no_thx), dialogClickListener)
            .show();
        } catch (Exception e) { }
    }

    @Override
    public void updateOpinionsViews(Product PRProduct) {
        if (customerContext != null && customerContext.isCustomerIdentified()) {
            if (customerContext.isProductInWishlist(PRProduct))
                opinionView.onAddToWishlistSuccess();
            else
                opinionView.onDeleteFromWishlistSuccess();
            if (customerContext.isProductLike(PRProduct))
                opinionView.onLikeSuccess();
            else if (customerContext.isProductDislike(PRProduct))
                opinionView.onDisLikeSuccess();
        }
    }

    public void like(Product PRProduct) {
        if (!customerContext.isProductLike(PRProduct))
            sendProductOpinion(PRProduct, true);
    }

    public void dislike(Product PRProduct) {
        if (!customerContext.isProductDislike(PRProduct))
            sendProductOpinion(PRProduct, false);
    }

    public void wishlist(Product PRProduct) {
        if (customerContext != null && customerContext.isCustomerIdentified()) {
            if (customerContext.isProductInWishlist(PRProduct))
                deleteFromWishlist(PRProduct);
            else
                addToWishlist(PRProduct);
        }
    }

    private void sendProductOpinion(final Product product, final Boolean isLike) {
        opinionView.showLoading();
        if (product != null && customerContext.getCustomer() != null) {
            opinionInteractor.addProductOpinion(isLike, customerContext.getCustomer().getEAN(),
                    product.getSkid(), new OpinionInteractor.addProductCallback() {

                @Override
                public void onSuccess() {
                    opinionView.hideLoading();

                    if (isLike) {
                        if (customerContext != null)
                            customerContext.addLike(product);
                        opinionView.onLikeSuccess();
                    }
                    else {
                        if (customerContext != null)
                            customerContext.addDislike(product);
                        opinionView.onDisLikeSuccess();
                    }
                }

                @Override
                public void onError(BaseException e) {
                    opinionView.hideLoading();
                    switch (e.getType()) {
                        case BUSINESS:
                            if (e.getResponse() != null && e.getResponse().getStatus() != null)
                                opinionView.displaySttPopUp(e.getResponse().getDetail(), e.getResponse().getStatus());
                            break;
                        case TECHNICAL:
                            opinionView.onError();
                    }
                }
            });
        }
    }

    public void addToWishlist(final Product PRProduct) {
        opinionView.showLoading();
        if (PRProduct != null && customerContext.getCustomer() != null) {
            wishlistInteractor.addProductWishlist(customerContext.getCustomer().getEAN(),
                    PRProduct.getSkid(), new WishlistInteractor.addProductCallback() {

                @Override
                public void onSuccess() {
                    if (customerContext != null)
                        customerContext.addProductToWishlist(PRProduct);
                    opinionView.hideLoading();
                    opinionView.onAddToWishlistSuccess();
                }

                @Override
                public void onError(BaseException e) {
                    opinionView.hideLoading();
                    switch (e.getType()){
                        case BUSINESS:
                            if (e.getResponse() != null && e.getResponse().getStatus() != null)
                                opinionView.displaySttPopUp(e.getResponse().getDetail(),e.getResponse().getStatus());
                            break;

                        case TECHNICAL:
                            opinionView.onError();
                            break;
                    }
                }

            });
        }
    }

    public void deleteFromWishlist(final Product PRProduct) {
        opinionView.showLoading();
        if (PRProduct != null && customerContext.getCustomer() != null) {
            wishlistInteractor.deleteProductWishlist(customerContext.getCustomer().getEAN(),
                    PRProduct.getSkid(), new WishlistInteractor.deleteProductCallback() {

                        @Override
                        public void onSuccess() {
                            if (customerContext != null)
                                customerContext.deleteProductFromWishlist(PRProduct);
                            opinionView.hideLoading();
                            opinionView.onDeleteFromWishlistSuccess();
                        }

                        @Override
                        public void onError(BaseException e) {
                            opinionView.hideLoading();
                            switch (e.getType()){
                                case BUSINESS:
                                    if (e.getResponse() != null && e.getResponse().getStatus() != null)
                                        opinionView.displaySttPopUp(e.getResponse().getDetail(),e.getResponse().getStatus());
                                    break;

                                case TECHNICAL:
                                    opinionView.onError();
                                    break;
                            }
                        }

                    });
        }
    }

    @Override
    public void onViewCreate() {

    }

    @Override
    public void onViewResume() {
        //Do nothing
    }

    @Override
    public void onViewDestroy() {
        //Do nothing
    }

}