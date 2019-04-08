package com.kfandra.tzlc.tzlc;

public class Card {
    private long id;
    private long matchID;
    private long playerID;
    private int type;
    private int time;
    private String reason;
    private long clubID;

    public Card() {
    }

    public Card(long matchID, long playerID, int type, int time, String reason) {
        this.matchID = matchID;
        this.playerID = playerID;
        this.type = type;
        this.time = time;
        this.reason = reason;
    }

    public Card(long matchID, long playerID, int time, String reason) {
        this.matchID = matchID;
        this.playerID = playerID;
        this.time = time;
        this.reason = reason;
    }

    public long getClubID() {return clubID;}

    public void setClubID(long clubID) {this.clubID = clubID;}

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

    public long getPlayerID() {
        return playerID;
    }

    public void setPlayerID(long playerID) {
        this.playerID = playerID;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
