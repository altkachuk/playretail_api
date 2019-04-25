package ninja.cyplay.com.apilibrary.utils;

/**
 * Created by damien on 27/04/15.
 */

import java.net.MalformedURLException;
import java.net.URL;

public class Constants {

    public final static String UTF_8_ENCODING = "UTF-8";
    // Server
    public final static String API_KEY = "58F36C4CA683F306216FBDA6A80B3FEEE8C2E556E6E754F39";

    // Payleven
    public final static String PAYLEVEN_API_KEY             = "ccd83cc89ce44d50b8bd6970064427da";
    public final static String PAYLEVEN_VENDOR_EMAIL        = "cyplay@payleven.de";
    public final static String PAYLEVEN_VENDOR_PWD          = "123456789";

    // tweet service
    public final static String CLIENT_ID = getCLIENT_ID(ClientUtil.getClientUrl());
    public final static String TWEET_PROVIDER_SOCKETIO = "socketio";
    public final static String TWEET_PROVIDER_FIREBASE = "firebase";
    public final static String TWEET_PROVIDER = TWEET_PROVIDER_SOCKETIO;

    // Firebase
    public final static String FIREBASE_URL = "https://shining-fire-8712.firebaseio.com/";

    //SocketIO Tweet server
    public final static String  TWEET_SERVER_URL = "https://socket-"+CLIENT_ID+".cy-play.com:8080";
//    public final static String TWEET_SERVER_URL = "http://192.168.1.17:3000";
    public final static String SOCKETIO_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1lIjoiYW5pcyJ9.sHITVuAMpiL1VxZ9kEIMUpYMsKCPp75Hd_lXq2dVJSg";

    // New Relic
    public final static String KEY_NEWRELIC = "AAddc2c4529839d489719fe0fe2ad405f5ef57d84d";

    // Default Pass length
    public final static int     DEFAULT_PASS_LENGTH = 4;

    // Reporting
    public final static int     SYNC_PAGEVIEW_DELAY = 60000;

    // FORM Masks
    public final static String  FORM_CREATE_KEY = "customerCreationForm";
    public final static String  FORM_UPDATE_KEY = "customerUpdateForm";

    public final static String FORM_SHIPPING_ADDRESS_KEY = "customerShippingAddressCreationForm";


    public final static int     FORM_MASK_REQUIRED = 0b00000001;
    public final static int     FORM_MASK_EDITONCE = 0b00000010;
    public final static int     FORM_MASK_DISABLED = 0b00000100;

    public final static int     MASK_REQUIRED = 0b00000001;
    public final static int     MASK_EDITABLE = 0b00000010;

    // EXTRA Constants
    public final static String EXTRA_SELLERS = "EXTRA_SELLERS";
    public final static String EXTRA_SELLER = "EXTRA_SELLER";
    public final static String EXTRA_SELLE_NAME = "EXTRA_SELLE_NAME";
    public final static String EXTRA_PRODUCT = "EXTRA_PRODUCT";
    public final static String EXTRA_SKU = "EXTRA_SKU";
    public final static String EXTRA_CUSTOMER = "EXTRA_CUSTOMER";
    public final static String EXTRA_CUSTOMER_PREVIEW = "EXTRA_CUSTOMER_PREVIEW";
    public final static String EXTRA_CUSTOMER_EDIT_FOCUS_ID = "EXTRA_FOCUS_ID";
    public final static String EXTRA_PHOTO_PATH = "EXTRA_PHOTO_PATH";
    public final static String EXTRA_CROPPED_PHOTO_PATH = "EXTRA_CROPPED_PHOTO_PATH";

    public final static String EXTRA_SHOP_OFFERS_ONLY = "EXTRA_SHOP_OFFERS_ONLY";
    public final static String EXTRA_SCAN_FILTER = "EXTRA_SCAN_FILTER";
    public final static String EXTRA_SEARCH_CUSTOMER_CONTEXT = "EXTRA_SEARCH_CUSTOMER_CONTEXT";
    public final static String EXTRA_SEARCH_FILTER = "EXTRA_SEARCH_FILTER";
    public final static String EXTRA_SEARCH_FILTER_CHECK = "EXTRA_SEARCH_FILTER_CHECK";
    public final static String EXTRA_CUSTOMER_FORM_MODE_EDIT = "EXTRA_CUSTOMER_FORM_MODE_EDIT";

    // EXTRA Values
    public final static int EXTRA_FORM_EDIT = 0;
    public final static int EXTRA_FORM_EDIT_ADDRESS = 1;
    public final static int EXTRA_FORM_EDIT_MAIL = 2;
    public final static int EXTRA_FORM_EDIT_PHONE = 3;


    // Results
    public final static Integer RESULT_SCAN_ACTIVITY = 1;
    public final static Integer RESULT_CUSTOMER_SEARCH_ACTIVITY = 21;
    public final static Integer RESULT_SELECT_FILTERS_ACTIVITY = 34;
    public final static Integer RESULT_PAIEMENT_COMPLETE = 68;
    public final static Integer RESULT_SIGNATURE_ACTIVITY = 24;

    // Color
    public final static String CYPLAY_ORANGE = "#FA6900";

    public static String getCLIENT_ID(String WEBSERVICE_URL) {
        try {
            URL url = new URL(WEBSERVICE_URL);
            String host = url.getHost();
            return (host.substring("playretail-".length(), host.length() - ".cy-play.com".length()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "democyplay";
        }
    }

}
