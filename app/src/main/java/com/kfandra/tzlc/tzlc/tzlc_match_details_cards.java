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
public class tzlc_match_details_cards extends Fragment {

    List<Card> cardList;
    private tzlcDataSource datasource;


    public tzlc_match_details_cards() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_tzlc_match_details_cards, container, false);
        final tzlc_match_details_tabs match_details = (tzlc_match_details_tabs) getActivity();
        datasource= new tzlcDataSource(getContext());
        datasource.open();
        cardList = datasource.getAllCardsForMatch(match_details.getMatchID());
        adaptor_card adaptor = new adaptor_card(getContext(), R.layout.displaylistcards, cardList);
        ListView lv = (ListView) rootView.findViewById(R.id.tabMatchDetailsCardList);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Card card  = cardList.get(position);
                Intent j = new Intent(getContext(),tzlc_card_add.class);
                Bundle extras  = new Bundle();
                extras.putLong("matchID", match_details.getMatchID());
                extras.putInt("matchTime",card.getTime());
                extras.putLong("cardID", card.getId());
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
        fragmentTransaction.detach(tzlc_match_details_cards.this).attach(tzlc_match_details_cards.this).commit();
        super.onAttach(context);
    }

}
