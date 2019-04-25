package ninja.cyplay.com.apilibrary.models.business;

import android.content.Context;

import ninja.cyplay.com.apilibrary.R;

public enum OfferState {
    OFFER_1,
    OFFER_2,
    OFFER_3,
    OFFER_4,
    OFFER_5;

    public static String[] labels;

    public static String[] getLabels(Context context) {
        if (labels == null) {
            labels = new String[OfferState.values().length];
            labels[OFFER_1.ordinal()] = context.getString(R.string.offer_1);
            labels[OFFER_2.ordinal()] = context.getString(R.string.offer_2);
            labels[OFFER_3.ordinal()] = context.getString(R.string.offer_3);
            labels[OFFER_4.ordinal()] = context.getString(R.string.offer_4);
            labels[OFFER_5.ordinal()] = context.getString(R.string.offer_5);
        }
        return labels;
    }
}
