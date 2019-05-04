package com.kfandra.tzlc.tzlc;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class tzlc_stats_completed_passes extends AppCompatActivity {

    private long matchID;
    private int matchTime;
    private  int homePasses, awayPasses;
    public EditText homeP, awayP;
    public Button save;
    public String homeClub, awayClub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tzlc_stats_completed_passes);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle b = getIntent().getExtras();
        matchID = b.getLong("matchID",-1);
        matchTime = b.getInt("matchTime",-1);
        homeClub = b.getString("homeClub","Home Club");
        awayClub = b.getString("awayClub","Away Club");
        int homeColor = b.getInt("homeColor",0);
        int awayColor = b.getInt("awayColor",0);


        TextView home = findViewById(R.id.txtHomeClubPasses);
        home.setText(" Enter Passes for " + homeClub +" : " );
        home.setTextColor(homeColor);
        TextView away = findViewById(R.id.txtAwayClubPasses);
        away.setText(" Enter Passes for " + awayClub +" : " );
        away.setTextColor(awayColor);


        save = findViewById(R.id.butSavePasses);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeP = findViewById(R.id.edtHomePasses);
                awayP = findViewById(R.id.edtAwayPasses);

                homePasses = Integer. parseInt(homeP.getText().toString());
                awayPasses = Integer. parseInt(awayP.getText().toString());
                Intent returnI = new Intent();
                Bundle extras  = new Bundle();
                extras.putLong("matchID", matchID);
                extras.putInt("homePasses", homePasses);
                extras.putInt("awayPasses", awayPasses);
                returnI.putExtras (extras);
                setResult(100,returnI);
                finish();
            }
        });



        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent returnI = new Intent();
        returnI.putExtra("matchID",matchID);
        setResult(100,returnI);
        finish();
    }
}
