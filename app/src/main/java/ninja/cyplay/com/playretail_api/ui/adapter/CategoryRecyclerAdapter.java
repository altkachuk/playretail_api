package ninja.cyplay.com.playretail_api.ui.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import ninja.cyplay.com.playretail_api.model.business.Category;
import ninja.cyplay.com.playretail_api.utils.ColorUtils;
import ninja.cyplay.com.playretail_api.utils.ImageUtils;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.ui.viewholder.CategoryViewHolder;

/**
 * Created by damien on 12/06/15.
 */
public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryViewHolder> {

    private Activity activity;

    private List<Category> categories;

    public CategoryRecyclerAdapter(Activity activity, List<Category> categories) {
        this.activity = activity;
        this.categories = categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_category, parent, false);
        CategoryViewHolder holder = new CategoryViewHolder(v);
//         int color = Color.parseColor(ColorUtils.FeatureColor.getColorHex(ColorUtils.FeatureColor.PRIMARY_LIGHT));
//        try {
//            GradientDrawable gd = (GradientDrawable) holder.root.getBackground();
//            int width_px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, activity.getResources().getDisplayMetrics());
//            gd.setStroke(width_px, color);
//        } catch (Exception e) { e.printStackTrace(); }
        ColorUtils.setTextColor(holder.category_initial, ColorUtils.FeatureColor.PRIMARY_DARK);
        ColorUtils.setBackgroundColor(holder.root, ColorUtils.FeatureColor.PRIMARY_LIGHT);
        return holder;
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        Category PRCategory = categories.get(position);
        if (PRCategory.getCat_image() != null && PRCategory.getCat_image().length() > 0) {
            Picasso.with(activity)
                    .load(ImageUtils.getImageUrl(PRCategory.getCat_image(), "sd"))
                    .placeholder(R.drawable.img_placeholder)
                    .into(holder.image);
            holder.category_initial.setVisibility(View.GONE);
            holder.image.setVisibility(View.VISIBLE);
        }
        else  {
            holder.category_initial.setText(String.valueOf(PRCategory.getCat_lab().substring(0, 1)));
            holder.category_initial.setVisibility(View.VISIBLE);
            holder.image.setVisibility(View.GONE);
        }
        holder.name.setText(PRCategory.getCat_lab());
        holder.name.invalidate();
    }

    @Override
    public int getItemCount() {
        return categories != null ? categories.size() : 0;
    }
}
