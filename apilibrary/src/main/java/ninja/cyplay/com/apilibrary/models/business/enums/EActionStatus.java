package ninja.cyplay.com.apilibrary.models.business.enums;

/**
 * Created by romainlebouc on 22/09/16.
 */
public enum  EActionStatus {

    SUCCESS(0),
    FAILURE(-1);

    private final int code;

    EActionStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static EActionStatus getActionStatus(boolean success){
        return  success ? SUCCESS : FAILURE;
    }

}
