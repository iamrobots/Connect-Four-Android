package com.iamrobots.connectfour.gamePlay;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.iamrobots.connectfour.R;
import com.iamrobots.connectfour.database.AppDatabase;
import com.iamrobots.connectfour.database.Player;

public class AIGameActivity extends AppCompatActivity {

    private static final String TAG = AIGameActivity.class.getName();

    private static final String FIRST_PLAYER_KEY = "PlayerOne";
    private static final String SECOND_PLAYER_KEY = "PlayerTwo";
    private static final String ROW_KEY = "Rows";
    private static final String COLUMNS_KEY = "Columns";
    private static final String ROUNDS_KEY = "Rounds";

    // AI Layout Components
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
    private int mPlayerOneWins;
    private int mPlayerTwoWins;

    private AppDatabase db;

    private Player mHumanPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setup();
        mBoardView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.performClick();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        switch (mGameModel.getGameState()) {
                            case 0: // Game is in play
                                gameInPlay(event.getX(), event.getY());
                                break;

                            case 1:  // Game is won
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
                    // newGame();
                }
            }
        });}



    public void gameInPlay(float x, float y) {

        Log.d(TAG, "Game in play");
        Pair<Integer,Integer> coordinates;
        int column = mBoardView.getColumn(x);
        int row = mBoardView.getRow(y);

        //mRewindable = true;

        if (row < 0 || column < 0)
            return;

        coordinates = mGameModel.dropToken(column);

        if (coordinates == null) {
            return;
        }

        mBoardView.dropToken(coordinates.first, coordinates.second, 0);

        if (mGameModel.getGameState() == 1) {
            mBoardView.highlightTokens(mGameModel.getWinners(), mGameModel.getCurrentPlayer());
            gameWon();
            return;
        }

        if (mGameModel.getCurrentPlayer() == 0) {
            mFirstPlayerToken.selected();
            mSecondPlayerToken.unselected();
        } else {
            mSecondPlayerToken.selected();
            mFirstPlayerToken.unselected();
        }

        //mRewindButton.setEnabled(mRewindable);

        //create ai method
        column = mGameModel.AIdropToken(column);
        coordinates = mGameModel.dropToken(column);
        if (coordinates == null) {
            return;
        }

        mBoardView.dropToken(coordinates.first,coordinates.second,1);

        if (mGameModel.getGameState() == 1) {
            mBoardView.highlightTokens(mGameModel.getWinners(), mGameModel.getCurrentPlayer());
            gameWon();
        }

        if (mGameModel.getCurrentPlayer() == 0) {
            mFirstPlayerToken.selected();
            mSecondPlayerToken.unselected();
        } else {
            mSecondPlayerToken.selected();
            mFirstPlayerToken.unselected();
        }
    }

    public void gameWon() {

        String winner;

        mRewindable = false;
        if (mGameModel.getCurrentPlayer() == 0) {
            winner = mHumanPlayer.getName();
            mHumanPlayer.setWins(mHumanPlayer.getWins() + 1);
            mPlayerOneWins += 1;
            //mPlayerTwo.setLosses(mPlayerTwo.getDraws() + 1);
        }
        else {
            winner = "AI";
            //mPlayerTwo.setWins(mPlayerTwo.getWins() + 1);
            //mPlayerTwoWins += 1;
            mHumanPlayer.setLosses(mHumanPlayer.getDraws() + 1);
        }
//        mFirstPlayerTextView.setText(mPlayerOne.getName() + " (" + mPlayerOneWins + ")");
//        mSecondPlayerTextView.setText(mPlayerTwo.getName() + " (" + mPlayerTwoWins + ")");

        db.playerDao().updatePlayers(mHumanPlayer);
        Toast.makeText(this, winner + " is the winner!", Toast.LENGTH_SHORT).show();

        if (mCurrentRound < mRounds) {
            mRoundsButton.setText(R.string.next_round);
            mRoundsButton.setEnabled(true);
        }
        // else: show dialog to play again
        else {
            Log.d(TAG, "onClick: opening dialog.");

            PlayAgainDialog dialog = new PlayAgainDialog();
            dialog.show(getFragmentManager(), "AddPlayerDialog");
        }
    }



    private void setup() {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String firstPlayerName = preferences.getString(FIRST_PLAYER_KEY, "Player 1");
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
        mSecondPlayerTextView.setText("Computer");
        mSecondPlayerToken = findViewById(R.id.player2_token_id);
        mSecondPlayerToken.setColor(secondPlayerColor);
        mSecondPlayerToken.unselected();
        //set depth here
        mGameModel = new GameModel(rows,columns,11);

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

        db = AppDatabase.getInstance(this);
        mHumanPlayer = db.playerDao().getPlayerByName(firstPlayerName);
        mPlayerOneWins = 0;
        mPlayerTwoWins = 0;
    }

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
}
