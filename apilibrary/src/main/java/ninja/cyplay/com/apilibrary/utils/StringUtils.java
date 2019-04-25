package ninja.cyplay.com.apilibrary.utils;

/**
 * Created by damien on 30/03/16.
 */
public class StringUtils {

    public final static String EMPTY_STRING = "";

    public static String join(Object[] array, String separator) {
        if (array == null) {
            return null;
        }
        if (separator == null) {
            separator = "";
        }

        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                buf.append(separator);
            }
            if (array[i] != null) {
                buf.append(array[i]);
            }
        }
        return buf.toString();
    }
}
