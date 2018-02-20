/*
 *  Used by PlayerSelectActivity. Not fully implemented.
 */

package com.iamrobots.connectfour.offline;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Abraham on 2/11/18.
 */

public class PagerAdapter extends FragmentStatePagerAdapter{
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                TabFragmentHuman tab1 = new TabFragmentHuman();
                return tab1;
            case 1:
                TabFragmentHuman tab2 = new TabFragmentHuman();
                return tab2;
            case 2:
                TabFragmentHuman tab3 = new TabFragmentHuman();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
