package org.projekt;

import java.io.Serializable;

public class Disk implements Serializable {
    private int PlayerId;

    public Disk(int PlayerId) throws Exception {
        if (PlayerId != 1 && PlayerId != 2) {
            throw new Exception("Błędne ID");
        }
        this.PlayerId = PlayerId;
    }

    public int GetPlayerId() {
        return PlayerId;
    }
}