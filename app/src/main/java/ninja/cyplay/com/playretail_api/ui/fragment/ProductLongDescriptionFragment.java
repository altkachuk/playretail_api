package ninja.cyplay.com.playretail_api.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Optional;
import ninja.cyplay.com.playretail_api.model.business.Product;
import ninja.cyplay.com.playretail_api.model.business.Sku;
import ninja.cyplay.com.apilibrary.utils.DialogUtils;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.app.BaseFragment;
import ninja.cyplay.com.playretail_api.model.business.EFeature;
import ninja.cyplay.com.playretail_api.ui.activity.ProductActivity;
import ninja.cyplay.com.playretail_api.ui.custom.MessageProgressDialog;
import ninja.cyplay.com.playretail_api.ui.presenter.BasketPresenter;
import ninja.cyplay.com.playretail_api.ui.view.BasketView;
import ninja.cyplay.com.playretail_api.utils.ColorUtils;
import ninja.cyplay.com.playretail_api.utils.ConfigHelper;
import ninja.cyplay.com.playretail_api.utils.SkuUtils;

/**
 * Created by damien on 08/03/16.
 */
public class ProductLongDescriptionFragment extends BaseFragment implements BasketView {

    @Inject
    BasketPresenter basketPresenter;

    @Optional
    @InjectView(R.id.product_summary_model)
    TextView model;

    @Optional
    @InjectView(R.id.product_summary_desc)
    TextView desc;

    @Optional
    @InjectView(R.id.product_sku_add_cart)
    TextView skuAddCart;

    @Optional
    @InjectView(R.id.product_sku_price)
    TextView skuPrice;

    @Optional
    @InjectView(R.id.buy_more_option_button)
    ImageView buyMoreOption;

    private MessageProgressDialog progress;

    private Product product;


    // -------------------------------------------------------------------------------------------
    //                                      Lifecycle
    // -------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_long_description, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        product = ((ProductActivity) getActivity()).product;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // init presenter
        basketPresenter.initialize();
        basketPresenter.setView(this);

        // update Design
        ColorUtils.setBackgroundColor(skuAddCart, ColorUtils.FeatureColor.PRIMARY_LIGHT);
//        ColorUtils.setTextColor(skuPrice, ColorUtils.FeatureColor.PRIMARY_LIGHT);

        showProductInformations();
    }

    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------


    public void showProductInformations() {
        if (product != null) {
            String sizes = "";
            if (product.getSkl() != null)
                for (int i = 0; i < product.getSkl().size(); i++) {
                    Sku sku = product.getSkl().get(i);

                    if (i == 0)
                        sizes += "<b>" + sku.skd + "</b>";
                    else
                        sizes += sku.skd;
                    if (i + 1 < product.getSkl().size())
                        sizes += ", ";
                }
            model.setText(Html.fromHtml(getResources().getString(R.string.product_model) + ": " + sizes));

            // set Desc
            desc.setText(product.desc);
            skuAddCart.setText(getContext().getString(R.string.sku_add_to_cart));

            if (product.skl != null && product.skl.size() > 0) {
                Sku sku = product.skl.get(0);
                skuPrice.setText(SkuUtils.priceFormat.format(SkuUtils.getPriceForSku(sku)) + " €");
            }
            updateButtonsVisibility();
        }
    }

    private void updateButtonsVisibility() {
        if (ConfigHelper.getInstance().isFeatureActivated(EFeature.BASKET))
        {
            buyMoreOption.setVisibility(View.GONE);
            skuAddCart.setVisibility(View.GONE);
        }
    }

    private void addToBasket() {
        if (product != null && product.skl != null && product.skl.size() > 0) {
            Sku sku = product.skl.get(0);
            basketPresenter.addOneQuantityItemToContextBasket(product, sku);
        }
    }

    // -------------------------------------------------------------------------------------------
    //                                      View Impl
    // -------------------------------------------------------------------------------------------

    @Override
    public void onSellerBasketSuccess() {

    }

    @Override
    public void onSellerBasketError() {

    }

    @Override
    public void onSyncSuccess() {
        if (getActivity() != null && product != null && product.skl != null && product.skl.size() > 0) {
            Toast.makeText(getActivity(), product.skl.get(0).skd + " "
                    + getActivity().getString(R.string.add_to_basket), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSyncError() {

    }

    @Override
    public void onDeleteSuccess() {

    }

    @Override
    public void onDeleteError() {

    }

    @Override
    public void showLoading() {
        if (progress != null)
            progress.show();
    }

    @Override
    public void hideLoading() {
        if (progress != null)
            progress.dismiss();
    }

    @Override
    public void displaySttPopUp(String message, String stt) {

    }

    @Override
    public void displayPopUp(String message) {
        if (getActivity() != null)
            DialogUtils.showDialog(getActivity(), getActivity().getString(R.string.warning), message);
    }

    @Override
    public void onError() {

    }

    // -------------------------------------------------------------------------------------------
    //                                      Action(s)
    // -------------------------------------------------------------------------------------------

    @Optional
    @OnClick(R.id.product_sku_add_cart)
    void onPriceClick() {
        YoYo.with(Techniques.Pulse).duration(300).playOn(skuAddCart);
        addToBasket();
    }

    @Optional
    @OnClick(R.id.buy_more_option_button)
    void onMoreOptionClick() {
        YoYo.with(Techniques.Pulse).duration(300).playOn(buyMoreOption);
        if (product != null && product.skl != null && product.skl.size() > 0)
            basketPresenter.moreOption(getActivity(), product, product.skl.get(0));
    }

}
