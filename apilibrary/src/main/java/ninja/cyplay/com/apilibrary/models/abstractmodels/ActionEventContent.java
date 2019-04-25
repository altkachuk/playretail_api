package ninja.cyplay.com.apilibrary.models.abstractmodels;

import java.util.ArrayList;
import java.util.List;

import ninja.cyplay.com.apilibrary.models.business.reporting.ActionEventContentData;

/**
 * Created by romainlebouc on 23/09/16.
 */

public class ActionEventContent {

    protected  String k;
    protected String v;


    public ActionEventContent(ActionEventContentData actionEventContentData
    ) {
        this.k = actionEventContentData.getK();
        this.v = actionEventContentData.getV();

    }

    public static List<ActionEventContent> getListFromActionEventContentDatas(List<ActionEventContentData> actionEventContentDatas) {
        List<ActionEventContent> result = new ArrayList<>();
        if (actionEventContentDatas != null) {
            for (ActionEventContentData actionEventContentData : actionEventContentDatas) {
                ActionEventContent actionEvent = new ActionEventContent(actionEventContentData);
                result.add(actionEvent);
            }
        }
        return result;
    }
}
