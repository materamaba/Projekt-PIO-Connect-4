package org.projekt;

import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

public class BoardTest {
    @Test
     void TestShouldBeEmpty() {
       Board board = new Board();
       int i = 0;
       while (i<6) {
            int j = 0;
            while (j < 7) {
                if(board.getDisc(i,j) != null){
                    fail("Board should be empty");
                }
                j++;
            }
            i++;
       }
    }

    @Test
    void TestShouldBeInsertedInCorrectColumn() {
        Board board = new Board();
        try{
            board.insert(7,1);
            fail("Function should throw an exception");
        }catch(Exception error){
        }
    }

    @Test
    void TestDiscShouldBePlacedAtTheBottom() throws Exception{
        Board board = new Board();
        board.insert(1,1);
        int i = 0;
        while (i<5) {
            if(board.getDisc(i,1) != null){
                fail("Rows 0-4 should be null");
            }
            i++;
        }
        if(board.getDisc(i,1) == null){
            fail("Row 5 should be Disc");
        }
    }
}
