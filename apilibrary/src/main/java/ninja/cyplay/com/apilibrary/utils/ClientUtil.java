package ninja.cyplay.com.apilibrary.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

import ninja.cyplay.com.apilibrary.models.business.enums.SSLCertificateSignatureType;


public class ClientUtil {

    public static SSLCertificateSignatureType sslCertificateSignature;
    public static String clientUrl;
    public static String clientId;
    public static String clientSecret;
    public static Boolean verifyHostNameCertificate = true;
    public static String shopId;

    public static SSLCertificateSignatureType getSslCertificateSignatureType() {
        return sslCertificateSignature;
    }

    public static void setSslCertificateSignatureType(SSLCertificateSignatureType sslCertificateSignature) {
        ClientUtil.sslCertificateSignature = sslCertificateSignature;
    }

    public static String getClientUrl() {
        return clientUrl != null ? clientUrl : "";
    }

    public static void setClientUrl(String buildConfig, String clientUrlsMap) {
        Map<String, String> configMap = getConfigMap(clientUrlsMap);
        ClientUtil.clientUrl = configMap.get(buildConfig);
    }

    public static String getClientId() {
        return clientId;
    }

    public static void setClientId(String buildConfig, String clientIdsMap) {
        Map<String, String> configMap = getConfigMap(clientIdsMap);
        ClientUtil.clientId = configMap.get(buildConfig);
    }

    public static String getClientSecret() {
        return clientSecret;
    }

    public static void setClientSecret(String buildConfig, String clientSecretsMap) {
        Map<String, String> configMap = getConfigMap(clientSecretsMap);
        ClientUtil.clientSecret = configMap.get(buildConfig);
    }

    private static Map<String, String> getConfigMap(String confString) {
        Type mapType = new TypeToken<Map<String, String>>() {
        }.getType();
        return new Gson().fromJson(confString, mapType);
    }

    public static void setVerifyHostNameCertificate(Boolean verifyHostNameCertificate) {
        ClientUtil.verifyHostNameCertificate = verifyHostNameCertificate;
    }

    public static Boolean getVerifyHostNameCertificate() {
        return verifyHostNameCertificate;
    }

    public static String getShopId() {
        return shopId;
    }

    public static void setShopId(String shopId) {
        ClientUtil.shopId = shopId;
    }




    public static String logMessage = "";

    public static void updateMessage(String msg) {
        logMessage += msg + "\n\n";
        Log.d("RetrofitLogMessage", msg);
    }

    public static void clearLogMessage() {
        logMessage = "";
    }
}