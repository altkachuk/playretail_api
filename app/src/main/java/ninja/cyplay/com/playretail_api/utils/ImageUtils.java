package ninja.cyplay.com.playretail_api.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

import ninja.cyplay.com.apilibrary.utils.Constants;

public class ImageUtils {

    public static String getIconUrl(Activity activity, String imageName) {
        String imageSize;
        switch (activity.getResources().getDisplayMetrics().densityDpi) {
            case DisplayMetrics.DENSITY_LOW:
                imageSize = "1x";
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                imageSize = "2x";
                break;
            case DisplayMetrics.DENSITY_HIGH:
                imageSize = "3x";
                break;
            case DisplayMetrics.DENSITY_XHIGH:
                imageSize = "3x";
                break;
            default:
                imageSize = "3x";
                break;
        }

        StringBuilder builder = new StringBuilder();
        builder.append(Constants.WEBSERVICE_URL);
        builder.append(Constants.IMAGE_ROUTE);
        //builder.append(Constants.CLIENT_DATABASE_NAME);
        builder.append("RomainAskedToWriteSthHere");
        builder.append("/");
        builder.append(imageName);
        builder.append("/");
        builder.append(imageSize);
        builder.append("/png/");
        return builder.toString();
    }

    /**
     * Return return the complete path of an Image
     *
     * @param imageHash hash of the image
     * @param imageSize assignedValue must be "sd" or "hd"
     * @return
     */
    public static String getImageUrl(String imageHash, String imageSize) {
        StringBuilder builder = new StringBuilder();
        if (ConfigHelper.getInstance().getFeature() != null
                && ConfigHelper.getInstance().getFeature().getAdditional_parameters() != null
                && ConfigHelper.getInstance().getFeature().getAdditional_parameters().getImage_service_prefix() != null) {
            String prefix = ConfigHelper.getInstance().getFeature().getAdditional_parameters().getImage_service_prefix();
            builder.append(prefix);
            builder.append(imageSize);
            builder.append("/");
            switch (DisplayUtils.getDpiDensity()) {
                case DisplayMetrics.DENSITY_LOW:
                    builder.append("1x");
                    break;
                case DisplayMetrics.DENSITY_MEDIUM:
                    builder.append("2x");
                    break;
                case DisplayMetrics.DENSITY_HIGH:
                    builder.append("3x");
                    break;
                case DisplayMetrics.DENSITY_XHIGH:
                    builder.append("3x");
                    break;
                default:
                    builder.append("3x");
                    break;
            }
            builder.append("/");
            builder.append(imageHash);
        }
        return builder.toString();
    }

}
