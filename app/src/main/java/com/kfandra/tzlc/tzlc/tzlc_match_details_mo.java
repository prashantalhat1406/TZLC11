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
public class tzlc_match_details_mo extends Fragment {

    List<MatchOffcial> moList;
    private tzlcDataSource datasource;
    ListView listView;
    //private long matchID;


    public tzlc_match_details_mo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_tzlc_match_details_mo, container, false);
        final tzlc_match_details_tabs match_details = (tzlc_match_details_tabs) getActivity();
        datasource= new tzlcDataSource(getContext());
        datasource.open();
        //matchID = match_details.getMatchID();
        moList = datasource.getAllMOsForMatch(match_details.getMatchID());
        adaptor_mo adaptor = new adaptor_mo(getContext(), R.layout.matchoffcialdisplaylist, moList);
        listView = (ListView) rootView.findViewById(R.id.tabMatchDetailsMOList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MatchOffcial matchOffcial = moList.get(position);

                if(matchOffcial.getJob() == 7)
                    Toast.makeText(getContext(),"Loans are not allowed to Edit/Delete from MO list.Please visit Loan screen",Toast.LENGTH_SHORT).show();
                else {
                    Intent j = new Intent(getContext(), tzlc_mo_add.class);
                    Bundle extras = new Bundle();
                    extras.putLong("matchID", match_details.getMatchID());
                    extras.putLong("moID", matchOffcial.getId());
                    j.putExtras(extras);
                    startActivity(j);
                }
            }
        });
        listView.setAdapter(adaptor);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        FragmentTransaction fragmentTransaction  = getFragmentManager().beginTransaction();
        fragmentTransaction.detach(tzlc_match_details_mo.this).attach(tzlc_match_details_mo.this).commit();

        super.onAttach(context);
    }


}
