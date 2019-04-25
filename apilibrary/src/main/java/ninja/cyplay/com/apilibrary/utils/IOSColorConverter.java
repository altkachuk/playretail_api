package ninja.cyplay.com.apilibrary.utils;

/**
 * Created by damien on 02/06/16.
 */
public class IOSColorConverter {

    public static String fromRGBAtoARGB(String color) {
        if (color != null && color.length() == 9) {
            return "#"
                    + color.substring(color.length() - 2, color.length())
                    + color.substring(1, color.length() - 2);
        }
        return color;
    }
}
