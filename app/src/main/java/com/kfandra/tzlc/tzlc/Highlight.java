package com.kfandra.tzlc.tzlc;

public class Highlight {
    private long id;
    private long matchID;
    private long clubID;
    private int vcmTime;
    private int srTime;
    private String highlight;
    private String highlight2;

    public Highlight() {}

    public Highlight(long matchID, int vcmTime, int srTime, String highlight) {
        this.matchID = matchID;
        this.vcmTime = vcmTime;
        this.srTime = srTime;
        this.highlight = highlight;
    }

    public Highlight(long matchID, long clubID, int vcmTime, int srTime, String highlight, String highlight2) {
        this.matchID = matchID;
        this.clubID = clubID;
        this.vcmTime = vcmTime;
        this.srTime = srTime;
        this.highlight = highlight;
        this.highlight2 = highlight2;
    }

    public long getClubID() {
        return clubID;
    }

    public void setClubID(long clubID) {
        this.clubID = clubID;
    }

    public String getHighlight2() {
        return highlight2;
    }

    public void setHighlight2(String highlight2) {
        this.highlight2 = highlight2;
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

    public int getVcmTime() {
        return vcmTime;
    }

    public void setVcmTime(int vcmTime) {
        this.vcmTime = vcmTime;
    }

    public int getSrTime() {
        return srTime;
    }

    public void setSrTime(int srTime) {
        this.srTime = srTime;
    }

    public String getHighlight() {
        return highlight;
    }

    public void setHighlight(String highlight) {
        this.highlight = highlight;
    }
}
