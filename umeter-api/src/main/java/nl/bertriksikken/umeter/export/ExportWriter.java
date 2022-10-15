package nl.bertriksikken.umeter.export;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

public final class ExportWriter {

    private final File file;
    private final CsvMapper mapper = new CsvMapper();

    public ExportWriter(File file) {
        this.file = file;
    }

    public void write(List<ExportRecord> records) throws IOException {
        CsvSchema schema = mapper.schemaFor(ExportRecord.class).withHeader();
        mapper.writer(schema).writeValue(file, records);
    }

}
