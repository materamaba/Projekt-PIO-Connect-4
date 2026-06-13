package org.projekt;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

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
    
    @Test
    public void testDisconnect() {
        Klient klient = new Klient();
        
        try {
            klient.disconnect();
        } catch (Exception e) {
            fail("Disconnect caused an unexpected error: " + e.getMessage());
        }
    }
    
    @Test
    public void testNullInsteadOnError() {
        Klient klient = new Klient();
        
        try {
            klient.initClientLogic("127.0.0.1", 1234, null);
            Thread.sleep(300);
            
        } catch (Exception e) {
            fail("The method should handle null as onError, but it threw an exception: " + e.getMessage());
        }
    }

    @Test
    @Timeout(value = 2, unit = TimeUnit.SECONDS)
    void connectToValidServerTest() {
        try (ServerSocket server = new ServerSocket(0)) {
            int activePort = server.getLocalPort();
            new Thread(() -> {
                try (Socket clientSocket = server.accept();
                     ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                     ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())) {
                } catch (Exception e) {
                }
            }).start();

            Klient client = new Klient();
            client.connectToServer("localhost", activePort);
            client.disconnect();

        } catch (Exception e) {
            fail("Client should connect to server");
        }
    }
}
