package com.iamrobots.connectfour.gamePlay;

/**
 * Created by David Lively on 2/22/18.
 * lively@iamrobots.com
 */

public class AIModel {

    private int mRows;
    private int mColumns;
    private int mDifficutly;

    private int[][] mBoardArray;
    private int mCurrentPlayer;

    public AIModel(int rows, int columns) {
        mRows = rows;
        mColumns = columns;
        mCurrentPlayer = 0;

        mBoardArray = new int[rows][columns];
        for (int i = 0; i < mBoardArray.length; i++) {
            for (int j = 0; j < mBoardArray.length; j++) {
                mBoardArray[i][j] = -1;
            }
        }
    }


}
