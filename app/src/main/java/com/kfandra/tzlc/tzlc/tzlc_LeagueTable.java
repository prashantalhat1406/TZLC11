package com.kfandra.tzlc.tzlc;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class tzlc_LeagueTable extends AppCompatActivity {

    private tzlcDataSource datasource;
    private ListView lv,lv2;
    private List<PointTable> pointTableListLeague1,pointTableListLeague2;
    private List<Club> clubs;
    private List<Match> matches;
    private Stats stats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tzlc__league_table);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("TZLC League Tables");

        datasource= new tzlcDataSource(this);
        datasource.open();

        clubs = datasource.getAllClubs();

        pointTableListLeague1 = new ArrayList<PointTable>();
        pointTableListLeague2 = new ArrayList<PointTable>();

        for (Club club : clubs) {
            if(club.getId() != 3) {
                PointTable pt = new PointTable();
                matches = datasource.getAllMatchesForClubTillDate(club.getId());
                //pt.setMatchesPlayed(matches.size());
                pt.setClubID(club.getId());
                for (Match match : matches) {
                    stats = datasource.getAllStatsForMatch(match.getId());
                    if(stats.getAway_Score() != -1)
                    {
                        pt.setMatchesPlayed(pt.getMatchesPlayed()+1);
                        if (match.getHomeClubID() == club.getId()) {
                            pt.setHomeMatchesPlayed(pt.getHomeMatchesPlayed() + 1);
                            if (stats.getHome_Score() != -1 && stats.getAway_Score() != -1) {
                                pt.setGoalsScored(pt.getGoalsScored() + stats.getHome_Score());
                                pt.setGoalsTaken(pt.getGoalsScored() + stats.getAway_Score());
                                pt.setGoalDifference(pt.getGoalsScored() - pt.getGoalsTaken());
                                if (stats.getHome_Score() == stats.getAway_Score())
                                    pt.setDraw(pt.getDraw() + 1);
                                else {
                                    if (stats.getHome_Score() > stats.getAway_Score())
                                        pt.setWin(pt.getWin() + 1);
                                    else
                                        pt.setLost(pt.getLost() + 1);
                                }
                            }
                        }
                        else
                            {
                            pt.setAwayMatchesPlayed(pt.getAwayMatchesPlayed() + 1);
                            if (stats.getHome_Score() != -1 && stats.getAway_Score() != -1) {
                                pt.setGoalsScored(pt.getGoalsScored() + stats.getAway_Score());
                                pt.setGoalsTaken(pt.getGoalsScored() + stats.getHome_Score());
                                pt.setGoalDifference(pt.getGoalsScored() - pt.getGoalsTaken());
                                if (stats.getHome_Score() == stats.getAway_Score())
                                    pt.setDraw(pt.getDraw() + 1);
                                else {
                                    if (stats.getHome_Score() > stats.getAway_Score())
                                        pt.setLost(pt.getLost() + 1);
                                    else
                                        pt.setWin(pt.getWin() + 1);

                                }
                            }
                        }
                    }

                }
                pt.setPoints(pt.getWin() * 3 + pt.getDraw());
                if(club.getId() == 1 || club.getId() == 2)
                    pt.setPoints(pt.getPoints()*2);

                if (club.getId() == 1 || club.getId() == 2 || club.getId() == 6 || club.getId() == 7 || club.getId() == 10)
                    pointTableListLeague2.add(pt);
                else
                    pointTableListLeague1.add(pt);
            }
        }

        Collections.sort(pointTableListLeague1, new Comparator<PointTable>() {
            @Override
            public int compare(PointTable o1, PointTable o2) {
                return Integer.valueOf(o2.getPoints()).compareTo(o1.getPoints());
            }
        });

        Collections.sort(pointTableListLeague2, new Comparator<PointTable>() {
            @Override
            public int compare(PointTable o1, PointTable o2) {
                return Integer.valueOf(o2.getPoints()).compareTo(o1.getPoints());
            }
        });


        lv = findViewById(R.id.displayLeagueTable);
        adaptor_league_table adaptor = new adaptor_league_table(tzlc_LeagueTable.this, R.layout.pointsdisplaylist, pointTableListLeague1,1);
        lv.setAdapter(adaptor);

        lv2 = findViewById(R.id.displayLeagueTable2);
        adaptor_league_table adaptor2 = new adaptor_league_table(tzlc_LeagueTable.this, R.layout.pointsdisplaylist, pointTableListLeague2,2);
        lv2.setAdapter(adaptor2);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

}
