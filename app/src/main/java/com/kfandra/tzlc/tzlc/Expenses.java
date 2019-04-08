package com.kfandra.tzlc.tzlc;

import java.util.Date;

public class Expenses {
    private long id;
    private String category;
    private int amount;
    private String date;
    private int type;
    private long date_number;

    public Expenses() {
    }

    public Expenses(String category, int amount, String date, int type) {
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.type = type;
    }

    public long getDate_number() {return date_number;}

    public void setDate_number(long date_number) {this.date_number = date_number;}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

