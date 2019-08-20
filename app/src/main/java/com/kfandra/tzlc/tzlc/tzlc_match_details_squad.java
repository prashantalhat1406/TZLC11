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
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class tzlc_match_details_squad extends Fragment {

    List<Squad> squadHomeList,squadAwayList;
    private tzlcDataSource datasource;


    public tzlc_match_details_squad() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_tzlc_match_details_squad, container, false);
        tzlc_match_details_tabs match_details = (tzlc_match_details_tabs) getActivity();
        datasource= new tzlcDataSource(getContext());
        datasource.open();

        TextView homeClubName = (TextView) rootView.findViewById(R.id.txtsquadDisplayHomeClub);
        homeClubName.setText(""+datasource.getClub(datasource.getMatch(match_details.getMatchID()).getHomeClubID()).getClubName());

        TextView awayClubName = (TextView) rootView.findViewById(R.id.txtsquadDisplayAwayClub);
        awayClubName.setText(""+datasource.getClub(datasource.getMatch(match_details.getMatchID()).getAwayClubID()).getClubName());


        squadHomeList = datasource.getAvailableSquadForMatchandClub(match_details.getMatchID(),datasource.getMatch(match_details.getMatchID()).getHomeClubID());
        adaptor_squad_display homeadaptor = new adaptor_squad_display(getContext(), R.layout.squadaddlistitem, squadHomeList);
        ListView homeList = (ListView) rootView.findViewById(R.id.tablstHomeSquadDisplay);
        homeList.setAdapter(homeadaptor);

        squadAwayList = datasource.getAvailableSquadForMatchandClub(match_details.getMatchID(),datasource.getMatch(match_details.getMatchID()).getAwayClubID());
        adaptor_squad_display awayadaptor = new adaptor_squad_display(getContext(), R.layout.squadaddlistitem, squadAwayList);
        ListView awayList = (ListView) rootView.findViewById(R.id.tablstAwaySquadDisplay);
        awayList.setAdapter(awayadaptor);
        //datasource= new tzlcDataSource(getContext());
        //datasource.open();
        //squadList = datasource.getAllSquadForMatch(match_details.getMatchID());
        //adaptor_highlight adaptor = new adaptor_highlight(getContext(), R.layout.highlightdisplaylist, highlightList);
        //ListView lv = (ListView) rootView.findViewById(R.id.tabMatchDetailsHighlightList);
        //lv.setAdapter(adaptor);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        FragmentTransaction fragmentTransaction  = getFragmentManager().beginTransaction();
        fragmentTransaction.detach(tzlc_match_details_squad.this).attach(tzlc_match_details_squad.this).commit();

        super.onAttach(context);
    }

}
