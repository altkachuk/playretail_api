package ninja.cyplay.com.playretail_api.utils;

import android.content.Context;

/**
 * Created by damien on 30/03/16.
 */
public class StringUtils {

    private static Context context;

    private static String packageName;

    public static void setContext(Context ctx) {
        context = ctx;
        packageName = ctx.getPackageName();
    }

    public static String getStringResourceByName(String aString) {
        try {
            int resId = context.getResources().getIdentifier(aString, "string", packageName);
            return context.getString(resId);
        } catch (Exception e) {}
        return "";
    }

}
