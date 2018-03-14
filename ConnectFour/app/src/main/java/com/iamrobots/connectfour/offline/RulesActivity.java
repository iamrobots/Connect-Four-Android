package com.iamrobots.connectfour.offline;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.iamrobots.connectfour.R;
import com.iamrobots.connectfour.TopScoresActivity;
import com.iamrobots.connectfour.online.OnlineActivity;

/*
 * Shows rules for the game and allows user to submit feedback.
 * Replaces FeedbackActivity.
 */

public class RulesActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
        Toolbar toolbar = findViewById(R.id.toolbar_rules);
        //setSupportActionBar(toolbar);

        findViewById(R.id.btn_feedback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendFeedback();
            }
        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.homeBottomBar);
        BottomNavigationHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_offline:
                        Intent i1 = new Intent(RulesActivity.this, GameHomeActivity.class);
                        startActivity(i1);
                        break;
                    case R.id.ic_online:
                        Intent i2 = new Intent(RulesActivity.this, OnlineActivity.class);
                        startActivity(i2);
                        break;
                    case R.id.ic_highscore:
                        Intent i3 = new Intent(RulesActivity.this, TopScoresActivity.class);
                        startActivity(i3);
                        break;
                    case R.id.ic_help:
                        break;
                }

                return false;
            }
        });

    }

    private void sendFeedback() {
        final Intent _Intent = new Intent(android.content.Intent.ACTION_SEND);
        _Intent.setType("text/html");
        _Intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{ getString(R.string.mail_feedback_email) });
        _Intent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.mail_feedback_subject));
        _Intent.putExtra(android.content.Intent.EXTRA_TEXT, getString(R.string.mail_feedback_message));
        startActivity(Intent.createChooser(_Intent, getString(R.string.title_send_feedback)));
        //Toast.makeText(this, "Testing!", Toast.LENGTH_SHORT).show();

    }
}
