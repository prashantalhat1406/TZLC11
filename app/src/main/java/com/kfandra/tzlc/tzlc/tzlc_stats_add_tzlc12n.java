package com.kfandra.tzlc.tzlc;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class tzlc_stats_add_tzlc12n extends AppCompatActivity {

    private tzlcDataSource datasource;
    private long matchID,lastGoalID,lastCardID;
    private Stats stat;
    private Button buthomeDFK, buthomeCor, buthomeLC, buthomeTI, buthomePOPScored, buthomePOPMissed,buthomeGK,buthomeOff;
    private Button butawayDFK, butawayCor, butawayLC, butawayTI, butawayPOPScored, butawayPOPMissed,butawayGK,butawayOff;
    private Button clear, save;
    private ImageButton startPause;
    private Spinner spnHomePlayers, spnAwayPlayers;
    private List<String> homePlayerNames, awayPlayerNames;
    Match m;
    private int undo;
    private Menu undoMenu;
    int homeColor, awayColor, temphomeColor, tempawayColor, homeTextColor,awayTextColor ;
    public TextView homeClub, awayClub;
    private TextView timerMatch,homeScore, awayScore;
    private Handler matchTimeHandler = new Handler();
    long matchTimeInMillisecond = 0L;
    long matchSwapBuff = 0L;
    long matchUpdatedTime = 0L;
    int matchTime = 0;
    private long matchStartTime = 0L;
    private boolean awayPossession=false;
    private boolean start=true;
    int halftime=0, resetCount =0;
    private RadioGroup homeActions, awayActions;
    public int completedPassesHome=0,completedPassesAway=0;
    private ToggleButton t1, t2, t3, t4;
    private String homeClubName, awayClubName;
    List<possesionTimer> possesionTimers;
    int h1=0,h2=0,h3=0,h4=0,a1=0,a2=0,a3=0,a4=0;
    int hh1=0,hh2=0,hh3=0,hh4=0,ha1=0,ha2=0,ha3=0,ha4=0;
    String lastPossession="";



    public boolean isColorDark(int color){
        double darkness = 1-(0.299* Color.red(color) + 0.587*Color.green(color) + 0.114*Color.blue(color))/255;
        if(darkness<0.5){
            return false; // It's a light color
        }else{
            return true; // It's a dark color
        }
    }

    public void clearPossessionButtons(String buttonName)
    {
        switch (buttonName)
        {
            case "t1" : t2.setTextOff("");t2.setTextOn("");t3.setTextOff("");t3.setTextOn("");t4.setTextOff("");t4.setTextOn("");break;
            case "t2" : t1.setTextOff("");t1.setTextOn("");t3.setTextOff("");t3.setTextOn("");t4.setTextOff("");t4.setTextOn("");break;
            case "t3" : t1.setTextOff("");t1.setTextOn("");t2.setTextOff("");t2.setTextOn("");t4.setTextOff("");t4.setTextOn("");break;
            case "t4" : t1.setTextOff("");t1.setTextOn("");t2.setTextOff("");t2.setTextOn("");t3.setTextOff("");t3.setTextOn("");break;
        }
    }



    public void clearActions()
    {
        spnHomePlayers.setSelection(0);
        spnAwayPlayers.setSelection(0);

        for(int i=0;i < homeActions.getChildCount();i++)
            ((RadioButton)homeActions.getChildAt(i)).setChecked(false);
        for(int i=0;i < awayActions.getChildCount();i++)
            ((RadioButton)awayActions.getChildAt(i)).setChecked(false);
    }

    public void updateScreenColor(int tempHColor, int tempAColor)
    {

        int temphomeTextColor = (isColorDark(tempHColor)?Color.WHITE:Color.BLACK);
        int tempawayTextColor = (isColorDark(tempAColor)?Color.WHITE:Color.BLACK);

        buthomeDFK.setBackgroundTintList(ColorStateList.valueOf(tempHColor));buthomeDFK.setTextColor(temphomeTextColor);
        buthomeCor.setBackgroundTintList(ColorStateList.valueOf(tempHColor));buthomeCor.setTextColor(temphomeTextColor);
        buthomeLC.setBackgroundTintList(ColorStateList.valueOf(tempHColor));buthomeLC.setTextColor(temphomeTextColor);
        buthomeGK.setBackgroundTintList(ColorStateList.valueOf(tempHColor));buthomeGK.setTextColor(temphomeTextColor);
        buthomeOff.setBackgroundTintList(ColorStateList.valueOf(tempHColor));buthomeOff.setTextColor(temphomeTextColor);
        buthomeTI.setBackgroundTintList(ColorStateList.valueOf(tempHColor));buthomeTI.setTextColor(temphomeTextColor);
        buthomePOPScored.setBackgroundTintList(ColorStateList.valueOf(tempHColor));buthomePOPScored.setTextColor(temphomeTextColor);
        buthomePOPMissed.setBackgroundTintList(ColorStateList.valueOf(tempHColor));buthomePOPMissed.setTextColor(temphomeTextColor);
        TextView homeClub = findViewById(R.id.txtstatsHomeClub);
        TextView homeClub1 = findViewById(R.id.txtHomeClub1);
        TextView homeClub2 = findViewById(R.id.txtHomeClub2);
        homeScore.setTextColor(tempHColor);
        homeClub.setTextColor(tempHColor);
        homeClub1.setTextColor(tempHColor);
        homeClub2.setTextColor(tempHColor);


        butawayDFK.setBackgroundTintList(ColorStateList.valueOf(tempAColor));butawayDFK.setTextColor(tempawayTextColor);
        butawayCor.setBackgroundTintList(ColorStateList.valueOf(tempAColor));butawayCor.setTextColor(tempawayTextColor);
        butawayLC.setBackgroundTintList(ColorStateList.valueOf(tempAColor));butawayLC.setTextColor(tempawayTextColor);
        butawayGK.setBackgroundTintList(ColorStateList.valueOf(tempAColor));butawayGK.setTextColor(tempawayTextColor);
        butawayOff.setBackgroundTintList(ColorStateList.valueOf(tempAColor));butawayOff.setTextColor(tempawayTextColor);
        butawayTI.setBackgroundTintList(ColorStateList.valueOf(tempAColor));butawayTI.setTextColor(tempawayTextColor);
        butawayPOPScored.setBackgroundTintList(ColorStateList.valueOf(tempAColor));butawayPOPScored.setTextColor(tempawayTextColor);
        butawayPOPMissed.setBackgroundTintList(ColorStateList.valueOf(tempAColor));butawayPOPMissed.setTextColor(tempawayTextColor);
        TextView awayClub = findViewById(R.id.txtstatsAwayClub);
        TextView awayClub1 = findViewById(R.id.txtAwayClub1);
        TextView awayClub2 = findViewById(R.id.txtAwayClub2);
        awayScore.setTextColor(tempAColor);
        awayClub.setTextColor(tempAColor);
        awayClub1.setTextColor(tempAColor);
        awayClub2.setTextColor(tempAColor);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tzlc_stats_add__tzlc12_n);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle b = getIntent().getExtras();
        matchID = b.getLong("matchID",-1);
        int colors [] = getResources().getIntArray(R.array.androidcolors);

        datasource= new tzlcDataSource(this);
        datasource.open();

        stat = datasource.getAllStatsForMatch(matchID);
        datasource.updateStats(stat);

        m = new Match();
        m = datasource.getMatch(matchID);

        possesionTimers = new ArrayList<>();
        possesionTimers.add( new possesionTimer("H2",0));

        homeColor =  datasource.getClub(m.getHomeClubID()).getClubColor();
        homeTextColor = (isColorDark(homeColor)?Color.WHITE:Color.BLACK);
        awayColor =  datasource.getClub(m.getAwayClubID()).getClubColor();
        awayTextColor = (isColorDark(awayColor)?Color.WHITE:Color.BLACK);

        temphomeColor = homeColor;
        tempawayColor = awayColor;

        timerMatch = findViewById(R.id.txtstatsMatchTimerTZLC12);
        matchStartTime = SystemClock.uptimeMillis();

        homeClubName =""+datasource.getClub( m.getHomeClubID()).getClubShortName();
        awayClubName = ""+datasource.getClub( m.getAwayClubID()).getClubShortName();

        homeClub = findViewById(R.id.txtstatsHomeClub);
        homeClub.setText(homeClubName);
        homeClub.setTextColor(homeColor);
        final TextView homeClub1 = findViewById(R.id.txtHomeClub1);
        TextView homeClub2 = findViewById(R.id.txtHomeClub2);
        homeClub1.setText(homeClubName);
        homeClub1.setTextColor(homeColor);
        homeClub2.setText(homeClubName);
        homeClub2.setTextColor(homeColor);

        awayClub = findViewById(R.id.txtstatsAwayClub);
        awayClub.setText(awayClubName);
        awayClub.setTextColor(awayColor);
        TextView awayClub1 = findViewById(R.id.txtAwayClub1);
        TextView awayClub2 = findViewById(R.id.txtAwayClub2);
        awayClub1.setText(awayClubName);
        awayClub1.setTextColor(awayColor);
        awayClub2.setText(awayClubName);
        awayClub2.setTextColor(awayColor);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(tzlc_stats_add_tzlc12n.this,R.array.matchType,android.R.layout.simple_spinner_item);
        TextView matchType = findViewById(R.id.txtstatsMatchTypeTZLC12);
        matchType.setText(""+adapter.getItem( m.getType()));

        homePlayerNames = datasource.getAllPlayerNamesForClub(m.getHomeClubID(),matchID);
        spnHomePlayers = findViewById(R.id.spnHomePlayers);
        ArrayAdapter<String> adapterPlayerNames = new ArrayAdapter<String>(tzlc_stats_add_tzlc12n.this,R.layout.dropdownitem, homePlayerNames);
        adapterPlayerNames.setDropDownViewResource(R.layout.dropdownitem);
        adapterPlayerNames.insert("Please select Player",0);
        spnHomePlayers.setAdapter(adapterPlayerNames);

        awayPlayerNames = datasource.getAllPlayerNamesForClub(m.getAwayClubID(),matchID);
        spnAwayPlayers = findViewById(R.id.spnAwayPlayers);
        ArrayAdapter<String> adapterPlayerNames2 = new ArrayAdapter<String>(tzlc_stats_add_tzlc12n.this,R.layout.dropdownitem, awayPlayerNames);
        adapterPlayerNames2.setDropDownViewResource(R.layout.dropdownitem);
        adapterPlayerNames2.insert("Please select Player",0);
        spnAwayPlayers.setAdapter(adapterPlayerNames2);



        homeActions = findViewById(R.id.rdgrpHomeActions);
        awayActions = findViewById(R.id.rdgrpAwayActions);


        clear = findViewById(R.id.butStatsActionClear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i < homeActions.getChildCount();i++)
                    ((RadioButton)homeActions.getChildAt(i)).setChecked(false);
                for(int i=0;i < awayActions.getChildCount();i++)
                    ((RadioButton)awayActions.getChildAt(i)).setChecked(false);
            }
        });

        save = findViewById(R.id.butStatsActionSave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Code for Adding Goal
                RadioButton hGoal = findViewById(R.id.rdbutHGoal);
                RadioButton aGoal = findViewById(R.id.rdbutAGoal);
                if ( hGoal.isChecked())
                {
                    if(!spnHomePlayers.getSelectedItem().toString().equals("Please select Player")) {
                        Goal goal = new Goal();
                        goal.setMatchID(matchID);
                        goal.setPlayerID(datasource.getPlayerID(spnHomePlayers.getSelectedItem().toString()));
                        goal.setAssistPlayerID(0);
                        goal.setMatchTime(matchTime);
                        goal.setVcmtime(0);
                        goal.setAgainstClubID(m.getHomeClubID());
                        goal.setOwnGoal(0);
                        lastGoalID = datasource.addGoal(goal);
                        Highlight highlight = new Highlight(matchID, goal.getAgainstClubID(), matchTime, 0, "GOAL", ""+spnHomePlayers.getSelectedItem().toString());
                        datasource.addHighlight(highlight);
                        homeScore.setText("" + (Integer.parseInt(homeScore.getText().toString()) + 1));
                        stat.setHome_Score(Integer.parseInt(homeScore.getText().toString()));
                        datasource.updateStats(stat);
                        clearActions();
                        Toast t =  Toast.makeText(tzlc_stats_add_tzlc12n.this, ""+datasource.getClub( m.getHomeClubID()).getClubShortName() + " : Goal Added", Toast.LENGTH_SHORT);
                        t.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL,0,0);
                        t.show();
                    }else
                    {
                        Toast t =  Toast.makeText(tzlc_stats_add_tzlc12n.this, "Error !!! Please select Player.", Toast.LENGTH_SHORT);
                        t.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL,0,0);
                        t.show();
                    }
                }
                if ( aGoal.isChecked())
                {
                    if(!spnAwayPlayers.getSelectedItem().toString().equals("Please select Player")) {
                        Goal goal = new Goal();
                        goal.setMatchID(matchID);
                        goal.setPlayerID(datasource.getPlayerID(spnAwayPlayers.getSelectedItem().toString()));
                        goal.setAssistPlayerID(0);
                        goal.setMatchTime(matchTime);
                        goal.setVcmtime(0);
                        goal.setAgainstClubID(m.getAwayClubID());
                        goal.setOwnGoal(0);
                        lastGoalID = datasource.addGoal(goal);
                        Highlight highlight = new Highlight(matchID, goal.getAgainstClubID(), matchTime, 0, "GOAL", ""+spnAwayPlayers.getSelectedItem().toString());
                        datasource.addHighlight(highlight);
                        awayScore.setText("" + (Integer.parseInt(awayScore.getText().toString()) + 1));
                        stat.setAway_Score(Integer.parseInt(awayScore.getText().toString()));
                        datasource.updateStats(stat);
                        clearActions();
                        Toast t =  Toast.makeText(tzlc_stats_add_tzlc12n.this, ""+datasource.getClub( m.getAwayClubID()).getClubShortName() + " : Goal Added", Toast.LENGTH_SHORT);
                        t.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL,0,0);
                        t.show();
                    }else
                    {
                        Toast t =  Toast.makeText(tzlc_stats_add_tzlc12n.this, "Error !!! Please select Player.", Toast.LENGTH_SHORT);
                        t.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL,0,0);
                        t.show();
                    }
                }


                //Code for Adding OWN Goal
                RadioButton hOwnGoal = findViewById(R.id.rdbutHOwnGoal);
                RadioButton aOwnGoal = findViewById(R.id.rdbutAOwnGoal);
                if ( hOwnGoal.isChecked())
                {
                    if(!spnHomePlayers.getSelectedItem().toString().equals("Please select Player")) {
                        Goal goal = new Goal();
                        goal.setMatchID(matchID);
                        goal.setPlayerID(datasource.getPlayerID(spnHomePlayers.getSelectedItem().toString()));
                        goal.setAssistPlayerID(0);
                        goal.setMatchTime(matchTime);
                        goal.setVcmtime(0);
                        goal.setAgainstClubID(m.getAwayClubID());
                        goal.setOwnGoal(1);
                        lastGoalID = datasource.addGoal(goal);
                        Highlight highlight = new Highlight(matchID, goal.getAgainstClubID(), matchTime, 0, "GOAL", ""+spnHomePlayers.getSelectedItem().toString());
                        datasource.addHighlight(highlight);
                        awayScore.setText("" + (Integer.parseInt(awayScore.getText().toString()) + 1));
                        stat.setAway_Score(Integer.parseInt(awayScore.getText().toString()));
                        datasource.updateStats(stat);
                        clearActions();
                        Toast t =  Toast.makeText(tzlc_stats_add_tzlc12n.this, ""+datasource.getClub( m.getHomeClubID()).getClubShortName() + " : Own Goal Added", Toast.LENGTH_SHORT);
                        t.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL,0,0);
                        t.show();
                    }else
                    {
                        Toast t =  Toast.makeText(tzlc_stats_add_tzlc12n.this, "Error !!! Please select Player.", Toast.LENGTH_SHORT);
                        t.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL,0,0);
                        t.show();
                    }
                }
                if ( aOwnGoal.isChecked())
                {
                    if(!spnAwayPlayers.getSelectedItem().toString().equals("Please select Player")) {
                        Goal goal = new Goal();
                        goal.setMatchID(matchID);
                        goal.setPlayerID(datasource.getPlayerID(spnAwayPlayers.getSelectedItem().toString()));
                        goal.setAssistPlayerID(0);
                        goal.setMatchTime(matchTime);
                        goal.setVcmtime(0);
                        goal.setAgainstClubID(m.getHomeClubID());
                        goal.setOwnGoal(1);
                        lastGoalID = datasource.addGoal(goal);
                        Highlight highlight = new Highlight(matchID, goal.getAgainstClubID(), 0, matchTime, "GOAL", ""+spnAwayPlayers.getSelectedItem().toString());
                        datasource.addHighlight(highlight);
                        homeScore.setText("" + (Integer.parseInt(homeScore.getText().toString()) + 1));
                        stat.setHome_Score(Integer.parseInt(homeScore.getText().toString()));
                        datasource.updateStats(stat);
                        clearActions();
                        Toast t =  Toast.makeText(tzlc_stats_add_tzlc12n.this, ""+datasource.getClub( m.getAwayClubID()).getClubShortName() + " : Own Goal Added", Toast.LENGTH_SHORT);
                        t.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL,0,0);
                        t.show();
                    }else
                    {
                        Toast t =  Toast.makeText(tzlc_stats_add_tzlc12n.this, "Error !!! Please select Player.", Toast.LENGTH_SHORT);
                        t.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL,0,0);
                        t.show();
                    }
                }

                //Code for Assist
                RadioButton hAssist = findViewById(R.id.rdbutHAssist);
                RadioButton aAssist = findViewById(R.id.rdbutAAssist);
                if(hAssist.isChecked())
                {
                    if(!spnHomePlayers.getSelectedItem().toString().equals("Please select Player")) {
                        Goal goal = datasource.getGoal(lastGoalID);
                        goal.setAssistPlayerID(datasource.getPlayerID(spnHomePlayers.getSelectedItem().toString()));
                        datasource.updateGoal(goal);
                        clearActions();
                        Toast t =  Toast.makeText(tzlc_stats_add_tzlc12n.this, ""+datasource.getClub( m.getHomeClubID()).getClubShortName() + " : Assist Added", Toast.LENGTH_SHORT);
                        t.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL,0,0);
                        t.show();
                    }else
                        Toast.makeText(tzlc_stats_add_tzlc12n.this, "Error !!! Please select Player.", Toast.LENGTH_SHORT).show();
                }
                if(aAssist.isChecked())
                {
                    if(!spnAwayPlayers.getSelectedItem().toString().equals("Please select Player")) {
                        Goal goal = datasource.getGoal(lastGoalID);
                        goal.setAssistPlayerID(datasource.getPlayerID(spnAwayPlayers.getSelectedItem().toString()));
                        datasource.updateGoal(goal);
                        clearActions();
                        Toast t =  Toast.makeText(tzlc_stats_add_tzlc12n.this, ""+datasource.getClub( m.getAwayClubID()).getClubShortName() + " : Assist Added", Toast.LENGTH_SHORT);
                        t.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL,0,0);
                        t.show();
                    }else
                    {
                        Toast t =  Toast.makeText(tzlc_stats_add_tzlc12n.this, "Error !!! Please select Player.", Toast.LENGTH_SHORT);
                        t.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL,0,0);
                        t.show();
                    }
                }

                //Code for Cards
                RadioButton hRedCard = findViewById(R.id.rdbutHRedCard);
                RadioButton hYellowCard = findViewById(R.id.rdbutHYellowCard);
                RadioButton hBlueCard = findViewById(R.id.rdbutHBlueCard);
                RadioButton aRedCard = findViewById(R.id.rdbutARedCard);
                RadioButton aYellowCard = findViewById(R.id.rdbutAYellowCard);
                RadioButton aBlueCard = findViewById(R.id.rdbutABlueCard);
                if( hRedCard.isChecked())
                {
                    if(!spnHomePlayers.getSelectedItem().toString().equals("Please select Player")) {
                        Card card = new Card();
                        card.setMatchID(matchID);
                        card.setPlayerID(datasource.getPlayerID(spnHomePlayers.getSelectedItem().toString()));
                        card.setTime(matchTime);
                        card.setReason("");
                        card.setType(1);
                        card.setClubID(datasource.getPlayer(datasource.getPlayerID(spnHomePlayers.getSelectedItem().toString())).getClubId());
                        lastCardID = datasource.addCard(card);
                        Highlight highlight = new Highlight(matchID, card.getClubID(), matchTime, -200, "Red Card",
                                ""+spnHomePlayers.getSelectedItem().toString());
                        datasource.addHighlight(highlight);
                        clearActions();
                        Toast t =  Toast.makeText(tzlc_stats_add_tzlc12n.this, ""+datasource.getClub( m.getHomeClubID()).getClubShortName() + " : Red Card Added", Toast.LENGTH_SHORT);
                        t.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL,0,0);
                        t.show();
                    }else
                    {
                        Toast t =  Toast.makeText(tzlc_stats_add_tzlc12n.this, "Error !!! Please select Player.", Toast.LENGTH_SHORT);
                        t.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL,0,0);
                        t.show();
                    }
                }
                if( hYellowCard.isChecked())
                {
                    if(!spnHomePlayers.getSelectedItem().toString().equals("Please select Player")) {
                        Card card = new Card();
                        card.setMatchID(matchID);
                        card.setPlayerID(datasource.getPlayerID(spnHomePlayers.getSelectedItem().toString()));
                        card.setTime(matchTime);
                        card.setReason("");
                        card.setType(0);
                        card.setClubID(datasource.getPlayer(datasource.getPlayerID(spnHomePlayers.getSelectedItem().toString())).getClubId());
                        lastCardID = datasource.addCard(card);
                        Highlight highlight = new Highlight(matchID, card.getClubID(), matchTime, -200, "Yellow Card",
                                ""+spnHomePlayers.getSelectedItem().toString());
                        datasource.addHighlight(highlight);
                        clearActions();
                        Toast t =  Toast.makeText(tzlc_stats_add_tzlc12n.this, ""+datasource.getClub( m.getHomeClubID()).getClubShortName() + " : Yellow Card Added", Toast.LENGTH_SHORT);
                        t.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL,0,0);
                        t.show();
                    }else
                    {
                        Toast t =  Toast.makeText(tzlc_stats_add_tzlc12n.this, "Error !!! Please select Player.", Toast.LENGTH_SHORT);
                        t.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL,0,0);
                        t.show();
                    }
                }
                if( hBlueCard.isChecked())
                {
                    if(!spnHomePlayers.getSelectedItem().toString().equals("Please select Player")) {
                        Card card = new Card();
                        card.setMatchID(matchID);
                        card.setPlayerID(datasource.getPlayerID(spnHomePlayers.getSelectedItem().toString()));
                        card.setTime(matchTime);
                        card.setReason("");
                        card.setType(2);
                        card.setClubID(datasource.getPlayer(datasource.getPlayerID(spnHomePlayers.getSelectedItem().toString())).getClubId());
                        lastCardID = datasource.addCard(card);
                        Highlight highlight = new Highlight(matchID, card.getClubID(), matchTime, -200, "Blue Card",
                                ""+spnHomePlayers.getSelectedItem().toString());
                        datasource.addHighlight(highlight);
                        clearActions();
                        Toast t =  Toast.makeText(tzlc_stats_add_tzlc12n.this, ""+datasource.getClub( m.getHomeClubID()).getClubShortName() + " : Blue Card Added", Toast.LENGTH_SHORT);
                        t.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL,0,0);
                        t.show();
                    }else
                    {
                        Toast t =  Toast.makeText(tzlc_stats_add_tzlc12n.this, "Error !!! Please select Player.", Toast.LENGTH_SHORT);
                        t.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL,0,0);
                        t.show();
                    }
                }
                if( aRedCard.isChecked())
                {
                    if(!spnAwayPlayers.getSelectedItem().toString().equals("Please select Player")) {
                        Card card = new Card();
                        card.setMatchID(matchID);
                        card.setPlayerID(datasource.getPlayerID(spnAwayPlayers.getSelectedItem().toString()));
                        card.setTime(matchTime);
                        card.setReason("");
                        card.setType(1);
                        card.setClubID(datasource.getPlayer(datasource.getPlayerID(spnAwayPlayers.getSelectedItem().toString())).getClubId());
                        lastCardID = datasource.addCard(card);
                        Highlight highlight = new Highlight(matchID, card.getClubID(), matchTime, -200, "Red Card",
                                ""+spnAwayPlayers.getSelectedItem().toString());
                        datasource.addHighlight(highlight);
                        clearActions();
                        Toast t =  Toast.makeText(tzlc_stats_add_tzlc12n.this, ""+datasource.getClub( m.getAwayClubID()).getClubShortName() + " : Red Card Added", Toast.LENGTH_SHORT);
                        t.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL,0,0);
                        t.show();
                    }else
                    {
                        Toast t =  Toast.makeText(tzlc_stats_add_tzlc12n.this, "Error !!! Please select Player.", Toast.LENGTH_SHORT);
                        t.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL,0,0);
                        t.show();
                    }
                }
                if( aYellowCard.isChecked())
                {
                    if(!spnAwayPlayers.getSelectedItem().toString().equals("Please select Player")) {
                        Card card = new Card();
                        card.setMatchID(matchID);
                        card.setPlayerID(datasource.getPlayerID(spnAwayPlayers.getSelectedItem().toString()));
                        card.setTime(matchTime);
                        card.setReason("");
                        card.setType(0);
                        card.setClubID(datasource.getPlayer(datasource.getPlayerID(spnAwayPlayers.getSelectedItem().toString())).getClubId());
                        lastCardID = datasource.addCard(card);
                        Highlight highlight = new Highlight(matchID, card.getClubID(), matchTime, -200, "Yellwo Card",
                                ""+spnAwayPlayers.getSelectedItem().toString());
                        datasource.addHighlight(highlight);
                        clearActions();
                        Toast t =  Toast.makeText(tzlc_stats_add_tzlc12n.this, ""+datasource.getClub( m.getAwayClubID()).getClubShortName() + " : Yellow Card Added", Toast.LENGTH_SHORT);
                        t.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL,0,0);
                        t.show();
                    }else
                    {
                        Toast t =  Toast.makeText(tzlc_stats_add_tzlc12n.this, "Error !!! Please select Player.", Toast.LENGTH_SHORT);
                        t.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL,0,0);
                        t.show();
                    }
                }
                if( aBlueCard.isChecked())
                {
                    if(!spnAwayPlayers.getSelectedItem().toString().equals("Please select Player")) {
                        Card card = new Card();
                        card.setMatchID(matchID);
                        card.setPlayerID(datasource.getPlayerID(spnAwayPlayers.getSelectedItem().toString()));
                        card.setTime(matchTime);
                        card.setReason("");
                        card.setType(2);
                        card.setClubID(datasource.getPlayer(datasource.getPlayerID(spnAwayPlayers.getSelectedItem().toString())).getClubId());
                        lastCardID = datasource.addCard(card);
                        Highlight highlight = new Highlight(matchID, card.getClubID(), matchTime, -200, "Blue Card",
                                ""+spnAwayPlayers.getSelectedItem().toString());
                        datasource.addHighlight(highlight);
                        clearActions();
                        Toast t =  Toast.makeText(tzlc_stats_add_tzlc12n.this, ""+datasource.getClub( m.getAwayClubID()).getClubShortName() + " : Blue Card Added", Toast.LENGTH_SHORT);
                        t.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL,0,0);
                        t.show();
                    }else
                    {
                        Toast t =  Toast.makeText(tzlc_stats_add_tzlc12n.this, "Error !!! Please select Player.", Toast.LENGTH_SHORT);
                        t.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL,0,0);
                        t.show();
                    }
                }

                //Code for Assist
                RadioButton hSub = findViewById(R.id.rdbutHSub);
                RadioButton aSub = findViewById(R.id.rdbutASub);
                if(hSub.isChecked())
                {
                    Highlight highlight = new Highlight(matchID, m.getHomeClubID(), matchTime, -100, "Sub", "");
                    datasource.addHighlight(highlight);
                    clearActions();
                    Toast t =  Toast.makeText(tzlc_stats_add_tzlc12n.this, ""+datasource.getClub( m.getHomeClubID()).getClubShortName() + " : Sub Added", Toast.LENGTH_SHORT);
                    t.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL,0,0);
                    t.show();
                }
                if(aSub.isChecked())
                {
                    Highlight highlight = new Highlight(matchID, m.getAwayClubID(), matchTime, -100, "Sub", "");
                    datasource.addHighlight(highlight);
                    clearActions();
                    Toast t =  Toast.makeText(tzlc_stats_add_tzlc12n.this, ""+datasource.getClub( m.getAwayClubID()).getClubShortName() + " : Sub Added", Toast.LENGTH_SHORT);
                    t.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL,0,0);
                    t.show();
                }

            }
        });




        t1 = findViewById(R.id.tog1);
        t1.setText(homeClubName);
        t1.setTextSize(30);
        t1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {

                    t2.setText("");
                    t2.setTextOff("");
                    t2.setTextOn("");
                    t3.setText("");
                    t3.setTextOff("");
                    t3.setTextOn("");
                    t4.setText("");
                    t4.setTextOff("");
                    t4.setTextOn("");
                    /*t2.setChecked(false);
                    t3.setChecked(false);
                    t4.setChecked(false);*/

                    if(resetCount > 0)
                    {
                        possesionTimers.add(new possesionTimer("A1", matchTime));
                        t1.setTextOn(awayClubName);
                        t1.setTextColor(awayColor);
                        lastPossession="A1";
                    }else {
                        possesionTimers.add(new possesionTimer("H1", matchTime));
                        t1.setTextOn(homeClubName);
                        t1.setTextColor(homeColor);
                        lastPossession="H1";
                    }
                }
                else
                {

                    t2.setText("");
                    t2.setTextOff("");
                    t2.setTextOn("");
                    t3.setText("");
                    t3.setTextOff("");
                    t3.setTextOn("");
                    t4.setText("");
                    t4.setTextOff("");
                    t4.setTextOn("");
                    /*t2.setChecked(false);
                    t3.setChecked(false);
                    t4.setChecked(false);*/

                    if(resetCount > 0)
                    {
                        possesionTimers.add(new possesionTimer("H4", matchTime));
                        t1.setTextOff(homeClubName);
                        t1.setTextColor(homeColor);
                        lastPossession="H4";
                    }else {
                        possesionTimers.add(new possesionTimer("A4", matchTime));
                        t1.setTextOff(awayClubName);
                        t1.setTextColor(awayColor);
                        lastPossession="A4";
                    }
                }

            }
        });

        t2 = findViewById(R.id.tog2);
        t2.setText(homeClubName);
        t2.setTextSize(30);
        t2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    t1.setText("");
                    t1.setTextOff("");
                    t1.setTextOn("");
                    t3.setText("");
                    t3.setTextOff("");
                    t3.setTextOn("");
                    t4.setText("");
                    t4.setTextOff("");
                    t4.setTextOn("");
                    /*t1.setChecked(false);
                    t3.setChecked(false);
                    t4.setChecked(false);*/



                    if(resetCount > 0)
                    {
                        possesionTimers.add(new possesionTimer("A2", matchTime));
                        t2.setTextOn(awayClubName);
                        t2.setTextColor(awayColor);
                        lastPossession="A2";
                    }else {
                        possesionTimers.add(new possesionTimer("H2", matchTime));
                        t2.setTextOn(homeClubName);
                        t2.setTextColor(homeColor);
                        lastPossession="H2";
                    }


                }
                else
                {
                    t1.setText("");
                    t1.setTextOff("");
                    t1.setTextOn("");
                    t3.setText("");
                    t3.setTextOff("");
                    t3.setTextOn("");
                    t4.setText("");
                    t4.setTextOff("");
                    t4.setTextOn("");
                    /*t1.setChecked(false);
                    t3.setChecked(false);
                    t4.setChecked(false);*/

                    if(resetCount > 0)
                    {
                        possesionTimers.add(new possesionTimer("H3", matchTime));
                        t2.setTextOff(homeClubName);
                        t2.setTextColor(homeColor);
                        lastPossession="H3";

                    }else {
                        possesionTimers.add(new possesionTimer("A3", matchTime));
                        t2.setTextOff(awayClubName);
                        t2.setTextColor(awayColor);
                        lastPossession="A3";
                    }

                }

            }
        });

        t3 = findViewById(R.id.tog3);
        t3.setText(awayClubName);
        t3.setTextSize(30);
        t3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {

                    t1.setText("");
                    t1.setTextOff("");
                    t1.setTextOn("");
                    t2.setText("");
                    t2.setTextOff("");
                    t2.setTextOn("");
                    t4.setText("");
                    t4.setTextOff("");
                    t4.setTextOn("");
                    /*t1.setChecked(false);
                    t2.setChecked(false);
                    t4.setChecked(false);*/


                    if(resetCount > 0)
                    {
                        possesionTimers.add(new possesionTimer("A3", matchTime));
                        t3.setTextOn(awayClubName);
                        t3.setTextColor(awayColor);
                        lastPossession="A3";

                    }else {
                        possesionTimers.add(new possesionTimer("H3", matchTime));
                        t3.setTextOn(homeClubName);
                        t3.setTextColor(homeColor);
                        lastPossession="H3";
                    }
                }
                else
                {

                    t1.setText("");
                    t1.setTextOff("");
                    t1.setTextOn("");
                    t2.setText("");
                    t2.setTextOff("");
                    t2.setTextOn("");
                    t4.setText("");
                    t4.setTextOff("");
                    t4.setTextOn("");
                    /*t1.setChecked(false);
                    t2.setChecked(false);
                    t4.setChecked(false);*/



                    if(resetCount > 0)
                    {
                        possesionTimers.add(new possesionTimer("H2", matchTime));
                        t3.setTextOff(homeClubName);
                        t3.setTextColor(homeColor);
                        lastPossession="H2";
                    }else {
                        possesionTimers.add(new possesionTimer("A2", matchTime));
                        t3.setTextOff(awayClubName);
                        t3.setTextColor(awayColor);
                        lastPossession="A2";
                    }
                }

            }
        });

        t4 = findViewById(R.id.tog4);
        t4.setText(awayClubName);
        t4.setTextSize(30);
        t4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {

                    t1.setText("");
                    t1.setTextOff("");
                    t1.setTextOn("");
                    t2.setText("");
                    t2.setTextOff("");
                    t2.setTextOn("");
                    t3.setText("");
                    t3.setTextOff("");
                    t3.setTextOn("");
                    /*t1.setChecked(false);
                    t2.setChecked(false);
                    t3.setChecked(false);*/




                    if(resetCount > 0)
                    {
                        possesionTimers.add(new possesionTimer("A4", matchTime));
                        t4.setTextOn(awayClubName);
                        t4.setTextColor(awayColor);
                        lastPossession="A4";
                    }else {
                        possesionTimers.add(new possesionTimer("H4", matchTime));
                        t4.setTextOn(homeClubName);
                        t4.setTextColor(homeColor);
                        lastPossession="H4";
                    }
                }
                else
                {

                    t1.setText("");
                    t1.setTextOff("");
                    t1.setTextOn("");
                    t2.setText("");
                    t2.setTextOff("");
                    t2.setTextOn("");
                    t3.setText("");
                    t3.setTextOff("");
                    t3.setTextOn("");
                    /*t1.setChecked(false);
                    t2.setChecked(false);
                    t3.setChecked(false);*/

                    if(resetCount > 0)
                    {
                        possesionTimers.add(new possesionTimer("H1", matchTime));
                        t4.setTextOff(homeClubName);
                        t4.setTextColor(homeColor);
                        lastPossession="H1";
                    }else {

                        possesionTimers.add(new possesionTimer("A1", matchTime));
                        t4.setTextOff(awayClubName);
                        t4.setTextColor(awayColor);
                        lastPossession="A1";
                    }
                }

            }
        });



        //Stats Buttons Code starts

        homeScore = findViewById(R.id.txtstatsHomeClubScore);
        homeScore.setText(""+stat.getHome_Score());
        homeScore.setTextColor(homeColor);
        awayScore = findViewById(R.id.txtstatsAwayClubScore);
        awayScore.setText(""+stat.getAway_Score());
        awayScore.setTextColor(awayColor);


        buthomeDFK = findViewById(R.id.butstatsHomeDFKTZLC12);
        buthomeDFK.setBackgroundTintList(ColorStateList.valueOf(homeColor));
        buthomeDFK.setTextColor(homeTextColor);
        buthomeDFK.setText(""+(stat.getHome_DFK()));
        buthomeDFK.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                buthomeDFK.setText(""+(stat.getHome_DFK()+1));stat.setHome_DFK(stat.getHome_DFK()+1);
                //undo = R.id.butstatsHomeDFK; undoMenu.findItem(R.id.undo).setEnabled(true);
                datasource.updateStats(stat);
                Highlight highlight = new Highlight(matchID,m.getHomeClubID() ,matchTime, 0,"DFK", "");
                datasource.addHighlight(highlight);
            }
        });

        buthomeCor = findViewById(R.id.butstatsHomeCornerTZLC12);
        buthomeCor.setBackgroundTintList(ColorStateList.valueOf(homeColor));
        buthomeCor.setTextColor(homeTextColor);
        buthomeCor.setText(""+(stat.getHome_COR()));
        buthomeCor.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                buthomeCor.setText(""+(stat.getHome_COR()+1));stat.setHome_COR(stat.getHome_COR()+1);
                //undo=R.id.butstatsHomeCor;undoMenu.findItem(R.id.undo).setEnabled(true);
                datasource.updateStats(stat);
                Highlight highlight = new Highlight(matchID,m.getHomeClubID() ,matchTime, 0,"Cornor", "");
                datasource.addHighlight(highlight);
            }
        });

        buthomeGK = findViewById(R.id.butstatsHomeGKTZLC12);
        buthomeGK.setBackgroundTintList(ColorStateList.valueOf(homeColor));
        buthomeGK.setTextColor(homeTextColor);
        buthomeGK.setText(""+(stat.getHome_GK()));
        buthomeGK.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                buthomeGK.setText(""+(stat.getHome_GK()+1));stat.setHome_GK(stat.getHome_GK()+1);
                //undo=R.id.butstatsHomeTI;undoMenu.findItem(R.id.undo).setEnabled(true);
                datasource.updateStats(stat);
                Highlight highlight = new Highlight(matchID,m.getHomeClubID() ,matchTime, 0,"GK", "");
                datasource.addHighlight(highlight);
            }
        });

        buthomeOff = findViewById(R.id.butstatsHomeOffTZLC12);
        buthomeOff.setBackgroundTintList(ColorStateList.valueOf(homeColor));
        buthomeOff.setTextColor(homeTextColor);
        buthomeOff.setText(""+(stat.getHome_OFF()));
        buthomeOff.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                buthomeOff.setText(""+(stat.getHome_OFF()+1));stat.setHome_OFF(stat.getHome_OFF()+1);
                //undo=R.id.butstatsHomeTI;undoMenu.findItem(R.id.undo).setEnabled(true);
                datasource.updateStats(stat);
                Highlight highlight = new Highlight(matchID,m.getHomeClubID() ,matchTime, 0,"Offside", "");
                datasource.addHighlight(highlight);
            }
        });

        buthomeLC = findViewById(R.id.butstatsHomeLCTZLC12);
        buthomeLC.setBackgroundTintList(ColorStateList.valueOf(homeColor));
        buthomeLC.setTextColor(homeTextColor);
        buthomeLC.setText(""+(stat.getHome_LC()));
        buthomeLC.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                buthomeLC.setText(""+(stat.getHome_LC()+1));stat.setHome_LC(stat.getHome_LC()+1);
                //undo=R.id.butstatsHomeLC;undoMenu.findItem(R.id.undo).setEnabled(true);
                datasource.updateStats(stat);
                Highlight highlight = new Highlight(matchID,m.getHomeClubID() ,matchTime, 0,"Late Challange", "");
                datasource.addHighlight(highlight);
            }
        });

        buthomeTI = findViewById(R.id.butstatsHomeThrowInTZLC12);
        buthomeTI.setBackgroundTintList(ColorStateList.valueOf(homeColor));
        buthomeTI.setTextColor(homeTextColor);
        buthomeTI.setText(""+(stat.getHome_TI()));
        buthomeTI.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                buthomeTI.setText(""+(stat.getHome_TI()+1));stat.setHome_TI(stat.getHome_TI()+1);
                //undo=R.id.butstatsHomeTI;undoMenu.findItem(R.id.undo).setEnabled(true);
                datasource.updateStats(stat);
                Highlight highlight = new Highlight(matchID,m.getHomeClubID() ,matchTime, 0,"Throw In", "");
                datasource.addHighlight(highlight);
            }
        });


        buthomePOPScored = findViewById(R.id.butstatsHomePOPScore);
        buthomePOPScored.setBackgroundTintList(ColorStateList.valueOf(homeColor));
        buthomePOPScored.setTextColor(homeTextColor);
        buthomePOPScored.setText(""+(stat.getHome_POP()));
        buthomePOPScored.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                buthomePOPScored.setText(""+(stat.getHome_POP()+1));stat.setHome_POP(stat.getHome_POP()+1);
                //undo=R.id.butstatsHomePOPScore;undoMenu.findItem(R.id.undo).setEnabled(true);
                datasource.updateStats(stat);
                Highlight highlight = new Highlight(matchID,m.getHomeClubID() ,matchTime, 0,"POP Scored", "");
                datasource.addHighlight(highlight);
            }
        });

        buthomePOPMissed = findViewById(R.id.butstatsHomePOPMiss);
        buthomePOPMissed.setBackgroundTintList(ColorStateList.valueOf(homeColor));
        buthomePOPMissed.setTextColor(homeTextColor);
        buthomePOPMissed.setText(""+(stat.getHome_TCK())); //Using tackle variable to store POP miss
        buthomePOPMissed.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                buthomePOPMissed.setText(""+(stat.getHome_TCK()+1));stat.setHome_TCK(stat.getHome_TCK()+1);
                //undo=R.id.butstatsHomePOPMiss;undoMenu.findItem(R.id.undo).setEnabled(true);
                datasource.updateStats(stat);
                Highlight highlight = new Highlight(matchID,m.getHomeClubID() ,matchTime, 0,"POP Missed", "");
                datasource.addHighlight(highlight);
            }
        });


        //Away Clubs stats start from here **************************************************
        butawayDFK = findViewById(R.id.butstatsAwayDFKTZLC12);
        butawayDFK.setBackgroundTintList(ColorStateList.valueOf(awayColor));
        butawayDFK.setTextColor(awayTextColor);
        butawayDFK.setText(""+(stat.getAway_DFK()));
        butawayDFK.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                butawayDFK.setText(""+(stat.getAway_DFK()+1));stat.setAway_DFK(stat.getAway_DFK()+1);
                //undo=R.id.butstatsAwayDFK;undoMenu.findItem(R.id.undo).setEnabled(true);
                datasource.updateStats(stat);
                Highlight highlight = new Highlight(matchID,m.getAwayClubID() ,matchTime, 0,"DFK", "");
                datasource.addHighlight(highlight);
            }
        });

        butawayCor = findViewById(R.id.butstatsAwayCornerTZLC12);
        butawayCor.setBackgroundTintList(ColorStateList.valueOf(awayColor));
        butawayCor.setTextColor(awayTextColor);
        butawayCor.setText(""+(stat.getAway_COR()));
        butawayCor.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                butawayCor.setText(""+(stat.getAway_COR()+1));stat.setAway_COR(stat.getAway_COR()+1);
                //undo=R.id.butstatsAwayCor;undoMenu.findItem(R.id.undo).setEnabled(true);
                datasource.updateStats(stat);
                Highlight highlight = new Highlight(matchID,m.getAwayClubID() ,matchTime, 0,"Cornor", "");
                datasource.addHighlight(highlight);
            }
        });

        butawayLC = findViewById(R.id.butstatsAwayLCTZLC12);
        butawayLC.setBackgroundTintList(ColorStateList.valueOf(awayColor));
        butawayLC.setTextColor(awayTextColor);
        butawayLC.setText(""+(stat.getAway_LC()));
        butawayLC.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                butawayLC.setText(""+(stat.getAway_LC()+1));stat.setAway_LC(stat.getAway_LC()+1);
                //undo=R.id.butstatsAwayLC;undoMenu.findItem(R.id.undo).setEnabled(true);
                datasource.updateStats(stat);
                Highlight highlight = new Highlight(matchID,m.getAwayClubID() ,matchTime, 0,"Late Challange", "");
                datasource.addHighlight(highlight);
            }
        });

        butawayGK = findViewById(R.id.butstatsAwayGKTZLC12);
        butawayGK.setBackgroundTintList(ColorStateList.valueOf(awayColor));
        butawayGK.setTextColor(awayTextColor);
        butawayGK.setText(""+(stat.getAway_GK()));
        butawayGK.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                butawayGK.setText(""+(stat.getAway_GK()+1));stat.setAway_GK(stat.getAway_GK()+1);
                //undo=R.id.butstatsAwayLC;undoMenu.findItem(R.id.undo).setEnabled(true);
                datasource.updateStats(stat);
                Highlight highlight = new Highlight(matchID,m.getAwayClubID() ,matchTime, 0,"GK", "");
                datasource.addHighlight(highlight);
            }
        });

        butawayOff = findViewById(R.id.butstatsAwayOffTZLC12);
        butawayOff.setBackgroundTintList(ColorStateList.valueOf(awayColor));
        butawayOff.setTextColor(awayTextColor);
        butawayOff.setText(""+(stat.getAway_OFF()));
        butawayOff.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                butawayOff.setText(""+(stat.getAway_OFF()+1));stat.setAway_OFF(stat.getAway_OFF()+1);
                //undo=R.id.butstatsAwayLC;undoMenu.findItem(R.id.undo).setEnabled(true);
                datasource.updateStats(stat);
                Highlight highlight = new Highlight(matchID,m.getAwayClubID() ,matchTime, 0,"Offside", "");
                datasource.addHighlight(highlight);
            }
        });

        butawayTI = findViewById(R.id.butstatsAwayThrowInTZLC12);
        butawayTI.setBackgroundTintList(ColorStateList.valueOf(awayColor));
        butawayTI.setTextColor(awayTextColor);
        butawayTI.setText(""+(stat.getAway_TI()));
        butawayTI.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                butawayTI.setText(""+(stat.getAway_TI()+1));stat.setAway_TI(stat.getAway_TI()+1);
                //undo=R.id.butstatsAwayTI;undoMenu.findItem(R.id.undo).setEnabled(true);
                datasource.updateStats(stat);
                Highlight highlight = new Highlight(matchID,m.getAwayClubID() ,matchTime, 0,"Throw In", "");
                datasource.addHighlight(highlight);
            }
        });

        butawayPOPScored = findViewById(R.id.butstatsAwayPOPScore);
        butawayPOPScored.setBackgroundTintList(ColorStateList.valueOf(awayColor));
        butawayPOPScored.setTextColor(awayTextColor);
        butawayPOPScored.setText(""+(stat.getAway_POP()));
        butawayPOPScored.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                butawayPOPScored.setText(""+(stat.getAway_POP()+1));stat.setAway_POP(stat.getAway_POP()+1);
                //undo=R.id.butstatsAwayPOPScore;undoMenu.findItem(R.id.undo).setEnabled(true);
                datasource.updateStats(stat);
                Highlight highlight = new Highlight(matchID,m.getAwayClubID() ,matchTime, 0,"POP Scored", "");
                datasource.addHighlight(highlight);
            }
        });

        butawayPOPMissed = findViewById(R.id.butstatsAwayPOPMiss);
        butawayPOPMissed.setBackgroundTintList(ColorStateList.valueOf(awayColor));
        butawayPOPMissed.setTextColor(awayTextColor);
        butawayPOPMissed.setText(""+(stat.getAway_TCK())); //use Tackle stat to store POP Miss
        butawayPOPMissed.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                butawayPOPMissed.setText(""+(stat.getAway_TCK()+1));stat.setAway_TCK(stat.getAway_TCK()+1);
               // undo=R.id.butstatsAwayPOPMiss;undoMenu.findItem(R.id.undo).setEnabled(true);
                datasource.updateStats(stat);
                Highlight highlight = new Highlight(matchID,m.getAwayClubID() ,matchTime, 0,"POP Missed", "");
                datasource.addHighlight(highlight);
            }
        });


        startPause = findViewById(R.id.butstatsPauseTZLC12);
        startPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(start)
                {
                    startPause.setImageResource(R.drawable.pause);
                    buthomeDFK.setEnabled(true);
                    buthomeCor.setEnabled(true);
                    buthomeLC.setEnabled(true);
                    buthomeTI.setEnabled(true);
                    buthomeGK.setEnabled(true);
                    buthomeOff.setEnabled(true);
                    buthomePOPScored.setEnabled(true);
                    buthomePOPMissed.setEnabled(true);

                    butawayDFK.setEnabled(true);
                    butawayCor.setEnabled(true);
                    butawayLC.setEnabled(true);
                    butawayTI.setEnabled(true);
                    butawayGK.setEnabled(true);
                    butawayOff.setEnabled(true);
                    butawayPOPScored.setEnabled(true);
                    butawayPOPMissed.setEnabled(true);

                    matchStartTime = SystemClock.uptimeMillis();
                    matchTimeHandler.postDelayed(updateMatchTimerThread,0);
                    start =false;
                    awayPossession = !awayPossession;
                }
                else{
                    startPause.setImageResource(R.drawable.start);

                    buthomeDFK.setEnabled(false);
                    buthomeCor.setEnabled(false);
                    buthomeLC.setEnabled(false);
                    buthomeTI.setEnabled(false);
                    buthomeGK.setEnabled(false);
                    buthomeOff.setEnabled(false);
                    buthomePOPScored.setEnabled(false);
                    buthomePOPMissed.setEnabled(false);

                    butawayDFK.setEnabled(false);
                    butawayCor.setEnabled(false);
                    butawayLC.setEnabled(false);
                    butawayTI.setEnabled(false);
                    butawayGK.setEnabled(false);
                    butawayOff.setEnabled(false);
                    butawayPOPScored.setEnabled(false);
                    butawayPOPMissed.setEnabled(false);

                    matchSwapBuff += matchTimeInMillisecond;
                    matchTimeHandler.removeCallbacks(updateMatchTimerThread);
                    start = true;


                }
            }
        });
    }

    private Runnable updateMatchTimerThread = new Runnable() {
        @Override
        public void run() {
            matchTimeInMillisecond= SystemClock.uptimeMillis() - matchStartTime;
            matchUpdatedTime = matchSwapBuff + matchTimeInMillisecond;
            int sec = (int) (matchUpdatedTime / 1000);
            int min = sec / 60;
            timerMatch.setText("" + String.format("%02d", min) + " : " + String.format("%02d", (sec % 60)));
            stat.setMatchTime(sec);
            matchTime = (min*60)+(sec % 60);
            matchTimeHandler.postDelayed(updateMatchTimerThread,0);
        }
    };

    @Override
    protected void onResume() {
        datasource.open();
        datasource.updateStats(stat);
        if((homeColor != temphomeColor) || (awayColor != tempawayColor))
            updateScreenColor(temphomeColor,tempawayColor);
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        datasource.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tzlc_stats_menu_tzlc12,menu);
        //undoMenu = menu;
        return true;
        //return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Bundle b = data.getExtras();
        matchID = b.getLong("matchID",-1);
        //goalID = b.getLong("goalID",-1);
        temphomeColor = b.getInt("homeColor",temphomeColor);
        tempawayColor = b.getInt("awayColor",tempawayColor);
    }

    @Override
    public void onBackPressed() {
        DialogInterface.OnClickListener dialog = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which)
                {
                    case DialogInterface.BUTTON_POSITIVE : break;
                    case DialogInterface.BUTTON_NEGATIVE : break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(tzlc_stats_add_tzlc12n.this);
        builder.setMessage("Please save Stats to exit this screen !!").setPositiveButton("Ok",dialog).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        switch (id) {
            case R.id.saveStatstalc12:

                DialogInterface.OnClickListener dialog = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which)
                        {
                            case DialogInterface.BUTTON_POSITIVE :
                                stat.setMatchID(m.getId());
                                stat.setMatchTime(matchTime);
                                stat.setHome_TIME((stat.getHome_TIME()*1000)+completedPassesHome);
                                stat.setAway_TIME((stat.getAway_TIME()*1000)+completedPassesAway);
                                stat.setHome_Score(Integer.parseInt(homeScore.getText().toString()));
                                stat.setAway_Score(Integer.parseInt(awayScore.getText().toString()));
                                possesionTimers.add(new possesionTimer(lastPossession, matchTime));
                                List<possesionTimer> finalTime = new ArrayList<possesionTimer>();
                                int i =0;
                                for(i = 0; i<(possesionTimers.size()-1);i++)
                                {
                                    if(possesionTimers.get(i+1).getTimer() < possesionTimers.get(i).getTimer())
                                        finalTime.add(new possesionTimer(possesionTimers.get(i).getLocation(), 0));
                                    else
                                        finalTime.add(new possesionTimer(possesionTimers.get(i).getLocation(), possesionTimers.get(i+1).getTimer()-possesionTimers.get(i).getTimer()));
                                }

                                h1=0;h2=0;h3=0;h4=0;a1=0;a2=0;a3=0;a4=0;

                                for (possesionTimer possesionTimer : finalTime) {
                                    Log.d(tzlc_stats_add_tzlc12n.class.getSimpleName(), ""+possesionTimer.getLocation()+" :: " + possesionTimer.getTimer());
                                    switch (possesionTimer.getLocation())
                                    {
                                        case "H1" : h1=h1+possesionTimer.getTimer();break;
                                        case "H2" : h2=h2+possesionTimer.getTimer();break;
                                        case "H3" : h3=h3+possesionTimer.getTimer();break;
                                        case "H4" : h4=h4+possesionTimer.getTimer();break;
                                        case "A1" : a1=a1+possesionTimer.getTimer();break;
                                        case "A2" : a2=a2+possesionTimer.getTimer();break;
                                        case "A3" : a3=a3+possesionTimer.getTimer();break;
                                        case "A4" : a4=a4+possesionTimer.getTimer();break;
                                    }
                                }
                                stat.setHome_H1(h1);
                                stat.setHome_H2(h2);
                                stat.setHome_H3(h3);
                                stat.setHome_H4(h4);

                                stat.setAway_A1(a1);
                                stat.setAway_A2(a2);
                                stat.setAway_A3(a3);
                                stat.setAway_A4(a4);

                                datasource.addStats(stat);

                                Intent returnI = new Intent();
                                Bundle extras = new Bundle();
                                extras.putLong("matchID", matchID);
                                returnI.putExtras(extras);
                                setResult(100, returnI);
                                finish();
                                break;
                            case DialogInterface.BUTTON_NEGATIVE : break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(tzlc_stats_add_tzlc12n.this);
                builder.setMessage("Do you want to save Match Stats ???").setNegativeButton("No",dialog).setPositiveButton("Yes",dialog).show();
                break;

            /*case R.id.undotzlc12 : undoHandler(undo);item.setEnabled(false);  break;*/

            case R.id.colortzlc12 : Intent jerseyColor = new Intent(tzlc_stats_add_tzlc12n.this, tzlc_change_jersey_color.class);
                Bundle extras  = new Bundle();
                extras.putLong("matchID", matchID);
                extras.putInt("homeColor", homeColor);
                extras.putString("homeClubName",datasource.getClub( m.getHomeClubID()).getClubShortName());
                extras.putInt("awayColor", awayColor);
                extras.putString("awayClubName",datasource.getClub( m.getAwayClubID()).getClubShortName());
                jerseyColor.putExtras(extras);
                startActivityForResult(jerseyColor,100);
                break;

            case R.id.resetTimerstzlc12 :

                DialogInterface.OnClickListener resetdialog = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which)
                        {
                            case DialogInterface.BUTTON_POSITIVE :
                                matchSwapBuff = 0L;
                                matchStartTime = SystemClock.uptimeMillis();
                                timerMatch.setText("" + String.format("%02d", 0) + " : " + String.format("%02d", 0));
                                if (matchTime < 2700)
                                    halftime = 2400;
                                else
                                    halftime = 2700;
                                halftime=0;
                                String resetReason [] = getResources().getStringArray(R.array.resetTime);
                                Highlight highlight = new Highlight(matchID,-1 , matchTime,0 , resetReason[resetCount],"--NA--");
                                datasource.addHighlight(highlight);



                                if(resetCount ==0 )
                                {
                                    possesionTimers.add(new possesionTimer(lastPossession, matchTime));
                                    List<possesionTimer> finalTime = new ArrayList<possesionTimer>();
                                    int i =0;
                                    for(i = 0; i<(possesionTimers.size()-1);i++)
                                    {
                                        finalTime.add(new possesionTimer(possesionTimers.get(i).getLocation(), possesionTimers.get(i+1).getTimer()-possesionTimers.get(i).getTimer()));
                                    }


                                    for (possesionTimer possesionTimer : finalTime) {
                                        Log.d(tzlc_stats_add_tzlc12n.class.getSimpleName(), ""+possesionTimer.getLocation()+" :: " + possesionTimer.getTimer());
                                        switch (possesionTimer.getLocation())
                                        {
                                            case "H1" : h1=h1+possesionTimer.getTimer();break;
                                            case "H2" : h2=h2+possesionTimer.getTimer();break;
                                            case "H3" : h3=h3+possesionTimer.getTimer();break;
                                            case "H4" : h4=h4+possesionTimer.getTimer();break;
                                            case "A1" : a1=a1+possesionTimer.getTimer();break;
                                            case "A2" : a2=a2+possesionTimer.getTimer();break;
                                            case "A3" : a3=a3+possesionTimer.getTimer();break;
                                            case "A4" : a4=a4+possesionTimer.getTimer();break;
                                        }
                                    }

                                    hh1 = h1;
                                    hh2 = h2;
                                    hh3 = h3;
                                    hh4 = h4;
                                    ha1 = a1;
                                    ha2 = a2;
                                    ha3 = a3;
                                    ha4 = a4;
                                }else
                                {
                                    t1.setEnabled(false);
                                    t2.setEnabled(false);
                                    t3.setEnabled(false);
                                    t4.setEnabled(false);
                                }
                                resetCount++;
                                break;
                            case DialogInterface.BUTTON_NEGATIVE : break;
                        }
                    }
                };
                AlertDialog.Builder resetbuilder = new AlertDialog.Builder(tzlc_stats_add_tzlc12n.this);
                resetbuilder.setTitle("Alert !!");
                resetbuilder.setMessage("Are you sure you want to reset Timer ??").setPositiveButton("Yes",resetdialog).setNegativeButton("No",resetdialog).show();

                break;
            case R.id.pausestarttzlc12 :
                if(start)
                {
                    item.setIcon(R.drawable.pause);

                    buthomeDFK.setEnabled(true);
                    buthomeCor.setEnabled(true);
                    buthomeLC.setEnabled(true);
                    buthomeTI.setEnabled(true);
                    buthomeGK.setEnabled(true);
                    buthomeOff.setEnabled(true);
                    buthomePOPScored.setEnabled(true);
                    buthomePOPMissed.setEnabled(true);

                    butawayDFK.setEnabled(true);
                    butawayCor.setEnabled(true);
                    butawayLC.setEnabled(true);
                    butawayTI.setEnabled(true);
                    butawayGK.setEnabled(true);
                    butawayOff.setEnabled(true);
                    butawayPOPScored.setEnabled(true);
                    butawayPOPMissed.setEnabled(true);

                    matchStartTime = SystemClock.uptimeMillis();
                    matchTimeHandler.postDelayed(updateMatchTimerThread,0);
                    start =false;
                    awayPossession = !awayPossession;
                }
                else{
                    item.setIcon(R.drawable.start);

                    buthomeDFK.setEnabled(false);
                    buthomeCor.setEnabled(false);
                    buthomeLC.setEnabled(false);
                    buthomeTI.setEnabled(false);
                    buthomeGK.setEnabled(false);
                    buthomeOff.setEnabled(false);
                    buthomePOPScored.setEnabled(false);
                    buthomePOPMissed.setEnabled(false);

                    butawayDFK.setEnabled(false);
                    butawayCor.setEnabled(false);
                    butawayLC.setEnabled(false);
                    butawayTI.setEnabled(false);
                    butawayGK.setEnabled(false);
                    butawayOff.setEnabled(false);
                    butawayPOPScored.setEnabled(false);
                    butawayPOPMissed.setEnabled(false);

                    matchSwapBuff += matchTimeInMillisecond;
                    matchTimeHandler.removeCallbacks(updateMatchTimerThread);
                    start = true;

                    /*DialogInterface.OnClickListener pausedialog = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which)
                            {
                                case DialogInterface.BUTTON_POSITIVE : startPause.callOnClick(); break;
                                case DialogInterface.BUTTON_NEGATIVE : start = false; startPause.callOnClick(); break;
                            }
                        }
                    };
                    AlertDialog.Builder pausebuilder = new AlertDialog.Builder(tzlc_stats_add_tzlc12n.this);
                    pausebuilder.setMessage("Match Paused").setPositiveButton("Start match",pausedialog).setNegativeButton("Pause",pausedialog).show();
                    */

                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
