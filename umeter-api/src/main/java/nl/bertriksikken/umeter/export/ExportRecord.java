package nl.bertriksikken.umeter.export;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@JsonPropertyOrder({"date", "from", "until", "r181", "r182", "r281", "r282"})
public record ExportRecord(String date, String from, String until, double r181, double r182, double r281, double r282) {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_TIME;

    ExportRecord(ZonedDateTime from, ZonedDateTime until, double r181, double r182, double r281, double r282) {
        this(DATE_FORMATTER.format(from), TIME_FORMATTER.format(from), TIME_FORMATTER.format(until), r181, r182, r281, r282);
    }

}
