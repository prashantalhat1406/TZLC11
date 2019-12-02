package com.kfandra.tzlc.tzlc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class tzlc_squad_add extends AppCompatActivity {

    private List<Player> players;
    private List<Squad> squadPlayers;
    private tzlcDataSource datasource;
    private long matchID,clubID;
    Match m;
    private ListView squadlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tzlc_squad_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle b = getIntent().getExtras();
        matchID = b.getLong("matchID",-1);
        clubID = b.getLong("clubID",-1);

        datasource = new tzlcDataSource(this);
        datasource.open();

        m = new Match();
        m = datasource.getMatch(matchID);

        squadPlayers = new ArrayList<>();
        squadPlayers = datasource.getAllSquadForMatchandClub(matchID,clubID);

        TextView clubname = findViewById(R.id.txtsquadClubName);
        clubname.setText(""+datasource.getClub(clubID).getClubName());

        squadlist = findViewById(R.id.lstSquadList);
        if(squadPlayers.size() == 0)
        {
            players = datasource.getAllMatchPlayers(clubID, matchID);
            for (Player player : players) {
                Squad squad = new Squad();
                squad.setPlayerID(player.getId());
                if(clubID == 1 || clubID == 2)
                    squad.setClubID(player.getOrgID());
                else
                    squad.setClubID(player.getClubId());
                if(clubID == 4)
                {
                    squad.setAbsent(1);
                    squad.setClubID(4);
                }
                else
                    squad.setAbsent(0);
                squad.setMatchID(matchID);
                long _id = datasource.addSquad(squad);
                squad.setId(_id);
                squadPlayers.add(squad);
            }
        }
        adaptor_squad_add squadadaptor = new adaptor_squad_add(tzlc_squad_add.this, R.layout.fixturelistitem, squadPlayers);
        squadlist.setAdapter(squadadaptor);
        squadlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBox cb = (CheckBox) view.findViewById(R.id.chkSquadAbsent);
                cb.setChecked(!cb.isChecked());
                if(!cb.isChecked())
                    squadPlayers.get(position).setAbsent(1);
                else
                    squadPlayers.get(position).setAbsent(0);
            }
        });

        Button save = findViewById(R.id.butSquadSave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Squad squad : squadPlayers) {
                    datasource.updateSquad(squad);
                }

                Toast t =  Toast.makeText(tzlc_squad_add.this, "Squad Added for " + datasource.getClub(clubID).getClubName() , Toast.LENGTH_SHORT);
                t.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL,0,0);
                t.show();

                Intent returnI = new Intent();
                returnI.putExtra("matchID", matchID);
                setResult(100, returnI);
                finish();

            }
        });

        Button cancel = findViewById(R.id.butSquadCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnI = new Intent();
                returnI.putExtra("matchID", matchID);
                setResult(100, returnI);
                finish();
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
        Intent returnI = new Intent();
        returnI.putExtra("matchID",matchID);
        setResult(100,returnI);
        finish();
    }

}
