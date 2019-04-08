package com.kfandra.tzlc.tzlc;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class tzlc_Reports extends AppCompatActivity {

    private tzlcDataSource datasource;
    Spinner reports;
    HashMap<Long,Integer> playerList;
    private ListView lv;

    public void populateReportDisplayList(List<Player> players)
    {
        adaptor_report adaptor = new adaptor_report(tzlc_Reports.this, R.layout.playerdisplaylist, players);
        lv.setAdapter(adaptor);

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tzlc__reports);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        datasource= new tzlcDataSource(this);
        datasource.open();

        lv = findViewById(R.id.displayReport);

        reports = findViewById(R.id.spnReport);
        ArrayAdapter<CharSequence> adapterReports = ArrayAdapter.createFromResource(this,R.array.reports,android.R.layout.simple_spinner_item);
        adapterReports.setDropDownViewResource(R.layout.dropdownitem);
        reports.setAdapter(adapterReports);

        playerList = datasource.getGoalsReport();

        reports.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0 : playerList = datasource.getGoalsReport();
                        break;
                    case 1 : playerList = datasource.getGoalAssistReport();
                        break;
                }

                List<Player> top10Players = new ArrayList<Player>();
                for(Map.Entry<Long, Integer > entry : playerList.entrySet())
                {
                    Player p = datasource.getPlayer(entry.getKey());
                    p.setTotalGoal(entry.getValue());
                    if(p.getPlayerName() != null)
                        top10Players.add(p);
                }

                Collections.sort(top10Players, new Comparator<Player>() {
                    @Override
                    public int compare(Player o1, Player o2) {
                        return Integer.valueOf(o2.getTotalGoal()).compareTo(o1.getTotalGoal());
                    }
                });

                populateReportDisplayList(top10Players);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        List<Player> top10Players = new ArrayList<Player>();

        for(Map.Entry<Long, Integer > entry : playerList.entrySet())
        {
            Player p = datasource.getPlayer(entry.getKey());
            p.setTotalGoal(entry.getValue());
            if(p.getPlayerName() != null)
                top10Players.add(p);
        }
        Collections.sort(top10Players, new Comparator<Player>() {
            @Override
            public int compare(Player o1, Player o2) {
                return Integer.valueOf(o2.getTotalGoal()).compareTo(o1.getTotalGoal());
            }
        });
        populateReportDisplayList(top10Players);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        datasource.close();
    }
}
