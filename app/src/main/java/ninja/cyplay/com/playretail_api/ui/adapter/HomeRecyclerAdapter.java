package ninja.cyplay.com.playretail_api.ui.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import ninja.cyplay.com.apilibrary.MVPCleanArchitectureApplication;
import ninja.cyplay.com.apilibrary.models.singleton.TweetCacheManager;
import ninja.cyplay.com.playretail_api.model.singleton.SellerContext;
import ninja.cyplay.com.playretail_api.utils.ColorUtils;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.model.cell.HomeTile;
import ninja.cyplay.com.playretail_api.ui.viewholder.HomeViewHolder;

/**
 * Created by damien on 01/07/15.
 */
public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeViewHolder> {

    @Inject
    SellerContext sellerContext;

    @Inject
    TweetCacheManager tweetCacheManager;

    private Activity activity;

    private final List<HomeTile> homeTiles;

    public HomeRecyclerAdapter(Activity activity, List<HomeTile> homeTiles) {
        this.activity = activity;
        this.homeTiles = homeTiles;
        // For injection
        ((MVPCleanArchitectureApplication)activity.getApplication()).inject(this);
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_home_tile, parent, false);
        HomeViewHolder holder = new HomeViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(HomeViewHolder holder, int position) {
        HomeTile tile = homeTiles.get(position);
        updateDesign(holder, tile);
        holder.homeTitle.setText(tile.getTitle());
        updateBadger(holder, tile);
        Picasso.with(activity)
                .load(tile.getDrawableUri())
                .into(holder.homeIcon);
    }

    private void updateDesign(HomeViewHolder holder, HomeTile tile ) {
        // Colorize stuff
//        int color = ColorUtils.generateRandomColorDark();
         int color = Color.parseColor(ColorUtils.FeatureColor.getColorHex(ColorUtils.FeatureColor.PRIMARY_DARK));
//        try {
//            GradientDrawable gd = (GradientDrawable) holder.root.getBackground();
//            int width_px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, activity.getResources().getDisplayMetrics());
//            gd.setStroke(width_px, color);
//        } catch (Exception e) { e.printStackTrace(); }
        ColorUtils.setBackgroundColor(holder.bottomBorder, color);
        ColorUtils.applyLightningColorFilter(holder.homeIcon, color);
//        ColorUtils.setTextColor(holder.homeTitle, ColorUtils.FeatureColor.PRIMARY_DARK);
//        ColorUtils.setBackgroundColor(holder.imageFrame, ColorUtils.lighter(color, 0.4f));
    }

    private void updateBadger(HomeViewHolder holder, HomeTile tile) {
//        if (tile.getAction().ordinal() == HomeTile.ETileAction.TWEETS.ordinal()) {
//            //TODO get Tweets Count
//            holder.badger.setVisibility(View.VISIBLE);
//            Glide.with(activity)
//                    .load(getBadgerDrawableFromInteger(5))
//                    .into(holder.badger);
//        }
//        else if (tile.getAction().ordinal() == HomeTile.ETileAction.CHISTORY.ordinal())

        if (tile.getAction().ordinal() == HomeTile.ETileAction.TWEETS.ordinal())
            updateTweetBadger(holder, tile);
        else if (tile.getAction().ordinal() == HomeTile.ETileAction.CHISTORY.ordinal())
            updateCustomerHistoryBadger(holder, tile);
        else if (tile.getAction().ordinal() == HomeTile.ETileAction.INSHOP.ordinal())
            updateCustomerHistoryBadger(holder, tile);
        else
            holder.badger.setVisibility(View.GONE);
    }

    private void updateCustomerHistoryBadger(HomeViewHolder holder, HomeTile tile) {
        if (sellerContext.getCustomer_history() != null && sellerContext.getCustomer_history().size() > 0) {
            holder.badger.setVisibility(View.VISIBLE);
            Picasso.with(activity)
                    .load(getBadgerDrawableFromInteger(sellerContext.getCustomer_history().size()))
                    .into(holder.badger);
        }
        else
            holder.badger.setVisibility(View.GONE);
    }

    private void updateTweetBadger(HomeViewHolder holder, HomeTile tile) {
        if (tweetCacheManager != null && tweetCacheManager.getTweetCache() != null) {
            int nb_tweet = tweetCacheManager.getNBNotReadTweetFromCache();
            if (nb_tweet > 0) {
                holder.badger.setVisibility(View.VISIBLE);
                Picasso.with(activity)
                        .load(getBadgerDrawableFromInteger(nb_tweet))
                        .into(holder.badger);
            }
            else
                holder.badger.setVisibility(View.GONE);
        }
        else
            holder.badger.setVisibility(View.GONE);
    }

    private int getBadgerDrawableFromInteger(int quantity) {
        if (quantity > 9)
            return R.drawable.ic_numeric_9_plus_box_outline_black_24dp;
        switch (quantity) {
            case 1:
                return R.drawable.ic_numeric_1_box_outline_black_24dp;
            case 2:
                return R.drawable.ic_numeric_2_box_outline_black_24dp;
            case 3:
                return R.drawable.ic_numeric_3_box_outline_black_24dp;
            case 4:
                return R.drawable.ic_numeric_4_box_outline_black_24dp;
            case 5:
                return R.drawable.ic_numeric_5_box_outline_black_24dp;
            case 6:
                return R.drawable.ic_numeric_6_box_outline_black_24dp;
            case 7:
                return R.drawable.ic_numeric_7_box_outline_black_24dp;
            case 8:
                return R.drawable.ic_numeric_8_box_outline_black_24dp;
            case 9:
                return R.drawable.ic_numeric_9_box_outline_black_24dp;
        }
        return R.drawable.ic_numeric_0_box_outline_black_24dp;
    }

    @Override
    public int getItemCount() {
        return homeTiles != null ? homeTiles.size() : 0;
    }
}

