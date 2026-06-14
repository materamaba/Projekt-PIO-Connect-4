package org.projekt;
import java.io.Serializable;

public class Board implements Serializable {
	public static final int WIDTH = 7;
	public static final int HIGHT = 6;
    private Disk[][] grid = new Disk[HIGHT][WIDTH];
    public void insert(int col, int playerID) throws Exception{
        if(col < 0 || col > 6){
            throw new Exception("Invalid column");
        }
        int i = 5;
        int flag = 0;
        while (i > -1) {
            if(grid[i][col] == null){
                grid[i][col] = new Disk(playerID);
                flag = 1;
                break;
            }
            i--;
        }
        if(flag == 0){
            throw new Exception("Kolumna jest pełna");
        }

    }

    public Disk getDisc(int row,int col){
        return grid[row][col];
    }
}