package org.projekt;

import org.junit.jupiter.api.Test;

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
}
