package ninja.cyplay.com.playretail_api.ui.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ninja.cyplay.com.playretail_api.R;

/**
 * Created by damien on 07/05/15.
 */
public class OfferViewHolder {

    @InjectView(R.id.image_view)
    public ImageView imageView;

    @InjectView(R.id.offer_sub_text)
    public TextView offerSubText;

    @InjectView(R.id.offer_text)
    public TextView offerText;

    public OfferViewHolder(View view) {
        ButterKnife.inject(this, view);
    }

}
