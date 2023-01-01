package nl.bertriksikken.umeter.auth;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import nl.bertriksikken.oauth2.AuthClient;
import nl.bertriksikken.oauth2.AuthException;
import nl.bertriksikken.oauth2.AuthResponse;

/**
 * Authentication services, handles one user.
 */
public final class AuthService implements IAuthService {

    private final ObjectMapper mapper = new ObjectMapper();
    private final AuthClient authClient;
    private final String user;
    private final String pass;

    public AuthService(AuthClient authClient, String user, String pass) {
        this.authClient = authClient;
        this.user = user;
        this.pass = pass;
    }

    @Override
    public String getBearerToken() throws AuthException, IOException {
        File file = createFile(user);
        AuthResponse authData = file.exists() ? mapper.readValue(file, AuthResponse.class) : fetchAuthToken(file);
        return "Bearer " + authData.accessToken;
    }

    @Override
    public boolean dropAuthToken() {
        File file = createFile(user);
        return file.delete();
    }
    
    /**
     * Explicitly fetches authentication token, and caches it on the file system
     */
    private AuthResponse fetchAuthToken(File file) throws IOException, AuthException {
        AuthResponse response = authClient.getToken(user, pass);
        if (response == null) {
            throw new AuthException("Could not get token for " + user);
        }
        mapper.writeValue(file, response);
        return response;
    }

    private File createFile(String user) {
        return new File(user + ".json");
    }

}
