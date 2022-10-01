package nl.bertriksikken.umeter.api;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.bertriksikken.oauth2.AuthApi;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public final class UmeterApi {

    private static final Logger LOG = LoggerFactory.getLogger(AuthApi.class);
    private final IUmeterApi restApi;

    private UmeterApi(IUmeterApi restApi) {
        this.restApi = restApi;
    }

    public static UmeterApi create(UmeterApiConfig config) {
        LOG.info("Creating new REST client for URL '{}' with timeout {}", config.getUrl(), config.getTimeout());
        OkHttpClient client = new OkHttpClient().newBuilder().callTimeout(config.getTimeout()).build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(config.getUrl())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create()).client(client).build();
        IUmeterApi restApi = retrofit.create(IUmeterApi.class);
        return new UmeterApi(restApi);
    }

    public CustomerData getCustomer(String authToken) throws IOException {
        String bearerToken = "Bearer " + authToken;
        Response<CustomerData> response = restApi.getCustomer(bearerToken).execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            LOG.info("Error response: {}", response.errorBody().string());
            return null;
        }
    }

}
