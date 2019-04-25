package ninja.cyplay.com.apilibrary.models.business;

import ninja.cyplay.com.apilibrary.R;

/**
 * Created by anishosni on 12/05/15.
 */
public enum EScanFilters {
    SCAN_ALL(R.string.scan_all),
    SCAN_PROD(R.string.scan_prod),
    SCAN_CUSTOMER(R.string.scan_customer),
    SCAN_NEW_CUSTOMER(R.string.scan_customer),
    SCAN_PROD_OR_EMPTY_CARD(R.string.scan_all);

    private int titleResourceId;

    EScanFilters(int titleResourceId){
        this.titleResourceId = titleResourceId;
    }

    public int getTitleResourceId() {
        return titleResourceId;
    }
}
