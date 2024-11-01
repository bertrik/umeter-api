package nl.bertriksikken.umeter.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CustomerData(@JsonProperty("id") String id,
                           @JsonProperty("addresses") List<Address> addresses) {
    CustomerData() {
        this("", new ArrayList<>());
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Address(
            @JsonProperty("id") String id,
            @JsonProperty("gId") String gId,
            @JsonProperty("status") String status,
            @JsonProperty("elecMeterNumber") String elecMeterNumber,
            @JsonProperty("eEan") Ean eEan) {
        Address() {
            this("", "", "", "", new Ean());
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Ean(@JsonProperty("ean") String ean) {
        Ean() {
            this("");
        }
    }
}
