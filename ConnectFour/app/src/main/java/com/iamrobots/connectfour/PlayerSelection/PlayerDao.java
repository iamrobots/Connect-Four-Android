package com.iamrobots.connectfour.PlayerSelection;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by namrathamanjunatha on 1/28/18.
 */

    // TODO: Add methods that returns a player by getting their id.
    // TODO: Add method to remove a player (by id or name or something).

@Dao
public interface PlayerDao {

    @Query("Select * from players")
    public LiveData<List<Player>> getAll();

    @Query("SELECT player_name FROM players WHERE id = :id")
    public String getPlayerNameById(int id);

    @Insert
    public void insertAll(Player... users);

   // @Query("DELETE FROM players")
  //  public void deleteTable();
}
