package org.projekt;
import java.io.Serializable;
public class Game implements Serializable {
    public static final int FIRST_PLAYER_WON = 1;
    public static final int SECOND_PLAYER_WON = 2;
    public static final int DRAW_RETURN_VALUE = 3;
    public static final int GAME_ONGOING_RETURN_VALUE = 0;

    public static final int ROWS = 6;
    public static final int COLS = 7;
    public static final int WINNING_LENGTH = 4;
    public static final int MAX_MOVES = ROWS * COLS; // 42

    public static final int PLAYER_1 = 1;
    public static final int PLAYER_2 = 2;

    public static final int STATUS_ONGOING = 0;
    public static final int STATUS_WON = 1;
    public static final int STATUS_DRAW = 2;
    public static final int PATTERN_FOUND = 1;
    public static final int PATTERN_NOT_FOUND = 0;

    private Board board = new Board();
    private int player = PLAYER_1;
    private int gameFinished = STATUS_ONGOING;
    private int moves = 0;

    public int makeMove(int col) throws Exception{
        if(gameFinished == STATUS_WON){
            throw new Exception("Gra skończona");
        }
        board.insert(col, player);
        moves++;

        if(checkHorizontal(player) == PATTERN_FOUND){
            gameFinished = STATUS_WON;
        }
        if(checkVerdical(player) == PATTERN_FOUND){
            gameFinished = STATUS_WON;
        }
        if(checkDiagonalDesc(player) == PATTERN_FOUND){
            gameFinished = STATUS_WON;
        }
        if(checkDiagonalAsc(player) == PATTERN_FOUND){
            gameFinished = STATUS_WON;
        }

        if (moves >= MAX_MOVES && gameFinished != STATUS_WON) {
            gameFinished = STATUS_DRAW;
        }

        if(gameFinished == STATUS_WON){
            if(player == PLAYER_1){
                return FIRST_PLAYER_WON;
            }
            return SECOND_PLAYER_WON;
        }
        if(gameFinished == STATUS_DRAW){
            return DRAW_RETURN_VALUE;
        }
        if(player == PLAYER_1){
            player = PLAYER_2;
        } else {
            player = PLAYER_1;
        }
        return GAME_ONGOING_RETURN_VALUE;
    }
    public int checkDisk(int row, int col, int player){
        Disk disk = board.getDisc(row, col);
        if(disk == null){
            return PATTERN_NOT_FOUND;
        }
        if(disk.GetPlayerId() != player){
            return PATTERN_NOT_FOUND;
        }
        return PATTERN_FOUND;
    }
    public int checkHorizontal(int player){
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j <= COLS - WINNING_LENGTH; j++){
                int counter = 0;
                for(int k = 0; k < WINNING_LENGTH; k++){
                    counter += checkDisk(i, j + k, player);
                }
                if(counter == WINNING_LENGTH){
                    return PATTERN_FOUND;
                }
            }
        }
        return PATTERN_NOT_FOUND;
    }
    public int checkVerdical(int player){
        for(int i = 0; i <= ROWS - WINNING_LENGTH; i++){
            for(int j = 0; j < COLS; j++){
                int counter = 0;
                for(int k = 0; k < WINNING_LENGTH; k++){
                    counter += checkDisk(i + k, j, player);
                }
                if(counter == WINNING_LENGTH){
                    return PATTERN_FOUND;
                }
            }
        }
        return PATTERN_NOT_FOUND;
    }
    public int checkDiagonalDesc(int player){
        for(int i = 0; i <= ROWS - WINNING_LENGTH; i++){
            for(int j = 0; j <= COLS - WINNING_LENGTH; j++){
                int counter = 0;
                for(int k = 0; k < WINNING_LENGTH; k++){
                    counter += checkDisk(i + k, j + k, player);
                }
                if(counter == WINNING_LENGTH){
                    return PATTERN_FOUND;
                }
            }
        }
        return PATTERN_NOT_FOUND;
    }
    public int checkDiagonalAsc(int player){
        for(int i = WINNING_LENGTH - 1; i < ROWS; i++){
            for(int j = 0; j <= COLS - WINNING_LENGTH; j++){
                int counter = 0;
                for(int k = 0; k < WINNING_LENGTH; k++){
                    counter += checkDisk(i - k, j + k, player);
                }
                if(counter == WINNING_LENGTH){
                    return PATTERN_FOUND;
                }
            }
        }
        return PATTERN_NOT_FOUND;
    }

    public int getNextPlayer(){
        return player;
    }

    public int isGameFinished() {
        return gameFinished;
    }
}