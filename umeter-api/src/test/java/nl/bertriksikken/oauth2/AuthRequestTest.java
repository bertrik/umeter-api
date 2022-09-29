package nl.bertriksikken.oauth2;

import org.junit.Assert;
import org.junit.Test;

public final class AuthRequestTest {
    
    @Test
    public void testEncode() {
        AuthRequest request = AuthRequest.password("user@gmail.com", "password");
        String encoded = request.encode();
        Assert.assertEquals("grant_type=password&username=user%40gmail.com&password=password", encoded);
    }

}
