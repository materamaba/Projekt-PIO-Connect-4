package org.projekt;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.ClientInfoStatus;

public class Klient extends Application {
    private Rozgrywka gameToDisplay;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public void connectToServer(String ipAddress, int port) throws Exception {
        if(port < 0 || port > 65535){
            throw new Exception("Bad port");
        }

        try {
            socket = new Socket(ipAddress, port);

            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new Exception("Can't connect to server");
        }
    }

    private void waitForServerToStartGame(){
        try{
            in.readObject();
        }catch(Exception e){
            System.out.println("Server disconnected");
        }
    }

    private void startClientAndServerCommunicationThread(){
        new Thread(() -> {
            while (true){
                try{
                    gameToDisplay = (Rozgrywka) in.readObject();
                }catch(Exception e){
                    System.out.println("Server disconnected");
                    break;
                }
            }
        }).start();
    }

    private void startGameDisplayThread(){
        new Thread(() -> {
            while (true){

            }
        }).start();
    }

    private void initClientLogic(){
        new Thread(() -> {
            try {
                connectToServer("localhost", 1234);
            }catch(Exception e){
                System.out.println("Cant connect to server");
                System.exit(0);
            }

            waitForServerToStartGame();

            startClientAndServerCommunicationThread();
            startGameDisplayThread();
        }).start();
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setOnCloseRequest(event -> {
            System.out.println("Game window closed");
            Platform.exit();
            System.exit(0);
        });

        initClientLogic();

        StackPane stackPane = new StackPane();
        Scene scene = new Scene(stackPane, 300, 250);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
