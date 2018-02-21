/*
 *  Used by PlayerSelectActivity. Not fully implemented.
 */

package com.iamrobots.connectfour.offline;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import com.iamrobots.connectfour.R;
import com.iamrobots.connectfour.gamePlay.GameActivity;

//import com.iamrobots.connectfour.GamePlay.GameActivity;

/**
 * Created by Abraham on 2/11/18.
 */

public class TabFragmentHuman extends Fragment {


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_human_tab, container, false);

    //@Override
    //protected void onCreate(Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_player_selection);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        final View rootView = inflater.inflate(R.layout.fragment_human_tab, container, false);

        mRows = 8;
        mColumns = 10;
        mFirstPlayerName = "Alice";
        mSecondPlayerName = "Bob";
        mRounds = 1;

        mFirstPlayerButton = rootView.findViewById(R.id.human_btn1);
        mSecondPlayerButton = rootView.findViewById(R.id.human_btn2);
        mBoardSizeSpinner = rootView.findViewById(R.id.human_board_spinner);
        mRoundsSpinner = rootView.findViewById(R.id.human_rounds_spinner);
        mPlayButton = rootView.findViewById(R.id.human_play_btn);

        mFirstPlayerButton.setText(mFirstPlayerName);
        mSecondPlayerButton.setText(mSecondPlayerName);

        //spinnerSetup();

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(FIRST_PLAYER_KEY, mFirstPlayerName);
                editor.putString(SECOND_PLAYER_KEY, mSecondPlayerName);
                editor.putInt(ROW_KEY, mRows);
                editor.putInt(COLUMNS_KEY, mColumns);
                editor.putInt(ROUNDS_KEY, mRounds);
                editor.apply();

                Intent i = new Intent(getActivity(), GameActivity.class);
                startActivity(i);
            }
        });

        mFirstPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), PlayerListActivity.class);
                startActivity(i);
            }
        });

        return inflater.inflate(R.layout.fragment_human_tab, container, false);
    }

    /*
     * Spinner Methods
     */
/*
    public void spinnerSetup() {
        //mBoardSizeSpinner.setOnItemSelectedListener(this);
        mBoardSizeSpinner.setOnItemSelectedListener();

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
    }*/
}
