package com.iamrobots.connectfour.gamePlay;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;

/**
 * Created by David Lively on 2/22/18.
 * lively@iamrobots.com
 */

public class AIModel {

    private int mRows;
    private int mColumns;
    private int mDifficutly;

    private int[][] mBoardArray;


    public AIModel(int rows, int columns) {
        mRows = rows;
        mColumns = columns;

        mBoardArray = new int[rows][columns];
        for (int i = 0; i < mBoardArray.length; i++) {
            for (int j = 0; j < mBoardArray.length; j++) {
                mBoardArray[i][j] = -1;
            }
        }
    }

    public int dropToken(int column)
    {
        for(int i=0;i<mRows;i++) {
            if ( mBoardArray[i][column] == -1)
            {
                mBoardArray[i][column] = 0;
            }
        }
        Random rand = new Random();
        int value = rand.nextInt(mColumns);
        while (mBoardArray[mRows-1][value] == -1){
            value = rand.nextInt(mColumns);
        }
        for(int i=0;i<mRows;i++) {
            if ( mBoardArray[i][value] == -1)
            {
                mBoardArray[i][value] = 1;
            }
        }
        //add time to wait
        return value;
    }

    public void reset(){
        for (int i = 0; i < mBoardArray.length; i++) {
            for (int j = 0; j < mBoardArray.length; j++) {
                mBoardArray[i][j] = -1;
            }
        }

    }



}
