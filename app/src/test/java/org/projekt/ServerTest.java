package org.projekt;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import static org.junit.jupiter.api.Assertions.fail;

public class ServerTest {
    @Test
    public void testServerConnections() throws InterruptedException {
        Thread serverThread = new Thread(() -> {
            Server server = new Server();
            server.startServer();
        });

        serverThread.start();
        Thread.sleep(500);

        try {
            //Player 1
            Socket client1 = new Socket("localhost", 1234);
            ObjectOutputStream out1 = new ObjectOutputStream(client1.getOutputStream());

            //Player 2
            Socket client2 = new Socket("localhost", 1234);
            ObjectOutputStream out2 = new ObjectOutputStream(client2.getOutputStream());

            //Player 1
            out1.close();
            client1.close();
            //Player 2
            out2.close();
            client2.close();
        }
        catch (IOException error) {
            fail("Serwer nie przyjął połączenia. Powód błędu: " + error.getMessage());
        }

        serverThread.interrupt();
    }
}
