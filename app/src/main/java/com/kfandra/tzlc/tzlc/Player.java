package com.kfandra.tzlc.tzlc;

public class Player
{
    private long id;
    private String playerName;
    private long clubId;
    private int currentValue;
    private int totalGoal;
    private int totalMO;
    private int totalCard;
    private int totalLoan;
    private long orgID;
    private int senialwombat;
    private int absent;
    private int position;

    public Player() {
    }

    public Player(String playerName, long clubId, int currentValue) {
        this.playerName = playerName;
        this.clubId = clubId;
        this.currentValue = currentValue;
    }

    public int getAbsent() {
        return absent;
    }

    public void setAbsent(int absent) {
        this.absent = absent;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getSenialwombat() {return senialwombat;}

    public void setSenialwombat(int senialwombat) {this.senialwombat = senialwombat;}

    public long getOrgID() {return orgID;}

    public void setOrgID(long orgID) {this.orgID = orgID;}

    public int getTotalGoal() {
        return totalGoal;
    }

    public void setTotalGoal(int totalGoal) {
        this.totalGoal = totalGoal;
    }

    public int getTotalMO() {
        return totalMO;
    }

    public void setTotalMO(int totalMO) {
        this.totalMO = totalMO;
    }

    public int getTotalCard() {
        return totalCard;
    }

    public void setTotalCard(int totalCard) {
        this.totalCard = totalCard;
    }

    public int getTotalLoan() {
        return totalLoan;
    }

    public void setTotalLoan(int totalLoan) {
        this.totalLoan = totalLoan;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public long getClubId() {
        return clubId;
    }

    public void setClubId(long clubId) {
        this.clubId = clubId;
    }

    public int getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(int currentValue) {
        this.currentValue = currentValue;
    }
}
