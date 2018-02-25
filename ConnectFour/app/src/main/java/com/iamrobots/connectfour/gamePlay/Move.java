package com.iamrobots.connectfour.gamePlay;

/**
 * Created by David Lively on 2/24/18.
 * lively@iamrobots.com
 */

public class Move {

    private int mColumn;
    private int mScore;

    public Move(int column, int score) {
        mColumn = column;
        mScore = score;
    }

    public int getColumn() {
        return mColumn;
    }

    public void setColumn(int column) {
        mColumn = column;
    }

    public int getScore() {
        return mScore;
    }

    public void setScore(int score) {
        mScore = score;
    }
}
