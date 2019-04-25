package ninja.cyplay.com.playretail_api.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import ninja.cyplay.com.playretail_api.model.business.Offer;
import ninja.cyplay.com.apilibrary.models.business.OfferState;
import ninja.cyplay.com.playretail_api.utils.ColorUtils;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.ui.viewholder.OfferViewHolder;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

public class OfferAdapter extends BaseAdapter implements StickyListHeadersAdapter, SectionIndexer {

    private final static SimpleDateFormat OUT_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    private final Activity activity;
    private LayoutInflater inflater;
    private List<Offer> offers;
    private int[] mSectionIndices;
    private String[] mSectionLetters;

    public OfferAdapter(Activity activity, List<Offer> offs) {
        inflater = LayoutInflater.from(activity);
        this.offers = filterWrongsOffers(offs);
        this.activity = activity;
        mSectionIndices = getSectionIndices();
        mSectionLetters = getSectionLetters();
    }

    private List<Offer> filterWrongsOffers(List<Offer> offs) {
        if (offs == null)
            return null;
        List<Offer> filteredOffers = new ArrayList<Offer>();
        for (int i = 0; i < offs.size(); i++) {
            Offer o = offs.get(i);
            if (o.ot != null)
                if (OfferState.values().length > o.ot.intValue() - 1) // delete offers that aren't in the enum
                    filteredOffers.add(o);
        }
        return filteredOffers;
    }

    public void setOffers(List<Offer> offs) {
        this.offers = filterWrongsOffers(offs);
    }

    @Override
    public int getCount() {
        return offers != null ? offers.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return offers != null && offers.size() > position ? offers.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Offer offer = (Offer) getItem(position);
        OfferViewHolder holder;

        if (convertView != null) {
            holder = (OfferViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.cell_offer, parent, false);
            holder = new OfferViewHolder(convertView);
            convertView.setTag(holder);
        }

        StringBuilder offerString = new StringBuilder();

        offerString.append(activity.getString(R.string.offer_valid));
        if (offer.getSdDateObject() != null)
            offerString.append(OUT_FORMAT.format(offer.getSdDateObject()));
        offerString.append(activity.getString(R.string.offer_until));
        if (offer.getEdDateObject() != null)
            offerString.append(OUT_FORMAT.format(offer.getEdDateObject()));

        holder.offerText.setText(offerString.toString());

        int imageId = -1;
        int section = getSectionForPosition(position);

        if (section == OfferState.OFFER_1.ordinal())
            imageId = R.drawable.sample_offer;
        else if (section == OfferState.OFFER_2.ordinal())
            imageId = R.drawable.sample_offer_gold;
        else if (section == OfferState.OFFER_3.ordinal())
            imageId = R.drawable.sample_offer_anniversary;
        else if (section == OfferState.OFFER_4.ordinal())
            imageId = R.drawable.sample_offer_special;
        else if (section == OfferState.OFFER_5.ordinal())
            imageId = R.drawable.sample_offer_gift;

        Picasso.with(activity)
                .load(imageId)
                .placeholder(R.drawable.img_placeholder)
                .into(holder.imageView);

        if (offer.desc != null && offer.desc.length() > 0) {
            holder.offerSubText.setVisibility(View.VISIBLE);
            holder.offerSubText.setText(offer.desc);
            ColorUtils.setTextColor(holder.offerSubText, ColorUtils.FeatureColor.PRIMARY_DARK);
        } else
            holder.offerSubText.setVisibility(View.GONE);

        return convertView;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        View vi = inflater.inflate(R.layout.customer_product_list_header, null);

        TextView tv = (TextView) vi.findViewById(R.id.title_text_view);
        ColorUtils.setTextColor(tv, ColorUtils.FeatureColor.PRIMARY_DARK);

        if (OfferState.values().length > getSectionForPosition(position))
            tv.setText(OfferState.getLabels(activity)[getSectionForPosition(position)]);

        return vi;
    }

    @Override
    public long getHeaderId(int position) {
        //return the first character of the country as ID because this is what headers are based upon
        int headerPosition = getSectionForPosition(position);
        return getSectionForPosition(headerPosition);
    }

    @Override
    public Object[] getSections() {
        return mSectionLetters;
    }

    private int[] getSectionIndices() {
        ArrayList<Integer> sectionIndices = new ArrayList<Integer>();

        int index = 0;
        int lastIndex = 0;
        for (int i = 0; i < OfferState.getLabels(activity).length; i++) {
            for (int j = 0; offers != null && j < offers.size(); j++) {
                if (offers.get(j).ot != null && offers.get(j).ot.intValue() == (i + 1))
                    index++;
            }
            if (index > 0 && lastIndex != index)
                sectionIndices.add(index);
            lastIndex = index;
        }
        int[] sections = new int[sectionIndices.size()];
        for (int i = 0; i < sectionIndices.size(); i++) {
            // remove 1 to re-set position to 0
            // because it found 1 element, but it's the first
            sections[i] = sectionIndices.get(i) - 1;
        }
        return sections;
    }

    private String[] getSectionLetters() {
        List<String> labels = new ArrayList<String>();

        for (int i = 0; i < OfferState.getLabels(activity.getApplicationContext()).length; i++) {
            int count = 0;
            for (int j = 0; offers != null && j < offers.size(); j++) {
                if (offers.get(j).ot != null && offers.get(j).ot.intValue() == (i + 1))
                    count++;
            }
            if (count > 0)
                labels.add(OfferState.getLabels(activity.getApplicationContext())[i]);
        }
        String[] labelsArr = new String[labels.size()];
        labels.toArray(labelsArr);
        return labelsArr;
    }

    @Override
    public int getSectionForPosition(int position) {
        Offer offer = (Offer) getItem(position);
        if (offer != null && offer.ot != null
                && (offer.ot.intValue() - 1) > 0
                && (offer.ot.intValue() - 1) < OfferState.values().length) {
            return OfferState.values()[offer.ot.intValue() - 1].ordinal();
        }
        return 0;
    }

    @Override
    public int getPositionForSection(int section) {
        if (section >= mSectionIndices.length) {
            section = mSectionIndices.length - 1;
        } else if (section < 0) {
            section = 0;
        }
        return mSectionIndices[section];
    }


}