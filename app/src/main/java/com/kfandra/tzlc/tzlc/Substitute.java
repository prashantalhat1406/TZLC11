package com.kfandra.tzlc.tzlc;

public class Substitute {
    private long id;
    private long matchID;
    private long clubID;
    private long playerOutID;
    private long playerInID;
    private int matchTime;
    private String reason;

    public Substitute() {
    }

    public Substitute(long matchID, long clubID, long playerOutID, long playerInID, int matchTime, String reason) {
        this.matchID = matchID;
        this.clubID = clubID;
        this.playerOutID = playerOutID;
        this.playerInID = playerInID;
        this.matchTime = matchTime;
        this.reason = reason;
    }

    public Substitute(long matchID, long playerOutID, long playerInID, int matchTime, String reason) {
        this.matchID = matchID;
        this.playerOutID = playerOutID;
        this.playerInID = playerInID;
        this.matchTime = matchTime;
        this.reason = reason;
    }

    public long getClubID() {
        return clubID;
    }

    public void setClubID(long clubID) {
        this.clubID = clubID;
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

    public long getPlayerOutID() {
        return playerOutID;
    }

    public void setPlayerOutID(long playerOutID) {
        this.playerOutID = playerOutID;
    }

    public long getPlayerInID() {
        return playerInID;
    }

    public void setPlayerInID(long playerInID) {
        this.playerInID = playerInID;
    }

    public int getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(int matchTime) {
        this.matchTime = matchTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
