package ninja.cyplay.com.playretail_api.ui.viewholder;

import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;
import ninja.cyplay.com.playretail_api.R;

/**
 * Created by damien on 06/07/15.
 */
public class PairedDeviceViewHolder {

    @Optional
    @InjectView(R.id.device_name)
    public TextView deviceName;

    public PairedDeviceViewHolder(View view) {
        ButterKnife.inject(this, view);
    }

}
