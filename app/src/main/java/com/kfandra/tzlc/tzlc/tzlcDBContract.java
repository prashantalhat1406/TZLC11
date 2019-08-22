package com.kfandra.tzlc.tzlc;

import android.provider.BaseColumns;

public class tzlcDBContract{
    public tzlcDBContract() {}


    static final String CREATE_CLUB_TABLE =
            "CREATE TABLE " + ClubDataDB.TABLE_NAME +
                " ( " +
                    ClubDataDB._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ClubDataDB.COLUMN_CLUB_NAME + " TEXT NOT NULL, " +
                    ClubDataDB.COLUMN_CLUB_SHORT_NAME + " TEXT NOT NULL, " +
                    ClubDataDB.COLUMN_MANAGER_NAME + " TEXT NOT NULL, " +
                    ClubDataDB.COLUMN_MANAGER2_NAME + " TEXT NOT NULL, " +
                    ClubDataDB.COLUMN_HOME_GROUND + " TEXT NOT NULL, " +
                    ClubDataDB.COLUMN_CLUB_COLOR + " INTEGER NOT NULL, " +
                    ClubDataDB.COLUMN_ORGANIZATION + " INTEGER NOT NULL, " +
                    ClubDataDB.COLUMN_SENIAL_WOMBATS + " INTEGER NOT NULL, " +
                    "UNIQUE ( " + ClubDataDB._ID + " ) ON CONFLICT REPLACE )";
    public static class ClubDataDB implements BaseColumns
    {
        public static final String TABLE_NAME = "clubDB";
        public static final String COLUMN_CLUB_NAME = "club_name";
        public static final String COLUMN_CLUB_SHORT_NAME = "club_short_name";
        public static final String COLUMN_MANAGER_NAME = "manager_name";
        public static final String COLUMN_MANAGER2_NAME = "manager2_name";
        public static final String COLUMN_HOME_GROUND = "home_ground";
        public static final String COLUMN_CLUB_COLOR = "club_color";
        public static final String COLUMN_ORGANIZATION = "org";
        public static final String COLUMN_SENIAL_WOMBATS = "sw";
    }

    //Table Schema for Match Table
    //RESULT : 0-lost,1-draw,2-win
    //TYPE : 1-League 1st Div (L1D), 2-League 2nd Div (L2D), 3-Combined League Match (CLM), 4-KFANDRAAI League Cup (KLC), 5-KFANDRAAI Cup (KC), 6-Friendly
    //SUBTYPE : 1-1st Round (1R), 2-Pre-Quarter Final (PQF),3-Semi Final (SF) and 4-Final (F)
    static final String CREATE_MATCH_TABLE =
            "CREATE TABLE " + MatchDB.TABLE_NAME +
                    " ( " +
                    MatchDB._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    MatchDB.COLUMN_DATE_NUMBER + " INTEGER NOT NULL, " +
                    MatchDB.COLUMN_HOME_CLUB + " INTEGER NOT NULL, " +
                    MatchDB.COLUMN_AWAY_CLUB + " INTEGER NOT NULL, " +
                    MatchDB.COLUMN_TYPE + " INTEGER NOT NULL, " +
                    MatchDB.COLUMN_SUBTYPE + " INTEGER NOT NULL, " +
                    MatchDB.COLUMN_RESULT + " INTEGER NOT NULL, " +
                    "UNIQUE ( " + MatchDB._ID + " ) ON CONFLICT REPLACE )";

    public static class MatchDB implements BaseColumns
    {
        public static final String TABLE_NAME = "matchDB";
        public static final String COLUMN_DATE_NUMBER = "date_number";
        public static final String COLUMN_HOME_CLUB = "homeClub";
        public static final String COLUMN_AWAY_CLUB = "awayClub";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_SUBTYPE = "subtype";
        public static final String COLUMN_RESULT = "result";
    }

    static final String CREATE_PLAYER_TABLE =
            "CREATE TABLE " + PlayerDB.TABLE_NAME +
                    " ( " +
                    PlayerDB._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    PlayerDB.COLUMN_PLAYER_NAME + " INTEGER NOT NULL, " +
                    PlayerDB.COLUMN_CLUB + " INTEGER NOT NULL, " +
                    PlayerDB.COLUMN_CURRENT_VALUE + " INTEGER NOT NULL, " +
                    PlayerDB.COLUMN_ORGANIZATION + " INTEGER NOT NULL, " +
                    PlayerDB.COLUMN_SENIALWOMBATS + " INTEGER NOT NULL, " +
                    PlayerDB.COLUMN_ABSENT + " INTEGER NOT NULL, " +
                    PlayerDB.COLUMN_POSITION + " INTEGER NOT NULL, " +
                    "UNIQUE ( " + PlayerDB._ID + " ) ON CONFLICT REPLACE )";

    public static class PlayerDB implements BaseColumns
    {
        public static final String TABLE_NAME = "playerDB";
        public static final String COLUMN_PLAYER_NAME = "playerName";
        public static final String COLUMN_CLUB = "club";
        public static final String COLUMN_CURRENT_VALUE = "value";
        public static final String COLUMN_ORGANIZATION = "org";
        public static final String COLUMN_SENIALWOMBATS = "sw";
        public static final String COLUMN_ABSENT = "absent";
        public static final String COLUMN_POSITION = "position";
    }

    static final String CREATE_MO_TABLE =
            "CREATE TABLE " + MatchOffcialDB.TABLE_NAME +
                    " ( " +
                    MatchOffcialDB._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    MatchOffcialDB.COLUMN_PLAYER_ID + " INTEGER NOT NULL, " +
                    MatchOffcialDB.COLUMN_MATCH_ID + " INTEGER NOT NULL, " +
                    MatchOffcialDB.COLUMN_JOB + " INTEGER NOT NULL, " +
                    MatchOffcialDB.COLUMN_ON_TIME + " INTEGER NOT NULL, " +
                    MatchOffcialDB.COLUMN_MO_TIME + " INTEGER NOT NULL, " +
                    MatchOffcialDB.COLUMN_CLUB_ID + " INTEGER NOT NULL, " +
                    "UNIQUE ( " + MatchOffcialDB._ID + " ) ON CONFLICT REPLACE )";

    public static class MatchOffcialDB implements BaseColumns
    {
        public static final String TABLE_NAME = "moDB";
        public static final String COLUMN_PLAYER_ID = "playerID";
        public static final String COLUMN_MATCH_ID = "matchID";
        public static final String COLUMN_JOB = "job";
        public static final String COLUMN_ON_TIME = "onTime";
        public static final String COLUMN_MO_TIME = "moTime";
        public static final String COLUMN_CLUB_ID = "clubid";
    }

    static final String CREATE_LOAN_TABLE =
            "CREATE TABLE " + LoanDB.TABLE_NAME +
                    " ( " +
                    LoanDB._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    LoanDB.COLUMN_MATCH_ID + " INTEGER NOT NULL, " +
                    LoanDB.COLUMN_HOME_CLUB_ID + " INTEGER NOT NULL, " +
                    LoanDB.COLUMN_MISSING_PLAYER_ID + " INTEGER NOT NULL, " +
                    LoanDB.COLUMN_LOAN_CLUB_ID + " INTEGER NOT NULL, " +
                    LoanDB.COLUMN_LOAN_PLAYER_ID + " INTEGER NOT NULL, " +
                    LoanDB.COLUMN_RULE + " INTEGER NOT NULL, " +
                    LoanDB.COLUMN_TYPE + " INTEGER NOT NULL, " +
                    "UNIQUE ( " + LoanDB._ID + " ) ON CONFLICT REPLACE )";

    public static class LoanDB implements BaseColumns
    {
        public static final String TABLE_NAME = "loanDB";
        public static final String COLUMN_MATCH_ID = "matchID";
        public static final String COLUMN_HOME_CLUB_ID = "homeClub";
        public static final String COLUMN_MISSING_PLAYER_ID = "missingPlayer";
        public static final String COLUMN_LOAN_CLUB_ID = "loanClub";
        public static final String COLUMN_LOAN_PLAYER_ID = "loanPlayer";
        public static final String COLUMN_RULE = "rule";
        public static final String COLUMN_TYPE = "type";
    }

    static final String CREATE_GOAL_TABLE =
            "CREATE TABLE " + GoalDB.TABLE_NAME +
                    " ( " +
                    GoalDB._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    GoalDB.COLUMN_MATCH_ID + " INTEGER NOT NULL, " +
                    GoalDB.COLUMN_PLAYER_ID + " INTEGER NOT NULL, " +
                    GoalDB.COLUMN_ASSIST_PLAYER_ID + " INTEGER NOT NULL, " +
                    GoalDB.COLUMN_CLUB_ID + " INTEGER NOT NULL, " +
                    GoalDB.COLUMN_MATCHTIME + " INTEGER NOT NULL, " +
                    GoalDB.COLUMN_VCMTIME + " INTEGER NOT NULL, " +
                    GoalDB.COLUMN_OWNGOAL + " INTEGER NOT NULL, " +
                    "UNIQUE ( " + GoalDB._ID + " ) ON CONFLICT REPLACE )";

    public static class GoalDB implements BaseColumns
    {
        public static final String TABLE_NAME = "goalDB";
        public static final String COLUMN_MATCH_ID = "matchID";
        public static final String COLUMN_PLAYER_ID = "playerID";
        public static final String COLUMN_ASSIST_PLAYER_ID = "assistPlayerID";
        public static final String COLUMN_CLUB_ID = "clubID";
        public static final String COLUMN_MATCHTIME = "time";
        public static final String COLUMN_VCMTIME = "srtime";
        public static final String COLUMN_OWNGOAL = "owngoal";
    }

    static final String CREATE_CARD_TABLE =
            "CREATE TABLE " + CardDB.TABLE_NAME +
                    " ( " +
                    CardDB._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CardDB.COLUMN_MATCH_ID + " INTEGER NOT NULL, " +
                    CardDB.COLUMN_PLAYER_ID + " INTEGER NOT NULL, " +
                    CardDB.COLUMN_TYPE + " INTEGER NOT NULL, " +
                    CardDB.COLUMN_TIME + " INTEGER NOT NULL, " +
                    CardDB.COLUMN_REASON + " TEXT NOT NULL, " +
                    CardDB.COLUMN_CLUB_ID + " INTEGER NOT NULL, " +
                    "UNIQUE ( " + CardDB._ID + " ) ON CONFLICT REPLACE )";

    public static class CardDB implements BaseColumns
    {
        public static final String TABLE_NAME = "cardDB";
        public static final String COLUMN_MATCH_ID = "matchID";
        public static final String COLUMN_PLAYER_ID = "playerID";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_REASON = "reason";
        public static final String COLUMN_CLUB_ID = "clubID";
    }


    static final String CREATE_STAT_TABLE =
            "CREATE TABLE " + StatDB.TABLE_NAME +
                    " ( " +
                    StatDB._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    StatDB.COLUMN_MATCH_ID + " INTEGER NOT NULL, " +
                    StatDB.COLUMN_MATCHTIME + " INTEGER NOT NULL, " +
                    StatDB.COLUMN_HOME_SCORE + " INTEGER NOT NULL, " +
                    StatDB.COLUMN_HOME_TI + " INTEGER NOT NULL, " +
                    StatDB.COLUMN_HOME_DFK + " INTEGER NOT NULL, " +
                    StatDB.COLUMN_HOME_SONT + " INTEGER NOT NULL, " +
                    StatDB.COLUMN_HOME_SOFFT + " INTEGER NOT NULL, " +
                    StatDB.COLUMN_HOME_GK + " INTEGER NOT NULL, " +
                    StatDB.COLUMN_HOME_OFF + " INTEGER NOT NULL, " +
                    StatDB.COLUMN_HOME_LC + " INTEGER NOT NULL, " +
                    StatDB.COLUMN_HOME_COR + " INTEGER NOT NULL, " +
                    StatDB.COLUMN_HOME_TCK + " INTEGER NOT NULL, " +
                    StatDB.COLUMN_HOME_POP + " INTEGER NOT NULL, " +
                    StatDB.COLUMN_HOME_TIME + " INTEGER NOT NULL, " +
                    StatDB.COLUMN_HOME_H1 + " INTEGER NOT NULL, " +
                    StatDB.COLUMN_HOME_H2 + " INTEGER NOT NULL, " +
                    StatDB.COLUMN_HOME_H3 + " INTEGER NOT NULL, " +
                    StatDB.COLUMN_HOME_H4 + " INTEGER NOT NULL, " +
                    StatDB.COLUMN_HOME_PASSES + " INTEGER NOT NULL, " +
                    StatDB.COLUMN_AWAY_SCORE + " INTEGER NOT NULL, " +
                    StatDB.COLUMN_AWAY_TI + " INTEGER NOT NULL, " +
                    StatDB.COLUMN_AWAY_DFK + " INTEGER NOT NULL, " +
                    StatDB.COLUMN_AWAY_SONT + " INTEGER NOT NULL, " +
                    StatDB.COLUMN_AWAY_SOFFT + " INTEGER NOT NULL, " +
                    StatDB.COLUMN_AWAY_GK + " INTEGER NOT NULL, " +
                    StatDB.COLUMN_AWAY_OFF + " INTEGER NOT NULL, " +
                    StatDB.COLUMN_AWAY_LC + " INTEGER NOT NULL, " +
                    StatDB.COLUMN_AWAY_COR + " INTEGER NOT NULL, " +
                    StatDB.COLUMN_AWAY_TCK + " INTEGER NOT NULL, " +
                    StatDB.COLUMN_AWAY_POP + " INTEGER NOT NULL, " +
                    StatDB.COLUMN_AWAY_TIME + " INTEGER NOT NULL, " +
                    StatDB.COLUMN_AWAY_A1 + " INTEGER NOT NULL, " +
                    StatDB.COLUMN_AWAY_A2 + " INTEGER NOT NULL, " +
                    StatDB.COLUMN_AWAY_A3 + " INTEGER NOT NULL, " +
                    StatDB.COLUMN_AWAY_A4 + " INTEGER NOT NULL, " +
                    StatDB.COLUMN_AWAY_PASSES + " INTEGER NOT NULL, " +
                    "UNIQUE ( " + StatDB._ID + " ) ON CONFLICT REPLACE )";

    public static class StatDB implements BaseColumns
    {
        public static final String TABLE_NAME = "statDB";
        public static final String COLUMN_MATCH_ID = "matchID";
        public static final String COLUMN_MATCHTIME = "matchtime";
        public static final String COLUMN_HOME_SCORE = "home_score";
        public static final String COLUMN_HOME_TI = "home_ti";
        public static final String COLUMN_HOME_DFK = "home_dfk";
        public static final String COLUMN_HOME_SONT = "home_sont";
        public static final String COLUMN_HOME_SOFFT = "home_sofft";
        public static final String COLUMN_HOME_GK = "home_gk";
        public static final String COLUMN_HOME_OFF = "home_off";
        public static final String COLUMN_HOME_LC = "home_lc";
        public static final String COLUMN_HOME_COR = "home_cor";
        public static final String COLUMN_HOME_TCK = "home_tck";
        public static final String COLUMN_HOME_POP = "home_pop";
        public static final String COLUMN_HOME_TIME = "home_time";
        public static final String COLUMN_HOME_H1 = "home_h1";
        public static final String COLUMN_HOME_H2 = "home_h2";
        public static final String COLUMN_HOME_H3 = "home_h3";
        public static final String COLUMN_HOME_H4 = "home_h4";
        public static final String COLUMN_HOME_PASSES = "home_passes";
        public static final String COLUMN_AWAY_SCORE = "away_score";
        public static final String COLUMN_AWAY_TI = "away_ti";
        public static final String COLUMN_AWAY_DFK = "away_dfk";
        public static final String COLUMN_AWAY_SONT = "away_sont";
        public static final String COLUMN_AWAY_SOFFT = "away_sofft";
        public static final String COLUMN_AWAY_GK = "away_gk";
        public static final String COLUMN_AWAY_OFF = "away_off";
        public static final String COLUMN_AWAY_LC = "away_lc";
        public static final String COLUMN_AWAY_COR = "away_cor";
        public static final String COLUMN_AWAY_TCK = "away_tck";
        public static final String COLUMN_AWAY_POP = "away_pop";
        public static final String COLUMN_AWAY_TIME = "away_time";
        public static final String COLUMN_AWAY_A1 = "away_a1";
        public static final String COLUMN_AWAY_A2 = "away_a2";
        public static final String COLUMN_AWAY_A3 = "away_a3";
        public static final String COLUMN_AWAY_A4 = "away_a4";
        public static final String COLUMN_AWAY_PASSES = "away_passes";
    }

    static final String CREATE_HIGHLIGHT_TABLE =
            "CREATE TABLE " + HighlightDB.TABLE_NAME +
                    " ( " +
                    HighlightDB._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    HighlightDB.COLUMN_MATCH_ID + " INTEGER NOT NULL, " +
                    HighlightDB.COLUMN_CLUB_ID + " INTEGER NOT NULL, " +
                    HighlightDB.COLUMN_VCM_TIME + " INTEGER NOT NULL, " +
                    HighlightDB.COLUMN_SR_TIME + " INTEGER NOT NULL, " +
                    HighlightDB.COLUMN_HIGHLIGHT + " TEXT NOT NULL, " +
                    HighlightDB.COLUMN_HIGHLIGHT2 + " TEXT NOT NULL, " +
                    "UNIQUE ( " + HighlightDB._ID + " ) ON CONFLICT REPLACE )";

    public static class HighlightDB implements BaseColumns
    {
        public static final String TABLE_NAME = "highlightDB";
        public static final String COLUMN_MATCH_ID = "matchID";
        public static final String COLUMN_CLUB_ID = "clubID";
        public static final String COLUMN_VCM_TIME = "vcmTime";
        public static final String COLUMN_SR_TIME = "srTime";
        public static final String COLUMN_HIGHLIGHT = "highlight";
        public static final String COLUMN_HIGHLIGHT2 = "highlight2";
    }

    static final String CREATE_SQUAD_TABLE =
            "CREATE TABLE " + SquadDB.TABLE_NAME +
                    " ( " +
                    SquadDB._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    SquadDB.COLUMN_MATCH_ID + " INTEGER NOT NULL, " +
                    SquadDB.COLUMN_CLUB_ID + " INTEGER NOT NULL, " +
                    SquadDB.COLUMN_PLAYER_ID + " INTEGER NOT NULL, " +
                    SquadDB.COLUMN_ABSENT + " INTEGER NOT NULL, " +
                    SquadDB.COLUMN_POSITION + " INTEGER NOT NULL, " +
                    "UNIQUE ( " + SquadDB._ID + " ) ON CONFLICT REPLACE )";

    public static class SquadDB implements BaseColumns
    {
        public static final String TABLE_NAME = "squadDB";
        public static final String COLUMN_MATCH_ID = "matchID";
        public static final String COLUMN_CLUB_ID = "clubID";
        public static final String COLUMN_PLAYER_ID = "playerID";
        public static final String COLUMN_ABSENT = "absent";
        public static final String COLUMN_POSITION = "position";
    }

    static final String CREATE_FORMATION_TABLE =
            "CREATE TABLE " + FormationDB.TABLE_NAME +
                    " ( " +
                    FormationDB._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    FormationDB.COLUMN_MATCH_ID + " INTEGER NOT NULL, " +
                    FormationDB.COLUMN_CLUB_ID + " INTEGER NOT NULL, " +
                    FormationDB.COLUMN_FORMATIONS + " INTEGER NOT NULL, " +
                    FormationDB.COLUMN_TYPE + " INTEGER NOT NULL, " +
                    FormationDB.COLUMN_GK + " INTEGER NOT NULL, " +
                    FormationDB.COLUMN_RB + " INTEGER NOT NULL, " +
                    FormationDB.COLUMN_RCD + " INTEGER NOT NULL, " +
                    FormationDB.COLUMN_CD + " INTEGER NOT NULL, " +
                    FormationDB.COLUMN_LCD + " INTEGER NOT NULL, " +
                    FormationDB.COLUMN_LB + " INTEGER NOT NULL, " +
                    FormationDB.COLUMN_RM + " INTEGER NOT NULL, " +
                    FormationDB.COLUMN_RCM + " INTEGER NOT NULL, " +
                    FormationDB.COLUMN_CM + " INTEGER NOT NULL, " +
                    FormationDB.COLUMN_LCM + " INTEGER NOT NULL, " +
                    FormationDB.COLUMN_LM + " INTEGER NOT NULL, " +
                    FormationDB.COLUMN_RST + " INTEGER NOT NULL, " +
                    FormationDB.COLUMN_ST + " INTEGER NOT NULL, " +
                    FormationDB.COLUMN_LST + " INTEGER NOT NULL, " +
                    "UNIQUE ( " + FormationDB._ID + " ) ON CONFLICT REPLACE )";

    public static class FormationDB implements BaseColumns
    {
        public static final String TABLE_NAME = "formationDB";
        public static final String COLUMN_MATCH_ID = "matchID";
        public static final String COLUMN_CLUB_ID = "clubID";
        public static final String COLUMN_FORMATIONS = "formations";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_GK = "gk";
        public static final String COLUMN_RB = "rb";
        public static final String COLUMN_RCD = "rcd";
        public static final String COLUMN_CD = "cd";
        public static final String COLUMN_LCD = "lcd";
        public static final String COLUMN_LB = "lb";
        public static final String COLUMN_RM = "rm";
        public static final String COLUMN_RCM = "rcm";
        public static final String COLUMN_CM = "cm";
        public static final String COLUMN_LCM = "lcm";
        public static final String COLUMN_LM = "lm";
        public static final String COLUMN_RST = "rst";
        public static final String COLUMN_ST = "st";
        public static final String COLUMN_LST = "lst";
    }

    static final String CREATE_SUBSTITUTE_TABLE =
            "CREATE TABLE " + SubDB.TABLE_NAME +
                    " ( " +
                    SubDB._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    SubDB.COLUMN_MATCH_ID + " INTEGER NOT NULL, " +
                    SubDB.COLUMN_PLAYER_OUT_ID + " INTEGER NOT NULL, " +
                    SubDB.COLUMN_PLAYER_IN_ID + " INTEGER NOT NULL, " +
                    SubDB.COLUMN_TIME + " INTEGER NOT NULL, " +
                    SubDB.COLUMN_REASON + " TEXT NOT NULL, " +
                    SubDB.COLUMN_CLUB_ID + " INTEGER NOT NULL, " +
                    "UNIQUE ( " + SubDB._ID + " ) ON CONFLICT REPLACE )";

    public static class SubDB implements BaseColumns
    {
        public static final String TABLE_NAME = "subDB";
        public static final String COLUMN_MATCH_ID = "matchID";
        public static final String COLUMN_PLAYER_OUT_ID = "playerOutID";
        public static final String COLUMN_PLAYER_IN_ID = "playerInID";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_REASON = "reason";
        public static final String COLUMN_CLUB_ID = "clubID";
    }







    static final String CREATE_EXPENSE_TABLE =
            "CREATE TABLE " + SaiSevaExpenseDB.TABLE_NAME +
                    " ( " +
                    SaiSevaExpenseDB._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    SaiSevaExpenseDB.COLUMN_CATEGORY + " TEXT NOT NULL, " +
                    SaiSevaExpenseDB.COLUMN_AMOUNT + " INTEGER NOT NULL, " +
                    SaiSevaExpenseDB.COLUMN_DATE + " TEXT NOT NULL, " +
                    SaiSevaExpenseDB.COLUMN_TYPE + " INTEGER NOT NULL, " +
                    SaiSevaExpenseDB.COLUMN_DATE_NUMBER + " INTEGER NOT NULL, " +
                    "UNIQUE ( " + SaiSevaExpenseDB._ID + " ) ON CONFLICT REPLACE )";


    public static class SaiSevaExpenseDB implements BaseColumns
    {
        public static final String TABLE_NAME = "expenseDB";
        public static final String COLUMN_CATEGORY = "category";
        public static final String COLUMN_AMOUNT = "amount";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_DATE_NUMBER = "date_number";
    }
}
