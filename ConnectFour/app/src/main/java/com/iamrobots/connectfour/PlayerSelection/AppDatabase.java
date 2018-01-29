package com.iamrobots.connectfour.PlayerSelection;

/**
 * Created by namrathamanjunatha on 1/28/18.
 */


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;


// TODO: Make this a singleton class.
// TODO: prefill database with two default players.

@Database(entities = {PlayerDetls.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PlayerDetlsDao playerDao();
}



