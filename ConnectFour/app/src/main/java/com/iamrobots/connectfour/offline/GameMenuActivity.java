package com.iamrobots.connectfour.offline;

/**
 * Created by namrathamanjunatha on 1/26/18.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.iamrobots.connectfour.online.OnlineActivity;
import com.iamrobots.connectfour.R;
import com.iamrobots.connectfour.TopScoresActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

public class GameMenuActivity extends AppCompatActivity{

    private static final String FIRST_PLAYER = "Alice";
    private static final String SECOND_PLAYER = "Bob";
    private Button mOfflineButton;
    private Button mOnlineButton;
    private Button mTopScoresButton;

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_menu);

        mOfflineButton = findViewById(R.id.button_player);
        mOnlineButton = findViewById(R.id.button_multi);
        mTopScoresButton = findViewById(R.id.button_top_scores);

        mOfflineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GameMenuActivity.this, PlayerSelectActivity.class);
                i.putExtra(FIRST_PLAYER, FIRST_PLAYER);
                i.putExtra(SECOND_PLAYER, SECOND_PLAYER);
                i.putExtra("FROM_TAB", "");
                startActivity(i);
            }
        });

        mOnlineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GameMenuActivity.this, OnlineActivity.class);
                startActivity(i);
            }
        });

        mTopScoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GameMenuActivity.this, TopScoresActivity.class);
                startActivity(i);

            }
        });
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
            Intent intent1 = new Intent(GameMenuActivity.this, RulesActivity.class);
            this.startActivity(intent1);
            return true;
        }

        if (id == R.id.help_id) {
            Intent intent2 = new Intent(GameMenuActivity.this, FeedbackActivity.class);
            this.startActivity(intent2);
        }

        return super.onOptionsItemSelected(item);
    }
}
