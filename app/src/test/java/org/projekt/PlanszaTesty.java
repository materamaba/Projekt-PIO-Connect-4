package org.projekt;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public class PlanszaTesty {
    @Test
     void TestShouldBeEmpty() {
       Plansza board = new Plansza();
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
        Plansza board = new Plansza();
        try{
            board.insert(7,1);
            fail("Function should throw an exception");
        }catch(Exception error){
        }
    }

    @Test
    void TestDiscShouldBePlacedAtTheBottom() throws Exception{
        Plansza board = new Plansza();
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
