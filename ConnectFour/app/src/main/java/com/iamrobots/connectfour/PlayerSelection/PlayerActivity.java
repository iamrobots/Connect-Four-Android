package com.iamrobots.connectfour.PlayerSelection;

/**
 * Created by David Lively on 1/25/18.
 * lively@iamrobots.com
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



public class PlayerActivity extends AppCompatActivity {

    private AppDatabase db;

    private EditText pName;
    private EditText pColor;

    private Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // -- Add your xml part here
       // setContentView(R.layout.create_user);

        //pName = findViewById(R.id.player_name);
       // pColor = findViewById(R.id.player_color);
       // button = findViewById(R.id.button);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Player pdt = new Player(pName.getText().toString(), pColor.getText().toString());
                //db.playerDao().insertAll(pdt);
                //startActivity(new Intent(PlayerActivity.this, MainActivity.class));
            }
        });
    }
}
