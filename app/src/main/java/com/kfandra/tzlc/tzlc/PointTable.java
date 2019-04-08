package com.kfandra.tzlc.tzlc;

public class PointTable {
    private long pointsID;
    private long clubID=-1;
    private int matchesPlayed=0;
    private int win=0;
    private int draw=0;
    private int lost=0;
    private int goalsScored=0;
    private int goalsTaken=0;
    private int goalDifference=0;
    private int points=0;
    private int homeMatchesPlayed=0;
    private int awayMatchesPlayed=0;

    public PointTable() {}

    public long getPointsID() {
        return pointsID;
    }

    public void setPointsID(long pointsID) {
        this.pointsID = pointsID;
    }

    public long getClubID() {
        return clubID;
    }

    public void setClubID(long clubID) {
        this.clubID = clubID;
    }

    public int getMatchesPlayed() {
        return matchesPlayed;
    }

    public void setMatchesPlayed(int matchesPlayed) {
        this.matchesPlayed = matchesPlayed;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getLost() {
        return lost;
    }

    public void setLost(int lost) {
        this.lost = lost;
    }

    public int getGoalsScored() {
        return goalsScored;
    }

    public void setGoalsScored(int goalsScored) {
        this.goalsScored = goalsScored;
    }

    public int getGoalsTaken() {
        return goalsTaken;
    }

    public void setGoalsTaken(int goalsTaken) {
        this.goalsTaken = goalsTaken;
    }

    public int getGoalDifference() {
        return goalDifference;
    }

    public void setGoalDifference(int goalDifference) {
        this.goalDifference = goalDifference;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getHomeMatchesPlayed() {
        return homeMatchesPlayed;
    }

    public void setHomeMatchesPlayed(int homeMatchesPlayed) {
        this.homeMatchesPlayed = homeMatchesPlayed;
    }

    public int getAwayMatchesPlayed() {
        return awayMatchesPlayed;
    }

    public void setAwayMatchesPlayed(int awayMatchesPlayed) {
        this.awayMatchesPlayed = awayMatchesPlayed;
    }
}
