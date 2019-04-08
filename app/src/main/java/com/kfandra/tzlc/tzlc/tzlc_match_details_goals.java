package com.kfandra.tzlc.tzlc;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class tzlc_match_details_goals extends Fragment {

    List<Goal> goalList;
    private tzlcDataSource datasource;


    public tzlc_match_details_goals() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_tzlc_match_details_goals, container, false);

        final tzlc_match_details_tabs match_details = (tzlc_match_details_tabs) getActivity();
        datasource= new tzlcDataSource(getContext());
        datasource.open();
        goalList = datasource.getAllGoalsForMatch(match_details.getMatchID());
        adaptor_goal adaptor = new adaptor_goal(getContext(), R.layout.goaldisplaylist, goalList);
        ListView lv = (ListView) rootView.findViewById(R.id.tabMatchDetailGoals);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Goal goal = goalList.get(position);
                Intent j = new Intent(getContext(),tzlc_goal_add.class);
                Bundle extras  = new Bundle();
                extras.putLong("matchID", match_details.getMatchID());
                extras.putInt("matchTime",goal.getVcmtime());
                extras.putLong("goalID", goal.getId());
                j.putExtras(extras);
                startActivity(j);
            }
        });
        lv.setAdapter(adaptor);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        FragmentTransaction fragmentTransaction  = getFragmentManager().beginTransaction();
        fragmentTransaction.detach(tzlc_match_details_goals.this).attach(tzlc_match_details_goals.this).commit();
        super.onAttach(context);
    }

}
