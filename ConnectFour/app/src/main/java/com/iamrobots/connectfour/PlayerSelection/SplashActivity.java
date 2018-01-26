package com.iamrobots.connectfour.PlayerSelection;

/**
 * Created by namrathamanjunatha on 1/25/18.
 */

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.iamrobots.connectfour.R;


public class SplashActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_splash);
            new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    // Start your app main activity
                    Intent i = new Intent(SplashActivity.this, GameMenuActivity.class);
                    startActivity(i);

                    // close this activity
                    finish();
                }
            }, 1000);
        }
}

