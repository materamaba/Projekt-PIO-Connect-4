package org.projekt;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Klient extends Application {
    private Rozgrywka gameFromServer;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    private GridPane gridPane;
    private Circle[][] circles = new Circle[6][7];
    private Stage stage;
    private Scene gameScene;

    private Gracz player = new Gracz();
    
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

    private void startClientAndServerCommunicationThread(){
        new Thread(() -> {
            while (true){
                try{
                    gameFromServer = (Rozgrywka) in.readObject();

                    Platform.runLater(this::updateDisplayedBoard);
                }catch(Exception e){
                    System.out.println("Server disconnected");
                    break;
                }
            }
        }).start();
    }

    private void updateDisplayedBoard() {
        if (gameFromServer == null) return;
        if (stage != null && stage.getScene() != gameScene) {
            stage.setScene(gameScene);
        }
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                if (gameFromServer.checkDisk(row, col, 1) == 1) {
                    circles[row][col].setFill(Color.RED);
                } else if (gameFromServer.checkDisk(row, col, 2) == 1) {
                    circles[row][col].setFill(Color.YELLOW);
                } else {
                    circles[row][col].setFill(Color.WHITE);
                }
            }
        }
    }

    public void initClientLogic(String ip, int port, Runnable onError) {
        new Thread(() -> {
            try {
                connectToServer(ip, port);
                startClientAndServerCommunicationThread();
            } catch(Exception e) {
                if (onError != null) {
                    Platform.runLater(onError);
                }
            }
        }).start();
    }

    public void disconnect() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (Exception e) {}
    }

    @Override
    public void start(Stage primaryStage) {
    	this.stage = primaryStage;
        primaryStage.setOnCloseRequest(event -> {
            System.out.println("Game window closed");
            Platform.exit();
            System.exit(0);
        });
        initDisplayedBoard();
        gameScene = new Scene(gridPane, 530, 460);
        new Menu(primaryStage, this);
        Menu menu = new Menu(primaryStage, this);
        menu.showMainMenu();
        primaryStage.show();
    }

    private void initDisplayedBoard() {
        gridPane = new GridPane();
        gridPane.setStyle("-fx-background-color: #0044AA; -fx-padding: 15;");
        gridPane.setHgap(12);
        gridPane.setVgap(12);

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                Circle circle = new Circle(30);
                circle.setFill(Color.WHITE);
                circles[row][col] = circle;

                gridPane.add(circle, col, row);

                final int currentColumn = col;
                circle.setOnMouseClicked(event -> {
                    if(player.canPlayerMakeMove(gameFromServer)){
                        sendMoveToServer(currentColumn);
                    }
                });
            }
        }
    }

    private void sendMoveToServer(int col) {
        try {
            out.writeObject(col);
            out.flush();
        } catch (Exception e) {
            System.out.println("Cant send move to server");
        }
    }

    public void setPlayersTeam(Zespol team) {
        player.setTeam(team);
    }

    public static void main(String[] args){
        launch(args);
    }
}
