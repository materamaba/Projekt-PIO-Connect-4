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

    /*
    @Test
    void connectToServerTest() {
        try{
            Klient client = new Klient();
            try(ServerSocket serverSocket = new ServerSocket(0)){

                client.connectToServer("localhost",1234);
            }catch (Exception e){
                fail("Client should connect to server");
            }
        }catch(Exception e){
            fail("Client constructor error");
        }
    }*/
}
