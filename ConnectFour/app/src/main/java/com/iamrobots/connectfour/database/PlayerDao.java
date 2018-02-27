package com.iamrobots.connectfour.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.iamrobots.connectfour.database.Player;

import java.util.List;

/**
 * Created by namrathamanjunatha on 1/28/18.
 */

    // TODO: Add methods that returns a player by getting their id.
    // TODO: Add method to remove a player (by id or name or something).

@Dao
public interface PlayerDao {

    @Query("SELECT * FROM players")
    List<Player> getPlayers();

    @Query("SELECT * FROM players order by wins DESC limit 5")
    List<Player> getTopScores();

    @Query("SELECT player_name FROM players WHERE id = :id")
    String getPlayerNameById(int id);

    @Query("SELECT * FROM players WHERE player_name = :name")
    Player getPlayerByName(String name);

    @Query("SELECT player_name FROM players order by wins DESC limit 5")
    List<String> topScores();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(Player... users);

    @Update
    void updatePlayers(Player... players);

    @Delete
    void deletePlayers(Player... players);
}
