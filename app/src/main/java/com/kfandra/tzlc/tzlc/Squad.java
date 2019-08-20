package com.kfandra.tzlc.tzlc;

public class Squad {
    private long id;
    private long matchID;
    private long clubID;
    private long playerID;
    private int absent;


    public Squad() {
    }

    public Squad(long matchID, long clubID, long playerID) {
        this.matchID = matchID;
        this.clubID = clubID;
        this.playerID = playerID;
    }

    public Squad(long matchID, long clubID, long playerID, int absent) {
        this.matchID = matchID;
        this.clubID = clubID;
        this.playerID = playerID;
        this.absent = absent;
    }

    public int getAbsent() {
        return absent;
    }

    public void setAbsent(int absent) {
        this.absent = absent;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMatchID() {
        return matchID;
    }

    public void setMatchID(long matchID) {
        this.matchID = matchID;
    }

    public long getClubID() {
        return clubID;
    }

    public void setClubID(long clubID) {
        this.clubID = clubID;
    }

    public long getPlayerID() {
        return playerID;
    }

    public void setPlayerID(long playerID) {
        this.playerID = playerID;
    }
}
