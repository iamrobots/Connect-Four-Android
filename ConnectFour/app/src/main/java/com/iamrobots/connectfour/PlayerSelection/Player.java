package com.iamrobots.connectfour.PlayerSelection;

/**
 * Created by David Lively on 1/25/18.
 * lively@iamrobots.com
 */

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


// TODO: Rename this class to PlayerActivity or PlayerEditAcitvity.

public class Player extends AppCompatActivity {

    AppDatabase db;

    EditText pName;
    EditText pColor;

    Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // -- Add your xml part here
       // setContentView(R.layout.create_user);

        //pName = findViewById(R.id.player_name);
       // pColor = findViewById(R.id.player_color);
       // button = findViewById(R.id.button);

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "production")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // PlayerDetls pdt = new PlayerDetls(pName.getText().toString(), pColor.getText().toString());
                //db.playerDao().insertAll(pdt);
                //startActivity(new Intent(Player.this, MainActivity.class));
            }
        });
    }
}
