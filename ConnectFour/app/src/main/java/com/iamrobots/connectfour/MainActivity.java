package com.iamrobots.connectfour;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.iamrobots.connectfour.views.BoardView;

public class MainActivity extends AppCompatActivity {

    BoardView mBoardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBoardView = (BoardView) findViewById(R.id.boardView);

//        mBoardView.dropBall(0, 0, 0);
    }
}
