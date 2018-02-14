package com.iamrobots.connectfour.PlayerSelection;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by namrathamanjunatha on 1/28/18.
 */

    // TODO: Add methods that returns a player by getting their id.
    // TODO: Add method to remove a player (by id or name or something).

@Dao
public interface PlayerDao {

    @Query("Select * from players")
    public List<Player> getPlayers();

    @Query("SELECT player_name FROM players WHERE id = :id")
    public String getPlayerNameById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAll(Player... users);

    @Update
    public void updatePlayers(Player... players);

    @Delete
    public void deletePlayers(Player... players);
}
