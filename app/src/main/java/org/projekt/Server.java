package org.projekt;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    private Game game;
    private final int PORT = 1234;

    //Player 1
    private Socket socket1;
    private ObjectOutputStream out1;
    private ObjectInputStream in1;

    //Player 2
    private Socket socket2;
    private ObjectOutputStream out2;
    private ObjectInputStream in2;

    public static void main(String[] args) {
        Server server = new Server();
        server.startServer();
    }

    public void startServer() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Serwer uruchomiony na porcie " + PORT + ". Oczekiwanie na graczy...");

            //Player 1
            socket1 = serverSocket.accept();
            System.out.println("Gracz 1 połączony!");
            out1 = new ObjectOutputStream(socket1.getOutputStream());
            in1 = new ObjectInputStream(socket1.getInputStream());

            //Player 2
            socket2 = serverSocket.accept();
            System.out.println("Gracz 2 połączony!");
            out2 = new ObjectOutputStream(socket2.getOutputStream());
            in2 = new ObjectInputStream(socket2.getInputStream());

            System.out.println("Obaj gracze połączeni. Rozpoczynamy grę!");
            game = new Game();
            gameStatusRefresh();
            gameLoop();
        }
        catch (IOException error) {
            System.out.println("Błąd uruchamiania serwera: " + error.getMessage());
        }
    }

    private void gameStatusRefresh() throws IOException {
        //Player 1
        out1.reset();
        out1.writeObject(game);
        out1.flush();

        //Player 2
        out2.reset();
        out2.writeObject(game);
        out2.flush();
    }

    private void gameLoop() {
        int currentPlayer;
        int selectedColumn;

        while (true) {
            try {
                currentPlayer = game.getNextPlayer();
                selectedColumn = readPlayerMove(currentPlayer);
                System.out.println("Gracz " + currentPlayer + " wrzucił krążek do kolumny: " + selectedColumn);
                gameProcess(selectedColumn);
            }
            catch (IOException | ClassNotFoundException error) {
                System.out.println("Gra przerwana! Jeden z graczy się rozłączył.");
                stopServer();
                break;
            }
        }
    }

    private int readPlayerMove(int currentPlayer) throws IOException, ClassNotFoundException {
        if (currentPlayer == 1) {
            System.out.println("Oczekiwanie na ruch Gracza 1...");
            return (int)in1.readObject();
        }
        else {
            System.out.println("Oczekiwanie na ruch Gracza 2...");
            return (int)in2.readObject();
        }
    }

    private void gameProcess(int selectedColumn) {
        try {
            int gameStatus = game.makeMove(selectedColumn);
            gameStatusRefresh();

            if (gameStatus == 1 || gameStatus == 2) {
                System.out.println("Gra zakończona! Wygrał Gracz " + gameStatus);
            }
        }
        catch (Exception error) {
            System.out.println("Nieprawidłowy ruch: " + error.getMessage());
        }
    }
    
    public void stopServer() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            if (socket1 != null && !socket1.isClosed()) socket1.close();
            if (socket2 != null && !socket2.isClosed()) socket2.close();
        } catch (IOException error) {
        }
    }
}