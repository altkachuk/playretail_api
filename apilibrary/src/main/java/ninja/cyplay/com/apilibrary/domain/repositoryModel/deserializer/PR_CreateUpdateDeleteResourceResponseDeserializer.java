package ninja.cyplay.com.apilibrary.domain.repositoryModel.deserializer;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import ninja.cyplay.com.apilibrary.domain.repositoryModel.responses.CreateUpdateDeleteResourceResponse;

/**
 * Created by damien on 10/08/16.
 */
public class PR_CreateUpdateDeleteResourceResponseDeserializer implements JsonDeserializer<CreateUpdateDeleteResourceResponse> {

    @Override
    public CreateUpdateDeleteResourceResponse deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException
    {
        Gson gson = new Gson();
        // Get Response
        CreateUpdateDeleteResourceResponse response = gson.fromJson(je.getAsJsonObject(), CreateUpdateDeleteResourceResponse.class);
        GsonSerializerHelper.fillDefaultsFields(response, je);
        return response;
    }

}
