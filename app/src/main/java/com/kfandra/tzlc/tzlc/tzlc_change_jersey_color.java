package com.kfandra.tzlc.tzlc;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

public class tzlc_change_jersey_color extends AppCompatActivity {

    int homeColor,awayColor;
    Button save,cancel;
    Button homeButton,awayButton;
    private String[] colorNames;
    private int[] colors;
    public long matchID;
    Spinner homeJerseySpinner,awayJerseySpinner;

    public boolean isColorDark(int color){
        double darkness = 1-(0.299*Color.red(color) + 0.587*Color.green(color) + 0.114*Color.blue(color))/255;
        if(darkness<0.5){
            return false; // It's a light color
        }else{
            return true; // It's a dark color
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tzlc_change_jersey_color);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Change Screen Colors");

        Bundle b = getIntent().getExtras();
        matchID = b.getLong("matchID",-1);
        homeColor = b.getInt("homeColor",-1);
        awayColor = b.getInt("awayColor",-1);
        String hClub = b.getString("homeClubName","HOME");;
        String aClub = b.getString("awayClubName","AWAY");;

        save = findViewById(R.id.butSaveJerseyColor);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent returnI = new Intent();
                Bundle extras = new Bundle();
                extras.putLong("matchID", matchID);
                extras.putInt("homeColor", homeColor);
                extras.putInt("awayColor", awayColor);
                returnI.putExtras(extras);
                setResult(100, returnI);
                finish();
            }
        });

        homeButton = findViewById(R.id.butHomeJerseyColor);
        homeButton.setBackgroundTintList(ColorStateList.valueOf(homeColor));
        homeButton.setTextColor((isColorDark(homeColor)?Color.WHITE:Color.BLACK));
        homeButton.setText(hClub);

        awayButton = findViewById(R.id.butAwayJerseyColor);
        awayButton.setBackgroundTintList(ColorStateList.valueOf(awayColor));
        awayButton.setTextColor((isColorDark(awayColor)?Color.WHITE:Color.BLACK));
        awayButton.setText(aClub);

        colorNames = getResources().getStringArray(R.array.jerseycolors );
        colors = this.getResources().getIntArray(R.array.androidcolors);
        adaptor_spinner_color adaptor = new adaptor_spinner_color(tzlc_change_jersey_color.this,android.R.layout.simple_spinner_item,colorNames);
        homeJerseySpinner = findViewById(R.id.spnJerseyH);
        homeJerseySpinner.setAdapter(adaptor);
        homeJerseySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                homeColor= colors[position];
                homeButton.setBackgroundTintList(ColorStateList.valueOf(homeColor));
                homeButton.setTextColor((isColorDark(homeColor)?Color.WHITE:Color.BLACK));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        awayJerseySpinner = findViewById(R.id.spnJerseyA);
        awayJerseySpinner.setAdapter(adaptor);
        awayJerseySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                awayColor = colors[position];
                awayButton.setBackgroundTintList(ColorStateList.valueOf(awayColor));
                awayButton.setTextColor((isColorDark(awayColor)?Color.WHITE:Color.BLACK));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


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
