package nl.bertriksikken.umeter;

import java.time.Duration;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(getterVisibility = Visibility.NONE)
public final class RestApiConfig {
    @JsonProperty("url")
    private String url = "";

    @JsonProperty("timeout")
    private int timeoutSec = 0;

    private RestApiConfig() {
        // jackson constructor
    }

    public RestApiConfig(String url, int timeoutSec) {
        this();
        this.url = url;
        this.timeoutSec = timeoutSec;
    }

    public String getUrl() {
        return url;
    }

    public Duration getTimeout() {
        return Duration.ofSeconds(timeoutSec);
    }
}
