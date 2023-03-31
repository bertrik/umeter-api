package nl.bertriksikken.oauth2;

import java.util.LinkedHashMap;
import java.util.Map;

public final class AuthRequest {

    private final Map<String, String> values = new LinkedHashMap<>();

    private AuthRequest() {
        // do not allow public construction
    }

    public static AuthRequest password(String userName, String password) {
        AuthRequest request = new AuthRequest();
        request.values.put("grant_type", "password");
        request.values.put("username", userName);
        request.values.put("password", password);
        return request;
    }
    
    public Map<String, String> map() {
        return Map.copyOf(values);
    }

}
