package nl.bertriksikken.umeter.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record P4Data(
        @JsonProperty("beginReadings") Readings beginReading,
        @JsonProperty("aData") List<PeriodReadings> periodReadings) {
    P4Data() {
        this(new Readings(), new ArrayList<>());
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record PeriodReadings(
            @JsonProperty("dateFrom")
            String dateFrom,
            @JsonProperty("dateTo")
            String dateTo,
            @JsonProperty("usage")
            Readings usage) {
        PeriodReadings() {
            this("", "", new Readings());
        }
    }

    // a set of counter values, either absolute or delta
    public record Readings(
            @JsonProperty("r181")
            double r181,
            @JsonProperty("r182")
            double r182,
            @JsonProperty("r281")
            double r281,
            @JsonProperty("r282")
            double r282) {
        Readings() {
            this(Double.NaN, Double.NaN, Double.NaN, Double.NaN);
        }
    }
}
