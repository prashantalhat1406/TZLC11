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
public class tzlc_club_detail_goal extends Fragment {
    List<Goal> goalList;
    private tzlcDataSource datasource;


    public tzlc_club_detail_goal() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_tzlc_club_detail_goal, container, false);
        View rootView = inflater.inflate(R.layout.fragment_tzlc_club_detail_goal, container, false);

        tzlc_club_detail ta = (tzlc_club_detail) getActivity();
        datasource= new tzlcDataSource(getContext());
        datasource.open();
        goalList = datasource.getAllGoalsForClub(ta.getClubID());
        adaptor_club_goal adaptor = new adaptor_club_goal(getContext(), R.layout.goaldisplaylist, goalList);
        ListView lv = (ListView) rootView.findViewById(R.id.tabClubGoal);
        lv.setAdapter(adaptor);
        return rootView;
    }

}
