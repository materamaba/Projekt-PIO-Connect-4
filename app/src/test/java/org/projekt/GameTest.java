package org.projekt;

import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

public class GameTest {
    @Test
    void TestMoveAfterWin(){
        Game game = new Game();
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
        Game game = new Game();
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
        Game game = new Game();
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
        Game game = new Game();
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
        Game game = new Game();
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