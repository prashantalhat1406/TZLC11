package com.kfandra.tzlc.tzlc;

public class Goal {
    private long id;
    private long matchID;
    private long playerID;
    private long assistPlayerID;
    private long againstClubID;
    private int matchTime;
    private  int vcmtime;
    private  int ownGoal;

    public Goal() {
    }

    public Goal(long matchID, long playerID, long assistPlayerID, long againstClubID, int matchTime, int vcmtime) {
        this.matchID = matchID;
        this.playerID = playerID;
        this.assistPlayerID = assistPlayerID;
        this.againstClubID = againstClubID;
        this.matchTime = matchTime;
        this.vcmtime = vcmtime;
    }

    public Goal(long matchID, long playerID, long assistPlayerID, long againstClubID, int matchTime) {
        this.matchID = matchID;
        this.playerID = playerID;
        this.assistPlayerID = assistPlayerID;
        this.againstClubID = againstClubID;
        this.matchTime = matchTime;
    }

    public int getOwnGoal() {return ownGoal;}

    public void setOwnGoal(int ownGoal) {this.ownGoal = ownGoal;}

    public int getVcmtime() {return vcmtime;}

    public void setVcmtime(int vcmtime) {this.vcmtime = vcmtime;}

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

    public long getPlayerID() {
        return playerID;
    }

    public void setPlayerID(long playerID) {
        this.playerID = playerID;
    }

    public long getAssistPlayerID() {
        return assistPlayerID;
    }

    public void setAssistPlayerID(long assistPlayerID) {
        this.assistPlayerID = assistPlayerID;
    }

    public long getAgainstClubID() {
        return againstClubID;
    }

    public void setAgainstClubID(long againstClubID) {
        this.againstClubID = againstClubID;
    }

    public int getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(int matchTime) {
        this.matchTime = matchTime;
    }
}
