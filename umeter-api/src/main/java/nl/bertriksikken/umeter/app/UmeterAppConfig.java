package nl.bertriksikken.umeter.app;

import com.fasterxml.jackson.annotation.JsonProperty;

import nl.bertriksikken.umeter.RestApiConfig;

public final class UmeterAppConfig {

    @JsonProperty("auth")
    public RestApiConfig authConfig = new RestApiConfig("https://auth.umeter.nl", 30);

    @JsonProperty("user")
    public String user = "";

    @JsonProperty("pass")
    public String pass = "";

    @JsonProperty("api")
    public RestApiConfig umeterApiConfig = new RestApiConfig("https://app.umeter.nl", 30);

    @JsonProperty("mp")
    public RestApiConfig meteringPointConfig = new RestApiConfig("https://mp.umeter.nl", 30);

}
