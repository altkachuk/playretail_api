package ninja.cyplay.com.playretail_api.ui.viewholder;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SwipeLayout;
import com.squareup.otto.Bus;

import org.parceler.Parcels;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Optional;
import ninja.cyplay.com.apilibrary.MVPCleanArchitectureApplication;
import ninja.cyplay.com.apilibrary.models.business.PR_BasketItem;
import ninja.cyplay.com.playretail_api.model.business.CartItem;
import ninja.cyplay.com.playretail_api.model.events.BasketItemDeletePressedEvent;
import ninja.cyplay.com.playretail_api.model.events.BasketItemMinusPressedEvent;
import ninja.cyplay.com.playretail_api.model.events.BasketItemPlusPressedEvent;
import ninja.cyplay.com.apilibrary.utils.Constants;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.ui.activity.ProductActivity;

/**
 * Created by damien on 26/05/15.
 */
public class BasketViewHolder extends RecyclerView.ViewHolder {

    @Inject
    Bus bus;

    @Optional
    @InjectView(R.id.swipe_layout)
    public SwipeLayout swipeLayout;

    @Optional
    @InjectView(R.id.swipe_view_right)
    public LinearLayout rightView;

    @Optional
    @InjectView(R.id.swipe_view_left)
    public LinearLayout leftView;

    @Optional
    @InjectView(R.id.button_delete)
    public ImageView deleteButton;

    @Optional
    @InjectView(R.id.button_plus)
    public ImageView plusButton;

    @Optional
    @InjectView(R.id.button_minus)
    public ImageView minusButton;

    @Optional
    @InjectView(R.id.product_cell_cat)
    public TextView cat;

    @Optional
    @InjectView(R.id.product_cell_pn)
    public TextView pn;

    @Optional
    @InjectView(R.id.product_cell_size)
    public TextView size;

    @Optional
    @InjectView(R.id.product_quantity)
    public TextView quantity;

    @Optional
    @InjectView(R.id.product_cell_price)
    public TextView price;

    @Optional
    @InjectView(R.id.product_cell_picture)
    public ImageView productCellPicture;

    private PR_BasketItem basketItem;

    private Activity activity;

    public BasketViewHolder(View view) {
        super(view);
        ButterKnife.inject(this, view);
        swipeLayout.addDrag(SwipeLayout.DragEdge.Left, leftView);
        swipeLayout.addDrag(SwipeLayout.DragEdge.Right, rightView);
    }

    public void setContext(Activity activity) {
        if (activity != null) {
            this.activity = activity;
            ((MVPCleanArchitectureApplication)activity.getApplication()).inject(this);
        }
    }

    public void setBasketItem(PR_BasketItem basketItem) {
        this.basketItem = basketItem;
    }

    @Optional
    @OnClick(R.id.button_delete)
    void delete() {
        if (basketItem != null) {
            YoYo.with(Techniques.Pulse).duration(300).playOn(deleteButton);
            bus.post(new BasketItemDeletePressedEvent(basketItem));
        }
    }

    @Optional
    @OnClick(R.id.button_minus)
    void minus() {
        if (basketItem != null) {
            YoYo.with(Techniques.Pulse).duration(300).playOn(minusButton);
            bus.post(new BasketItemMinusPressedEvent(basketItem));
        }
    }

    @Optional
    @OnClick(R.id.button_plus)
    void plus() {
        if (basketItem != null) {
            YoYo.with(Techniques.Pulse).duration(300).playOn(plusButton);
            bus.post(new BasketItemPlusPressedEvent(basketItem));
        }
    }

    @Optional
    @OnClick(R.id.card_view)
    void onProductClick() {
//        if (basketItem != null && basketItem.product != null) {
//            Intent intent = new Intent(activity, ProductActivity.class);
//            intent.putExtra(Constants.EXTRA_PRODUCT, Parcels.wrap(basketItem.product));
//            activity.startActivity(intent);
//        }
    }

}
