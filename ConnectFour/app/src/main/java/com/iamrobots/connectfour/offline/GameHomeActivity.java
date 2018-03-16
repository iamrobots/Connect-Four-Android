package com.iamrobots.connectfour.offline;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.iamrobots.connectfour.R;
import com.iamrobots.connectfour.TopScoresActivity;
import com.iamrobots.connectfour.gamePlay.GameActivity;
import com.iamrobots.connectfour.online.OnlineActivity;

import java.lang.reflect.Field;

// Home screen of app, replaces GameMenuActivity. Not fully implemented.

public class GameHomeActivity extends AppCompatActivity {
    private static final String TAG = "GameHomeActivity";

    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_home);
        Log.d(TAG, "onCreate: Starting");

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.home_container);
        setupViewPager(mViewPager);

        Intent mIntent = getIntent();
        String fromTab= mIntent.getStringExtra("FROM_TAB");
        if(fromTab != null && fromTab.equals("ComputerTabFragment"))
        {
            mViewPager.setCurrentItem(1);
        }

        TabLayout tabs = (TabLayout) findViewById(R.id.home_tabs);
        tabs.setupWithViewPager(mViewPager);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.homeBottomBar);
        //BottomNavigationHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_offline:
                        break;
                    case R.id.ic_online:
                        Intent i2 = new Intent(GameHomeActivity.this, OnlineActivity.class);
                        startActivity(i2);
                        break;
                    case R.id.ic_highscore:
                        Intent i3 = new Intent(GameHomeActivity.this, TopScoresActivity.class);
                        startActivity(i3);
                        break;
                    case R.id.ic_help:
                        Intent i4 = new Intent(GameHomeActivity.this, RulesActivity.class);
                        startActivity(i4);
                        break;
                }

                return false;
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new TabFragmentHuman(), "Human");
        adapter.addFragment(new TabFragmentComputer(), "Computer");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.options_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.rules_id) {
            Intent intent1 = new Intent(GameHomeActivity.this, RulesActivity.class);
            this.startActivity(intent1);
            return true;
        }

        if (id == R.id.help_id) {
            Intent intent2 = new Intent(GameHomeActivity.this, FeedbackActivity.class);
            this.startActivity(intent2);
        }

        return super.onOptionsItemSelected(item);
    }
}