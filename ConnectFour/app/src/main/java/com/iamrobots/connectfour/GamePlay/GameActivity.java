package com.iamrobots.connectfour.GamePlay;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.util.Pair;
import android.widget.TextView;
import android.widget.Toast;

import com.iamrobots.connectfour.R;

/*
 * TODO: Implement a back button that takes the user back to PlayerActivity Selection
 * TODO: Figure out Database access.
 * TODO: Implement onPause and onResume
 */

public class GameActivity extends AppCompatActivity {

    private static final String FIRST_PLAYER_KEY = "PlayerOne";
    private static final String SECOND_PLAYER_KEY = "PlayerTwo";
    private static final String ROW_KEY = "Rows";
    private static final String COLUMNS_KEY = "Columns";
    private static final String ROUNDS_KEY = "Rounds";

    // Game Layout Components
    private TextView mFirstPlayerTextView;
    private TextView mSecondPlayerTextView;
    private TokenView mFirstPlayerToken;
    private TokenView mSecondPlayerToken;
    private BoardView mBoardView;
    private ImageButton mRewindButton;
    private Button mRoundsButton;

    // Game Model/State
    private GameModel mGameModel;


    private int mRounds;
    private int mCurrentRound;
    private Boolean mRewindable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        setup();

        mBoardView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i("debug" , "calledactivity");
                v.performClick();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        Log.i("debug" , "about to call getGameState");

                        switch (mGameModel.getGameState()) {
                            case 0: // Game is in play
                                gameInPlay(event.getX(), event.getY());
                                break;

                            case 1:  // Game is won
                                gameWon();
                                break;

                            case 2:  // Game is draw
                                Toast.makeText(getApplicationContext(), "The game is a draw", Toast.LENGTH_SHORT).show();
                                break;
                        }
                        break;
                }
                return true;
            }
        });

        mRoundsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mGameModel.getGameState() > 0 && mCurrentRound < mRounds) {
                    mCurrentRound++;
                    newGame();
                }
            }
        });

        mRewindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rewind();
            }
        });
    }

    public void gameInPlay(float x, float y) {

        Pair<Integer,Integer> coordinates;
        int column = mBoardView.getColumn(x);
        int row = mBoardView.getRow(y);

        mRewindable = true;

        if (row < 0 || column < 0)
            return;

        int player = mGameModel.getCurrentPlayer();
        coordinates = mGameModel.dropToken(column);

        if (coordinates == null) {
            return;
        }

        mBoardView.dropToken(coordinates.first, coordinates.second, player);

        if (mGameModel.getGameState() == 1) {
            gameWon();
        }

        if (mGameModel.getCurrentPlayer() == 0) {
            mFirstPlayerToken.selected();
            mSecondPlayerToken.unselected();
        } else {
            mSecondPlayerToken.selected();
            mFirstPlayerToken.unselected();
        }

        mRewindButton.setEnabled(mRewindable);
    }

    public void gameWon() {

        String winner;

        mRewindable = false;
        mBoardView.highlightTokens(mGameModel.getWinners(), mGameModel.getCurrentPlayer());
        if (mGameModel.getCurrentPlayer() == 0)
            winner = mFirstPlayerTextView.getText().toString();
        else
            winner = mSecondPlayerTextView.getText().toString();
        Toast.makeText(this, winner + " is the winner!", Toast.LENGTH_SHORT).show();

        if (mCurrentRound < mRounds) {
            mRoundsButton.setText(R.string.next_round);
            mRoundsButton.setEnabled(true);
        }
    }

    public void rewind() {
        Pair<Integer, Integer> rowColumn = mGameModel.rewind();
        mRewindable = false;
        mRewindButton.setEnabled(mRewindable);

        if (mGameModel.getCurrentPlayer() == 0) {
            mFirstPlayerToken.selected();
            mSecondPlayerToken.unselected();
        } else {
            mSecondPlayerToken.selected();
            mFirstPlayerToken.unselected();
        }

        if (rowColumn != null) {
            mBoardView.removeToken(rowColumn.first, rowColumn.second);
        }
    }

    @SuppressLint("SetTextI18n")
    public void newGame() {
        mGameModel.reset();
        mBoardView.clear();
        mFirstPlayerToken.selected();
        mSecondPlayerToken.unselected();
        mRoundsButton.setText("Round " + mCurrentRound + "/" + mRounds);
        mRoundsButton.setTextColor(Color.BLACK);
        mRoundsButton.setEnabled(false);
        mRewindable = false;
        mRewindButton.setEnabled(mRewindable);
    }

    @SuppressLint("SetTextI18n")
    private void setup() {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String firstPlayerName = preferences.getString(FIRST_PLAYER_KEY, "Player 1");
        String secondPlayerName = preferences.getString(SECOND_PLAYER_KEY, "Player 2");
        int rows = preferences.getInt(ROW_KEY, 6);
        int columns = preferences.getInt(COLUMNS_KEY, 7);
        mRounds = preferences.getInt(ROUNDS_KEY, 1);
        mCurrentRound = 1;

        // Temporary Variables. Will get rows and columns from PlayerActivity selection.
        int firstPlayerColor = Color.parseColor("#f1c40f");
        int secondPlayerColor = Color.parseColor("#e74c3c");


        mFirstPlayerTextView = findViewById(R.id.player1_id);
        mFirstPlayerTextView.setText(firstPlayerName);
        mFirstPlayerToken = findViewById(R.id.player1_token_id);
        mFirstPlayerToken.setColor(firstPlayerColor);
        mFirstPlayerToken.selected();

        mSecondPlayerTextView = findViewById(R.id.player2_id);
        mSecondPlayerTextView.setText(secondPlayerName);
        mSecondPlayerToken = findViewById(R.id.player2_token_id);
        mSecondPlayerToken.setColor(secondPlayerColor);
        mSecondPlayerToken.unselected();

        mGameModel = new GameModel(rows,columns);

        mBoardView = findViewById(R.id.boardView);
        mBoardView.setRowsColumns(rows, columns);
        mBoardView.setFirstPlayerColor(firstPlayerColor);
        mBoardView.setSecondPlayerColor(secondPlayerColor);

        mRoundsButton = findViewById(R.id.roundsButton);
        mRoundsButton.setText("Round " + mCurrentRound + "/" + mRounds);
        mRoundsButton.setTextColor(Color.BLACK);
        mRoundsButton.setEnabled(false);

        mRewindable = false;
        mRewindButton = findViewById(R.id.rewindButton);
        mRewindButton.setEnabled(mRewindable);
//        mRewindButton.setVisibility(View.GONE);
    }

}
