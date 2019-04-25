package ninja.cyplay.com.apilibrary.utils.enumeration;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by romainlebouc on 12/05/16.
 */
public enum EBarCodeMapping {
    NO_CORRESPONDENCE("0"),
    CORRESPONDENCE_CARD_CODE("1"),
    CORRESPONDENCE_PRODUCT ("2"),
    CORRESPONDENCE_CUSTOMER_TO_ADD ("3"),
    CORRESPONDENCE_CUSTOMER_TO_ADD_OR_UPDATE ("4");

    private final String code;

    EBarCodeMapping(String code){
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public final static Map<String, EBarCodeMapping> CODE_2_BAR_CODE_TYPE = new HashMap<>();

    static{
        for (EBarCodeMapping barCodeMapping: EBarCodeMapping.values()){
            CODE_2_BAR_CODE_TYPE.put(barCodeMapping.getCode(), barCodeMapping);
        }
    }

    public static EBarCodeMapping valueofCode(String code){
        return CODE_2_BAR_CODE_TYPE.get(code);
    }
}
