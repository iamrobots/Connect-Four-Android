package com.iamrobots.connectfour;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.iamrobots.connectfour.database.AppDatabase;
import com.iamrobots.connectfour.database.Player;

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPlayerArray = new ArrayList<>();

        mTopScoresRecyclerView = findViewById(R.id.top_scores_recycler_view);

        mDatabase = AppDatabase.getInstance(TopScoresActivity.this);
        LoadPlayerNames loader = new LoadPlayerNames(this);
        loader.execute();
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
        Collections.sort(mPlayerArray, Collections.reverseOrder());

        TopScoresAdaptor adaptor = new TopScoresAdaptor(this, mPlayerArray);
        mTopScoresRecyclerView.setAdapter(adaptor);
        mTopScoresRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
