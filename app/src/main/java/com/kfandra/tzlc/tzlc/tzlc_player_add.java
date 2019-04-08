package com.kfandra.tzlc.tzlc;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class tzlc_player_add extends AppCompatActivity {
    private tzlcDataSource datasource;
    public EditText playername, currentvalue,lastName;
    public Spinner club, org;
    private long playerID;
    CheckBox sw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tzlc_player_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        datasource = new tzlcDataSource(this);
        datasource.open();

        playername = (EditText) findViewById(R.id.edtPlayerName);
        lastName = findViewById(R.id.edtPlayerLastName);
        currentvalue = (EditText) findViewById(R.id.edtCurrentValue);
        org = findViewById(R.id.spnPlayerOrgnization);
        club = findViewById(R.id.spnPlayerClub);
        sw = findViewById(R.id.chkPlayerSW);
        currentvalue.setText(""+-1);
        currentvalue.setVisibility(View.GONE);

        List<String> clubnames = datasource.getAllClubNames();
        ArrayAdapter<String> adaptor = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,clubnames);
        adaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adaptor.setDropDownViewResource(R.layout.dropdownitem);
        club.setAdapter(adaptor);

        List<String> orgNames = datasource.getAllOrgNames();
        ArrayAdapter<String> adaptor1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,orgNames);
        adaptor1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adaptor1.setDropDownViewResource(R.layout.dropdownitem);
        org.setAdapter(adaptor1);

        playerID = getIntent().getLongExtra("playerID",-1);
        if (playerID==-1)
        {
            playername.setText("");
            lastName.setText("");
        }
        else
        {
            Player player = datasource.getPlayer(playerID);
            playername.setText(player.getPlayerName().split("@")[0]);
            lastName.setText(player.getPlayerName().split("@")[1]);
            sw.setChecked(player.getSenialwombat()!=0);
            org.setSelection(orgNames.indexOf(datasource.getClub(player.getOrgID()).getClubName()));
            club.setSelection( clubnames.indexOf(datasource.getClub(player.getClubId()).getClubName()));
        }



        if(clubnames.size() == 0)
        {
            Toast.makeText(tzlc_player_add.this,"Error!! Please add atleast 1 club",Toast.LENGTH_SHORT).show();
            finish();
        }
        else{
            if(orgNames.size() == 0)
            {
                Toast.makeText(tzlc_player_add.this,"Error!! Please add atleast 1 organization",Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        Log.d(tzlc_player_add.class.getSimpleName(), "Clubs fetched " +clubnames.size());









        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if((playername.getText().toString().length()==0)|| (lastName.getText().toString().length()==0))
                {
                    Toast.makeText(tzlc_player_add.this,"Error!! Name can not be blank",Toast.LENGTH_SHORT).show();
                }else{
                    String pn = playername.getText().toString() +"@"+ lastName.getText().toString();
                    Player player = new Player(pn, datasource.getClubID(club.getSelectedItem().toString()), Integer.parseInt(currentvalue.getText().toString()));
                    player.setOrgID(datasource.getClubID(org.getSelectedItem().toString()));
                    player.setSenialwombat(sw.isChecked()?1:0);

                    long ifPlayerPresent = datasource.getPlayerID(playername.getText().toString().substring(0, 2) + ". " + lastName.getText().toString());
                    if (ifPlayerPresent != -1 && playerID != ifPlayerPresent ){
                        Toast.makeText(tzlc_player_add.this,"Name matches with existing player : " + datasource.getPlayer(ifPlayerPresent).getPlayerName().split("@")[0].substring(0,2)+". "+datasource.getPlayer(ifPlayerPresent).getPlayerName().split("@")[1] +". Please provide unquie name.",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if (playerID == -1) {
                            datasource.addPlayer(player);
                        } else {
                            player.setId(playerID);
                            datasource.updatePlayer(player);
                        }

                    finish();
                    }
                }

            }
        });
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        datasource.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        datasource.close();
    }

}
