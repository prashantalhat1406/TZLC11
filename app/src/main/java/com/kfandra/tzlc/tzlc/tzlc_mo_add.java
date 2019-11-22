package com.kfandra.tzlc.tzlc;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

public class tzlc_mo_add extends AppCompatActivity {

    public Spinner player, jobprofile,clubName;
    public CheckBox ontime;
    public long matchID,moID;
    public EditText moTime;
    private tzlcDataSource datasource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tzlc_mo_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle b = getIntent().getExtras();
        matchID = b.getLong("matchID",-1);
        moID = b.getLong("moID",-1);

        datasource = new tzlcDataSource(this);
        datasource.open();

        jobprofile = findViewById(R.id.spnMOJobProfile);
        ontime = findViewById(R.id.chkMOOnTime);
        clubName = findViewById(R.id.spnMOClubName);
        player = findViewById(R.id.spnMOPlayerName);
        moTime = findViewById(R.id.edtMOTime);
        moTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = moTime.getText().toString();
                if(str.length() == 2)
                    moTime.append(":");
            }
        });


        ArrayAdapter<CharSequence> adapterjob = ArrayAdapter.createFromResource(this,R.array.MOJobProfile,R.layout.dropdownitem);
        adapterjob.setDropDownViewResource(R.layout.dropdownitem);
        jobprofile.setAdapter(adapterjob);


        final List<String> clubnames = datasource.getAllClubNamesApartFromMatchClubs(matchID);
        ArrayAdapter<String> adaptorClubs = new ArrayAdapter<String>(this,R.layout.dropdownitem,clubnames);
        adaptorClubs.setDropDownViewResource(R.layout.dropdownitem);
        clubName.setAdapter(adaptorClubs);



        clubName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                player.setEnabled(true);

                List<String> playernames = datasource.getAllPlayerNamesForClub(datasource.getClubID(clubName.getSelectedItem().toString()));
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(tzlc_mo_add.this,R.layout.dropdownitem,playernames);
                adapter.setDropDownViewResource(R.layout.dropdownitem);
                player.setAdapter(adapter);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        if(moID == -1){
            getSupportActionBar().setTitle("Add Match-Offcial");
            player.setEnabled(false);
            clubName.setAdapter(adaptorClubs);
            jobprofile.setAdapter(adapterjob);
            invalidateOptionsMenu();
        }
        else{
            MatchOffcial matchOffcial = datasource.getMatchOffcial(moID);

                getSupportActionBar().setTitle("Update/Delete Match-Offcial");
                if (matchOffcial.getOnTime() == 1)
                    ontime.setChecked(true);
                else
                    ontime.setChecked(false);


                clubName.setSelection(adaptorClubs.getPosition(datasource.getClub(matchOffcial.getClubID()).getClubName()));

                List<String> playernames = datasource.getAllPlayerNamesForClub(matchOffcial.getClubID());
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(tzlc_mo_add.this, R.layout.dropdownitem, playernames);
                adapter.setDropDownViewResource(R.layout.dropdownitem);
                String playeName = datasource.getPlayer(matchOffcial.getPlayerId()).getPlayerName().split("@")[0].substring(0, 2) + ". " + datasource.getPlayer(matchOffcial.getPlayerId()).getPlayerName().split("@")[1];
                player.setSelection(adapter.getPosition(playeName));

                jobprofile.setSelection(matchOffcial.getJob());

                moTime.setText(""+ String.format("%02d", (matchOffcial.getMoTime()/60))+ ":" + String.format("%02d", (matchOffcial.getMoTime()%60)));

        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int motime;

                String srt = moTime.getText().toString();
                if(srt.length() == 0 )
                    motime = 0;
                else {
                    String[] arr = srt.split(":");
                    motime = (Integer.parseInt(arr[0]) * 60) + Integer.parseInt(arr[1]);
                }

                MatchOffcial matchOffcial = new MatchOffcial();
                matchOffcial.setClubID( datasource.getClubID(clubName.getSelectedItem().toString()));
                //matchOffcial.setPlayerId(datasource.getPlayerID(player.getSelectedItem().toString()));
                matchOffcial.setPlayerId(datasource.getPlayerID(player.getSelectedItem().toString(),matchOffcial.getClubID()));
                matchOffcial.setMatchId(matchID);
                matchOffcial.setJob(jobprofile.getSelectedItemPosition());
                if(ontime.isChecked())
                    matchOffcial.setOnTime(1);
                else
                    matchOffcial.setOnTime(0);

                matchOffcial.setClubID( datasource.getClubID(clubName.getSelectedItem().toString()));
                matchOffcial.setMoTime(motime);

                if(moID == -1)
                    datasource.addMatchOffcial(matchOffcial);
                else
                {
                    matchOffcial.setId(moID);
                    datasource.updateMatchOffcial(matchOffcial);
                }
                Intent returnI = new Intent();
                returnI.putExtra("matchID",matchID);
                setResult(100,returnI);
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_delete, menu);
        menu.findItem(R.id.editScreen).setVisible(false);
        menu.findItem(R.id.addScreen).setVisible(false);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if(moID == -1)
            menu.findItem(R.id.deleteScreen).setVisible(false);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.editScreen :          break;
            case R.id.deleteScreen :
                DialogInterface.OnClickListener dialog = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which)
                        {
                            case DialogInterface.BUTTON_POSITIVE :
                                //datasource.deleteGoal(goalID);
                                datasource.deleteMatchOffcial(moID);
                                Intent returnI = new Intent();
                                returnI.putExtra("matchID",matchID);
                                setResult(100,returnI);
                                finish(); break;
                            case DialogInterface.BUTTON_NEGATIVE : break;
                        }
                    }
                };
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(tzlc_mo_add.this);
                builder.setMessage("Are you sure, you want to delete MO ?").setPositiveButton("Yes",dialog).setNegativeButton("No",dialog).show();
        }
        return super.onOptionsItemSelected(item);
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
        returnI.putExtra("matchID",matchID);
        setResult(100,returnI);
        finish();
    }

}
