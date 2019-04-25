package ninja.cyplay.com.apilibrary.models.filters;

import android.content.Context;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;

import java.util.ArrayList;
import java.util.List;

import ninja.cyplay.com.apilibrary.utils.ReflectionUtils;

/**
 * Created by romainlebouc on 05/09/16.
 */
public abstract class ResourceFilter<TResourceFilter extends ResourceFilter, TResourceFilterValue extends ResourceFilterValue> {

    public static int TYPE_LEVEL_ATTRIBUTE = 1;
    public static int TYPE_LEVEL_DICT_VALUE = 2;
    public final static String TYPE_LEVEL_DICT_SPEC_SUFFIX = "__spec__text";
    public final static String TYPE_LEVEL_DICT_VALUE_SUFFIX = "__value__text";

    public String label;
    public String key;
    public Integer level;

    public boolean expanded = false;

    public abstract ResourceFilter copyWithValue(ResourceFilterValue filterValue) ;

    public abstract List<TResourceFilterValue> getValues() ;

    public abstract void setValues(List<TResourceFilterValue> values) ;

    public String getLabel(Context context) {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }


    @Override
    public int hashCode() {
        int result = label != null ? label.hashCode() : 0;
        result = 31 * result + (key != null ? key.hashCode() : 0);
        result = 31 * result + (level != null ? level.hashCode() : 0);
        return result;
    }

    public static <Resource> List<Resource> filterResources(List<Resource> offers, List<ResourceFilter<ResourceFilter, ResourceFilterValue>> offerFilters) {
        List<Resource> result = new ArrayList<>(offers);
        if (offerFilters != null && !offerFilters.isEmpty()) {
            for (final ResourceFilter<ResourceFilter, ResourceFilterValue> filter : offerFilters) {
                if (filter.getValues() != null) {
                    for (final ResourceFilterValue filterValue : filter.getValues()) {
                        // do filtering over the same list
                        result = FluentIterable.from(result).filter(new Predicate<Resource>() {
                            public boolean apply(Resource input) {
                                return filterValue.getKey().equals(ReflectionUtils.get(input, filter.getKey()));
                            }
                        }).toList();
                    }
                }

            }
        }
        // Because FluentIterable is sending back a immutable List
        return new ArrayList(result);
    }

}
