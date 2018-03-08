package com.iamrobots.connectfour.online;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.iamrobots.connectfour.R;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.iamrobots.connectfour.TopScoresActivity;
import com.iamrobots.connectfour.offline.GameHomeActivity;
import com.iamrobots.connectfour.offline.GameMenuActivity;
import com.iamrobots.connectfour.offline.PlayerSelectActivity;

public class OnlineActivity extends AppCompatActivity {

    private Button mOnlineButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online);
        Toolbar toolbar = findViewById(R.id.toolbar_online);
        setSupportActionBar(toolbar);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.homeBottomBar);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_offline:
                        Intent i1 = new Intent(OnlineActivity.this, GameHomeActivity.class);
                        startActivity(i1);
                        break;
                    case R.id.ic_online:
                        break;
                    case R.id.ic_highscore:
                        Intent i3 = new Intent(OnlineActivity.this, TopScoresActivity.class);
                        startActivity(i3);
                        break;
                }

                return false;
            }
        });


        mOnlineButton = findViewById(R.id.button_play);

        mOnlineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(OnlineActivity.this, OnlinedemoActivity.class);
                startActivity(i);
            }
        });


    }
}
