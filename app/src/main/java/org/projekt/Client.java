package org.projekt;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Client extends Application {
    public static final int HEIGHT = 6;
    public static final int WIDTH = 7;
    public static final int MAX_PORT = 65535;
    public static final int MIN_PORT = 0;
    public static final int SCENE_WIDTH = 530;
    public static final int SCENE_HEIGHT = 460;
    public static final int CIRCLE_RADIUS = 30;
    public static final int PIXEL_GAP = 12;
    private Game gameFromServer;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    private GridPane gridPane;
    private Circle[][] circles = new Circle[HEIGHT][WIDTH];
    private Stage stage;
    private Scene gameScene;

    private Player player = new Player();
    
    public void connectToServer(String ipAddress, int port) throws Exception {
        if(port < MIN_PORT || port > MAX_PORT){
            throw new Exception("zły port");
        }

        try {
            socket = new Socket(ipAddress, port);

            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new Exception("Nie udało się połączyć z serwerem");
        }
    }

    private void startClientAndServerCommunicationThread(){
        new Thread(() -> {
            while (true){
                try{
                    gameFromServer = (Game) in.readObject();

                    Platform.runLater(this::updateDisplayedBoard);
                }catch(Exception e){
                    System.out.println("Utracono połączenie z serwerem");

                    Platform.runLater(() -> {
                        if (gameFromServer != null && gameFromServer.isGameFinished() == 0) {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Przerwana gra");
                            alert.setHeaderText(null);
                            alert.setContentText("Przeciwnik się rozłączył!");
                            alert.showAndWait();

                            disconnect();
                            gameFromServer = null;
                            new Menu(stage, Client.this).showMainMenu();
                        }
                    });
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
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                if (gameFromServer.checkDisk(row, col, 1) == 1) {
                    circles[row][col].setFill(Color.RED);
                } else if (gameFromServer.checkDisk(row, col, 2) == 1) {
                    circles[row][col].setFill(Color.YELLOW);
                } else {
                    circles[row][col].setFill(Color.WHITE);
                }
            }
        }

        if (gameFromServer.isGameFinished() != 0) {
            handleGameOver();
        }
    }

    private void handleGameOver() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Koniec gry");
        alert.setHeaderText(null);

        if (gameFromServer.isGameFinished() == 2) {
            alert.setContentText("Remis!");
        } else {
            int winner = gameFromServer.getNextPlayer();
            alert.setContentText("Wygrał gracz " + winner + "!");
        }

        alert.showAndWait();

        disconnect();
        gameFromServer = null;
        new Menu(stage, this).showMainMenu();
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
            System.out.println("Zamknięto okno");
            Platform.exit();
            System.exit(0);
        });
        initDisplayedBoard();
        gameScene = new Scene(gridPane, SCENE_WIDTH, SCENE_HEIGHT);
        new Menu(primaryStage, this);
        Menu menu = new Menu(primaryStage, this);
        menu.showMainMenu();
        primaryStage.show();
    }

    private void initDisplayedBoard() {
        gridPane = new GridPane();
        gridPane.setStyle("-fx-background-color: #0044AA; -fx-padding: 15;");
        gridPane.setHgap(PIXEL_GAP);
        gridPane.setVgap(PIXEL_GAP);

        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                Circle circle = new Circle(CIRCLE_RADIUS);
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
            System.out.println("Nie udało się przesłać ruchu");
        }
    }

    public void setPlayersTeam(Team team) {
        player.setTeam(team);
    }

    public static void main(String[] args){
        launch(args);
    }
}
