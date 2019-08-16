package com.kfandra.tzlc.tzlc;

public class possesionTimer {
    String location;
    int timer;

    public possesionTimer() {
    }

    public possesionTimer(String location, int timer) {
        this.location = location;
        this.timer = timer;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }
}
