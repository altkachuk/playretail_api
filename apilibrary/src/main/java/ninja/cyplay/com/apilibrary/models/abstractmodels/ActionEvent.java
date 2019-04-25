package ninja.cyplay.com.apilibrary.models.abstractmodels;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ninja.cyplay.com.apilibrary.models.APIResource;
import ninja.cyplay.com.apilibrary.models.business.reporting.ActionEventData;


/**
 * Created by romainlebouc on 22/09/16.
 */
@APIResource(name = ActionEvent.API_RESOURCE_NAME)
public class ActionEvent {

    public final static transient String API_RESOURCE_NAME = "actionevents";

    protected String resource;
    protected String attribute;
    protected String sub_attribute;
    protected String action;
    protected String value;
    protected Integer status;
    protected String seller_id;
    protected String customer_id;
    protected String shop_id;
    protected Date date;
    protected List<ActionEventContent> contents = new ArrayList<>();

    public ActionEvent(ActionEventData actionEventData
    ) {
        this.resource = actionEventData.getResource();
        this.attribute = actionEventData.getAttribute();
        this.sub_attribute = actionEventData.getSub_attribute();
        this.action = actionEventData.getAction();
        this.status = actionEventData.getStatus();
        this.seller_id = actionEventData.getSeller_id();
        this.customer_id = actionEventData.getCustomer_id();
        this.shop_id = actionEventData.getShop_id();
        this.date = actionEventData.getDate();
        this.contents = ActionEventContent.getListFromActionEventContentDatas(actionEventData.getContents());
        this.value = actionEventData.getValue();
    }

    public static List<ActionEvent> getListFromActionEventDatas(List<ActionEventData> actionEventDatas) {
        List<ActionEvent> result = new ArrayList<>();
        if (actionEventDatas != null) {
            for (ActionEventData actionEventData : actionEventDatas) {
                ActionEvent actionEvent = new ActionEvent(actionEventData);
                result.add(actionEvent);
            }
        }
        return result;
    }

}
