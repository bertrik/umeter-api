package nl.bertriksikken.oauth2;

import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableMap;

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

    public String encode() {
        return values.keySet().stream().map(k -> k + "=" + URLEncoder.encode(values.get(k), Charsets.UTF_8))
                .collect(Collectors.joining("&"));
    }
    
    public Map<String, String> map() {
        return ImmutableMap.copyOf(values);
    }

}
