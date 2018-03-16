package com.iamrobots.connectfour.gamePlay;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
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

import java.lang.ref.WeakReference;

public class AIGameActivity extends AppCompatActivity {

    private static final String TAG = AIGameActivity.class.getName();

    private static final String FIRST_PLAYER_KEY = "PlayerOne";
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
    private Boolean mAIPlaying;

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
                        if (mAIPlaying) {
                            return true;
                        }
                        switch (mGameModel.getGameState()) {
                            case 0: // Game is in play
                                gameInPlay(event.getX(), event.getY());
                                break;

                            case 1:  // Game is won
                                break;

                            case 2:  // Game is draw
                                if (mCurrentRound < mRounds) {
                                    mRoundsButton.setText(R.string.next_round);
                                    mRoundsButton.setEnabled(true);
                                }
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
        });}



    public void gameInPlay(float x, float y) {
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

        mAIPlaying = true;
        AIPlay playAI = new AIPlay(this);
        playAI.execute(column);
    }

    public void gameWon() {

        String winner;

        mRewindable = false;
        if (mGameModel.getCurrentPlayer() == 0) {
            winner = mHumanPlayer.getName();
            mHumanPlayer.setWins(mHumanPlayer.getWins() + 1);
            mPlayerOneWins += 1;
            mFirstPlayerToken.setScore(String.valueOf(mPlayerOneWins));
        }
        else {
            winner = "AI";
            mHumanPlayer.setLosses(mHumanPlayer.getDraws() + 1);
            mPlayerTwoWins += 1;
            mSecondPlayerToken.setScore(String.valueOf(mPlayerTwoWins));
        }

        db.playerDao().updatePlayers(mHumanPlayer);
        Toast.makeText(this, winner + " is the winner!", Toast.LENGTH_SHORT).show();

        if (mCurrentRound < mRounds) {
            mRoundsButton.setText(R.string.next_round);
            mRoundsButton.setEnabled(true);
        }
        // else: show dialog to play again, no longer used
        /*else {
            PlayAgainDialog dialog = new PlayAgainDialog();
            dialog.show(getFragmentManager(), "AddPlayerDialog");
        }*/
    }



    private void setup() {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String firstPlayerName = preferences.getString(FIRST_PLAYER_KEY, "Alice");
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
        mGameModel = new GameModel(rows,columns,10);

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
        mRewindButton.setVisibility(View.GONE);

        db = AppDatabase.getInstance(this);
        mHumanPlayer = db.playerDao().getPlayerByName(firstPlayerName);
        mPlayerOneWins = 0;
        mPlayerTwoWins = 0;

        mAIPlaying = false;
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


    private static class AIPlay extends AsyncTask<Integer, Void, Integer> {

        WeakReference<AIGameActivity> mActivityReference;

        AIPlay(AIGameActivity context) {
            mActivityReference = new WeakReference<>(context);
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            return mActivityReference.get().mGameModel.AIdropToken(integers[0]);
        }

        @Override
        protected void onPostExecute(Integer column) {
            super.onPostExecute(column);
            mActivityReference.get().aiTurn(column);
        }
    }

    private void aiTurn(int column) {

        Pair<Integer, Integer> coordinates = mGameModel.dropToken(column);
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

        mAIPlaying = false;
    }
}
