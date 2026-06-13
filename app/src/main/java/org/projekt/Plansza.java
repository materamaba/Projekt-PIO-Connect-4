package org.projekt;
import java.io.Serializable;

public class Plansza implements Serializable {
	public static final int WIDTH = 7;
	public static final int HIGHT = 6;
    private Krażek[][] grid = new Krażek[HIGHT][WIDTH];
    public void insert(int col, int playerID) throws Exception{
        if(col < 0 || col > 6){
            throw new Exception("Invalid column");
        }
        int i = 5;
        int flag = 0;
        while (i > -1) {
            if(grid[i][col] == null){
                grid[i][col] = new Krażek(playerID);
                flag = 1;
                break;
            }
            i--;
        }
        if(flag == 0){
            throw new Exception("Column is full");
        }

    }

    public Krażek getDisc(int row,int col){
        return grid[row][col];
    }
}