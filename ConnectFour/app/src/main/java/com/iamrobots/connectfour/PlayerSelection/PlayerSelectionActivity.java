package com.iamrobots.connectfour.PlayerSelection;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

import com.iamrobots.connectfour.R;

// TODO: Add a Start/Play button that brings you to the GameActivity!
// TODO: Implement Shared preferences to pass information to GameActivity!.
// TODO: Add member variables to hold the First and Second Player information.
// TODO: Add member variables for number or rows and columns

public class PlayerSelectionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_selection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        /*Button btnPlayer1 = (Button)findViewById(R.id.btnPlayer1);
        btnPlayer1.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {
               Intent i = new Intent(PlayerSelectionActivity.this, GameActivity.class);
               startActivity(i);
           }
        });

        Button btnPlayer2 = (Button)findViewById(R.id.btnPlayer2);
        btnPlayer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PlayerSelectionActivity.this, GameActivity.class);
                startActivity(i);
            }
        });*/

        /*Button addPlyr = (Button)findViewById(R.id.fab);
        addPlyr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PlayerSelectionActivity.this, NewPlayerActivity.class);
                startActivity(i);
            }
        });*/
    }

}
