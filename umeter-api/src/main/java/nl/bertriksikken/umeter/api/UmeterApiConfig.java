package nl.bertriksikken.umeter.api;

import java.time.Duration;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(getterVisibility = Visibility.NONE)
public final class UmeterApiConfig {

    @JsonProperty("url")
    private String url = "https://app.umeter.nl";

    @JsonProperty("timeout")
    private int timeout = 30;

    public String getUrl() {
        return url;
    }

    public Duration getTimeout() {
        return Duration.ofSeconds(timeout);
    }
}
