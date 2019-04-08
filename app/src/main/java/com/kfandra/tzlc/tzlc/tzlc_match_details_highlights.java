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
        View rootView = inflater.inflate(R.layout.fragment_tzlc_match_details_highlights, container, false);
        tzlc_match_details_tabs match_details = (tzlc_match_details_tabs) getActivity();
        datasource= new tzlcDataSource(getContext());
        datasource.open();
        highlightList = datasource.getAllHighlights(match_details.getMatchID());
        adaptor_highlight adaptor = new adaptor_highlight(getContext(), R.layout.highlightdisplaylist, highlightList);
        ListView lv = (ListView) rootView.findViewById(R.id.tabMatchDetailsHighlightList);
        lv.setAdapter(adaptor);
        return rootView;
    }

}
