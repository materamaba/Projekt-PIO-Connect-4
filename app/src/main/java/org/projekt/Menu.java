package org.projekt;
import java.net.InetAddress;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Menu {
	private final Stage stage;
	private final Client clientEntity;
	private Server activeServer;
	
	public Menu(Stage stage, Client clientInstance) {
		this.stage = stage;
		this.clientEntity = clientInstance;
	}

	private void style(VBox layout) {
		layout.setAlignment(Pos.CENTER);
		layout.setStyle("-fx-background-color: #0066CC;");
		layout.setStyle(layout.getStyle() + "-fx-font-family: 'Arial';");
	}

	public void showMainMenu() {
		Label title = new Label("CONNECT 4");
		title.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: yellow;");
		Button multiplayerButton = new Button("Gra Multiplayer");
		Button botButton = new Button("Gra z Botem");
		multiplayerButton.setPrefWidth(200);
		botButton.setPrefWidth(200);
		multiplayerButton.setStyle("-fx-font-size: 16px;");
		botButton.setStyle("-fx-font-size: 16px;");
		multiplayerButton.setOnAction(e -> showMultiplayerMenu());
		botButton.setOnAction(e -> {
			handleBotGame();
			clientEntity.setPlayersTeam(Team.RED);
		});
		VBox layout = new VBox(20, title, multiplayerButton, botButton);
		style(layout);
		stage.setScene(new Scene(layout, 400, 400));
	}

	private void showMultiplayerMenu() {
		Label title = new Label("MULTIPLAYER");
		title.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: yellow;");
		Button hostButton = new Button("Hostuj");
		Button joinButton = new Button("Dołącz");
		Button backButton = new Button("Powrót");
		hostButton.setPrefWidth(200);
		joinButton.setPrefWidth(200);
		backButton.setPrefWidth(200);
		hostButton.setStyle("-fx-font-size: 16px;");
		joinButton.setStyle("-fx-font-size: 16px;");
		backButton.setStyle("-fx-font-size: 16px;");
		hostButton.setOnAction(e -> {
			if (isServerIsAlreadyStarted()){
				clientEntity.setPlayersTeam(Team.YELLOW);
				clientEntity.initClientLogic("localhost", 1234, null);
			}else{
				clientEntity.setPlayersTeam(Team.RED);
				startServerAndGoToHostMenu();
			}
		});
		joinButton.setOnAction(e -> {
			showJoinMenu();
			clientEntity.setPlayersTeam(Team.YELLOW);
		});
		backButton.setOnAction(e -> showMainMenu());
		VBox layout = new VBox(20, title, hostButton, joinButton, backButton);
		style(layout);
		stage.setScene(new Scene(layout, 400, 400));
	}

	private boolean isServerIsAlreadyStarted() {
		try{
			new java.net.ServerSocket(1234).close();
			return false;
		}catch(Exception e){
			return true;
		}
	}

	private void showHostMenu() {
		Label title = new Label("HOSTING");
		title.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: yellow;");
		String serverIp = getLocalIpAddress();
		Label ipLabel = new Label("IP dla 2 gracza to: " + serverIp);
		ipLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-font-weight: bold;");
		Label waitingLabel = new Label("Oczekiwanie na 2 gracza...");
		waitingLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #2ecc71; -fx-font-weight: bold;");
		Button backButton = new Button("Powrót");
		backButton.setPrefWidth(250);
		backButton.setStyle("-fx-font-size: 16px;");
		backButton.setOnAction(e -> {
			if (activeServer != null) {
				activeServer.stopServer();
			}
			clientEntity.disconnect();
			showMultiplayerMenu();
		});
		VBox layout = new VBox(20, title, ipLabel, waitingLabel, backButton);
		style(layout);
		stage.setScene(new Scene(layout, 400, 400));
	}

	private void showJoinMenu() {
		Label title = new Label("Dołącz");
		title.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: yellow;");
		TextField ipField = new TextField();
		ipField.setPromptText("Podaj adres IP"); 
		ipField.setMaxWidth(250);
		ipField.setStyle("-fx-font-size: 14px;");
		Button confirmButton = new Button("Zatwierdź");
		Button backButton = new Button("Powrót");
		confirmButton.setPrefWidth(200);
		backButton.setPrefWidth(200);
		confirmButton.setStyle("-fx-font-size: 16px;");
		backButton.setStyle("-fx-font-size: 16px;");
		confirmButton.setOnAction(e -> {
			String ip = ipField.getText().trim();
			if (ip.isEmpty()) {
				confirmButton.setText("Niepoprawne IP");
				confirmButton.setStyle("-fx-font-size: 16px; -fx-background-color: #ff4d4d; -fx-text-fill: white;");
			} else {
				confirmButton.setText("Lączenie...");
				handleJoinServer(ip, confirmButton);
			}
		});
		backButton.setOnAction(e -> {
			clientEntity.disconnect();
			showMultiplayerMenu();
		});
		VBox layout = new VBox(20, title, ipField, confirmButton, backButton);
		style(layout);
		stage.setScene(new Scene(layout, 400, 400));
	}

	private void startServerAndGoToHostMenu() {
		activeServer = new Server();
		new Thread(() -> {
			activeServer.startServer();
		}).start();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		clientEntity.initClientLogic("localhost", 1234, null);

		showHostMenu();
	}

	private void handleJoinServer(String ip, Button button) {
		clientEntity.initClientLogic(ip, 1234, () -> {
			button.setText("Niepoprawne IP");
			button.setStyle("-fx-font-size: 16px; -fx-background-color: #ff4d4d; -fx-text-fill: white;");
		});
	}

	private void handleBotGame() {
        if (activeServer != null) {
            activeServer.stopServer(); 
        }
        clientEntity.disconnect();

        activeServer = new Server();
        new Thread(() -> {
            activeServer.startServer();
        }).start();
        
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        clientEntity.initClientLogic("localhost", 1234, null);

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Bot bot = new Bot();
        new Thread(() -> {
            bot.start("localhost", 1234);
        }).start();
    }

	public String getLocalIpAddress() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (Exception e) {
			return "127.0.0.1";
		}
	}
}