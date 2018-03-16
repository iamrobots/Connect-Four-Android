package com.iamrobots.connectfour;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.iamrobots.connectfour.database.AppDatabase;
import com.iamrobots.connectfour.database.Player;
import com.iamrobots.connectfour.offline.BottomNavigationHelper;
import com.iamrobots.connectfour.offline.GameHomeActivity;
import com.iamrobots.connectfour.offline.RulesActivity;
import com.iamrobots.connectfour.online.OnlineActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TopScoresActivity extends AppCompatActivity {

    private ArrayList<Player> mPlayerArray;
    private AppDatabase mDatabase;
    private RecyclerView mTopScoresRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_scores);
        Toolbar toolbar = findViewById(R.id.toolbar_scores);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // uncomment for back button

        mPlayerArray = new ArrayList<>();

        mTopScoresRecyclerView = findViewById(R.id.top_scores_recycler_view);

        mDatabase = AppDatabase.getInstance(TopScoresActivity.this);
        LoadPlayerNames loader = new LoadPlayerNames(this);
        loader.execute();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.homeBottomBar);
        //BottomNavigationHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_offline:
                        Intent i1 = new Intent(TopScoresActivity.this, GameHomeActivity.class);
                        startActivity(i1);
                        break;
                    case R.id.ic_online:
                        Intent i2 = new Intent(TopScoresActivity.this, OnlineActivity.class);
                        startActivity(i2);
                        break;
                    case R.id.ic_highscore:
                        break;
                    case R.id.ic_help:
                        Intent i4 = new Intent(TopScoresActivity.this, RulesActivity.class);
                        startActivity(i4);
                        break;
                }

                return false;
            }
        });
    }

    private static class LoadPlayerNames extends AsyncTask<Void, Void, List<Player>> {

        WeakReference<TopScoresActivity> mActivityReference;

        LoadPlayerNames(TopScoresActivity context) {
            mActivityReference = new WeakReference<>(context);
        }

        @Override
        protected List<Player> doInBackground(Void... voids) {

            return mActivityReference.get().mDatabase.playerDao().getTopScores();
        }

        @Override
        protected void onPostExecute(List<Player> players) {
            super.onPostExecute(players);


            for (Player player : players) {
                mActivityReference.get().mPlayerArray.add(player);
            }


            mActivityReference.get().updateListView();
        }
    }

    private void updateListView() {
        TopScoresAdaptor adaptor = new TopScoresAdaptor(this, mPlayerArray);
        mTopScoresRecyclerView.setAdapter(adaptor);
        mTopScoresRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
