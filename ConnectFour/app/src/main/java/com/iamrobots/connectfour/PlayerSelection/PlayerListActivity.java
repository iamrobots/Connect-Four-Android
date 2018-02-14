package com.iamrobots.connectfour.PlayerSelection;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.iamrobots.connectfour.R;

import java.util.ArrayList;

//import android.support.v7.app.AppCompatActivity;

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

        // Temporary player names
        String[] testPlayers = new String[] { "Alice", "Bob", "Jane", "Mei" };

        if (getIntent().getStringExtra(FROM_BUTTON_KEY).equals(FIRST_PLAYER_KEY)) {
            mPlayerKey = FIRST_PLAYER_KEY;
        } else {
            mPlayerKey = SECOND_PLAYER_KEY;
        }

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        mCurrentPlayerName = preferences.getString(mPlayerKey, "");

        //db = AppDatabase.getInstance(this.getApplicationContext());
        //playersList = db.playerDao().getAll();

        mPlayerListView = findViewById(R.id.playerList);

        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < testPlayers.length; ++i) {
            if (mCurrentPlayerName.equals(testPlayers[i]))
                continue;
            list.add(testPlayers[i]);
        }

        ArrayAdapter<String> playerListAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_list_item_1, list);
        mPlayerListView.setAdapter(playerListAdapter);

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
}
