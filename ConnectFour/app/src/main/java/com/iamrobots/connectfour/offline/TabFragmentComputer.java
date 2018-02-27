/*
 *  Used by PlayerSelectActivity. Not fully implemented.
 */

package com.iamrobots.connectfour.offline;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.iamrobots.connectfour.R;
import com.iamrobots.connectfour.gamePlay.AIGameActivity;
import com.iamrobots.connectfour.gamePlay.GameActivity;

//import com.iamrobots.connectfour.GamePlay.GameActivity;

/**
 * Created by Abraham on 2/11/18.
 */

public class TabFragmentComputer extends Fragment implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "ComputerTabFragment";

    private static final String FIRST_PLAYER_KEY = "PlayerOne";
    private static final String SECOND_PLAYER_KEY = "Computer";
    private static final String ROW_KEY = "Rows";
    private static final String COLUMNS_KEY = "Columns";
    private static final String ROUNDS_KEY = "Rounds";
    private static final String FROM_BUTTON_KEY = "From";

    private Button mPlayButton;
    private Button mFirstPlayerButton;
    private Spinner mBoardSizeSpinner;
    private Spinner mRoundsSpinner;

    private String mFirstPlayerName;
    private String mSecondPlayerName = "Computer";
    private int mRows;
    private int mColumns;
    private int mRounds;

    private String[] mBoardSizeArray = {"7 x 6", "8 x 7", "10 x 8"};
    private String[] mRoundsArray = {"1", "2", "3", "4", "5"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: started");

        View view = inflater.inflate(R.layout.fragment_computer_tab, container, false);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        mFirstPlayerName = preferences.getString(FIRST_PLAYER_KEY, "Alice");
        mSecondPlayerName = preferences.getString(SECOND_PLAYER_KEY, "Bob");
        mRows = preferences.getInt(ROW_KEY, 6);
        mColumns = preferences.getInt(COLUMNS_KEY, 7);
        mRounds = preferences.getInt(ROUNDS_KEY, 1);

        mFirstPlayerButton = view.findViewById(R.id.comp_btn1);
        mBoardSizeSpinner = view.findViewById(R.id.comp_board_spinner);
        mRoundsSpinner = view.findViewById(R.id.comp_rounds_spinner);
        mPlayButton = view.findViewById(R.id.comp_play_btn);

        mFirstPlayerButton.setText(mFirstPlayerName);

        spinnerSetup();

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "Play Button clicked!", Toast.LENGTH_SHORT).show();
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(FIRST_PLAYER_KEY, mFirstPlayerName);
                editor.putString(SECOND_PLAYER_KEY, mSecondPlayerName);
                editor.putInt(ROW_KEY, mRows);
                editor.putInt(COLUMNS_KEY, mColumns);
                editor.putInt(ROUNDS_KEY, mRounds);
                editor.apply();

                Intent i = new Intent(getActivity(), AIGameActivity.class);
                startActivity(i);
            }
        });

        mFirstPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "Player Button clicked!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getActivity(), PlayerListActivity.class);
                i.putExtra(FROM_BUTTON_KEY, FIRST_PLAYER_KEY);
                i.putExtra("FROM_TAB", TAG);
                startActivity(i);
            }
        });

        return view;
    }

    /*
     * Spinner Methods
     */

    public void spinnerSetup() {
        mBoardSizeSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> boardSizeAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, mBoardSizeArray);
        boardSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBoardSizeSpinner.setAdapter(boardSizeAdapter);

        mRoundsSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> roundsAdaptor = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, mRoundsArray);
        roundsAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mRoundsSpinner.setAdapter(roundsAdaptor);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (parent.getId() == R.id.comp_board_spinner) {
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
        } else if (parent.getId() == R.id.comp_rounds_spinner) {
            mRounds = position + 1;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
