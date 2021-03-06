package com.kfandra.tzlc.tzlc;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class tzlc_match_details_stats extends Fragment {

    Stats stat;
    private tzlcDataSource datasource;


    public tzlc_match_details_stats() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tzlc_match_details_stats, container, false);

        tzlc_match_details_tabs match_details = (tzlc_match_details_tabs) getActivity();
        datasource= new tzlcDataSource(getContext());
        datasource.open();

        stat = datasource.getAllStatsForMatch(match_details.getMatchID());

        TextView noStats = rootView.findViewById(R.id.txtNoStats);
        LinearLayout h1 = rootView.findViewById(R.id.statH1);
        LinearLayout h2 = rootView.findViewById(R.id.layoutStats);
        LinearLayout h3 = rootView.findViewById(R.id.layoutGoals);
        LinearLayout lp = rootView.findViewById(R.id.layoutPossessionStats);
        LinearLayout layoutFormation = rootView.findViewById(R.id.layoutFormation);
        if (stat.getHome_Score() == -1)
        {
            noStats.setVisibility(View.VISIBLE);
            h1.setVisibility(View.GONE);
            h2.setVisibility(View.GONE);
            h3.setVisibility(View.GONE);
            lp.setVisibility(View.GONE);
            layoutFormation.setVisibility(View.GONE);
        }

        Match match = datasource.getMatch(match_details.getMatchID());
        List <Goal> goals = datasource.getAllGoalsForMatch(match_details.getMatchID());
        List <Highlight> highlights = datasource.getAllHighlights(match_details.getMatchID());

        Formation homeFormation = datasource.getFormation(match.getId(),match.getHomeClubID());
        showHomeFormation(homeFormation,rootView);
        List<Squad> homeSquad = datasource.getAvailableSquadForMatchandClub(match.getId(),match.getHomeClubID());
        showHomeSubs(rootView,homeSquad);

        Formation awayFormation = datasource.getFormation(match.getId(),match.getAwayClubID());
        showAwayFormation(awayFormation,rootView);
        List<Squad> awaySquad = datasource.getAvailableSquadForMatchandClub(match.getId(),match.getAwayClubID());
        showAwaySubs(rootView,awaySquad);

        int htHome=0, htAway=0;
        for (Highlight highlight : highlights) {
            if(highlight.getHighlight().equals("GOAL"))
            {
                if (highlight.getClubID() == match.getHomeClubID())
                    htHome = htHome + 1;
                else
                    htAway = htAway + 1;
            }
            if(highlight.getHighlight().equals("Half Time"))
                break;
        }

        String homeGoals="", awayGoals="", goalString="";
        int homeTotalGoals=0,awayTotalGoals=0;

        for (Goal goal : goals) {
            if(goal.getOwnGoal() == 1)
                goalString = " "
                    + datasource.getPlayer(goal.getPlayerID()).getPlayerName().split("@")[0].substring(0,2)+". "+datasource.getPlayer(goal.getPlayerID()).getPlayerName().split("@")[1]
                    + " (" + String.format("%02d", goal.getMatchTime()/60) + "')" + " (OG) , ";
            else
                goalString = " "
                        + datasource.getPlayer(goal.getPlayerID()).getPlayerName().split("@")[0].substring(0,2)+". "+datasource.getPlayer(goal.getPlayerID()).getPlayerName().split("@")[1]
                        + " (" + String.format("%02d", goal.getMatchTime()/60) + "')" + " , ";
            if(goal.getAgainstClubID() == match.getHomeClubID())
            {homeGoals = homeGoals + goalString;homeTotalGoals=homeTotalGoals+1;}
            else
            {awayGoals = awayGoals + goalString;awayTotalGoals=awayTotalGoals+1;}

        }
        if(homeGoals.length() > 0)
            homeGoals = homeGoals.substring(0, homeGoals.length() - 2);
        if(awayGoals.length() > 0)
            awayGoals = awayGoals.substring(0, awayGoals.length() - 2);



        int[] homeCards = new int[] {0,0,0}, awayCards = new int[] {0,0,0};
        List <Card> cards = datasource.getAllCardsForMatch(match_details.getMatchID());
        List <String> homePlayers = datasource.getAllPlayerNamesForClub(match.getHomeClubID(),match.getId());
        //List <String> awayPlayers = datasource.getAllPlayerNamesForClub(match.getAwayClubID(),match.getId());
        boolean flag;

        for (Card card : cards) {
            String playerName = datasource.getPlayer(card.getPlayerID()).getPlayerName().split("@")[0].substring(0,2)+". "+datasource.getPlayer(card.getPlayerID()).getPlayerName().split("@")[1];
            flag = false;
            for (String homePlayer : homePlayers) {
                if(homePlayer.equals(playerName))
                {flag = true;break;}
            }
            if (flag)
            {
                switch (card.getType()){
                    case 0 : homeCards[0] = homeCards[0] +1;break;
                    case 1 : homeCards[1] = homeCards[1] +1;break;
                    case 2 : homeCards[2] = homeCards[2] +1;break;
                }
            }
            else
            {
                switch (card.getType()){
                    case 0 : awayCards[0] = awayCards[0] +1;break;
                    case 1 : awayCards[1] = awayCards[1] +1;break;
                    case 2 : awayCards[2] = awayCards[2] +1;break;
                }
            }
        }



        TextView homeClub = rootView.findViewById(R.id.matchDetailsHomeClub);
        homeClub.setText("" + datasource.getClub(match.getHomeClubID()).getClubName()+"     ("+htHome+") "+stat.getHome_Score() + "    ,P [" + stat.getHome_TIME() +"]");
        homeClub.setTextSize(12);
        homeClub.setTextColor(datasource.getClub(match.getHomeClubID()).getClubColor());
        TextView awayClub = rootView.findViewById(R.id.matchDetailsAwayClub);
        awayClub.setText("" + datasource.getClub(match.getAwayClubID()).getClubName()+"     ("+htAway+") "+stat.getAway_Score()+ "    ,P [" + stat.getAway_TIME() +"]");
        awayClub.setTextSize(12);
        awayClub.setTextColor(datasource.getClub(match.getAwayClubID()).getClubColor());
        //TextView matchtype = rootView.findViewById(R.id.matchDetailsMatchType);
        //ArrayAdapter<CharSequence> adapterMatchSubType = ArrayAdapter.createFromResource(getContext(), R.array.matchTypeShort, android.R.layout.simple_spinner_item);
        //matchtype.setText("" + adapterMatchSubType.getItem(match.getType()));

        //TextView homeClubScore = rootView.findViewById(R.id.matchDetailsHomeScore);
        //homeClubScore.setText("("+htHome+") "+stat.getHome_Score());
        //homeClubScore.setText(""+homeTotalGoals);
        //TextView homeClubPossession = rootView.findViewById(R.id.matchDetailsHomePossession);
        //homeClubPossession.setText(""+String.format("%.2f", (((double)stat.getHome_TIME()/(stat.getHome_TIME()+stat.getAway_TIME()))*100))+"%");
        //homeClubPossession.setText(""+stat.getHome_TIME());
        //homeClubPossession.setText(""+stat.getHome_TIME()%1000 + " [ " + stat.getHome_TIME()/1000 + " ]");
        //homeClubPossession.setText(""+stat.getHome_SOnT()/1000 + " , " + stat.getHome_SOnT()%1000 + " ,"+stat.getHome_SOffT()/1000 + " , " + stat.getHome_SOffT()%1000);
        TextView homeGoalDetails = rootView.findViewById(R.id.matchDetailsHomeGoals);
        homeGoalDetails.setText(homeGoals);
        TextView homeClubCards = rootView.findViewById(R.id.matchDetailsHomeCARDS);
        String hC = "" + homeCards[0] + "<font color='#FFE333'> (Y),  </font>"+ homeCards[1] + "<font color='#FF3333'> (R),  </font>"+ homeCards[2] + "<font color='#3355FF'> (B)</font>";
        homeClubCards.setText(Html.fromHtml(hC));
        TextView homeClubDFK = rootView.findViewById(R.id.matchDetailsHomeDFK);
        homeClubDFK.setText(""+stat.getHome_DFK());
        TextView homeClubCOR = rootView.findViewById(R.id.matchDetailsHomeCOR);
        homeClubCOR.setText(""+stat.getHome_COR());
        TextView homeClubGK = rootView.findViewById(R.id.matchDetailsHomeGK);
        homeClubGK.setText(""+stat.getHome_GK());
        TextView homeClubSOnT = rootView.findViewById(R.id.matchDetailsHomeSOnT);
        homeClubSOnT.setText(""+(stat.getHome_SOnT()+stat.getHome_SOffT())+ " [ "+stat.getHome_SOnT()+" ]");
        TextView homeClubSOffT = rootView.findViewById(R.id.matchDetailsHomeSOffT);
        homeClubSOffT.setText(""+stat.getHome_SOffT());
        homeClubSOffT.setVisibility(View.GONE);
        TextView homeClubLC = rootView.findViewById(R.id.matchDetailsHomeLC);
        homeClubLC.setText(""+stat.getHome_LC());
        TextView homeClubTCK = rootView.findViewById(R.id.matchDetailsHomeTCK);
        homeClubTCK.setText(""+stat.getHome_TCK());
        TextView homeClubTI = rootView.findViewById(R.id.matchDetailsHomeTI);
        homeClubTI.setText(""+stat.getHome_TI());
        TextView homeClubOFF = rootView.findViewById(R.id.matchDetailsHomeOFF);
        homeClubOFF.setText(""+stat.getHome_OFF());
        TextView homeClubPOP = rootView.findViewById(R.id.matchDetailsHomePOP);
        homeClubPOP.setText(""+(stat.getHome_POP()+stat.getHome_TCK())+" [ "+stat.getHome_POP()+ " ]");


        //TextView awayClubScore = rootView.findViewById(R.id.matchDetailsAwayScore);
        //awayClubScore.setText("("+htAway+") "+stat.getAway_Score());
        //awayClubScore.setText(""+awayTotalGoals);
        //TextView awayClubPossession = rootView.findViewById(R.id.matchDetailsAwayPossession);
        //awayClubPossession.setText(""+String.format("%.2f", (((double)stat.getAway_TIME()/(stat.getHome_TIME()+stat.getAway_TIME()))*100))+"%");
        //awayClubPossession.setText(""+stat.getAway_TIME());
        //awayClubPossession.setText(""+stat.getAway_TIME()%1000 + " [ " + stat.getAway_TIME()/1000 + " ]");
        //awayClubPossession.setText(""+stat.getAway_SOnT()/1000 + " , " + stat.getAway_SOnT()%1000 + " ,"+stat.getAway_SOffT()/1000 + " , " + stat.getAway_SOffT()%1000);
        TextView awayGoalDetails = rootView.findViewById(R.id.matchDetailsAwayGoals);
        awayGoalDetails.setText(awayGoals);
        TextView awayClubCards = rootView.findViewById(R.id.matchDetailsAwayCards);
        String aC = "" + awayCards[0] + "<font color='#FFE333'> (Y),  </font>"+ awayCards[1] + "<font color='#FF3333'> (R),  </font>"+ awayCards[2] + "<font color='#3355FF'> (B)</font>";
        awayClubCards.setText(Html.fromHtml(aC));
        TextView awayClubDFK = rootView.findViewById(R.id.matchDetailsAwayDFK);
        awayClubDFK.setText(""+stat.getAway_DFK());
        TextView awayClubCOR = rootView.findViewById(R.id.matchDetailsAwayCOR);
        awayClubCOR.setText(""+stat.getAway_COR());
        TextView awayClubGK = rootView.findViewById(R.id.matchDetailsAwayGK);
        awayClubGK.setText(""+stat.getAway_GK());
        TextView awayClubSOnT = rootView.findViewById(R.id.matchDetailsAwaySOnT);
        awayClubSOnT.setText(""+(stat.getAway_SOnT()+stat.getAway_SOffT()) + " [ "+stat.getAway_SOnT()+" ]");
        TextView awayClubSOffT = rootView.findViewById(R.id.matchDetailsAwaySOffT);
        awayClubSOffT.setText(""+stat.getAway_SOffT());
        awayClubSOffT.setVisibility(View.GONE);
        TextView awayClubLC = rootView.findViewById(R.id.matchDetailsAwayLC);
        awayClubLC.setText(""+stat.getAway_LC());
        TextView awayClubTCK = rootView.findViewById(R.id.matchDetailsAwayTCK);
        awayClubTCK.setText(""+stat.getAway_TCK());
        TextView awayClubTI = rootView.findViewById(R.id.matchDetailsAwayTI);
        awayClubTI.setText(""+stat.getAway_TI());
        TextView awayClubOFF = rootView.findViewById(R.id.matchDetailsAwayOFF);
        awayClubOFF.setText(""+stat.getAway_OFF());
        TextView awayClubPOP = rootView.findViewById(R.id.matchDetailsAwayPOP);
        awayClubPOP.setText(""+stat.getAway_POP());
        awayClubPOP.setText(""+(stat.getAway_POP()+stat.getAway_TCK())+" [ "+stat.getAway_POP()+ " ]");


        int homeColor,awayColor;
        homeColor = datasource.getClub(match.getHomeClubID()).getClubColor();
        awayColor = datasource.getClub(match.getAwayClubID()).getClubColor();
        TextView hp1 = rootView.findViewById(R.id.txtvH1);
        hp1.setTextColor(homeColor);
        hp1.setText(""+ (stat.getHome_H1())/60+":"+(stat.getHome_H1())%60);
        TextView ap1 = rootView.findViewById(R.id.txtvA1);
        ap1.setTextColor(awayColor);
        ap1.setText(""+(stat.getAway_A4())/60+":"+(stat.getAway_A4())%60);


        TextView hp2 = rootView.findViewById(R.id.txtvH2);
        hp2.setTextColor(homeColor);
        hp2.setText(""+(stat.getHome_H2())/60+":"+(stat.getHome_H2())%60);
        TextView ap2 = rootView.findViewById(R.id.txtvA2);
        ap2.setTextColor(awayColor);
        ap2.setText(""+(stat.getAway_A3())/60+":"+(stat.getAway_A3())%60);

        TextView hp3 = rootView.findViewById(R.id.txtvH3);
        hp3.setTextColor(homeColor);
        hp3.setText(""+(stat.getHome_H3())/60+":"+(stat.getHome_H3())%60);
        TextView ap3 = rootView.findViewById(R.id.txtvA3);
        ap3.setTextColor(awayColor);
        ap3.setText(""+(stat.getAway_A2())/60+":"+(stat.getAway_A2())%60);

        TextView hp4 = rootView.findViewById(R.id.txtvH4);
        hp4.setTextColor(homeColor);
        hp4.setText(""+(stat.getHome_H4())/60+":"+(stat.getHome_H4())%60);
        TextView ap4 = rootView.findViewById(R.id.txtvA4);
        ap4.setTextColor(awayColor);
        ap4.setText(""+(stat.getAway_A1())/60+":"+(stat.getAway_A1())%60);

        return rootView;
    }

    private void showHomeFormation(Formation homeFormation,View rootView) {
        switch (homeFormation.getFormations()){
            case 0:
                showHomeDefLine(4,rootView,homeFormation);
                showHomeMidLine(4,rootView,homeFormation);
                showHomeStrLine(2,rootView,homeFormation);
                break;
            case 1:
                showHomeDefLine(3,rootView,homeFormation);
                showHomeMidLine(5,rootView,homeFormation);
                showHomeStrLine(2,rootView,homeFormation);
                break;
            case 2:
                showHomeDefLine(4,rootView,homeFormation);
                showHomeMidLine(3,rootView,homeFormation);
                showHomeStrLine(3,rootView,homeFormation);
                break;
        }


    }

    private void showHomeSubs(View rootView, List<Squad> homeSquad) {
        TextView subs = rootView.findViewById(R.id.txtHomeSubs);
        subs.setText("Subs : ");
        for (Squad squad : homeSquad) {
            if(squad.getPosition()==0)
                subs.setText(subs.getText() + datasource.getPlayer(squad.getPlayerID()).getPlayerName().split("@")[1] + " , ");
        }
        subs.setText(subs.getText().toString().substring(0,subs.getText().toString().length()-2));

    }

    private void showAwaySubs(View rootView, List<Squad> awaySquad) {
        TextView subs = rootView.findViewById(R.id.txtAwaySubs);
        subs.setText("Subs : ");
        for (Squad squad : awaySquad) {
            if(squad.getPosition()==0)
                subs.setText(subs.getText() + datasource.getPlayer(squad.getPlayerID()).getPlayerName().split("@")[1] + " , ");
        }
        subs.setText(subs.getText().toString().substring(0,subs.getText().toString().length()-2));

    }

    private void showHomeMidLine(int positions, View rootView, Formation formation) {
        //String name = datasource.getPlayer(datasource.getSquad(formation.getRm()).getPlayerID()).getPlayerName().split("@")[1];
        //playerName.setText(""+name.split("@")[0].substring(0,2)+". "+name.split("@")[1]);

        TextView t1 = rootView.findViewById(R.id.statsHomeFormationRM);
        if(datasource.getPlayer(datasource.getSquad(formation.getRm()).getPlayerID()).getPlayerName() != null)
            t1.setText(""+ datasource.getPlayer(datasource.getSquad(formation.getRm()).getPlayerID()).getPlayerName().split("@")[1]);
        TextView t2 = rootView.findViewById(R.id.statsHomeFormationRCM);
        if(datasource.getPlayer(datasource.getSquad(formation.getRcm()).getPlayerID()).getPlayerName() != null)
            t2.setText(""+ datasource.getPlayer(datasource.getSquad(formation.getRcm()).getPlayerID()).getPlayerName().split("@")[1]);
        TextView t3 = rootView.findViewById(R.id.statsHomeFormationCM);
        if(datasource.getPlayer(datasource.getSquad(formation.getCm()).getPlayerID()).getPlayerName() != null)
            t3.setText(""+ datasource.getPlayer(datasource.getSquad(formation.getCm()).getPlayerID()).getPlayerName().split("@")[1]);
        TextView t4 = rootView.findViewById(R.id.statsHomeFormationLCM);
        if(datasource.getPlayer(datasource.getSquad(formation.getLcm()).getPlayerID()).getPlayerName() != null)
            t4.setText(""+ datasource.getPlayer(datasource.getSquad(formation.getLcm()).getPlayerID()).getPlayerName().split("@")[1]);
        TextView t5 = rootView.findViewById(R.id.statsHomeFormationLM);
        if(datasource.getPlayer(datasource.getSquad(formation.getLm()).getPlayerID()).getPlayerName() != null)
            t5.setText(""+ datasource.getPlayer(datasource.getSquad(formation.getLm()).getPlayerID()).getPlayerName().split("@")[1]);

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

    private void showHomeStrLine(int positions, View rootView, Formation formation) {

        TextView t1 = rootView.findViewById(R.id.statsHomeFormationRST);
        if(datasource.getPlayer(datasource.getSquad(formation.getRst()).getPlayerID()).getPlayerName() != null)
            t1.setText(""+ datasource.getPlayer(datasource.getSquad(formation.getRst()).getPlayerID()).getPlayerName().split("@")[1]);
        TextView t2 = rootView.findViewById(R.id.statsHomeFormationST);
        if(datasource.getPlayer(datasource.getSquad(formation.getSt()).getPlayerID()).getPlayerName() != null)
            t2.setText(""+ datasource.getPlayer(datasource.getSquad(formation.getSt()).getPlayerID()).getPlayerName().split("@")[1]);
        TextView t3 = rootView.findViewById(R.id.statsHomeFormationLST);
        if(datasource.getPlayer(datasource.getSquad(formation.getLst()).getPlayerID()).getPlayerName() != null)
            t3.setText(""+ datasource.getPlayer(datasource.getSquad(formation.getLst()).getPlayerID()).getPlayerName().split("@")[1]);

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

    private void showHomeDefLine(int positions, View rootView, Formation formation) {

        TextView t1 = rootView.findViewById(R.id.statsHomeFormationRB);
        if(datasource.getPlayer(datasource.getSquad(formation.getRb()).getPlayerID()).getPlayerName() != null)
            t1.setText("" + datasource.getPlayer(datasource.getSquad(formation.getRb()).getPlayerID()).getPlayerName().split("@")[1]);
        TextView t2 = rootView.findViewById(R.id.statsHomeFormationRCD);
        if(datasource.getPlayer(datasource.getSquad(formation.getRcd()).getPlayerID()).getPlayerName() != null)
            t2.setText("" + datasource.getPlayer(datasource.getSquad(formation.getRcd()).getPlayerID()).getPlayerName().split("@")[1]);
        TextView t3 = rootView.findViewById(R.id.statsHomeFormationCD);
        if(datasource.getPlayer(datasource.getSquad(formation.getCd()).getPlayerID()).getPlayerName() != null)
            t3.setText("" + datasource.getPlayer(datasource.getSquad(formation.getCd()).getPlayerID()).getPlayerName().split("@")[1]);
        TextView t4 = rootView.findViewById(R.id.statsHomeFormationLCD);
        if(datasource.getPlayer(datasource.getSquad(formation.getLcd()).getPlayerID()).getPlayerName() != null)
            t4.setText("" + datasource.getPlayer(datasource.getSquad(formation.getLcd()).getPlayerID()).getPlayerName().split("@")[1]);
        TextView t5 = rootView.findViewById(R.id.statsHomeFormationLB);
        if(datasource.getPlayer(datasource.getSquad(formation.getLb()).getPlayerID()).getPlayerName() != null)
            t5.setText("" + datasource.getPlayer(datasource.getSquad(formation.getLb()).getPlayerID()).getPlayerName().split("@")[1]);
        TextView gk = rootView.findViewById(R.id.statsHomeFormationGK);
        if(datasource.getPlayer(datasource.getSquad(formation.getGk()).getPlayerID()).getPlayerName() != null)
            gk.setText("" + datasource.getPlayer(datasource.getSquad(formation.getGk()).getPlayerID()).getPlayerName().split("@")[1]);

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

    private void showAwayFormation(Formation awayFormation,View rootView) {
        switch (awayFormation.getFormations()){
            case 0:
                showAwayDefLine(4,rootView,awayFormation);
                showAwayMidLine(4,rootView,awayFormation);
                showAwayStrLine(2,rootView,awayFormation);
                break;
            case 1:
                showAwayDefLine(3,rootView,awayFormation);
                showAwayMidLine(5,rootView,awayFormation);
                showAwayStrLine(2,rootView,awayFormation);
                break;
            case 2:
                showAwayDefLine(4,rootView,awayFormation);
                showAwayMidLine(3,rootView,awayFormation);
                showAwayStrLine(3,rootView,awayFormation);
                break;
        }

    }

    private void showAwayMidLine(int positions, View rootView, Formation formation) {
        //String name = datasource.getPlayer(datasource.getSquad(formation.getRm()).getPlayerID()).getPlayerName().split("@")[1];
        //playerName.setText(""+name.split("@")[0].substring(0,2)+". "+name.split("@")[1]);

        TextView t1 = rootView.findViewById(R.id.statsAwayFormationRM);
        if(datasource.getPlayer(datasource.getSquad(formation.getRm()).getPlayerID()).getPlayerName() != null)
            t1.setText(""+ datasource.getPlayer(datasource.getSquad(formation.getRm()).getPlayerID()).getPlayerName().split("@")[1]);
        TextView t2 = rootView.findViewById(R.id.statsAwayFormationRCM);
        if(datasource.getPlayer(datasource.getSquad(formation.getRcm()).getPlayerID()).getPlayerName() != null)
            t2.setText(""+ datasource.getPlayer(datasource.getSquad(formation.getRcm()).getPlayerID()).getPlayerName().split("@")[1]);
        TextView t3 = rootView.findViewById(R.id.statsAwayFormationCM);
        if(datasource.getPlayer(datasource.getSquad(formation.getCm()).getPlayerID()).getPlayerName() != null)
            t3.setText(""+ datasource.getPlayer(datasource.getSquad(formation.getCm()).getPlayerID()).getPlayerName().split("@")[1]);
        TextView t4 = rootView.findViewById(R.id.statsAwayFormationLCM);
        if(datasource.getPlayer(datasource.getSquad(formation.getLcm()).getPlayerID()).getPlayerName() != null)
            t4.setText(""+ datasource.getPlayer(datasource.getSquad(formation.getLcm()).getPlayerID()).getPlayerName().split("@")[1]);
        TextView t5 = rootView.findViewById(R.id.statsAwayFormationLM);
        if(datasource.getPlayer(datasource.getSquad(formation.getLm()).getPlayerID()).getPlayerName() != null)
            t5.setText(""+ datasource.getPlayer(datasource.getSquad(formation.getLm()).getPlayerID()).getPlayerName().split("@")[1]);

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

    private void showAwayStrLine(int positions, View rootView, Formation formation) {

        TextView t1 = rootView.findViewById(R.id.statsAwayFormationRST);
        if(datasource.getPlayer(datasource.getSquad(formation.getRst()).getPlayerID()).getPlayerName() != null)
            t1.setText(""+ datasource.getPlayer(datasource.getSquad(formation.getRst()).getPlayerID()).getPlayerName().split("@")[1]);
        TextView t2 = rootView.findViewById(R.id.statsAwayFormationST);
        if(datasource.getPlayer(datasource.getSquad(formation.getSt()).getPlayerID()).getPlayerName() != null)
            t2.setText(""+ datasource.getPlayer(datasource.getSquad(formation.getSt()).getPlayerID()).getPlayerName().split("@")[1]);
        TextView t3 = rootView.findViewById(R.id.statsAwayFormationLST);
        if(datasource.getPlayer(datasource.getSquad(formation.getLst()).getPlayerID()).getPlayerName() != null)
            t3.setText(""+ datasource.getPlayer(datasource.getSquad(formation.getLst()).getPlayerID()).getPlayerName().split("@")[1]);

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

    private void showAwayDefLine(int positions, View rootView, Formation formation) {

        TextView t1 = rootView.findViewById(R.id.statsAwayFormationRB);
        if(datasource.getPlayer(datasource.getSquad(formation.getRb()).getPlayerID()).getPlayerName() != null)
            t1.setText("" + datasource.getPlayer(datasource.getSquad(formation.getRb()).getPlayerID()).getPlayerName().split("@")[1]);
        TextView t2 = rootView.findViewById(R.id.statsAwayFormationRCD);
        if(datasource.getPlayer(datasource.getSquad(formation.getRcd()).getPlayerID()).getPlayerName() != null)
            t2.setText("" + datasource.getPlayer(datasource.getSquad(formation.getRcd()).getPlayerID()).getPlayerName().split("@")[1]);
        TextView t3 = rootView.findViewById(R.id.statsAwayFormationCD);
        if(datasource.getPlayer(datasource.getSquad(formation.getCd()).getPlayerID()).getPlayerName() != null)
            t3.setText("" + datasource.getPlayer(datasource.getSquad(formation.getCd()).getPlayerID()).getPlayerName().split("@")[1]);
        TextView t4 = rootView.findViewById(R.id.statsAwayFormationLCD);
        if(datasource.getPlayer(datasource.getSquad(formation.getLcd()).getPlayerID()).getPlayerName() != null)
            t4.setText("" + datasource.getPlayer(datasource.getSquad(formation.getLcd()).getPlayerID()).getPlayerName().split("@")[1]);
        TextView t5 = rootView.findViewById(R.id.statsAwayFormationLB);
        if(datasource.getPlayer(datasource.getSquad(formation.getLb()).getPlayerID()).getPlayerName() != null)
            t5.setText("" + datasource.getPlayer(datasource.getSquad(formation.getLb()).getPlayerID()).getPlayerName().split("@")[1]);
        TextView gk = rootView.findViewById(R.id.statsAwayFormationGK);
        if(datasource.getPlayer(datasource.getSquad(formation.getGk()).getPlayerID()).getPlayerName() != null)
            gk.setText("" + datasource.getPlayer(datasource.getSquad(formation.getGk()).getPlayerID()).getPlayerName().split("@")[1]);

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
