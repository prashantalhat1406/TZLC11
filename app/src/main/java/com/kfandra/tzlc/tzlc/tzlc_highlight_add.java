package com.kfandra.tzlc.tzlc;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class tzlc_highlight_add extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, TimePickerDialog.OnTimeSetListener {

    public long matchID;
    private tzlcDataSource datasource;
    private RadioGroup rdHLGroup,rdHL2Group;
    private EditText vcmtime, srtime;
    int matchTime;
    RadioButton homeClub, awayClub;
    Match m;
    CheckBox chk1,chk2,chk3,chk4,chk5,chk6,chk7,chk8,chk9,chk10,chk11,chk12;
    String highlight_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        highlight_1="";
        setContentView(R.layout.activity_tzlc_highlight_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        datasource= new tzlcDataSource(this);
        datasource.open();


        Bundle b = getIntent().getExtras();
        matchID = b.getLong("matchID",-1); //getIntent().getLongExtra("matchID", -1);
        matchTime = b.getInt("matchTime",-1);//getIntent().getIntExtra("matchTime", 0);

        m = datasource.getMatch(matchID);

        homeClub = findViewById(R.id.highlightHomeClub);
        homeClub.setText(datasource.getClub(m.getHomeClubID()).getClubShortName());
        homeClub.setTextColor(datasource.getClub(m.getHomeClubID()).getClubColor());
        awayClub = findViewById(R.id.highlightAwayClub);
        awayClub.setText(datasource.getClub(m.getAwayClubID()).getClubShortName());
        awayClub.setTextColor(datasource.getClub(m.getAwayClubID()).getClubColor());


        vcmtime = findViewById(R.id.edtVCMTime);
        vcmtime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = vcmtime.getText().toString();
                if(str.length() == 2)
                    vcmtime.append(":");
            }
        });
        /*vcmtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                new TimePickerDialog(tzlc_highlight_add.this,tzlc_highlight_add.this,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();
            }
        });*/


        srtime = findViewById(R.id.edtsrTime);
        srtime.setText(""+ String.format("%02d", (matchTime/60))+ ":" + String.format("%02d", (matchTime%60)));
        srtime.setEnabled(false);
        //rdHLGroup = findViewById(R.id.HLGroup);
        //((RadioButton)rdHLGroup.getChildAt(1)).setChecked(true);
        //rdHL2Group = findViewById(R.id.HL2Group);
        //((RadioButton)rdHL2Group.getChildAt(0)).setChecked(true);

        chk1 = findViewById(R.id.chkHL1);
        chk2 = findViewById(R.id.chkHL2);
        chk3 = findViewById(R.id.chkHL3);
        chk4 = findViewById(R.id.chkHL4);
        chk5 = findViewById(R.id.chkHL5);
        chk6 = findViewById(R.id.chkHL6);
        chk7 = findViewById(R.id.chkHL7);
        chk8 = findViewById(R.id.chkHL8);
        chk9 = findViewById(R.id.chkHL9);
        /*chk10 = findViewById(R.id.chkHL10);
        chk11 = findViewById(R.id.chkHL11);
        chk12 = findViewById(R.id.chkHL12);*/
        //chk1.setOnCheckedChangeListener(this);

        //chk2 = findViewById(R.id.chkHL2);
        //chk2.setOnCheckedChangeListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //long matchID, int vcmTime, int srTime, String highlight
                //String h = createHighlight();
                String srt = vcmtime.getText().toString();
                String[] arr = srt.split(":");
                if ((arr.length == 2)){
                    if(homeClub.isChecked()||awayClub.isChecked()){
                int srti = (Integer.parseInt(arr[0]) *60) + Integer.parseInt(arr[1]);
                //RadioButton rb = findViewById(rdHLGroup.getCheckedRadioButtonId());
                //Highlight highlight = new Highlight(matchID,srti,matchTime,rb.getText().toString());
                Highlight highlight = new Highlight(matchID,srti,matchTime,createHighlight());

                if (homeClub.isChecked())
                    highlight.setClubID(m.getHomeClubID());
                if (awayClub.isChecked())
                    highlight.setClubID(m.getAwayClubID());

                //RadioButton rb2 = findViewById(rdHL2Group.getCheckedRadioButtonId());
                //highlight.setHighlight2(rb2.getText().toString());
                highlight.setHighlight2("");

                long hID =  datasource.addHighlight(highlight);
                Intent returnI = new Intent();
                Bundle extras = new Bundle();
                extras.putLong("matchID",matchID);
                extras.putLong("highlightID",hID);
                returnI.putExtras(extras);
                setResult(100,returnI);
                finish();
                    }else{
                        Toast.makeText(tzlc_highlight_add.this, "Error !!! Please select Club.", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(tzlc_highlight_add.this,"Error !!! Please enter VCM time in MM:SS format.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        datasource.close();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent returnI = new Intent();
        Bundle extras = new Bundle();
        extras.putLong("matchID",matchID);
        //extras.putLong("clubID",datasource.getClubID(againstClub.getSelectedItem().toString()));
        returnI.putExtras(extras);
        //returnI.putExtra("matchID",matchID);

        setResult(100,returnI);
        finish();
    }



    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked)
            Toast.makeText(tzlc_highlight_add.this,"Highlight : "+buttonView.getText(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        vcmtime.setText(""+ String.format("%02d", hourOfDay)+ ":" + String.format("%02d", minute));
    }

    public String createHighlight()
    {
        String highlight = "";
        if(chk1.isChecked())
            highlight = highlight  + chk1.getText() + ", ";

        if(chk2.isChecked())
            highlight = highlight  + chk2.getText()+ ", ";

        if(chk3.isChecked())
            highlight = highlight  + chk3.getText()+ ", ";

        if(chk4.isChecked())
            highlight = highlight  + chk4.getText()+ ", ";

        if(chk5.isChecked())
            highlight = highlight  + chk5.getText()+ ", ";

        if(chk6.isChecked())
            highlight = highlight  + chk6.getText()+ ", ";

        if(chk7.isChecked())
            highlight = highlight + chk7.getText()+ ", ";

        if(chk8.isChecked())
            highlight = highlight + chk8.getText()+ ", ";

        if(chk9.isChecked())
            highlight = highlight  + chk9.getText()+ ", ";

        /*if(chk10.isChecked())
            highlight = highlight + chk10.getText()+ ", ";

        if(chk11.isChecked())
            highlight = highlight  + chk11.getText()+ ", ";

        if(chk12.isChecked())
            highlight = highlight  + chk12.getText()+ ", ";*/

        return highlight;
    }
}
