package ninja.cyplay.com.apilibrary.domain.repositoryModel.deserializer;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.List;

import ninja.cyplay.com.apilibrary.domain.repositoryModel.factory.ModelFactory;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.responses.FilteredPaginatedResourcesResponse;

/**
 * Created by romainlebouc on 09/08/16.
 */
public class PR_FilteredPaginatedResourceDeserializer<Resource, Filter> extends PR_PaginatedResourceDeserializer<Resource>
{


    private final static String FILTERS_KEY = "filters";
    protected Class<Filter> filterClass;

    public PR_FilteredPaginatedResourceDeserializer(Class<Resource> entityClass, Class<Filter> filterClass){
        // Get Generic class type at runtime
        super(entityClass);
        this.filterClass = filterClass;
    }

    @Override
    public FilteredPaginatedResourcesResponse deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException
    {
        Gson gson = new Gson();
        FilteredPaginatedResourcesResponse response = new FilteredPaginatedResourcesResponse();
        GsonSerializerHelper.fillDefaultsFields(response, je);

        super.deserializePagination(je, gson, response);
        this.deserializeFilters(je, gson, response);
        return response;
    }

    private void deserializeFilters(JsonElement je, Gson gson, FilteredPaginatedResourcesResponse response){
        if (je.getAsJsonObject().has(FILTERS_KEY) && !je.getAsJsonObject().get(FILTERS_KEY).isJsonNull() ){
            final Class filterClass = ModelFactory.getInstance().get(this.filterClass.getName());
            Type t = new ListParameterizedType(filterClass);
            List<Filter> filterList = gson.fromJson(je.getAsJsonObject().get(FILTERS_KEY), t);
            response.setFilters(filterList);
        }
    }

}

