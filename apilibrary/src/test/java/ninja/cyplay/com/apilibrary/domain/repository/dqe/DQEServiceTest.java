package ninja.cyplay.com.apilibrary.domain.repository.dqe;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import ninja.cyplay.com.apilibrary.BuildConfig;
import ninja.cyplay.com.apilibrary.TestApplication;
import ninja.cyplay.com.apilibrary.models.abstractmodels.DQEResult;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

import static org.junit.Assert.assertNotNull;


/**
 * Created by wentongwang on 21/06/2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(application = TestApplication.class, constants = BuildConfig.class)
public class DQEServiceTest {


    private DQEService dqeService;

    @Before
    public void setUp() {
        //print log
        ShadowLog.stream = System.out;

        GsonConverter gsonConverter = new GsonConverter(initGson());
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(DQEApiRepository.DEFAULT_BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setClient(new OkClient())
                .setConverter(gsonConverter)
                .build();

        dqeService = restAdapter.create(DQEService.class);

    }

    private Gson initGson() {
        return new GsonBuilder()
                .setDateFormat(DQEApiRepository.GSON_BUILDER_DATE_FORMAT)
                .create();
    }

    @Test
    public void publicRepositories() throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("Adresse", "18 rue du");
        params.put("Pays", "FRA");
        params.put("Licence", "CYPLW0PAEL");

        Map<String, DQEResult> resultMap = dqeService.getAddress(params);

        if (resultMap.size() > 0) {
            assertNotNull(resultMap.get("1"));
        }
    }

}
