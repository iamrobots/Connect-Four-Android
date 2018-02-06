package com.iamrobots.connectfour.PlayerSelection;

import android.app.ListActivity;
import android.arch.lifecycle.LiveData;
import android.os.Bundle;

import com.iamrobots.connectfour.R;

import java.util.List;

public class PlayerListActivity extends ListActivity {

    private LiveData<List<Player>> playersList = null;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_list);

        db = AppDatabase.getInstance(this.getApplicationContext());
        playersList = db.playerDao().getAll();
    }
}
