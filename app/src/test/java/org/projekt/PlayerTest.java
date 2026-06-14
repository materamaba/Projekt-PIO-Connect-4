package org.projekt;

import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

public class PlayerTest {
    @Test
    void createNewPlayerTest() {
        try{
            Player player1 = new Player(Team.RED);
            Player player2 = new Player(Team.YELLOW);

            if(player1.getTeam() != Team.RED || player2.getTeam() != Team.YELLOW){
                fail("Player1 should have RED team, and player2 YELLOW team");
            }
        }catch(Exception e){
            fail("Constructor should create a correct object");
        }
    }

    @Test
    void samePlayerCantMakeTwoMovesTest() {
        try{
            Player player1 = new Player(Team.RED);
            Player player2 = new Player(Team.YELLOW);
            Game game = new Game();

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
            Player player2 = new Player(Team.YELLOW);
            Game game = new Game();

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
            Player player = new Player(Team.YELLOW);
            player.setTeam(Team.RED);

            if(player.getTeam() != Team.RED){
                fail("Player's team should be red");
            }
        }catch(Exception e){
            fail("There should be no exceptions here");
        }
    }
}
