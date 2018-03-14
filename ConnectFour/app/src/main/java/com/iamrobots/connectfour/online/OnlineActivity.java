package com.iamrobots.connectfour.online;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.iamrobots.connectfour.R;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.iamrobots.connectfour.TopScoresActivity;
import com.iamrobots.connectfour.gamePlay.AIGameActivity;
import com.iamrobots.connectfour.offline.BottomNavigationHelper;
import com.iamrobots.connectfour.offline.GameHomeActivity;
import com.iamrobots.connectfour.offline.GameMenuActivity;
import com.iamrobots.connectfour.offline.PlayerListActivity;
import com.iamrobots.connectfour.offline.PlayerSelectActivity;
import com.iamrobots.connectfour.offline.RulesActivity;

public class OnlineActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "OnlineActivity";

    private static final String FIRST_PLAYER_KEY = "PlayerOne";
    private static final String SECOND_PLAYER_KEY = "Computer";
    private static final String ROW_KEY = "Rows";
    private static final String COLUMNS_KEY = "Columns";
    private static final String ROUNDS_KEY = "Rounds";
    private static final String FROM_BUTTON_KEY = "From";

    private Button mOnlineButton;

    private Spinner mBoardSizeSpinner;
    private Spinner mRoundsSpinner;

    private String mFirstPlayerName;
    private String mSecondPlayerName = "Computer";
    private EditText mPlayerName;
    private int mRows;
    private int mColumns;
    private int mRounds;

    private String[] mBoardSizeArray = {"7 x 6", "8 x 7", "10 x 8"};
    private String[] mRoundsArray = {"1", "2", "3", "4", "5"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online);
        Toolbar toolbar = findViewById(R.id.toolbar_online);
        setSupportActionBar(toolbar);


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        mFirstPlayerName = preferences.getString(FIRST_PLAYER_KEY, "Alice");
        mSecondPlayerName = preferences.getString(SECOND_PLAYER_KEY, "Bob");
        mRows = preferences.getInt(ROW_KEY, 6);
        mColumns = preferences.getInt(COLUMNS_KEY, 7);
        mRounds = preferences.getInt(ROUNDS_KEY, 1);

        mBoardSizeSpinner = findViewById(R.id.online_board_spinner);
        mRoundsSpinner = findViewById(R.id.online_rounds_spinner);

        spinnerSetup();

        mOnlineButton = findViewById(R.id.button_play);
        mPlayerName = findViewById(R.id.editText2);

        mOnlineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(OnlineActivity.this, OnlinedemoActivity.class);
                i.putExtra("PlayerName", mPlayerName.getText().toString());
                i.putExtra(ROW_KEY, mRows);
                i.putExtra(COLUMNS_KEY, mColumns);
                i.putExtra(ROUNDS_KEY, mRounds);
                startActivity(i);
            }
        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.homeBottomBar);
        BottomNavigationHelper.disableShiftMode(bottomNavigationView);
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
                    case R.id.ic_help:
                        Intent i4 = new Intent(OnlineActivity.this, RulesActivity.class);
                        startActivity(i4);
                        break;
                }

                return false;
            }
        });
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

        if (parent.getId() == R.id.online_board_spinner) {
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
        } else if (parent.getId() == R.id.online_rounds_spinner) {
            mRounds = position + 1;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
