package com.kfandra.tzlc.tzlc;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class tzlc_formation_add extends AppCompatActivity {

    private List<Player> homePlayerNames, awayPlayerNames;
    private List<Squad> homePlayers, awayPlayers;
    private tzlcDataSource datasource;
    private long matchID;
    Match m;
    private ListView homeList, awayList;
    private int def=0,mid=0,str=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tzlc_formation_add);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle b = getIntent().getExtras();
        matchID = b.getLong("matchID",-1);

        datasource = new tzlcDataSource(this);
        datasource.open();

        m = new Match();
        m = datasource.getMatch(matchID);

        homePlayers = new ArrayList<>();
        homePlayers = datasource.getAllSquadForMatchandClub(matchID, m.getHomeClubID());

        //TextView homeClubname = findViewById(R.id.txtsquadHomeClub);
        //homeClubname.setText(""+datasource.getClub(m.getHomeClubID()).getClubName());

        homeList = findViewById(R.id.lstFormationSquad);

        adaptor_squad_display squadadaptor = new adaptor_squad_display(tzlc_formation_add.this, R.layout.fixturelistitem, homePlayers);
        homeList.setAdapter(squadadaptor);

        Spinner formations = findViewById(R.id.spnFormations);
        ArrayAdapter<CharSequence> formationsadapter = ArrayAdapter.createFromResource(this,R.array.formations,R.layout.dropdownitem);
        formationsadapter.setDropDownViewResource(R.layout.dropdownitem);
        formations.setAdapter(formationsadapter);
        formations.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Toast.makeText(tzlc_formation_add.this,"4-4-2",Toast.LENGTH_SHORT) .show();
                        showDefLine(4);
                        showMidLine(4);
                        showStrLine(2);
                        break;
                    case 1:
                        Toast.makeText(tzlc_formation_add.this,"3-5-2",Toast.LENGTH_SHORT) .show();
                        showDefLine(3);
                        showMidLine(5);
                        showStrLine(2);
                        break;
                    case 2:
                        Toast.makeText(tzlc_formation_add.this,"4-3-3",Toast.LENGTH_SHORT) .show();
                        showDefLine(4);
                        showMidLine(3);
                        showStrLine(3);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void showMidLine(int positions) {
        TextView t1 = findViewById(R.id.formationRM);
        TextView t2 = findViewById(R.id.formationRCM);
        TextView t3 = findViewById(R.id.formationCM);
        TextView t4 = findViewById(R.id.formationLCM);
        TextView t5 = findViewById(R.id.formationLM);
        switch (positions){
            case 5 :
                t1.setVisibility(View.VISIBLE);
                t2.setVisibility(View.VISIBLE);
                t3.setVisibility(View.VISIBLE);
                t4.setVisibility(View.VISIBLE);
                t5.setVisibility(View.VISIBLE);
                break;
            case 4 :
                t1.setVisibility(View.VISIBLE);
                t2.setVisibility(View.VISIBLE);
                t3.setVisibility(View.GONE);
                t4.setVisibility(View.VISIBLE);
                t5.setVisibility(View.VISIBLE);
                break;
            case 3 :
                t1.setVisibility(View.GONE);
                t2.setVisibility(View.VISIBLE);
                t3.setVisibility(View.VISIBLE);
                t4.setVisibility(View.VISIBLE);
                t5.setVisibility(View.GONE);
                break;
        }
    }

    private void showStrLine(int positions) {
        TextView t1 = findViewById(R.id.formationRST);
        TextView t2 = findViewById(R.id.formationST);
        TextView t3 = findViewById(R.id.formationLST);

        switch (positions){
            case 1 :
                t1.setVisibility(View.GONE);
                t2.setVisibility(View.VISIBLE);
                t3.setVisibility(View.GONE);
                break;
            case 2 :
                t1.setVisibility(View.VISIBLE);
                t2.setVisibility(View.GONE);
                t3.setVisibility(View.VISIBLE);
                break;
            case 3 :
                t1.setVisibility(View.VISIBLE);
                t2.setVisibility(View.VISIBLE);
                t3.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void showDefLine(int positions) {
        TextView t1 = findViewById(R.id.formationRB);
        TextView t2 = findViewById(R.id.formationRCD);
        TextView t3 = findViewById(R.id.formationCD);
        TextView t4 = findViewById(R.id.formationLCD);
        TextView t5 = findViewById(R.id.formationLB);
        switch (positions){
            case 4 :
                t1.setVisibility(View.VISIBLE);
                t2.setVisibility(View.VISIBLE);
                t3.setVisibility(View.GONE);
                t4.setVisibility(View.VISIBLE);
                t5.setVisibility(View.VISIBLE);
                break;
            case 3 :
                t1.setVisibility(View.GONE);
                t2.setVisibility(View.VISIBLE);
                t3.setVisibility(View.VISIBLE);
                t4.setVisibility(View.VISIBLE);
                t5.setVisibility(View.GONE);
                break;
        }
    }

}
