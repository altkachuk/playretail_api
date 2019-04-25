package ninja.cyplay.com.apilibrary.domain.repositoryModel.deserializer;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import ninja.cyplay.com.apilibrary.domain.repositoryModel.factory.ModelFactory;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.responses.ResourceResponse;

/**
 * Created by damien on 10/08/16.
 */
public class PR_ResourceDeserializer<Resource, T> implements JsonDeserializer<ResourceResponse<Resource>>
{
    private Class<T> persistentClass;

    public PR_ResourceDeserializer(Class<T> entityClass){
        // Get Generic class type at runtime
        this.persistentClass = entityClass;
    }

    @Override
    public ResourceResponse<Resource> deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException
    {
        Gson gson = new Gson();
        ResourceResponse<Resource> response = new ResourceResponse<>();
        GsonSerializerHelper.fillDefaultsFields(response, je);

        // Get Resource
        final Class genericClass = ModelFactory.getInstance().get(persistentClass.getName());
        response.setResource((Resource) gson.fromJson(je.getAsJsonObject(), genericClass !=null ? genericClass:persistentClass ));

        return response;
    }

}

