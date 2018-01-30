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

    int[][] mboardArray;
    int boardRow,boardColumn;
    int currentPlayer=0;
    GameModel(int rows,int columns) {
        mboardArray = new int[rows][columns];
        boardRow = rows;
        boardColumn = columns;
        for (int i = 0; i < mboardArray.length; i++)
        {
            for (int j = 0; j < mboardArray[i].length; j++)
            {

                mboardArray[i][j] = -1;
            }
        }

        Stack<Integer>[] mboardColumn = (Stack<Integer>[]) new Stack[columns];

    }

    public int getPlayer(){
       return currentPlayer;
    }
    public void setPlayer(){
        currentPlayer = (currentPlayer == 0) ? 1 : 0;
    }

    public Pair<Integer, Integer> dropToken(int column) {
       int returnRow;
     for(int i =0 ; i < boardRow ; i++)
     {
    if(mboardArray[i][column] == -1) {
        mboardArray[i][column] = getPlayer();
        returnRow=i;
        setPlayer();
        return Pair.create(returnRow,column);
    }
    }

     return null;
    }

    // Return Pair<row, column> to be removed
    public Pair<Integer, Integer> rewind() {

        return null;
    }

    public boolean checkHorizontal(){
        for(int i = 0 ; i < boardRow ; i++ )
        {
            for(int j=0 ; j <= boardColumn-4 ; j++ )
            {
                if((mboardArray[i][j] == 0
                        && mboardArray[i][j+1] == 0
                        && mboardArray[i][j+2] == 0
                        && mboardArray[i][j+3] == 0) || (mboardArray[i][j] == 1
                        && mboardArray[i][j+1] == 1
                        && mboardArray[i][j+2] == 1
                        && mboardArray[i][j+3] == 1))
                {
                    return true;
                }

            }
        }
        return false;

    }

    public boolean checkVertical(){
        for(int i = 0 ; i < boardColumn ; i++ )
        {
            for(int j=0 ; j <= boardRow-4 ; j++ )
            {
                if((mboardArray[j][i] == 0
                        && mboardArray[j+1][i] == 0
                        && mboardArray[j+2][i] == 0
                        && mboardArray[j+3][i] == 0) || (mboardArray[j][i] == 1
                    && mboardArray[j+1][i] == 1
                    && mboardArray[j+2][i] == 1
                    && mboardArray[j+3][i] == 1))
                {
                    return true;
                }

            }
        }
        return false;
    }

    public boolean checkDiagonalUpwards(){
        for(int i = 0 ; i <= boardRow-4 ; i++ )
        {
            for(int j=3 ; j <=boardColumn-1  ; j++ )
            {
                if((mboardArray[i][j] == 0
                        && mboardArray[i+1][j-1] == 0
                        && mboardArray[i+2][j-2] == 0
                        && mboardArray[i+3][j-3] == 0) || (mboardArray[i][j] == 1
                        && mboardArray[i+1][j-1] == 1
                        && mboardArray[i+2][j-2] == 1
                        && mboardArray[i+3][j-3] == 1))
                {
                    return true;
                }

            }
        }
        return false;
    }

    public boolean checkDiagonalDownwards(){
        for(int i = 0 ; i <= boardRow-4 ; i++ )
        {
            for(int j=0 ; j <= boardColumn-4 ; j++ )
            {
                if((mboardArray[i][j] == 0
                        && mboardArray[i+1][j+1] == 0
                        && mboardArray[i+2][j+2] == 0
                        && mboardArray[i+3][j+3] == 0) || (mboardArray[i][j] == 1
                        && mboardArray[i+1][j+1] == 1
                        && mboardArray[i+2][j+2] == 1
                        && mboardArray[i+3][j+3] == 1))
                {
                    return true;
                }

            }
        }
        return false;
    }

    public int getGameState() {

        boolean horz = checkHorizontal();
        boolean vert = checkVertical();
        boolean diagUp = checkDiagonalUpwards();
        boolean diagDown = checkDiagonalDownwards();

        if (horz || vert || diagUp || diagDown)
            return 1;
        else
            //yet to add draw logic
            return 0;
    }


    // Return list of winners Pair<row, column>
    public ArrayList<Pair<Integer, Integer>> getWinners() {

        return null;
    }

    public void reset() {

    }
}
