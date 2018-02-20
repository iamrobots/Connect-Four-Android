package com.iamrobots.connectfour.offline;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.iamrobots.connectfour.R;
import com.iamrobots.connectfour.database.AppDatabase;
import com.iamrobots.connectfour.database.Player;

import java.lang.ref.WeakReference;

public class AddPlayerActivity extends AppCompatActivity {

    private static final String FROM_BUTTON_KEY = "From";

    private String mReturnPlayerKey;
    private Button mAddButton;
    private EditText mPlayerName;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);

        mAddButton = findViewById(R.id.add_player_button);
        mPlayerName = findViewById(R.id.name_edit_text);
        mReturnPlayerKey = getIntent().getStringExtra(FROM_BUTTON_KEY);

        db = AppDatabase.getInstance(AddPlayerActivity.this);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mPlayerName.getText().toString().isEmpty()) {
                    Player player = new Player(mPlayerName.getText().toString(), Color.RED);
                    new AddPlayer(AddPlayerActivity.this, player).execute();
                } else {
                    endAdd();
                }
            }
        });
    }

    private void endAdd() {

        Intent i = new Intent(AddPlayerActivity.this, PlayerListActivity.class);
        i.putExtra(FROM_BUTTON_KEY, mReturnPlayerKey);
        startActivity(i);
    }

    private static class AddPlayer extends AsyncTask<Void, Void, Void> {

        private WeakReference<AddPlayerActivity> mActivityReference;
        private Player mPlayer;

        AddPlayer(AddPlayerActivity context, Player player) {
            mActivityReference = new WeakReference<>(context);
            mPlayer = player;
        }



        @Override
        protected Void doInBackground(Void... voids) {
            mActivityReference.get().db.playerDao().insertAll(mPlayer);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mActivityReference.get().endAdd();
        }
    }
}
