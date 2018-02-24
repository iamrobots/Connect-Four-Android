package com.iamrobots.connectfour.offline;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.iamrobots.connectfour.R;
import com.iamrobots.connectfour.database.AppDatabase;
import com.iamrobots.connectfour.database.Player;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class PlayerListActivity extends AppCompatActivity {

    private static final String TAG = "PlayerListActivity";

    private static final String FIRST_PLAYER_KEY = "PlayerOne";
    private static final String SECOND_PLAYER_KEY = "PlayerTwo";
    private static final String FROM_BUTTON_KEY = "From";

    protected AppDatabase mDatabase;

    private FloatingActionButton mAddPlayerFab;
    private ListView mPlayerListView;
    private String mPlayerKey;
    private String mCurrentPlayerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_list);
        mDatabase = AppDatabase.getInstance(PlayerListActivity.this);
        mPlayerListView = findViewById(R.id.playerList);
        mAddPlayerFab = findViewById(R.id.add_player_fab);

        if (getIntent().getStringExtra(FROM_BUTTON_KEY).equals(FIRST_PLAYER_KEY)) {
            mPlayerKey = FIRST_PLAYER_KEY;
        } else {
            mPlayerKey = SECOND_PLAYER_KEY;
        }

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        mCurrentPlayerName = preferences.getString(mPlayerKey, null);

        LoadPlayerNames loader = new LoadPlayerNames(PlayerListActivity.this);
        loader.execute();

        mPlayerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                mCurrentPlayerName = (String) parent.getItemAtPosition(position);

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(mPlayerKey, mCurrentPlayerName);
                editor.apply();

                // return to no tabs
                /*Intent i = new Intent(PlayerListActivity.this, PlayerSelectionActivity.class);
                startActivity(i);*/

                // return to tabs
                Intent i = new Intent(PlayerListActivity.this, PlayerSelectActivity.class);
                startActivity(i);
            }

        });

        // add player with activity
        /*
        mAddPlayerFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PlayerListActivity.this, AddPlayerActivity.class);
                i.putExtra(FROM_BUTTON_KEY, mPlayerKey);
                startActivity(i);
            }
        });
        */

        // add player with dialog
        mAddPlayerFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: opening dialog.");

                AddPlayerDialog dialog = new AddPlayerDialog();
                dialog.show(getFragmentManager(), "AddPlayerDialog");
            }
        });
    }

    protected void updateListView(ArrayList<String> playerList) {
        ArrayAdapter<String> playerListAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, playerList);
        mPlayerListView.setAdapter(playerListAdapter);
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        LoadPlayerNames loader = new LoadPlayerNames(PlayerListActivity.this);
        loader.execute();
    }

    private static class LoadPlayerNames extends AsyncTask<Void, Void, List<Player>> {

        WeakReference<PlayerListActivity> mActivityReference;

        LoadPlayerNames(PlayerListActivity context) {
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

            mActivityReference.get().updateListView(playerList);
        }
    }
}
