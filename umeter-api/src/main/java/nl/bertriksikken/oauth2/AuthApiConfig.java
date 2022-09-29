package nl.bertriksikken.oauth2;

import java.time.Duration;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(getterVisibility = Visibility.NONE)
public final class AuthApiConfig {
    
    @JsonProperty("url")
    private String url = "";
    
    @JsonProperty("timeout")
    private int timeout = 0;
    
    private AuthApiConfig() {
        // jackson constructor
    }
    
    public AuthApiConfig(String url, int timeout) {
        this();
        this.url = url;
        this.timeout = timeout;
    }
    
    public String getUrl() {
        return url;
    }
    
    public Duration getTimeout() {
        return Duration.ofSeconds(timeout);
    }

}
