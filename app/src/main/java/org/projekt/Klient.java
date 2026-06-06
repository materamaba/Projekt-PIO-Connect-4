package org.projekt;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Klient extends Application {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public void connectToServer(String ipAddress, int port) throws Exception {
        if(port < 0 || port > 65535){
            throw new Exception("Bad port");
        }
    }

    @Override
    public void start(Stage primaryStage) {
        StackPane stackPane = new StackPane();
        Scene scene = new Scene(stackPane, 300, 250);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
