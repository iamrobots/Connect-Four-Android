package com.iamrobots.connectfour.online;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.iamrobots.connectfour.R;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.iamrobots.connectfour.offline.GameMenuActivity;
import com.iamrobots.connectfour.offline.PlayerSelectActivity;

public class OnlineActivity extends AppCompatActivity {

    private Button mOnlineButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online);




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
