package com.iamrobots.connectfour.PlayerSelection;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.iamrobots.connectfour.R;

import java.util.ArrayList;
import java.util.List;

public class PlayerListActivity extends AppCompatActivity {

    private static final String FIRST_PLAYER_KEY = "PlayerOne";
    private static final String SECOND_PLAYER_KEY = "PlayerTwo";
    private static final String FROM_BUTTON_KEY = "From";

    private ListView mPlayerListView;
    private String mPlayerKey;
    private String mCurrentPlayerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_list);

        if (getIntent().getStringExtra(FROM_BUTTON_KEY).equals(FIRST_PLAYER_KEY)) {
            mPlayerKey = FIRST_PLAYER_KEY;
        } else {
            mPlayerKey = SECOND_PLAYER_KEY;
        }

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        mCurrentPlayerName = preferences.getString(mPlayerKey, null);

        mPlayerListView = findViewById(R.id.playerList);

        LoadPlayerNames loader = new LoadPlayerNames();
        loader.execute();

        // clicking list item should either mark the item or switch back to
        // PlayerSelectionActivity with button updated.
        mPlayerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                String name = (String) parent.getItemAtPosition(position);

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(mPlayerKey, name);
                editor.apply();

                Intent i = new Intent(PlayerListActivity.this, PlayerSelectionActivity.class);
                startActivity(i);
            }

        });
    }

    private class LoadPlayerNames extends AsyncTask<Void, Void, List<Player>> {

        AppDatabase db = AppDatabase.getInstance(PlayerListActivity.this);

        @Override
        protected List<Player> doInBackground(Void... voids) {
            return db.playerDao().getPlayers();
        }

        @Override
        protected void onPostExecute(List<Player> players) {
            super.onPostExecute(players);

            ArrayList<String> playerList = new ArrayList<>();

            for (Player player : players) {
                playerList.add(player.getName());
            }

            ArrayAdapter<String> playerListAdapter = new ArrayAdapter<String>(PlayerListActivity.this,
                    android.R.layout.simple_list_item_1, playerList);
            mPlayerListView.setAdapter(playerListAdapter);
        }
    }
}
