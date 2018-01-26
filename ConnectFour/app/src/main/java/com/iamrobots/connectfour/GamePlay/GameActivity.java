package com.iamrobots.connectfour.GamePlay;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.util.Pair;
import android.widget.TextView;
import android.widget.Toast;

import com.iamrobots.connectfour.R;

/*
 * TODO: Get player selection information from Share Preferences
 * TODO: Implement a back button that takes the user back to Player Selection
 * TODO: Implement multiple rounds.
 * TODO: Implement onPause and onResume
 */

public class GameActivity extends AppCompatActivity {

    // Game Layout Components
    TextView mFirstPlayerTextView;
    TextView mSecondPlayerTextView;
    TokenView mFirstPlayerToken;
    TokenView mSecondPlayerToken;
    BoardView mBoardView;
    ImageButton mRewindButton;

    // Game Model/State
    GameModel mGameModel;
    int mCurrentPlayer;
    boolean mGameOver;

//    int mRounds;
//    int mCurrentRound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Temporary Variables. Will get rows and columns from Player selection.
        int rows = 6;
        int columns = 7;
        String firstPlayerName = "Player 1";
        String secondPlayerName = "Player 2";
        int firstPlayerColor = Color.parseColor("#f1c40f");
        int secondPlayerColor = Color.parseColor("#e74c3c");


        mFirstPlayerTextView = findViewById(R.id.player1_id);
        mFirstPlayerTextView.setText(firstPlayerName);
        mFirstPlayerToken = findViewById(R.id.player1_token_id);
        mFirstPlayerToken.setColor(firstPlayerColor);

        mSecondPlayerTextView = findViewById(R.id.player2_id);
        mSecondPlayerTextView.setText(secondPlayerName);
        mSecondPlayerToken = findViewById(R.id.player2_token_id);
        mSecondPlayerToken.setColor(secondPlayerColor);

        mGameModel = new GameModel();
        mGameModel.setRows(rows);
        mGameModel.setColumns(columns);

        mBoardView = findViewById(R.id.boardView);
        mBoardView.setRowsColumns(rows, columns);
        mBoardView.setFirstPlayerColor(firstPlayerColor);
        mBoardView.setSecondPlayerColor(secondPlayerColor);

        mRewindButton = findViewById(R.id.rewindButton);

        mCurrentPlayer = 0;
        mGameOver = false;

        mBoardView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.performClick();

                if (!mGameOver) {
                    return false;
                }

                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        viewClicked(event.getX(), event.getY());
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

    public void viewClicked(float x, float y) {
        int column = mBoardView.getColumn(x);
        int row = mBoardView.getRow(y);

        if (row < 0 || column < 0)
            return;

        playToken(row, column);
    }

    public void playToken(int row, int column) {
        if (!mGameModel.dropToken(column)) {
            // Invalid position
            return;
        }
        mBoardView.dropToken(row, column, mCurrentPlayer);

        switch (mGameModel.getGameState()) {
            case 0: // Game is in play
                gameInPlay();
                break;

            case 1:  // Game is won
                gameWon();
                break;

            case 2:  // Game is draw
                mGameOver = true;
                Toast.makeText(this, "The game is a draw", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void gameInPlay() {

        mCurrentPlayer = (mCurrentPlayer == 0) ? 1 : 0;

        if (mCurrentPlayer == 0) {
            mFirstPlayerToken.selected();
            mSecondPlayerToken.unselected();
        } else {
            mSecondPlayerToken.selected();
            mFirstPlayerToken.unselected();
        }
    }

    public void gameWon() {

        mGameOver = true;
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
        mGameOver = false;
        mCurrentPlayer = 0;
    }

}
