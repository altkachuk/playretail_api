package ninja.cyplay.com.playretail_api.utils;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.Random;

/**
 * Created by damien on 26/02/15.
 */
public class ColorUtils {

    private final static Random random = new Random();

    public static void setProgressBarColor(ProgressBar progressBar, FeatureColor color) {
        if (progressBar != null) {
            if (progressBar.getProgressDrawable() != null)
                progressBar.getProgressDrawable().setColorFilter(
                        Color.parseColor(FeatureColor.getColorHex(color)), PorterDuff.Mode.SRC_IN);
            if (progressBar.getIndeterminateDrawable() != null)
                progressBar.getIndeterminateDrawable().setColorFilter(
                        Color.parseColor(FeatureColor.getColorHex(color)), PorterDuff.Mode.SRC_IN);
        }
    }

    public static void setActionBarBackgroundColor(android.app.ActionBar bar, FeatureColor color) {
        if (bar != null) {
            bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(FeatureColor.getColorHex(color))));
            bar.setDisplayShowTitleEnabled(false);  // required to force redraw, without, gray color
            bar.setDisplayShowTitleEnabled(true);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void settatusBarColor(Window window, FeatureColor color) {
        if (window != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.parseColor(FeatureColor.getColorHex(color)));
        }
    }

    public static void setToolBarBackgroundColor(Toolbar bar, FeatureColor color) {
        if (bar != null) {
            if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN)
                bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(FeatureColor.getColorHex(color))));
            else
                bar.setBackground(new ColorDrawable(Color.parseColor(FeatureColor.getColorHex(color))));
        }
    }

    public static void setActionBarBackgroundColor(ActionBar bar, FeatureColor color) {

        if (bar != null) {
            bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(FeatureColor.getColorHex(color))));
            bar.setDisplayShowTitleEnabled(false);  // required to force redraw, without, gray color
            bar.setDisplayShowTitleEnabled(true);
        }
    }

    public static void setTabActionBarBackgroundColor(android.app.ActionBar bar, FeatureColor color) {
        if (bar != null) {
            if (android.os.Build.VERSION.SDK_INT > 13)
                setBackgroundColorV14(bar, color);
        }
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private static void setBackgroundColorV14(android.app.ActionBar bar, FeatureColor color) {
        bar.setStackedBackgroundDrawable(new ColorDrawable(
                Color.parseColor(ColorUtils.FeatureColor.getColorHex(color))));
    }

    public static void setBackgroundColor(View view, FeatureColor color) {
        try {
            if (view != null) {
                GradientDrawable drawable = (GradientDrawable) view.getBackground();
                if (drawable != null)
                    drawable.setColor(Color.parseColor(FeatureColor.getColorHex(color)));
                else
                    view.setBackgroundColor(Color.parseColor(FeatureColor.getColorHex(color)));
            }
        }
        catch (Exception e) {
            view.setBackgroundColor(Color.parseColor(FeatureColor.getColorHex(color)));
        }
    }

    public static void setBackgroundColor(View view, int color) {
        try {
            if (view != null) {
                GradientDrawable drawable = (GradientDrawable) view.getBackground();
                if (drawable != null)
                    drawable.setColor(color);
                else
                    view.setBackgroundColor(color);
            }
        }
        catch (Exception e) {
            view.setBackgroundColor(color);
        }
    }

    public static void setCardViewBackgroundColor(CardView view ,FeatureColor color) {
        if (view != null)
            view.setCardBackgroundColor(Color.parseColor(FeatureColor.getColorHex(color)));
    }

    public static void applyColorFilter(ImageView view, FeatureColor color) {
        try {
            if (view != null) {
                view.getDrawable().setColorFilter(Color.parseColor(FeatureColor.getColorHex(color)), PorterDuff.Mode.MULTIPLY);
                view.invalidate();
            }
        }
        catch(Exception e) { }
    }

    public static void applyLightningColorFilter(ImageView view, String colorhex) {
        try {
            if (view != null) {
                ColorFilter filter = new LightingColorFilter(Color.BLACK, Color.parseColor(colorhex));
                view.setColorFilter(filter);
                view.invalidate();
            }
        }
        catch(Exception e) { }
    }

    public static void applyLightningColorFilter(ImageView view, FeatureColor color) {
        try {
            if (view != null) {
                ColorFilter filter = new LightingColorFilter(Color.BLACK, Color.parseColor(FeatureColor.getColorHex(color)));
                view.setColorFilter(filter);
                view.invalidate();
            }
        }
        catch(Exception e) { }
    }

    public static void applyColorFilterFromWhite(ImageView view, FeatureColor color) {
        try {
            if (view != null) {
                ColorFilter filter = new LightingColorFilter(Color.WHITE, Color.parseColor(FeatureColor.getColorHex(color)));
                view.setColorFilter(filter);
            }
        }
        catch(Exception e) { }
    }

    public static void applyLightningColorFilter(ImageView view, int color) {
        try {
            if (view != null) {
                ColorFilter filter = new LightingColorFilter(Color.BLACK, color);
                view.setColorFilter(filter);
            }
        }
        catch(Exception e) { }
    }

    public static void applyLightningColorFilter(ToggleButton view, FeatureColor color) {
        try {
            if (view != null) {
                ColorFilter filter = new LightingColorFilter(Color.BLACK, Color.parseColor(FeatureColor.getColorHex(color)));
                view.getBackground().setColorFilter(filter);
            }
        } catch (Exception e) { }
    }

    public static void removeColorFilter(ImageView view) {
        try {
            if (view != null) {
                view.setColorFilter(null);
            }
        } catch (Exception e) { }
    }

    public static void removeColorFilter(ToggleButton view) {
        try {
            if (view != null) {
                view.getBackground().setColorFilter(null);
            }
        } catch (Exception e) { }
    }

    public static void setTextColor(TextView textview, FeatureColor color) {
        if (textview != null)
            textview.setTextColor(Color.parseColor(FeatureColor.getColorHex(color)));
    }

    public static void setTextColor(TextView textview, int color) {
        if (textview != null)
            textview.setTextColor(color);
    }

    public static void setButtonColor(Button button, FeatureColor color) {
        if (button != null)
            button.setTextColor(Color.parseColor(FeatureColor.getColorHex(color)));
    }

    public static enum FeatureColor {
        NEUTRAL_DARK,
        NEUTRAL_LIGHT,
        PRIMARY_LIGHT,
        NEUTRAL_MEDIUM,
        PRIMARY_DARK,
        PRIMARY_DARK_BIS,
        PRIMARY_LIKE;

        public static String getColorHex(FeatureColor color) {
            if (ConfigHelper.getInstance().getFeature() != null
                    && ConfigHelper.getInstance().getFeature().getAppearanceConfig() != null
                    && ConfigHelper.getInstance().getFeature().getAppearanceConfig().getColor() != null) {
                switch (color) {
                    case NEUTRAL_DARK:
                        return ConfigHelper.getInstance().getFeature().getAppearanceConfig().getColor().getNeutral_dark();
                    case NEUTRAL_LIGHT:
                        return ConfigHelper.getInstance().getFeature().getAppearanceConfig().getColor().getNeutral_light();
                    case PRIMARY_LIGHT:
                        return ConfigHelper.getInstance().getFeature().getAppearanceConfig().getColor().getPrimary_light();
                    case NEUTRAL_MEDIUM:
                        return ConfigHelper.getInstance().getFeature().getAppearanceConfig().getColor().getNeutral_medium();
                    case PRIMARY_DARK:
                        return ConfigHelper.getInstance().getFeature().getAppearanceConfig().getColor().getPrimary_dark();
                    case PRIMARY_DARK_BIS:
                        return "#A16C41";
                    case PRIMARY_LIKE:
                        return "#3CB371"; // MediumSeaGreen
                }
            }
            return "#333333";
        }
    }

    public static int generateRandomColor() {
        // This is the base color which will be mixed with the generated one
        final int baseColor = Color.WHITE;

        final int baseRed = Color.red(baseColor);
        final int baseGreen = Color.green(baseColor);
        final int baseBlue = Color.blue(baseColor);

        final int red = (baseRed + random.nextInt(256)) / 2;
        final int green = (baseGreen + random.nextInt(256)) / 2;
        final int blue = (baseBlue + random.nextInt(256)) / 2;
        return Color.rgb(red, green, blue);
    }


    public static int generateRandomColorDark() {
        // This is the base color which will be mixed with the generated one
        final int baseColor = Color.WHITE;

        final int red = (random.nextInt(128));
        final int green = (random.nextInt(128));
        final int blue = (random.nextInt(128));
        return Color.rgb(red, green, blue);
    }

    /**
     * Lightens a color by a given factor.
     *
     * @param color
     *            The color to lighten
     * @param factor
     *            The factor to lighten the color. 0 will make the color unchanged. 1 will make the
     *            color white.
     * @return lighter version of the specified color.
     */
    public static int lighter(int color, float factor) {
        int red = (int) ((Color.red(color) * (1 - factor) / 255 + factor) * 255);
        int green = (int) ((Color.green(color) * (1 - factor) / 255 + factor) * 255);
        int blue = (int) ((Color.blue(color) * (1 - factor) / 255 + factor) * 255);
        return Color.argb(Color.alpha(color), red, green, blue);
    }

}