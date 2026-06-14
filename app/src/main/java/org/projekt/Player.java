package org.projekt;

public class Player {
    private Team team;

    public Player(Team team){
        this.team = team;
    }
    public Player(){}

    public void playerMakeMove(Game game, int col) throws Exception {
        if(!canPlayerMakeMove(game)){
            return;
        }

        game.makeMove(col);
    }

    public boolean canPlayerMakeMove(Game game) {
        return !((game.getNextPlayer() == 1 && team == Team.YELLOW) || (game.getNextPlayer() == 2 && team == Team.RED));
    }

    public void setTeam(Team team){
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }
}
