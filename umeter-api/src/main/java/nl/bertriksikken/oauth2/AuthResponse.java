package nl.bertriksikken.oauth2;

import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class AuthResponse {

    @JsonProperty("access_token")
    public String accessToken = "";
    
    @JsonProperty("expires_in")
    public long expiresIn = 0;
    
    @JsonProperty("refresh_token")
    public String refreshToken = "";
    
    @JsonProperty("token_type")
    public ETokenType tokenType = ETokenType.UNKNOWN;
    
    @JsonProperty("scope")
    public String scope = "";
    
    @JsonProperty("userName")
    public String userName = "";
    
    @JsonProperty("accountId")
    public String accountId = "";
    
    @JsonProperty("error")
    public String error = "";
    
    public enum ETokenType {
        UNKNOWN("Unknown"),
        BEARER("Bearer");
        
        private final String id;

        ETokenType(String id) {
            this.id = id;
        }
        
        @Override
        @JsonValue
        public String toString() {
            return id;
        }
        
        @JsonCreator
        public static ETokenType fromString(String id) {
            return Stream.of(values()).filter(v -> v.id.equals(id)).findFirst().orElse(UNKNOWN);
        }
    }

}
