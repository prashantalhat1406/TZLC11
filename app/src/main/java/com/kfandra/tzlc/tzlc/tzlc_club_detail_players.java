package com.kfandra.tzlc.tzlc;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class tzlc_club_detail_players extends Fragment {

    List<Player> playerList;
    private tzlcDataSource datasource;


    public tzlc_club_detail_players() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_tzlc_club_detail_players, container, false);
        tzlc_club_detail ta = (tzlc_club_detail) getActivity();
        datasource= new tzlcDataSource(getContext());
        datasource.open();
        playerList = datasource.getAllPlayersForClub(ta.getClubID());



        HashMap<Long,Integer> cardsList = datasource.getAllCardsForClubID(ta.getClubID()); //new HashMap<Long, Integer>();
        HashMap<Long,Integer> moList = datasource.getAllMOsForClubID(ta.getClubID()); //new HashMap<Long, Integer>();
        HashMap<Long,Integer> goalList = datasource.getAllGoalsForClubID(ta.getClubID()); //new HashMap<Long, Integer>();
        HashMap<Long,Integer> loanList = datasource.getAllLoansForClubID(ta.getClubID()); //new HashMap<Long, Integer>();

        for (Player player : playerList) {
            player.setTotalCard((cardsList.get(player.getId()) == null) ? 0 : cardsList.get(player.getId()));
            player.setTotalGoal((goalList.get(player.getId()) == null) ? 0 : goalList.get(player.getId()));
            player.setTotalLoan((loanList.get(player.getId()) == null) ? 0 : loanList.get(player.getId()));
            player.setTotalMO((moList.get(player.getId()) == null) ? 0 : moList.get(player.getId()));
        }
        //cardsList = datasource.getAllCardsForClub(ta.getClubID());

        adaptor_club_player adaptor = new adaptor_club_player(getContext(), R.layout.playerdisplaylist, playerList);
        ListView lv =  (ListView) rootView.findViewById(R.id.tabClubPlayerList);
        //comment list click if there is any issue of sync
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), tzlc_player_details.class);
                Bundle extras  = new Bundle();
                extras.putLong("playerID", playerList.get(position).getId());
                extras.putInt("fromClubDetails",1);
                intent.putExtras(extras);
                startActivity(intent);

            }
        });
        lv.setAdapter(adaptor);

        return rootView;
    }
}
