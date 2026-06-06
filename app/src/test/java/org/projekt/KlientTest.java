package org.projekt;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

public class KlientTest {
    @Test
    void connectToBadPortTest() {
        try{
            Klient client = new Klient();
            client.connectToServer("localhost", -1234);
            fail("Client should check port");
        }catch(Exception e){
            // this test should throw exception
        }
    }

    @Test
    void connectToBadIPAddressTest() {
        try{
            Klient client = new Klient();
            client.connectToServer("badIP", 1234);
            fail("Client should ip address");
        }catch(Exception e){
            // this test should throw exception
        }
    }
}
