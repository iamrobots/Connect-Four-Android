/*
 *  Used by PlayerSelectActivity. Not fully implemented.
 */

package com.iamrobots.connectfour.offline;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Abraham on 2/11/18.
 */

public class PagerAdapter extends FragmentStatePagerAdapter{

    private Context context;

    public PagerAdapter(FragmentManager fm, PlayerSelectionActivity activity) {
        super(fm);
        context = activity;
        new TabFragmentHuman();
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
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
