package nl.bertriksikken.umeter.api;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.bertriksikken.oauth2.AuthApi;
import nl.bertriksikken.umeter.RestApiConfig;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public final class UmeterApi {

    private static final Logger LOG = LoggerFactory.getLogger(AuthApi.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    private final IUmeterApi restApi;

    private UmeterApi(IUmeterApi restApi) {
        this.restApi = restApi;
    }

    public static UmeterApi create(RestApiConfig config) {
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
            LOG.info("Error response: {} - {}", response.code(), response.message());
            return null;
        }
    }

    public P4Data getP4Data(String authToken, String ean, ZonedDateTime from, ZonedDateTime to) throws IOException {
        String bearerToken = "Bearer " + authToken;
        String fromDate = DATE_FORMATTER.format(from);
        String toDate = DATE_FORMATTER.format(to);
        Response<P4Data> response = restApi.getP4Data(bearerToken, ean, fromDate, toDate, false).execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            LOG.info("Error response: {} - {}", response.code(), response.message());
            return null;
        }
    }

}
