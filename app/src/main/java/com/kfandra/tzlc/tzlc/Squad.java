package com.kfandra.tzlc.tzlc;

import java.util.List;

public class Squad {
    private long id;
    private long matchID;
    private long clubID;
    private long absentPlayer;

    public Squad() {
    }

    public Squad(long matchID, long clubID, long absentPlayer) {
        this.matchID = matchID;
        this.clubID = clubID;
        this.absentPlayer = absentPlayer;
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

    public long getAbsentPlayer() {
        return absentPlayer;
    }

    public void setAbsentPlayer(long absentPlayer) {
        this.absentPlayer = absentPlayer;
    }
}
