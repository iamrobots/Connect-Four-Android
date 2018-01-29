package com.iamrobots.connectfour.PlayerSelection;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by namrathamanjunatha on 1/28/18.
 */

    // TODO: Rename this class to PlayerDAO.
    // TODO: Add methods that returns a player by getting their id.
    // TODO: Add method to remove a player (by id or name or something).

@Dao
public interface PlayerDetlsDao {

    @Query("Select * from players")
    public List<PlayerDetls> getAll();

    @Query("SELECT player_name FROM players WHERE id = :id")
    public String getPlayerNameById(int id);

    @Insert
    public void insertAll(PlayerDetls... users);

   // @Query("DELETE FROM players")
  //  public void deleteTable();
}
