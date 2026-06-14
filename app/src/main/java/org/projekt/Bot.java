package org.projekt;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Bot {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    
    private final int botID = 2;
    private final int humanID = 1;

    public static void main(String[] args) {
        Bot bot = new Bot();
        bot.start("localhost", 1234);
    }

    public void start(String ip, int port) {
        try {
            socket = new Socket(ip, port);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            System.out.println("Bot podłączony.");
            playLoop();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    int getDropRow(Game game, int col) {
        if (col < 0 || col > 6) {
            return -1; 
        }
        for (int row = 5; row >= 0; row--) {
            if (game.checkDisk(row, col, 1) == 0 && game.checkDisk(row, col, 2) == 0) {
                return row; 
            }
        }
        return -1; 
    }

    int checkTrioInRow(Game game, int playerId) {
        for (int row = 0; row < 6; row++) {
            for (int startCol = 0; startCol < 4; startCol++) {
                int playerDisks = 0;
                int emptyCells = 0;
                int targetCol = -1;

                for (int i = 0; i < 4; i++) {
                    int currentCol = startCol + i;
                    
                    if (game.checkDisk(row, currentCol, playerId) == 1) {
                        playerDisks++;
                    } else if (game.checkDisk(row, currentCol, 1) == 0 && 
                               game.checkDisk(row, currentCol, 2) == 0) {
                        emptyCells++;
                        targetCol = currentCol; 
                    }
                }

                if (playerDisks == 3 && emptyCells == 1) {
                    if (getDropRow(game, targetCol) == row) {
                        return targetCol; 
                    }
                }
            }
        }
        return -1; 
    }

    int checkTrioInCol(Game game, int playerId) {
        for (int col = 0; col < 7; col++) {
            for (int startRow = 0; startRow < 3; startRow++) {
                int playerDisks = 0;
                int emptyCells = 0;
                int targetRow = -1;

                for (int i = 0; i < 4; i++) {
                    int currentRow = startRow + i;
                    
                    if (game.checkDisk(currentRow, col, playerId) == 1) {
                        playerDisks++;
                    } else if (game.checkDisk(currentRow, col, 1) == 0 && 
                               game.checkDisk(currentRow, col, 2) == 0) {
                        emptyCells++;
                        targetRow = currentRow; 
                    }
                }

                if (playerDisks == 3 && emptyCells == 1) {
                    if (getDropRow(game, col) == targetRow) {
                        return col; 
                    }
                }
            }
        }
        return -1; 
    }

    int checkTrioInDiag(Game game, int playerId) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 4; col++) {
                int playerDisks = 0;
                int emptyCells = 0;
                int targetCol = -1;
                int targetRow = -1;
                for (int i = 0; i < 4; i++) {
                    int currentRow = row + i;
                    int currentCol = col + i;
                    if (game.checkDisk(currentRow, currentCol, playerId) == 1) {
                        playerDisks++;
                    }
                    else if (game.checkDisk(currentRow, currentCol, 1) == 0 && game.checkDisk(currentRow, currentCol, 2) == 0) {
                        emptyCells++; targetRow = currentRow; targetCol = currentCol;
                    }
                }
                if (playerDisks == 3 && emptyCells == 1 && getDropRow(game, targetCol) == targetRow) {
                    return targetCol;
                }
            }
        }
        for (int row = 3; row < 6; row++) {
            for (int col = 0; col < 4; col++) {
                int playerDisks = 0;
                int emptyCells = 0;
                int targetCol = -1;
                int targetRow = -1;
                for (int i = 0; i < 4; i++) {
                    int currentRow = row - i;
                    int currentCol = col + i;
                    if (game.checkDisk(currentRow, currentCol, playerId) == 1){
                        playerDisks++;
                    }
                    else if (game.checkDisk(currentRow, currentCol, 1) == 0 && game.checkDisk(currentRow, currentCol, 2) == 0) {
                        emptyCells++; targetRow = currentRow;
                        targetCol = currentCol;
                    }
                }
                if (playerDisks == 3 && emptyCells == 1 && getDropRow(game, targetCol) == targetRow){
                    return targetCol;
                }
            }
        }
        return -1;
    }


    int checkPairInRow(Game game, int playerId) {
        for (int row = 0; row < 6; row++) {
            for (int startCol = 0; startCol < 4; startCol++) {
                int playerDisks = 0;
                int emptyCells = 0;
                int[] emptyCols = new int[2];
                int emptyIndex = 0;

                for (int i = 0; i < 4; i++) {
                    int currentCol = startCol + i;
                    
                    if (game.checkDisk(row, currentCol, playerId) == 1) {
                        playerDisks++;
                    } else if (game.checkDisk(row, currentCol, 1) == 0 && 
                               game.checkDisk(row, currentCol, 2) == 0) {
                        emptyCells++;
                        if (emptyIndex < 2) {
                            emptyCols[emptyIndex] = currentCol;
                            emptyIndex++;
                        }
                    }
                }

                if (playerDisks == 2 && emptyCells == 2) {
                    if (getDropRow(game, emptyCols[0]) == row) {
                        return emptyCols[0];
                    }
                    if (getDropRow(game, emptyCols[1]) == row) {
                        return emptyCols[1];
                    }
                }
            }
        }
        return -1; 
    }

    int checkPairInCol(Game game, int playerId) {
        for (int col = 0; col < 7; col++) {
            for (int startRow = 0; startRow < 3; startRow++) {
                int playerDisks = 0;
                int emptyCells = 0;
                int lowestEmptyRow = -1; 

                for (int i = 0; i < 4; i++) {
                    int currentRow = startRow + i;
                    
                    if (game.checkDisk(currentRow, col, playerId) == 1) {
                        playerDisks++;
                    } else if (game.checkDisk(currentRow, col, 1) == 0 && 
                               game.checkDisk(currentRow, col, 2) == 0) {
                        emptyCells++;
                        lowestEmptyRow = currentRow;
                    }
                }

                if (playerDisks == 2 && emptyCells == 2) {
                    if (getDropRow(game, col) == lowestEmptyRow) {
                        return col; 
                    }
                }
            }
        }
        return -1; 
    }

    int checkPairInDiag(Game game, int playerId) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 4; col++) {
                int playerDisks = 0;
                int emptyCells = 0;
                int[] emptyCols = new int[2];
                int[] emptyRows = new int[2];
                int emptyIndex = 0;
                for (int i = 0; i < 4; i++) {
                    int currentRow = row + i;
                    int currentCol = col + i;
                    if (game.checkDisk(currentRow, currentCol, playerId) == 1){
                        playerDisks++;
                    }
                    else if (game.checkDisk(currentRow, currentCol, 1) == 0 && game.checkDisk(currentRow, currentCol, 2) == 0) {
                        emptyCells++;
                        if (emptyIndex < 2) {
                            emptyCols[emptyIndex] = currentCol;
                            emptyRows[emptyIndex] = currentRow;
                            emptyIndex++;
                        }
                    }
                }
                if (playerDisks == 2 && emptyCells == 2) {
                    if (getDropRow(game, emptyCols[0]) == emptyRows[0]){
                        return emptyCols[0];
                    }
                    if (getDropRow(game, emptyCols[1]) == emptyRows[1]){
                        return emptyCols[1];
                    }
                }
            }
        }
        for (int row = 3; row < 6; row++) {
            for (int col = 0; col < 4; col++) {
                int playerDisks = 0;
                int emptyCells = 0;
                int[] emptyCols = new int[2];
                int[] emptyRows = new int[2];
                int emptyIndex = 0;
                for (int i = 0; i < 4; i++) {
                    int currentRow = row - i;
                    int currentCol = col + i;
                    if (game.checkDisk(currentRow, currentCol, playerId) == 1) {
                        playerDisks++;
                    }
                    else if (game.checkDisk(currentRow, currentCol, 1) == 0 && game.checkDisk(currentRow, currentCol, 2) == 0) {
                        emptyCells++;
                        if (emptyIndex < 2) {
                            emptyCols[emptyIndex] = currentCol;
                            emptyRows[emptyIndex] = currentRow;
                            emptyIndex++;
                        }
                    }
                }
                if (playerDisks == 2 && emptyCells == 2) {
                    if (getDropRow(game, emptyCols[0]) == emptyRows[0]){
                        return emptyCols[0];
                    }
                    if (getDropRow(game, emptyCols[1]) == emptyRows[1]){
                        return emptyCols[1];
                    }
                }
            }
        }
        return -1;
    }

   
    private void sendMove(int col) throws Exception {
        Thread.sleep(500); 
        out.writeObject(col);
        out.flush();
    } 

    private void playLoop() {
        while (true) {
            try {
                Game game = (Game) in.readObject();
                
                if (game.isGameFinished() != 0) {
                    System.out.println("Gra skończona. Bot rozłączony.");
                    try {
                        Thread.sleep(500); 
                        if (socket != null && !socket.isClosed()) {
                            socket.close();
                        }
                    } catch (Exception ex) {}
                    break;
                }

                if (game.getNextPlayer() == botID) {
                    int selectedCol;

                    selectedCol = checkTrioInRow(game, botID);
                    if (selectedCol != -1) { sendMove(selectedCol); continue; }
                    
                    selectedCol = checkTrioInCol(game, botID);
                    if (selectedCol != -1) { sendMove(selectedCol); continue; }

                    selectedCol = checkTrioInDiag(game, botID);
                    if (selectedCol != -1) { sendMove(selectedCol); continue; }

                    selectedCol = checkTrioInRow(game, humanID);
                    if (selectedCol != -1) { sendMove(selectedCol); continue; }

                    selectedCol = checkTrioInCol(game, humanID);
                    if (selectedCol != -1) { sendMove(selectedCol); continue; }

                    selectedCol = checkTrioInDiag(game, humanID);
                    if (selectedCol != -1) { sendMove(selectedCol); continue; }

                    selectedCol = checkPairInCol(game, botID);
                    if (selectedCol != -1) { sendMove(selectedCol); continue; }
                    
                    selectedCol = checkPairInRow(game, botID);
                    if (selectedCol != -1) { sendMove(selectedCol); continue; }

                    selectedCol = checkPairInDiag(game, botID);
                    if (selectedCol != -1) { sendMove(selectedCol); continue; }
                    
                    do {
                        selectedCol = (int) (Math.random() * 7);
                    } while (getDropRow(game, selectedCol) == -1); 
                    
                    sendMove(selectedCol);
                }

            
            } catch (Exception e) {
                System.out.println("Rozłączono z serwerem lub gra się skończyła.");
                try {
                    if (socket != null && !socket.isClosed()) {
                        socket.close();
                    }
                } catch (Exception ex) {}
                break; 
            }
        }
    }
}