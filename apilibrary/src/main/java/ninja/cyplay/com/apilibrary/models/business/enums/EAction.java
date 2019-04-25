package ninja.cyplay.com.apilibrary.models.business.enums;

/**
 * Created by romainlebouc on 23/09/16.
 */
public enum EAction {

    SEARCH("search"),
    SUGGESTION("suggestion"),
    LOGIN("login"),
    SHARE("share"),
    UPDATE("update"),
    CREATE("create"),
    PUT("put"),
    SCAN("scan"),
    DELETE("delete"),
    GET("get"),
    DISPLAY("display");

    private final String code;

    EAction(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}