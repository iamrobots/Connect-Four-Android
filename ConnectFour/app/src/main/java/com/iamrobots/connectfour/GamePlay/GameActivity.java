package com.iamrobots.connectfour.GamePlay;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.util.Pair;
import android.widget.TextView;
import android.widget.Toast;

import com.iamrobots.connectfour.R;

/*
 * TODO: Get player selection information from Share Preferences
 * TODO: Implement a back button that takes the user back to PlayerActivity Selection
 * TODO: Figure out Database access.
 * TODO: Implement multiple rounds.
 * TODO: Implement onPause and onResume
 */

public class GameActivity extends AppCompatActivity {

    private static final String FIRST_PLAYER_KEY = "PlayerOne";
    private static final String SECOND_PLAYER_KEY = "PlayerTwo";
    private static final String ROW_KEY = "Rows";
    private static final String COLUMNS_KEY = "Columns";

    // Game Layout Components
    private TextView mFirstPlayerTextView;
    private TextView mSecondPlayerTextView;
    private TokenView mFirstPlayerToken;
    private TokenView mSecondPlayerToken;
    private BoardView mBoardView;
    private ImageButton mRewindButton;

    // Game Model/State
    private GameModel mGameModel;
    private int mCurrentPlayer;

    // PlayerActivity information
    private String mFirstPlayerName;
    private String mSecondPlayerName;

//    int mRounds;
//    int mCurrentRound;

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

        mRewindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rewind();
            }
        });
    }

    public void gameInPlay(float x, float y) {

        //Get the current player from getCurrentPlayer().
        mCurrentPlayer = (mCurrentPlayer == 0) ? 1 : 0;

        int column = mBoardView.getColumn(x);
        int row = mBoardView.getRow(y);

        if (row < 0 || column < 0)
            return;

        if (!mGameModel.dropToken(column)) {
            // Invalid position
            return;
        }
        mBoardView.dropToken(row, column, mCurrentPlayer);

        if (mGameModel.getGameState() == 1) {
            gameWon();
        }

        if (mCurrentPlayer == 0) {
            mFirstPlayerToken.selected();
            mSecondPlayerToken.unselected();
        } else {
            mSecondPlayerToken.selected();
            mFirstPlayerToken.unselected();
        }
    }

    public void gameWon() {

        String winner;
        mBoardView.highlightTokens(mGameModel.getWinners(), mCurrentPlayer);
        if (mCurrentPlayer == 0)
            winner = mFirstPlayerTextView.getText().toString();
        else
            winner = mSecondPlayerTextView.getText().toString();
        Toast.makeText(this, "The winner is" + winner, Toast.LENGTH_SHORT).show();
    }

    public void rewind() {
        Pair<Integer, Integer> rowColumn = mGameModel.rewind();
        if (rowColumn != null) {
            mBoardView.removeToken(rowColumn.first, rowColumn.second);
        }
    }

    public void newGame() {
        mGameModel.reset();
        mBoardView.clear();
        mCurrentPlayer = 0;
        mFirstPlayerToken.selected();
        mSecondPlayerToken.unselected();
    }

    private void setup() {
        String firstPlayerName;
        String secondPlayerName;

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        mFirstPlayerName = preferences.getString(FIRST_PLAYER_KEY, "One");
        mSecondPlayerName = preferences.getString(SECOND_PLAYER_KEY, "Two");
        int rows = preferences.getInt(ROW_KEY, 6);
        int columns = preferences.getInt(COLUMNS_KEY, 7);

        // Temporary Variables. Will get rows and columns from PlayerActivity selection.
        int firstPlayerColor = Color.parseColor("#f1c40f");
        int secondPlayerColor = Color.parseColor("#e74c3c");


        mFirstPlayerTextView = findViewById(R.id.player1_id);
        mFirstPlayerTextView.setText(mFirstPlayerName);
        mFirstPlayerToken = findViewById(R.id.player1_token_id);
        mFirstPlayerToken.setColor(firstPlayerColor);
        mFirstPlayerToken.selected();

        mSecondPlayerTextView = findViewById(R.id.player2_id);
        mSecondPlayerTextView.setText(mSecondPlayerName);
        mSecondPlayerToken = findViewById(R.id.player2_token_id);
        mSecondPlayerToken.setColor(secondPlayerColor);
        mSecondPlayerToken.unselected();

        mGameModel = new GameModel(rows,columns);

        mBoardView = findViewById(R.id.boardView);
        mBoardView.setRowsColumns(rows, columns);
        mBoardView.setFirstPlayerColor(firstPlayerColor);
        mBoardView.setSecondPlayerColor(secondPlayerColor);

        mRewindButton = findViewById(R.id.rewindButton);

        mCurrentPlayer = 0;
    }

}
