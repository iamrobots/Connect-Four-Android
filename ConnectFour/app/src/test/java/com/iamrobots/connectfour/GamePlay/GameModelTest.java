package com.iamrobots.connectfour.GamePlay;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by David Lively on 1/30/18.
 * lively@iamrobots.com
 */
public class GameModelTest {
    @Test
    public void currentPlayerSwitchesAfterDropToken() throws Exception {
        GameModel tester = new GameModel(6, 7);

        assertEquals( "current player should be 0", 0, tester.getCurrentPlayer());
        tester.dropToken(1);
        assertEquals( "current player should now be 1", 1, tester.getCurrentPlayer());
    }
}