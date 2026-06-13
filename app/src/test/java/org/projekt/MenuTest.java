package org.projekt;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MenuTest {
	@Test
    public void testGetLocalIpAddress() {
        Menu menu = new Menu(null, null);
        try {
            String ip = menu.getLocalIpAddress();
            assertNotNull(ip, "IP address should not be null");
            assertTrue(ip.length() > 0, "IP address should not be empty");
        } catch (Exception e) {
            fail("Retrieving IP address should not throw any exceptions");
        }
    }
}