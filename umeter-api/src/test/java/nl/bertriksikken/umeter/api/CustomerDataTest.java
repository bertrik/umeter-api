package nl.bertriksikken.umeter.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.bertriksikken.umeter.api.CustomerData.Address;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;

public final class CustomerDataTest {

    @Test
    public void testDecode() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        URL url = this.getClass().getClassLoader().getResource("customer.json");
        CustomerData customerData = mapper.readValue(url, CustomerData.class);

        Assertions.assertEquals("edfd586c-6b4c-437e-b5f2-08da95cd6b4d", customerData.id());
        Address address = customerData.addresses().get(0);
        Assertions.assertEquals("elecMeterNumber", address.elecMeterNumber());
        Assertions.assertEquals("ean", address.eEan().ean());
    }

}
