package ninja.cyplay.com.playretail_api.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;
import ninja.cyplay.com.playretail_api.R;

public class HomeViewHolder extends RecyclerView.ViewHolder {

    @Optional
    @InjectView(R.id.home_tile_root)
    public View root;

    @Optional
    @InjectView(R.id.image_frame)
    public View imageFrame;

    @Optional
    @InjectView(R.id.home_tile_icon)
    public ImageView homeIcon;

    @Optional
    @InjectView(R.id.home_tile_title)
    public TextView homeTitle;

    @Optional
    @InjectView(R.id.badger)
    public ImageView badger;

    @Optional
    @InjectView(R.id.home_title_background)
    public View bottomBorder;

    public HomeViewHolder(View view) {
        super(view);
        ButterKnife.inject(this, view);
    }
}
