package com.kfandra.tzlc.tzlc;

import android.content.DialogInterface;
import android.content.Intent;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Queue;

public class tzlc_stats_add extends AppCompatActivity {
    private tzlcDataSource datasource;
    private long matchID,goalID,cardID;
    private Stats stat;
    private Button buthomeDFK, buthomeCor, buthomeGK, buthomeSOnT, buthomeSOffT, buthomeLC, buthomeTCK, buthomeTI, buthomeOff, buthomePOP;
    private Button butawayDFK, butawayCor, butawayGK, butawaySOnT, butawaySOffT, butawayLC, butawayTCK, butawayTI, butawayOff, butawayPOP;
    private ImageButton startPause, resetTimer;
    private FloatingActionButton possisionChange;
    private FloatingActionButton buthomePass, butawayPass;
    private long homeStartTime = 0L;
    private long awayStartTime = 0L;
    private long matchStartTime = 0L;
    private Handler homeTimeHandler = new Handler();
    private Handler awayTimeHandler = new Handler();
    private Handler matchTimeHandler = new Handler();
    long homeTimeInMillisecond = 0L;
    long awayTimeInMillisecond = 0L;
    long matchTimeInMillisecond = 0L;
    long homeSwapBuff = 0L;
    long awaySwapBuff = 0L;
    long matchSwapBuff = 0L;
    long homeUpdatedTime = 0L;
    long awayUpdatedTime = 0L;
    long matchUpdatedTime = 0L;
    private TextView homePasses;
    private TextView awayPasses;
    private TextView timerMatch;
    private TextView homeScore, awayScore;
    int matchTime = 0;
    public int completedPassesHome=0,completedPassesAway=0;
    int homeTime = 0,htHomeTime=0;
    int awayTime = 0, htAwayTime=0;
    private long    goalAgainstClubId;
    Match m;
    int homeColor, awayColor;
    int temphomeColor, tempawayColor;
    int homeTextColor,awayTextColor;
    ImageButton highlightButton, cardButton, goalButton,subsButton;
    private int undo;
    private Menu undoMenu;
    private boolean awayPossession=false;
    private boolean start=true;
    int halftime=0, resetCount =0;
    private Queue<Integer> cardTimes;
    private Queue<Integer> cardTtypes;
    public TextView homeClub, awayClub;

    public boolean isColorDark(int color){
        double darkness = 1-(0.299*Color.red(color) + 0.587*Color.green(color) + 0.114*Color.blue(color))/255;
        if(darkness<0.5){
            return false; // It's a light color
        }else{
            return true; // It's a dark color
        }
    }

    public void updateScreenColor(int tempHColor, int tempAColor)
    {

        int temphomeTextColor = (isColorDark(tempHColor)?Color.WHITE:Color.BLACK);
        int tempawayTextColor = (isColorDark(tempAColor)?Color.WHITE:Color.BLACK);

        buthomeDFK.setBackgroundTintList(ColorStateList.valueOf(tempHColor));buthomeDFK.setTextColor(temphomeTextColor);
        buthomeCor.setBackgroundTintList(ColorStateList.valueOf(tempHColor));buthomeCor.setTextColor(temphomeTextColor);
        buthomeGK.setBackgroundTintList(ColorStateList.valueOf(tempHColor));buthomeGK.setTextColor(temphomeTextColor);
        buthomeSOnT.setBackgroundTintList(ColorStateList.valueOf(tempHColor));buthomeSOnT.setTextColor(temphomeTextColor);
        buthomeSOffT.setBackgroundTintList(ColorStateList.valueOf(tempHColor));buthomeSOffT.setTextColor(temphomeTextColor);
        buthomeLC.setBackgroundTintList(ColorStateList.valueOf(tempHColor));buthomeLC.setTextColor(temphomeTextColor);
        buthomeTCK.setBackgroundTintList(ColorStateList.valueOf(tempHColor));buthomeTCK.setTextColor(temphomeTextColor);
        buthomeTI.setBackgroundTintList(ColorStateList.valueOf(tempHColor));buthomeTI.setTextColor(temphomeTextColor);
        buthomeOff.setBackgroundTintList(ColorStateList.valueOf(tempHColor));buthomeOff.setTextColor(temphomeTextColor);
        buthomePOP.setBackgroundTintList(ColorStateList.valueOf(tempHColor));buthomePOP.setTextColor(temphomeTextColor);
        buthomePOP.setBackgroundTintList(ColorStateList.valueOf(tempHColor));
        homePasses.setTextColor(tempHColor);
        TextView homeClub = findViewById(R.id.txtstatsHomeClub);
        TextView homeClub1 = findViewById(R.id.txtHomeClub1);
        TextView homeClub2 = findViewById(R.id.txtHomeClub2);
        homeScore.setTextColor(tempHColor);
        homeClub.setTextColor(tempHColor);
        homeClub1.setTextColor(tempHColor);
        homeClub2.setTextColor(tempHColor);


        butawayDFK.setBackgroundTintList(ColorStateList.valueOf(tempAColor));butawayDFK.setTextColor(tempawayTextColor);
        butawayCor.setBackgroundTintList(ColorStateList.valueOf(tempAColor));butawayCor.setTextColor(tempawayTextColor);
        butawayGK.setBackgroundTintList(ColorStateList.valueOf(tempAColor));butawayGK.setTextColor(tempawayTextColor);
        butawaySOnT.setBackgroundTintList(ColorStateList.valueOf(tempAColor));butawaySOnT.setTextColor(tempawayTextColor);
        butawaySOffT.setBackgroundTintList(ColorStateList.valueOf(tempAColor));butawaySOffT.setTextColor(tempawayTextColor);
        butawayLC.setBackgroundTintList(ColorStateList.valueOf(tempAColor));butawayLC.setTextColor(tempawayTextColor);
        butawayTCK.setBackgroundTintList(ColorStateList.valueOf(tempAColor));butawayTCK.setTextColor(tempawayTextColor);
        butawayTI.setBackgroundTintList(ColorStateList.valueOf(tempAColor));butawayTI.setTextColor(tempawayTextColor);
        butawayOff.setBackgroundTintList(ColorStateList.valueOf(tempAColor));butawayOff.setTextColor(tempawayTextColor);
        butawayPOP.setBackgroundTintList(ColorStateList.valueOf(tempAColor));butawayPOP.setTextColor(tempawayTextColor);
        butawayPass.setBackgroundTintList(ColorStateList.valueOf(tempAColor));
        awayPasses.setTextColor(tempAColor);
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
        setContentView(R.layout.activity_tzlc_stats_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Bundle b = getIntent().getExtras();
        matchID = b.getLong("matchID",-1);
        goalAgainstClubId = b.getLong("clubID",-1);
        int colors [] = getResources().getIntArray(R.array.androidcolors);




        Log.d(tzlc_goal_add.class.getSimpleName(), "Match Time : " + matchTime);
        datasource= new tzlcDataSource(this);
        datasource.open();

        homePasses = findViewById(R.id.txtstatsHomePasses);
        awayPasses = findViewById(R.id.txtstatsAwayPasses);

        //stat = new Stats();
        stat = datasource.getAllStatsForMatch(matchID);
        homePasses.setText(""+((stat.getHome_TIME()/1000)));stat.setHome_TIME((stat.getHome_TIME()/1000));
        awayPasses.setText(""+((stat.getAway_TIME()/1000)));stat.setAway_TIME((stat.getAway_TIME()/1000));
        //undo=R.id.butHomePasses;undoMenu.findItem(R.id.undo).setEnabled(true);
        datasource.updateStats(stat);

        //stat.setAway_Score(0);
        //stat.setHome_Score(0);
        m = new Match();
        m = datasource.getMatch(matchID);

        homeColor =  datasource.getClub(m.getHomeClubID()).getClubColor();
        homeTextColor = (isColorDark(homeColor)?Color.WHITE:Color.BLACK);
        awayColor =  datasource.getClub(m.getAwayClubID()).getClubColor();
        awayTextColor = (isColorDark(awayColor)?Color.WHITE:Color.BLACK);

        temphomeColor = homeColor;
        tempawayColor = awayColor;


        homePasses.setTextColor(homeColor);

        awayPasses.setTextColor(awayColor);

        timerMatch = findViewById(R.id.txtstatsMatchTimer);

        homeStartTime = SystemClock.uptimeMillis();
        awayStartTime = SystemClock.uptimeMillis();
        matchStartTime = SystemClock.uptimeMillis();

        goalButton = findViewById(R.id.butstatsGoal);
        goalButton.setEnabled(false);
        goalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(tzlc_stats_add.this, tzlc_goal_display.class);
                Bundle extras  = new Bundle();
                extras.putLong("matchID", matchID);
                extras.putInt("matchTime",matchTime+halftime);
                /*if(halftime)
                    extras.putInt("matchTime",matchTime+2400);
                else
                    extras.putInt("matchTime",matchTime);*/
                i.putExtras(extras);
                startActivityForResult(i,100);
            }
        });

        cardButton = findViewById(R.id.butstatsCard);
        cardButton.setEnabled(false);
        cardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(tzlc_stats_add.this, tzlc_card_display.class);
                Bundle extras  = new Bundle();
                extras.putLong("matchID", matchID);
                extras.putInt("matchTime",matchTime+halftime);
                /*
                if(halftime)
                    extras.putInt("matchTime",matchTime+2400);
                else
                    extras.putInt("matchTime",matchTime);*/
                i.putExtras(extras);
                startActivityForResult(i,100);
            }
        });

        highlightButton = findViewById(R.id.butstatsHighlight);
        highlightButton.setEnabled(false);
        highlightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(tzlc_stats_add.this, tzlc_highlight_display.class);
                Bundle extras  = new Bundle();
                extras.putLong("matchID", matchID);
                extras.putInt("matchTime",matchTime+halftime);
                /*if(halftime)
                    extras.putInt("matchTime",matchTime+2400);
                else
                    extras.putInt("matchTime",matchTime);*/
                i.putExtras(extras);
                startActivityForResult(i,100);
            }
        });

        /*subsButton = findViewById(R.id.butstatsSubs);
        subsButton.setEnabled(false);
        subsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(tzlc_stats_add.this, tzlc_sub_display.class);
                Bundle extras  = new Bundle();
                extras.putLong("matchID", matchID);
                extras.putInt("matchTime",matchTime+halftime);

                i.putExtras(extras);
                startActivityForResult(i,100);
            }
        });*/








        homeClub = findViewById(R.id.txtstatsHomeClub);
        homeClub.setText(""+datasource.getClub( m.getHomeClubID()).getClubShortName());
        homeClub.setTextColor(homeColor);
        TextView homeClub1 = findViewById(R.id.txtHomeClub1);
        TextView homeClub2 = findViewById(R.id.txtHomeClub2);
        homeClub1.setText(""+datasource.getClub( m.getHomeClubID()).getClubShortName());
        homeClub1.setTextColor(homeColor);
        homeClub2.setText(""+datasource.getClub( m.getHomeClubID()).getClubShortName());
        homeClub2.setTextColor(homeColor);

        awayClub = findViewById(R.id.txtstatsAwayClub);
        awayClub.setText(""+datasource.getClub( m.getAwayClubID()).getClubShortName());
        awayClub.setTextColor(awayColor);
        TextView awayClub1 = findViewById(R.id.txtAwayClub1);
        TextView awayClub2 = findViewById(R.id.txtAwayClub2);
        awayClub1.setText(""+datasource.getClub( m.getAwayClubID()).getClubShortName());
        awayClub1.setTextColor(awayColor);
        awayClub2.setText(""+datasource.getClub( m.getAwayClubID()).getClubShortName());
        awayClub2.setTextColor(awayColor);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(tzlc_stats_add.this,R.array.matchType,android.R.layout.simple_spinner_item);
        TextView matchType = findViewById(R.id.txtstatsMatchType);
        matchType.setText(""+adapter.getItem( m.getType()));

        homeScore = findViewById(R.id.txtstatsHomeClubScore);
        homeScore.setText(""+stat.getHome_Score());
        homeScore.setTextColor(homeColor);
        awayScore = findViewById(R.id.txtstatsAwayClubScore);
        awayScore.setText(""+stat.getAway_Score());
        awayScore.setTextColor(awayColor);


        buthomeDFK = findViewById(R.id.butstatsHomeDFK);
        buthomeDFK.setBackgroundTintList(ColorStateList.valueOf(homeColor));
        buthomeDFK.setTextColor(homeTextColor);
        buthomeDFK.setText(""+(stat.getHome_DFK()));
        buthomeDFK.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                buthomeDFK.setText(""+(stat.getHome_DFK()+1));stat.setHome_DFK(stat.getHome_DFK()+1);
                undo = R.id.butstatsHomeDFK; undoMenu.findItem(R.id.undo).setEnabled(true);
                datasource.updateStats(stat);
            }
        });

        buthomeCor = findViewById(R.id.butstatsHomeCor);
        buthomeCor.setBackgroundTintList(ColorStateList.valueOf(homeColor));
        buthomeCor.setTextColor(homeTextColor);
        buthomeCor.setText(""+(stat.getHome_COR()));
        buthomeCor.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                buthomeCor.setText(""+(stat.getHome_COR()+1));stat.setHome_COR(stat.getHome_COR()+1);
                undo=R.id.butstatsHomeCor;undoMenu.findItem(R.id.undo).setEnabled(true);
                datasource.updateStats(stat);
            }
        });

        buthomeGK = findViewById(R.id.butstatsHomeGK);
        buthomeGK.setBackgroundTintList(ColorStateList.valueOf(homeColor));
        buthomeGK.setTextColor(homeTextColor);
        buthomeGK.setText(""+(stat.getHome_GK()));
        buthomeGK.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                buthomeGK.setText(""+(stat.getHome_GK()+1));stat.setHome_GK(stat.getHome_GK()+1);
                undo=R.id.butstatsHomeGK;undoMenu.findItem(R.id.undo).setEnabled(true);
                datasource.updateStats(stat);
            }
        });

        buthomeSOnT = findViewById(R.id.butstatsHomeSOnT);
        buthomeSOnT.setBackgroundTintList(ColorStateList.valueOf(homeColor));
        buthomeSOnT.setTextColor(homeTextColor);
        buthomeSOnT.setText(""+(stat.getHome_SOnT()));
        buthomeSOnT.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                buthomeSOnT.setText(""+(stat.getHome_SOnT()+1));stat.setHome_SOnT(stat.getHome_SOnT()+1);
                undo=R.id.butstatsHomeSOnT;undoMenu.findItem(R.id.undo).setEnabled(true);
                datasource.updateStats(stat);
            }
        });

        buthomeSOffT = findViewById(R.id.butstatsHomeSOffT);
        buthomeSOffT.setBackgroundTintList(ColorStateList.valueOf(homeColor));
        buthomeSOffT.setTextColor(homeTextColor);
        buthomeSOffT.setText(""+(stat.getHome_SOffT()));
        buthomeSOffT.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                buthomeSOffT.setText(""+(stat.getHome_SOffT()+1));stat.setHome_SOffT(stat.getHome_SOffT()+1);
                undo=R.id.butstatsHomeSOffT;undoMenu.findItem(R.id.undo).setEnabled(true);
                datasource.updateStats(stat);
            }
        });

        buthomeLC = findViewById(R.id.butstatsHomeLC);
        buthomeLC.setBackgroundTintList(ColorStateList.valueOf(homeColor));
        buthomeLC.setTextColor(homeTextColor);
        buthomeLC.setText(""+(stat.getHome_LC()));
        buthomeLC.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                buthomeLC.setText(""+(stat.getHome_LC()+1));stat.setHome_LC(stat.getHome_LC()+1);
                undo=R.id.butstatsHomeLC;undoMenu.findItem(R.id.undo).setEnabled(true);
                datasource.updateStats(stat);
            }
        });

        buthomeTCK = findViewById(R.id.butstatsHomeTackle);
        buthomeTCK.setBackgroundTintList(ColorStateList.valueOf(homeColor));
        buthomeTCK.setTextColor(homeTextColor);
        buthomeTCK.setText(""+(stat.getHome_TCK()));
        buthomeTCK.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                buthomeTCK.setText(""+(stat.getHome_TCK()+1));stat.setHome_TCK(stat.getHome_TCK()+1);
                undo=R.id.butstatsHomeTackle;undoMenu.findItem(R.id.undo).setEnabled(true);
                datasource.updateStats(stat);
            }
        });

        buthomeTI = findViewById(R.id.butstatsHomeTI);
        buthomeTI.setBackgroundTintList(ColorStateList.valueOf(homeColor));
        buthomeTI.setTextColor(homeTextColor);
        buthomeTI.setText(""+(stat.getHome_TI()));
        buthomeTI.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                buthomeTI.setText(""+(stat.getHome_TI()+1));stat.setHome_TI(stat.getHome_TI()+1);
                undo=R.id.butstatsHomeTI;undoMenu.findItem(R.id.undo).setEnabled(true);
                datasource.updateStats(stat);
            }
        });

        buthomeOff = findViewById(R.id.butstatsHomeOff);
        buthomeOff.setBackgroundTintList(ColorStateList.valueOf(homeColor));
        buthomeOff.setTextColor(homeTextColor);
        buthomeOff.setText(""+(stat.getHome_OFF()));
        buthomeOff.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                buthomeOff.setText(""+(stat.getHome_OFF()+1));stat.setHome_OFF(stat.getHome_OFF()+1);
                undo=R.id.butstatsHomeOff;undoMenu.findItem(R.id.undo).setEnabled(true);
                datasource.updateStats(stat);
            }
        });

        buthomePOP = findViewById(R.id.butstatsHomePOP);
        buthomePOP.setBackgroundTintList(ColorStateList.valueOf(homeColor));
        buthomePOP.setTextColor(homeTextColor);
        buthomePOP.setText(""+(stat.getHome_POP()));
        buthomePOP.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                buthomePOP.setText(""+(stat.getHome_POP()+1));stat.setHome_POP(stat.getHome_POP()+1);
                undo=R.id.butstatsHomePOP;undoMenu.findItem(R.id.undo).setEnabled(true);
                datasource.updateStats(stat);
            }
        });

        buthomePass = findViewById(R.id.butHomePasses);
        buthomePass.setBackgroundTintList(ColorStateList.valueOf(homeColor));
        buthomePass.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                homePasses.setText(""+((stat.getHome_TIME())+1));stat.setHome_TIME((stat.getHome_TIME())+1);
                //undo=R.id.butHomePasses;undoMenu.findItem(R.id.undo).setEnabled(true);
                datasource.updateStats(stat);
            }
        });


        butawayDFK = findViewById(R.id.butstatsAwayDFK);
        butawayDFK.setBackgroundTintList(ColorStateList.valueOf(awayColor));
        butawayDFK.setTextColor(awayTextColor);
        butawayDFK.setText(""+(stat.getAway_DFK()));
        butawayDFK.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                butawayDFK.setText(""+(stat.getAway_DFK()+1));stat.setAway_DFK(stat.getAway_DFK()+1);
                undo=R.id.butstatsAwayDFK;undoMenu.findItem(R.id.undo).setEnabled(true);
                datasource.updateStats(stat);
            }
        });

        butawayCor = findViewById(R.id.butstatsAwayCor);
        butawayCor.setBackgroundTintList(ColorStateList.valueOf(awayColor));
        butawayCor.setTextColor(awayTextColor);
        butawayCor.setText(""+(stat.getAway_COR()));
        butawayCor.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                butawayCor.setText(""+(stat.getAway_COR()+1));stat.setAway_COR(stat.getAway_COR()+1);
                undo=R.id.butstatsAwayCor;undoMenu.findItem(R.id.undo).setEnabled(true);
                datasource.updateStats(stat);
            }
        });

        butawayGK = findViewById(R.id.butstatsAwayGK);
        butawayGK.setBackgroundTintList(ColorStateList.valueOf(awayColor));
        butawayGK.setTextColor(awayTextColor);
        butawayGK.setText(""+(stat.getAway_GK()));
        butawayGK.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                butawayGK.setText(""+(stat.getAway_GK()+1));stat.setAway_GK(stat.getAway_GK()+1);
                undo=R.id.butstatsAwayGK;undoMenu.findItem(R.id.undo).setEnabled(true);
                datasource.updateStats(stat);
            }
        });

        butawaySOnT = findViewById(R.id.butstatsAwaySOnT);
        butawaySOnT.setBackgroundTintList(ColorStateList.valueOf(awayColor));
        butawaySOnT.setTextColor(awayTextColor);
        butawaySOnT.setText(""+(stat.getAway_SOnT()));
        butawaySOnT.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                butawaySOnT.setText(""+(stat.getAway_SOnT()+1));stat.setAway_SOnT(stat.getAway_SOnT()+1);
                undo=R.id.butstatsAwaySOnT;undoMenu.findItem(R.id.undo).setEnabled(true);
                datasource.updateStats(stat);
            }
        });

        butawaySOffT = findViewById(R.id.butstatsAwaySOffT);
        butawaySOffT.setBackgroundTintList(ColorStateList.valueOf(awayColor));
        butawaySOffT.setTextColor(awayTextColor);
        butawaySOffT.setText(""+(stat.getAway_SOffT()));
        butawaySOffT.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                butawaySOffT.setText(""+(stat.getAway_SOffT()+1));stat.setAway_SOffT(stat.getAway_SOffT()+1);
                undo=R.id.butstatsAwaySOffT;undoMenu.findItem(R.id.undo).setEnabled(true);
                datasource.updateStats(stat);
            }
        });

        butawayLC = findViewById(R.id.butstatsAwayLC);
        butawayLC.setBackgroundTintList(ColorStateList.valueOf(awayColor));
        butawayLC.setTextColor(awayTextColor);
        butawayLC.setText(""+(stat.getAway_LC()));
        butawayLC.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                butawayLC.setText(""+(stat.getAway_LC()+1));stat.setAway_LC(stat.getAway_LC()+1);
                undo=R.id.butstatsAwayLC;undoMenu.findItem(R.id.undo).setEnabled(true);
                datasource.updateStats(stat);
            }
        });

        butawayTCK = findViewById(R.id.butstatsAwayTackle);
        butawayTCK.setBackgroundTintList(ColorStateList.valueOf(awayColor));
        butawayTCK.setTextColor(awayTextColor);
        butawayTCK.setText(""+(stat.getAway_TCK()));
        butawayTCK.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                butawayTCK.setText(""+(stat.getAway_TCK()+1));stat.setAway_TCK(stat.getAway_TCK()+1);
                undo=R.id.butstatsAwayTackle;undoMenu.findItem(R.id.undo).setEnabled(true);
                datasource.updateStats(stat);
            }
        });

        butawayTI = findViewById(R.id.butstatsAwayTI);
        butawayTI.setBackgroundTintList(ColorStateList.valueOf(awayColor));
        butawayTI.setTextColor(awayTextColor);
        butawayTI.setText(""+(stat.getAway_TI()));
        butawayTI.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                butawayTI.setText(""+(stat.getAway_TI()+1));stat.setAway_TI(stat.getAway_TI()+1);
                undo=R.id.butstatsAwayTI;undoMenu.findItem(R.id.undo).setEnabled(true);
                datasource.updateStats(stat);
            }
        });

        butawayOff = findViewById(R.id.butstatsAwayOff);
        butawayOff.setBackgroundTintList(ColorStateList.valueOf(awayColor));
        butawayOff.setTextColor(awayTextColor);
        butawayOff.setText(""+(stat.getAway_OFF()));
        butawayOff.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                butawayOff.setText(""+(stat.getAway_OFF()+1));stat.setAway_OFF(stat.getAway_OFF()+1);
                undo=R.id.butstatsAwayOff;undoMenu.findItem(R.id.undo).setEnabled(true);
                datasource.updateStats(stat);
            }
        });

        butawayPOP = findViewById(R.id.butstatsAwayPOP);
        butawayPOP.setBackgroundTintList(ColorStateList.valueOf(awayColor));
        butawayPOP.setTextColor(awayTextColor);
        butawayPOP.setText(""+(stat.getAway_POP()));
        butawayPOP.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                butawayPOP.setText(""+(stat.getAway_POP()+1));stat.setAway_POP(stat.getAway_POP()+1);
                undo=R.id.butstatsAwayPOP;undoMenu.findItem(R.id.undo).setEnabled(true);
                datasource.updateStats(stat);
            }
        });

        butawayPass= findViewById(R.id.butAwayPasses);
        butawayPass.setBackgroundTintList(ColorStateList.valueOf(awayColor));
        //butawayPass.setTextColor(awayTextColor);
        //butawayPass.setText(""+(stat.getAway_POP()));
        butawayPass.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                awayPasses.setText(""+((stat.getAway_TIME())+1));stat.setAway_TIME((stat.getAway_TIME())+1);
                //undo=R.id.butAwayPasses;undoMenu.findItem(R.id.undo).setEnabled(true);
                datasource.updateStats(stat);
            }
        });



        /*possisionChange = findViewById(R.id.butstatsChange);
        possisionChange.setEnabled(false);
        possisionChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                awayPossession = !awayPossession;
                if(awayPossession)
                {
                    awayStartTime = SystemClock.uptimeMillis();
                    awayTimeHandler.postDelayed(updateAwayTimerThread,0);
                    //possisionChange.setBackgroundTintList(ColorStateList.valueOf(awayColor));
                    possisionChange.setBackgroundTintList(ColorStateList.valueOf(tempawayColor));
                    homeSwapBuff += homeTimeInMillisecond;
                    homeTimeHandler.removeCallbacks(updateHomeTimerThread);
                }
                else
                {
                    homeStartTime = SystemClock.uptimeMillis();
                    homeTimeHandler.postDelayed(updateHomeTimerThread,0);
                    //possisionChange.setBackgroundTintList(ColorStateList.valueOf(homeColor));
                    possisionChange.setBackgroundTintList(ColorStateList.valueOf(temphomeColor));
                    awaySwapBuff += awayTimeInMillisecond;
                    awayTimeHandler.removeCallbacks(updateAwayTimerThread);

                }
            }
        });*/




        startPause = findViewById(R.id.butstatsPause);
        startPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(start) {
                    startPause.setImageResource(R.drawable.pause);
                    //possisionChange.setEnabled(true);
                    highlightButton.setEnabled(true);
                    goalButton.setEnabled(true);
                    cardButton.setEnabled(true);
                    //subsButton.setEnabled(true);

                    buthomeDFK.setEnabled(true);
                    buthomeCor.setEnabled(true);
                    buthomeGK.setEnabled(true);
                    buthomeSOnT.setEnabled(true);
                    buthomeSOffT.setEnabled(true);
                    buthomeLC.setEnabled(true);
                    buthomeTCK.setEnabled(true);
                    buthomeTI.setEnabled(true);
                    buthomeOff.setEnabled(true);
                    buthomePOP.setEnabled(true);
                    butawayDFK.setEnabled(true);
                    butawayCor.setEnabled(true);
                    butawayGK.setEnabled(true);
                    butawaySOnT.setEnabled(true);
                    butawaySOffT.setEnabled(true);
                    butawayLC.setEnabled(true);
                    butawayTCK.setEnabled(true);
                    butawayTI.setEnabled(true);
                    butawayOff.setEnabled(true);
                    butawayPOP.setEnabled(true);;

                    matchStartTime = SystemClock.uptimeMillis();
                    matchTimeHandler.postDelayed(updateMatchTimerThread,0);
                    //homeTimeHandler.postDelayed(updateHomeTimerThread,0);
                    //awayTimeHandler.postDelayed(updateAwayTimerThread,0);

                    start =false;
                    awayPossession = !awayPossession;
                    //possisionChange.callOnClick();
                }
                else{
                    startPause.setImageResource(R.drawable.start);
                    //possisionChange.setEnabled(false);
                    highlightButton.setEnabled(false);
                    goalButton.setEnabled(false);
                    cardButton.setEnabled(false);
                    //subsButton.setEnabled(false);

                    buthomeDFK.setEnabled(false);
                    buthomeCor.setEnabled(false);
                    buthomeGK.setEnabled(false);
                    buthomeSOnT.setEnabled(false);
                    buthomeSOffT.setEnabled(false);
                    buthomeLC.setEnabled(false);
                    buthomeTCK.setEnabled(false);
                    buthomeTI.setEnabled(false);
                    buthomeOff.setEnabled(false);
                    buthomePOP.setEnabled(false);
                    butawayDFK.setEnabled(false);
                    butawayCor.setEnabled(false);
                    butawayGK.setEnabled(false);
                    butawaySOnT.setEnabled(false);
                    butawaySOffT.setEnabled(false);
                    butawayLC.setEnabled(false);
                    butawayTCK.setEnabled(false);
                    butawayTI.setEnabled(false);
                    butawayOff.setEnabled(false);
                    butawayPOP.setEnabled(false);;

                    matchSwapBuff += matchTimeInMillisecond;
                    matchTimeHandler.removeCallbacks(updateMatchTimerThread);
                    start = true;
                    /*awaySwapBuff += awayTimeInMillisecond;
                    awayTimeHandler.removeCallbacks(updateAwayTimerThread);
                    homeSwapBuff += homeTimeInMillisecond;
                    homeTimeHandler.removeCallbacks(updateHomeTimerThread);*/
                }
            }
        });

        /*resetTimer = findViewById(R.id.butstatsResetTime);
        resetTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogInterface.OnClickListener dialog = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which)
                        {
                            case DialogInterface.BUTTON_POSITIVE :
                                awaySwapBuff = 0L;
                                homeSwapBuff = 0L;
                                matchSwapBuff = 0L;
                                homeStartTime = SystemClock.uptimeMillis();
                                awayStartTime = SystemClock.uptimeMillis();
                                matchStartTime = SystemClock.uptimeMillis();
                                htHomeTime =homeTime;
                                htAwayTime =awayTime;
                                homeTime =0;
                                awayTime =0;
                                timerMatch.setText("" + String.format("%02d", 0) + " : " + String.format("%02d", 0));
                                if (matchTime < 2700)
                                    halftime = 2400;
                                else
                                    halftime = 2700;
                                halftime=0;

                                //ArrayAdapter<CharSequence> adapterResetReason = ArrayAdapter.createFromResource(tzlc_stats_add.this,R.array.resetTime,R.layout.dropdownitem);
                                String resetReason [] = getResources().getStringArray(R.array.resetTime);


                                Highlight highlight = new Highlight(matchID,-1 , -300,
                                        matchTime,
                                        resetReason[resetCount],
                                        "--NA--");
                                datasource.addHighlight(highlight);
                                resetCount++;

                                break;
                            case DialogInterface.BUTTON_NEGATIVE : break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(tzlc_stats_add.this);
                builder.setTitle("Alert !!");
                builder.setMessage("Are you sure you want to reset Timer ??").setPositiveButton("Yes",dialog).setNegativeButton("No",dialog).show();


            }
        });*/

    }




    @Override
    protected void onResume() {
        datasource.open();
        if(goalID > 0) {
            Goal goal = datasource.getGoal(goalID);
            if (goal.getOwnGoal() == 1) {
                if (goal.getAgainstClubID() == m.getHomeClubID())
                    homeScore.setText("" + (Integer.parseInt(homeScore.getText().toString()) + 1));
                if (goal.getAgainstClubID() == m.getAwayClubID())
                    awayScore.setText("" + (Integer.parseInt(awayScore.getText().toString()) + 1));
            } else {
                if (goal.getAgainstClubID() == m.getHomeClubID())
                    homeScore.setText("" + (Integer.parseInt(homeScore.getText().toString()) + 1));
                if (goal.getAgainstClubID() == m.getAwayClubID())
                    awayScore.setText("" + (Integer.parseInt(awayScore.getText().toString()) + 1));
            }
        }
        stat.setHome_Score(Integer.parseInt(homeScore.getText().toString()));
        stat.setAway_Score(Integer.parseInt(awayScore.getText().toString()));
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
        AlertDialog.Builder builder = new AlertDialog.Builder(tzlc_stats_add.this);
        builder.setMessage("Please save Stats to exit this screen !!").setPositiveButton("Ok",dialog).show();
    }

    /*private Runnable updateHomeTimerThread = new Runnable() {
        @Override
        public void run() {
            homeTimeInMillisecond = SystemClock.uptimeMillis() - homeStartTime;
            homeUpdatedTime = homeSwapBuff + homeTimeInMillisecond;
            int sec = (int) (homeUpdatedTime / 1000);
            int min = sec / 60;
            homePasses.setText("" + String.format("%02d", min) + " : " + String.format("%02d", (sec % 60)));
            stat.setHome_TIME(sec);
            homeTime = (min*60)+sec;
            homeTimeHandler.postDelayed(updateHomeTimerThread,0);
        }
    };

    private Runnable updateAwayTimerThread = new Runnable() {
        @Override
        public void run() {
            awayTimeInMillisecond = SystemClock.uptimeMillis() - awayStartTime;
            awayUpdatedTime = awaySwapBuff + awayTimeInMillisecond;
            int sec = (int) (awayUpdatedTime / 1000);
            int min = sec / 60;
            awayPasses.setText("" + String.format("%02d", min) + " : " + String.format("%02d", (sec % 60)));
            stat.setAway_TIME(sec);
            awayTime = (min*60)+sec;
            awayTimeHandler.postDelayed(updateAwayTimerThread,0);
        }
    };
*/
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
            /*if(matchTime > 2280 && matchTime < 2300)
            {
                if(timerMatch.getCurrentTextColor() == -16777216)
                    timerMatch.setTextColor(-1697461);
                else
                    timerMatch.setTextColor(-16777216);
            }
            else
                timerMatch.setTextColor(-16777216);*/
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Bundle b = data.getExtras();
        matchID = b.getLong("matchID",-1);
        goalAgainstClubId = b.getLong("clubID",-1);
        goalID = b.getLong("goalID",-1);
        temphomeColor = b.getInt("homeColor",temphomeColor);
        tempawayColor = b.getInt("awayColor",tempawayColor);
        completedPassesHome = b.getInt("homePasses",0);
        completedPassesAway = b.getInt("awayPasses",0);



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tzlc_stats_menu,menu);
        undoMenu = menu;
        return true;
        //return super.onCreateOptionsMenu(menu);
    }

    public void undoHandler(int statsID)
    {
        switch (statsID)
        {
            case R.id.butstatsHomeDFK :
            buthomeDFK.setText(""+(stat.getHome_DFK()-1));stat.setHome_DFK(stat.getHome_DFK()-1);break;

            case R.id.butstatsHomeCor :
            buthomeCor.setText(""+(stat.getHome_COR()-1));stat.setHome_COR(stat.getHome_COR()-1);break;

            case R.id.butstatsHomeGK :
            buthomeGK.setText(""+(stat.getHome_GK()-1));stat.setHome_GK(stat.getHome_GK()-1);break;

            case R.id.butstatsHomeSOnT :
            buthomeSOnT.setText(""+(stat.getHome_SOnT()-1));stat.setHome_SOnT(stat.getHome_SOnT()-1);break;

            case R.id.butstatsHomeSOffT :
            buthomeSOffT.setText(""+(stat.getHome_SOffT()-1));stat.setHome_SOffT(stat.getHome_SOffT()-1);break;

            case R.id.butstatsHomeLC :
            buthomeLC.setText(""+(stat.getHome_LC()-1));stat.setHome_LC(stat.getHome_LC()-1);break;

            case R.id.butstatsHomeTackle :
            buthomeTCK.setText(""+(stat.getHome_TCK()-1));stat.setHome_TCK(stat.getHome_TCK()-1);break;

            case R.id.butstatsHomeTI :
            buthomeTI.setText(""+(stat.getHome_TI()-1));stat.setHome_TI(stat.getHome_TI()-1);break;

            case R.id.butstatsHomeOff :
            buthomeOff.setText(""+(stat.getHome_OFF()-1));stat.setHome_OFF(stat.getHome_OFF()-1);break;

            case R.id.butstatsHomePOP :
            buthomePOP.setText(""+(stat.getHome_POP()-1));stat.setHome_POP(stat.getHome_POP()-1);break;

            case R.id.butstatsAwayDFK :
            butawayDFK.setText(""+(stat.getAway_DFK()-1));stat.setAway_DFK(stat.getAway_DFK()-1);break;

            case R.id.butstatsAwayCor :
            butawayCor.setText(""+(stat.getAway_COR()-1));stat.setAway_COR(stat.getAway_COR()-1);break;

            case R.id.butstatsAwayGK :
            butawayGK.setText(""+(stat.getAway_GK()-1));stat.setAway_GK(stat.getAway_GK()-1);break;

            case R.id.butstatsAwaySOnT :
            butawaySOnT.setText(""+(stat.getAway_SOnT()-1));stat.setAway_SOnT(stat.getAway_SOnT()-1);break;

            case R.id.butstatsAwaySOffT :
            butawaySOffT.setText(""+(stat.getAway_SOffT()-1));stat.setAway_SOffT(stat.getAway_SOffT()-1);break;

            case R.id.butstatsAwayLC :
            butawayLC.setText(""+(stat.getAway_LC()-1));stat.setAway_LC(stat.getAway_LC()-1);break;

            case R.id.butstatsAwayTackle :
            butawayTCK.setText(""+(stat.getAway_TCK()-1));stat.setAway_TCK(stat.getAway_TCK()-1);break;

            case R.id.butstatsAwayTI :
            butawayTI.setText(""+(stat.getAway_TI()-1));stat.setAway_TI(stat.getAway_TI()-1);break;

            case R.id.butstatsAwayOff :
            butawayOff.setText(""+(stat.getAway_OFF()-1));stat.setAway_OFF(stat.getAway_OFF()-1);break;

            case R.id.butstatsAwayPOP :
            butawayPOP.setText(""+(stat.getAway_POP()-1));stat.setAway_POP(stat.getAway_POP()-1);break;

        }
        datasource.updateStats(stat);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        switch (id) {
            case R.id.saveStats:

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
                                //stat.setHome_TIME(htHomeTime + homeTime);
                                //tat.setAway_TIME(htAwayTime + awayTime);
                                stat.setHome_Score(Integer.parseInt(homeScore.getText().toString()));
                                stat.setAway_Score(Integer.parseInt(awayScore.getText().toString()));

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
                AlertDialog.Builder builder = new AlertDialog.Builder(tzlc_stats_add.this);
                builder.setMessage("Do you want to save Match Stats ???").setNegativeButton("No",dialog).setPositiveButton("Yes",dialog).show();
                break;

            case R.id.undo : undoHandler(undo);item.setEnabled(false); break;

            case R.id.color : Intent jerseyColor = new Intent(tzlc_stats_add.this, tzlc_change_jersey_color.class);
                Bundle extras  = new Bundle();
                extras.putLong("matchID", matchID);
                extras.putInt("homeColor", homeColor);
                extras.putString("homeClubName",datasource.getClub( m.getHomeClubID()).getClubShortName());
                extras.putInt("awayColor", awayColor);
                extras.putString("awayClubName",datasource.getClub( m.getAwayClubID()).getClubShortName());
                jerseyColor.putExtras(extras);
                startActivityForResult(jerseyColor,100);
                break;

            case R.id.resetTimers :

                DialogInterface.OnClickListener resetdialog = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which)
                        {
                            case DialogInterface.BUTTON_POSITIVE :
                                //awaySwapBuff = 0L;
                                //homeSwapBuff = 0L;
                                matchSwapBuff = 0L;
                                //homeStartTime = SystemClock.uptimeMillis();
                                //awayStartTime = SystemClock.uptimeMillis();
                                matchStartTime = SystemClock.uptimeMillis();
                                //htHomeTime =homeTime;
                                //htAwayTime =awayTime;
                                //homeTime =0;
                                //awayTime =0;
                                timerMatch.setText("" + String.format("%02d", 0) + " : " + String.format("%02d", 0));
                                if (matchTime < 2700)
                                    halftime = 2400;
                                else
                                    halftime = 2700;
                                halftime=0;

                                //ArrayAdapter<CharSequence> adapterResetReason = ArrayAdapter.createFromResource(tzlc_stats_add.this,R.array.resetTime,R.layout.dropdownitem);
                                String resetReason [] = getResources().getStringArray(R.array.resetTime);


                                Highlight highlight = new Highlight(matchID,-1 , -300,
                                        matchTime,
                                        resetReason[resetCount],
                                        "--NA--");
                                datasource.addHighlight(highlight);
                                resetCount++;

                                break;
                            case DialogInterface.BUTTON_NEGATIVE : break;
                        }
                    }
                };
                AlertDialog.Builder resetbuilder = new AlertDialog.Builder(tzlc_stats_add.this);
                resetbuilder.setTitle("Alert !!");
                resetbuilder.setMessage("Are you sure you want to reset Timer ??").setPositiveButton("Yes",resetdialog).setNegativeButton("No",resetdialog).show();

                break;
            case R.id.completedPasses :
                Intent completedPasses = new Intent(tzlc_stats_add.this, tzlc_stats_completed_passes.class);
                Bundle extras_passes  = new Bundle();
                extras_passes.putLong("matchID", matchID);
                extras_passes.putInt("matchTime",matchTime+halftime);
                extras_passes.putInt("homeColor",temphomeColor);
                extras_passes.putInt("awayColor",tempawayColor);
                extras_passes.putString("homeClub",datasource.getClub( m.getHomeClubID()).getClubShortName()) ;
                extras_passes.putString("awayClub",datasource.getClub( m.getAwayClubID()).getClubShortName());
                completedPasses.putExtras(extras_passes);
                startActivityForResult(completedPasses,100);
                break;
    }

        return super.onOptionsItemSelected(item);
        //return true;

    }



}
