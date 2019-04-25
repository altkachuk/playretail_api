package ninja.cyplay.com.apilibrary.models.meta;

import android.content.Context;

import java.util.Comparator;

/**
 * Created by romainlebouc on 18/08/16.
 */
public class ResourceFieldSorting {

    final String field;
    final int labelResourceId;
    final ESortingWay sortingWay;
    final Comparator comparator;
    String label;

    public ResourceFieldSorting(String field, int labelResourceId, ESortingWay sortingWay, Comparator comparator) {
        this.field = field;
        this.labelResourceId = labelResourceId;
        this.sortingWay = sortingWay;
        this.comparator = comparator;
    }

    public ResourceFieldSorting(String field, int labelResourceId, ESortingWay sortingWay) {
        this(field, labelResourceId, sortingWay, null);
    }

    public ResourceFieldSorting(String field, String label, ESortingWay sortingWay) {
        this(field, -1, sortingWay, null);
        this.label = label;
    }

    public ResourceFieldSorting(String field, String label, ESortingWay sortingWay, Comparator comparator) {
        this.labelResourceId = -1;
        this.field = field;
        this.sortingWay = sortingWay;
        this.label = label;
        this.comparator = comparator;
    }

    public String getLabel(Context context) {
        // atproj: huck of null label
        this.label = "Test";

        if (label != null) {
            return label;
        } else {
            return context.getString(labelResourceId);
        }

    }

    public String getField() {
        return field;
    }

    public ESortingWay getSortingWay() {
        return sortingWay;
    }

    public String getSortingRequest() {
        String result = null;
        if (field != null) {
            result = ESortingWay.DESC == sortingWay ? "-" + field : field;
        }
        return result;
    }

    public Comparator getComparator() {
        return comparator;
    }

    public String getLabel() {
        return label;
    }
}
