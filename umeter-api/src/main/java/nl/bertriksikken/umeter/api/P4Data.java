package nl.bertriksikken.umeter.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class P4Data {

    @JsonProperty("beginReadings")
    public Readings beginReading = new Readings();

    @JsonProperty("aData")
    public List<PeriodReadings> periodReadings = new ArrayList<>();

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "{beginReading=%s,periodReadings=%s}", beginReading, periodReadings);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class PeriodReadings {
        @JsonProperty("dateFrom")
        public String dateFrom = "";

        @JsonProperty("dateTo")
        public String dateTo = "";

        @JsonProperty("usage")
        public Readings usage = new Readings();

        @Override
        public String toString() {
            return String.format(Locale.ROOT, "{from=%s,to=%s,usage=%s}", dateFrom, dateTo, usage);
        }
    }

    // a set of counter values, either absolute or delta
    public static final class Readings {
        @JsonProperty("r181")
        public double r181 = Double.NaN;
        @JsonProperty("r182")
        public double r182 = Double.NaN;
        @JsonProperty("r281")
        public double r281 = Double.NaN;
        @JsonProperty("r282")
        public double r282 = Double.NaN;

        public String toString() {
            return String.format(Locale.ROOT, "{r181=%.2f,r182=%.2f,r281=%.2f,r282=%.2f}", r181, r182, r281, r282);
        }
    }
}
