package com.kfandra.tzlc.tzlc;

public class Formation {
    private long id;
    private long matchID;
    private long clubID;
    private int formations;
    private int formationType;
    private long gk;
    private long rb;
    private long rcd;
    private long cd;
    private long lcd;
    private long lb;
    private long rm;
    private long rcm;
    private long cm;
    private long lcm;
    private long lm;
    private long rst;
    private long st;
    private long lst;

    public Formation(long matchID, long clubID, int formations, int formationType, long gk, long rb, long rcd, long cd, long lcd, long lb, long rm, long rcm, long cm, long lcm, long lm, long rst, long st, long lst) {
        this.matchID = matchID;
        this.clubID = clubID;
        this.formations = formations;
        this.formationType = formationType;
        this.gk = gk;
        this.rb = rb;
        this.rcd = rcd;
        this.cd = cd;
        this.lcd = lcd;
        this.lb = lb;
        this.rm = rm;
        this.rcm = rcm;
        this.cm = cm;
        this.lcm = lcm;
        this.lm = lm;
        this.rst = rst;
        this.st = st;
        this.lst = lst;
    }

    public Formation() {
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

    public int getFormations() {
        return formations;
    }

    public void setFormations(int formations) {
        this.formations = formations;
    }

    public int getFormationType() {
        return formationType;
    }

    public void setFormationType(int formationType) {
        this.formationType = formationType;
    }

    public long getGk() {
        return gk;
    }

    public void setGk(long gk) {
        this.gk = gk;
    }

    public long getRb() {
        return rb;
    }

    public void setRb(long rb) {
        this.rb = rb;
    }

    public long getRcd() {
        return rcd;
    }

    public void setRcd(long rcd) {
        this.rcd = rcd;
    }

    public long getCd() {
        return cd;
    }

    public void setCd(long cd) {
        this.cd = cd;
    }

    public long getLcd() {
        return lcd;
    }

    public void setLcd(long lcd) {
        this.lcd = lcd;
    }

    public long getLb() {
        return lb;
    }

    public void setLb(long lb) {
        this.lb = lb;
    }

    public long getRm() {
        return rm;
    }

    public void setRm(long rm) {
        this.rm = rm;
    }

    public long getRcm() {
        return rcm;
    }

    public void setRcm(long rcm) {
        this.rcm = rcm;
    }

    public long getCm() {
        return cm;
    }

    public void setCm(long cm) {
        this.cm = cm;
    }

    public long getLcm() {
        return lcm;
    }

    public void setLcm(long lcm) {
        this.lcm = lcm;
    }

    public long getLm() {
        return lm;
    }

    public void setLm(long lm) {
        this.lm = lm;
    }

    public long getRst() {
        return rst;
    }

    public void setRst(long rst) {
        this.rst = rst;
    }

    public long getSt() {
        return st;
    }

    public void setSt(long st) {
        this.st = st;
    }

    public long getLst() {
        return lst;
    }

    public void setLst(long lst) {
        this.lst = lst;
    }
}
