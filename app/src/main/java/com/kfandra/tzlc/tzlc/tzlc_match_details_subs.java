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
import android.widget.Toast;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class tzlc_match_details_subs extends Fragment {

    List<Substitute> substituteList;
    private tzlcDataSource datasource;
    ListView listView;
    //private long matchID;


    public tzlc_match_details_subs() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tzlc_match_details_subs, container, false);
        final tzlc_match_details_tabs match_details = (tzlc_match_details_tabs) getActivity();
        datasource= new tzlcDataSource(getContext());
        datasource.open();
        //matchID = match_details.getMatchID();
        substituteList = datasource.getAllSubstituteForMatch(match_details.getMatchID());
        adaptor_subs adaptor = new adaptor_subs(getContext(), R.layout.subsdisplaylist, substituteList);
        listView = (ListView) rootView.findViewById(R.id.tabSubsList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Substitute substitute = substituteList.get(position);


                Intent j = new Intent(getContext(), tzlc_sub_add.class);
                Bundle extras = new Bundle();
                extras.putLong("matchID", match_details.getMatchID());
                extras.putLong("subID", substitute.getId());
                j.putExtras(extras);
                startActivity(j);

            }
        });
        listView.setAdapter(adaptor);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        FragmentTransaction fragmentTransaction  = getFragmentManager().beginTransaction();
        fragmentTransaction.detach(tzlc_match_details_subs.this).attach(tzlc_match_details_subs.this).commit();

        super.onAttach(context);
    }

}
