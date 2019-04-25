package ninja.cyplay.com.playretail_api.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;
import ninja.cyplay.com.playretail_api.R;

/**
 * Created by damien on 06/05/15.
 */
public class CustomePreviewViewHolder extends RecyclerView.ViewHolder {

    @Optional
    @InjectView(R.id.customer_preview_name)
    public TextView name;

    public CustomePreviewViewHolder(View view) {
        super(view);
        ButterKnife.inject(this, view);
    }

}
