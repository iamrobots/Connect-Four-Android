package com.iamrobots.connectfour.PlayerSelection;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.iamrobots.connectfour.GamePlay.GameActivity;
import com.iamrobots.connectfour.R;

public class PlayerSelectionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String FIRST_PLAYER_KEY = "PlayerOne";
    private static final String SECOND_PLAYER_KEY = "PlayerTwo";
    private static final String ROW_KEY = "Rows";
    private static final String COLUMNS_KEY = "Columns";
    private static final String ROUNDS_KEY = "Rounds";

    private Button mPlayButton;
    private Button mFirstPlayerButton;
    private Button mSecondPlayerButton;
    private Spinner mBoardSizeSpinner;
    private Spinner mRoundsSpinner;

    private String mFirstPlayerName;
    private String mSecondPlayerName;
    private int mRows;
    private int mColumns;
    private int mRounds;

    private String[] mBoardSizeArray = {"7 x 6", "8 x 7", "10 x 8"};
    private String[] mRoundsArray = {"1", "2", "3", "4", "5"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_selection);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRows = 8;
        mColumns = 10;
        mFirstPlayerName = "Alice";
        mSecondPlayerName = "Bob";
        mRounds = 1;

        mFirstPlayerButton = findViewById(R.id.btnPlayer1);
        mSecondPlayerButton = findViewById(R.id.btnPlayer2);
        mBoardSizeSpinner = findViewById(R.id.board_size_spinner);
        mRoundsSpinner = findViewById(R.id.rounds_spinner);
        mPlayButton = findViewById(R.id.play_button);

        mFirstPlayerButton.setText(mFirstPlayerName);
        mSecondPlayerButton.setText(mSecondPlayerName);

        spinnerSetup();

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(FIRST_PLAYER_KEY, mFirstPlayerName);
                editor.putString(SECOND_PLAYER_KEY, mSecondPlayerName);
                editor.putInt(ROW_KEY, mRows);
                editor.putInt(COLUMNS_KEY, mColumns);
                editor.putInt(ROUNDS_KEY, mRounds);
                editor.apply();

                Intent i = new Intent(PlayerSelectionActivity.this, GameActivity.class);
                startActivity(i);
            }
        });

        mFirstPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PlayerSelectionActivity.this, PlayerListActivity.class);
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


    /*
     * Spinner Methods
     */

    public void spinnerSetup() {
        mBoardSizeSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> boardSizeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mBoardSizeArray);
        boardSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBoardSizeSpinner.setAdapter(boardSizeAdapter);

        mRoundsSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> roundsAdaptor = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mRoundsArray);
        roundsAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mRoundsSpinner.setAdapter(roundsAdaptor);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (parent.getId() == R.id.board_size_spinner) {
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
                    mRows = 6;
                    mColumns = 7;
            }
        } else if (parent.getId() == R.id.rounds_spinner) {
            mRounds = position + 1;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
