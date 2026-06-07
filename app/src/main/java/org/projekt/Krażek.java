package org.projekt;

import java.io.Serializable;

public class Krażek implements Serializable {
        private int PlayerId;
    public Krażek(int PlayerId) throws Exception{
        if(PlayerId != 1 && PlayerId != 2){
             throw new Exception("Incorrect PlayerID");
        }
        this.PlayerId = PlayerId;
    }

    public int GetPlayerId(){
        return PlayerId;
    }
}
