package com.kfandra.tzlc.tzlc;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class tzlc_match_details_highlights extends Fragment {

    List<Highlight> highlightList;
    private tzlcDataSource datasource;


    public tzlc_match_details_highlights() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_tzlc_match_details_highlights, container, false);
        final tzlc_match_details_tabs match_details = (tzlc_match_details_tabs) getActivity();
        datasource= new tzlcDataSource(getContext());
        datasource.open();
        final Spinner filter = rootView.findViewById(R.id.spnHighlightFilter);
        List<String> allHLS = datasource.getUnquieHighlights();
        ArrayAdapter<String> filteradapter = new ArrayAdapter<String>(getContext(),R.layout.dropdownitem,allHLS);
        filteradapter.setDropDownViewResource(R.layout.dropdownitem);
        filter.setAdapter(filteradapter);
        filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                highlightList = datasource.getHighlightsOf(filter.getSelectedItem().toString() , match_details.getMatchID());
                adaptor_highlight adaptor = new adaptor_highlight(getContext(), R.layout.highlightdisplaylist, highlightList);
                ListView lv = (ListView) rootView.findViewById(R.id.tabMatchDetailsHighlightList);
                lv.setAdapter(adaptor);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        FragmentTransaction fragmentTransaction  = getFragmentManager().beginTransaction();
        fragmentTransaction.detach(tzlc_match_details_highlights.this).attach(tzlc_match_details_highlights.this).commit();

        super.onAttach(context);
    }

}
