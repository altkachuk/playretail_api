package ninja.cyplay.com.playretail_api.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;
import ninja.cyplay.com.playretail_api.R;

/**
 * Created by damien on 30/04/15.
 */
public class CustomerHistoryViewHolder  extends RecyclerView.ViewHolder {

    @Optional
    @InjectView(R.id.name_text)
    public TextView name;

    @Optional
    @InjectView(R.id.location_text)
    public TextView location;

    @Optional
    @InjectView(R.id.location_city_text)
    public TextView location_city;

    @Optional
    @InjectView(R.id.customer_in_house_image_view)
    public ImageView inHouse;

    public CustomerHistoryViewHolder(View view) {
        super(view);
        ButterKnife.inject(this, view);
    }
}
