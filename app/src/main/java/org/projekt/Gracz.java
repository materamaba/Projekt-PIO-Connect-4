package org.projekt;

public class Gracz {
    private Zespol team;

    public Gracz(Zespol team){
        this.team = team;
    }
    public Gracz(){}

    public void playerMakeMove(Rozgrywka rozgrywka, int col) throws Exception {
        if((rozgrywka.getNextPlayer() == 1 && team == Zespol.YELLOW) || (rozgrywka.getNextPlayer() == 2 && team == Zespol.RED)){
            return;
        }

        rozgrywka.makeMove(col);
    }

    public void setTeam(Zespol team){
        this.team = team;
    }

    public Zespol getTeam() {
        return team;
    }
}
