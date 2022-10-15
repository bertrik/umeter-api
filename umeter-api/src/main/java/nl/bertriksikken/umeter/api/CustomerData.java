package nl.bertriksikken.umeter.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class CustomerData {

    @JsonProperty("id")
    public String id = "";

    @JsonProperty("addresses")
    public List<Address> addresses = new ArrayList<>();

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "{id=%s,addresses=%s}", id, addresses);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Address {
        @JsonProperty("id")
        public String id = "";

        @JsonProperty("gId")
        public String gId = "";

        @JsonProperty("status")
        public String status = "";

        @JsonProperty("elecMeterNumber")
        public String elecMeterNumber = "";

        @JsonProperty("eEan")
        public Ean eEan = new Ean();

        @Override
        public String toString() {
            return String.format(Locale.ROOT, "{id=%s,gid=%s,status=%s,elecMeterNumber=%s,ean=%s", id, gId, status,
                    elecMeterNumber, eEan);
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static final class Ean {
            @JsonProperty("ean")
            public String ean = "";

            @Override
            public String toString() {
                return String.format(Locale.ROOT, "{ean=%s}", ean);
            }
        }
    }
}
