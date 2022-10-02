package nl.bertriksikken.umeter.api;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.bertriksikken.umeter.RestApiConfig;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public final class MeteringPointApi {

    private static final Logger LOG = LoggerFactory.getLogger(MeteringPointApi.class);

    private final IMeteringPointApi restApi;

    private MeteringPointApi(IMeteringPointApi restApi) {
        this.restApi = restApi;
    }

    public static MeteringPointApi create(RestApiConfig config) {
        LOG.info("Creating new REST client for URL '{}' with timeout {}", config.getUrl(), config.getTimeout());
        OkHttpClient client = new OkHttpClient().newBuilder().callTimeout(config.getTimeout()).build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(config.getUrl())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create()).client(client).build();
        IMeteringPointApi restApi = retrofit.create(IMeteringPointApi.class);
        return new MeteringPointApi(restApi);
    }

    public String getLastDayRequests(String authToken, String ean, int number) throws IOException {
        Response<String> response = restApi.getLastDayRequests(authToken, ean, number).execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            LOG.info("Error response: {}", response.errorBody().string());
            return "";
        }
    }

}
