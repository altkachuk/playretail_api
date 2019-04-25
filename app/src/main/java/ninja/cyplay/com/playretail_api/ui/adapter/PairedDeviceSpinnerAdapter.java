package ninja.cyplay.com.playretail_api.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import de.payleven.payment.PairedDevice;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.ui.viewholder.PairedDeviceViewHolder;

/**
 * Created by damien on 06/07/15.
 */
public class PairedDeviceSpinnerAdapter extends ArrayAdapter<PairedDevice> {

    // Your sent context
    private Context context;
    // Your custom values for the spinner (User)
    private List<PairedDevice> values;

    private final LayoutInflater inflater;

    public PairedDeviceSpinnerAdapter(Context context, List<PairedDevice> values) {
        super(context, R.layout.cell_paired_device, values);
        this.context = context;
        this.values = values;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setValues(List<PairedDevice> values) {
        this.values = values;
    }

    public int getCount(){
        return values != null ? values.size() : 0;
    }

    public PairedDevice getItem(int position){
        return values.get(position);
    }

    public long getItemId(int position){
        return position;
    }

    // And the "magic" goes here
    // This is for the "passive" state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    private View getCustomView(int position, View convertView, ViewGroup parent) {
        View row = inflater.inflate(R.layout.cell_paired_device, parent, false);
        final PairedDeviceViewHolder holder = new PairedDeviceViewHolder(row);

        String name = getItem(position).getName();
        holder.deviceName.setText(name);

        return row;
    }

}