package com.iamrobots.connectfour.PlayerSelection;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.iamrobots.connectfour.R;

import java.util.ArrayList;

//import android.support.v7.app.AppCompatActivity;

public class PlayerListActivity extends AppCompatActivity {

    //private LiveData<List<Player>> playersList = null;
    //private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_list);

        //db = AppDatabase.getInstance(this.getApplicationContext());
        //playersList = db.playerDao().getAll();

        ListView plyrList = findViewById(R.id.playerList);
        String[] testPlayers = new String[] { "Alice", "Bob", "Jane", "Mei" };

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < testPlayers.length; ++i) {
            list.add(testPlayers[i]);
        }

        ArrayAdapter<String> playerListAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_list_item_1, list);
        plyrList.setAdapter(playerListAdapter);

        // clicking list item should either mark the item or switch back to
        // PlayerSelectionActivity with button updated.
        plyrList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                // mark selection and pass to PlayerSelectionActivity
                //Button mFirstPlyrBtn = findViewById(R.id.btnPlayer1);
                //mFirstPlyrBtn.setText(item);

                //Intent i = new Intent(PlayerListActivity.this, PlayerSelectionActivity.class);
                //startActivity(i);
            }

        });
    }
}
