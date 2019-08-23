package com.kfandra.tzlc.tzlc;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class tzlc_match_details_tabs extends AppCompatActivity {
    public long matchID;
    private tzlcDataSource datasource;
    public int tabID;
    public Menu tabMenu;
    private List<Goal> goals;
    private List<Card> cards;
    private List<Highlight> highlights;
    private List<Substitute> substitutes;
    //private Stats stats;
    private List<MatchOffcial> mos;
    private List<Loan> loans;
    private int fromClubDetails=-1;
    int scrollIndexL = 0;

    public long getMatchID() {
        return matchID;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tzlc_match_details_tabs);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        datasource= new tzlcDataSource(this);
        datasource.open();

        Bundle b = getIntent().getExtras();
        matchID = b.getLong("matchID",-1);
        fromClubDetails = b.getInt("fromClubDetails",-1);
        scrollIndexL = b.getInt("scrollIndex",-1);

        //matchID = getIntent().getLongExtra("matchID",-1);

        Match match = datasource.getMatch(matchID);

        if (datasource.getClub(match.getHomeClubID()).getClubShortName() == null)
            getSupportActionBar().setTitle("Match : TBD vs TBD");
        else
            getSupportActionBar().setTitle("Match : " + datasource.getClub(match.getHomeClubID()).getClubShortName() + " vs " + datasource.getClub(match.getAwayClubID()).getClubShortName());

        TabLayout tabs = (TabLayout) findViewById(R.id.tabMatchDetailLayout);


        /*View stats = getLayoutInflater().inflate(R.layout.tabslayout, null);
        stats.findViewById(R.id.tabIcon).setBackgroundResource(R.drawable.stats);
        tabs.addTab(tabs.newTab().setCustomView(stats));

        View goal = getLayoutInflater().inflate(R.layout.tabslayout, null);
        goal.findViewById(R.id.tabIcon).setBackgroundResource(R.drawable.goal);
        tabs.addTab(tabs.newTab().setCustomView(goal));

        View loan = getLayoutInflater().inflate(R.layout.tabslayout, null);
        loan.findViewById(R.id.tabIcon).setBackgroundResource(R.drawable.loan);
        tabs.addTab(tabs.newTab().setCustomView(loan));

        View card = getLayoutInflater().inflate(R.layout.tabslayout, null);
        card.findViewById(R.id.tabIcon).setBackgroundResource(R.drawable.cards);
        tabs.addTab(tabs.newTab().setCustomView(card));

        View mo = getLayoutInflater().inflate(R.layout.tabslayout, null);
        mo.findViewById(R.id.tabIcon).setBackgroundResource(R.drawable.referee);
        tabs.addTab(tabs.newTab().setCustomView(mo));

        View highlight = getLayoutInflater().inflate(R.layout.tabslayout, null);
        highlight.findViewById(R.id.tabIcon).setBackgroundResource(R.drawable.tv);
        tabs.addTab(tabs.newTab().setCustomView(highlight));*/

        //String [] images={"stats","goal","loan","cards","referee","tv","subs"};
        String [] images={"stats","goal","loan","cards","referee","tv","squad","subs"};
        //String [] images={"Stats","Goals","Loans","Cards","MO's","Highlight"};

        for (int i = 0; i < images.length; i++) {
            ImageView imageView = new ImageView(tzlc_match_details_tabs.this);
            imageView.setImageResource(getResources().getIdentifier(images[i],"drawable",getPackageName()));
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setMaxWidth(50);
            imageView.setMaxHeight(50);
            tabs.addTab(tabs.newTab().setCustomView(imageView));
            //tabs.addTab(tabs.newTab().setText(images[i]));
        }

        tabs.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager)findViewById(R.id.viewMatchDetailPage);
        final adaptor_match_tabs pa = new adaptor_match_tabs(getSupportFragmentManager(),tabs.getTabCount());
        viewPager.setAdapter(pa);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                tabID = tab.getPosition();
                invalidateOptionsMenu();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        invalidateOptionsMenu();


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        matchID = data.getLongExtra("matchID",-1);
        scrollIndexL = data.getIntExtra("scrollIndex",scrollIndexL);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_delete, menu);
        menu.findItem(R.id.deleteScreen).setVisible(false);
        menu.findItem(R.id.editScreen).setVisible(false);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        datasource.open();
    }

    @Override
    public void onBackPressed() {
        Intent returnI = new Intent();
        Bundle extras = new Bundle();
        extras.putLong("matchID",matchID);
        extras.putInt("scrollIndex",scrollIndexL);
        returnI.putExtras(extras);
        setResult(100,returnI);
        finish();
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //if(tabID == 1 || tabID == 3 || tabID == 5 || tabID == 6)
        if(tabID == 1 || tabID == 3 || tabID == 5 )
            menu.findItem(R.id.addScreen).setVisible(false );
        else
            menu.findItem(R.id.addScreen).setVisible(true );

        if(tabID == 0)
        {
            menu.findItem(R.id.deleteScreen).setVisible(true);
            menu.findItem(R.id.editScreen).setVisible(true);
            menu.findItem(R.id.emailScreen).setVisible(true);

            Stats stats = datasource.getAllStatsForMatch(matchID);
            if(stats.getHome_Score() == -1)
                menu.findItem(R.id.addScreen).setVisible(true);
            else
                menu.findItem(R.id.addScreen).setVisible(false);

            if(datasource.isMatchHappened(matchID)) {
                menu.findItem(R.id.addScreen).setVisible(false);
                menu.findItem(R.id.deleteScreen).setVisible(false);
                menu.findItem(R.id.editScreen).setVisible(false);
            }
            else
                menu.findItem(R.id.addScreen).setVisible(true);


        }else
        {
            menu.findItem(R.id.deleteScreen).setVisible(false);
            menu.findItem(R.id.editScreen).setVisible(false);
            menu.findItem(R.id.emailScreen).setVisible(false);
        }

        if(fromClubDetails == 1)
        {
            menu.findItem(R.id.addScreen).setVisible(false);
            menu.findItem(R.id.deleteScreen).setVisible(false);
            menu.findItem(R.id.editScreen).setVisible(false);
            menu.findItem(R.id.emailScreen).setVisible(false);
        }

        menu.findItem(R.id.editScreen).setVisible(false);
        menu.findItem(R.id.emailScreen).setVisible(false);
        menu.findItem(R.id.players).setVisible(false);
        menu.findItem(R.id.clubs).setVisible(false);




        return  true;
        //return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Bundle extras  = new Bundle();
        switch (id)
        {
            case R.id.emailScreen :
                Stats stats = datasource.getAllStatsForMatch(matchID);
                if (stats.getHome_Score() == -1)
                {
                    Toast.makeText(tzlc_match_details_tabs.this,"Error!! No Stats to send.",Toast.LENGTH_SHORT).show();
                }
                else {


                    goals = datasource.getAllGoalsForMatch(matchID);
                    cards = datasource.getAllCardsForMatch(matchID);
                    highlights = datasource.getAllHighlights(matchID);
                    loans = datasource.getAllLoansForMatch(matchID);
                    mos = datasource.getAllMOsForMatch(matchID);
                    substitutes = datasource.getAllSubstituteForMatch(matchID);

                    Match match = datasource.getMatch(matchID);
                    //String matchName = "/" + datasource.getClub(match.getHomeClubID()).getClubShortName() + "_" + datasource.getClub(match.getAwayClubID()).getClubShortName() + ".txt";
                    String matchName = "/" +"M"+match.getId()+"_"+ datasource.getClub(match.getHomeClubID()).getClubShortName() + "_" + datasource.getClub(match.getAwayClubID()).getClubShortName() + ".html";
                    //File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/TZLC.txt");
                    //File folderName = new File(getFilesDir(),"TZLCHTML");
                    //File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + matchName);
                    //File file = new File(folderName +"/" + matchName);
                    File file = new File(getFilesDir() + matchName);
                    //if(!file.exists())
                    //{



                    try {


                        if(!file.exists()) {
                            FileWriter fileWriter = new FileWriter(file);
                            int htHome = 0, htAway = 0;

                            for (Goal goal : goals) {
                                if (goal.getMatchTime() < 2400) {
                                    if (goal.getAgainstClubID() == match.getHomeClubID())
                                        htHome = htHome + 1;
                                    else
                                        htAway = htAway + 1;
                                }
                            }

                            StringBuilder statReport = new StringBuilder();
                            statReport.append("<!DOCTYPE html>\n" +
                                    "<html>\n" +
                                    "<head>\n" +
                                    "<style>\n" +
                                    "table {\n" +
                                    "    border: 2px solid black;\n" +
                                    "    border-collapse: collapse;\n" +
                                    "    font:font-family:verdana;\n" +
                                    "}\n" +
                                    "th, td, tr {\n" +
                                    "    padding: 5px;\n" +
                                    "    text-align: center;\n" +
                                    "}\n" +
                                    "th {\n" +
                                    "    text-align: center;\n" +
                                    "    background-color: #f1f1c1;\n" +
                                    "}\n" +
                                    "\n" +
                                    ".foo {\n" +
                                    "  float: left;\n" +
                                    "  width: 10px;\n" +
                                    "  height: 15px;  \n" +
                                    "}\n" +
                                    "\n" +
                                    ".blue {\n" +
                                    "  background: blue;\n" +
                                    "}\n" +
                                    "\n" +
                                    ".red {\n" +
                                    "  background: red;\n" +
                                    "}\n" +
                                    "\n" +
                                    ".yellow {\n" +
                                    "  background: yellow;\n" +
                                    "}\n" +
                                    "\n" +
                                    "</style>\n" +
                                    "</head>\n" +
                                    "<body>");

                            ArrayAdapter<CharSequence> adapterMatchType = ArrayAdapter.createFromResource(tzlc_match_details_tabs.this, R.array.matchType, android.R.layout.simple_spinner_item);
                            ArrayAdapter<CharSequence> adapterMatchSubType = ArrayAdapter.createFromResource(tzlc_match_details_tabs.this, R.array.matchSubTypeShort, android.R.layout.simple_spinner_item);


                            statReport.append("<center><h1> <span style='color: #000075;' >" + datasource.getClub(match.getHomeClubID()).getClubName() + "</span> vs <span style='color: #000075;'>" + datasource.getClub(match.getAwayClubID()).getClubName() + "</span></h1></center>");
                            if (adapterMatchSubType.getItem(match.getSubtype()).equals("NA"))
                                statReport.append("<center style='color: #000075;'><h2>" + adapterMatchType.getItem(match.getType()) + "</center> </h2>");
                            else
                                statReport.append("<center style='color: #000075;'><h2>" + adapterMatchType.getItem(match.getType()) + "(" + adapterMatchSubType.getItem(match.getSubtype()) + ")" + "</center> </h2>");


                            statReport.append("<table style='width:100%'>");
                            statReport.append("<tr> <th width=40%><span style=\'color: #000075;\'>" + datasource.getClub(match.getHomeClubID()).getClubShortName() + "</span></th> <th></th> <th width=40%><span style=\'color: #000075;\'>" + datasource.getClub(match.getAwayClubID()).getClubShortName() + "</span></th></tr>");
                            statReport.append("<tr> <td>" + "(" + htHome + ") " + stats.getHome_Score() + "</td><td>" + "Score" + "</td> <td>" + "(" + htAway + ") " + stats.getAway_Score() + "</td></tr>");
                            statReport.append("<tr> <td>" + String.format("%.2f", (((double) stats.getHome_TIME() / (stats.getHome_TIME() + stats.getAway_TIME())) * 100)) + "%" + "</td><td>" + "Possession" + "</td><td>" + String.format("%.2f", (((double) stats.getAway_TIME() / (stats.getHome_TIME() + stats.getAway_TIME())) * 100)) + "%" + "</td></tr>");
                            statReport.append("<tr> <td>" + "" + "</td><td>" + "" + "</td><td>" + "" + "</td></tr>");
                            statReport.append("<tr> <td>" + "" + "</td><td>" + "" + "</td><td>" + "" + "</td></tr>");
                            statReport.append("<tr> <td>" + "" + "</td><td>" + "" + "</td><td>" + "" + "</td></tr>");
                            statReport.append("<tr> <td>" + "" + "</td><td><h3>" + "Match Stats" + "</h3></td><td>" + "" + "</td></tr>");
                            statReport.append("<tr> <td>" + stats.getHome_DFK() + "</td><td>" + "Free Kick" + "</td><td>" + stats.getAway_DFK() + "</td></tr>");
                            statReport.append("<tr> <td>" + stats.getHome_COR() + "</td><td>" + "Corners" + "</td><td>" + stats.getAway_COR() + "</td></tr>");
                            statReport.append("<tr> <td>" + stats.getHome_GK() + "</td><td>" + "Goal Kick" + "</td><td>" + stats.getAway_GK() + "</td></tr>");
                            statReport.append("<tr> <td>" + stats.getHome_SOnT() + "</td><td>" + "Shot On Target" + "</td><td>" + stats.getAway_SOnT() + "</td></tr>");
                            statReport.append("<tr> <td>" + stats.getHome_SOffT() + "</td><td>" + "Shot Off Target" + "</td><td>" + stats.getAway_SOffT() + "</td></tr>");
                            statReport.append("<tr> <td>" + stats.getHome_LC() + "</td><td>" + "Late Challenge" + "</td><td>" + stats.getAway_LC() + "</td></tr>");
                            statReport.append("<tr> <td>" + stats.getHome_TCK() + "</td><td>" + "Tackles" + "</td><td>" + stats.getAway_TCK() + "</td></tr>");
                            statReport.append("<tr> <td>" + stats.getHome_TI() + "</td><td>" + "Throw-In" + "</td><td>" + stats.getAway_TI() + "</td></tr>");
                            statReport.append("<tr> <td>" + stats.getHome_OFF() + "</td><td>" + "Offside" + "</td><td>" + stats.getAway_OFF() + "</td></tr>");
                            statReport.append("<tr> <td>" + stats.getHome_POP() + "</td><td>" + "POP" + "</td><td>" + stats.getAway_POP() + "</td></tr>");
                            statReport.append("</table>");

                            statReport.append("<table style='width:100%'>");
                            statReport.append("<caption style='color: #000075'><h2> Goals</h2></caption>");
                            statReport.append("<tr> <th width=40%><span style=\'color: #000075;\'>" + datasource.getClub(match.getHomeClubID()).getClubShortName() + "</span></th> <th></th> <th width=40%><span style=\'color: #000075;\'>" + datasource.getClub(match.getAwayClubID()).getClubShortName() + "</span></th></tr>");
                            List<Goal> homeGoals = new ArrayList<Goal>(), awayGoals = new ArrayList<Goal>();
                            for (Goal goal : goals) {
                                if (goal.getAgainstClubID() == match.getHomeClubID())
                                    homeGoals.add(goal);
                                else
                                    awayGoals.add(goal);
                            }
                            int maxRows = homeGoals.size() > awayGoals.size() ? homeGoals.size() : awayGoals.size();
                            String homeInfo, awayInfo;
                            for (int i = 0; i < maxRows; i++) {
                                if (i < homeGoals.size())
                                    homeInfo = "" + datasource.getPlayer(homeGoals.get(i).getPlayerID()).getPlayerName().split("@")[0].substring(0, 2) + ". " + datasource.getPlayer(homeGoals.get(i).getPlayerID()).getPlayerName().split("@")[1] + ((homeGoals.get(i).getOwnGoal() == 1) ? "(OG)" : "") + "  " + homeGoals.get(i).getMatchTime() / 60 + "'";
                                else
                                    homeInfo = "";

                                if (i < awayGoals.size())
                                    awayInfo = "" + datasource.getPlayer(awayGoals.get(i).getPlayerID()).getPlayerName().split("@")[0].substring(0, 2) + ". " + datasource.getPlayer(awayGoals.get(i).getPlayerID()).getPlayerName().split("@")[1] + ((awayGoals.get(i).getOwnGoal() == 1) ? "(OG)" : "") + "  " + awayGoals.get(i).getMatchTime() / 60 + "'";
                                else
                                    awayInfo = "";

                                statReport.append("<tr> <td>" + homeInfo + "</td><td>" + "" + "</td><td>" + awayInfo + "</td></tr>");
                            }
                            statReport.append("</table>");


                            statReport.append("<table style='width:100%'>");
                            statReport.append("<caption style='color: #000075'><h2> Cards</h2></caption>");
                            statReport.append("<tr> <th width=40%><span style=\'color: #000075;\'>" + datasource.getClub(match.getHomeClubID()).getClubShortName() + "</span></th> <th></th> <th width=40%><span style=\'color: #000075;\'>" + datasource.getClub(match.getAwayClubID()).getClubShortName() + "</span></th></tr>");
                            List<Card> homeCards = new ArrayList<Card>(), awayCards = new ArrayList<Card>();
                            for (Card card : cards) {
                                if (card.getClubID() == match.getHomeClubID())
                                    homeCards.add(card);
                                else
                                    awayCards.add(card);
                            }
                            maxRows = homeCards.size() > awayCards.size() ? homeCards.size() : awayCards.size();
                            String[] cardColor = {"yellow", "red", "blue"};
                            for (int i = 0; i < maxRows; i++) {
                                if (i < homeCards.size())
                                    homeInfo = "" + "<td width=40%><div class='foo " + cardColor[homeCards.get(i).getType()] + "'></div><span>" + datasource.getPlayer(homeCards.get(i).getPlayerID()).getPlayerName().split("@")[0].substring(0, 2) + ". " + datasource.getPlayer(homeCards.get(i).getPlayerID()).getPlayerName().split("@")[1] + "</span></td>";
                                else
                                    homeInfo = "<td> </td>";

                                if (i < awayCards.size())
                                    awayInfo = "" + "<td width=40%><div class='foo " + cardColor[awayCards.get(i).getType()] + "' style='float:right' ></div><span>" + datasource.getPlayer(awayCards.get(i).getPlayerID()).getPlayerName().split("@")[0].substring(0, 2) + ". " + datasource.getPlayer(awayCards.get(i).getPlayerID()).getPlayerName().split("@")[1] + "</span></td>";
                                else
                                    awayInfo = "<td> </td>";
                                statReport.append("<tr> " + homeInfo + "<td>" + "" + "</td> " + awayInfo + "</tr>");
                            }
                            statReport.append("</table>");


                            statReport.append("<table style='width:100%'>");
                            statReport.append("<caption style='color: #000075'><h2> Loans</h2></caption>");
                            statReport.append("<tr> <th width=40%><span style=\'color: #000075;\'>" + datasource.getClub(match.getHomeClubID()).getClubShortName() + "</span></th> <th></th> <th width=40%><span style=\'color: #000075;\'>" + datasource.getClub(match.getAwayClubID()).getClubShortName() + "</span></th></tr>");
                            List<Loan> homeLoans = new ArrayList<Loan>(), awayLoans = new ArrayList<Loan>();

                            for (Loan loan : loans) {
                                if (loan.getHomeClubID() == match.getHomeClubID())
                                    homeLoans.add(loan);
                                else
                                    awayLoans.add(loan);
                            }
                            maxRows = homeLoans.size() > awayLoans.size() ? homeLoans.size() : awayLoans.size();
                            String[] loanType = {"AVGKL", "AVPL ", "DL   ", "GKL  "};
                            for (int i = 0; i < maxRows; i++) {
                                if (i < homeLoans.size())
                                    homeInfo = "[" + loanType[homeLoans.get(i).getRule()] + "]" + "(" + datasource.getClub(datasource.getPlayer(homeLoans.get(i).getLoanPlayerID()).getClubId()).getClubShortName() + ")" + datasource.getPlayer(homeLoans.get(i).getLoanPlayerID()).getPlayerName().split("@")[0].substring(0, 2) + ". " + datasource.getPlayer(homeLoans.get(i).getLoanPlayerID()).getPlayerName().split("@")[1];
                                else
                                    homeInfo = "";

                                if (i < awayLoans.size())
                                    awayInfo = "[" + loanType[homeLoans.get(i).getRule()] + "]" + "(" + datasource.getClub(datasource.getPlayer(awayLoans.get(i).getLoanPlayerID()).getClubId()).getClubShortName() + ")" + datasource.getPlayer(awayLoans.get(i).getLoanPlayerID()).getPlayerName().split("@")[0].substring(0, 2) + ". " + datasource.getPlayer(awayLoans.get(i).getLoanPlayerID()).getPlayerName().split("@")[1];
                                else
                                    awayInfo = "";

                                statReport.append("<tr> <td>" + homeInfo + "</td><td>" + "" + "</td><td>" + awayInfo + "</td></tr>");
                            }
                            statReport.append("</table>");


                            statReport.append("<table style='width:100%'>");
                            statReport.append("<caption style='color: #000075'><h2> Substitute</h2></caption>");
                            statReport.append("<tr> <th width=40% colspan='3'><span style=\'color: #000075;\'>" + datasource.getClub(match.getHomeClubID()).getClubShortName() + "</span></th> <th></th> <th width=40% colspan='3'><span style=\'color: #000075;\'>" + datasource.getClub(match.getAwayClubID()).getClubShortName() + "</span></th></tr>");
                            List<Substitute> homeSubstitute = new ArrayList<Substitute>(), awaySubstitute = new ArrayList<Substitute>();

                            for (Substitute substitute : substitutes) {
                                if (substitute.getClubID() == match.getHomeClubID())
                                    homeSubstitute.add(substitute);
                                else
                                    awaySubstitute.add(substitute);
                            }
                            maxRows = homeSubstitute.size() > awaySubstitute.size() ? homeSubstitute.size() : awaySubstitute.size();
                            for (int i = 0; i < maxRows; i++) {


                                if (i < homeSubstitute.size())
                                    homeInfo = "" + "<td>" + (homeSubstitute.get(i).getMatchTime()) / 60 + "'" + "</td> <td style='color: red;'>" + datasource.getPlayer(homeSubstitute.get(i).getPlayerOutID()).getPlayerName().split("@")[0].substring(0, 2) + ". " + datasource.getPlayer(homeSubstitute.get(i).getPlayerOutID()).getPlayerName().split("@")[1] + "</td> <td style='color: green;'>" + datasource.getPlayer(homeSubstitute.get(i).getPlayerInID()).getPlayerName().split("@")[0].substring(0, 2) + ". " + datasource.getPlayer(homeSubstitute.get(i).getPlayerInID()).getPlayerName().split("@")[1] + "</td>";
                                else
                                    homeInfo = "" + "<td>" + "" + "</td> <td style='color: red;'>" + "" + "</td> <td style='color: green;'>" + "" + "</td>";

                                if (i < awaySubstitute.size())
                                    awayInfo = "" + "<td style='color: red;'>" + datasource.getPlayer(awaySubstitute.get(i).getPlayerOutID()).getPlayerName().split("@")[0].substring(0, 2) + ". " + datasource.getPlayer(awaySubstitute.get(i).getPlayerOutID()).getPlayerName().split("@")[1] + "</td> <td style='color: green;'>" + datasource.getPlayer(awaySubstitute.get(i).getPlayerInID()).getPlayerName().split("@")[0].substring(0, 2) + ". " + datasource.getPlayer(awaySubstitute.get(i).getPlayerInID()).getPlayerName().split("@")[1] + "</td> <td>" + (awaySubstitute.get(i).getMatchTime()) / 60 + "'" + "</td>";
                                else
                                    awayInfo = "" + "<td style='color: red;'>" + "" + "</td> <td style='color: green;'>" + "" + "</td> <td>" + "" + "</td>";

                                statReport.append("<tr>" + homeInfo + "<td>" + "" + "</td>" + awayInfo + "</tr>");
                            }
                            statReport.append("</table>");


                            statReport.append("<table style='width:100%'>");
                            statReport.append("<caption style='color: #000075'><h2> Match Offcials</h2></caption>");
                            statReport.append("<tr> <th width=40%><span style=\'color: #000075;\'>" + "Player Name" + "</span></th> <th>Job</th> <th width=40%><span style=\'color: #000075;\'>" + "On Time ?" + "</span></th></tr>");
                            ArrayAdapter<CharSequence> moJobs = ArrayAdapter.createFromResource(tzlc_match_details_tabs.this, R.array.MOJobProfile, android.R.layout.simple_spinner_item);

                            for (MatchOffcial matchOffcial : mos) {
                                statReport.append("<tr> <td>(" + datasource.getClub(datasource.getPlayer(matchOffcial.getPlayerId()).getClubId()).getClubShortName() + ") " + datasource.getPlayer(matchOffcial.getPlayerId()).getPlayerName().split("@")[0].substring(0, 2) + ". " + datasource.getPlayer(matchOffcial.getPlayerId()).getPlayerName().split("@")[1] + "</td><td>" + moJobs.getItem(matchOffcial.getJob()) + "</td><td>" + (matchOffcial.getOnTime() == 0 ? "No" : "Yes") + "</td></tr>");
                            }

                            statReport.append("</table>");
                            statReport.append("</body></html>");
                            fileWriter.append(statReport);

                            fileWriter.flush();
                            fileWriter.close();
                        }


                        Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), matchName));
                        //Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                        Intent emailIntent = new Intent(Intent.ACTION_SEND, Uri.fromParts("mailto", "prashant.alhat@gmail.com", null));
                        emailIntent.setType("text/plain");
                        //emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] {"prashant.alhat@gmail.com"});
                        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "TZLC Match Report");
                        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Respected KFANDRAAI, \n\nPlease see attached match report for match " +
                                datasource.getClub(match.getHomeClubID()).getClubShortName() + " vs " + datasource.getClub(match.getAwayClubID()).getClubShortName() + "\n\nRegards,");

                        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
                        startActivity(Intent.createChooser(emailIntent, "Send mail..."));


                    } catch (Exception e) {
                        Log.d(tzlc_stats_display.class.getSimpleName(), "" + e);
                        Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case R.id.addScreen :

                switch (tabID)
                {
                    case 0 :

                        DialogInterface.OnClickListener dialog = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which)
                                {
                                    case DialogInterface.BUTTON_POSITIVE :
                                        //Intent statsAdd = new Intent(tzlc_match_details_tabs.this, tzlc_stats_add.class);
                                        Intent statsAdd = new Intent(tzlc_match_details_tabs.this, tzlc_stats_add_tzlc12n.class);
                                        Bundle extras  = new Bundle();
                                        extras.putLong("matchID", matchID);
                                        extras.putInt("scrollIndex",scrollIndexL);
                                        statsAdd.putExtras(extras);
                                        if(datasource.getMatch(matchID).getHomeClubID() == 0 || datasource.getMatch(matchID).getAwayClubID()<0)
                                            Toast.makeText(tzlc_match_details_tabs.this,"Please add both clubs",Toast.LENGTH_SHORT).show();
                                        else
                                        {
                                            if(!datasource.isStatsAvailableForMatch(matchID))
                                            {
                                                Stats stat = new Stats();
                                                stat.setMatchID(matchID);
                                                stat.setMatchTime(0);
                                                stat.setHome_TIME(0);
                                                stat.setAway_TIME(0);
                                                stat.setHome_Score(0);
                                                stat.setAway_Score(0);
                                                datasource.addStats(stat);
                                            }

                                            startActivityForResult(statsAdd,100);
                                        }
                                    case DialogInterface.BUTTON_NEGATIVE : break;
                                }
                            }
                        };
                        AlertDialog.Builder builder = new AlertDialog.Builder(tzlc_match_details_tabs.this);
                        builder.setMessage("Do you want to start recording Stats for match ???").setNegativeButton("No",dialog).setPositiveButton("Yes",dialog).show();

                        break;

                    case 1:
                        Intent goalAdd = new Intent(tzlc_match_details_tabs.this, tzlc_goal_add.class);
                        extras  = new Bundle();
                        extras.putLong("matchID", matchID);
                        extras.putInt("matchTime",-1);
                        extras.putInt("scrollIndex",scrollIndexL);
                        goalAdd.putExtras(extras);
                        startActivityForResult(goalAdd,100);
                        break;
                    case 2: //loans
                        Intent loanAdd = new Intent(tzlc_match_details_tabs.this, tzlc_loan_add.class);
                        loanAdd.putExtra("matchID", matchID);
                        extras.putInt("scrollIndex",scrollIndexL);
                        startActivity(loanAdd);
                        break;

                    case 3: // cards
                        Intent cardAdd = new Intent(tzlc_match_details_tabs.this, tzlc_card_add.class);
                        extras  = new Bundle();
                        extras.putLong("matchID", matchID);
                        extras.putInt("scrollIndex",scrollIndexL);
                        extras.putInt("matchTime",-1);
                        cardAdd.putExtras(extras);
                        startActivityForResult(cardAdd,100);
                        break;
                    case 4: //mo


                            Intent moAdd = new Intent(tzlc_match_details_tabs.this, tzlc_mo_add.class);
                            //moAdd.putExtra("matchID", matchID);
                            extras  = new Bundle();
                            extras.putLong("matchID", matchID);
                            extras.putInt("scrollIndex",scrollIndexL);
                            moAdd.putExtras(extras);
                            startActivity(moAdd);

                        break;
                    case 5: //highlight
                        Intent highlightAdd = new Intent(tzlc_match_details_tabs.this, tzlc_highlight_add.class);
                        extras  = new Bundle();
                        extras.putLong("matchID", matchID);
                        extras.putInt("scrollIndex",scrollIndexL);
                        extras.putInt("matchTime",-1);
                        highlightAdd.putExtras(extras);
                        startActivityForResult(highlightAdd,100);
                        break;

                    case 6: //squads
                        Intent squadAdd = new Intent(tzlc_match_details_tabs.this, tzlc_squad_add.class);
                        //Intent squadAdd = new Intent(tzlc_match_details_tabs.this, tzlc_formation_add.class);
                        squadAdd.putExtra("matchID", matchID);
                        RadioButton hRDB1 = findViewById(R.id.rdbsquadHome);
                        RadioButton aRDB1 = findViewById(R.id.rdbsquadAway);
                        if(hRDB1.isChecked())
                            squadAdd.putExtra("clubID", datasource.getMatch(matchID).getHomeClubID());
                        if(aRDB1.isChecked())
                            squadAdd.putExtra("clubID", datasource.getMatch(matchID).getAwayClubID());
                        extras.putInt("scrollIndex",scrollIndexL);
                        startActivityForResult(squadAdd,100);
                        break;
                    case 7: //Formation
                        Intent formationAdd = new Intent(tzlc_match_details_tabs.this, tzlc_formation_add.class);
                        RadioButton hRDB = findViewById(R.id.rdbFormationDisplayHomeClub);
                        RadioButton aRDB = findViewById(R.id.rdbFormationDisplayAwayClub);
                        if(hRDB.isChecked())
                            formationAdd.putExtra("clubID", datasource.getMatch(matchID).getHomeClubID());
                        if(aRDB.isChecked())
                            formationAdd.putExtra("clubID", datasource.getMatch(matchID).getAwayClubID());
                        formationAdd.putExtra("matchID", matchID);
                        extras.putInt("scrollIndex",scrollIndexL);
                        startActivityForResult(formationAdd,100);
                        break;
                }
                break;


            case R.id.editScreen :         Intent matchEdit = new Intent(tzlc_match_details_tabs.this, tzlc_match_add.class);
                //i.putExtra("matchID", matchID);
                extras  = new Bundle();
                extras.putLong("matchID", matchID);
                extras.putInt("scrollIndex",scrollIndexL);
                matchEdit.putExtras(extras);
                startActivityForResult(matchEdit,100); /*finish();*/ break;

            case R.id.deleteScreen :
                DialogInterface.OnClickListener dialog = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which)
                        {
                            case DialogInterface.BUTTON_POSITIVE : datasource.deleteMatch(matchID);finish(); break;
                            case DialogInterface.BUTTON_NEGATIVE : break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(tzlc_match_details_tabs.this);
                builder.setMessage("Are you sure, you want to delete Match ?").setPositiveButton("Yes",dialog).setNegativeButton("No",dialog).show();
        }
        return super.onOptionsItemSelected(item);
    }

}
