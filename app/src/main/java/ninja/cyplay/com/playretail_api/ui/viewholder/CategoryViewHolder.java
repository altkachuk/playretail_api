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
 * Created by damien on 04/05/15.
 */
public class CategoryViewHolder extends RecyclerView.ViewHolder {

    @Optional
    @InjectView(R.id.root)
    public View root;

    @Optional
    @InjectView(R.id.category_text_view)
    public TextView name;

//    @Optional
//    @InjectView(R.id.category_text_bg)
//    public View categoryTextBg;

    @Optional
    @InjectView(R.id.category_initial)
    public TextView category_initial;

    @Optional
    @InjectView(R.id.category_image)
    public ImageView image;

    public CategoryViewHolder(View view) {
        super(view);
        ButterKnife.inject(this, view);
    }

}
