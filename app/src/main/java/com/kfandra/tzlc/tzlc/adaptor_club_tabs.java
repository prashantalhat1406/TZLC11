package com.kfandra.tzlc.tzlc;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class adaptor_club_tabs extends FragmentStatePagerAdapter {

    int tabNumber;

    public adaptor_club_tabs(FragmentManager fm, int noOfTabs) {
        super(fm);
        this.tabNumber = noOfTabs;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i)
        {
            case 0: tzlc_club_detail_players t1 = new tzlc_club_detail_players();  return t1;
            case 1: tzlc_club_detail_matches t2 = new tzlc_club_detail_matches(); return t2;
            case 2: tzlc_club_detail_mo t3 = new tzlc_club_detail_mo(); return t3;
            case 3: tzlc_club_detail_cards t4 = new tzlc_club_detail_cards(); return t4;
            case 4: tzlc_club_detail_goal t5 = new tzlc_club_detail_goal(); return t5;
            case 5: tzlc_club_detail_loan t6 = new tzlc_club_detail_loan(); return t6;
            default : return null; 
        }

        //return null;
    }

    @Override
    public int getCount() {
        //return 0;
        return tabNumber;
    }
}
