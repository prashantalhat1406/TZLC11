package com.kfandra.tzlc.tzlc;

public class MatchOffcial {
    private long id;
    private long playerId;
    private long matchId;
    private int job;
    private int onTime;
    private long clubID;
    private int moTime;

    public MatchOffcial() {}

    public MatchOffcial(long playerId, long matchId, int job, int onTime) {
        this.playerId = playerId;
        this.matchId = matchId;
        this.job = job;
        this.onTime = onTime;
    }

    public int getMoTime() {
        return moTime;
    }

    public void setMoTime(int moTime) {
        this.moTime = moTime;
    }

    public long getClubID() {return clubID;}

    public void setClubID(long clubID) {this.clubID = clubID;}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    public int getJob() {
        return job;
    }

    public void setJob(int job) {
        this.job = job;
    }

    public int getOnTime() {
        return onTime;
    }

    public void setOnTime(int onTime) {
        this.onTime = onTime;
    }
}
