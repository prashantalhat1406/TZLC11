package com.kfandra.tzlc.tzlc;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class adaptor_match_tabs extends FragmentStatePagerAdapter {

    int tabNumber;

    public adaptor_match_tabs(FragmentManager fm, int noOfTabs) {
        super(fm);
        this.tabNumber = noOfTabs;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i)
        {
            case 0: tzlc_match_details_stats t1 = new tzlc_match_details_stats(); return t1;
            case 1: tzlc_match_details_loans t3 = new tzlc_match_details_loans(); return t3;
            case 2: tzlc_match_details_mo t5 = new tzlc_match_details_mo(); return t5;
            case 3: tzlc_match_details_highlights t6 = new tzlc_match_details_highlights(); return t6;
            case 4: tzlc_match_details_squad t7 = new tzlc_match_details_squad(); return t7;
            case 5: tzlc_match_details_formation t8 = new tzlc_match_details_formation(); return t8;
            default : return null;
        }
    }

    @Override
    public int getCount() {
        return tabNumber;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        //return super.getItemPosition(object);
        return POSITION_NONE;
    }
}
