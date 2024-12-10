package nl.bertriksikken.umeter.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.bertriksikken.umeter.api.P4Data.PeriodReadings;
import nl.bertriksikken.umeter.api.P4Data.Readings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public final class P4DataTest {

    @Test
    public void testDecode() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        URL url = this.getClass().getClassLoader().getResource("p4data.json");
        P4Data p4Data = mapper.readValue(url, P4Data.class);

        List<PeriodReadings> aData = p4Data.periodReadings();
        Assertions.assertFalse(aData.isEmpty());

        PeriodReadings readings = aData.get(0);
        Assertions.assertEquals("2022-09-28 00:00:00", readings.dateFrom());
        Assertions.assertEquals("2022-09-28 00:15:00", readings.dateTo());

        Readings usage = readings.usage();
        Assertions.assertEquals(0.02, usage.r181(), 0.01);
        Assertions.assertEquals(0.0, usage.r182(), 0.01);
        Assertions.assertEquals(0.0, usage.r281(), 0.01);
        Assertions.assertEquals(0.0, usage.r282(), 0.01);
    }

}
