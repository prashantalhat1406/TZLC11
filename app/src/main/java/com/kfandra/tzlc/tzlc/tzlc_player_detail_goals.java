package com.kfandra.tzlc.tzlc;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class tzlc_player_detail_goals extends Fragment {

    List<Goal> goalList;
    private tzlcDataSource datasource;


    public tzlc_player_detail_goals() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_tzlc_player_detail_goals, container, false);

        View rootView = inflater.inflate(R.layout.fragment_tzlc_player_detail_goals, container, false);

        tzlc_player_details ta = (tzlc_player_details) getActivity();
        datasource= new tzlcDataSource(getContext());
        datasource.open();
        goalList = datasource.getAllGoalsForPlayer(ta.getPlayerID());
        adaptor_player_goal adaptor = new adaptor_player_goal(getContext(), R.layout.goaldisplaylist, goalList);
        ListView lv = (ListView) rootView.findViewById(R.id.tabPlayerGoal);
        lv.setAdapter(adaptor);
        return rootView;
    }

}
