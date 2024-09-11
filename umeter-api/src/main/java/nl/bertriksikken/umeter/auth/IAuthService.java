package nl.bertriksikken.umeter.auth;

import java.io.IOException;

import nl.bertriksikken.oauth2.AuthException;

public interface IAuthService {

    /**
     * Gets cached token, or attempts to retrieve it.
     */
    String getBearerToken() throws AuthException, IOException;

    /**
     * Drops the existing authentication token
     * @return true if it was actually dropped.
     */
    boolean dropAuthToken();

}
