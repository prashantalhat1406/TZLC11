package com.kfandra.tzlc.tzlc;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class tzlc_squad_add extends AppCompatActivity {

    private List<Player> homePlayerNames, awayPlayerNames;
    private List<Squad> homePlayers, awayPlayers;
    private tzlcDataSource datasource;
    private long matchID;
    Match m;
    private ListView homeList, awayList;
    private List<Squad> playingPlayersHome, playingPlayersAway;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tzlc_squad_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle b = getIntent().getExtras();
        matchID = b.getLong("matchID",-1);

        datasource = new tzlcDataSource(this);
        datasource.open();

        m = new Match();
        m = datasource.getMatch(matchID);

        playingPlayersHome = new ArrayList<>();
        playingPlayersAway = new ArrayList<>();

        homePlayers = new ArrayList<>();
        awayPlayers = new ArrayList<>();
        homePlayers = datasource.getAllSquadForMatchandClub(matchID,m.getHomeClubID());
        awayPlayers = datasource.getAllSquadForMatchandClub(matchID,m.getAwayClubID());

        TextView homeClubname = findViewById(R.id.txtsquadHomeClub);
        homeClubname.setText(""+datasource.getClub(m.getHomeClubID()).getClubName());

        homeList = findViewById(R.id.lstHomeSquad);
        if(homePlayers.size() == 0)
        {
            homePlayerNames = datasource.getAllMatchPlayers(m.getHomeClubID(), matchID);
            for (Player homePlayerName : homePlayerNames) {
                Squad squad = new Squad();
                squad.setPlayerID(homePlayerName.getId());
                squad.setClubID(homePlayerName.getClubId());
                squad.setAbsent(0);
                squad.setMatchID(matchID);
                datasource.addSquad(squad);
                homePlayers.add(squad);
            }
        }
        adaptor_squad_add squadadaptor = new adaptor_squad_add(tzlc_squad_add.this, R.layout.fixturelistitem, homePlayers);
        homeList.setAdapter(squadadaptor);
        homeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBox cb = (CheckBox) view.findViewById(R.id.chkSquadAbsent);
                cb.setChecked(!cb.isChecked());
                if(!cb.isChecked())
                    homePlayers.get(position).setAbsent(1);
                else
                    homePlayers.get(position).setAbsent(0);

                /*if(!cb.isChecked()) {
                    if (!playingPlayersHome.contains(homePlayers.get(position))) {
                        homePlayers.get(position).setAbsent(1);
                        playingPlayersHome.add(homePlayers.get(position));
                    }
                }else
                {
                    if (playingPlayersHome.contains(homePlayers.get(position))) {
                        homePlayers.get(position).setAbsent(0);
                        playingPlayersHome.remove(homePlayers.get(position));
                    }
                }*/
            }
        });

        TextView awayClubname = findViewById(R.id.txtsquadAwayClub);
        awayClubname.setText(""+datasource.getClub(m.getAwayClubID()).getClubName());

        awayList = findViewById(R.id.lstAwaySquad);
        if(awayPlayers.size() == 0)
        {
            awayPlayerNames = datasource.getAllMatchPlayers(m.getAwayClubID(), matchID);
            for (Player awayPlayerName : awayPlayerNames) {
                Squad squad = new Squad();
                squad.setPlayerID(awayPlayerName.getId());
                squad.setClubID(awayPlayerName.getClubId());
                squad.setAbsent(0);
                squad.setMatchID(matchID);
                datasource.addSquad(squad);
                awayPlayers.add(squad);
            }
        }

        adaptor_squad_add awaysquadadaptor = new adaptor_squad_add(tzlc_squad_add.this, R.layout.fixturelistitem, awayPlayers);
        awayList.setAdapter(awaysquadadaptor);
        awayList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBox cb = (CheckBox) view.findViewById(R.id.chkSquadAbsent);
                cb.setChecked(!cb.isChecked());

                if(!cb.isChecked())
                    awayPlayers.get(position).setAbsent(1);
                else
                    awayPlayers.get(position).setAbsent(0);
                /*
                if(!cb.isChecked()) {
                    if (!playingPlayersAway.contains(awayPlayers.get(position))) {
                        awayPlayers.get(position).setAbsent(1);
                        playingPlayersAway.add(awayPlayers.get(position));
                    }
                }else
                {
                    if (playingPlayersAway.contains(awayPlayers.get(position))) {
                        awayPlayers.get(position).setAbsent(0);
                        playingPlayersAway.remove(awayPlayers.get(position));
                    }
                }*/
            }
        });

        Button save = findViewById(R.id.butSquadSave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Squad squad : homePlayers) {
                    Log.d(tzlc_squad_add.class.getSimpleName(), "Home : "+ datasource.getPlayer(squad.getPlayerID()).getPlayerName());
                    datasource.updateSquad(squad);
                }

                for (Squad squad : awayPlayers) {
                    Log.d(tzlc_squad_add.class.getSimpleName(), "Away : "+ datasource.getPlayer(squad.getPlayerID()).getPlayerName());
                    datasource.updateSquad(squad);
                }

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

        /*final List<String> clubnames = datasource.getAllClubNamesApartFromMatchClubs(matchID);
        ArrayAdapter<String> adaptorClubs = new ArrayAdapter<String>(this,R.layout.dropdownitem,clubnames);
        adaptorClubs.setDropDownViewResource(R.layout.dropdownitem);
        clubName.setAdapter(adaptorClubs);*/

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

    }

    @Override
    protected void onPause() {
        super.onPause();
        datasource.close();
    }

}
