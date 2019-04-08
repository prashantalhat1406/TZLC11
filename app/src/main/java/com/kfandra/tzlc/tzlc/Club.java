package com.kfandra.tzlc.tzlc;

import java.util.List;

public class Club {
    private long id;
    private String clubName;
    private String clubShortName;
    private String managerName;
    private String manager2Name;
    private String homeGround;
    private List<Long> playerIDs;
    private List<Long> matchIDs;
    private PointTable pointTable;
    private int clubColor;
    private int organization;
    private int senialWombat;


    public Club() {}

    public Club(String clubName, String clubShortName, String managerName, String homeGround) {
        this.clubName = clubName;
        this.clubShortName = clubShortName;
        this.managerName = managerName;
        this.homeGround = homeGround;
    }

    public String getManager2Name() {
        return manager2Name;
    }

    public void setManager2Name(String manager2Name) {
        this.manager2Name = manager2Name;
    }

    public int getSenialWombat() {return senialWombat;}

    public void setSenialWombat(int senialWombat) {this.senialWombat = senialWombat;}

    public int getOrganization() {return organization;}

    public void setOrganization(int organization) {this.organization = organization;}

    public int getClubColor() {
        return clubColor;
    }

    public void setClubColor(int clubColor) {
        this.clubColor = clubColor;
    }

    public String getClubShortName() {return clubShortName;}

    public void setClubShortName(String clubShortName) {this.clubShortName = clubShortName;}

    public List<Long> getPlayerIDs() {return playerIDs;}

    public void setPlayerIDs(List<Long> playerIDs) {this.playerIDs = playerIDs;}

    public List<Long> getMatchIDs() {return matchIDs;}

    public void setMatchIDs(List<Long> matchIDs) {this.matchIDs = matchIDs;}

    public PointTable getPointTable() {return pointTable;}

    public void setPointTable(PointTable pointTable) {this.pointTable = pointTable;}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getHomeGround() {
        return homeGround;
    }

    public void setHomeGround(String homeGround) {
        this.homeGround = homeGround;
    }
}
