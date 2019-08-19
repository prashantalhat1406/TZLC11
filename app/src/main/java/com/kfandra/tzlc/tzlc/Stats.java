package com.kfandra.tzlc.tzlc;

public class Stats {
    private long id;
    private long matchID;
    private int matchTime=0;
    private int home_Score=-1;
    private int home_TI=0;
    private int home_DFK=0;
    private int home_SOnT=0;
    private int home_SOffT=0;
    private int home_GK=0;
    private int home_OFF=0;
    private int home_LC=0;
    private int home_COR=0;
    private int home_TCK=0;
    private int home_POP=0;
    private int home_TIME=0;
    private int home_H1=0;
    private int home_H2=0;
    private int home_H3=0;
    private int home_H4=0;
    private int home_Passes=0;
    private int away_Score=-1;
    private int away_TI=0;
    private int away_DFK=0;
    private int away_SOnT=0;
    private int away_SOffT=0;
    private int away_GK=0;
    private int away_OFF=0;
    private int away_LC=0;
    private int away_COR=0;
    private int away_TCK=0;
    private int away_POP=0;
    private int away_TIME=0;
    private int away_A1=0;
    private int away_A2=0;
    private int away_A3=0;
    private int away_A4=0;
    private int away_Passes=0;

    public Stats() {
    }

    public Stats(long matchID, int matchTime, int home_Score, int home_TI, int home_DFK, int home_SOnT, int home_SOffT, int home_GK, int home_OFF, int home_LC, int home_COR, int home_TCK, int home_POP, int home_TIME, int home_H1, int home_H2, int home_H3, int home_H4, int home_Passes, int away_Score, int away_TI, int away_DFK, int away_SOnT, int away_SOffT, int away_GK, int away_OFF, int away_LC, int away_COR, int away_TCK, int away_POP, int away_TIME, int away_A1, int away_A2, int away_A3, int away_A4, int away_Passes) {
        this.matchID = matchID;
        this.matchTime = matchTime;
        this.home_Score = home_Score;
        this.home_TI = home_TI;
        this.home_DFK = home_DFK;
        this.home_SOnT = home_SOnT;
        this.home_SOffT = home_SOffT;
        this.home_GK = home_GK;
        this.home_OFF = home_OFF;
        this.home_LC = home_LC;
        this.home_COR = home_COR;
        this.home_TCK = home_TCK;
        this.home_POP = home_POP;
        this.home_TIME = home_TIME;
        this.home_H1 = home_H1;
        this.home_H2 = home_H2;
        this.home_H3 = home_H3;
        this.home_H4 = home_H4;
        this.home_Passes = home_Passes;
        this.away_Score = away_Score;
        this.away_TI = away_TI;
        this.away_DFK = away_DFK;
        this.away_SOnT = away_SOnT;
        this.away_SOffT = away_SOffT;
        this.away_GK = away_GK;
        this.away_OFF = away_OFF;
        this.away_LC = away_LC;
        this.away_COR = away_COR;
        this.away_TCK = away_TCK;
        this.away_POP = away_POP;
        this.away_TIME = away_TIME;
        this.away_A1 = away_A1;
        this.away_A2 = away_A2;
        this.away_A3 = away_A3;
        this.away_A4 = away_A4;
        this.away_Passes = away_Passes;
    }

    public Stats(long matchID, int matchTime, int home_Score, int home_TI, int home_DFK, int home_SOnT, int home_SOffT, int home_GK, int home_OFF, int home_LC, int home_COR, int home_TCK, int home_POP, int home_TIME, int away_Score, int away_TI, int away_DFK, int away_SOnT, int away_SOffT, int away_GK, int away_OFF, int away_LC, int away_COR, int away_TCK, int away_POP, int away_TIME) {
        this.matchID = matchID;
        this.matchTime = matchTime;
        this.home_Score = home_Score;
        this.home_TI = home_TI;
        this.home_DFK = home_DFK;
        this.home_SOnT = home_SOnT;
        this.home_SOffT = home_SOffT;
        this.home_GK = home_GK;
        this.home_OFF = home_OFF;
        this.home_LC = home_LC;
        this.home_COR = home_COR;
        this.home_TCK = home_TCK;
        this.home_POP = home_POP;
        this.home_TIME = home_TIME;
        this.away_Score = away_Score;
        this.away_TI = away_TI;
        this.away_DFK = away_DFK;
        this.away_SOnT = away_SOnT;
        this.away_SOffT = away_SOffT;
        this.away_GK = away_GK;
        this.away_OFF = away_OFF;
        this.away_LC = away_LC;
        this.away_COR = away_COR;
        this.away_TCK = away_TCK;
        this.away_POP = away_POP;
        this.away_TIME = away_TIME;
    }

    public int getHome_Passes() {
        return home_Passes;
    }

    public void setHome_Passes(int home_Passes) {
        this.home_Passes = home_Passes;
    }

    public int getAway_Passes() {
        return away_Passes;
    }

    public void setAway_Passes(int away_Passes) {
        this.away_Passes = away_Passes;
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

    public int getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(int matchTime) {
        this.matchTime = matchTime;
    }

    public int getHome_Score() {
        return home_Score;
    }

    public void setHome_Score(int home_Score) {
        this.home_Score = home_Score;
    }

    public int getHome_TI() {
        return home_TI;
    }

    public void setHome_TI(int home_TI) {
        this.home_TI = home_TI;
    }

    public int getHome_DFK() {
        return home_DFK;
    }

    public void setHome_DFK(int home_DFK) {
        this.home_DFK = home_DFK;
    }

    public int getHome_SOnT() {
        return home_SOnT;
    }

    public void setHome_SOnT(int home_SOnT) {
        this.home_SOnT = home_SOnT;
    }

    public int getHome_SOffT() {
        return home_SOffT;
    }

    public void setHome_SOffT(int home_SOffT) {
        this.home_SOffT = home_SOffT;
    }

    public int getHome_GK() {
        return home_GK;
    }

    public void setHome_GK(int home_GK) {
        this.home_GK = home_GK;
    }

    public int getHome_OFF() {
        return home_OFF;
    }

    public void setHome_OFF(int home_OFF) {
        this.home_OFF = home_OFF;
    }

    public int getHome_LC() {
        return home_LC;
    }

    public void setHome_LC(int home_LC) {
        this.home_LC = home_LC;
    }

    public int getHome_COR() {
        return home_COR;
    }

    public void setHome_COR(int home_COR) {
        this.home_COR = home_COR;
    }

    public int getHome_TCK() {
        return home_TCK;
    }

    public void setHome_TCK(int home_TCK) {
        this.home_TCK = home_TCK;
    }

    public int getHome_POP() {
        return home_POP;
    }

    public void setHome_POP(int home_POP) {
        this.home_POP = home_POP;
    }

    public int getHome_TIME() {
        return home_TIME;
    }

    public void setHome_TIME(int home_TIME) {
        this.home_TIME = home_TIME;
    }

    public int getAway_Score() {
        return away_Score;
    }

    public void setAway_Score(int away_Score) {
        this.away_Score = away_Score;
    }

    public int getAway_TI() {
        return away_TI;
    }

    public void setAway_TI(int away_TI) {
        this.away_TI = away_TI;
    }

    public int getAway_DFK() {
        return away_DFK;
    }

    public void setAway_DFK(int away_DFK) {
        this.away_DFK = away_DFK;
    }

    public int getAway_SOnT() {
        return away_SOnT;
    }

    public void setAway_SOnT(int away_SOnT) {
        this.away_SOnT = away_SOnT;
    }

    public int getAway_SOffT() {
        return away_SOffT;
    }

    public void setAway_SOffT(int away_SOffT) {
        this.away_SOffT = away_SOffT;
    }

    public int getAway_GK() {
        return away_GK;
    }

    public void setAway_GK(int away_GK) {
        this.away_GK = away_GK;
    }

    public int getAway_OFF() {
        return away_OFF;
    }

    public void setAway_OFF(int away_OFF) {
        this.away_OFF = away_OFF;
    }

    public int getAway_LC() {
        return away_LC;
    }

    public void setAway_LC(int away_LC) {
        this.away_LC = away_LC;
    }

    public int getAway_COR() {
        return away_COR;
    }

    public void setAway_COR(int away_COR) {
        this.away_COR = away_COR;
    }

    public int getAway_TCK() {
        return away_TCK;
    }

    public void setAway_TCK(int away_TCK) {
        this.away_TCK = away_TCK;
    }

    public int getAway_POP() {
        return away_POP;
    }

    public void setAway_POP(int away_POP) {
        this.away_POP = away_POP;
    }

    public int getAway_TIME() {
        return away_TIME;
    }

    public void setAway_TIME(int away_TIME) {
        this.away_TIME = away_TIME;
    }

    public int getHome_H1() { return home_H1;
    }

    public void setHome_H1(int home_H1) {
        this.home_H1 = home_H1;
    }

    public int getHome_H2() {
        return home_H2;
    }

    public void setHome_H2(int home_H2) {
        this.home_H2 = home_H2;
    }

    public int getHome_H3() {
        return home_H3;
    }

    public void setHome_H3(int home_H3) {
        this.home_H3 = home_H3;
    }

    public int getHome_H4() {
        return home_H4;
    }

    public void setHome_H4(int home_H4) {
        this.home_H4 = home_H4;
    }

    public int getAway_A1() {
        return away_A1;
    }

    public void setAway_A1(int away_A1) {
        this.away_A1 = away_A1;
    }

    public int getAway_A2() {
        return away_A2;
    }

    public void setAway_A2(int away_A2) {
        this.away_A2 = away_A2;
    }

    public int getAway_A3() {
        return away_A3;
    }

    public void setAway_A3(int away_A3) {
        this.away_A3 = away_A3;
    }

    public int getAway_A4() {
        return away_A4;
    }

    public void setAway_A4(int away_A4) {
        this.away_A4 = away_A4;
    }
}
