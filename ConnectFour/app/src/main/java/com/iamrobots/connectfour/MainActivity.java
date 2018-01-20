package com.iamrobots.connectfour;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.iamrobots.connectfour.views.BoardView;



// TESTING TESTING 1, 2, 3

public class MainActivity extends AppCompatActivity {

    BoardView mBoardView;
    int mCurrentPlayer;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCurrentPlayer = 0;
        mBoardView = findViewById(R.id.boardView);


        mBoardView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        float x = event.getX();
                        float y = event.getY();
                        int row = mBoardView.getRow(y);
                        int column = mBoardView.getColumn(x);
                        if (x < 0 || y < 0) break;
                        mBoardView.dropBall(row, column, mCurrentPlayer);
                        mCurrentPlayer = mCurrentPlayer == 0 ? 1: 0;
                        break;
                }
                return true;
            }
        });
    }
}
