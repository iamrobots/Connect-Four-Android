package com.iamrobots.connectfour.GamePlay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.util.Pair;

import com.iamrobots.connectfour.R;

import java.util.Stack;

public class GameActivity extends AppCompatActivity {
    BoardView mBoardView;
    ImageButton mRewindButton;
    int mCurrentPlayer;
    Stack<Pair<Integer, Integer >> stackRowColumn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mCurrentPlayer = 0;
        mBoardView = findViewById(R.id.boardView);
        mRewindButton = findViewById(R.id.rewindButton);
        stackRowColumn = new Stack<>();

        mBoardView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.performClick();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        float x = event.getX();
                        float y = event.getY();
                        int row = mBoardView.getRow(y);
                        int column = mBoardView.getColumn(x);
                        if (x < 0 || y < 0) break;
                        if (mBoardView.addToken(row, column, mCurrentPlayer))
                            mCurrentPlayer = mCurrentPlayer == 0 ? 1: 0;
                        stackRowColumn.push(new Pair<>(row, column));
                        break;
                }
                return true;
            }
        });

        mRewindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!stackRowColumn.isEmpty()) {
                    Pair<Integer, Integer> pair = stackRowColumn.pop();
                    mBoardView.removeToken(pair.first, pair.second);
                    mCurrentPlayer = mCurrentPlayer == 0 ? 1 : 0;
                }
            }
        });

    }
}
