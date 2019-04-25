package ninja.cyplay.com.apilibrary.models.meta;

import java.util.HashMap;

/**
 * Created by romainlebouc on 18/08/16.
 */
public enum ESortingWay {
    ASC(1),DESC(-1);

    private final int code;

    private final static HashMap<Integer, ESortingWay> CODE_TO_ESORTINGWAY = new HashMap<>();

    static {
        for (ESortingWay eChannel : ESortingWay.values()) {
            CODE_TO_ESORTINGWAY.put(eChannel.code, eChannel);
        }
    }

    ESortingWay(int code) {
        this.code = code;
    }

    public static ESortingWay getESortingWayFromCode(Integer code) {
        ESortingWay eSortingWay = CODE_TO_ESORTINGWAY.get(code);
        return eSortingWay!=null ? eSortingWay : ASC;
    }

    public int getCode() {
        return code;
    }
}

