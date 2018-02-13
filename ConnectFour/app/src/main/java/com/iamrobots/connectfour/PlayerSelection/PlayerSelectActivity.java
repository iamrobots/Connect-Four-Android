/*
 *  Alternate to PlayerSelectionActivity that uses tabs to show human and ai modes.
 *  Not fully implemented.
 */

package com.iamrobots.connectfour.PlayerSelection;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;

import com.iamrobots.connectfour.R;

public class PlayerSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_select);

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        ViewPager mPager = (ViewPager) findViewById(R.id.container);

        ViewPagerAdapter mPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),PlayerSelectActivity.this);
        mPager.setAdapter(mPagerAdapter);
        tabLayout.setupWithViewPager(mPager);

        //set first tab
        View tab1 = (View) LayoutInflater.from(this).inflate()
                //in process

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        tabs.addTab(tabs.newTab().setText("Human"));
        tabs.addTab(tabs.newTab().setText("Computer"));
        //tabs.setTabGravity(TabLayout.GRAVITY_FILL);

        //final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_player_select, menu);
        return true;
    }*/

    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
