package ninja.cyplay.com.playretail_api.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ninja.cyplay.com.apilibrary.MVPCleanArchitectureApplication;
import ninja.cyplay.com.playretail_api.model.business.Product;
import ninja.cyplay.com.playretail_api.model.business.SalesHistory;
import ninja.cyplay.com.playretail_api.model.singleton.CustomerContext;
import ninja.cyplay.com.playretail_api.utils.ColorUtils;
import ninja.cyplay.com.playretail_api.utils.ImageUtils;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.ui.viewholder.ProductViewHolder;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

public class SalesHistoryAdapter extends BaseSwipeAdapter implements StickyListHeadersAdapter, SectionIndexer {

    @Inject
    CustomerContext customerContext;

    private List<SalesHistory> PRSalesHistory;
    private final List<Product> PRProducts = new ArrayList<>();

    private final Activity activity;
    private final LayoutInflater inflater;

    private int[] mSectionIndices;
    private String[] mSectionLetters;

    private final static SimpleDateFormat OUT_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

    public SalesHistoryAdapter(Activity activity, List<SalesHistory> PRSalesHistory) {
        inflater = LayoutInflater.from(activity);
        this.PRSalesHistory = PRSalesHistory;
        this.activity = activity;
        // For injection
        ((MVPCleanArchitectureApplication) activity.getApplication()).inject(this);
        setPRSalesHistory(this.PRSalesHistory);
    }

    public void setPRSalesHistory(List<SalesHistory> PRSalesHistory) {
        this.PRSalesHistory = PRSalesHistory;
        if (PRProducts != null && PRProducts.size() > 0)
            PRProducts.clear();
        if (PRSalesHistory != null) {
            for (int i = 0 ; i < PRSalesHistory.size() ; i++) {
                SalesHistory s = PRSalesHistory.get(i);
                if (PRProducts != null && s.getPl() != null)
                    PRProducts.addAll(s.getPl());
            }
            mSectionIndices = getSectionIndices();
            mSectionLetters = getSectionTitle();
        }
    }

    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    private int[] getSectionIndices() {
        List<Integer> sizes = new ArrayList<>();
        if (PRSalesHistory != null)
            for (int i = 0 ; i < PRSalesHistory.size() ; i++)
                if (PRSalesHistory.get(i).getPl() != null)
                    sizes.add(PRSalesHistory.get(i).getPl().size());
                else
                    sizes.add(0);

        int[] sections = new int[sizes.size()];
        for (int i = 0; i < sizes.size(); i++) {
            sections[i] = sizes.get(i);
        }
        return sections;
    }

    private String[] getSectionTitle() {
        List<String> dates = new ArrayList<>();
        if (PRSalesHistory != null)
            for (int i = 0 ; i < PRSalesHistory.size() ; i++)
                dates.add(PRSalesHistory.get(i).getDate());
        return dates.toArray(new String[dates.size()]);
    }

    // -------------------------------------------------------------------------------------------
    //                                      Adater (Impl)
    // -------------------------------------------------------------------------------------------

    @Override
    public int getCount() {
        return PRProducts != null ? PRProducts.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return PRProducts != null ? PRProducts.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object[] getSections() {
        return mSectionLetters;
    }

    @Override
    public int getPositionForSection(int section) {
        if (mSectionIndices.length == 0) {
            return 0;
        }
        if (section >= mSectionIndices.length) {
            section = mSectionIndices.length - 1;
        } else if (section < 0) {
            section = 0;
        }
        return mSectionIndices[section];
    }

    @Override
    public int getSectionForPosition(int position) {
        for (int i = 0 ; i < mSectionIndices.length ; i++) {
            if (position < mSectionIndices[i]) {
                return i;
            }
        }
        return mSectionIndices.length - 1;
    }

    // -------------------------------------------------------------------------------------------
    //                                      Header
    // -------------------------------------------------------------------------------------------


    @Override
    public long getHeaderId(int i) {
        return i;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        View vi = inflater.inflate(R.layout.customer_product_list_header, null);

        TextView tv = (TextView) vi.findViewById(R.id.title_text_view);
        ColorUtils.setTextColor(tv, ColorUtils.FeatureColor.PRIMARY_DARK);

        if (PRSalesHistory.get(getSectionForPosition(position)) != null)
            tv.setText(OUT_FORMAT.format(PRSalesHistory.get(getSectionForPosition(position)).getDateObject()));

        return vi;
    }

    // -------------------------------------------------------------------------------------------
    //                                      Swipe Impl
    // -------------------------------------------------------------------------------------------


    @Override
    public int getSwipeLayoutResourceId(int i) {
        return R.id.swipe_layout;
    }

    @Override
    public View generateView(int i, ViewGroup viewGroup) {
        return inflater.inflate(R.layout.cell_product, viewGroup, false);
    }

    @Override
    public void fillValues(int position, View view) {
        boolean disableRightSwipe = false;
        boolean disableLeftSwipe = true;

        final Product PRProduct = (Product) getItem(position);
        final ProductViewHolder holder = new ProductViewHolder(view);

        // to be able to link likes and dislikes
        holder.setProduct(PRProduct);
        holder.setContext(activity);
        holder.activateClick();

        holder.resetColorIcons();
        if (customerContext != null && customerContext.isProductLike(PRProduct))
            holder.productLike();
        else if (customerContext != null && customerContext.isProductDislike(PRProduct))
            holder.productDislike();

        ColorUtils.setTextColor(holder.cat, ColorUtils.FeatureColor.PRIMARY_LIGHT);
        holder.cat.setText(PRProduct.cat);
        holder.pn.setText(PRProduct.pn);
        ColorUtils.setTextColor(holder.bd, ColorUtils.FeatureColor.NEUTRAL_DARK);
        holder.bd.setText(PRProduct.skd);

        if (disableLeftSwipe && disableRightSwipe)
            holder.swipeLayout.setSwipeEnabled(false);
        else {
            holder.swipeLayout.setRightSwipeEnabled(!disableRightSwipe);
            holder.swipeLayout.setLeftSwipeEnabled(!disableLeftSwipe);
        }
        if (PRSalesHistory != null) {
            if (PRSalesHistory.size() > getSectionForPosition(position)) {
                SalesHistory s = PRSalesHistory.get(getSectionForPosition(position));
                if (s != null && s.getSta() != null && s.getSta() == 1)
                    holder.swipeLayout.setLeftSwipeEnabled(true);
            }
        }

        Picasso.with(activity)
                .load(ImageUtils.getImageUrl(PRProduct.getImg(), "sd"))
                .placeholder(R.drawable.img_placeholder)
                .into(holder.productCellPicture);
    }
}
