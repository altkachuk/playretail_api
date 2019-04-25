package ninja.cyplay.com.playretail_api.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by damien on 11/03/16.
 */
public class DisplayUtils {

    private static Context context;

    public static void setContext(Context ctx) {
        context = ctx;
    }

    public static int getDpiDensity() {
        if (context != null)
            return context.getResources().getDisplayMetrics().densityDpi;
       return DisplayMetrics.DENSITY_LOW;
    }
}
