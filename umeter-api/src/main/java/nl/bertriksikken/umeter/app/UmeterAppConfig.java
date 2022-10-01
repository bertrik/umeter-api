package nl.bertriksikken.umeter.app;

import com.fasterxml.jackson.annotation.JsonProperty;

import nl.bertriksikken.oauth2.AuthApiConfig;
import nl.bertriksikken.umeter.api.UmeterApiConfig;

public final class UmeterAppConfig {

    @JsonProperty("auth")
    public AuthApiConfig authConfig = new AuthApiConfig("https://auth.umeter.nl", 30);

    @JsonProperty("user")
    public String user = "";

    @JsonProperty("pass")
    public String pass = "";

    @JsonProperty("api")
    public UmeterApiConfig umeterApiConfig = new UmeterApiConfig();

}
