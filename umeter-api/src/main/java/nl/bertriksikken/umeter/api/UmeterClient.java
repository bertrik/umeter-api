package nl.bertriksikken.umeter.api;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.bertriksikken.oauth2.AuthClient;
import nl.bertriksikken.oauth2.AuthException;
import nl.bertriksikken.umeter.RestApiConfig;
import nl.bertriksikken.umeter.auth.AuthService;
import nl.bertriksikken.umeter.auth.IAuthService;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public final class UmeterClient {

    private static final Logger LOG = LoggerFactory.getLogger(AuthClient.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    private final IUmeterApi restApi;
    private final IAuthService authService;

    private UmeterClient(IUmeterApi restApi, IAuthService authService) {
        this.restApi = restApi;
        this.authService = authService;
    }

    public static UmeterClient create(RestApiConfig config, AuthService authService) {
        LOG.info("Creating new REST client for URL '{}' with timeout {}", config.getUrl(), config.getTimeout());
        OkHttpClient client = new OkHttpClient().newBuilder().callTimeout(config.getTimeout()).build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(config.getUrl())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create()).client(client).build();
        IUmeterApi restApi = retrofit.create(IUmeterApi.class);
        return new UmeterClient(restApi, authService);
    }

    public CustomerData getCustomer() throws IOException, AuthException {
        String bearerToken = authService.getBearerToken();
        try {
            return executeCustomerDataRequest(bearerToken);
        } catch (AuthException e) {
            // try once more with new auth data, if it failed because of authentication
            LOG.info("Authentication failed, refetching token and trying again...");
            authService.dropAuthToken();
            bearerToken = authService.getBearerToken();
            return executeCustomerDataRequest(bearerToken);
        }
    }

    private CustomerData executeCustomerDataRequest(String bearerToken) throws IOException, AuthException {
        Response<CustomerData> response = restApi.getCustomer(bearerToken).execute();
        if (!response.isSuccessful()) {
            if (response.code() == 401) {
                throw new AuthException("Not authorized");
            } else {
                throw new IOException(response.errorBody().string());
            }
        }
        return response.body();
    }

    /**
     * Fetches usage data.
     * <p>
     * Resolution depends on the requested time span:
     * <ul>
     * <li>1 hour to 24 hours => 15 minute resolution
     * <li>25 hours to 4 days => 1 hour resolution
     * <li>5 days or more => 1 day resolution
     * </ul>
     */
    public P4Data getP4Data(String ean, ZonedDateTime from, ZonedDateTime to) throws IOException, AuthException {
        String bearerToken = authService.getBearerToken();
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
