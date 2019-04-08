package com.kfandra.tzlc.tzlc;

import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StatsRecording  extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_recording);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
//extends AppCompatActivity implements View.OnClickListener
    /*public Stats ms;
    private long homeStartTime = 0L;
    private long awayStartTime = 0L;
    private Handler homeTimeHandler = new Handler();
    private Handler awayTimeHandler = new Handler();
    long homeTimeInMillisecond = 0L;
    long awayTimeInMillisecond = 0L;
    long homeSwapBuff = 0L;
    long awaySwapBuff = 0L;
    long homeUpdatedTime = 0L;
    long awayUpdatedTime = 0L;
    private TextView timerHome;
    private TextView timerAway;

    private boolean flag=true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_recording);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        //ms = new Stats();
        /*int t[] = ms.getCOR();
        Log.d(StatsRecording.class.getSimpleName(), "Home Team : " + t[0] + ", Away Team : " + t[1]);*/

        /*Button home_TI = findViewById(R.id.home_TI);
        home_TI.setOnClickListener(this);
        Button home_DFK =  findViewById(R.id.home_DFK);
        home_DFK.setOnClickListener(this);
        Button home_IFK =  findViewById(R.id.home_IFK);
        home_IFK.setOnClickListener(this);
        Button home_GK =  findViewById(R.id.home_GK);
        home_GK.setOnClickListener(this);
        Button home_OFF =  findViewById(R.id.home_OFF);
        home_OFF.setOnClickListener(this);
        Button home_LC =  findViewById(R.id.home_LC);
        home_LC.setOnClickListener(this);
        Button home_COR =  findViewById(R.id.home_COR);
        home_COR.setOnClickListener(this);
        Button home_TCK =  findViewById(R.id.home_TCK);
        home_TCK.setOnClickListener(this);


        Button away_TI =  findViewById(R.id.away_TI);
        away_TI.setOnClickListener(this);
        Button away_DFK =  findViewById(R.id.away_DFK);
        away_DFK.setOnClickListener(this);
        Button away_IFK =  findViewById(R.id.away_IFK);
        away_IFK.setOnClickListener(this);
        Button away_GK =  findViewById(R.id.away_GK);
        away_GK.setOnClickListener(this);
        Button away_OFF =  findViewById(R.id.away_OFF);
        away_OFF.setOnClickListener(this);
        Button away_LC =  findViewById(R.id.away_LC);
        away_LC.setOnClickListener(this);
        Button away_COR =  findViewById(R.id.away_COR);
        away_COR.setOnClickListener(this);
        Button away_TCK =  findViewById(R.id.away_TCK);
        away_TCK.setOnClickListener(this);


        timerHome = findViewById(R.id.home_TIME);
        timerAway = findViewById(R.id.away_TIME);*/
        //homeStartTime = SystemClock.uptimeMillis();
        //awayStartTime = SystemClock.uptimeMillis();

       /* Button timeButton = findViewById(R.id.possessionChange);
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = !flag;
                if(flag)
                {
                    awayStartTime = SystemClock.uptimeMillis();
                    awayTimeHandler.postDelayed(updateAwayTimerThread,0);

                    homeSwapBuff += homeTimeInMillisecond;
                    homeTimeHandler.removeCallbacks(updateHomeTimerThread);
                }
                else
                {
                    homeStartTime = SystemClock.uptimeMillis();
                    homeTimeHandler.postDelayed(updateHomeTimerThread,0);

                    awaySwapBuff += awayTimeInMillisecond;
                    awayTimeHandler.removeCallbacks(updateAwayTimerThread);

                }
                //customHandler.postDelayed(updateTimerThread,0);
            }
        });*/


       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    //}

    /*private Runnable updateHomeTimerThread = new Runnable() {
        @Override
        public void run() {
            homeTimeInMillisecond = SystemClock.uptimeMillis() - homeStartTime;
            homeUpdatedTime = homeSwapBuff + homeTimeInMillisecond;
            int sec = (int) (homeUpdatedTime / 1000);
            int min = sec / 60;
            timerHome.setText("" + String.format("%02d", min) + " : " + String.format("%02d", (sec % 60)));
            ms.setHome_TIME(sec);
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
            timerAway.setText("" + String.format("%02d", min) + " : " + String.format("%02d", (sec % 60)));
            ms.setAway_TIME(sec);
            awayTimeHandler.postDelayed(updateAwayTimerThread,0);
        }
    };*/




    /*private Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
            if(flag)
            {
                timeInMill = SystemClock.uptimeMillis() - homeStartTime;
                awaySwapBuff= awaySwapBuff + timeInMill;
                updatedTime = awaySwapBuff + timeInMill;
            }
            else
            {
                timeInMill = SystemClock.uptimeMillis() - awayStartTime;
                homeSwapBuff= homeSwapBuff + timeInMill;
                updatedTime = homeSwapBuff + timeInMill;
            }

            int sec = (int) (updatedTime / 1000);
            int min = sec / 60;

            if(flag)
                timerHome.setText("" + min + ":" + sec);
            else
                timerAway.setText("" + min + ":" + sec);
            customHandler.postDelayed(this,0);
        }
    };*/



/*
    @Override
    public void onClick(View v) {
        Button t = findViewById(v.getId());

        switch (v.getId()){
            case R.id.home_TI :     ms.setHome_TI(ms.getHome_TI()+1);
                                    //t.setText(getResources().getString(R.string.throw_in) + ms.getHome_TI());
                                    t.setText(""+ms.getHome_TI());
                                    break;
            case R.id.home_DFK :    ms.setHome_DFK(ms.getHome_DFK()+1);
                                    t.setText("" + ms.getHome_DFK());
                                    break;
            case R.id.home_IFK :    ms.setHome_IFK(ms.getHome_IFK()+1);
                                    t.setText("" + ms.getHome_IFK());
                                    break;
            case R.id.home_GK :    ms.setHome_GK(ms.getHome_GK()+1);
                                    t.setText("" + ms.getHome_GK());
                                    break;
            case R.id.home_OFF :    ms.setHome_OFF(ms.getHome_OFF()+1);
                                    t.setText("" + ms.getHome_OFF());
                                    break;
            case R.id.home_LC :    ms.setHome_LC(ms.getHome_LC()+1);
                                    t.setText("" + ms.getHome_LC());
                                    break;
            case R.id.home_COR :    ms.setHome_COR(ms.getHome_COR()+1);
                                    t.setText("" + ms.getHome_COR());
                                    break;
            case R.id.home_TCK :    ms.setHome_TCK(ms.getHome_TCK()+1);
                                    t.setText("" + ms.getHome_TCK());
                                    break;

            case R.id.away_TI :     ms.setAway_TI(ms.getAway_TI()+1);
                                    t.setText("" + ms.getAway_TI());
                                    break;
            case R.id.away_DFK :    ms.setAway_DFK(ms.getAway_DFK()+1);
                                    t.setText("" + ms.getAway_DFK());
                                    break;
            case R.id.away_IFK :    ms.setAway_IFK(ms.getAway_IFK()+1);
                                    t.setText("" + ms.getAway_IFK());
                                    break;
            case R.id.away_GK :    ms.setAway_GK(ms.getAway_GK()+1);
                                    t.setText("" + ms.getAway_GK());
                                    break;
            case R.id.away_OFF :    ms.setAway_OFF(ms.getAway_OFF()+1);
                                    t.setText("" + ms.getAway_OFF());
                                    break;
            case R.id.away_LC :    ms.setAway_LC(ms.getAway_LC()+1);
                                    t.setText("" + ms.getAway_LC());
                                    break;
            case R.id.away_COR :    ms.setAway_COR(ms.getAway_COR()+1);
                                    t.setText("" + ms.getAway_COR());
                                    break;
            case R.id.away_TCK :    ms.setAway_TCK(ms.getAway_TCK()+1);
                                    t.setText("" + ms.getAway_TCK());
                                    break;
        }

    }*/

