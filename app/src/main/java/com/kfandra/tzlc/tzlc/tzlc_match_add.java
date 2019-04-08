package com.kfandra.tzlc.tzlc;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

public class tzlc_match_add extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private tzlcDataSource datasource;
    public EditText datepicker;
    Spinner type,subtype,homeClubName,awayClubName;
    public long matchID;
    public int scrollIndex;

    private long d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tzlc_match_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        homeClubName = findViewById(R.id.spnHomeClub);
        awayClubName = findViewById(R.id.spnAwayClub);

        datasource = new tzlcDataSource(this);
        datasource.open();

        Bundle b = getIntent().getExtras();
        matchID = b.getLong("matchID",-1);
        scrollIndex = b.getInt("scrollIndex",-1);

        List<String> clubnames = datasource.getAllClubNames();
        clubnames.add("TBD");
        ArrayAdapter<String> adaptor = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,clubnames);
        adaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adaptor.setDropDownViewResource(R.layout.dropdownitem);
        homeClubName.setAdapter(adaptor);
        awayClubName.setAdapter(adaptor);

        type = findViewById(R.id.spnType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.matchType,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.dropdownitem);
        type.setAdapter(adapter);

        subtype = findViewById(R.id.spnSubType);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,R.array.matchSubType,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter1.setDropDownViewResource(R.layout.dropdownitem);
        subtype.setAdapter(adapter1);

        Calendar c = Calendar.getInstance();
        datepicker = (EditText) findViewById(R.id.edtMatchDate);
        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                DatePickerDialog datePickerDialog =  new DatePickerDialog(tzlc_match_add.this,tzlc_match_add.this,c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));//.getDatePicker();// .show();
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });

        //matchID = getIntent().getLongExtra("matchID",-1);


        if (matchID==-1){
            datepicker.setText("");
        }else{
            Match match = datasource.getMatch(matchID);
            datepicker.setText(""+String.format("%02d", (match.getDate_number()%100))+"/"+String.format("%02d", ((match.getDate_number()/100)%100))+"/"+match.getDate_number()/10000);
            if(match.getHomeClubID() == 0)
                homeClubName.setSelection(adaptor.getPosition("TBD"));
            else
                homeClubName.setSelection(adaptor.getPosition(datasource.getClub(match.getHomeClubID()).getClubName()));

            if(match.getAwayClubID() == 0)
                awayClubName.setSelection(adaptor.getPosition("TBD"));
            else
                awayClubName.setSelection(adaptor.getPosition(datasource.getClub(match.getAwayClubID()).getClubName()));
            type.setSelection(match.getType());
            subtype.setSelection(match.getSubtype());
            d = match.getDate_number();
        }








        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(datepicker.getText().toString().length()==0){
                    Toast.makeText(tzlc_match_add.this,"Error!! Please select date.",Toast.LENGTH_SHORT).show();
                }else{
                Match match = new Match();
                match.setDate_number(d);
                if(homeClubName.getSelectedItem().toString() == "TBD")
                    match.setHomeClubID(0);
                else
                    match.setHomeClubID(datasource.getClubID(homeClubName.getSelectedItem().toString()));

                if(awayClubName.getSelectedItem().toString() == "TBD")
                    match.setAwayClubID(0);
                else
                    match.setAwayClubID(datasource.getClubID(awayClubName.getSelectedItem().toString()));

                match.setType(type.getSelectedItemPosition());
                match.setSubtype(subtype.getSelectedItemPosition());
                match.setResult(-1);

                if(match.getHomeClubID() == match.getAwayClubID() )
                    Toast.makeText(tzlc_match_add.this,"Error!! Home & Away club can not be same",Toast.LENGTH_SHORT).show();
                else {
                    if(matchID==-1){datasource.addMatch(match); }
                    else
                        {
                            match.setId(matchID);datasource.updateMatch(match);
                        }
                    Intent returnI = new Intent();
                    Bundle extras = new Bundle();
                    extras.putLong("matchID",matchID);
                    extras.putInt("scrollIndex",scrollIndex);
                    returnI.putExtras(extras);
                    setResult(100,returnI);
                    finish();
                }}

            }
        });
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        datepicker.setText(""+String.format("%02d",dayOfMonth )+"/"+String.format("%02d",   (month+1))+"/"+year);
        ;
        d=year*100+(month+1);
        d=d*100+dayOfMonth;

    }

    @Override
    protected void onResume() {
        super.onResume();
        datasource.open();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        matchID = data.getLongExtra("matchID",-1);
        scrollIndex = data.getIntExtra("scrollIndex",scrollIndex);

    }



    @Override
    protected void onPause() {
        super.onPause();
        datasource.close();
    }

}


