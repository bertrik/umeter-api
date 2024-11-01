package nl.bertriksikken.umeter.api;

import java.io.IOException;
import java.net.URL;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import nl.bertriksikken.umeter.api.CustomerData.Address;

public final class CustomerDataTest {

    @Test
    public void testDecode() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        URL url = this.getClass().getClassLoader().getResource("customer.json");
        CustomerData customerData = mapper.readValue(url, CustomerData.class);

        Assert.assertEquals("edfd586c-6b4c-437e-b5f2-08da95cd6b4d", customerData.id());
        Address address = customerData.addresses().get(0);
        Assert.assertEquals("elecMeterNumber", address.elecMeterNumber());
        Assert.assertEquals("ean", address.eEan().ean());
    }

}
