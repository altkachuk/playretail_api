package ninja.cyplay.com.apilibrary.utils;


import java.util.Locale;

import javax.annotation.Nonnull;


/**
 * Created by romainlebouc on 03/02/2017.
 */

public class CompatUtils {


    @Nonnull
    public static String toLanguageTag(@Nonnull final Locale locale) {
        // just perform simple generation
        final StringBuilder sb = new StringBuilder(5);

        // append the language
        sb.append(locale.getLanguage().toLowerCase(Locale.US));

        // append the region
        final String region = locale.getCountry();
        if (region != null && region.length() > 0) {
            sb.append('-').append(region.toUpperCase(Locale.US));
        }

        // output the language tag
        return sb.toString();
    }
}
