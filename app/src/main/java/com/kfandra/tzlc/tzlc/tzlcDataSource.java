package com.kfandra.tzlc.tzlc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


public class tzlcDataSource
{

    private SQLiteDatabase database;
    private tzlcSQLOpenHelper dbhelper;

    tzlcDataSource(Context context)
    {
        this.dbhelper = new tzlcSQLOpenHelper(context);
    }

    public void open()    {
        database = dbhelper.getWritableDatabase();
        Log.d(tzlcDataSource.class.getSimpleName(), "database Open");
    }

    public void close()    {
        dbhelper.close();
        Log.d(tzlcDataSource.class.getSimpleName(), "database Close");
    }





    //CULB
    public void deleteClub(long clubID)    {
        String selection = tzlcDBContract.ClubDataDB._ID + " = ?";
        String[] selectionargs = {String.valueOf(clubID)};
        int count = database.delete(tzlcDBContract.ClubDataDB.TABLE_NAME,selection,selectionargs);
        Log.d(tzlcDataSource.class.getSimpleName(), "Club deleted " + count);
    }

    public ContentValues createContentForClub(Club club)    {
        ContentValues value = new ContentValues();
        value.put(tzlcDBContract.ClubDataDB.COLUMN_CLUB_NAME,club.getClubName());
        value.put(tzlcDBContract.ClubDataDB.COLUMN_CLUB_SHORT_NAME,club.getClubShortName());
        value.put(tzlcDBContract.ClubDataDB.COLUMN_MANAGER_NAME,club.getManagerName());
        value.put(tzlcDBContract.ClubDataDB.COLUMN_MANAGER2_NAME,club.getManager2Name());
        value.put(tzlcDBContract.ClubDataDB.COLUMN_HOME_GROUND,club.getHomeGround());
        value.put(tzlcDBContract.ClubDataDB.COLUMN_CLUB_COLOR,club.getClubColor());
        value.put(tzlcDBContract.ClubDataDB.COLUMN_ORGANIZATION,club.getOrganization());
        value.put(tzlcDBContract.ClubDataDB.COLUMN_SENIAL_WOMBATS,club.getSenialWombat());
        return value;
    }

    public List<Club> createClubList(Cursor cursor){
        List<Club> clubs = new ArrayList<>();

        try{
            while(cursor.moveToNext())
            {
                Club c  = new Club(
                        cursor.getString(cursor.getColumnIndex(tzlcDBContract.ClubDataDB.COLUMN_CLUB_NAME)),
                        cursor.getString(cursor.getColumnIndex(tzlcDBContract.ClubDataDB.COLUMN_CLUB_SHORT_NAME)),
                        cursor.getString(cursor.getColumnIndex(tzlcDBContract.ClubDataDB.COLUMN_MANAGER_NAME)),
                        cursor.getString(cursor.getColumnIndex(tzlcDBContract.ClubDataDB.COLUMN_HOME_GROUND))
                        //getInt
                );
                c.setClubColor(cursor.getInt((cursor.getColumnIndex(tzlcDBContract.ClubDataDB.COLUMN_CLUB_COLOR))));
                c.setOrganization(cursor.getInt((cursor.getColumnIndex(tzlcDBContract.ClubDataDB.COLUMN_ORGANIZATION))));
                c.setSenialWombat(cursor.getInt((cursor.getColumnIndex(tzlcDBContract.ClubDataDB.COLUMN_SENIAL_WOMBATS))));
                c.setId(cursor.getLong((cursor.getColumnIndex(tzlcDBContract.ClubDataDB._ID))));
                c.setManager2Name(cursor.getString(cursor.getColumnIndex(tzlcDBContract.ClubDataDB.COLUMN_MANAGER2_NAME)));
                Log.d(tzlcDataSource.class.getSimpleName(), "Data Fetched " + c.getClubName()+","+c.getClubShortName()+","+c.getHomeGround()+","+c.getManagerName());
                clubs.add(c);
            }
        }
        finally {
            if(cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return clubs;
    }

    public void addClub(Club c)    {
        ContentValues value = createContentForClub(c);
        long rowID =  database.insert(tzlcDBContract.ClubDataDB.TABLE_NAME,null, value);
        Log.d(tzlcDataSource.class.getSimpleName(), "Club added " + rowID);
    }

    public void updateClub(Club club)    {
        ContentValues value = createContentForClub(club);
        String selection = tzlcDBContract.ClubDataDB._ID + " = ?";
        String[] selectionargs = {String.valueOf(club.getId())};

        int count = database.update(tzlcDBContract.ClubDataDB.TABLE_NAME,value,selection,selectionargs);
        Log.d(tzlcDataSource.class.getSimpleName(), "Club record updated " + count);
    }

    public List<Club> getAllClubs()    {
        List<Club> clubs = new ArrayList<>();
        String selectQuery = "SELECT * FROM clubDB ORDER BY club_name ASC";
        Cursor cursor = database.rawQuery(selectQuery, null);
        clubs = createClubList(cursor);
        return clubs;
    }

    public int getClubsCount()    {
        int clubCount=-1;
        List<Club> clubs = getAllClubs();
        return clubs.size();
    }

    public long getClubID(String clubName)    {

        long clubID=-1;
        String selectQuery = "SELECT "+tzlcDBContract.ClubDataDB._ID+" FROM clubDB where club_name LIKE '"+clubName+"' ORDER BY club_name ASC";
        Cursor cursor = database.rawQuery(selectQuery, null);
        Log.d(tzlcDataSource.class.getSimpleName(), "Cursor Size : " + cursor.getCount());

        try{

            while(cursor.moveToNext()){
                Log.d(tzlcDataSource.class.getSimpleName(), "Club ID : " + cursor.getLong(cursor.getColumnIndex(tzlcDBContract.ClubDataDB._ID)));
                clubID =cursor.getLong(cursor.getColumnIndex(tzlcDBContract.ClubDataDB._ID));
            }
        }
        finally {
            if(cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return clubID;
    }

    public Club getClub(long clubID)    {

        Club club = new Club();
        String selectQuery = "SELECT * FROM clubDB where _ID = "+clubID+"";
        Cursor cursor = database.rawQuery(selectQuery, null);
        Log.d(tzlcDataSource.class.getSimpleName(), "Cursor Size : " + cursor.getCount());

        try{
            while(cursor.moveToNext())
            {
                club.setClubName(cursor.getString(cursor.getColumnIndex(tzlcDBContract.ClubDataDB.COLUMN_CLUB_NAME)));
                club.setManagerName(cursor.getString(cursor.getColumnIndex(tzlcDBContract.ClubDataDB.COLUMN_MANAGER_NAME)));
                club.setManager2Name(cursor.getString(cursor.getColumnIndex(tzlcDBContract.ClubDataDB.COLUMN_MANAGER2_NAME)));
                club.setClubShortName(cursor.getString(cursor.getColumnIndex(tzlcDBContract.ClubDataDB.COLUMN_CLUB_SHORT_NAME)));
                club.setHomeGround(cursor.getString(cursor.getColumnIndex(tzlcDBContract.ClubDataDB.COLUMN_HOME_GROUND)));
                club.setClubColor(cursor.getInt((cursor.getColumnIndex(tzlcDBContract.ClubDataDB.COLUMN_CLUB_COLOR))));
                club.setOrganization(cursor.getInt((cursor.getColumnIndex(tzlcDBContract.ClubDataDB.COLUMN_ORGANIZATION))));
                club.setSenialWombat(cursor.getInt((cursor.getColumnIndex(tzlcDBContract.ClubDataDB.COLUMN_SENIAL_WOMBATS))));
                club.setId(cursor.getLong((cursor.getColumnIndex(tzlcDBContract.ClubDataDB._ID))));

                Log.d(tzlcDataSource.class.getSimpleName(), "Data Fetched " + club.getClubName()+","+club.getClubShortName()+","+club.getHomeGround()+","+club.getManagerName());

            }
        }
        finally {
            if(cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return club;
    }

    public boolean isOrgnization(long clubID){

        Club c = getClub(clubID);
        if(c.getOrganization() == 0)
            return false;
        else
            return true;
    }

    public boolean isSenialWombat(long clubID){

        Club c = getClub(clubID);
        if(c.getSenialWombat() == 0)
            return false;
        else
            return true;
    }

    public List<String> getAllClubNames()    {

        List<String> clubnames = new ArrayList<>();
        String selectQuery = "SELECT club_name FROM clubDB  ORDER BY club_name ASC";
        Cursor cursor = database.rawQuery(selectQuery, null);
        Log.d(tzlcDataSource.class.getSimpleName(), "Cursor Size : " + cursor.getCount());

        try{

            while(cursor.moveToNext()){
                Log.d(tzlcDataSource.class.getSimpleName(), "Club Name : " + cursor.getString(cursor.getColumnIndex(tzlcDBContract.ClubDataDB.COLUMN_CLUB_NAME)));
                clubnames.add(cursor.getString(cursor.getColumnIndex(tzlcDBContract.ClubDataDB.COLUMN_CLUB_NAME)));
            }
        }
        finally {
            if(cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return clubnames;
    }

    public List<String> getAllClubNamesExceptOrg()    {

        List<String> clubnames = new ArrayList<>();
        String selectQuery = "SELECT club_name FROM clubDB WHERE org = 0  ORDER BY club_name ASC";
        Cursor cursor = database.rawQuery(selectQuery, null);
        Log.d(tzlcDataSource.class.getSimpleName(), "Cursor Size : " + cursor.getCount());
        try{

            while(cursor.moveToNext()){
                Log.d(tzlcDataSource.class.getSimpleName(), "Club Name : " + cursor.getString(cursor.getColumnIndex(tzlcDBContract.ClubDataDB.COLUMN_CLUB_NAME)));
                clubnames.add(cursor.getString(cursor.getColumnIndex(tzlcDBContract.ClubDataDB.COLUMN_CLUB_NAME)));
            }
        }
        finally {
            if(cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return clubnames;
    }

    public List<String> getAllOrgNames()    {

        List<String> orgnames = new ArrayList<>();
        String selectQuery = "SELECT club_name FROM clubDB where org = 1  ORDER BY club_name ASC";
        Cursor cursor = database.rawQuery(selectQuery, null);
        Log.d(tzlcDataSource.class.getSimpleName(), "Cursor Size : " + cursor.getCount());

        try{

            while(cursor.moveToNext()){
                Log.d(tzlcDataSource.class.getSimpleName(), "Club Name : " + cursor.getString(cursor.getColumnIndex(tzlcDBContract.ClubDataDB.COLUMN_CLUB_NAME)));
                orgnames.add(cursor.getString(cursor.getColumnIndex(tzlcDBContract.ClubDataDB.COLUMN_CLUB_NAME)));
            }
        }
        finally {
            if(cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return orgnames;
    }

    public List<String> getAllClubNamesApartFromMatchClubs(long matchID)    {
        List<String> clubnames = new ArrayList<>();
        List<String> temp = new ArrayList<>();
        temp=getAllClubNamesExceptOrg();
        Match m = getMatch(matchID);
        String homeName, awayName;
        homeName= getClub(m.getHomeClubID()).getClubName();
        awayName=getClub(m.getAwayClubID()).getClubName();
        for (String t : temp) {
            if(!t.equalsIgnoreCase(homeName) && !t.equalsIgnoreCase(awayName))
                clubnames.add(t);

        }
        return clubnames;

    }


    //MATCH
    public ContentValues createContentForMatch(Match m)     {
        ContentValues value = new ContentValues();
        value.put(tzlcDBContract.MatchDB.COLUMN_DATE_NUMBER,m.getDate_number());
        value.put(tzlcDBContract.MatchDB.COLUMN_HOME_CLUB,m.getHomeClubID());
        value.put(tzlcDBContract.MatchDB.COLUMN_AWAY_CLUB,m.getAwayClubID());
        value.put(tzlcDBContract.MatchDB.COLUMN_TYPE,m.getType());
        value.put(tzlcDBContract.MatchDB.COLUMN_SUBTYPE,m.getSubtype());
        value.put(tzlcDBContract.MatchDB.COLUMN_RESULT,m.getResult());
        return value;
    }

    public void addMatch(Match match) {
        ContentValues value = createContentForMatch(match);
        long rowID =  database.insert(tzlcDBContract.MatchDB.TABLE_NAME,null, value);
        Log.d(tzlcDataSource.class.getSimpleName(), "Match added " + rowID);
    }

    public void updateMatch(Match match)    {
        ContentValues value = createContentForMatch(match);
        String selection = tzlcDBContract.MatchDB._ID + " = ?";
        String[] selectionargs = {String.valueOf(match.getId())};
        int count = database.update(tzlcDBContract.MatchDB.TABLE_NAME,value,selection,selectionargs);
        Log.d(tzlcDataSource.class.getSimpleName(), "Match record updated " + count);
    }

    public void deleteMatch(long matchID)    {
        String selection = tzlcDBContract.MatchDB._ID + " = ?";
        String[] selectionargs = {String.valueOf(matchID)};
        int count = database.delete(tzlcDBContract.MatchDB.TABLE_NAME,selection,selectionargs);
        Log.d(tzlcDataSource.class.getSimpleName(), "Match deleted " + count);
    }

    public List<Match> createMatchList(Cursor cursor){
        List<Match> matches = new ArrayList<>();
        try{
            while(cursor.moveToNext())
            {
                Match m  = new Match(
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.MatchDB.COLUMN_DATE_NUMBER)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.MatchDB.COLUMN_HOME_CLUB)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.MatchDB.COLUMN_AWAY_CLUB)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.MatchDB.COLUMN_TYPE)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.MatchDB.COLUMN_SUBTYPE)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.MatchDB.COLUMN_RESULT))
                );
                m.setId(cursor.getLong((cursor.getColumnIndex(tzlcDBContract.MatchDB._ID))));
                Log.d(tzlcDataSource.class.getSimpleName(), "Match  Fetched " +m.getDate_number()+"," +m.getHomeClubID()+","+m.getAwayClubID()+","+m.getType()+","+m.getSubtype()+","+m.getResult());
                matches.add(m);
            }
        }
        finally {
            if(cursor != null && !cursor.isClosed())
                cursor.close();
        }

        return matches;
    }

    public List<Match> getAllMatches()    {
        List<Match> matches = new ArrayList<>();
        String selectQuery = "SELECT * FROM matchDB ORDER BY date_number";
        Cursor cursor = database.rawQuery(selectQuery, null);
        matches = createMatchList(cursor);

        return matches;
    }

    public static int getWeekStartDate() {
        Calendar calendar = Calendar.getInstance();
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            calendar.add(Calendar.DATE, -1);
        }
        return calendar.get(Calendar.YEAR)*10000+(calendar.get(Calendar.MONTH)+1)*100+calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static int getWeekEndDate() {
        Calendar calendar = Calendar.getInstance();
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            calendar.add(Calendar.DATE, 1);
        }
        calendar.add(Calendar.DATE, -1);
        return calendar.get(Calendar.YEAR)*10000+(calendar.get(Calendar.MONTH)+1)*100+calendar.get(Calendar.DAY_OF_MONTH);
    }

    public List<Match> getAllMatchesCurrentWeek()    {
        List<Match> matches = new ArrayList<>();
        String selectQuery = "SELECT * FROM matchDB WHERE date_number > "+getWeekStartDate()+" AND date_number <= "+getWeekEndDate()+" ORDER BY date_number";
        Cursor cursor = database.rawQuery(selectQuery, null);
        matches = createMatchList(cursor);
        return matches;
    }


    public List<Match> getAllMatchesForClub(long clubID) {
        List<Match> matches = new ArrayList<>();
        String selectQuery = "SELECT * FROM matchDB where homeClub = "+ clubID+" OR awayClub = "+ clubID+"";
        Cursor cursor = database.rawQuery(selectQuery, null);
        matches = createMatchList(cursor);
        return matches;
    }

    public List<Match> getAllMatchesForClubTillDate(long clubID) {
        List<Match> matches = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int currentDate = calendar.get(Calendar.YEAR)*10000+(calendar.get(Calendar.MONTH)+1)*100+calendar.get(Calendar.DAY_OF_MONTH);
        String selectQuery = "SELECT * FROM matchDB where (homeClub = "+ clubID+" OR awayClub = "+ clubID+") AND date_number <= "+currentDate+" AND type < "+3+" ORDER BY date_number";
        Cursor cursor = database.rawQuery(selectQuery, null);
        matches = createMatchList(cursor);
        return matches;
    }

    public List<Match> getAllMatchesForClubCurrentWeek(long clubID) {
        List<Match> matches = new ArrayList<>();
        String selectQuery = "SELECT * FROM matchDB where (homeClub = "+ clubID+" OR awayClub = "+ clubID+") AND (date_number > "+getWeekStartDate()+" AND date_number <= "+getWeekEndDate()+")";
        Cursor cursor = database.rawQuery(selectQuery, null);
        matches = createMatchList(cursor);
        return matches;
    }



    public Match getMatch(long matchID)    {
        Match m = new Match() ;

        String selectQuery = "SELECT * FROM matchDB where _ID = "+matchID+"";
        Cursor cursor = database.rawQuery(selectQuery, null);
        Log.d(tzlcDataSource.class.getSimpleName(), "Cursor Size : " + cursor.getCount());

        try{
            while(cursor.moveToNext())
            {
                m  = new Match(
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.MatchDB.COLUMN_DATE_NUMBER)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.MatchDB.COLUMN_HOME_CLUB)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.MatchDB.COLUMN_AWAY_CLUB)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.MatchDB.COLUMN_TYPE)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.MatchDB.COLUMN_SUBTYPE)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.MatchDB.COLUMN_RESULT))
                        //getInt
                );
                m.setId(cursor.getLong((cursor.getColumnIndex(tzlcDBContract.MatchDB._ID))));
                Log.d(tzlcDataSource.class.getSimpleName(), "Match  Fetched " +m.getDate_number()+"," +m.getHomeClubID()+","+m.getAwayClubID()+","+m.getType()+","+m.getSubtype()+","+m.getResult());

            }
        }
        finally {
            if(cursor != null && !cursor.isClosed())
                cursor.close();
        }

        return m;
    }

    public boolean isMatchHappened(long matchID)   {
        Match m = getMatch(matchID);
        Calendar c = Calendar.getInstance();
        int d=0;

        d=c.get(Calendar.YEAR)*100+(c.get(Calendar.MONTH)+1);
        d=d*100+c.get(Calendar.DAY_OF_MONTH);
        if (d>m.getDate_number())
            return  true;
        else
            return false;
    }


    //PLAYER
    public ContentValues createContentForPlayer(Player p) {
        ContentValues value = new ContentValues();
        value.put(tzlcDBContract.PlayerDB.COLUMN_PLAYER_NAME,p.getPlayerName());
        value.put(tzlcDBContract.PlayerDB.COLUMN_CLUB,p.getClubId());
        value.put(tzlcDBContract.PlayerDB.COLUMN_CURRENT_VALUE,p.getCurrentValue());
        value.put(tzlcDBContract.PlayerDB.COLUMN_ORGANIZATION,p.getOrgID());
        value.put(tzlcDBContract.PlayerDB.COLUMN_SENIALWOMBATS,p.getSenialwombat());
        value.put(tzlcDBContract.PlayerDB.COLUMN_ABSENT,p.getAbsent());
        value.put(tzlcDBContract.PlayerDB.COLUMN_POSITION,p.getPosition());

        return value;
    }

    public void addPlayer(Player player) {
        ContentValues value = createContentForPlayer(player);
        long rowID =  database.insert(tzlcDBContract.PlayerDB.TABLE_NAME,null, value);
        Log.d(tzlcDataSource.class.getSimpleName(), "Player added " + rowID);
    }

    public void updatePlayer(Player player)    {
        ContentValues value = createContentForPlayer(player);
        String selection = tzlcDBContract.PlayerDB._ID + " = ?";
        String[] selectionargs = {String.valueOf(player.getId())};
        int count = database.update(tzlcDBContract.PlayerDB.TABLE_NAME,value,selection,selectionargs);
        Log.d(tzlcDataSource.class.getSimpleName(), "Player record updated " + count);
    }

    public void deletePlayer(long playerID)    {
        String selection = tzlcDBContract.PlayerDB._ID + " = ?";
        String[] selectionargs = {String.valueOf(playerID)};
        int count = database.delete(tzlcDBContract.PlayerDB.TABLE_NAME,selection,selectionargs);
        Log.d(tzlcDataSource.class.getSimpleName(), "Player deleted " + count);
    }

    public List<Player> createPlayerList(Cursor cursor)    {
        List<Player> players = new ArrayList<>();

        try{
            while(cursor.moveToNext())
            {
                Player p  = new Player(
                        cursor.getString(cursor.getColumnIndex(tzlcDBContract.PlayerDB.COLUMN_PLAYER_NAME)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.PlayerDB.COLUMN_CLUB)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.PlayerDB.COLUMN_CURRENT_VALUE))
                );
                p.setId(cursor.getLong((cursor.getColumnIndex(tzlcDBContract.PlayerDB._ID))));
                p.setOrgID((cursor.getLong((cursor.getColumnIndex(tzlcDBContract.PlayerDB.COLUMN_ORGANIZATION)))));
                p.setSenialwombat((cursor.getInt((cursor.getColumnIndex(tzlcDBContract.PlayerDB.COLUMN_SENIALWOMBATS)))));
                p.setAbsent((cursor.getInt((cursor.getColumnIndex(tzlcDBContract.PlayerDB.COLUMN_ABSENT)))));
                p.setPosition((cursor.getInt((cursor.getColumnIndex(tzlcDBContract.PlayerDB.COLUMN_POSITION)))));
                Log.d(tzlcDataSource.class.getSimpleName(), "Player  Fetched " +p.getPlayerName()+"," +p.getClubId()+","+p.getCurrentValue());
                players.add(p);
            }
        }
        finally {
            if(cursor != null && !cursor.isClosed())
                cursor.close();
        }

        return players;
    }

    public List<Player> getAllPlayers()    {
        List<Player> players = new ArrayList<>();
        String selectQuery = "SELECT * FROM playerDB ORDER BY playerName ASC";
        Cursor cursor = database.rawQuery(selectQuery, null);
        players = createPlayerList(cursor);
        return players;
    }

    public List<String> getAllPlayerNames()    {
        List<String> playerNames = new ArrayList<>();
        List<Player> allPlayers =  getAllPlayers();
        for (Player player : allPlayers) {
            //playerNames.add(player.getPlayerName());
            playerNames.add(player.getPlayerName().split("@")[0].substring(0,2)+". "+player.getPlayerName().split("@")[1]);

        }
        return playerNames;
    }

    public List<Player> getAllPlayersForClub(long clubID)    {
        List<Player> players = new ArrayList<>();
        String selectQuery;

        if (isSenialWombat(clubID))
            selectQuery = "SELECT * FROM playerDB WHERE sw = 1  ORDER BY playerName ASC";
        else {
            if (isOrgnization(clubID))
                selectQuery = "SELECT * FROM playerDB WHERE org = " + clubID + " ORDER BY playerName ASC";
            else
                selectQuery = "SELECT * FROM playerDB WHERE club = " + clubID + " ORDER BY playerName ASC";
        }

        Cursor cursor = database.rawQuery(selectQuery, null);
        players = createPlayerList(cursor);
        return players;
    }

    public List<String> getAllPlayerNamesForClub(long clubID)    {
        List<String> playerNames = new ArrayList<>();
        List<Player> allPlayers =  getAllPlayersForClub(clubID);
        for (Player player : allPlayers) {
            playerNames.add(player.getPlayerName().split("@")[0].substring(0,2)+". "+player.getPlayerName().split("@")[1]);
        }
        return playerNames;
    }

    public List<String> getAllPlayerNamesForClub(long clubID,long matchId)    {
        List<String> playerNames = new ArrayList<>();
        List<Player> allPlayers =  getAllPlayersForClub(clubID);
        for (Player player : allPlayers) {
            playerNames.add(player.getPlayerName().split("@")[0].substring(0,2)+". "+player.getPlayerName().split("@")[1]);
        }

        List<Loan> loans = getAllLoansForClubForMatch(clubID,matchId);
        for (Loan loan : loans) {
            playerNames.add(getPlayer(loan.getLoanPlayerID()).getPlayerName().split("@")[0].substring(0,2)+". "+getPlayer(loan.getLoanPlayerID()).getPlayerName().split("@")[1]);
        }

        return playerNames;
    }

    public List<Player> getAllMatchPlayers(long clubID,long matchId)    {
        List<Player> matchPlayers = new ArrayList<>();
        List<Player> clubPlayers =  getAllPlayersForClub(clubID);
        List<Player> loanPlayers = new ArrayList<>();
        List<Loan> loans = getAllLoansForClubForMatch(clubID,matchId);
        for (Loan loan : loans) {
            Player p = new Player();
            p.setId(loan.getLoanPlayerID());
            p.setClubId(loan.getHomeClubID());
            loanPlayers.add(p);
        }

        for (Player clubPlayer : clubPlayers) { matchPlayers.add(clubPlayer); }
        for (Player loanPlayer : loanPlayers) { matchPlayers.add(loanPlayer); }

        return matchPlayers;
    }

    public Player getPlayer(long playerID)    {
        Player player = new Player();
        String selectQuery = "SELECT * FROM playerDB where _ID = "+playerID+"";
        Cursor cursor = database.rawQuery(selectQuery, null);
        Log.d(tzlcDataSource.class.getSimpleName(), "Cursor Size : " + cursor.getCount());

        try{
            while(cursor.moveToNext())
            {
                player.setPlayerName(cursor.getString(cursor.getColumnIndex(tzlcDBContract.PlayerDB.COLUMN_PLAYER_NAME)));
                player.setClubId(cursor.getLong(cursor.getColumnIndex(tzlcDBContract.PlayerDB.COLUMN_CLUB)));
                player.setCurrentValue(cursor.getInt(cursor.getColumnIndex(tzlcDBContract.PlayerDB.COLUMN_CURRENT_VALUE)));
                player.setOrgID(cursor.getLong(cursor.getColumnIndex(tzlcDBContract.PlayerDB.COLUMN_ORGANIZATION)));
                player.setSenialwombat(cursor.getInt(cursor.getColumnIndex(tzlcDBContract.PlayerDB.COLUMN_SENIALWOMBATS)));
                player.setAbsent(cursor.getInt(cursor.getColumnIndex(tzlcDBContract.PlayerDB.COLUMN_ABSENT)));
                player.setPosition(cursor.getInt(cursor.getColumnIndex(tzlcDBContract.PlayerDB.COLUMN_POSITION)));
                player.setId(cursor.getLong((cursor.getColumnIndex(tzlcDBContract.PlayerDB._ID))));

                Log.d(tzlcDataSource.class.getSimpleName(), "Data Fetched " + player.getPlayerName()+","+player.getClubId()+","+player.getCurrentValue());
            }
        }
        finally {
            if(cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return player;
    }

    public long getPlayerID(String playerName)    {

        long playerID=-1;

        String temp = playerName.substring(0,2)+"%"+playerName.split(". ")[1];
        String selectQuery = "SELECT "+tzlcDBContract.PlayerDB._ID+" FROM playerDB where playerName LIKE '"+temp+"'";
        Cursor cursor = database.rawQuery(selectQuery, null);
        Log.d(tzlcDataSource.class.getSimpleName(), "Cursor Size : " + cursor.getCount());
        try{
            while (cursor.moveToNext()) {
                Log.d(tzlcDataSource.class.getSimpleName(), "Player ID : " + cursor.getLong(cursor.getColumnIndex(tzlcDBContract.PlayerDB._ID)));
                playerID = cursor.getLong(cursor.getColumnIndex(tzlcDBContract.PlayerDB._ID));
            }
        }
        finally {
            if(cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return playerID;
    }

    public long getPlayerID(String playerName, long clubID)    {

        long playerID=-1;

        String temp = playerName.substring(0,2)+"%"+playerName.split(". ")[1];
        String selectQuery = "SELECT "+tzlcDBContract.PlayerDB._ID+" FROM playerDB where playerName LIKE '"+temp+"'" + "AND club = " +clubID+"";
        Cursor cursor = database.rawQuery(selectQuery, null);
        Log.d(tzlcDataSource.class.getSimpleName(), "Cursor Size : " + cursor.getCount());

        try{

            while(cursor.moveToNext()){
                Log.d(tzlcDataSource.class.getSimpleName(), "Player ID : " + cursor.getLong(cursor.getColumnIndex(tzlcDBContract.PlayerDB._ID)));
                playerID =cursor.getLong(cursor.getColumnIndex(tzlcDBContract.PlayerDB._ID));
            }
        }
        finally {
            if(cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return playerID;
    }






    //MOs

    public ContentValues createContentForMO(MatchOffcial matchOffcial){
        ContentValues value = new ContentValues();
        value.put(tzlcDBContract.MatchOffcialDB.COLUMN_PLAYER_ID,matchOffcial.getPlayerId());
        value.put(tzlcDBContract.MatchOffcialDB.COLUMN_MATCH_ID,matchOffcial.getMatchId());
        value.put(tzlcDBContract.MatchOffcialDB.COLUMN_JOB,matchOffcial.getJob());
        value.put(tzlcDBContract.MatchOffcialDB.COLUMN_ON_TIME,matchOffcial.getOnTime());
        value.put(tzlcDBContract.MatchOffcialDB.COLUMN_CLUB_ID,matchOffcial.getClubID());
        value.put(tzlcDBContract.MatchOffcialDB.COLUMN_MO_TIME,matchOffcial.getMoTime());
        return value;

    }

    public void addMatchOffcial(MatchOffcial mo) {
        ContentValues value = createContentForMO(mo);

        long rowID =  database.insert(tzlcDBContract.MatchOffcialDB.TABLE_NAME,null, value);
        Log.d(tzlcDataSource.class.getSimpleName(), "MO added " + rowID);
    }

    public List<MatchOffcial> createMOList(Cursor cursor)     {
        List<MatchOffcial> mos = new ArrayList<>();

        try{
            while(cursor.moveToNext())
            {
                MatchOffcial mo  = new MatchOffcial(
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.MatchOffcialDB.COLUMN_PLAYER_ID)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.MatchOffcialDB.COLUMN_MATCH_ID)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.MatchOffcialDB.COLUMN_JOB)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.MatchOffcialDB.COLUMN_ON_TIME))
                );
                mo.setMoTime(cursor.getInt(cursor.getColumnIndex(tzlcDBContract.MatchOffcialDB.COLUMN_MO_TIME)));
                mo.setClubID(cursor.getLong(cursor.getColumnIndex(tzlcDBContract.MatchOffcialDB.COLUMN_CLUB_ID)));
                mo.setId(cursor.getLong((cursor.getColumnIndex(tzlcDBContract.MatchOffcialDB._ID))));
                Log.d(tzlcDataSource.class.getSimpleName(), "MO  Fetched " +mo.getPlayerId()+"," +mo.getMatchId()+","+mo.getJob()+","+mo.getOnTime());
                mos.add(mo);
            }
        }
        finally {
            if(cursor != null && !cursor.isClosed())
                cursor.close();
        }

        return mos;
    }

    public List<MatchOffcial> getAllMOsForMatch(long matchID)    {
        List<MatchOffcial> mos = new ArrayList<>();
        String selectQuery = "SELECT * FROM moDB WHERE matchID = "+ matchID+ "";

        Cursor cursor = database.rawQuery(selectQuery, null);
        try{
            while(cursor.moveToNext())
            {
                MatchOffcial mo  = new MatchOffcial(
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.MatchOffcialDB.COLUMN_PLAYER_ID)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.MatchOffcialDB.COLUMN_MATCH_ID)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.MatchOffcialDB.COLUMN_JOB)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.MatchOffcialDB.COLUMN_ON_TIME))
                );
                mo.setMoTime(cursor.getInt(cursor.getColumnIndex(tzlcDBContract.MatchOffcialDB.COLUMN_MO_TIME)));
                mo.setClubID(cursor.getLong(cursor.getColumnIndex(tzlcDBContract.MatchOffcialDB.COLUMN_CLUB_ID)));
                mo.setId(cursor.getLong((cursor.getColumnIndex(tzlcDBContract.MatchOffcialDB._ID))));
                Log.d(tzlcDataSource.class.getSimpleName(), "MO  Fetched " +mo.getPlayerId()+"," +mo.getMatchId()+","+mo.getJob()+","+mo.getOnTime());
                mos.add(mo);
            }
        }
        finally {
            if(cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return mos;
    }

    public HashMap<Long,Integer> getAllMOsForClubID(long clubID)    {
        HashMap<Long,Integer> list = new HashMap<Long, Integer>();

        String selectQuery = "SELECT playerid, count(playerid) as TotalMOs FROM moDB WHERE clubID = "+ clubID+ " group by playerid";
        Cursor cursor = database.rawQuery(selectQuery, null);
        try{
            while(cursor.moveToNext())
                list.put(cursor.getLong(cursor.getColumnIndex(tzlcDBContract.MatchOffcialDB.COLUMN_PLAYER_ID)),cursor.getInt(cursor.getColumnIndex("TotalMOs")));
        }
        finally {
            if(cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return list;
    }

    public List<MatchOffcial> getAllMOsForClub(long clubID)    {
        List<MatchOffcial> mos = new ArrayList<>();
        String selectQuery = "SELECT * FROM moDB WHERE clubid = "+ clubID+ "";
        Cursor cursor = database.rawQuery(selectQuery, null);
        mos= createMOList(cursor);
        return mos;
    }

    public MatchOffcial getMatchOffcial(long moID){
        MatchOffcial matchOffcial = new MatchOffcial();
        String selectQuery = "SELECT * FROM moDB WHERE _ID = "+ moID + "";

        Cursor cursor = database.rawQuery(selectQuery, null);
        try{
            while(cursor.moveToNext())
            {
                matchOffcial  = new MatchOffcial(
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.MatchOffcialDB.COLUMN_PLAYER_ID)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.MatchOffcialDB.COLUMN_MATCH_ID)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.MatchOffcialDB.COLUMN_JOB)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.MatchOffcialDB.COLUMN_ON_TIME))
                );
                matchOffcial.setMoTime(cursor.getInt(cursor.getColumnIndex(tzlcDBContract.MatchOffcialDB.COLUMN_MO_TIME)));
                matchOffcial.setClubID(cursor.getLong(cursor.getColumnIndex(tzlcDBContract.MatchOffcialDB.COLUMN_CLUB_ID)));
                matchOffcial.setId(cursor.getLong((cursor.getColumnIndex(tzlcDBContract.MatchOffcialDB._ID))));
            }
        }
        finally {
            if(cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return matchOffcial;
    }

    public MatchOffcial getMatchOffcial(long playerID, long matchID){

        MatchOffcial matchOffcial = new MatchOffcial();
        String selectQuery = "SELECT * FROM moDB WHERE playerid = "+playerID+" and matchid = "+matchID + "";
        Cursor cursor = database.rawQuery(selectQuery, null);
        try{
            while(cursor.moveToNext())
            {
                matchOffcial  = new MatchOffcial(
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.MatchOffcialDB.COLUMN_PLAYER_ID)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.MatchOffcialDB.COLUMN_MATCH_ID)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.MatchOffcialDB.COLUMN_JOB)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.MatchOffcialDB.COLUMN_ON_TIME))
                );
                matchOffcial.setMoTime(cursor.getInt(cursor.getColumnIndex(tzlcDBContract.MatchOffcialDB.COLUMN_MO_TIME)));
                matchOffcial.setClubID(cursor.getLong(cursor.getColumnIndex(tzlcDBContract.MatchOffcialDB.COLUMN_CLUB_ID)));
                matchOffcial.setId(cursor.getLong((cursor.getColumnIndex(tzlcDBContract.MatchOffcialDB._ID))));
            }
        }
        finally {
            if(cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return matchOffcial;

    }

    public List<MatchOffcial> getAllMOsForPlayer(long playerID)    {
        List<MatchOffcial> mos = new ArrayList<>();
        String selectQuery = "SELECT * FROM moDB WHERE playerID = "+ playerID+ "";
        Cursor cursor = database.rawQuery(selectQuery, null);
        mos= createMOList(cursor);
        return mos;
    }

    public List<MatchOffcial> getAllMOForPlayerForMatch(long playerID, long matchID)    {
        List<MatchOffcial> mos = new ArrayList<>();
        String selectQuery = "SELECT * FROM moDB WHERE playerID = "+ playerID+ "";
        Cursor cursor = database.rawQuery(selectQuery, null);
        mos= createMOList(cursor);
        return mos;
    }

    public void updateMatchOffcial(MatchOffcial mo)    {

        ContentValues value = createContentForMO(mo);
        String selection = tzlcDBContract.MatchOffcialDB._ID + " = ?";
        String[] selectionargs = {String.valueOf(mo.getId())};
        int count = database.update(tzlcDBContract.MatchOffcialDB.TABLE_NAME,value,selection,selectionargs);
        Log.d(tzlcDataSource.class.getSimpleName(), "MO updated " + count);
    }

    public void deleteMatchOffcial(long moID)    {
        String selection = tzlcDBContract.MatchOffcialDB._ID + " = ?";
        String[] selectionargs = {String.valueOf(moID)};
        int count = database.delete(tzlcDBContract.MatchOffcialDB.TABLE_NAME,selection,selectionargs);
        Log.d(tzlcDataSource.class.getSimpleName(), "MO deleted " + count);
    }






    //LOAN

    public ContentValues createContentForLoan(Loan loan){
        ContentValues value = new ContentValues();
        value.put(tzlcDBContract.LoanDB.COLUMN_MATCH_ID,loan.getMatchID());
        value.put(tzlcDBContract.LoanDB.COLUMN_HOME_CLUB_ID,loan.getHomeClubID());
        value.put(tzlcDBContract.LoanDB.COLUMN_MISSING_PLAYER_ID,loan.getMissingPlayerID());
        value.put(tzlcDBContract.LoanDB.COLUMN_LOAN_CLUB_ID,loan.getLoanClubID());
        value.put(tzlcDBContract.LoanDB.COLUMN_LOAN_PLAYER_ID,loan.getLoanPlayerID());
        value.put(tzlcDBContract.LoanDB.COLUMN_RULE,loan.getRule());
        value.put(tzlcDBContract.LoanDB.COLUMN_TYPE,loan.getType());
        return value;
    }

    public void addLoan(Loan loan) {
        ContentValues value = createContentForLoan(loan);
        long rowID =  database.insert(tzlcDBContract.LoanDB.TABLE_NAME,null, value);
        Log.d(tzlcDataSource.class.getSimpleName(), "Loan added : " + rowID);
    }

    public void updateLoan(Loan loan)    {
        ContentValues value = createContentForLoan(loan);
        String selection = tzlcDBContract.LoanDB._ID + " = ?";
        String[] selectionargs = {String.valueOf(loan.getId())};
        int count = database.update(tzlcDBContract.LoanDB.TABLE_NAME,value,selection,selectionargs);
        Log.d(tzlcDataSource.class.getSimpleName(), "Loan record updated " + count);
    }

    public void deleteLoan(long loanID)    {
        String selection = tzlcDBContract.LoanDB._ID + " = ?";
        String[] selectionargs = {String.valueOf(loanID)};
        int count = database.delete(tzlcDBContract.LoanDB.TABLE_NAME,selection,selectionargs);
        Log.d(tzlcDataSource.class.getSimpleName(), "Loan deleted " + count);
    }

    public Loan getLoan(long loanID){
        Loan loan = new Loan();
        String selectQuery = "SELECT * FROM loanDB WHERE _ID = "+ loanID+ "";
        Cursor cursor = database.rawQuery(selectQuery, null);
        try{
            while(cursor.moveToNext())
            {
                loan  = new Loan(
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.LoanDB.COLUMN_MATCH_ID)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.LoanDB.COLUMN_HOME_CLUB_ID)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.LoanDB.COLUMN_MISSING_PLAYER_ID)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.LoanDB.COLUMN_LOAN_CLUB_ID)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.LoanDB.COLUMN_LOAN_PLAYER_ID)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.LoanDB.COLUMN_RULE)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.LoanDB.COLUMN_TYPE))
                );
                loan.setId(cursor.getLong((cursor.getColumnIndex(tzlcDBContract.LoanDB._ID))));
                Log.d(tzlcDataSource.class.getSimpleName(), "Loan  Fetched " +loan.getMatchID()+"," +loan.getMatchID()+","+loan.getHomeClubID()+","+loan.getMissingPlayerID()+","+loan.getLoanClubID()+","+loan.getLoanPlayerID()+","+loan.getRule()+","+loan.getType());

            }
        }
        finally {
            if(cursor != null && !cursor.isClosed())
                cursor.close();
        }

        return loan;
    }

    public List<Loan> createLoanList(Cursor cursor)    {
        List<Loan> loans = new ArrayList<>();

        try{
            while(cursor.moveToNext())
            {
                Loan loan  = new Loan(
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.LoanDB.COLUMN_MATCH_ID)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.LoanDB.COLUMN_HOME_CLUB_ID)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.LoanDB.COLUMN_MISSING_PLAYER_ID)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.LoanDB.COLUMN_LOAN_CLUB_ID)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.LoanDB.COLUMN_LOAN_PLAYER_ID)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.LoanDB.COLUMN_RULE)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.LoanDB.COLUMN_TYPE))
                );
                loan.setId(cursor.getLong((cursor.getColumnIndex(tzlcDBContract.LoanDB._ID))));
                Log.d(tzlcDataSource.class.getSimpleName(), "Loan  Fetched " +loan.getMatchID()+"," +loan.getMatchID()+","+loan.getHomeClubID()+","+loan.getMissingPlayerID()+","+loan.getLoanClubID()+","+loan.getLoanPlayerID()+","+loan.getRule()+","+loan.getType());
                loans.add(loan);
            }
        }
        finally {
            if(cursor != null && !cursor.isClosed())
                cursor.close();
        }

        return loans;
    }

    public List<Loan> getAllLoansForClub(long clubID)    {
        List<Loan> loans = new ArrayList<>();
        String selectQuery = "SELECT * FROM loanDB WHERE homeClub = "+ clubID + "";
        Cursor cursor = database.rawQuery(selectQuery, null);
        loans = createLoanList(cursor);
        return loans;
    }

    public List<Loan> getAllLoansForPlayer(long playerID)    {
        List<Loan> loans = new ArrayList<>();
        String selectQuery = "SELECT * FROM loanDB WHERE loanPlayer = "+ playerID + "";
        Cursor cursor = database.rawQuery(selectQuery, null);
        loans = createLoanList(cursor);
        return loans;
    }

    public List<Loan> getAllLoansForMatch(long matchID)    {
        List<Loan> loans = new ArrayList<>();
        String selectQuery = "SELECT * FROM loanDB WHERE matchID = "+ matchID+ "";
        Cursor cursor = database.rawQuery(selectQuery, null);
        loans = createLoanList(cursor);
        return loans;
    }

    public List<Loan> getAllLoansForClubForMatch(long clubID, long matchId)    {
        List<Loan> loans = new ArrayList<>();
        String selectQuery = "SELECT * FROM loanDB WHERE homeClub = "+ clubID + " AND " + "matchID = "+ matchId+ "";
        Cursor cursor = database.rawQuery(selectQuery, null);
        loans = createLoanList(cursor);
        return loans;
    }

    public HashMap<Long,Integer> getAllLoansForClubID(long clubID)    {
        HashMap<Long,Integer> list = new HashMap<Long, Integer>();

        String selectQuery = "SELECT loanPlayer, count(loanPlayer) as TotalLoans FROM loanDB WHERE loanClub = "+ clubID+ " group by loanPlayer";
        Cursor cursor = database.rawQuery(selectQuery, null);
        try{
            while(cursor.moveToNext())
            {
                list.put(cursor.getLong(cursor.getColumnIndex(tzlcDBContract.LoanDB.COLUMN_LOAN_PLAYER_ID)),cursor.getInt(cursor.getColumnIndex("TotalLoans")));
            }
        }
        finally {
            if(cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return list;
    }


    //GOAL
    public ContentValues createContentForGoal(Goal goal){
        ContentValues value = new ContentValues();
        value.put(tzlcDBContract.GoalDB.COLUMN_MATCH_ID,goal.getMatchID());
        value.put(tzlcDBContract.GoalDB.COLUMN_PLAYER_ID,goal.getPlayerID());
        value.put(tzlcDBContract.GoalDB.COLUMN_ASSIST_PLAYER_ID,goal.getAssistPlayerID());
        value.put(tzlcDBContract.GoalDB.COLUMN_CLUB_ID,goal.getAgainstClubID());
        value.put(tzlcDBContract.GoalDB.COLUMN_MATCHTIME,goal.getMatchTime());
        value.put(tzlcDBContract.GoalDB.COLUMN_VCMTIME,goal.getVcmtime());
        value.put(tzlcDBContract.GoalDB.COLUMN_OWNGOAL,goal.getOwnGoal());
        return value;
    }

    public long addGoal(Goal goal) {
        ContentValues value = createContentForGoal(goal);
        long rowID =  database.insert(tzlcDBContract.GoalDB.TABLE_NAME,null, value);
        Log.d(tzlcDataSource.class.getSimpleName(), "Goal added : " + rowID);
        return rowID;
    }

    public List<Goal> createGoalList(Cursor cursor)  {
        List<Goal> goals = new ArrayList<>();
        try{
            while(cursor.moveToNext())
            {
                Goal goal  = new Goal(
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.GoalDB.COLUMN_MATCH_ID)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.GoalDB.COLUMN_PLAYER_ID)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.GoalDB.COLUMN_ASSIST_PLAYER_ID)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.GoalDB.COLUMN_CLUB_ID)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.GoalDB.COLUMN_MATCHTIME)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.GoalDB.COLUMN_VCMTIME))
                );
                goal.setOwnGoal(cursor.getInt(cursor.getColumnIndex(tzlcDBContract.GoalDB.COLUMN_OWNGOAL)));
                goal.setId(cursor.getLong((cursor.getColumnIndex(tzlcDBContract.GoalDB._ID))));
                goals.add(goal);
            }
        }
        finally {
            if(cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return goals;

    }

    public List<Goal> getAllGoalsForMatch(long matchID)    {
        List<Goal> goals = new ArrayList<>();
        String selectQuery = "SELECT * FROM goalDB WHERE matchID = "+ matchID+ "";

        Cursor cursor = database.rawQuery(selectQuery, null);
        goals = createGoalList(cursor);
        return goals;
    }

    public Goal getGoal(long goalID)    {
        Goal goal = new Goal();
        String selectQuery = "SELECT * FROM goalDB WHERE _ID = "+ goalID+ "";

        Cursor cursor = database.rawQuery(selectQuery, null);
        try{
            while(cursor.moveToNext())
            {
                goal  = new Goal(
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.GoalDB.COLUMN_MATCH_ID)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.GoalDB.COLUMN_PLAYER_ID)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.GoalDB.COLUMN_ASSIST_PLAYER_ID)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.GoalDB.COLUMN_CLUB_ID)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.GoalDB.COLUMN_MATCHTIME)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.GoalDB.COLUMN_VCMTIME))
                );
                goal.setOwnGoal(cursor.getInt(cursor.getColumnIndex(tzlcDBContract.GoalDB.COLUMN_OWNGOAL)));
                goal.setId(cursor.getLong((cursor.getColumnIndex(tzlcDBContract.GoalDB._ID))));
            }
        }
        finally {
            if(cursor != null && !cursor.isClosed())
                cursor.close();
        }

        return goal;
    }

    public HashMap<Long,Integer> getAllGoalsForClubID(long clubID)    {
        HashMap<Long,Integer> list = new HashMap<Long, Integer>();
        String selectQuery = "SELECT playerid, count(playerid) AS TotalGoals FROM goalDB WHERE clubID = "+ clubID+ " GROUP BY playerid";
        Cursor cursor = database.rawQuery(selectQuery, null);
        try{
            while(cursor.moveToNext())
            {
                list.put(cursor.getLong(cursor.getColumnIndex(tzlcDBContract.GoalDB.COLUMN_PLAYER_ID)),cursor.getInt(cursor.getColumnIndex("TotalGoals")));
            }
        }
        finally {
            if(cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return list;
    }


    public HashMap<Long,Integer> getGoalsReport()    {
        HashMap<Long,Integer> list = new HashMap<Long, Integer>();
        String selectQuery = "SELECT playerid, count(playerid) AS TotalGoals FROM goalDB GROUP BY playerid ORDER BY TotalGoals";
        Cursor cursor = database.rawQuery(selectQuery, null);
        try{
            while(cursor.moveToNext())
            {
                list.put(cursor.getLong(cursor.getColumnIndex(tzlcDBContract.GoalDB.COLUMN_PLAYER_ID)),cursor.getInt(cursor.getColumnIndex("TotalGoals")));
            }
        }
        finally {
            if(cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return list;
    }

    public HashMap<Long,Integer> getGoalAssistReport()    {
        HashMap<Long,Integer> list = new HashMap<Long, Integer>();

        String selectQuery = "SELECT assistPlayerID, count(assistPlayerID) AS TotalAssist FROM goalDB GROUP BY assistPlayerID ORDER BY TotalAssist";
        Cursor cursor = database.rawQuery(selectQuery, null);
        try{
            while(cursor.moveToNext())
            {
                if (cursor.getLong(cursor.getColumnIndex(tzlcDBContract.GoalDB.COLUMN_ASSIST_PLAYER_ID)) != 0 )
                    list.put(cursor.getLong(cursor.getColumnIndex(tzlcDBContract.GoalDB.COLUMN_ASSIST_PLAYER_ID)),cursor.getInt(cursor.getColumnIndex("TotalAssist")));
            }
        }
        finally {
            if(cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return list;
    }

    public List<Goal> getAllGoalsForClub(long clubID)    {
        List<Goal> goals = new ArrayList<>();
        String selectQuery = "SELECT * FROM goalDB WHERE clubID = "+ clubID+ "";
        Cursor cursor = database.rawQuery(selectQuery, null);
        goals = createGoalList(cursor);
        return goals;
    }

    public List<Goal> getAllGoalsForPlayer(long playerID)    {
        List<Goal> goals = new ArrayList<>();
        String selectQuery = "SELECT * FROM goalDB WHERE playerID = "+ playerID+ "";
        Cursor cursor = database.rawQuery(selectQuery, null);
        goals = createGoalList(cursor);
        return goals;
    }

    public void deleteGoal(long goalID)    {
        String selection = tzlcDBContract.GoalDB._ID + " = ?";
        String[] selectionargs = {String.valueOf(goalID)};
        int count = database.delete(tzlcDBContract.GoalDB.TABLE_NAME,selection,selectionargs);
        Log.d(tzlcDataSource.class.getSimpleName(), "Goal deleted " + count);
    }

    public void updateGoal(Goal goal)    {
        ContentValues value = createContentForGoal(goal);
        String selection = tzlcDBContract.GoalDB._ID + " = ?";
        String[] selectionargs = {String.valueOf(goal.getId())};
        int count = database.update(tzlcDBContract.GoalDB.TABLE_NAME,value,selection,selectionargs);
        Log.d(tzlcDataSource.class.getSimpleName(), "Goal record updated " + count);
    }





    //CARD
    public ContentValues createContentForCard(Card card){
        ContentValues value = new ContentValues();
        value.put(tzlcDBContract.CardDB.COLUMN_MATCH_ID,card.getMatchID());
        value.put(tzlcDBContract.CardDB.COLUMN_PLAYER_ID,card.getPlayerID());
        value.put(tzlcDBContract.CardDB.COLUMN_TYPE,card.getType());
        value.put(tzlcDBContract.CardDB.COLUMN_TIME,card.getTime());
        value.put(tzlcDBContract.CardDB.COLUMN_REASON,card.getReason());
        value.put(tzlcDBContract.CardDB.COLUMN_CLUB_ID,card.getClubID());

        return  value;
    }

    public long addCard(Card card) {
        ContentValues value = createContentForCard(card);
        long rowID =  database.insert(tzlcDBContract.CardDB.TABLE_NAME,null, value);
        Log.d(tzlcDataSource.class.getSimpleName(), "Card added : " + rowID);
        return  rowID;
    }

    public void updateCard(Card card)    {
        ContentValues value = createContentForCard(card);
        String selection = tzlcDBContract.CardDB._ID + " = ?";
        String[] selectionargs = {String.valueOf(card.getId())};
        int count = database.update(tzlcDBContract.CardDB.TABLE_NAME,value,selection,selectionargs);
        Log.d(tzlcDataSource.class.getSimpleName(), "Card record updated " + count);
    }

    public void deleteCard(long cardID)    {
        String selection = tzlcDBContract.CardDB._ID + " = ?";
        String[] selectionargs = {String.valueOf(cardID)};
        int count = database.delete(tzlcDBContract.CardDB.TABLE_NAME,selection,selectionargs);
        Log.d(tzlcDataSource.class.getSimpleName(), "Card deleted " + count);
    }

    public Card getCard(long cardID){
        Card card = new Card();
        String selectQuery = "SELECT * FROM cardDB WHERE _ID = "+ cardID+ "";
        Cursor cursor = database.rawQuery(selectQuery, null);
        try{
            while(cursor.moveToNext())
            {
                card  = new Card(
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.CardDB.COLUMN_MATCH_ID)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.CardDB.COLUMN_PLAYER_ID)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.CardDB.COLUMN_TYPE)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.CardDB.COLUMN_TIME)),
                        cursor.getString(cursor.getColumnIndex(tzlcDBContract.CardDB.COLUMN_REASON))
                );
                card.setClubID(cursor.getLong(cursor.getColumnIndex(tzlcDBContract.CardDB.COLUMN_CLUB_ID)));
                card.setId(cursor.getLong((cursor.getColumnIndex(tzlcDBContract.CardDB._ID))));
            }
        }
        finally {
            if(cursor != null && !cursor.isClosed())
                cursor.close();
        }

        return card;
    }

    public List<Card> createCardList(Cursor cursor)    {
        List<Card> cards = new ArrayList<Card>();
        try{
            while(cursor.moveToNext())
            {
                Card card  = new Card(
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.CardDB.COLUMN_MATCH_ID)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.CardDB.COLUMN_PLAYER_ID)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.CardDB.COLUMN_TYPE)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.CardDB.COLUMN_TIME)),
                        cursor.getString(cursor.getColumnIndex(tzlcDBContract.CardDB.COLUMN_REASON))
                );
                card.setClubID(cursor.getLong(cursor.getColumnIndex(tzlcDBContract.CardDB.COLUMN_CLUB_ID)));
                card.setId(cursor.getLong((cursor.getColumnIndex(tzlcDBContract.CardDB._ID))));
                Log.d(tzlcDataSource.class.getSimpleName(), "Card  Fetched " +card.getMatchID()+"," +card.getPlayerID()+","+card.getType()+","+card.getTime()+","+card.getReason());
                cards.add(card);
            }
        }
        finally {
            if(cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return cards;
    }

    public List<Card> getAllCardsForMatch(long matchID)    {
        List<Card> cards = new ArrayList<>();
        String selectQuery = "SELECT * FROM cardDB WHERE matchID = "+ matchID+ "";
        Cursor cursor = database.rawQuery(selectQuery, null);
        cards = createCardList(cursor);
        return cards;
    }

    public HashMap<Long,Integer> getAllCardsForClubID(long clubID)    {
        HashMap<Long,Integer> list = new HashMap<Long, Integer>();

        String selectQuery = "SELECT playerid, count(playerid) as TotalCards FROM cardDB WHERE clubID = "+ clubID+ " group by playerid";
        Cursor cursor = database.rawQuery(selectQuery, null);
        try{
            while(cursor.moveToNext())
            {
                list.put(cursor.getLong(cursor.getColumnIndex(tzlcDBContract.CardDB.COLUMN_PLAYER_ID)),cursor.getInt(cursor.getColumnIndex("TotalCards")));
            }
        }
        finally {
            if(cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return list;
    }

    public List<Card> getAllCardsForClub(long clubID)    {
        List<Card> cards = new ArrayList<>();
        String selectQuery = "SELECT * FROM cardDB WHERE clubID = "+ clubID+ "";
        Cursor cursor = database.rawQuery(selectQuery, null);
        cards = createCardList(cursor);
        return cards;
    }

    public List<Card> getAllCardsForPlayer(long playerID)    {
        List<Card> cards = new ArrayList<>();
        String selectQuery = "SELECT * FROM cardDB WHERE playerID = "+ playerID+ "";
        Cursor cursor = database.rawQuery(selectQuery, null);
        cards = createCardList(cursor);
        return cards;
    }





    //STATS


    public ContentValues createContentForStats(Stats stat){
        ContentValues value = new ContentValues();
        value.put(tzlcDBContract.StatDB.COLUMN_MATCH_ID, stat.getMatchID());
        value.put(tzlcDBContract.StatDB.COLUMN_MATCHTIME, stat.getMatchTime());
        value.put(tzlcDBContract.StatDB.COLUMN_HOME_SCORE, stat.getHome_Score());
        value.put(tzlcDBContract.StatDB.COLUMN_HOME_TI, stat.getHome_TI());
        value.put(tzlcDBContract.StatDB.COLUMN_HOME_DFK, stat.getHome_DFK());
        value.put(tzlcDBContract.StatDB.COLUMN_HOME_SONT, stat.getHome_SOnT());
        value.put(tzlcDBContract.StatDB.COLUMN_HOME_SOFFT, stat.getHome_SOffT());
        value.put(tzlcDBContract.StatDB.COLUMN_HOME_GK, stat.getHome_GK());
        value.put(tzlcDBContract.StatDB.COLUMN_HOME_OFF, stat.getHome_OFF());
        value.put(tzlcDBContract.StatDB.COLUMN_HOME_LC, stat.getHome_LC());
        value.put(tzlcDBContract.StatDB.COLUMN_HOME_COR, stat.getHome_COR());
        value.put(tzlcDBContract.StatDB.COLUMN_HOME_TCK, stat.getHome_TCK());
        value.put(tzlcDBContract.StatDB.COLUMN_HOME_POP, stat.getHome_POP());
        value.put(tzlcDBContract.StatDB.COLUMN_HOME_TIME, stat.getHome_TIME());
        value.put(tzlcDBContract.StatDB.COLUMN_HOME_H1, stat.getHome_H1());
        value.put(tzlcDBContract.StatDB.COLUMN_HOME_H2, stat.getHome_H2());
        value.put(tzlcDBContract.StatDB.COLUMN_HOME_H3, stat.getHome_H3());
        value.put(tzlcDBContract.StatDB.COLUMN_HOME_H4, stat.getHome_H4());
        value.put(tzlcDBContract.StatDB.COLUMN_HOME_PASSES, stat.getHome_Passes());
        value.put(tzlcDBContract.StatDB.COLUMN_AWAY_SCORE, stat.getAway_Score());
        value.put(tzlcDBContract.StatDB.COLUMN_AWAY_TI, stat.getAway_TI());
        value.put(tzlcDBContract.StatDB.COLUMN_AWAY_DFK, stat.getAway_DFK());
        value.put(tzlcDBContract.StatDB.COLUMN_AWAY_SONT, stat.getAway_SOnT());
        value.put(tzlcDBContract.StatDB.COLUMN_AWAY_SOFFT, stat.getAway_SOffT());
        value.put(tzlcDBContract.StatDB.COLUMN_AWAY_GK, stat.getAway_GK());
        value.put(tzlcDBContract.StatDB.COLUMN_AWAY_OFF, stat.getAway_OFF());
        value.put(tzlcDBContract.StatDB.COLUMN_AWAY_LC, stat.getAway_LC());
        value.put(tzlcDBContract.StatDB.COLUMN_AWAY_COR, stat.getAway_COR());
        value.put(tzlcDBContract.StatDB.COLUMN_AWAY_TCK, stat.getAway_TCK());
        value.put(tzlcDBContract.StatDB.COLUMN_AWAY_POP, stat.getAway_POP());
        value.put(tzlcDBContract.StatDB.COLUMN_AWAY_TIME, stat.getAway_TIME());
        value.put(tzlcDBContract.StatDB.COLUMN_AWAY_A1, stat.getAway_A1());
        value.put(tzlcDBContract.StatDB.COLUMN_AWAY_A2, stat.getAway_A2());
        value.put(tzlcDBContract.StatDB.COLUMN_AWAY_A3, stat.getAway_A3());
        value.put(tzlcDBContract.StatDB.COLUMN_AWAY_A4, stat.getAway_A4());
        value.put(tzlcDBContract.StatDB.COLUMN_AWAY_PASSES, stat.getAway_Passes());
        return  value;
    }

    public void addStats(Stats stat) {
        ContentValues value = createContentForStats(stat);
        long rowID =  database.insert(tzlcDBContract.StatDB.TABLE_NAME,null, value);
        Log.d(tzlcDataSource.class.getSimpleName(), "Stat added : " + rowID);
    }

    public void updateStats(Stats stats)    {
        ContentValues value = createContentForStats(stats);
        String selection = tzlcDBContract.StatDB._ID + " = ?";
        String[] selectionargs = {String.valueOf(stats.getId())};
        int count = database.update(tzlcDBContract.StatDB.TABLE_NAME,value,selection,selectionargs);
        Log.d(tzlcDataSource.class.getSimpleName(), "Stats record updated " + count);
    }

    public Stats getAllStatsForMatch(long matchID)    {
        //List stats = new ArrayList<>();
        String selectQuery = "SELECT * FROM statDB WHERE matchID = "+ matchID + "";
        Stats stats = new Stats();

        Cursor cursor = database.rawQuery(selectQuery, null);
        try{
            while(cursor.moveToNext())
            {
                Stats stat  = new Stats(
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.StatDB.COLUMN_MATCH_ID)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.StatDB.COLUMN_MATCHTIME)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.StatDB.COLUMN_HOME_SCORE)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.StatDB.COLUMN_HOME_TI)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.StatDB.COLUMN_HOME_DFK)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.StatDB.COLUMN_HOME_SONT)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.StatDB.COLUMN_HOME_SOFFT)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.StatDB.COLUMN_HOME_GK)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.StatDB.COLUMN_HOME_OFF)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.StatDB.COLUMN_HOME_LC)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.StatDB.COLUMN_HOME_COR)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.StatDB.COLUMN_HOME_TCK)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.StatDB.COLUMN_HOME_POP)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.StatDB.COLUMN_HOME_TIME)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.StatDB.COLUMN_HOME_H1)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.StatDB.COLUMN_HOME_H2)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.StatDB.COLUMN_HOME_H3)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.StatDB.COLUMN_HOME_H4)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.StatDB.COLUMN_HOME_PASSES)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.StatDB.COLUMN_AWAY_SCORE)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.StatDB.COLUMN_AWAY_TI)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.StatDB.COLUMN_AWAY_DFK)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.StatDB.COLUMN_AWAY_SONT)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.StatDB.COLUMN_AWAY_SOFFT)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.StatDB.COLUMN_AWAY_GK)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.StatDB.COLUMN_AWAY_OFF)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.StatDB.COLUMN_AWAY_LC)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.StatDB.COLUMN_AWAY_COR)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.StatDB.COLUMN_AWAY_TCK)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.StatDB.COLUMN_AWAY_POP)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.StatDB.COLUMN_AWAY_TIME)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.StatDB.COLUMN_AWAY_A1)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.StatDB.COLUMN_AWAY_A2)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.StatDB.COLUMN_AWAY_A3)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.StatDB.COLUMN_AWAY_A4)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.StatDB.COLUMN_AWAY_PASSES))
                );
                stat.setId(cursor.getLong((cursor.getColumnIndex(tzlcDBContract.StatDB._ID))));
                Log.d(tzlcDataSource.class.getSimpleName(), "Stat  Fetched " +stat.getMatchID());
                //stats.add(stat);
                stats = stat;
            }
        }
        finally {
            if(cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return stats;
    }

    public boolean isStatsAvailableForMatch(long matchID) {
        //List stats = new ArrayList<>();

        String selectQuery = "SELECT * FROM statDB WHERE matchID = " + matchID + "";
        Stats stats = new Stats();
        Cursor cursor = database.rawQuery(selectQuery, null);
        return cursor.getCount() > 0;
    }


    //HIGHLIGHT
    public long addHighlight(Highlight highlight) {
        ContentValues value = new ContentValues();
        value.put(tzlcDBContract.HighlightDB.COLUMN_MATCH_ID,highlight.getMatchID());
        value.put(tzlcDBContract.HighlightDB.COLUMN_CLUB_ID,highlight.getClubID());
        value.put(tzlcDBContract.HighlightDB.COLUMN_VCM_TIME,highlight.getVcmTime());
        value.put(tzlcDBContract.HighlightDB.COLUMN_SR_TIME,highlight.getSrTime());
        value.put(tzlcDBContract.HighlightDB.COLUMN_HIGHLIGHT,highlight.getHighlight());
        value.put(tzlcDBContract.HighlightDB.COLUMN_HIGHLIGHT2,highlight.getHighlight2());

        long rowID =  database.insert(tzlcDBContract.HighlightDB.TABLE_NAME,null, value);
        Log.d(tzlcDataSource.class.getSimpleName(), "Highlight added " + rowID);
        return rowID;
    }

    public List<Highlight> getAllHighlights(long matchID)    {
        List<Highlight> highlights = new ArrayList<>();
        String selectQuery = "SELECT * FROM highlightDB WHERE matchID = "+ matchID+ "";
        Cursor cursor = database.rawQuery(selectQuery, null);
        try{
            while(cursor.moveToNext())
            {
                Highlight highlight  = new Highlight(
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.HighlightDB.COLUMN_MATCH_ID)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.HighlightDB.COLUMN_CLUB_ID)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.HighlightDB.COLUMN_VCM_TIME)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.HighlightDB.COLUMN_SR_TIME)),
                        cursor.getString(cursor.getColumnIndex(tzlcDBContract.HighlightDB.COLUMN_HIGHLIGHT)),
                        cursor.getString(cursor.getColumnIndex(tzlcDBContract.HighlightDB.COLUMN_HIGHLIGHT2))
                );
                highlight.setId(cursor.getLong((cursor.getColumnIndex(tzlcDBContract.HighlightDB._ID))));
                Log.d(tzlcDataSource.class.getSimpleName(), "Highlight  Fetched " +highlight.getMatchID()+"," +highlight.getVcmTime()+","+highlight.getSrTime()+","+highlight.getHighlight());
                highlights.add(highlight);
            }
        }
        finally {
            if(cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return highlights;
    }

    public List<Highlight> getHighlightsOf(String event,long matchID)    {
        List<Highlight> highlights = new ArrayList<>();
        //String selectQuery = "SELECT * FROM highlightDB WHERE matchID = "+ matchID+ "";
        String selectQuery = "";
        if(event.equalsIgnoreCase("ALL"))
            selectQuery = "SELECT * FROM highlightDB  WHERE highlight LIKE '%%' AND matchID = " + matchID + "";
        else
            selectQuery = "SELECT * FROM highlightDB  WHERE highlight LIKE '%" + event + "%' AND matchID = " + matchID + "";
        Cursor cursor = database.rawQuery(selectQuery, null);
        try{
            while(cursor.moveToNext())
            {
                Highlight highlight  = new Highlight(
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.HighlightDB.COLUMN_MATCH_ID)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.HighlightDB.COLUMN_CLUB_ID)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.HighlightDB.COLUMN_VCM_TIME)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.HighlightDB.COLUMN_SR_TIME)),
                        cursor.getString(cursor.getColumnIndex(tzlcDBContract.HighlightDB.COLUMN_HIGHLIGHT)),
                        cursor.getString(cursor.getColumnIndex(tzlcDBContract.HighlightDB.COLUMN_HIGHLIGHT2))
                );
                highlight.setId(cursor.getLong((cursor.getColumnIndex(tzlcDBContract.HighlightDB._ID))));
                Log.d(tzlcDataSource.class.getSimpleName(), "Highlight  Fetched " +highlight.getMatchID()+"," +highlight.getVcmTime()+","+highlight.getSrTime()+","+highlight.getHighlight());
                highlights.add(highlight);
            }
        }
        finally {
            if(cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return highlights;
    }

    public List<String> getUnquieHighlights()
    {
        List<String> unquieHLs = new ArrayList<>();
        unquieHLs.add("ALL");
        String selectQuery = "SELECT DISTINCT (highlight) FROM highlightDB";
        Cursor cursor = database.rawQuery(selectQuery, null);
        try{

            while(cursor.moveToNext()){
                unquieHLs.add(cursor.getString(cursor.getColumnIndex(tzlcDBContract.HighlightDB.COLUMN_HIGHLIGHT)));
            }
        }
        finally {
            if(cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return unquieHLs;
    }



    //SQUAD
    public ContentValues createContentForSquad(Squad squad)    {
        ContentValues value = new ContentValues();
        value.put(tzlcDBContract.SquadDB.COLUMN_MATCH_ID,squad.getMatchID());
        value.put(tzlcDBContract.SquadDB.COLUMN_CLUB_ID,squad.getClubID());
        value.put(tzlcDBContract.SquadDB.COLUMN_PLAYER_ID,squad.getPlayerID());
        value.put(tzlcDBContract.SquadDB.COLUMN_ABSENT,squad.getAbsent());
        value.put(tzlcDBContract.SquadDB.COLUMN_POSITION,squad.getPosition());
        return value;
    }

    public List<Squad> createSquadList(Cursor cursor){
        List<Squad> squads = new ArrayList<>();

        try{
            while(cursor.moveToNext())
            {
                Squad squad  = new Squad(
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.SquadDB.COLUMN_MATCH_ID)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.SquadDB.COLUMN_CLUB_ID)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.SquadDB.COLUMN_PLAYER_ID)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.SquadDB.COLUMN_ABSENT)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.SquadDB.COLUMN_POSITION))
                );
                squad.setId(cursor.getLong((cursor.getColumnIndex(tzlcDBContract.SquadDB._ID))));
                Log.d(tzlcDataSource.class.getSimpleName(), "squad Fetched ");
                squads.add(squad);
            }
        }
        finally {
            if(cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return squads;
    }

    public void addSquad(Squad squad) {
        ContentValues value = createContentForSquad(squad);
        long rowID =  database.insert(tzlcDBContract.SquadDB.TABLE_NAME,null, value);
        Log.d(tzlcDataSource.class.getSimpleName(), "squad added " + rowID);
    }

    public Squad getSquad(long squadID)    {
        Squad squad = new Squad();
        String selectQuery = "SELECT * FROM squadDB WHERE _ID = "+ squadID+ "";
        Cursor cursor = database.rawQuery(selectQuery, null);
        try{
            while(cursor.moveToNext())
            {

                squad=  new Squad(
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.SquadDB.COLUMN_MATCH_ID)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.SquadDB.COLUMN_CLUB_ID)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.SquadDB.COLUMN_PLAYER_ID)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.SquadDB.COLUMN_ABSENT)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.SquadDB.COLUMN_POSITION))
                );
                squad.setId(cursor.getLong((cursor.getColumnIndex(tzlcDBContract.SquadDB._ID))));
            }
        }
        finally {
            if(cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return squad;
    }

    public void updateSquad(Squad squad)    {
        ContentValues value = createContentForSquad(squad);
        String selection = tzlcDBContract.SquadDB._ID + " = ?";
        String[] selectionargs = {String.valueOf(squad.getId())};
        int count = database.update(tzlcDBContract.SquadDB.TABLE_NAME,value,selection,selectionargs);
        Log.d(tzlcDataSource.class.getSimpleName(), "Squad record updated " + count);
    }

    public List<Squad> getAllSquadForMatch(long matchID)    {
        List<Squad> squads = new ArrayList<>();
        String selectQuery = "SELECT * FROM squadDB WHERE matchID = "+ matchID+ "";
        Cursor cursor = database.rawQuery(selectQuery, null);
        squads = createSquadList(cursor);
        return squads;
    }

    public List<Squad> getAllSquadForMatchandClub(long matchID, long clubID)    {
        List<Squad> squads = new ArrayList<>();
        String selectQuery = "SELECT * FROM squadDB WHERE matchID = "+ matchID+ " AND clubID = "+ clubID+ "";
        Cursor cursor = database.rawQuery(selectQuery, null);
        squads = createSquadList(cursor);
        return squads;
    }

    public List<Squad> getAvailableSquadForMatchandClub(long matchID, long clubID)    {
        List<Squad> squads = new ArrayList<>();
        String selectQuery = "SELECT * FROM squadDB WHERE matchID = "+ matchID+ " AND clubID = "+ clubID+ " AND absent = "+ 0 + "";
        Cursor cursor = database.rawQuery(selectQuery, null);
        squads = createSquadList(cursor);
        return squads;
    }


    //FORMATION
    public ContentValues createContentForFormation(Formation formation)    {
        ContentValues value = new ContentValues();
        value.put(tzlcDBContract.FormationDB.COLUMN_MATCH_ID,formation.getMatchID());
        value.put(tzlcDBContract.FormationDB.COLUMN_CLUB_ID,formation.getClubID());
        value.put(tzlcDBContract.FormationDB.COLUMN_FORMATIONS,formation.getFormations());
        value.put(tzlcDBContract.FormationDB.COLUMN_TYPE,formation.getFormationType());
        value.put(tzlcDBContract.FormationDB.COLUMN_GK,formation.getGk());
        value.put(tzlcDBContract.FormationDB.COLUMN_RB,formation.getRb());
        value.put(tzlcDBContract.FormationDB.COLUMN_RCD,formation.getRcd());
        value.put(tzlcDBContract.FormationDB.COLUMN_CD,formation.getCd());
        value.put(tzlcDBContract.FormationDB.COLUMN_LCD,formation.getLcd());
        value.put(tzlcDBContract.FormationDB.COLUMN_LB,formation.getLb());
        value.put(tzlcDBContract.FormationDB.COLUMN_RM,formation.getRm());
        value.put(tzlcDBContract.FormationDB.COLUMN_RCM,formation.getRcm());
        value.put(tzlcDBContract.FormationDB.COLUMN_CM,formation.getCm());
        value.put(tzlcDBContract.FormationDB.COLUMN_LCM,formation.getLcm());
        value.put(tzlcDBContract.FormationDB.COLUMN_LM,formation.getLm());
        value.put(tzlcDBContract.FormationDB.COLUMN_RST,formation.getRst());
        value.put(tzlcDBContract.FormationDB.COLUMN_ST,formation.getSt());
        value.put(tzlcDBContract.FormationDB.COLUMN_LST,formation.getLst());

        return value;
    }

    public List<Formation> createFormationList(Cursor cursor){
        List<Formation> formations = new ArrayList<>();

        try{
            while(cursor.moveToNext())
            {
                Formation formation  = new Formation(
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.FormationDB.COLUMN_MATCH_ID)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.FormationDB.COLUMN_CLUB_ID)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.FormationDB.COLUMN_FORMATIONS)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.FormationDB.COLUMN_TYPE)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.FormationDB.COLUMN_GK)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.FormationDB.COLUMN_RB)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.FormationDB.COLUMN_RCD)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.FormationDB.COLUMN_CD)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.FormationDB.COLUMN_LCD)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.FormationDB.COLUMN_LB)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.FormationDB.COLUMN_RM)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.FormationDB.COLUMN_RCM)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.FormationDB.COLUMN_CM)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.FormationDB.COLUMN_LCM)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.FormationDB.COLUMN_LM)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.FormationDB.COLUMN_RST)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.FormationDB.COLUMN_ST)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.FormationDB.COLUMN_LST))
                );
                formation.setId(cursor.getLong((cursor.getColumnIndex(tzlcDBContract.FormationDB._ID))));
                Log.d(tzlcDataSource.class.getSimpleName(), "formation Fetched ");
                formations.add(formation);
            }
        }
        finally {
            if(cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return formations;
    }

    public void addFormation(Formation formation) {
        ContentValues value = createContentForFormation(formation);
        long rowID =  database.insert(tzlcDBContract.FormationDB.TABLE_NAME,null, value);
        Log.d(tzlcDataSource.class.getSimpleName(), "Formation added " + rowID);
    }

    public Formation getFormation(long formationID)    {
        Formation formation = new Formation();
        String selectQuery = "SELECT * FROM formationDB WHERE _ID = "+ formationID + "";
        Cursor cursor = database.rawQuery(selectQuery, null);
        try{
            while(cursor.moveToNext())
            {

                formation=  new Formation(
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.FormationDB.COLUMN_MATCH_ID)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.FormationDB.COLUMN_CLUB_ID)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.FormationDB.COLUMN_FORMATIONS)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.FormationDB.COLUMN_TYPE)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.FormationDB.COLUMN_GK)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.FormationDB.COLUMN_RB)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.FormationDB.COLUMN_RCD)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.FormationDB.COLUMN_CD)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.FormationDB.COLUMN_LCD)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.FormationDB.COLUMN_LB)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.FormationDB.COLUMN_RM)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.FormationDB.COLUMN_RCM)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.FormationDB.COLUMN_CM)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.FormationDB.COLUMN_LCM)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.FormationDB.COLUMN_LM)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.FormationDB.COLUMN_RST)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.FormationDB.COLUMN_ST)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.FormationDB.COLUMN_LST))
                );
                formation.setId(cursor.getLong((cursor.getColumnIndex(tzlcDBContract.FormationDB._ID))));
            }
        }
        finally {
            if(cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return formation;
    }

    public void updateFormation(Formation formation)    {
        ContentValues value = createContentForFormation(formation);
        String selection = tzlcDBContract.FormationDB._ID + " = ?";
        String[] selectionargs = {String.valueOf(formation.getId())};
        int count = database.update(tzlcDBContract.FormationDB.TABLE_NAME,value,selection,selectionargs);
        Log.d(tzlcDataSource.class.getSimpleName(), "Formation record updated " + count);
    }

    public List<Formation> getAllFormationForMatch(long matchID)    {
        List<Formation> formations = new ArrayList<>();
        String selectQuery = "SELECT * FROM formationDB WHERE matchID = "+ matchID+ "";
        Cursor cursor = database.rawQuery(selectQuery, null);
        formations = createFormationList(cursor);
        return formations;
    }

    public List<Formation> getFormationForMatchandClub(long matchID, long clubID)    {
        List<Formation> formations = new ArrayList<>();
        String selectQuery = "SELECT * FROM formationDB WHERE matchID = "+ matchID+ " AND clubID = "+ clubID+ "";
        Cursor cursor = database.rawQuery(selectQuery, null);
        formations = createFormationList(cursor);
        return formations;
    }

    public Formation getFormation(long matchID, long clubID)    {
        Formation formation = new Formation();
        String selectQuery = "SELECT * FROM formationDB WHERE matchID = "+ matchID+ " AND clubID = "+ clubID+ "";
        Cursor cursor = database.rawQuery(selectQuery, null);
        try{
            while(cursor.moveToNext())
            {

                formation=  new Formation(
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.FormationDB.COLUMN_MATCH_ID)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.FormationDB.COLUMN_CLUB_ID)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.FormationDB.COLUMN_FORMATIONS)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.FormationDB.COLUMN_TYPE)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.FormationDB.COLUMN_GK)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.FormationDB.COLUMN_RB)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.FormationDB.COLUMN_RCD)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.FormationDB.COLUMN_CD)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.FormationDB.COLUMN_LCD)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.FormationDB.COLUMN_LB)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.FormationDB.COLUMN_RM)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.FormationDB.COLUMN_RCM)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.FormationDB.COLUMN_CM)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.FormationDB.COLUMN_LCM)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.FormationDB.COLUMN_LM)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.FormationDB.COLUMN_RST)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.FormationDB.COLUMN_ST)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.FormationDB.COLUMN_LST))
                );
                formation.setId(cursor.getLong((cursor.getColumnIndex(tzlcDBContract.FormationDB._ID))));
            }
        }
        finally {
            if(cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return formation;
    }


    //SUBSTITUTE
    public ContentValues createContentForSubstitute(Substitute substitute)    {
        ContentValues value = new ContentValues();
        value.put(tzlcDBContract.SubDB.COLUMN_MATCH_ID,substitute.getMatchID());
        value.put(tzlcDBContract.SubDB.COLUMN_CLUB_ID,substitute.getClubID());
        value.put(tzlcDBContract.SubDB.COLUMN_PLAYER_OUT_ID,substitute.getPlayerOutID());
        value.put(tzlcDBContract.SubDB.COLUMN_PLAYER_IN_ID,substitute.getPlayerInID());
        value.put(tzlcDBContract.SubDB.COLUMN_TIME,substitute.getMatchTime());
        value.put(tzlcDBContract.SubDB.COLUMN_REASON,substitute.getReason());
        return value;
    }

    public List<Substitute> createSubstituteList(Cursor cursor){
        List<Substitute> substitutes = new ArrayList<>();
        try{
            while(cursor.moveToNext())
            {
                Substitute substitute = new Substitute(
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.SubDB.COLUMN_MATCH_ID)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.SubDB.COLUMN_CLUB_ID)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.SubDB.COLUMN_PLAYER_OUT_ID)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.SubDB.COLUMN_PLAYER_IN_ID)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.SubDB.COLUMN_TIME)),
                        cursor.getString(cursor.getColumnIndex(tzlcDBContract.SubDB.COLUMN_REASON))
                );
                substitute.setId(cursor.getLong((cursor.getColumnIndex(tzlcDBContract.SquadDB._ID))));
                Log.d(tzlcDataSource.class.getSimpleName(), "Sub Fetched ");
                substitutes.add(substitute);
            }
        }
        finally {
            if(cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return substitutes;
    }

    public long addSubstitute(Substitute substitute) {
        ContentValues value = createContentForSubstitute(substitute);

        long rowID =  database.insert(tzlcDBContract.SubDB.TABLE_NAME,null, value);
        Log.d(tzlcDataSource.class.getSimpleName(), "Subs added " + rowID);
        return rowID;
    }

    public List<Substitute> getAllSubstituteForMatch(long matchID)    {
        List<Substitute> substitutes = new ArrayList<>();
        String selectQuery = "SELECT * FROM subDB WHERE matchID = "+ matchID+ "";
        Cursor cursor = database.rawQuery(selectQuery, null);
        substitutes = createSubstituteList(cursor);
        return substitutes;
    }

    public Substitute getSubstitute(long subID){
        Substitute substitute = new Substitute();
        String selectQuery = "SELECT * FROM subDB WHERE _ID = "+ subID+ "";
        Cursor cursor = database.rawQuery(selectQuery, null);
        try{
            while(cursor.moveToNext())
            {
                substitute = new Substitute(
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.SubDB.COLUMN_MATCH_ID)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.SubDB.COLUMN_CLUB_ID)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.SubDB.COLUMN_PLAYER_OUT_ID)),
                        cursor.getLong(cursor.getColumnIndex(tzlcDBContract.SubDB.COLUMN_PLAYER_IN_ID)),
                        cursor.getInt(cursor.getColumnIndex(tzlcDBContract.SubDB.COLUMN_TIME)),
                        cursor.getString(cursor.getColumnIndex(tzlcDBContract.SubDB.COLUMN_REASON))
                );
                substitute.setId(cursor.getLong((cursor.getColumnIndex(tzlcDBContract.SquadDB._ID))));
            }
        }
        finally {
            if(cursor != null && !cursor.isClosed())
                cursor.close();
        }

        return substitute;
    }

    public void updateSubstitute(Substitute substitute)    {
        ContentValues value = createContentForSubstitute(substitute);
        String selection = tzlcDBContract.SubDB._ID + " = ?";
        String[] selectionargs = {String.valueOf(substitute.getId())};
        int count = database.update(tzlcDBContract.SubDB.TABLE_NAME,value,selection,selectionargs);
        Log.d(tzlcDataSource.class.getSimpleName(), "Subs record updated " + count);
    }

    public void deleteSubstitute(long subID)    {
        String selection = tzlcDBContract.SubDB._ID + " = ?";
        String[] selectionargs = {String.valueOf(subID)};
        int count = database.delete(tzlcDBContract.SubDB.TABLE_NAME,selection,selectionargs);
        Log.d(tzlcDataSource.class.getSimpleName(), "Subs deleted " + count);
    }

}
