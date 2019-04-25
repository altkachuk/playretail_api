package ninja.cyplay.com.apilibrary.models.business.reporting;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.RealmClass;
import ninja.cyplay.com.apilibrary.models.ModelField;
import ninja.cyplay.com.apilibrary.models.business.enums.EActionStatus;
import ninja.cyplay.com.apilibrary.models.business.enums.EResource;
import ninja.cyplay.com.apilibrary.models.filters.ResourceFilter;
import ninja.cyplay.com.apilibrary.models.filters.ResourceFilterValue;
import ninja.cyplay.com.apilibrary.models.meta.Pagination;
import ninja.cyplay.com.apilibrary.models.meta.ResourceFieldSorting;
import ninja.cyplay.com.apilibrary.utils.StringUtils;

import static org.reflections.ReflectionUtils.getAllFields;
import static org.reflections.ReflectionUtils.withAnnotation;


/**
 * Created by romainlebouc on 22/09/16.
 */
@RealmClass
public class ActionEventData<T> extends RealmObject implements IActionEvent, ReportingResource {
    protected final static transient String PARAMS_ENTITY_FIELD_SEPARATOR = "__";
    protected final static transient String FROM_CONTENT_KEY = "#from";

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

    protected RealmList<ActionEventContentData> contents = new RealmList<>();
    int syncRetryCount;

    public ActionEventData() {
    }

    public ActionEventData(String resource,
                           String attribute,
                           String sub_attribute,
                           String action,
                           Integer status,
                           String seller_id,
                           String customer_id,
                           String shop_id,
                           Date date,
                           String value,
                           RealmList<ActionEventContentData> contents) {
        this.resource = resource;
        this.attribute = attribute;
        this.sub_attribute = sub_attribute;
        this.action = action;
        this.status = status;
        this.seller_id = seller_id;
        this.customer_id = customer_id;
        this.shop_id = shop_id;
        this.date = date;
        this.contents = contents;
        this.value = value;
    }

    public String getResource() {
        return resource;
    }

    public String getAttribute() {
        return attribute;
    }

    public String getSub_attribute() {
        return sub_attribute;
    }

    public String getAction() {
        return action;
    }

    public Integer getStatus() {
        return status;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public String getValue() {
        return value;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public RealmList<ActionEventContentData> getContents() {
        return contents;
    }

    public ActionEventData build() {
        return new ActionEventData(this.getResource(),
                this.getAttribute(),
                this.getSub_attribute(),
                this.getAction(),
                this.getStatus(),
                this.getSeller_id(),
                this.getCustomer_id(),
                this.getShop_id(),
                this.getDate(),
                this.getValue(),
                this.getContents());
    }

    public T setStatus(EActionStatus status) {
        this.status = status.getCode();
        return (T) this;
    }

    public T setResource(EResource eResource) {
        this.resource = eResource.getCode();
        return (T) this;
    }

    public T setAttribute(String attribute) {
        this.attribute = attribute;
        return (T) this;
    }

    public T setSubAttribute(String subAttribute) {
        this.sub_attribute = subAttribute;
        return (T) this;
    }

    public T setValue(String value) {
        this.value = value;
        return (T) this;
    }

    public T addFilterParams(List<?> resourceFilters) {
        // log filters
        if (resourceFilters != null) {
            for (int i = 0; i < resourceFilters.size(); i++) {
                ResourceFilter resourceFilter = (ResourceFilter) resourceFilters.get(i);
                if (resourceFilter != null && resourceFilter.getValues() != null) {
                    for (int j = 0; j < resourceFilter.getValues().size(); j++) {
                        ResourceFilterValue filterValue = (ResourceFilterValue) resourceFilter.getValues().get(j);
                        if (filterValue != null && resourceFilter.getKey() != null && filterValue.getKey() != null) {
                            this.getContents().add(new ActionEventContentData("filter." + resourceFilter.getKey(), filterValue.getKey()));
                        }
                    }
                }
            }
        }
        return (T) this;
    }

    public T addSortParams(ResourceFieldSorting resourceFieldSorting) {
        // log sort
        if (resourceFieldSorting != null
                && resourceFieldSorting.getField() != null
                && resourceFieldSorting.getSortingWay() != null) {
            this.getContents().add(new ActionEventContentData("sorting.field", resourceFieldSorting.getField()));
            this.getContents().add(new ActionEventContentData("sorting.way", resourceFieldSorting.getSortingWay().name()));
        }
        return (T) this;
    }

    public T addPaginationParams(Pagination pagination) {
        this.getContents().add(new ActionEventContentData("pagination.offset", String.valueOf(pagination.getOffset())));
        this.getContents().add(new ActionEventContentData("pagination.limit", String.valueOf(pagination.getLimit())));
        return (T) this;
    }

    public T addObjectParams(Object object) {
        return addObjectParams(StringUtils.EMPTY_STRING, object);
    }

    private T addObjectParams(String prefix, Object object) {
        try {
            if (object != null) {
                Set<Field> fields = getAllFields(object.getClass(), withAnnotation(ModelField.class));
                for (Field field : fields) {
                    ModelField modelField = field.getAnnotation(ModelField.class);
                    //TODO : manage embedded + many
                    if (!modelField.embedded()) {
                        this.getContents().add(new ActionEventContentData(prefix + field.getName(), field.get(object) != null ? field.get(object).toString() : null));
                    } else {
                        this.addObjectParams(prefix + field.getName() + PARAMS_ENTITY_FIELD_SEPARATOR, field.get(object));
                    }
                }
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return (T) this;
    }

    public ActionEventData<T> addFromContent(String value) {
        this.getContents().add(new ActionEventContentData(FROM_CONTENT_KEY, value));
        return this;
    }

    public int getSyncRetryCount() {
        return syncRetryCount;
    }

    public int incSyncRetryCount() {
        syncRetryCount = syncRetryCount + 1;
        return syncRetryCount;
    }


}
