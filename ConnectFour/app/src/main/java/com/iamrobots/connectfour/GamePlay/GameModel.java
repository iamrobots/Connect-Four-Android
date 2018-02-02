package com.iamrobots.connectfour.GamePlay;

import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by Aniketha Katakam on 1/22/18.
 *
 */

// TODO: Make all member variables private.
// TODO: Refactor member variables to be of the form mBoardArray, mRows, mColumns ect.
// TODO: Create a mGameState member variable to keep track of what state the game is in instead of checking in getGameState.
// TODO: Have dropToken method return a Pair<Integer, Integer> that corresponds to Row and Column that the token was put into.
//
// TODO: Make internal methods private (checkHorizontal, checkVertical ect).
// TODO: mGameState should be checked and set in the dropToken method.
// TODO: Add a stack of the columns that where selected to keep track of what was played.


public class GameModel {

    private int[][] mBoardArray;
    private int mBoardRow;
    private int mBoardColumn;
    private int mCurrentPlayer = 0;
    private int mGameState;

    private ArrayList<Pair<Integer, Integer>> mWinCoordinates;

    GameModel(int rows,int columns) {
        mBoardArray = new int[rows][columns];
        mBoardRow = rows;
        mBoardColumn = columns;
        mWinCoordinates = new ArrayList<>();
        for (int i = 0; i < mBoardArray.length; i++)
        {
            for (int j = 0; j < mBoardArray[i].length; j++)
            {
                mBoardArray[i][j] = -1;
            }
        }
    }

    public int getCurrentPlayer(){
        return mCurrentPlayer;
    }

    public void setCurrentPlayer(){
        mCurrentPlayer = (mCurrentPlayer == 0) ? 1 : 0;
    }

    public Pair<Integer, Integer> dropToken(int column) {
        int returnRow;
        for(int i = 0; i < mBoardRow; i++)
        {
            if(mBoardArray[i][column] == -1) {
                mBoardArray[i][column] = getCurrentPlayer();
                returnRow=i;

                boolean horz = checkHorizontal();
                boolean vert = checkVertical();
                boolean diagUp = checkDiagonalUpwards();
                boolean diagDown = checkDiagonalDownwards();

                if (horz|| vert || diagUp || diagDown) {
                    mGameState = 1; //game won
                    Log.i("debug testing","coordinates "+mWinCoordinates);
                }
                else {
                    int stalemateCounter=0;
                    int inplayCounter=0;
                    int boardSize;
                    //yet to add draw logic
                    for (int k = 0; k < mBoardArray.length; k++)
                    {
                        for (int j = 0; j < mBoardArray[k].length; j++)
                        {
                            if(mBoardArray[k][j]== 1 || mBoardArray[k][j]== 0)
                                stalemateCounter++;
                            if(mBoardArray[k][j]== -1)
                                inplayCounter++;
                        }
                    }

                    boardSize = (mBoardArray.length)*(mBoardArray[0].length);

                    if(boardSize > inplayCounter )
                        mGameState = 0;
                    if(stalemateCounter+1 == boardSize)
                        mGameState=2;
                    Log.i("boardsize","size is "+ boardSize);
                    Log.i("stalemateCounter","stalemate is "+ stalemateCounter);
                    Log.i("inplayCounter","inplayCounter is "+ inplayCounter);
                    setCurrentPlayer();
                }

                return Pair.create(returnRow,column);
            }
        }

        return null;
    }

    // Return Pair<row, column> to be removed
    public Pair<Integer, Integer> rewind() {

        return null;
    }

    private boolean checkHorizontal(){
        for(int i = 0; i < mBoardRow; i++ )
        {
            for(int j = 0; j <= mBoardColumn -4 ; j++ )
            {
                if((mBoardArray[i][j] == 0
                        && mBoardArray[i][j+1] == 0
                        && mBoardArray[i][j+2] == 0
                        && mBoardArray[i][j+3] == 0) || (mBoardArray[i][j] == 1
                        && mBoardArray[i][j+1] == 1
                        && mBoardArray[i][j+2] == 1
                        && mBoardArray[i][j+3] == 1))
                {
                    mWinCoordinates.add( Pair.create(i, j));
                    mWinCoordinates.add( Pair.create(i, j+1));
                    mWinCoordinates.add( Pair.create(i, j+2));
                    mWinCoordinates.add( Pair.create(i, j+3));
                    return true;
                }

            }
        }
        return false;

    }

    private boolean checkVertical(){
        for(int i = 0; i < mBoardColumn; i++ )
        {
            for(int j = 0; j <= mBoardRow -4 ; j++ )
            {
                if((mBoardArray[j][i] == 0
                        && mBoardArray[j+1][i] == 0
                        && mBoardArray[j+2][i] == 0
                        && mBoardArray[j+3][i] == 0) || (mBoardArray[j][i] == 1
                        && mBoardArray[j+1][i] == 1
                        && mBoardArray[j+2][i] == 1
                        && mBoardArray[j+3][i] == 1))
                {
                    mWinCoordinates.add( Pair.create(j, i));
                    mWinCoordinates.add( Pair.create(j+1,i));
                    mWinCoordinates.add( Pair.create(j+2,i));
                    mWinCoordinates.add( Pair.create(j+3,i));
                    return true;
                }

            }
        }
        return false;
    }

    private boolean checkDiagonalUpwards(){
        for(int i = 0; i <= mBoardRow -4 ; i++ )
        {
            for(int j = 3; j <= mBoardColumn -1  ; j++ )
            {
                if((mBoardArray[i][j] == 0
                        && mBoardArray[i+1][j-1] == 0
                        && mBoardArray[i+2][j-2] == 0
                        && mBoardArray[i+3][j-3] == 0) || (mBoardArray[i][j] == 1
                        && mBoardArray[i+1][j-1] == 1
                        && mBoardArray[i+2][j-2] == 1
                        && mBoardArray[i+3][j-3] == 1))
                {
                    mWinCoordinates.add( Pair.create(i, j));
                    mWinCoordinates.add( Pair.create(i+1, j-1));
                    mWinCoordinates.add( Pair.create(i+2, j-2));
                    mWinCoordinates.add( Pair.create(i+3, j-3));
                    return true;
                }

            }
        }
        return false;
    }

    private boolean checkDiagonalDownwards(){
        for(int i = 0; i <= mBoardRow -4 ; i++ )
        {
            for(int j = 0; j <= mBoardColumn -4 ; j++ )
            {
                if((mBoardArray[i][j] == 0
                        && mBoardArray[i+1][j+1] == 0
                        && mBoardArray[i+2][j+2] == 0
                        && mBoardArray[i+3][j+3] == 0) || (mBoardArray[i][j] == 1
                        && mBoardArray[i+1][j+1] == 1
                        && mBoardArray[i+2][j+2] == 1
                        && mBoardArray[i+3][j+3] == 1))
                {
                    mWinCoordinates.add( Pair.create(i, j));
                    mWinCoordinates.add( Pair.create(i+1, j+1));
                    mWinCoordinates.add( Pair.create(i+2, j+2));
                    mWinCoordinates.add( Pair.create(i+3, j+3));
                    return true;
                }

            }
        }
        return false;
    }

    public int getGameState() {

        return mGameState;
    }


    // Return list of winners Pair<row, column>
    public ArrayList<Pair<Integer, Integer>> getWinners() {

        return mWinCoordinates;
    }

    public void reset() {

    }
}
