package com.iamrobots.connectfour;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.iamrobots.connectfour.database.AppDatabase;
import com.iamrobots.connectfour.database.Player;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TopScoresActivity extends AppCompatActivity {

    private AppDatabase mDatabase;
    private ListView mTopScoresView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_scores);
        mDatabase = AppDatabase.getInstance(TopScoresActivity.this);
    }

    private static class LoadPlayerNames extends AsyncTask<Void, Void, List<Player>> {

        WeakReference<TopScoresActivity> mActivityReference;

        LoadPlayerNames(TopScoresActivity context) {
            mActivityReference = new WeakReference<>(context);
        }

        @Override
        protected List<Player> doInBackground(Void... voids) {

            return mActivityReference.get().mDatabase.playerDao().getPlayers();
        }

        @Override
        protected void onPostExecute(List<Player> players) {
            super.onPostExecute(players);

            ArrayList<String> playerList = new ArrayList<>();

            for (Player player : players) {
                playerList.add(player.getName());
            }

            Collections.sort(playerList);

            mActivityReference.get().updateListView(playerList);
        }
    }

    private void updateListView(ArrayList<String> playerList) {
        ArrayAdapter<String> playerListAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, playerList);
        mTopScoresView.setAdapter(playerListAdapter);
    }
}
