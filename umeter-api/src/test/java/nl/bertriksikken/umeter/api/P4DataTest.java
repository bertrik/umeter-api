package nl.bertriksikken.umeter.api;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import nl.bertriksikken.umeter.api.P4Data.QuarterData;
import nl.bertriksikken.umeter.api.P4Data.Readings;

public final class P4DataTest {

    @Test
    public void testDecode() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        URL url = this.getClass().getClassLoader().getResource("p4data.json");
        P4Data p4Data = mapper.readValue(url, P4Data.class);

        List<QuarterData> aData = p4Data.quarterData;
        Assert.assertTrue(aData.size() > 0);

        QuarterData quarterData = aData.get(0);
        Assert.assertEquals("2022-09-28 00:00:00", quarterData.dateFrom);
        Assert.assertEquals("2022-09-28 00:15:00", quarterData.dateTo);

        Readings usage = quarterData.usage;
        Assert.assertEquals(0.02, usage.r181, 0.01);
        Assert.assertEquals(0.0, usage.r182, 0.01);
        Assert.assertEquals(0.0, usage.r281, 0.01);
        Assert.assertEquals(0.0, usage.r282, 0.01);
    }

}
