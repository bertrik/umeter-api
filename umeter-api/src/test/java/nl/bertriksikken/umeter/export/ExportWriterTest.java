package nl.bertriksikken.umeter.export;

import java.io.File;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.fasterxml.jackson.databind.ObjectMapper;

import nl.bertriksikken.umeter.api.P4Data;
import nl.bertriksikken.umeter.api.P4Data.PeriodReadings;

public final class ExportWriterTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void testExport() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        P4Data p4Data = mapper.readValue(this.getClass().getClassLoader().getResource("p4data.json"), P4Data.class);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                .withZone(ZoneId.of("Europe/Amsterdam"));
        List<ExportRecord> records = new ArrayList<>();
        for (PeriodReadings readings : p4Data.periodReadings) {
            ZonedDateTime from = ZonedDateTime.parse(readings.dateFrom, formatter);
            ZonedDateTime to = ZonedDateTime.parse(readings.dateTo, formatter);
            ExportRecord record = new ExportRecord(from, to, readings.usage.r181, readings.usage.r182,
                    readings.usage.r281, readings.usage.r282);
            records.add(record);
        }

        File file = folder.newFile();
        ExportWriter writer = new ExportWriter(file);
        writer.write(records);
    }

}
