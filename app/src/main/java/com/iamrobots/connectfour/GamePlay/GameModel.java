package com.iamrobots.connectfour.GamePlay;

import android.util.Pair;

import java.util.ArrayList;

/**
 * Created by David Lively on 1/22/18.
 * lively@iamrobots.com
 */

public class GameModel {

    public void setRows(int rows) {

    }

    public void setColumns(int columns) {

    }

    public boolean dropToken(int column) {

        return false;
    }

    // Return Pair<row, column> to be removed
    public Pair<Integer, Integer> rewind() {

        return null;
    }

    public int getGameState() {

        return 0;
    }

    // Return list of winners Pair<row, column>
    public ArrayList<Pair<Integer, Integer>> getWinners() {

        return null;
    }

    public void reset() {

    }
}
