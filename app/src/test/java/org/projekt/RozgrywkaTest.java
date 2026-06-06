package org.projekt;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RozgrywkaTest {
    @Test
    void TestMoveAfterWin(){
        Rozgrywka game = new Rozgrywka();
        try{
            game.makeMove(0);
            game.makeMove(1);
            game.makeMove(0);
            game.makeMove(1);
            game.makeMove(0);
            game.makeMove(1);
            game.makeMove(0);
            game.makeMove(1);
            fail("Function should throw an exception");
        }catch(Exception error){
        }
    }

    @Test
    void TestHorizontalWin() throws Exception{
        Rozgrywka game = new Rozgrywka();
        int result = 0;
        result = game.makeMove(0);
        result = game.makeMove(0);
        result = game.makeMove(1);
        result = game.makeMove(1);
        result = game.makeMove(2);
        result = game.makeMove(2);
        result = game.makeMove(3);
        if(result != 1){
            fail("Player 1 should win");
        }
    }

    @Test
    void TestVerdicalWin() throws Exception{
        Rozgrywka game = new Rozgrywka();
        int result = 0;
        result = game.makeMove(0);
        result = game.makeMove(1);
        result = game.makeMove(0);
        result = game.makeMove(1);
        result = game.makeMove(0);
        result = game.makeMove(1);
        result = game.makeMove(0);
        if(result != 1){
            fail("Player 1 should win");
        }
    }

    @Test
    void TestDiagonalAscWin() throws Exception{
        Rozgrywka game = new Rozgrywka();
        int result = 0;
        result = game.makeMove(0);
        result = game.makeMove(1);
        result = game.makeMove(1);
        result = game.makeMove(2);
        result = game.makeMove(2);
        result = game.makeMove(3);
        result = game.makeMove(2);
        result = game.makeMove(3);
        result = game.makeMove(3);
        result = game.makeMove(6);
        result = game.makeMove(3);
        if(result != 1){
            fail("Player 1 should win");
        }
    }

    @Test
    void TestDiagonalDescWin() throws Exception{
        Rozgrywka game = new Rozgrywka();
        int result = 0;
        result = game.makeMove(3);
        result = game.makeMove(2);
        result = game.makeMove(2);
        result = game.makeMove(1);
        result = game.makeMove(1);
        result = game.makeMove(0);
        result = game.makeMove(1);
        result = game.makeMove(0);
        result = game.makeMove(4);
        result = game.makeMove(0);
        result = game.makeMove(0);
        if(result != 1){
            fail("Player 1 should win");
        }
    }
}