package com.iamrobots.connectfour.online;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.github.nkzawa.emitter.Emitter;
import com.iamrobots.connectfour.R;
import com.iamrobots.connectfour.database.AppDatabase;
import com.iamrobots.connectfour.database.Player;
import com.iamrobots.connectfour.gamePlay.BoardView;
import com.iamrobots.connectfour.gamePlay.TokenView;

import com.iamrobots.connectfour.gamePlay.PlayAgainDialog;
import java.util.List;

public class OnlinedemoActivity extends AppCompatActivity {

    private static final String TAG = "GameActivity";

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

    private GameActivity mGameActivity;

    private Context mContext;
    private int mRounds;
    private int mCurrentRound;
    private Boolean mRewindable;
    private int mPlayerOneWins;
    private int mPlayerTwoWins;

    private AppDatabase db;
    private Player mPlayerOne;
    private Player mPlayerTwo;
    private List<String> mScoreList;
    private Socket mSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_game);
mContext=this;
        {
            try {
                mSocket = IO.socket("http://10.0.0.31:3001");
                Log.e("message :  ", "Fine!");
                String msg = "connected successfully!!";
                mSocket.emit("new msg", msg);
            } catch (Exception e) {
                Log.e("message     :   ", "Error Connecting to IP!" + e.getMessage());
            }
        }

        mSocket.on("column_event", columnEvent);
        mSocket.connect();
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

    }

    private Emitter.Listener columnEvent = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            OnlinedemoActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    JSONObject data = (JSONObject) args[0];
                    int column;
                    int row;
                    int player;
                    try {
                        row = data.getInt("row");
                        column = data.getInt("column");
                        player = data.getInt("player");
                    } catch (JSONException e) {
                        return;
                    }


                    //mGameActivity.gameInPlay(row,column);
                    Pair<Integer,Integer> coordinates = mGameModel.dropToken(column);
                    mBoardView.dropToken(row, column, player );
                    //mGameModel.setCurrentPlayer();

                    if (mGameModel.getGameState() == 1) {
                       // mBoardView.highlightTokens(mGameModel.getWinners(), mGameModel.getCurrentPlayer());
                        //gameWon();
                    }
                    if (player == 0) {
                        mFirstPlayerToken.selected();
                        mSecondPlayerToken.unselected();
                    } else {
                        mSecondPlayerToken.selected();
                        mFirstPlayerToken.unselected();
                    }

                }
            });
        }
    };

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
        String coord = coordinates.first + "," + coordinates.second + "," + player;
        //mSocket.emit("coordinates", coord);
        mBoardView.dropToken(coordinates.first, coordinates.second, player);
        //mGameModel.getCurrentPlayer();
        mSocket.emit("call",coord);



        if (mGameModel.getGameState() == 1) {
            mBoardView.highlightTokens(mGameModel.getWinners(), mGameModel.getCurrentPlayer());
           // gameWon();
        }

        if (mGameModel.getCurrentPlayer() == 0) {
            mFirstPlayerToken.selected();
            mSecondPlayerToken.unselected();
        } else {
            mSecondPlayerToken.selected();
            mFirstPlayerToken.unselected();
        }

        //mRewindButton.setEnabled(mRewindable);
    }

    private void setup() {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String firstPlayerName = preferences.getString(FIRST_PLAYER_KEY, "Alice");
        String secondPlayerName = preferences.getString(SECOND_PLAYER_KEY, "Bob");
        int rows = preferences.getInt(ROW_KEY, 6);
        int columns = preferences.getInt(COLUMNS_KEY, 7);
        mRounds = preferences.getInt(ROUNDS_KEY, 1);
        mCurrentRound = 1;


        // Temporary Variables. Will get rows and columns from PlayerActivity selection.
        int firstPlayerColor = Color.parseColor("#f1c40f");
        int secondPlayerColor = Color.parseColor("#e74c3c");


//        mFirstPlayerTextView = findViewById(R.id.player1_id);
//        mFirstPlayerTextView.setText(firstPlayerName);
        mFirstPlayerToken = findViewById(R.id.player1_token_id);
        mFirstPlayerToken.setColor(firstPlayerColor);
        mFirstPlayerToken.selected();
//
//        mSecondPlayerTextView = findViewById(R.id.player2_id);
//        mSecondPlayerTextView.setText(secondPlayerName);
        mSecondPlayerToken = findViewById(R.id.player2_token_id);
        mSecondPlayerToken.setColor(secondPlayerColor);
        mSecondPlayerToken.unselected();
        //added a new field depth, but this will not affect the actual Game Model
        mGameModel = new GameModel(rows,columns,1);
        mGameActivity = new GameActivity();

        mBoardView = findViewById(R.id.boardView);
        mBoardView.setRowsColumns(rows, columns);
        mBoardView.setFirstPlayerColor(firstPlayerColor);
        mBoardView.setSecondPlayerColor(secondPlayerColor);

//        mRoundsButton = findViewById(R.id.roundsButton);
//        mRoundsButton.setText("Round " + mCurrentRound + "/" + mRounds);
//        mRoundsButton.setTextColor(Color.BLACK);
//        mRoundsButton.setEnabled(false);

//        mRewindable = false;
//        mRewindButton = findViewById(R.id.rewindButton);
//        mRewindButton.setEnabled(mRewindable);
//        mRewindButton.setVisibility(View.GONE);

//        db = AppDatabase.getInstance(this);
//        mPlayerOne = db.playerDao().getPlayerByName(firstPlayerName);
//        mPlayerTwo = db.playerDao().getPlayerByName(secondPlayerName);
//        mPlayerOneWins = 0;
//        mPlayerTwoWins = 0;
    }


}
