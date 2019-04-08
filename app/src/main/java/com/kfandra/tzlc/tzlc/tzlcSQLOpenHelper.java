package com.kfandra.tzlc.tzlc;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class tzlcSQLOpenHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "tzlcTest.db";
    private static final int VERSION_NUMBER = 26;

    public tzlcSQLOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUMBER);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(tzlcDBContract.CREATE_CLUB_TABLE);
        db.execSQL(tzlcDBContract.CREATE_MATCH_TABLE);
        db.execSQL(tzlcDBContract.CREATE_PLAYER_TABLE);
        db.execSQL(tzlcDBContract.CREATE_MO_TABLE);
        db.execSQL(tzlcDBContract.CREATE_LOAN_TABLE);
        db.execSQL(tzlcDBContract.CREATE_GOAL_TABLE);
        db.execSQL(tzlcDBContract.CREATE_CARD_TABLE);
        db.execSQL(tzlcDBContract.CREATE_STAT_TABLE);
        db.execSQL(tzlcDBContract.CREATE_HIGHLIGHT_TABLE);
        db.execSQL(tzlcDBContract.CREATE_SQUAD_TABLE);
        db.execSQL(tzlcDBContract.CREATE_SUBSTITUTE_TABLE);
        db.execSQL(tzlcDBContract.CREATE_EXPENSE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + tzlcDBContract.ClubDataDB.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + tzlcDBContract.MatchDB.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + tzlcDBContract.PlayerDB.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + tzlcDBContract.MatchOffcialDB.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + tzlcDBContract.LoanDB.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + tzlcDBContract.GoalDB.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + tzlcDBContract.CardDB.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + tzlcDBContract.StatDB.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + tzlcDBContract.HighlightDB.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + tzlcDBContract.SquadDB.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + tzlcDBContract.SubDB.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + tzlcDBContract.SaiSevaExpenseDB.TABLE_NAME);
        onCreate(db);
    }
}
