package ninja.cyplay.com.apilibrary.domain.repositoryModel.deserializer;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.List;

import ninja.cyplay.com.apilibrary.domain.repositoryModel.factory.ModelFactory;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.responses.PaginatedResourcesResponse;

/**
 * Created by romainlebouc on 09/08/16.
 */
public class PR_PaginatedResourceDeserializer<Resource> implements JsonDeserializer<PaginatedResourcesResponse<Resource>> {

    private final static String RESULTS_KEY = "results";
    private final static String PREVIOUS_KEY = "previous";
    private final static String NEXT_KEY = "next";
    private final static String COUNT_KEY = "count";

    protected Class<Resource> persistentClass;

    public PR_PaginatedResourceDeserializer(Class<Resource> entityClass) {
        // Get Generic class type at runtime
        this.persistentClass = entityClass;
    }

    @Override
    public PaginatedResourcesResponse<Resource> deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
        Gson gson = new Gson();
        PaginatedResourcesResponse<Resource> response = new PaginatedResourcesResponse<>();
        GsonSerializerHelper.fillDefaultsFields(response, je);

        deserializePagination(je, gson, response);

        return response;
    }

    protected void deserializePagination(JsonElement je, Gson gson, PaginatedResourcesResponse<Resource> response) {
        // Get
        if (je.getAsJsonObject().has(RESULTS_KEY)) {
            final Class genericClass = ModelFactory.getInstance().get(persistentClass.getName());
            Type t = new ListParameterizedType(genericClass != null ? genericClass : persistentClass);
            List<Resource> customerPreviewList = gson.fromJson(je.getAsJsonObject().get(RESULTS_KEY), t);
            response.setResults(customerPreviewList);
        }
        if (je.getAsJsonObject().has(PREVIOUS_KEY) && !je.getAsJsonObject().get(PREVIOUS_KEY).isJsonNull()) {
            response.setPrevious(je.getAsJsonObject().get(PREVIOUS_KEY).getAsString());
        }
        if (je.getAsJsonObject().has(NEXT_KEY) && !je.getAsJsonObject().get(NEXT_KEY).isJsonNull()) {
            response.setNext(je.getAsJsonObject().get(NEXT_KEY).getAsString());
        }
        if (je.getAsJsonObject().has(COUNT_KEY) && !je.getAsJsonObject().get(COUNT_KEY).isJsonNull()) {
            response.setCount(je.getAsJsonObject().get(COUNT_KEY).getAsInt());
        }

    }


}

