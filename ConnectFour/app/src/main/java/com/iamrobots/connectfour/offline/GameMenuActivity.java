package com.iamrobots.connectfour.offline;

/**
 * Created by namrathamanjunatha on 1/26/18.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.iamrobots.connectfour.online.OnlineActivity;
import com.iamrobots.connectfour.R;
import com.iamrobots.connectfour.TopScoresActivity;

import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class GameMenuActivity extends AppCompatActivity{

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
                Intent i = new Intent(GameMenuActivity.this, PlayerSelectionActivity.class);
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

}
