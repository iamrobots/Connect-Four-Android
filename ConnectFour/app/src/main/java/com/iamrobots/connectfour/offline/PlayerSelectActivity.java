/*
 *  Alternate to PlayerSelectionActivity that uses tabs to show human and ai modes.
 *  Not fully implemented.
 */

package com.iamrobots.connectfour.offline;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.iamrobots.connectfour.R;

public class PlayerSelectActivity extends AppCompatActivity {
    private static final String TAG = "PlayerSelectActivity";

    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_select);
        Log.d(TAG, "onCreate: Starting");

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        Intent mIntent = getIntent();
        String fromTab= mIntent.getStringExtra("FROM_TAB");
        if(fromTab != null && fromTab.equals("ComputerTabFragment"))
        {
            mViewPager.setCurrentItem(1);
        }

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new TabFragmentHuman(), "Human");
        adapter.addFragment(new TabFragmentComputer(), "Computer");
        viewPager.setAdapter(adapter);
    }
}









