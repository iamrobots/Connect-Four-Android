package com.iamrobots.connectfour.gamePlay;

import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.util.Log;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by David Lively on 2/22/18.
 * lively@iamrobots.com
 */

public class AIModel {
    private static final String TAG = AIModel.class.getName();

    private int mRows;
    private int mColumns;
    private int mDepth;

    private int[][] mBoardArray;


    public AIModel(int rows, int columns, int depth) {
        mRows = rows;
        mColumns = columns;
        mDepth = depth;

        mBoardArray = new int[rows][columns];
        for (int i = 0; i < mRows; i++) {
            for (int j = 0; j < mColumns; j++) {
                mBoardArray[i][j] = -1;
            }
        }
    }

    public int dropToken(int column) {
       //First move might be better to be random.
        for (int i = 0; i < mRows; i++) {
            if (mBoardArray[i][column] == -1) {
                mBoardArray[i][column] = 0;
            }
        }

        column = getColumn();

        for (int i = 0; i < mRows; i++) {
            if (mBoardArray[i][column] == -1) {
                mBoardArray[i][column] = 0;
            }
        }

        return column;
    }

    public void reset() {
        for (int i = 0; i < mBoardArray.length; i++) {
            for (int j = 0; j < mBoardArray.length; j++) {
                mBoardArray[i][j] = -1;
            }
        }

    }

    @NonNull
    private Move chooseMove(int player, int opponent, int alpha, int beta, int depth) {
        Move best = new Move(-1, player == 1 ? alpha : beta);

        for (int column = 0; column < mColumns; column++) {
            if (mBoardArray[mRows - 1][column] == -1) {
                placeToken(player, column);
                int score = 0;

                if (checkBoard()) {
                    score = player == 1 ? 1 : -1;
                } else if (depth > 1) {
                    score = chooseMove(opponent, player, alpha, beta, depth - 1).getScore();
                }

                removeToken(column);

                if (player == 1 && score > best.getColumn()) {
                    best = new Move(column, score);
                    alpha = score;
                } else if (player == 0 && score < best.getScore()) {
                    best = new Move(column, score);
                    beta = score;
                }

                if (alpha >= beta) {
                    return best;
                }
            }
        }
        return best;
    }

    private int getColumn() {
        return chooseMove(1, 0, -10000, 10000, mDepth).getColumn();
    }

    private void placeToken(int player, int column) {
        for (int i = 0; i < mRows; i++) {
            if (mBoardArray[i][column] == -1) {
                mBoardArray[i][column] = player;
                break;
            }
        }
    }

    private void removeToken(int column) {
        for (int i = 1; i < mRows - 1; ++i) {
            if (mBoardArray[i + 1][column] == -1) {
                mBoardArray[i][column] = -1;
                break;
            }
        }
    }




    private boolean checkHorizontal(){
        for(int i = 0; i < mRows; i++ )
        {
            for(int j = 0; j <= mColumns -4 ; j++ )
            {
                if((mBoardArray[i][j] == 0
                        && mBoardArray[i][j+1] == 0
                        && mBoardArray[i][j+2] == 0
                        && mBoardArray[i][j+3] == 0) || (mBoardArray[i][j] == 1
                        && mBoardArray[i][j+1] == 1
                        && mBoardArray[i][j+2] == 1
                        && mBoardArray[i][j+3] == 1))
                {
                    return true;
                }

            }
        }
        return false;

    }

    private boolean checkBoard() {

        if (checkHorizontal()
                || checkVertical()
                || checkDiagonalUpwards()
                || checkDiagonalDownwards()) {
            return true;
        }
        return false;
    }

    private boolean checkVertical(){
        for(int i = 0; i < mColumns; i++ )
        {
            for(int j = 0; j <= mRows -4 ; j++ )
            {
                if((mBoardArray[j][i] == 0
                        && mBoardArray[j+1][i] == 0
                        && mBoardArray[j+2][i] == 0
                        && mBoardArray[j+3][i] == 0) || (mBoardArray[j][i] == 1
                        && mBoardArray[j+1][i] == 1
                        && mBoardArray[j+2][i] == 1
                        && mBoardArray[j+3][i] == 1))
                {
                    return true;
                }

            }
        }
        return false;
    }

    private boolean checkDiagonalUpwards(){
        for(int i = 0; i <= mRows -4 ; i++ )
        {
            for(int j = 3; j <= mColumns -1  ; j++ )
            {
                if((mBoardArray[i][j] == 0
                        && mBoardArray[i+1][j-1] == 0
                        && mBoardArray[i+2][j-2] == 0
                        && mBoardArray[i+3][j-3] == 0) || (mBoardArray[i][j] == 1
                        && mBoardArray[i+1][j-1] == 1
                        && mBoardArray[i+2][j-2] == 1
                        && mBoardArray[i+3][j-3] == 1))
                {
                    return true;
                }

            }
        }
        return false;
    }

    private boolean checkDiagonalDownwards(){
        for(int i = 0; i <= mRows -4 ; i++ )
        {
            for(int j = 0; j <= mColumns -4 ; j++ )
            {
                if((mBoardArray[i][j] == 0
                        && mBoardArray[i+1][j+1] == 0
                        && mBoardArray[i+2][j+2] == 0
                        && mBoardArray[i+3][j+3] == 0) || (mBoardArray[i][j] == 1
                        && mBoardArray[i+1][j+1] == 1
                        && mBoardArray[i+2][j+2] == 1
                        && mBoardArray[i+3][j+3] == 1))
                {
                    return true;
                }

            }
        }
        return false;
    }
}
