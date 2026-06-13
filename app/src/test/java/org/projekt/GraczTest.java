package org.projekt;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.fail;

public class GraczTest {
    @Test
    void createNewPlayerTest() {
        try{
            Gracz player1 = new Gracz(Zespol.RED);
            Gracz player2 = new Gracz(Zespol.YELLOW);

            if(player1.getTeam() != Zespol.RED || player2.getTeam() != Zespol.YELLOW){
                fail("Player1 should have RED team, and player2 YELLOW team");
            }
        }catch(Exception e){
            fail("Constructor should create a correct object");
        }
    }

    @Test
    void samePlayerCantMakeTwoMovesTest() {
        try{
            Gracz player1 = new Gracz(Zespol.RED);
            Gracz player2 = new Gracz(Zespol.YELLOW);
            Rozgrywka game = new Rozgrywka();

            player1.playerMakeMove(game, 0);
            player1.playerMakeMove(game, 1); // this shouldnt do anything
            player2.playerMakeMove(game, 1);

            if(game.checkDisk(5, 0, 1) != 1 || game.checkDisk(5, 1, 2) != 1){
                fail("Same player cant make two moves");
            }
        }catch(Exception e){
            fail("There should be no exceptions here");
        }
    }

    @Test
    void yellowTeamCantStartGameTest() {
        try{
            Gracz player2 = new Gracz(Zespol.YELLOW);
            Rozgrywka game = new Rozgrywka();

            player2.playerMakeMove(game, 0); //this shouldnt do anything

            if(game.checkDisk(5, 0, 2) != 0){
                fail("Only red team can start game");
            }
        }catch(Exception e){
            fail("There should be no exceptions here");
        }
    }

    @Test
    void changeTeamTest() {
        try{
            Gracz player = new Gracz(Zespol.YELLOW);
            player.setTeam(Zespol.RED);

            if(player.getTeam() != Zespol.RED){
                fail("Player's team should be red");
            }
        }catch(Exception e){
            fail("There should be no exceptions here");
        }
    }
}
