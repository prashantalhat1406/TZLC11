package com.kfandra.tzlc.tzlc;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class tzlc_stats_add_tzlc12n extends AppCompatActivity {

    private tzlcDataSource datasource;
    private long matchID,goalID,cardID;
    private Stats stat;
    private Button buthomeDFK, buthomeCor, buthomeLC, buthomeTI, buthomePOPScored, buthomePOPMissed;
    private Button butawayDFK, butawayCor, butawayLC, butawayTI, butawayPOPScored, butawayPOPMissed;
    private ImageButton startPause;
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


    public boolean isColorDark(int color){
        double darkness = 1-(0.299* Color.red(color) + 0.587*Color.green(color) + 0.114*Color.blue(color))/255;
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
        buthomeLC.setBackgroundTintList(ColorStateList.valueOf(tempHColor));buthomeLC.setTextColor(temphomeTextColor);
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

        homeColor =  datasource.getClub(m.getHomeClubID()).getClubColor();
        homeTextColor = (isColorDark(homeColor)?Color.WHITE:Color.BLACK);
        awayColor =  datasource.getClub(m.getAwayClubID()).getClubColor();
        awayTextColor = (isColorDark(awayColor)?Color.WHITE:Color.BLACK);

        temphomeColor = homeColor;
        tempawayColor = awayColor;

        timerMatch = findViewById(R.id.txtstatsMatchTimerTZLC12);
        matchStartTime = SystemClock.uptimeMillis();

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


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(tzlc_stats_add_tzlc12n.this,R.array.matchType,android.R.layout.simple_spinner_item);
        TextView matchType = findViewById(R.id.txtstatsMatchTypeTZLC12);
        matchType.setText(""+adapter.getItem( m.getType()));

        //Buttons Code starts

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
                undo = R.id.butstatsHomeDFK; undoMenu.findItem(R.id.undo).setEnabled(true);
                datasource.updateStats(stat);
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
                undo=R.id.butstatsHomeCor;undoMenu.findItem(R.id.undo).setEnabled(true);
                datasource.updateStats(stat);
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
                undo=R.id.butstatsHomeLC;undoMenu.findItem(R.id.undo).setEnabled(true);
                datasource.updateStats(stat);
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
                undo=R.id.butstatsHomeTI;undoMenu.findItem(R.id.undo).setEnabled(true);
                datasource.updateStats(stat);
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
                undo=R.id.butstatsHomePOPScore;undoMenu.findItem(R.id.undo).setEnabled(true);
                datasource.updateStats(stat);
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
                undo=R.id.butstatsHomePOPMiss;undoMenu.findItem(R.id.undo).setEnabled(true);
                datasource.updateStats(stat);
            }
        });







        butawayDFK = findViewById(R.id.butstatsAwayDFKTZLC12);
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

        butawayCor = findViewById(R.id.butstatsAwayCornerTZLC12);
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

        butawayLC = findViewById(R.id.butstatsAwayLCTZLC12);
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



        butawayTI = findViewById(R.id.butstatsAwayThrowInTZLC12);
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

        butawayPOPScored = findViewById(R.id.butstatsAwayPOPScore);
        butawayPOPScored.setBackgroundTintList(ColorStateList.valueOf(awayColor));
        butawayPOPScored.setTextColor(awayTextColor);
        butawayPOPScored.setText(""+(stat.getAway_POP()));
        butawayPOPScored.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                butawayPOPScored.setText(""+(stat.getAway_POP()+1));stat.setAway_POP(stat.getAway_POP()+1);
                undo=R.id.butstatsAwayPOPScore;undoMenu.findItem(R.id.undo).setEnabled(true);
                datasource.updateStats(stat);
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
                undo=R.id.butstatsAwayPOPMiss;undoMenu.findItem(R.id.undo).setEnabled(true);
                datasource.updateStats(stat);
            }
        });


        startPause = findViewById(R.id.butstatsPauseTZLC12);
        startPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(start) {
                    startPause.setImageResource(R.drawable.pause);
                    buthomeDFK.setEnabled(true);
                    buthomeCor.setEnabled(true);
                    buthomeLC.setEnabled(true);
                    buthomeTI.setEnabled(true);
                    buthomePOPScored.setEnabled(true);
                    buthomePOPMissed.setEnabled(true);

                    butawayDFK.setEnabled(true);
                    butawayCor.setEnabled(true);
                    butawayLC.setEnabled(true);
                    butawayTI.setEnabled(true);
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
                    buthomePOPScored.setEnabled(false);
                    buthomePOPMissed.setEnabled(false);

                    butawayDFK.setEnabled(false);
                    butawayCor.setEnabled(false);
                    butawayLC.setEnabled(false);
                    butawayTI.setEnabled(false);
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
        inflater.inflate(R.menu.tzlc_stats_menu,menu);
        undoMenu = menu;
        return true;
        //return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Bundle b = data.getExtras();
        matchID = b.getLong("matchID",-1);
        goalID = b.getLong("goalID",-1);
        temphomeColor = b.getInt("homeColor",temphomeColor);
        tempawayColor = b.getInt("awayColor",tempawayColor);
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
                                /*stat.setMatchID(m.getId());
                                stat.setMatchTime(matchTime);
                                stat.setHome_TIME((stat.getHome_TIME()*1000)+completedPassesHome);
                                stat.setAway_TIME((stat.getAway_TIME()*1000)+completedPassesAway);
                                stat.setHome_Score(Integer.parseInt(homeScore.getText().toString()));
                                stat.setAway_Score(Integer.parseInt(awayScore.getText().toString()));

                                datasource.addStats(stat);

                                Intent returnI = new Intent();
                                Bundle extras = new Bundle();
                                extras.putLong("matchID", matchID);
                                returnI.putExtras(extras);
                                setResult(100, returnI);
                                finish();*/
                                break;
                            case DialogInterface.BUTTON_NEGATIVE : break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(tzlc_stats_add_tzlc12n.this);
                builder.setMessage("Do you want to save Match Stats ???").setNegativeButton("No",dialog).setPositiveButton("Yes",dialog).show();
                break;

            //case R.id.undo : undoHandler(undo);item.setEnabled(false); break;

            case R.id.color : Intent jerseyColor = new Intent(tzlc_stats_add_tzlc12n.this, tzlc_change_jersey_color.class);
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
                                /*matchSwapBuff = 0L;
                                matchStartTime = SystemClock.uptimeMillis();
                                timerMatch.setText("" + String.format("%02d", 0) + " : " + String.format("%02d", 0));
                                if (matchTime < 2700)
                                    halftime = 2400;
                                else
                                    halftime = 2700;
                                halftime=0;
                                String resetReason [] = getResources().getStringArray(R.array.resetTime);
                                Highlight highlight = new Highlight(matchID,-1 , -300,
                                        matchTime,
                                        resetReason[resetCount],
                                        "--NA--");
                                datasource.addHighlight(highlight);
                                resetCount++;*/
                                break;
                            case DialogInterface.BUTTON_NEGATIVE : break;
                        }
                    }
                };
                AlertDialog.Builder resetbuilder = new AlertDialog.Builder(tzlc_stats_add_tzlc12n.this);
                resetbuilder.setTitle("Alert !!");
                resetbuilder.setMessage("Are you sure you want to reset Timer ??").setPositiveButton("Yes",resetdialog).setNegativeButton("No",resetdialog).show();

                break;
            case R.id.completedPasses :
                /*Intent completedPasses = new Intent(tzlc_stats_add_tzlc12n.this, tzlc_stats_completed_passes.class);
                Bundle extras_passes  = new Bundle();
                extras_passes.putLong("matchID", matchID);
                extras_passes.putInt("matchTime",matchTime+halftime);
                extras_passes.putInt("homeColor",temphomeColor);
                extras_passes.putInt("awayColor",tempawayColor);
                extras_passes.putString("homeClub",datasource.getClub( m.getHomeClubID()).getClubShortName()) ;
                extras_passes.putString("awayClub",datasource.getClub( m.getAwayClubID()).getClubShortName());
                completedPasses.putExtras(extras_passes);
                startActivityForResult(completedPasses,100);*/
                break;
        }

        return super.onOptionsItemSelected(item);
        //return true;

    }

}
