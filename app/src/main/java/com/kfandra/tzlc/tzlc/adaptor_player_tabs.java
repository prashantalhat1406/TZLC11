package com.kfandra.tzlc.tzlc;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class adaptor_player_tabs extends FragmentStatePagerAdapter {

    int tabNumber;

    public adaptor_player_tabs(FragmentManager fm, int noOfTabs) {
        super(fm);
        this.tabNumber = noOfTabs;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i)
        {
            case 0: tzlc_player_detail_mo t1 = new tzlc_player_detail_mo(); return t1;
            case 1: tzlc_player_detail_cards t2 = new tzlc_player_detail_cards(); return t2;
            case 2: tzlc_player_detail_goals t3 = new tzlc_player_detail_goals(); return t3;
            case 3: tzlc_player_detail_loans t4 = new tzlc_player_detail_loans(); return t4;
            default : return null;
        }
    }

    @Override
    public int getCount() {
        return tabNumber;
    }
}
