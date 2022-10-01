package nl.bertriksikken.umeter.auth;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import nl.bertriksikken.oauth2.AuthApi;
import nl.bertriksikken.oauth2.AuthResponse;

public final class AuthService {

    private final Logger LOG = LoggerFactory.getLogger(AuthService.class);

    private final ObjectMapper mapper = new ObjectMapper();
    private final AuthApi authApi;

    public AuthService(AuthApi authApi) {
        this.authApi = authApi;
    }

    // get cached token, return empty string if not found
    public String getToken(String user, String pass) {
        File file = new File(user + ".json");
        AuthResponse authData = null;
        try {
            authData = mapper.readValue(file, AuthResponse.class);
        } catch (IOException e) {
            if (!file.delete()) {
                LOG.warn("Failed to delete invalid {}", file);
            }
        }
        if (authData == null) {
            try {
                authData = authApi.getToken(user, pass);
                mapper.writeValue(file, authData);
            } catch (IOException e) {
                LOG.warn("Could not fetch/cache token: ", e);
                return "";
            }
        }
        return authData.accessToken;
    }

}
