package ninja.cyplay.com.apilibrary.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import ninja.cyplay.com.apilibrary.R;

public class DateUtils {

    public static int getSecondsUntilTomorrow6AM() {
        Calendar c =    Calendar.getInstance();
        int seconds =   c.get(Calendar.SECOND);
        int minutes =   c.get(Calendar.MINUTE)  * 60;
        int hours =     c.get(Calendar.HOUR)    * 3600;

        // 24 hours - today's time + tomorrow till 6am
        int oneDay = 24 * 60 * 60;
        int sixAM = 6 * 60 * 60;

        return (oneDay - (hours + minutes + seconds) + sixAM);
    }

    //TODO bring all used date code here ...

    private final static SimpleDateFormat OUT_FORMAT = new SimpleDateFormat("yyyyMMdd");
    private final static int[] months = {
            R.string.date_jan,
            R.string.date_feb,
            R.string.date_mar,
            R.string.date_apr,
            R.string.date_may,
            R.string.date_jun,
            R.string.date_jul,
            R.string.date_aug,
            R.string.date_sep,
            R.string.date_oct,
            R.string.date_nov,
            R.string.date_dec
    };

    public static SimpleDateFormat IN_FORMAT = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());

    public static int getDateYear(Date date) {
        int result = 0;
        if (date != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            result = c.get(Calendar.YEAR);
        }
        return result;
    }

    public static int getMonthStringId(int month) {
        if (month < months.length)
            return months[month];
        return months[0];
    }

    public static String convertDateStringFormat(String d) {
        Date date = null;
        try {
            date = IN_FORMAT.parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date != null)
            return OUT_FORMAT.format(date);
        return null;
    }

    /** Transform Calendar to ISO 8601 string. */
    public static String fromCalendar(final Calendar calendar) {
        Date date = calendar.getTime();
        String formatted = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .format(date);
        return formatted.substring(0, 22) + ":" + formatted.substring(22);
    }

    /** Get current date and time formatted as ISO 8601 string. */
    public static String now() {
        return fromCalendar(GregorianCalendar.getInstance());
    }

    /** Transform ISO 8601 string to Calendar. */
    public static Calendar toCalendar(final String iso8601string)
            throws ParseException {
        Calendar calendar = GregorianCalendar.getInstance();
        String s = iso8601string.replace("Z", "+00:00");
        try {
            s = s.substring(0, 22) + s.substring(23);  // to id rid of the ":"
        } catch (IndexOutOfBoundsException e) {
            throw new ParseException("Invalid length", 0);
        }
        Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.ssZ").parse(s);
        calendar.setTime(date);
        return calendar;
    }

}