package nl.bertriksikken.umeter.export;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "date", "from", "until", "r181", "r182", "r281", "r282" })
public final class ExportRecord {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_TIME;

    @JsonProperty("date")
    private final String date;

    @JsonProperty("from")
    private final String fromTime;

    @JsonProperty("until")
    private final String untilTime;

    @JsonProperty("r181")
    private final double r181;
    @JsonProperty("r182")
    private final double r182;
    @JsonProperty("r281")
    private final double r281;
    @JsonProperty("r282")
    private final double r282;

    ExportRecord(ZonedDateTime from, ZonedDateTime until, double r181, double r182, double r281, double r282) {
        this.date = DATE_FORMATTER.format(from);
        this.fromTime = TIME_FORMATTER.format(from);
        this.untilTime = TIME_FORMATTER.format(until);
        this.r181 = r181;
        this.r182 = r182;
        this.r281 = r281;
        this.r282 = r282;
    }

}
