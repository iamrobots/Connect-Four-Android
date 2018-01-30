package com.iamrobots.connectfour.PlayerSelection;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.content.Intent;
import android.widget.Spinner;

import com.iamrobots.connectfour.GamePlay.GameActivity;
import com.iamrobots.connectfour.R;

// TODO: Add a Start/Play button that brings you to the GameActivity!
// TODO: Implement Shared preferences to pass information to GameActivity!.
// TODO: Add member variables to hold the First and Second PlayerActivity information.
// TODO: Add member variables for number or rows and columns

public class PlayerSelectionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String FIRST_PLAYER_KEY = "PlayerOne";
    private static final String SECOND_PLAYER_KEY = "PlayerTwo";
    private static final String ROW_KEY = "Rows";
    private static final String COLUMNS_KEY = "Columns";

    private Button mPlayButton;
    private Spinner mBoardSizeSpinner;

    private String mFirstPlayerName;
    private String mSecondPlayerName;
    private int mRows;
    private int mColumns;

    private String[] mBoardSizeArray = {"7x6", "8x7", "10x8"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_selection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRows = 8;
        mColumns = 10;
        mFirstPlayerName = "Alice";
        mSecondPlayerName = "Bob";

        mBoardSizeSpinner = findViewById(R.id.board_size_spinner);
        mPlayButton = findViewById(R.id.play_button);


        mBoardSizeSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mBoardSizeArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBoardSizeSpinner.setAdapter(adapter);


        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(FIRST_PLAYER_KEY, mFirstPlayerName);
                editor.putString(SECOND_PLAYER_KEY, mSecondPlayerName);
                editor.putInt(ROW_KEY, mRows);
                editor.putInt(COLUMNS_KEY, mColumns);
                editor.apply();

                Intent i = new Intent(PlayerSelectionActivity.this, GameActivity.class);
                startActivity(i);
            }
        });


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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                mRows = 6;
                mColumns = 7;
                break;
            case 1:
                mRows = 7;
                mColumns = 8;
                break;
            case 2:
                mRows = 8;
                mColumns = 10;
                break;
            default:
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
