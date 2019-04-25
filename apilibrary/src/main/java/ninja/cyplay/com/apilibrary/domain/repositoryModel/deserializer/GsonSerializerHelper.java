package ninja.cyplay.com.apilibrary.domain.repositoryModel.deserializer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import ninja.cyplay.com.apilibrary.domain.repositoryModel.responses.BaseResponse;

/**
 * Created by damien on 18/12/15.
 */
public class GsonSerializerHelper {

    public static void fillDefaultsFields(BaseResponse response, JsonElement je) {
        if (response != null && je != null && ! (je instanceof JsonArray) && je.getAsJsonObject().has("status") && je.getAsJsonObject().has("detail")) {
            // Status
            if (je.getAsJsonObject().has("status"))
                response.setStatus(je.getAsJsonObject().get("status").getAsString());
            // Old Status
            if (je.getAsJsonObject().has("stt"))
                response.setStatus(je.getAsJsonObject().get("stt").getAsString());
            // Detail
            if (je.getAsJsonObject().has("detail"))
                response.setDetail(je.getAsJsonObject().get("detail").getAsString());
        }
    }
}
