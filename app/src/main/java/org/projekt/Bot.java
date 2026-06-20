package org.projekt;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Bot {
    private static final int ROWS = 6;
    private static final int COLS = 7;
    private static final int WIN_LENGTH = 4;
    
    private static final int EMPTY = 0;
    private static final int HAS_DISK = 1;
    private static final int NOT_FOUND = -1;
    private static final int GAME_NOT_FINISHED = 0;
    
    private static final int DEFAULT_PORT = 1234;
    private static final int BOT_DELAY_MS = 500;

    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    
    private final int botID = 2;
    private final int humanID = 1;

    public static void main(String[] args) {
        Bot bot = new Bot();
        bot.start("localhost", DEFAULT_PORT);
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
        if (col < 0 || col >= COLS) {
            return NOT_FOUND; 
        }
        for (int row = ROWS - 1; row >= 0; row--) {
            if (game.checkDisk(row, col, humanID) == EMPTY && game.checkDisk(row, col, botID) == EMPTY) {
                return row; 
            }
        }
        return NOT_FOUND; 
    }

    int checkTrioInRow(Game game, int playerId) {
        for (int row = 0; row < ROWS; row++) {
            for (int startCol = 0; startCol <= COLS - WIN_LENGTH; startCol++) {
                int playerDisks = 0;
                int emptyCells = 0;
                int targetCol = NOT_FOUND;

                for (int i = 0; i < WIN_LENGTH; i++) {
                    int currentCol = startCol + i;
                    
                    if (game.checkDisk(row, currentCol, playerId) == HAS_DISK) {
                        playerDisks++;
                    } else if (game.checkDisk(row, currentCol, humanID) == EMPTY && 
                               game.checkDisk(row, currentCol, botID) == EMPTY) {
                        emptyCells++;
                        targetCol = currentCol; 
                    }
                }

                if (playerDisks == WIN_LENGTH - 1 && emptyCells == 1) {
                    if (getDropRow(game, targetCol) == row) {
                        return targetCol; 
                    }
                }
            }
        }
        return NOT_FOUND; 
    }

    int checkTrioInCol(Game game, int playerId) {
        for (int col = 0; col < COLS; col++) {
            for (int startRow = 0; startRow <= ROWS - WIN_LENGTH; startRow++) {
                int playerDisks = 0;
                int emptyCells = 0;
                int targetRow = NOT_FOUND;

                for (int i = 0; i < WIN_LENGTH; i++) {
                    int currentRow = startRow + i;
                    
                    if (game.checkDisk(currentRow, col, playerId) == HAS_DISK) {
                        playerDisks++;
                    } else if (game.checkDisk(currentRow, col, humanID) == EMPTY && 
                               game.checkDisk(currentRow, col, botID) == EMPTY) {
                        emptyCells++;
                        targetRow = currentRow; 
                    }
                }

                if (playerDisks == WIN_LENGTH - 1 && emptyCells == 1) {
                    if (getDropRow(game, col) == targetRow) {
                        return col; 
                    }
                }
            }
        }
        return NOT_FOUND; 
    }

    int checkTrioInDiag(Game game, int playerId) {
        // \
        for (int row = 0; row <= ROWS - WIN_LENGTH; row++) {
            for (int col = 0; col <= COLS - WIN_LENGTH; col++) {
                int playerDisks = 0;
                int emptyCells = 0;
                int targetCol = NOT_FOUND;
                int targetRow = NOT_FOUND;
                for (int i = 0; i < WIN_LENGTH; i++) {
                    int currentRow = row + i;
                    int currentCol = col + i;
                    if (game.checkDisk(currentRow, currentCol, playerId) == HAS_DISK) {
                        playerDisks++;
                    }
                    else if (game.checkDisk(currentRow, currentCol, humanID) == EMPTY && game.checkDisk(currentRow, currentCol, botID) == EMPTY) {
                        emptyCells++; targetRow = currentRow; targetCol = currentCol;
                    }
                }
                if (playerDisks == WIN_LENGTH - 1 && emptyCells == 1 && getDropRow(game, targetCol) == targetRow) {
                    return targetCol;
                }
            }
        }
        // /
        for (int row = WIN_LENGTH - 1; row < ROWS; row++) {
            for (int col = 0; col <= COLS - WIN_LENGTH; col++) {
                int playerDisks = 0;
                int emptyCells = 0;
                int targetCol = NOT_FOUND;
                int targetRow = NOT_FOUND;
                for (int i = 0; i < WIN_LENGTH; i++) {
                    int currentRow = row - i;
                    int currentCol = col + i;
                    if (game.checkDisk(currentRow, currentCol, playerId) == HAS_DISK){
                        playerDisks++;
                    }
                    else if (game.checkDisk(currentRow, currentCol, humanID) == EMPTY && game.checkDisk(currentRow, currentCol, botID) == EMPTY) {
                        emptyCells++; targetRow = currentRow;
                        targetCol = currentCol;
                    }
                }
                if (playerDisks == WIN_LENGTH - 1 && emptyCells == 1 && getDropRow(game, targetCol) == targetRow){
                    return targetCol;
                }
            }
        }
        return NOT_FOUND;
    }

    int checkPairInRow(Game game, int playerId) {
        for (int row = 0; row < ROWS; row++) {
            for (int startCol = 0; startCol <= COLS - WIN_LENGTH; startCol++) {
                int playerDisks = 0;
                int emptyCells = 0;
                int[] emptyCols = new int[WIN_LENGTH - 2];
                int emptyIndex = 0;

                for (int i = 0; i < WIN_LENGTH; i++) {
                    int currentCol = startCol + i;
                    
                    if (game.checkDisk(row, currentCol, playerId) == HAS_DISK) {
                        playerDisks++;
                    } else if (game.checkDisk(row, currentCol, humanID) == EMPTY && 
                               game.checkDisk(row, currentCol, botID) == EMPTY) {
                        emptyCells++;
                        if (emptyIndex < WIN_LENGTH - 2) {
                            emptyCols[emptyIndex] = currentCol;
                            emptyIndex++;
                        }
                    }
                }

                if (playerDisks == WIN_LENGTH - 2 && emptyCells == 2) {
                    if (getDropRow(game, emptyCols[0]) == row) {
                        return emptyCols[0];
                    }
                    if (getDropRow(game, emptyCols[1]) == row) {
                        return emptyCols[1];
                    }
                }
            }
        }
        return NOT_FOUND; 
    }

    int checkPairInCol(Game game, int playerId) {
        for (int col = 0; col < COLS; col++) {
            for (int startRow = 0; startRow <= ROWS - WIN_LENGTH; startRow++) {
                int playerDisks = 0;
                int emptyCells = 0;
                int lowestEmptyRow = NOT_FOUND; 

                for (int i = 0; i < WIN_LENGTH; i++) {
                    int currentRow = startRow + i;
                    
                    if (game.checkDisk(currentRow, col, playerId) == HAS_DISK) {
                        playerDisks++;
                    } else if (game.checkDisk(currentRow, col, humanID) == EMPTY && 
                               game.checkDisk(currentRow, col, botID) == EMPTY) {
                        emptyCells++;
                        lowestEmptyRow = currentRow;
                    }
                }

                if (playerDisks == WIN_LENGTH - 2 && emptyCells == 2) {
                    if (getDropRow(game, col) == lowestEmptyRow) {
                        return col; 
                    }
                }
            }
        }
        return NOT_FOUND; 
    }

    int checkPairInDiag(Game game, int playerId) {
        for (int row = 0; row <= ROWS - WIN_LENGTH; row++) {
            for (int col = 0; col <= COLS - WIN_LENGTH; col++) {
                int playerDisks = 0;
                int emptyCells = 0;
                int[] emptyCols = new int[WIN_LENGTH - 2];
                int[] emptyRows = new int[WIN_LENGTH - 2];
                int emptyIndex = 0;
                for (int i = 0; i < WIN_LENGTH; i++) {
                    int currentRow = row + i;
                    int currentCol = col + i;
                    if (game.checkDisk(currentRow, currentCol, playerId) == HAS_DISK){
                        playerDisks++;
                    }
                    else if (game.checkDisk(currentRow, currentCol, humanID) == EMPTY && game.checkDisk(currentRow, currentCol, botID) == EMPTY) {
                        emptyCells++;
                        if (emptyIndex < WIN_LENGTH - 2) {
                            emptyCols[emptyIndex] = currentCol;
                            emptyRows[emptyIndex] = currentRow;
                            emptyIndex++;
                        }
                    }
                }
                if (playerDisks == WIN_LENGTH - 2 && emptyCells == 2) {
                    if (getDropRow(game, emptyCols[0]) == emptyRows[0]){
                        return emptyCols[0];
                    }
                    if (getDropRow(game, emptyCols[1]) == emptyRows[1]){
                        return emptyCols[1];
                    }
                }
            }
        }
        for (int row = WIN_LENGTH - 1; row < ROWS; row++) {
            for (int col = 0; col <= COLS - WIN_LENGTH; col++) {
                int playerDisks = 0;
                int emptyCells = 0;
                int[] emptyCols = new int[WIN_LENGTH - 2];
                int[] emptyRows = new int[WIN_LENGTH - 2];
                int emptyIndex = 0;
                for (int i = 0; i < WIN_LENGTH; i++) {
                    int currentRow = row - i;
                    int currentCol = col + i;
                    if (game.checkDisk(currentRow, currentCol, playerId) == HAS_DISK) {
                        playerDisks++;
                    }
                    else if (game.checkDisk(currentRow, currentCol, humanID) == EMPTY && game.checkDisk(currentRow, currentCol, botID) == EMPTY) {
                        emptyCells++;
                        if (emptyIndex < WIN_LENGTH - 2) {
                            emptyCols[emptyIndex] = currentCol;
                            emptyRows[emptyIndex] = currentRow;
                            emptyIndex++;
                        }
                    }
                }
                if (playerDisks == WIN_LENGTH - 2 && emptyCells == 2) {
                    if (getDropRow(game, emptyCols[0]) == emptyRows[0]){
                        return emptyCols[0];
                    }
                    if (getDropRow(game, emptyCols[1]) == emptyRows[1]){
                        return emptyCols[1];
                    }
                }
            }
        }
        return NOT_FOUND;
    }

    private void sendMove(int col) throws Exception {
        Thread.sleep(BOT_DELAY_MS); 
        out.writeObject(col);
        out.flush();
    } 

    private void playLoop() {
        while (true) {
            try {
                Game game = (Game) in.readObject();
                
                if (game.isGameFinished() != GAME_NOT_FINISHED) {
                    System.out.println("Gra skończona. Bot rozłączony.");
                    try {
                        Thread.sleep(BOT_DELAY_MS); 
                        if (socket != null && !socket.isClosed()) {
                            socket.close();
                        }
                    } catch (Exception ex) {}
                    break;
                }

                if (game.getNextPlayer() == botID) {
                    int selectedCol;

                    selectedCol = checkTrioInRow(game, botID);
                    if (selectedCol != NOT_FOUND) { sendMove(selectedCol); continue; }
                    
                    selectedCol = checkTrioInCol(game, botID);
                    if (selectedCol != NOT_FOUND) { sendMove(selectedCol); continue; }

                    selectedCol = checkTrioInDiag(game, botID);
                    if (selectedCol != NOT_FOUND) { sendMove(selectedCol); continue; }

                    selectedCol = checkTrioInRow(game, humanID);
                    if (selectedCol != NOT_FOUND) { sendMove(selectedCol); continue; }

                    selectedCol = checkTrioInCol(game, humanID);
                    if (selectedCol != NOT_FOUND) { sendMove(selectedCol); continue; }

                    selectedCol = checkTrioInDiag(game, humanID);
                    if (selectedCol != NOT_FOUND) { sendMove(selectedCol); continue; }

                    selectedCol = checkPairInCol(game, botID);
                    if (selectedCol != NOT_FOUND) { sendMove(selectedCol); continue; }
                    
                    selectedCol = checkPairInRow(game, botID);
                    if (selectedCol != NOT_FOUND) { sendMove(selectedCol); continue; }

                    selectedCol = checkPairInDiag(game, botID);
                    if (selectedCol != NOT_FOUND) { sendMove(selectedCol); continue; }
                    
                    do {
                        selectedCol = (int) (Math.random() * COLS);
                    } while (getDropRow(game, selectedCol) == NOT_FOUND); 
                    
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