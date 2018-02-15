package com.iamrobots.connectfour.PlayerSelection;

/**
 * Created by namrathamanjunatha on 1/28/18.
 */


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.graphics.Color;


// TODO: prefill database with two default players.

@Database(entities = {Player.class}, version = 4)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PlayerDao playerDao();

    private static final String DB_NAME = "production";
    private static volatile AppDatabase instance;

    static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }

        return instance;
    }

    private static AppDatabase create(final Context context) {
        AppDatabase database = Room.databaseBuilder(context, AppDatabase.class, DB_NAME)
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build();

        database.playerDao().insertAll(new Player("Alice", Color.RED),
                new Player("Bob", Color.BLACK),
                new Player("Jane", Color.YELLOW),
                new Player("David", Color.BLUE));

        return database;
    }

}



