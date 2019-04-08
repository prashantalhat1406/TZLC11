package com.kfandra.tzlc.tzlc;

public class Match {
    private long id;
    private long date_number;
    private long homeClubID;
    private long awayClubID;
    private int type;
    private int subtype;
    private int result;

    public Match() {}

    public Match(long date_number, long homeClubID, long awayClubID, int type,int subtype,int result ) {
        this.date_number = date_number;
        this.homeClubID = homeClubID;
        this.awayClubID = awayClubID;
        this.type = type;
        this.subtype = subtype;
        this.result = result;
    }

    public int getSubtype() {return subtype;}

    public void setSubtype(int subtype) {this.subtype = subtype;}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDate_number() {
        return date_number;
    }

    public void setDate_number(long date_number) {
        this.date_number = date_number;
    }

    public long getHomeClubID() {
        return homeClubID;
    }

    public void setHomeClubID(long homeClubID) {
        this.homeClubID = homeClubID;
    }

    public long getAwayClubID() {
        return awayClubID;
    }

    public void setAwayClubID(long awayClubID) {
        this.awayClubID = awayClubID;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
