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
import nl.bertriksikken.umeter.api.P4Data.QuarterData;

public final class ExportWriterTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void testExport() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        P4Data data = mapper.readValue(this.getClass().getClassLoader().getResource("p4data.json"), P4Data.class);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                .withZone(ZoneId.of("Europe/Amsterdam"));
        List<ExportRecord> records = new ArrayList<>();
        for (QuarterData qdata : data.quarterData) {
            ZonedDateTime from = ZonedDateTime.parse(qdata.dateFrom, formatter);
            ZonedDateTime to = ZonedDateTime.parse(qdata.dateTo, formatter);
            ExportRecord record = new ExportRecord(from, to, qdata.usage.r181, qdata.usage.r182, qdata.usage.r281,
                    qdata.usage.r282);
            records.add(record);
        }

        File file = folder.newFile();
        ExportWriter writer = new ExportWriter(file);
        writer.write(records);
    }

}
