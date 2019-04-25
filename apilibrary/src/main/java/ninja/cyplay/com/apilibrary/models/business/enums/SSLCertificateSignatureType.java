package ninja.cyplay.com.apilibrary.models.business.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by romainlebouc on 08/06/2017.
 */

public enum SSLCertificateSignatureType {
    SELF(0), CA(1);

    private final int code;

    private final static Map<Integer, SSLCertificateSignatureType> CODE_2_SIGNATURE = new HashMap<>();

    static {
        for (SSLCertificateSignatureType sslCertificateSignatureType : SSLCertificateSignatureType.values()) {
            CODE_2_SIGNATURE.put(sslCertificateSignatureType.getCode(), sslCertificateSignatureType);
        }
    }

    SSLCertificateSignatureType(int code) {
        this.code = code;
    }

    public static SSLCertificateSignatureType valueOfCode(int code) {
        SSLCertificateSignatureType result = CODE_2_SIGNATURE.get(code);
        return result != null ? result : SELF;

    }

    public int getCode() {
        return code;
    }
}
