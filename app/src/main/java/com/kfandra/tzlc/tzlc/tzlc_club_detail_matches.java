package com.kfandra.tzlc.tzlc;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class tzlc_club_detail_matches extends Fragment {
    List<Match> matchList;
    private tzlcDataSource datasource;


    public tzlc_club_detail_matches() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_tzlc_club_detail_matches, container, false);
        tzlc_club_detail ta = (tzlc_club_detail) getActivity();
        datasource= new tzlcDataSource(getContext());
        datasource.open();
        matchList = datasource.getAllMatchesForClub(ta.getClubID());
        adaptor_match adaptor = new adaptor_match(getContext(), R.layout.matchdisplaylist, matchList);
        ListView lv = (ListView) rootView.findViewById(R.id.tabCubMatchList);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), tzlc_match_details_tabs.class);

                Stats stats = datasource.getAllStatsForMatch(matchList.get(position).getId());
                if(stats.getHome_Score() == -1)
                    Toast.makeText(getContext(),"Match stats not available",Toast.LENGTH_SHORT).show();
                else
                {
                    //i.putExtra("matchID", matchList.get(position).getId());
                    //i.putExtra("fromClubDetails", 1);
                    //startActivity(i);

                    Bundle extras  = new Bundle();
                    extras.putLong("matchID", matchList.get(position).getId());
                    extras.putInt("fromClubDetails",1);
                    intent.putExtras(extras);
                    startActivity(intent);
                }

            }
        });
        lv.setAdapter(adaptor);
        return rootView;
    }

}
