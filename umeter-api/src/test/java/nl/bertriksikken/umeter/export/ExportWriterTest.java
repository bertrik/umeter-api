package nl.bertriksikken.umeter.export;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.bertriksikken.umeter.api.P4Data;
import nl.bertriksikken.umeter.api.P4Data.PeriodReadings;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public final class ExportWriterTest {

    @TempDir
    File folder;

    @Test
    public void testExport() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        P4Data p4Data = mapper.readValue(this.getClass().getClassLoader().getResource("p4data.json"), P4Data.class);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                .withZone(ZoneId.of("Europe/Amsterdam"));
        List<ExportRecord> records = new ArrayList<>();
        for (PeriodReadings readings : p4Data.periodReadings()) {
            ZonedDateTime from = ZonedDateTime.parse(readings.dateFrom(), formatter);
            ZonedDateTime to = ZonedDateTime.parse(readings.dateTo(), formatter);
            ExportRecord record = new ExportRecord(from, to, readings.usage().r181(), readings.usage().r182(),
                    readings.usage().r281(), readings.usage().r282());
            records.add(record);
        }

        File file = new File(folder, "export.csv");
        ExportWriter writer = new ExportWriter(file);
        writer.write(records);
    }

}
