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
public class tzlc_club_detail_cards extends Fragment {

    List<Card> cardList;
    private tzlcDataSource datasource;


    public tzlc_club_detail_cards() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_tzlc_club_detail_cards, container, false);

        View rootView = inflater.inflate(R.layout.fragment_tzlc_club_detail_cards, container, false);

        tzlc_club_detail ta = (tzlc_club_detail) getActivity();
        datasource= new tzlcDataSource(getContext());
        datasource.open();
        cardList = datasource.getAllCardsForClub(ta.getClubID());
        adaptor_club_card adaptor = new adaptor_club_card(getContext(), R.layout.displaylistcards, cardList);
        ListView lv = (ListView) rootView.findViewById(R.id.tabClubCardList);
        lv.setAdapter(adaptor);
        return rootView;
    }

}
