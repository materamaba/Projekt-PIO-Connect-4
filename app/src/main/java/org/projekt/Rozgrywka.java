package org.projekt;

import java.io.Serializable;

public class Rozgrywka implements Serializable {
    private Plansza board = new Plansza();
    private int player = 1;
    private int gameFinished = 0;

    public int makeMove(int col) throws Exception{
        if(gameFinished == 1){
            throw new Exception("Game is finished");
        }
        board.insert(col, player);
        if(checkHorizontal(player) == 1){
            gameFinished = 1;
        }
        if(checkVerdical(player) == 1){
            gameFinished = 1;
        }
       if(checkDiagonalDesc(player) == 1){
            gameFinished = 1;
        }
        if(checkDiagonalAsc(player) == 1){
            gameFinished = 1;
        }
        if(gameFinished == 1){
            if(player == 1){
                return 1;
            }
            return 2;
        }
        if(player == 1){
            player = 2;
        }else{
            player = 1;
        }
        return 0;
    }

    public int checkDisk(int row,int col,int player){
        Krażek disk = board.getDisc(row, col);
        if(disk == null){
            return 0;
        }
        if(disk.GetPlayerId() != player){
            return 0;
        }
        return 1;
    }

    public int checkHorizontal(int player){
        for(int i = 0; i < 6;i++){
            for(int j = 0; j < 4;j++){
                int counter = 0;
                for(int k = 0; k < 4;k++){
                    counter += checkDisk(i, j + k, player);
                }
                if(counter == 4){
                    return 1;
                }
            }
        }
        return 0;
    }

    public int checkVerdical(int player){
        for(int i = 0; i < 3;i++){
            for(int j = 0; j < 7;j++){
                int counter = 0;
                for(int k = 0; k < 4;k++){
                    counter += checkDisk(i + k, j, player);
                }
                if(counter == 4){
                    return 1;
                }
            }
        }
        return 0;
    }

    public int checkDiagonalDesc(int player){
          for(int i = 0; i < 3;i++){
            for(int j = 0; j < 4;j++){
                int counter = 0;
                for(int k = 0; k < 4;k++){
                    counter += checkDisk(i + k, j + k, player);
                }
                if(counter == 4){
                    return 1;
                }
            }
        }
        return 0;
    }


    public int checkDiagonalAsc(int player){
          for(int i = 3; i < 6;i++){
            for(int j = 0; j < 4;j++){
                int counter = 0;
                for(int k = 0; k < 4;k++){
                    counter += checkDisk(i - k, j + k, player);
                }
                if(counter == 4){
                    return 1;
                }
            }
        }
        return 0;
    }
    public int getNextPlayer(){
        return player;
    }
}