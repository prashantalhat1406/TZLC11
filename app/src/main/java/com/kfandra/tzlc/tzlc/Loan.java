package com.kfandra.tzlc.tzlc;

public class Loan {
    private long id;
    private long matchID;
    private long homeClubID;
    private long missingPlayerID;
    private long loanClubID;
    private long loanPlayerID;
    private int rule;
    private int type;

    public Loan() {}

    public Loan(long matchID, long homeClubID, long missingPlayerID, long loanClubID, long loanPlayerID, int rule, int type) {
        this.matchID = matchID;
        this.homeClubID = homeClubID;
        this.missingPlayerID = missingPlayerID;
        this.loanClubID = loanClubID;
        this.loanPlayerID = loanPlayerID;
        this.rule = rule;
        this.type = type;
    }

    public Loan(long matchID, long homeClubID, long missingPlayerID, long loanClubID, long loanPlayerID) {
        this.matchID = matchID;
        this.homeClubID = homeClubID;
        this.missingPlayerID = missingPlayerID;
        this.loanClubID = loanClubID;
        this.loanPlayerID = loanPlayerID;
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

    public long getHomeClubID() {
        return homeClubID;
    }

    public void setHomeClubID(long homeClubID) {
        this.homeClubID = homeClubID;
    }

    public long getMissingPlayerID() {
        return missingPlayerID;
    }

    public void setMissingPlayerID(long missingPlayerID) {
        this.missingPlayerID = missingPlayerID;
    }

    public long getLoanClubID() {
        return loanClubID;
    }

    public void setLoanClubID(long loanClubID) {
        this.loanClubID = loanClubID;
    }

    public long getLoanPlayerID() {
        return loanPlayerID;
    }

    public void setLoanPlayerID(long loanPlayerID) {
        this.loanPlayerID = loanPlayerID;
    }

    public int getRule() {
        return rule;
    }

    public void setRule(int rule) {
        this.rule = rule;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
