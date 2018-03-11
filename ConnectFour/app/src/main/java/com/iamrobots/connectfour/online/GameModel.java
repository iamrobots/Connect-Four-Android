package com.iamrobots.connectfour.online;

import android.support.annotation.NonNull;
import android.util.Pair;

import com.iamrobots.connectfour.gamePlay.Move;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by Aniketha Katakam on 1/22/18.
 *
 */

// TODO: Add a stack of the columns that where selected to keep track of what was played.


public class GameModel {
    private static final String TAG = GameModel.class.getName();

    private int[][] mBoardArray;
    private int mBoardRow;
    private int mBoardColumn;
    private int mDepth;
    private int mCurrentPlayer;
    private int mGameState;

    private ArrayList<Pair<Integer, Integer>> mWinCoordinates;
    private Stack<Pair<Integer, Integer>> mMoveStack;

    GameModel(int rows, int columns, int depth) {
        mCurrentPlayer = 0;
        mGameState = 0;
        mBoardArray = new int[rows][columns];
        mBoardRow = rows;
        mBoardColumn = columns;
        mDepth = depth;
        mWinCoordinates = new ArrayList<>();
        mMoveStack = new Stack<>();
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

    public int AIdropToken(int column) {
        column = getColumn();
        return column;
    }


    @NonNull
    private Move chooseMove(int player, int opponent, int alpha, int beta, int depth) {
        Move best = new Move(-1, player == 1 ? alpha : beta);

        for (int column = 0; column < mBoardColumn; column++) {
            if (mBoardArray[mBoardRow - 1][column] == -1) {
                placeToken(player, column);
                int score = 0;
                if (checkBoard()) {
                    score = player == 1 ? 1 : -1;
                    score = player == 1 ? 1 : -1;
                } else if (depth > 1) {
                    score = chooseMove(opponent, player, alpha, beta, depth - 1).getScore();
                }
                removeToken(column);

                if (player == 1 && score > best.getScore()) {
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
        return chooseMove(0, 1, -10000, 10000, mDepth).getColumn();
    }

    private void placeToken(int player, int column) {
        for (int i = 0; i < mBoardRow; i++) {
            if (mBoardArray[i][column] == -1) {
                mBoardArray[i][column] = player;
                break;
            }
        }
    }

    private void removeToken(int column) {
        for (int i = 1; i < mBoardRow; ++i) {
            if (mBoardArray[i][column] == -1) {
                mBoardArray[i-1][column] = -1;
                break;
            }
        }
        mBoardArray[mBoardRow-1][column] = -1;
    }

    private boolean checkBoard() {
        boolean horz = AIcheckHorizontal();
        boolean vert = AIcheckVertical();
        boolean diagUp = AIcheckDiagonalUpwards();
        boolean diagDown = AIcheckDiagonalDownwards();

        if (horz|| vert || diagUp || diagDown) {
            return true;
        }
        return false;
    }

    private boolean AIcheckHorizontal(){
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
                    return true;
                }

            }
        }
        return false;

    }

    private boolean AIcheckVertical(){
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
                    return true;
                }

            }
        }
        return false;
    }

    private boolean AIcheckDiagonalUpwards(){
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
                    return true;
                }

            }
        }
        return false;
    }

    private boolean AIcheckDiagonalDownwards(){
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
                    return true;
                }

            }
        }
        return false;
    }


    public Pair<Integer, Integer> dropToken(int column) {
        int returnRow;
        for(int i = 0; i < mBoardRow; i++)
        {
            if(mBoardArray[i][column] == -1) {
                mBoardArray[i][column] = getCurrentPlayer();

                setCurrentPlayer();
                returnRow=i;

                boolean horz = checkHorizontal();
                boolean vert = checkVertical();
                boolean diagUp = checkDiagonalUpwards();
                boolean diagDown = checkDiagonalDownwards();

                if (horz|| vert || diagUp || diagDown) {
                    mGameState = 1; //game won
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
                    //setCurrentPlayer();
                }
                mMoveStack.push(new Pair<>(returnRow, column));
                return Pair.create(returnRow,column);
            }
        }

        return null;
    }

    // Return Pair<row, column> to be removed
    public Pair<Integer, Integer> rewind() {
        if (mMoveStack.isEmpty())
            return null;

        Pair<Integer, Integer> rowColumnPair = mMoveStack.pop();
        setCurrentPlayer();
        mBoardArray[rowColumnPair.first][rowColumnPair.second] = -1;
        return rowColumnPair;
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
        mGameState = 0;
        mCurrentPlayer = 0;
        mWinCoordinates = new ArrayList<>();

        for (int i = 0; i < mBoardArray.length; i++)
        {
            for (int j = 0; j < mBoardArray[i].length; j++)
            {
                mBoardArray[i][j] = -1;
            }
        }
    }
}
