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
public class tzlc_club_detail_loan extends Fragment {

    List<Loan> loanList;
    private tzlcDataSource datasource;


    public tzlc_club_detail_loan() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_tzlc_club_detail_loan, container, false);

        View rootView = inflater.inflate(R.layout.fragment_tzlc_club_detail_loan, container, false);
        tzlc_club_detail ta = (tzlc_club_detail) getActivity();
        datasource= new tzlcDataSource(getContext());
        datasource.open();
        loanList = datasource.getAllLoansForClub(ta.getClubID());
        adaptor_club_loan adaptor = new adaptor_club_loan(getContext(), R.layout.loandisplaylist, loanList);
        ListView lv = (ListView) rootView.findViewById(R.id.tabClubLoanList);
        lv.setAdapter(adaptor);
        return rootView;
    }

}
