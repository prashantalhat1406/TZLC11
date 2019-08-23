package com.kfandra.tzlc.tzlc;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class tzlc_match_details_squad extends Fragment {

    List<Squad> squadList;
    private tzlcDataSource datasource;


    public tzlc_match_details_squad() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_tzlc_match_details_squad, container, false);
        final tzlc_match_details_tabs match_details = (tzlc_match_details_tabs) getActivity();
        datasource= new tzlcDataSource(getContext());
        datasource.open();

        /*TextView homeClubName = (TextView) rootView.findViewById(R.id.txtsquadDisplayHomeClub);
        homeClubName.setText(""+datasource.getClub(datasource.getMatch(match_details.getMatchID()).getHomeClubID()).getClubName());

        TextView awayClubName = (TextView) rootView.findViewById(R.id.txtsquadDisplayAwayClub);
        awayClubName.setText(""+datasource.getClub(datasource.getMatch(match_details.getMatchID()).getAwayClubID()).getClubName());*/

        /*squadHomeList = datasource.getAvailableSquadForMatchandClub(match_details.getMatchID(),datasource.getMatch(match_details.getMatchID()).getHomeClubID());
        adaptor_squad_display homeadaptor = new adaptor_squad_display(getContext(), R.layout.squadaddlistitem, squadHomeList);
        ListView homeList = (ListView) rootView.findViewById(R.id.tablstHomeSquadDisplay);
        homeList.setAdapter(homeadaptor);*/

        RadioButton awaySquad = rootView.findViewById(R.id.rdbsquadAway);
        awaySquad.setText(datasource.getClub(datasource.getMatch(match_details.getMatchID()).getAwayClubID()).getClubName());
        awaySquad.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    squadList = datasource.getAvailableSquadForMatchandClub(match_details.getMatchID(),datasource.getMatch(match_details.getMatchID()).getAwayClubID());
                    adaptor_squad_display squadadaptor = new adaptor_squad_display(getContext(), R.layout.squadaddlistitem, squadList);
                    ListView squadList = (ListView) rootView.findViewById(R.id.tablstSquadDisplay);
                    squadList.setAdapter(squadadaptor);
                }
            }
        });

        RadioButton homeSquad = rootView.findViewById(R.id.rdbsquadHome);
        homeSquad.setText(datasource.getClub(datasource.getMatch(match_details.getMatchID()).getHomeClubID()).getClubName());
        homeSquad.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    squadList = datasource.getAvailableSquadForMatchandClub(match_details.getMatchID(),datasource.getMatch(match_details.getMatchID()).getHomeClubID());
                    adaptor_squad_display squadadaptor = new adaptor_squad_display(getContext(), R.layout.squadaddlistitem, squadList);
                    ListView squadList = (ListView) rootView.findViewById(R.id.tablstSquadDisplay);
                    squadList.setAdapter(squadadaptor);
                }
            }
        });
/*

        squadHomeList = datasource.getAvailableSquadForMatchandClub(match_details.getMatchID(),datasource.getMatch(match_details.getMatchID()).getHomeClubID());
        adaptor_squad_display homeadaptor = new adaptor_squad_display(getContext(), R.layout.squadaddlistitem, squadHomeList);
        ListView homeList = (ListView) rootView.findViewById(R.id.tablstHomeSquadDisplay);
        homeList.setAdapter(homeadaptor);

        squadAwayList = datasource.getAvailableSquadForMatchandClub(match_details.getMatchID(),datasource.getMatch(match_details.getMatchID()).getAwayClubID());
        adaptor_squad_display awayadaptor = new adaptor_squad_display(getContext(), R.layout.squadaddlistitem, squadAwayList);
        ListView awayList = (ListView) rootView.findViewById(R.id.tablstAwaySquadDisplay);
        awayList.setAdapter(awayadaptor);*/
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        FragmentTransaction fragmentTransaction  = getFragmentManager().beginTransaction();
        fragmentTransaction.detach(tzlc_match_details_squad.this).attach(tzlc_match_details_squad.this).commit();

        super.onAttach(context);
    }

}
