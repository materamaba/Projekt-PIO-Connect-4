package org.projekt;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Klient extends Application {
    private Rozgrywka gameToDisplay;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    private GridPane gridPane;
    private Circle[][] circles = new Circle[6][7];

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
        }).start();
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setOnCloseRequest(event -> {
            System.out.println("Game window closed");
            Platform.exit();
            System.exit(0);
        });

        initDisplayedBoard();

        initClientLogic();

        StackPane stackPane = new StackPane();
        Scene scene = new Scene(stackPane, 300, 250);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initDisplayedBoard() {
        gridPane = new GridPane();
        gridPane.setStyle("-fx-background-color: #0044AA; -fx-padding: 15;");
        gridPane.setHgap(12);
        gridPane.setVgap(12);

        for (int row = 0; row < 7; row++) {
            for (int col = 0; col < 6; col++) {
                Circle circle = new Circle(30);
                circle.setFill(Color.WHITE);
                circles[row][col] = circle;

                gridPane.add(circle, col, row);

                final int currentColumn = col;
                circle.setOnMouseClicked(event -> sendMoveToServer(currentColumn));
            }
        }
    }

    private void sendMoveToServer(int col) {
        try {
            //send col to server
        } catch (Exception e) {
            System.out.println("Cant send move to server");
        }
    }

    public static void main(String[] args){
        launch(args);
    }
}
