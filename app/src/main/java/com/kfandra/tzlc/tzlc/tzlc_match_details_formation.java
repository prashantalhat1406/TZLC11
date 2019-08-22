package com.kfandra.tzlc.tzlc;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;



public class tzlc_match_details_formation extends Fragment {
    List<Squad> squadHomeList,squadAwayList;
    private tzlcDataSource datasource;

    public tzlc_match_details_formation() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_tzlc_match_details_formation, container, false);
        final tzlc_match_details_tabs match_details = (tzlc_match_details_tabs) getActivity();
        datasource= new tzlcDataSource(getContext());
        datasource.open();

        RadioButton homeFormation = rootView.findViewById(R.id.rdbFormationDisplayHomeClub);
        homeFormation.setText(datasource.getClub(datasource.getMatch(match_details.getMatchID()).getHomeClubID()).getClubName());

        homeFormation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    //Toast.makeText(getContext(), "Home", Toast.LENGTH_SHORT).show();
                    Formation formation = datasource.getFormation(match_details.getMatchID(), datasource.getMatch(match_details.getMatchID()).getHomeClubID());
                    showFormation(formation,rootView);
                }
            }
        });
        RadioButton awayFormation = rootView.findViewById(R.id.rdbFormationDisplayAwayClub);
        awayFormation.setText(datasource.getClub(datasource.getMatch(match_details.getMatchID()).getAwayClubID()).getClubName());
        awayFormation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    //Toast.makeText(getContext(), "Away", Toast.LENGTH_SHORT).show();
                    Formation formation = datasource.getFormation(match_details.getMatchID(), datasource.getMatch(match_details.getMatchID()).getAwayClubID());
                    showFormation(formation,rootView);
                }
            }
        });
        return rootView;
    }

    private void showFormation(Formation formation, View rootView) {
        switch (formation.getFormations()){
            case 0:
                showDefLine(4,rootView,formation);
                showMidLine(4,rootView,formation);
                showStrLine(2,rootView,formation);
                break;
            case 1:
                showDefLine(3,rootView,formation);
                showMidLine(5,rootView,formation);
                showStrLine(2,rootView,formation);
                break;
            case 2:
                showDefLine(4,rootView,formation);
                showMidLine(3,rootView,formation);
                showStrLine(3,rootView,formation);
                break;
        }
    }

    private void showMidLine(int positions, View rootView, Formation formation) {
        //String name = datasource.getPlayer(datasource.getSquad(formation.getRm()).getPlayerID()).getPlayerName().split("@")[1];
        //playerName.setText(""+name.split("@")[0].substring(0,2)+". "+name.split("@")[1]);

        TextView t1 = rootView.findViewById(R.id.formationDisplayRM);
        if(datasource.getPlayer(datasource.getSquad(formation.getRm()).getPlayerID()).getPlayerName() != null)
        t1.setText(""+ datasource.getPlayer(datasource.getSquad(formation.getRm()).getPlayerID()).getPlayerName().split("@")[1]);
        TextView t2 = rootView.findViewById(R.id.formationDisplayRCM);
        if(datasource.getPlayer(datasource.getSquad(formation.getRcm()).getPlayerID()).getPlayerName() != null)
        t2.setText(""+ datasource.getPlayer(datasource.getSquad(formation.getRcm()).getPlayerID()).getPlayerName().split("@")[1]);
        TextView t3 = rootView.findViewById(R.id.formationDisplayCM);
        if(datasource.getPlayer(datasource.getSquad(formation.getCm()).getPlayerID()).getPlayerName() != null)
        t3.setText(""+ datasource.getPlayer(datasource.getSquad(formation.getCm()).getPlayerID()).getPlayerName().split("@")[1]);
        TextView t4 = rootView.findViewById(R.id.formationDisplayLCM);
        if(datasource.getPlayer(datasource.getSquad(formation.getLcm()).getPlayerID()).getPlayerName() != null)
        t4.setText(""+ datasource.getPlayer(datasource.getSquad(formation.getLcm()).getPlayerID()).getPlayerName().split("@")[1]);
        TextView t5 = rootView.findViewById(R.id.formationDisplayLM);
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

    private void showStrLine(int positions, View rootView, Formation formation) {

        TextView t1 = rootView.findViewById(R.id.formationDisplayRST);
        if(datasource.getPlayer(datasource.getSquad(formation.getRst()).getPlayerID()).getPlayerName() != null)
        t1.setText(""+ datasource.getPlayer(datasource.getSquad(formation.getRst()).getPlayerID()).getPlayerName().split("@")[1]);
        TextView t2 = rootView.findViewById(R.id.formationDisplayST);
        if(datasource.getPlayer(datasource.getSquad(formation.getSt()).getPlayerID()).getPlayerName() != null)
        t2.setText(""+ datasource.getPlayer(datasource.getSquad(formation.getSt()).getPlayerID()).getPlayerName().split("@")[1]);
        TextView t3 = rootView.findViewById(R.id.formationDisplayLST);
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

    private void showDefLine(int positions, View rootView, Formation formation) {

            TextView t1 = rootView.findViewById(R.id.formationDisplayRB);
            if(datasource.getPlayer(datasource.getSquad(formation.getRb()).getPlayerID()).getPlayerName() != null)
                t1.setText("" + datasource.getPlayer(datasource.getSquad(formation.getRb()).getPlayerID()).getPlayerName().split("@")[1]);
            TextView t2 = rootView.findViewById(R.id.formationDisplayRCD);
            if(datasource.getPlayer(datasource.getSquad(formation.getRcd()).getPlayerID()).getPlayerName() != null)
                t2.setText("" + datasource.getPlayer(datasource.getSquad(formation.getRcd()).getPlayerID()).getPlayerName().split("@")[1]);
            TextView t3 = rootView.findViewById(R.id.formationDisplayCD);
            if(datasource.getPlayer(datasource.getSquad(formation.getCd()).getPlayerID()).getPlayerName() != null)
                t3.setText("" + datasource.getPlayer(datasource.getSquad(formation.getCd()).getPlayerID()).getPlayerName().split("@")[1]);
            TextView t4 = rootView.findViewById(R.id.formationDisplayLCD);
            if(datasource.getPlayer(datasource.getSquad(formation.getLcd()).getPlayerID()).getPlayerName() != null)
            t4.setText("" + datasource.getPlayer(datasource.getSquad(formation.getLcd()).getPlayerID()).getPlayerName().split("@")[1]);
            TextView t5 = rootView.findViewById(R.id.formationDisplayLB);
            if(datasource.getPlayer(datasource.getSquad(formation.getLb()).getPlayerID()).getPlayerName() != null)
            t5.setText("" + datasource.getPlayer(datasource.getSquad(formation.getLb()).getPlayerID()).getPlayerName().split("@")[1]);
            TextView gk = rootView.findViewById(R.id.formationDisplayGK);
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


    @Override
    public void onAttach(Context context) {
        FragmentTransaction fragmentTransaction  = getFragmentManager().beginTransaction();
        fragmentTransaction.detach(tzlc_match_details_formation.this).attach(tzlc_match_details_formation.this).commit();
        super.onAttach(context);
    }

}
