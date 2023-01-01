package nl.bertriksikken.oauth2;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.bertriksikken.umeter.RestApiConfig;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public final class AuthClient {

    private static final Logger LOG = LoggerFactory.getLogger(AuthClient.class);

    private final IAuthApi restApi;

    private AuthClient(IAuthApi restApi) {
        this.restApi = restApi;
    }

    public static AuthClient create(RestApiConfig config) {
        LOG.info("Creating new REST client for URL '{}' with timeout {}", config.getUrl(), config.getTimeout());
        OkHttpClient client = new OkHttpClient().newBuilder().callTimeout(config.getTimeout()).build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(config.getUrl())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create()).client(client).build();
        IAuthApi restApi = retrofit.create(IAuthApi.class);
        return new AuthClient(restApi);
    }

    public AuthResponse getToken(String userName, String password) throws IOException {
        AuthRequest request = AuthRequest.password(userName, password);
        Response<AuthResponse> response = restApi.getToken(request.map()).execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            LOG.info("Error response: {} - {}", response.code(), response.message());
            return null;
        }
    }

}
