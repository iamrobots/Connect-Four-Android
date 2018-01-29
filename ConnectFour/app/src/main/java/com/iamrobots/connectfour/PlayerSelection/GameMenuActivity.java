package com.iamrobots.connectfour.PlayerSelection;

/**
 * Created by namrathamanjunatha on 1/26/18.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.iamrobots.connectfour.R;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class GameMenuActivity extends AppCompatActivity{

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_menu);

        Button btnOffline = (Button)findViewById(R.id.button_player);

        btnOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GameMenuActivity.this, PlayerSelectionActivity.class);
                startActivity(i);
            }
        });
    }

}
