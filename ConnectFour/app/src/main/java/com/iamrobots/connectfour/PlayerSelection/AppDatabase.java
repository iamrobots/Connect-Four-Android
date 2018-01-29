package com.iamrobots.connectfour.PlayerSelection;

/**
 * Created by namrathamanjunatha on 1/28/18.
 */


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;


@Database(entities = {PlayerDetls.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PlayerDetlsDao playerDao();
}



